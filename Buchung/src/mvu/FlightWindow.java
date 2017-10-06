package mvu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class FlightWindow implements ActionListener{
	private static JFrame frame = new JFrame("Flugbuchung");
	private static JPanel pane = new JPanel();
	
	private static JLabel JAbflugland = new JLabel("Abflugland");
	private static JLabel Jzielland = new JLabel("Zielland");
	private static JLabel AbflughafenLabel = new JLabel("Abflughafen");
	private static JLabel ZielflughafenLabel = new JLabel("Zielflughafen");
	
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
	private static JButton searchFlightButton;
	private static JLabel possibleflightsLabel;
	private static String[] columnNames = {"Airline", "Flightnumber", "Departure Airport", "Departure Time", "Destination Airport", "Destination Time", "Planetype"};
	private static String depAirportCode;
	private static String destAirportCode;
	private static DefaultTableModel model;
	private static String[] possflightAr;
	private static JTable possflightsTable;
	private static JScrollPane tablePane;
	
	private static BookingWindow bw;
	private static DatabaseWindow dw;
	static DatabaseConnection dc;
	/**
	 * Erstellt das GUI, hat ein Gridlayout
	 */
	public FlightWindow(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pane.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		//Sozusagen ein Margin der ueberall 5px abstand zum Rand behaelt
		c.insets = new Insets(5,5,5,5);
		
		//Font der JLabels aendern
		JAbflugland.setFont(JAbflugland.getFont().deriveFont(32.0f));
		Jzielland.setFont(Jzielland.getFont().deriveFont(32.0f));
		AbflughafenLabel.setFont(AbflughafenLabel.getFont().deriveFont(32.0f));
		ZielflughafenLabel.setFont(ZielflughafenLabel.getFont().deriveFont(32.0f));
		
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
		countrieDrop1.setSelectedIndex(-1);
		countrieDrop1.setActionCommand("countrySelect1");
		countrieDrop1.addActionListener(this);
		pane.add(countrieDrop1,c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.weightx=1.;
		
		//Zielland bestimmen
		countrieList2 = dc.getAllCountries();
		countrieDrop2 = new JComboBox<String>(countrieList2);
		countrieDrop2.setSelectedIndex(-1);
		countrieDrop2.setActionCommand("countrySelect2");
		countrieDrop2.addActionListener(this);
		pane.add(countrieDrop2,c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.weightx=1.;
		pane.add(AbflughafenLabel,c);
		
		c.gridx = 1;
		c.gridy = 2;
		c.weightx=1.;
		pane.add(ZielflughafenLabel,c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.weightx=1.;
		
		//Dropdown Liste fuer Flughaefen dynamisch erzeugt nachdem user Land ausgewaehlt hat
		//airportAr1 ist ein String Array von Flughaefen in einem Land
		airportAr1 = null;
		airportDrop1 = new JComboBox<String>();
		pane.add(airportDrop1,c);
		
		c.gridx = 1;
		c.gridy = 3;
		c.weightx=1.;
		
		//airportAr2 ist ein String Array von Flughaefen in einem Land
		airportAr2 = null;
		airportDrop2 = new JComboBox<String>();
		pane.add(airportDrop2,c);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		searchFlightButton = new JButton("Search for a Flight");
		searchFlightButton.setActionCommand("searchFlightButtonButton");
		searchFlightButton.addActionListener(this);
		pane.add(searchFlightButton, c);
		
		//TODO Platzalter fuer moegliche Fluege
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.weightx=1.0;
		possibleflightsLabel = new JLabel("Moeglichen Fluege:");
		possibleflightsLabel.setFont(possibleflightsLabel.getFont().deriveFont(32.0f));
		pane.add(possibleflightsLabel, c);
		
		//Erstellt ein TableModel mit Anschriften aber keinen rows, zudem kann man die Zellen nicht bearbeiten
		c.gridx = 0;
		c.gridy = 6;
		c.weightx=1.0;
		c.gridwidth = 4;
		possflightAr = null;
		model = new DefaultTableModel(columnNames, 0){
			@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		possflightsTable = new JTable(model);
		//Wrapt Table in einen ScrollPane um es anzuzeigen
		tablePane = new JScrollPane(possflightsTable);
		pane.add(tablePane, c);
		

		setInvisble();
		frame.add(pane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * Setzt alle Felder, au�er die Landauswahl mit dazugehoeriger JLabel auf, unsichtbar
	 */
	public static void setInvisble(){
		AbflughafenLabel.setVisible(false);
		ZielflughafenLabel.setVisible(false);
		airportDrop1.setVisible(false);
		airportDrop2.setVisible(false);
		searchFlightButton.setVisible(false);
		possibleflightsLabel.setVisible(false);
		tablePane.setVisible(false);
	}
	
	/**
	 * Fragt Datenbank nach den Flughaefen in einem Land ab
	 * @param country
	 * @return String Array von den Flughaefen eines Landes
	 */
	public static String[] getAirports(String country){
		return dc.getAirportsCountry(country);
	}
	
	/**
	 * Connected zur Datenbank, wird in DatabaseWindow aufgerufen mit den entsprechenden Parameter
	 * Damit man die Datenbank Connection im FlightWindow aufbaut und einspeichern kann
	 * @param hostname
	 * @param port
	 * @param user
	 * @param password
	 * @param databasename
	 */
	public static void connectToDatabase(String hostname, String port, String user, String password, String databasename){
		dc = new DatabaseConnection(hostname,port,user,password,databasename);
		FlightWindow fw = new FlightWindow();
	}
	
	/**
	 * ActionListener der, je nach auswaehlten Feld, die entsprechend naechsten List- bzw Tablleneintraege dynamisch aus der Datenbank ausliest und einfuegt
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("countrySelect1") || e.getActionCommand().equals("countrySelect2")){
			selctedCountrie1 = (String) countrieDrop1.getSelectedItem();
			selctedCountrie2 = (String) countrieDrop2.getSelectedItem();
			
			if(selctedCountrie1 != null && selctedCountrie2 != null){
				airportAr1 = dc.getAirportsCountry(selctedCountrie1);
				airportDrop1.removeAllItems();
				for(int i = 0; i<airportAr1.length;i++){
					airportDrop1.addItem(airportAr1[i]);
				}
				
				airportAr2 = dc.getAirportsCountry(selctedCountrie2);
				airportDrop2.removeAllItems();
				airportDrop1.setSelectedIndex(-1);
				for(int i = 0; i<airportAr2.length;i++){
					airportDrop2.addItem(airportAr2[i]);
				}
				
				searchFlightButton.setVisible(true);
				AbflughafenLabel.setVisible(true);
				ZielflughafenLabel.setVisible(true);
				airportDrop1.setVisible(true);
				airportDrop2.setVisible(true);
				
				frame.pack();
			}
		}else if(e.getActionCommand().equals("searchFlightButtonButton")){
			selectedAirport1 = (String) airportDrop1.getSelectedItem();
			selectedAirport2 = (String) airportDrop2.getSelectedItem();
			if(selectedAirport1 != null && selectedAirport2 != null){
				depAirportCode = dc.getAirportcode(selctedCountrie1, selectedAirport1);
				destAirportCode = dc.getAirportcode(selctedCountrie2, selectedAirport2);
				try{
					for(int i = 0; i<model.getRowCount(); i++){
						model.removeRow(i);
					}
					
					int lengthOfFlights = dc.getFlights(depAirportCode, destAirportCode).size();
					if(lengthOfFlights != 0){
						model.fireTableRowsDeleted(0, model.getRowCount());
						for(int i = 0; i<lengthOfFlights;i++){
							ArrayList<Object[]> ar = dc.getFlights(depAirportCode, destAirportCode);
							model.addRow(ar.get(i));
							possflightsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
								/**
								 * Wenn User auf die Tabelle drueckt wird BookingWindow aufgerufen
								 */
								@Override
								public void valueChanged(ListSelectionEvent e) {
									if(e.getValueIsAdjusting()){
										bw = new BookingWindow(possflightsTable.getValueAt(possflightsTable.getSelectedRow(),2).toString()
												,possflightsTable.getValueAt(possflightsTable.getSelectedRow(),4).toString()
												,possflightsTable.getValueAt(possflightsTable.getSelectedRow(),3).toString()
												,possflightsTable.getValueAt(possflightsTable.getSelectedRow(),5).toString()
												,possflightsTable.getValueAt(possflightsTable.getSelectedRow(),0).toString()
												,possflightsTable.getValueAt(possflightsTable.getSelectedRow(),6).toString()
												,possflightsTable.getValueAt(possflightsTable.getSelectedRow(),1).toString());
									}
									
								}
							});
							
							possibleflightsLabel.setVisible(true);
							tablePane.setVisible(true);
							frame.pack();
						}
					}else{
						JOptionPane.showMessageDialog(null, ("Kein Flug vorhanden!"));
					}
				}catch(Exception exc){
					System.out.println(exc.getMessage());
				}
			}
		}
	}
	
	/**
	 * Main methode
	 * @param args
	 */
	public static void main(String[] args){
		dw = new DatabaseWindow();
	}
}
