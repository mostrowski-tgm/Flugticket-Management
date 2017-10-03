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
import javax.swing.JTextField;
import java.sql.*;

public class DatabaseWindow implements ActionListener{
	private  JFrame frame = new JFrame("DB Server");
	private  JPanel pane = new JPanel();
	JLabel hostname = new JLabel("Hostname");
	JLabel port = new JLabel("Port");
	JLabel user = new JLabel("User");
	JLabel password = new JLabel("Password");
	
	JTextField hostnameInput = new JTextField();
	JTextField portInput = new JTextField();
	JTextField userInput = new JTextField();
	JTextField passwordInput = new JTextField();

	JButton submit = new JButton("Submit");
 
	private Properties properties;
 //TODO Fehler bei der Eingabe beheben
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
	pane.add(submit,c);
	submit.addActionListener(this);
	
	frame.add(pane);
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
	frame.setMinimumSize(frame.getSize());
}
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
	if(readyToSubmit){
		properties.setProperty("user", userInput.getText());
		properties.setProperty("password", passwordInput.getText());
		properties.setProperty("hostname", hostnameInput.getText());
	}
	System.out.println(getProperties());
	}

public Properties getProperties(){
	return properties;
}


}
