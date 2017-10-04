package mvu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FlightWindow implements ActionListener{
	private static JFrame frame = new JFrame("Titel1");
	private static JPanel pane = new JPanel();
	private static JLabel JAbflugland = new JLabel("Abflugland");
	private static JLabel Jzielland = new JLabel("Zielland");
	private static JLabel JAbflughafen = new JLabel("Abflughafen");
	private static JLabel JZielflughafen = new JLabel("Zielflughafen");
	
	private static String countrieList1[];
	private static String countrieList2[];
	private static JComboBox<String> countrieDrop1;
	private static JComboBox<String> countrieDrop2;
	private static String selctedCountrie1;
	private static String selctedCountrie2;
	
	private static String[] airportAr1; 
	private static String[] airportAr2;
	private static JComboBox<String> airportDrop1;
	private static JComboBox<String> airportDrop2;
	private static String selectedAirport1;
	private static String selectedAirport2;
	
	private static String[] columnNames = {"Airline", "Flightnumber", "Departure Airport", "Departure Time", "Destination Airport", "Destination Time", "Planetype"};
	private static String depAirportCode;
	private static String destAirportCode;
	private static DefaultTableModel model;
	private static String[] possflightAr;
	private static JTable possflightsTable;
	
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
		countrieDrop1 = new JComboBox<String>(countrieList1);
		countrieDrop1.setActionCommand("countrySelect1");
		countrieDrop1.addActionListener(this);
		pane.add(countrieDrop1,c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.weightx=1.;
		
		//Zielland bestimmen
		countrieList2 = dc.getAllCountries();
		countrieDrop2 = new JComboBox<String>(countrieList2);
		countrieDrop2.setActionCommand("countrySelect2");
		countrieDrop2.addActionListener(this);
		pane.add(countrieDrop2,c);
		
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
		airportDrop1 = new JComboBox<String>();
		airportDrop1.setActionCommand("airportDrop1");
		airportDrop1.addActionListener(this);
		pane.add(airportDrop1,c);
		
		c.gridx = 1;
		c.gridy = 3;
		c.weightx=1.;
		
		airportAr2 = null;
		airportDrop2 = new JComboBox<String>();
		airportDrop2.setActionCommand("airportDrop2");
		airportDrop2.addActionListener(this);
		pane.add(airportDrop2,c);
		
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
		c.gridwidth = 4;
		possflightAr = null;
		model = new DefaultTableModel(columnNames, 0);
		possflightsTable = new JTable(model);
	//	possflightsTable.setActionCommand("possibleFlights");
	//	possflightsTable.addActionListener(this);
		pane.add(new JScrollPane(possflightsTable), c);
		
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
		if(e.getActionCommand().equals("countrySelect1")){
			selctedCountrie1 = (String) countrieDrop1.getSelectedItem();
			airportAr1 = dc.getAirportsCountry(selctedCountrie1);
			airportDrop1.removeAllItems();
			for(int i = 0; i<airportAr1.length;i++){
				airportDrop1.addItem(airportAr1[i]);
			}
		}else if(e.getActionCommand().equals("countrySelect2")){
			selctedCountrie2 = (String) countrieDrop2.getSelectedItem();
			airportAr2 = dc.getAirportsCountry(selctedCountrie2);
			airportDrop2.removeAllItems();
			for(int i = 0; i<airportAr2.length;i++){
				airportDrop2.addItem(airportAr2[i]);
			}
		}else if(e.getActionCommand().equals("airportDrop1") || e.getActionCommand().equals("airportDrop2")){

			selectedAirport1 = (String) airportDrop1.getSelectedItem();
			selectedAirport2 = (String) airportDrop2.getSelectedItem();
			System.out.println(selectedAirport1);
			System.out.println(selectedAirport2);
			if(selectedAirport1 != null && selectedAirport2 != null){
				
				depAirportCode = dc.getAirportcode(selctedCountrie1, selectedAirport1);
				destAirportCode = dc.getAirportcode(selctedCountrie2, selectedAirport2);
				//System.out.println("CODE: "+depAirportCode+";"+destAirportCode);
				//possflightsTable.removeAll();
				try{
					int lengthOfFlights = dc.getFlights(depAirportCode, destAirportCode).size();
					if(lengthOfFlights != 0){
						//possflightsTable = new JTable(dc.getFlights(depAirportCode, depAirportCode), columnNames);
						//possflightsTable.addRow(dc.getFlights(depAirportCode, destAirportCode));
						for(int i = 0; i<lengthOfFlights;i++){
							System.out.println(dc.getFlights(depAirportCode, destAirportCode));
							ArrayList<Object[]> ar = dc.getFlights(depAirportCode, destAirportCode);
							model.addRow(ar.get(i));
							frame.pack();
						}
						
						
					}
				}catch(Exception exc){
					exc.printStackTrace();
					System.out.println("Keine vorhandenen Fl�ge!");
				}
			}
			//Die Zeile gilt wenn der User auf einen Flug klickt
		}else if(e.getActionCommand().equals("possibleFlights")){
			
		}
	}
	
	public static void main(String[] args){
		dw = new DatabaseWindow();
	}
}
