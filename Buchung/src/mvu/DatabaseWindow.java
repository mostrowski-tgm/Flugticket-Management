package mvu;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.sql.*;

public class DatabaseWindow implements ActionListener{
	private  JFrame frame = new JFrame("DB Server");
	private  JPanel pane = new JPanel();
	JLabel hostname = new JLabel("Hostname");
	JLabel port = new JLabel("Port");
	JLabel user = new JLabel("User");
	JLabel password = new JLabel("Password");
	JLabel databasename = new JLabel("Databasename");
	
	JTextField hostnameInput = new JTextField();
	JTextField portInput = new JTextField();
	JTextField userInput = new JTextField();
	JPasswordField passwordInput = new JPasswordField();
	JTextField databasenameInput = new JTextField();
	
	JButton submit = new JButton("Submit");

	private static Properties properties;
public DatabaseWindow (){
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	pane.setLayout(new GridBagLayout());
	
	GridBagConstraints c = new GridBagConstraints();
	c.insets = new Insets(5,5,5,5);
	
	c.gridx = 0;
	c.gridy = 0;
	c.ipadx = 30;
	pane.add(hostname,c);
	
	c.gridx = 1;
	c.gridy = 0;
	pane.add(port,c);
	
	c.gridx = 2;
	c.gridy = 0;
	pane.add(user,c);
	
	c.gridx = 3;
	c.gridy = 0;
	pane.add(password,c);
	
	c.gridx = 4;
	c.gridy = 0;
	pane.add(databasename,c);
	
	//Hostname, f�r Lokal ist es "localhost"
	c.gridx = 0;
	c.gridy = 1;
	c.weightx=1.;
    c.fill=GridBagConstraints.HORIZONTAL;
	pane.add(hostnameInput,c);
	
	//Port, local => "3306"
	c.gridx = 1;
	c.gridy = 1;
	c.weightx=1.;
    c.fill=GridBagConstraints.HORIZONTAL;
	pane.add(portInput,c);
	
	//User, standartm�ssig "root"
	c.gridx = 2;
	c.gridy = 1;
	c.weightx=1.;
    c.fill=GridBagConstraints.HORIZONTAL;
	pane.add(userInput,c);
	
	//Pw, passwort um in die MySQL CMD zu kommen
	c.gridx = 3;
	c.gridy = 1;
	c.weightx=1.;
    c.fill=GridBagConstraints.HORIZONTAL;
	pane.add(passwordInput,c);
	
	c.gridx = 4;
	c.gridy = 1;
	c.weightx=1.;
    c.fill=GridBagConstraints.HORIZONTAL;
	pane.add(databasenameInput,c);
	
	c.gridx = 5;
	c.gridy = 1;
	c.weightx=1.;
    c.fill=GridBagConstraints.HORIZONTAL;
	pane.add(submit,c);
	submit.addActionListener(this);
	
	//Test um nicht immer das gleiche eingeben zu m�ssen
	hostnameInput.setText("localhost");
	userInput.setText("root");
	passwordInput.setText("");
	databasenameInput.setText("flightdata");
	portInput.setText("3306");
	
	frame.add(pane);
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
	frame.setMinimumSize(frame.getSize());
}
/**
 * Checkt den Input ob g�ltig, wenn ja schickt er diesen an FlightWindow weiter
 */
@Override
public void actionPerformed(ActionEvent arg0) {
	properties = new Properties();
	boolean readyToSubmit = true;
	if(portInput.getText() != null){
		if(!portInput.getText().matches("[0-9]*")) {
            JOptionPane.showMessageDialog(null, "Ung�ltige Port Nummer");    
            portInput.setText("");
            readyToSubmit = false;
        }else{
        	properties.setProperty("port", portInput.getText());
        }
	}
	if(hostnameInput.getText().equals("localhost")){
		properties.setProperty("hostname", "127.0.0.1");
	}
	
	if(userInput.getText() == null || passwordInput.getText() == null || databasenameInput.getText() == null){
		JOptionPane.showMessageDialog(null, "Ung�ltige Eingabe!");
		readyToSubmit = false;
	}
	if(readyToSubmit){
		properties.setProperty("user", userInput.getText());
		properties.setProperty("password", passwordInput.getText());
		properties.setProperty("databasename", databasenameInput.getText());
	}
	//TODO Sysout l�schen
	frame.dispose();
	FlightWindow.connectToDatabase(getProperties().getProperty("hostname"),getProperties().getProperty("port"),getProperties().getProperty("user"),getProperties().getProperty("password"),getProperties().getProperty("databasename"));
	
	
	}

public static Properties getProperties(){
	return properties;
}


}
