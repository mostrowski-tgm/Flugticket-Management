package mvu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DatabaseConnection {
	private Connection connection;
	
	//TODO Eventuelle Auswahl der Datenbank und damit verschiedene database_driver einbinden
	private static String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static String DATABASE_URL; //"jdbc:mysql://localhost:3306/database_name"
	private static String HOSTNAME;
	private static int PORT;
	private static String USERNAME;
	private static String PASSWORD;
	
	private static MysqlDataSource ds;
	public DatabaseConnection(){
		DatabaseWindow dbw = new DatabaseWindow();
		DATABASE_URL = "jdbc:mysql://"+dbw.getProperties().getProperty("hostname")+":"+dbw.getProperties().getProperty("port")+"/flightdata";
		HOSTNAME = dbw.getProperties().getProperty("hostname");
		PORT = Integer.parseInt(dbw.getProperties().getProperty("port"));
		USERNAME = dbw.getProperties().getProperty("user");
		PASSWORD = dbw.getProperties().getProperty("password");
	}
	
	public Connection DatabaseURL(){
		if (connection == null){
			try {
				Class.forName(DATABASE_DRIVER);
				connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	public void connect(){
		ds.setUser(USERNAME);
		ds.setPassword(PASSWORD);
		ds.setServerName(HOSTNAME);
		ds.setPort(PORT);
	}
}
