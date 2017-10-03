package mvu;

import java.awt.*;
import javax.swing.*;

public class FlightWindow {
	private static JFrame frame = new JFrame("Titel1");
	private static JPanel pane = new JPanel();
	private static JLabel abflugland = new JLabel("Abflugland");
	private static JLabel zielland = new JLabel("Zielland");
	private static JLabel abflughafen = new JLabel("Abflughafen");
	private static JLabel zielflughafen = new JLabel("Zielflughafen");
	
	/**
	 * Erstellt das GUI, hat ein Gridlayout
	 */
	public static void createGUI(){
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
		String countrieList1[] = {"item01","item02"};
		JComboBox<String> countrieSelect1 = new JComboBox<String>(countrieList1);
		pane.add(countrieSelect1,c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.weightx=1.;
		//TODO Soll dynamisch auslesen aus der DB k�nnen
		String countrieList2[] = {"item01","item02"};
		JComboBox<String> countrieSelect2 = new JComboBox<String>(countrieList2);
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
		//TODO Soll dynamisch auslesen aus der DB k�nnen
		String airportList1[] = {"item01","item02"};
		JComboBox<String> airportSelect1 = new JComboBox<String>(airportList1);
		pane.add(airportSelect1,c);
		
		c.gridx = 1;
		c.gridy = 3;
		c.weightx=1.;
		//TODO Soll dynamisch auslesen aus der DB k�nnen
		String airportList2[] = {"item01","item02"};
		JComboBox<String> airportSelect2 = new JComboBox<String>(airportList2);
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
	
	public static void main(String[] args){
		DatabaseWindow dw = new DatabaseWindow();
	}
}
