package mvu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.xml.bind.ParseConversionEvent;

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
		if(ds == null){
			ds = new MysqlDataSource();
			ds.setUser(user);
			ds.setDatabaseName(databasename);
			ds.setPassword(password);
			ds.setServerName(hostname);
			ds.setPort(Integer.parseInt(port));
		}
		
	}
	/**
	 * Baut eine Verbindung zur Database auf und lie�t alle Countrie-namen aus
	 * @return eine Arraylist mit allen L�ndern
	 */
	public String[] getAllCountries(){
		try{
			connection = ds.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT name FROM countries;");
			
			ArrayList<String> ar = new ArrayList<String>();
			while(rs.next()){
				ar.add(rs.getString(1));
			}			
			String[] stringAr = ar.toArray(new String[ar.size()]);
			// aufr�umen
			rs.close(); st.close(); connection.close();
			//return countriesArr;
			return stringAr;
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Connection konnte nicht mit der Database aufgebaut werden!");
		}
		return null;
	}
	
	public String[] getAirportsCountry(String country){
		try{
			connection = ds.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT airports.name FROM airports,countries WHERE airports.country = countries.code AND countries.name = '"+country+"';");
			
			ArrayList<String> ar = new ArrayList<String>();
			while(rs.next()){
				ar.add(rs.getString(1));
			}
			
			String[] stringAr = ar.toArray(new String[ar.size()]);
			//aufr�umen
			rs.close(); st.close(); connection.close();
			return stringAr;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String getAirportcode(String country, String airportname){
		try{
			connection = ds.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT airports.airportcode FROM airports,countries WHERE airports.country = countries.code AND countries.name = '"+country+"' AND airports.name = '"+airportname+"';");
			
			String airportCode = null;
			while(rs.next()){
				airportCode = (rs.getString(1));
			}
			//System.out.println(airportCode);
			//aufr�umen
			rs.close(); st.close(); connection.close();
			return airportCode;
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Connection konnte nicht mit der Database aufgebaut werden!");
		}
		return null;
	}
	
	public ArrayList getFlights(String depAirport, String destAirport){
		try{
			connection = ds.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT airline,flightnr,departure_airport,departure_time,destination_airport,destination_time,planetype FROM flights INNER JOIN airports ON flights.departure_airport = airports.airportcode AND flights.departure_airport =  '"+depAirport+"' AND flights.destination_airport =  '"+destAirport+"';");
			//TODO time in dd.mm.yyyy hh:mm ausgeben
			ArrayList<Object[]> ar = new ArrayList<Object[]>();
			while(rs.next()){
				SimpleDateFormat sFromDep = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sToDep = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				String dateFormattedDep = sToDep.format(sFromDep.parse(rs.getString(4)));

				SimpleDateFormat sFromDest = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sToDest = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				String dateFormattedDest = sToDest.format(sFromDest.parse(rs.getString(6)));
				System.out.println(dateFormattedDest);
				Object[] row = {getFullAirlinesname(rs.getString(1)),rs.getString(2),rs.getString(3),dateFormattedDep,rs.getString(5),dateFormattedDest,getFullPlanename(rs.getString(7))};
				ar.add(row);
			}
			//aufr�umen
			rs.close(); st.close(); connection.close();
			return ar;
		}catch(SQLException | ParseException e){
			JOptionPane.showMessageDialog(null, "Connection konnte nicht mit der Database aufgebaut werden!");
		}
		return null;
	}
	
	public void addPassenger(String name,String lastname, String row, String seat, String airline, String flightnr){
		try{
			connection = ds.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT max(id) FROM passengers;");
			
			int freeID = 0;
			while(rs.next()){
				freeID = rs.getInt("max(id)")+1;
			}
			
			
			rs.close(); st.close();
			PreparedStatement statment = connection.prepareStatement("INSERT INTO passengers VALUES("+freeID+",'"+name+"','"+lastname+"','"+airline+"','"+flightnr+"',"+Integer.parseInt(row)+",'"+seat+"')");
			
			statment.execute();
			
			JOptionPane.showMessageDialog(null, "Erfolgreich gebucht!");
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Es konnte nicht gebucht werden!");
		}
	}
	
	public String getFullAirlinesname(String id){
		try{
			connection = ds.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT name FROM airlines INNER JOIN flights ON airlines.id = flights.airline AND airlines.id = 'AA';");
			
			String fullname = null;
			while(rs.next()){
				fullname = (rs.getString(1));
			}
			//System.out.println(airportCode);
			//aufr�umen
			rs.close(); st.close(); connection.close();
			return fullname;
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Connection konnte nicht mit der Database aufgebaut werden!");
		}
		return null;
	}
	
	public String getFullPlanename(String id){
		try{
			connection = ds.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT manufacturer, type FROM planes INNER JOIN flights ON planes.id = flights.planetype AND planes.id = "+id+" LIMIT 1;");
			
			String fullname = null;
			while(rs.next()){
				fullname = (rs.getString(1)+" "+rs.getString(2));
			}
			//System.out.println(airportCode);
			//aufr�umen
			rs.close(); st.close(); connection.close();
			return fullname;
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Connection konnte nicht mit der Database aufgebaut werden!");
		}
		return null;
	}
	
	public String getFullAirportname(String airportcode){
		try{
			connection = ds.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("SELECT name FROM airports WHERE airports.airportcode = '"+airportcode+"';");
			
			String fullname = null;
			while(rs.next()){
				fullname = (rs.getString(1));
			}
			//System.out.println(airportCode);
			//aufr�umen
			rs.close(); st.close(); connection.close();
			return fullname;
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Connection konnte nicht mit der Database aufgebaut werden!");
		}
		return null;
	}
	
}
