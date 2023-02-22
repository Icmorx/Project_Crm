package it.logicainformatica.projectcrm.db;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			PreparedStatement statement = dbconn
					.prepareStatement("INSERT INTO anagrafica(nome, cognome, telefono) VALUES(?, ?, ?)");

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

	// STAMPA I DATI DA DB
	public List<ProjectCrmBean> stampaDati() {

		// CREO L'OGGETTO CONNECTION
		Connection dbconn = null;
		
		List<ProjectCrmBean> lista = new ArrayList<ProjectCrmBean>();

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

				// IMPORTO CLASSE BEAN
				ProjectCrmBean p = new ProjectCrmBean();

				// INSERENDO IL NOME DELLA COLONNA E MI PRENDO IL VALORE
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setCognome(rs.getNString("cognome"));
				p.setTelefono(rs.getString("telefono"));

				lista.add(p);
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
		return lista;
	}
 
	// CREO E SCRIVO SUL FILE
	public void newFile() {
		String path = "C:/Project_Crm.txt";

		try {
			File file = new File(path);
			FileWriter write = new FileWriter("Project_Crm.txt");
			ProjectCrmBean p = new ProjectCrmBean();

			if(file.exists()) {
				write.write("Id: " + p.getId() + ",");
				write.write("Nome: " + p.getNome() + ",");
				write.write("Cognome: " + p.getCognome() + ",");
				write.write("Telefono: " + p.getTelefono() + ",");
			} else if (file.createNewFile()){
				write.write("Id: " + p.getId() + ",");
				write.write("Nome: " + p.getNome() + ",");
				write.write("Cognome: " + p.getCognome() + ",");
				write.write("Telefono: " + p.getTelefono() + ",");
			}
			write.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
