package it.logicainformatica.projectcrm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.logicainformatica.projectcrm.bean.ProjectCrmBean;

public class ProjectCrmDB {

	DataBase db = new DataBase();
	
	// INSERIRE I DATI SUL DB
	public void inserisciUtente(ProjectCrmBean p) {

		// CREO L'OGGETTO CONNECTION
		Connection dbconn = null;

		try {

			// VALORIZZO L'OGGETTO CONNECTION
			dbconn = db.getConnessione();

			// PREPARO L'ISTRUZIONE SQL
			PreparedStatement statement = dbconn.prepareStatement(
					"INSERT INTO anagrafica(nome, cognome, telefono) VALUES(?, ?, ?)");

			// SOSTITUISCO IL ? CON UN VALORE REALE
			statement.setString(1, p.getNome());
			statement.setString(2, p.getCognome());
			statement.setString(3, p.getTelefono());
			
			// ESEGUO LA QUERY SUL DB
			statement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				dbconn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	public void stampaDati() {

		// CREO L'OGGETTO CONNECTION
		Connection dbconn = null;

		try {

			// VALORIZZO L'OGGETTO CONNECTION
			dbconn = db.getConnessione();

			// PREPARO L'ISTRUZIONE SQL
			PreparedStatement statement = dbconn.prepareStatement("SELECT * FROM anagrafica");

			// LANCIO LA QUERY SUL DATA BASE E I RISULTATI ME LI RESTITUSCIE IN UN OGGETTO
			// DI TIPO RESULTSET
			ResultSet rs = statement.executeQuery();

			// CICLO I VALORI CHE ARRIVANO DAL DB
			while (rs.next()) {

				ProjectCrmBean p = new ProjectCrmBean();
				
				// INSERENDO IL NOME DELLA COLONNA E MI PRENDO IL VALORE
				 p.setNome(rs.getString("nome"));
				 p.setCognome(rs.getNString("cognome"));
				 p.setTelefono(rs.getString("telefono"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				dbconn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
