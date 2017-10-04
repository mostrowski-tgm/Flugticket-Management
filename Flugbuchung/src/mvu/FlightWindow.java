package mvu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FlightWindow implements ActionListener{
	private static JFrame frame = new JFrame("Titel1");
	private static JPanel pane = new JPanel();
	private static JLabel JAbflugland = new JLabel("Abflugland");
	private static JLabel Jzielland = new JLabel("Zielland");
	private static JLabel JAbflughafen = new JLabel("Abflughafen");
	private static JLabel JZielflughafen = new JLabel("Zielflughafen");
	
	private static String countrieList1[];
	private static String countrieList2[];
	private static JComboBox<String> countrieSelect1;
	private static JComboBox<String> countrieSelect2;
	private static String selctedCountrie1;
	private static String selctedCountrie2;
	
	private static String[] airportAr1; 
	private static String[] airportAr2;
	private static JComboBox<String> airportSelect1;
	private static JComboBox<String> airportSelect2;
	
	private static String[] possflightAr;
	private static JTable possibleflightsTable;
	
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
		
		JAbflugland.setFont(JAbflugland.getFont().deriveFont(32.0f));
		Jzielland.setFont(Jzielland.getFont().deriveFont(32.0f));
		
		c.gridx = 0;
		c.gridy = 0;
		pane.add(JAbflugland,c);
		
		c.gridx = 1;
		c.gridy = 0;
		pane.add(Jzielland,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx=1.;
		//Abflugland bestimmen
		countrieList1 = dc.getAllCountries();
		countrieSelect1 = new JComboBox<String>(countrieList1);
		countrieSelect1.setActionCommand("countrySelect1");
		countrieSelect1.addActionListener(this);
		pane.add(countrieSelect1,c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.weightx=1.;
		
		//Zielland bestimmen
		countrieList2 = dc.getAllCountries();
		countrieSelect2 = new JComboBox<String>(countrieList2);
		countrieSelect2.setActionCommand("countrySelect2");
		countrieSelect2.addActionListener(this);
		pane.add(countrieSelect2,c);
		
		//Font der JLabels �ndern
		JAbflughafen.setFont(JAbflughafen.getFont().deriveFont(32.0f));
		JZielflughafen.setFont(JZielflughafen.getFont().deriveFont(32.0f));
		
		c.gridx = 0;
		c.gridy = 2;
		c.weightx=1.;
		pane.add(JAbflughafen,c);
		
		c.gridx = 1;
		c.gridy = 2;
		c.weightx=1.;
		pane.add(JZielflughafen,c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.weightx=1.;
		
		//Dropdown Liste f�r Flughaefen dynamisch erzeugt nachdem user Land ausgew�hlt hat
		//airportList ist ein String Array von Flughaefen in einem Land
		airportAr1 = null;
		airportSelect1 = new JComboBox<String>();
		pane.add(airportSelect1,c);
		
		c.gridx = 1;
		c.gridy = 3;
		c.weightx=1.;
		
		airportAr2 = null;
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
		possflightAr = null;
		possibleflightsTable = new JTable();
	//	possibleflightsTable.setActionCommand("possibleFlights");
	//	possibleflightsTable.addActionListener(this);
		pane.add(possibleflightsTable, c);
		
		frame.add(pane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * Um dynamisch eine Liste zu erstellen ist diese Funktion seperat n�tig
	 * @param country
	 * @return String Array von den Flugh�fen eines Landes
	 */
	public static String[] getAirports(String country){
		return dc.getAirportsCountry(country);
	}
	
	public static void connectToDatabase(String hostname, String port, String user, String password, String databasename){
		dc = new DatabaseConnection(hostname,port,user,password,databasename);
		FlightWindow fw = new FlightWindow();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		dc.getFlights("CMA", "IZT");
		if(e.getActionCommand().equals("countrySelect1")){
			selctedCountrie1 = (String) countrieSelect1.getSelectedItem();
			airportAr1 = dc.getAirportsCountry(selctedCountrie1);
			airportSelect1.removeAllItems();
			for(int i = 0; i<airportAr1.length;i++){
				airportSelect1.addItem(airportAr1[i]);
			}
		}else if(e.getActionCommand().equals("countrySelect2")){
			selctedCountrie2 = (String) countrieSelect2.getSelectedItem();
			airportAr2 = dc.getAirportsCountry(selctedCountrie2);
			airportSelect2.removeAllItems();
			for(int i = 0; i<airportAr2.length;i++){
				airportSelect2.addItem(airportAr2[i]);
			}
		}else if(e.getActionCommand().equals("possibleFlights")){
			
		}
	}
	
	public static void main(String[] args){
		dw = new DatabaseWindow();
	}
}
