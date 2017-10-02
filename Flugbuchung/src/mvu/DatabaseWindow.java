package mvu;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DatabaseWindow implements ActionListener{
	private  JFrame frame = new JFrame("DB Server");
	private  JPanel pane = new JPanel();
	 JLabel hostname = new JLabel("Hostname");
	 JLabel port = new JLabel("Port");
	 JLabel user = new JLabel("User");
	 JLabel password = new JLabel("password");
	 
	 JTextField hostnameInput = new JTextField();
	 JTextField portInput = new JTextField();
	 JTextField userInput = new JTextField();
	 JTextField passwordInput = new JTextField();
	
	 JButton submit = new JButton("Submit");
	 
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
		
		c.gridx = 0;
		c.gridy = 1;
		c.weightx=1.;
	    c.fill=GridBagConstraints.HORIZONTAL;
		pane.add(hostnameInput,c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.weightx=1.;
	    c.fill=GridBagConstraints.HORIZONTAL;
		pane.add(portInput,c);
		
		c.gridx = 2;
		c.gridy = 1;
		c.weightx=1.;
	    c.fill=GridBagConstraints.HORIZONTAL;
		pane.add(userInput,c);
		
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
		//TODO Ruft Database mit den eingebenen Werten ein
		boolean readyToSubmit = true;
		if(portInput.getText() != null){
			if(!portInput.getText().matches("[0-9]*")) {
	            JOptionPane.showMessageDialog(null, "Ung�ltige Port Nummer");    
	            portInput.setText("");
	            readyToSubmit = false;
	        }
		}
		submitDataToDatabase(hostnameInput.getText(),portInput.getText(),userInput.getText(), passwordInput.getText());
	}
	
	public void submitDataToDatabase(String hostname,String port, String user, String password){
		//TODO submit
	}
}
