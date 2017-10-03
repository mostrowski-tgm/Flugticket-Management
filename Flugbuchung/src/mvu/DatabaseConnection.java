package mvu;

import java.sql.Connection;

public class DatabaseConnection {
	private Connection connection;
	
	//TODO Eventuelle Auswahl der Datenbank und damit verschiedene database_driver einbinden
	private static String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static String DATABASE_URL; //"jdbc:mysql://localhost:3306/database_name"
	private static String USERNAME;
	private static String PASSWORD;
	
	public DatabaseConnection(){
		DatabaseWindow dbw = new DatabaseWindow();
		DATABASE_URL = "jdbc:mysql://"+dbw.getProperties().getProperty("hostname")+":"+dbw.getProperties().getProperty("port")+"/flightdata";
		USERNAME = dbw.getProperties().getProperty("user");
		PASSWORD = dbw.getProperties().getProperty("password");
	}
	
	public Connection connect(){
		if (connection == null){
			try {
				Class.forName(DATABASE_DRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
	}
}
