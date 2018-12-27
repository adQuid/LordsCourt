package view.popups;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import view.mainUI.MainUI;

public class CourtMapSavePopup extends Popup implements Runnable{

	public static void createSavePopup() {
		JFrame GUI = new JFrame("Save map");
		GUI.setLayout(new GridLayout(3,1));
		GUI.add(new JLabel("Map Name:"));
		
		JTextField nameField = new JTextField();
		
		JButton saveButton = new JButton("Save");
		
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainUI.saveActiveMap(nameField.getText());
			}			
		});
		
		setPopupBehavior(GUI);
		
		GUI.add(nameField);
		GUI.add(saveButton);
		GUI.pack();
		GUI.setVisible(true);
	}

	@Override
	public void run() {
		createSavePopup();
	}
	
}
