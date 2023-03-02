package it.logicainformatica.projectcrm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// classe che al suo interno contiene la connessione con il db
public class DataBase {

	Connection dbconn = null;

	public Connection getConnessione() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_crm", "root", "");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Errore di tipo ClassNotFoundException nel metodo getConnessione della classe DataBase");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore di tipo SQL nel metodo getConnessione della classe DataBase");
		}
		return dbconn;
	}
}
