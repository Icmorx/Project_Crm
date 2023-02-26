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

import it.logicainformatica.projectcrm.bean.AnagraficaBean;

// classe che al suo interno contine tutti i metodi che interagiscono con il DB
public class MetodiDB {

	DataBase db = new DataBase();

	// metodo che serve per inserire i dati dell utente sul DB
	public void inserisciUtente(AnagraficaBean usrObj) {

		// creo l'oggetto connection
		Connection dbconn = null;

		try {

			// do un valore all'oggetto connection
			dbconn = db.getConnessione();

			// preparo l'istruzione sql
			PreparedStatement statement = dbconn
					.prepareStatement("INSERT INTO anagrafica(nome, cognome, telefono) VALUES(?, ?, ?)");

			// sostituisco i ? con dei valori reali
			statement.setString(1, usrObj.getNome());
			statement.setString(2, usrObj.getCognome());
			statement.setString(3, usrObj.getTelefono());

			// eseguo la query
			statement.execute();

		} catch (SQLException e) { // gestisco eventuali errori di tipo sql
			e.printStackTrace();
			System.out.println("Errore SQL nel metodo stampaDati " + e.getMessage());
		} catch (Exception e) { // gestisco eventuali errori generici
			e.printStackTrace();
			System.out.println("Errore generico nel metodo inserisciUtente" + e.getMessage());
		} finally { // nel finaly chiudo la connessione con il DB
			try {
				dbconn.close();
			} catch (SQLException e) { // gestisco eventuali errori di tipo sql
				e.printStackTrace();
			}
		}

	}

	// metodo per prendere i dati dal DB
	public List<AnagraficaBean> getDati() {

		// creo l'oggetto connection
		Connection dbconn = null;

		// creo un oggetto di tipo lista
		List<AnagraficaBean> lista = new ArrayList<AnagraficaBean>();

		try {

			// do un valore all'oggetto connection
			dbconn = db.getConnessione();

			// preparo l'istruzione sql
			PreparedStatement statement = dbconn.prepareStatement("SELECT * FROM anagrafica");

			// lancio la query sul DB e mi restituisce i dati in un'oggetto di tipo
			// ResultSet
			ResultSet rs = statement.executeQuery();

			// ciclo i valori che prendo dal db
			while (rs.next()) {

				// importo la classe con i set i e get
				AnagraficaBean usrObj= new AnagraficaBean();

				// inserendo il nome della colonna mi prendo il dato contenuto in essa
				usrObj.setId(rs.getInt("id"));
				usrObj.setNome(rs.getString("nome"));
				usrObj.setCognome(rs.getNString("cognome"));
				usrObj.setTelefono(rs.getString("telefono"));

				// aggiungo i dati presi dalle colonne all'oggetto lista
				lista.add(usrObj);
			}

		} catch (SQLException e) { // gestisco eventuali errori di tipo sql
			e.printStackTrace();
			System.out.println("Errore SQL nel metodo stampaDati " + e.getMessage());
		} finally { // nel finaly chiudo la connessione con il DB
			try {
				dbconn.close();
			} catch (SQLException e) { // gestisco eventuali errori di tipo sql
				e.printStackTrace();
			}
		}
		// ritorno l'oggetto lista pieno
		return lista;
	}

	// creo il file e scrivo sul file
	public void writeFile(AnagraficaBean usrObj) throws IOException {

		// creo un nuovo file specificando la directory e il nome che dovrà avere il
		// file
		File file = new File("Project_Crm.txt");

		// creo l'oggetto che mi serve per scrivere i dati sul file e gli passo
		// l'oggetto file
		FileWriter fW = new FileWriter(file, true); // aggiungo il parametro true per scrivere al fondo del file

		try {
			// scrivo i dati nel file
			fW.write(usrObj.getId() + ",");
			fW.write(usrObj.getNome() + ",");
			fW.write(usrObj.getCognome() + ",");
			fW.write(usrObj.getTelefono() + "\n");

		} catch (Exception e) { // gestisco eventuali errori generici
			e.printStackTrace();
			System.out.println("Errore nel metodo scrivoFile " + e.getMessage());
		} finally {
			// chiudo l'oggetto FileWriter
			fW.close();
		}
	}
}
