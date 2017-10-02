package mvu;

import java.awt.*;
import javax.swing.*;

public class FlightWindow {
	private static JFrame frame = new JFrame("Titel1");
	private static JPanel pane = new JPanel();
	private static JLabel jl1 = new JLabel("Abflugland");
	private static JLabel jl2 = new JLabel("Zielland");

	
	public static void createGUI(){
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setResizable(false);
		
		pane.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		
		c.gridx = 0;
		c.gridy = 0;
		pane.add(jl1,c);
		
		c.gridx = 1;
		c.gridy = 0;
		pane.add(jl2,c);
		
		c.gridx = 0;
		c.gridy = 1;
		//TODO Soll dynamisch auslesen aus der DB k�nnen
		String countrieList1[] = {"item01","item02"};
		JComboBox<String> countrieSelect1 = new JComboBox<String>(countrieList1);
		pane.add(countrieSelect1,c);
		
		c.gridx = 1;
		c.gridy = 1;
		//TODO Soll dynamisch auslesen aus der DB k�nnen
		String countrieList2[] = {"item01","item02"};
		JComboBox<String> countrieSelect2 = new JComboBox<String>(countrieList2);
		pane.add(countrieSelect2,c);
		
		//TODO Platzalter f�r m�gliche Fl�ge
		c.gridx = 0;
		c.gridy = 2;
		JLabel possibleflights = new JLabel("M�glichen Fl�ge:");
		pane.add(possibleflights, c);
		
		c.gridx = 1;
		c.gridy = 2;
		String possflight[] = {"item01","item02"};
		JComboBox<String> possibleflightsBox = new JComboBox<String>(possflight);
		pane.add(possibleflightsBox, c);
		
		frame.add(pane);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
	createGUI();
	}
}