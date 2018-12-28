package view.popups;

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
import view.mainUI.MainUI;

public class CourtMapLoadPopup extends Popup{

	public static void createLoadPopup(int x, int y) {
		JFrame GUI = setupGUI("Load map",x,y);
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
				
		GUI.add(nameField);
		GUI.add(loadButton);
		GUI.pack();
		GUI.setVisible(true);
	}
	
}
