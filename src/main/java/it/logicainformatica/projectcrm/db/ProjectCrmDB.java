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

	// metodo che serve per inserire i dati dell utente sul DB
	public void inserisciUtente(ProjectCrmBean p) {

		// creo l'oggetto connection
		Connection dbconn = null;

		try {

			// do un valore all'oggetto connection
			dbconn = db.getConnessione();

			// preparo l'istruzione sql
			PreparedStatement statement = dbconn
					.prepareStatement("INSERT INTO anagrafica(nome, cognome, telefono) VALUES(?, ?, ?)");

			// sostituisco i ? con dei valori reali
			statement.setString(1, p.getNome());
			statement.setString(2, p.getCognome());
			statement.setString(3, p.getTelefono());

			// eseguo la query
			statement.execute();

		} catch (SQLException e) { // gestisco eventuali errori di tipo sql
			e.printStackTrace();
			System.out.println("Errore SQL nel metodo stampaDati " + e.getMessage());
		} catch (Exception e) { // gestisco eventuali errori generici
			e.printStackTrace();
			System.out.println("Errore generico nel metodo inserisciUtente" + e.getMessage());
		} finally {  // nel finaly chiudo la connessione con il DB
			try {
				dbconn.close();
			} catch (SQLException e) { //gestisco eventuali errori di tipo sql
				e.printStackTrace();
			}
		}

	}

	// metodo per prendere i dati dal DB
	public List<ProjectCrmBean> getDati() {

		// creo l'oggetto connection
		Connection dbconn = null;

		// creo un oggetto di tipo lista 
		List<ProjectCrmBean> lista = new ArrayList<ProjectCrmBean>();

		try {

			// do un valore all'oggetto connection
			dbconn = db.getConnessione();

			// preparo l'istruzione sql
			PreparedStatement statement = dbconn.prepareStatement("SELECT * FROM anagrafica");

			// lancio la query sul DB e mi restituisce i dati in un'oggetto di tipo ResultSet
			ResultSet rs = statement.executeQuery();

			// ciclo i valori che prendo dal db
			while (rs.next()) {

				// importo la classe con i set i e get
				ProjectCrmBean p = new ProjectCrmBean();

				// inserendo il nome della colonna mi prendo il dato contenuto in essa
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setCognome(rs.getNString("cognome"));
				p.setTelefono(rs.getString("telefono"));

				// aggiungo i dati presi dalle colonne all'oggetto lista
				lista.add(p);
			}

		} catch (SQLException e) { //gestisco eventuali errori di tipo sql
			e.printStackTrace();
			System.out.println("Errore SQL nel metodo stampaDati " + e.getMessage());
		} finally { // nel finaly chiudo la connessione con il DB
			try {
				dbconn.close();
			} catch (SQLException e) { //gestisco eventuali errori di tipo sql
				e.printStackTrace();
			}
		}
		// ritorno l'oggetto lista pieno
		return lista;
	}

	// creo il file e scrivo sul file
	public void scrivoFile(ProjectCrmBean pB) throws IOException {

		// STRINGA CON LA DIRECTORY DEL FILE TXT
		// String path = "C:/Project_Crm.txt";

		// creo un nuovo file specificando la directory e il nome che dovrà avere il file
		File file = new File("Project_Crm.txt");

		// creo l'oggetto che mi serve per scrivere i dati sul file e gli passo l'oggetto file
		FileWriter fW = new FileWriter(file);

		try {

			// controllo se il file di testo esiste, se esiste scrivo i dati al suo interno
			if (file.exists()) {
				fW.write(pB.getId() + ",");
				fW.write(pB.getNome() + ",");
				fW.write(pB.getCognome() + ",");
				fW.write(pB.getTelefono() + "\n");
			} // se il file non esiste creo un nuovo file e scrivo i dati al suo interno
			else if (file.createNewFile()) {
				fW.write(pB.getId() + ",");
				fW.write(pB.getNome() + ",");
				fW.write(pB.getCognome() + ",");
				fW.write(pB.getTelefono() + "\n");
			}

		} catch (Exception e) { // gestisco eventuali errori generici
			e.printStackTrace();
			System.out.println("Erroe nel metodo scrivoFile " + e.getMessage());
		} finally { // nel finaly chiudo l'oggetto FileWriter
			fW.close();
		}
	}
}
