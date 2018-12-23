package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import court.model.Court;

public class CourtMapLoadPopup {

	public static void createLoadPopup() {
		JFrame GUI = new JFrame("Load map");
		GUI.setLayout(new GridLayout(3,1));
		GUI.add(new JLabel("Map Name:"));
		
		JTextField nameField = new JTextField();
		
		JButton loadButton = new JButton("Load");
		
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//ID here is just a filler
				MainUI.startEditior(new Court(1,nameField.getText()));
			}			
		});
		
		GUI.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {
			}
			@Override
			public void windowClosed(WindowEvent arg0) {
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
			}
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				GUI.setVisible(false);
			}
			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}
			@Override
			public void windowIconified(WindowEvent arg0) {
			}
			@Override
			public void windowOpened(WindowEvent arg0) {
			}			
		});
		
		GUI.add(nameField);
		GUI.add(loadButton);
		GUI.pack();
		GUI.setVisible(true);
	}
	
}
