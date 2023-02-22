package it.logicainformatica.projectcrm.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

	Connection dbconn = null;

	public Connection getConnessione() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project_crm", "root", "");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dbconn;
	}
}
