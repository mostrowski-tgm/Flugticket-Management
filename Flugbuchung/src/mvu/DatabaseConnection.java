package mvu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DatabaseConnection {
	private Connection connection;
	
	//TODO Eventuelle Auswahl der Datenbank und damit verschiedene database_driver einbinden
	/**
	 *private static String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static String DATABASE_URL; //"jdbc:mysql://localhost:3306/database_name"
	private static String HOSTNAME;
	private static int PORT;
	private static String USERNAME;
	private static String PASSWORD;
	 */
	private static MysqlDataSource ds;
	
	/**
	 * Konstruktor baut die DataSource auf
	 * @param hostname
	 * @param port
	 * @param user
	 * @param password
	 * @param databasename
	 */
	public DatabaseConnection(String hostname, String port, String user, String password, String databasename){
		/**
		 * DATABASE_URL = "jdbc:mysql://"+hostname+":"+port+"/flightdata";
		HOSTNAME = hostname;
		PORT = Integer.parseInt(port);
		USERNAME = user;
		PASSWORD = password;
		 */
		ds = new MysqlDataSource();
		ds.setUser(user);
		ds.setDatabaseName(databasename);
		ds.setPassword(password);
		ds.setServerName(hostname);
		ds.setPort(Integer.parseInt(port));
	}
	/**
	 * Baut eine Verbindung zur Database auf und lie�t alle Countrie-namen aus
	 * @return eine Arraylist mit allen L�ndern
	 */
	public ArrayList<String> getAllCountries(){
		try{
			connection = ds.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT name FROM countries;");
			
			ArrayList<String> ar = new ArrayList<String>();
			while(rs.next()){
				ar.add(rs.getString(1));
			}
			/**
			 * Testzwecke
			 * for(int i = 0; i<ar.size();i++){
				System.out.println(ar.get(i));
			}
			 */
			
			
			// aufr�umen
			rs.close(); st.close(); connection.close();
			//return countriesArr;
			return ar;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getAirportsCountry(String country){
		try{
			connection = ds.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT airports.name FROM airports,countries WHERE airports.country = countries.code AND countries.name = '"+country+"';");
			
			ArrayList<String> ar = new ArrayList<String>();
			while(rs.next()){
				ar.add(rs.getString(1));
			}
			
			//aufr�umen
			rs.close(); st.close(); connection.close();
			return ar;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
}
