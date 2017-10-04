package mvu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FlightWindow implements ActionListener{
	private static JFrame frame = new JFrame("Titel1");
	private static JPanel pane = new JPanel();
	private static JLabel abflugland = new JLabel("Abflugland");
	private static JLabel zielland = new JLabel("Zielland");
	private static JLabel abflughafen = new JLabel("Abflughafen");
	private static JLabel zielflughafen = new JLabel("Zielflughafen");
	
	private static String countrieList1[];
	private static JComboBox<String> countrieSelect1;
	private static String countrieList2[];
	private static JComboBox<String> countrieSelect2;
	private static String selctedCountrie1;
	private static String selctedCountrie2;
	private static String[] airportList1; 
	private static String[] airportList2;
	private static JComboBox<String> airportSelect1;
	private static JComboBox<String> airportSelect2;
	private static DatabaseWindow dw;
	private static DatabaseConnection dc;
	/**
	 * Erstellt das GUI, hat ein Gridlayout
	 */
	public FlightWindow(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		
		pane.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		
		abflugland.setFont(abflugland.getFont().deriveFont(32.0f));
		zielland.setFont(zielland.getFont().deriveFont(32.0f));
		
		c.gridx = 0;
		c.gridy = 0;
		pane.add(abflugland,c);
		
		c.gridx = 1;
		c.gridy = 0;
		pane.add(zielland,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx=1.;
		//TODO Soll dynamisch auslesen aus der DB k�nnen
		countrieList1 = dc.getAllCountries();
		countrieSelect1 = new JComboBox<String>(countrieList1);
		countrieSelect1.setActionCommand("countrySelect1");
		countrieSelect1.addActionListener(this);
		pane.add(countrieSelect1,c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.weightx=1.;
		
		countrieList2 = dc.getAllCountries();
		countrieSelect2 = new JComboBox<String>(countrieList2);
		countrieSelect2.setActionCommand("countrySelect2");
		countrieSelect2.addActionListener(this);
		pane.add(countrieSelect2,c);
		
		
		abflughafen.setFont(abflughafen.getFont().deriveFont(32.0f));
		zielflughafen.setFont(zielflughafen.getFont().deriveFont(32.0f));
		
		c.gridx = 0;
		c.gridy = 2;
		c.weightx=1.;
		pane.add(abflughafen,c);
		
		c.gridx = 1;
		c.gridy = 2;
		c.weightx=1.;
		pane.add(zielflughafen,c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.weightx=1.;
		
		airportList1 = null;
		airportSelect1 = new JComboBox<String>();
		pane.add(airportSelect1,c);
		
		c.gridx = 1;
		c.gridy = 3;
		c.weightx=1.;
		
		airportList2 = null;
		airportSelect2 = new JComboBox<String>();
		pane.add(airportSelect2,c);
		
		//TODO Platzalter f�r m�gliche Fl�ge
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.weightx=1.0;
		JLabel possibleflights = new JLabel("M�glichen Fl�ge:");
		possibleflights.setFont(possibleflights.getFont().deriveFont(32.0f));
		pane.add(possibleflights, c);
		
		c.gridx = 0;
		c.gridy = 5;
		c.weightx=1.0;
		String possflight[] = {"item01","item02"};
		JComboBox<String> possibleflightsBox = new JComboBox<String>(possflight);
		pane.add(possibleflightsBox, c);
		
		frame.add(pane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * Actionenlistener einf�gen immer wenn ein anderes Land selected wird
	 * @param country
	 * @return
	 */
	public static String[] getAirports(String country){
		return dc.getAirportsCountry(country);
		
	}
	
	public static void connectToDatabase(String hostname, String port, String user, String password, String databasename){
		dc = new DatabaseConnection(hostname,port,user,password,databasename);
		FlightWindow fw = new FlightWindow();
		/*
		 * dc.getAllCountries();
			System.out.println("Get Airports:");
			dc.getAirportsCountry("Austria");
			System.out.println("Get Airportcode");
			dc.getAirportcode("Austria", "St Anton");
			System.out.println("Get Flights");
			dc.getFlights("CMA", "IZT");
		 */
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand() +", countrySelect1");
		
		if(e.getActionCommand().equals("countrySelect1")){
			System.out.println("hier2");
			selctedCountrie1 = (String) countrieSelect1.getSelectedItem();
			airportList1 = dc.getAirportsCountry(selctedCountrie1);
			airportSelect1 = new JComboBox<String>(airportList1);
		}else if(e.getActionCommand().equals("countrySelect2")){
			selctedCountrie2 = (String) countrieSelect2.getSelectedItem();
			airportList2 = dc.getAirportsCountry(selctedCountrie2);
			airportSelect2 = new JComboBox<String>(airportList2);
		}
		
		
		
		
		
		
	}
	
	public static void main(String[] args){
		dw = new DatabaseWindow();
	}
}
