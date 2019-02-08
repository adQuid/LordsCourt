package view.popups;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Game.model.Setting;
import court.model.Court;
import court.model.CourtCharacter;
import court.model.Tile;
import court.model.TileClass;
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
				//null setting is very dangerous
				Court court;
				try {
					Scanner reader;
					reader = new Scanner(new File("maps/"+nameField.getText()+".cort"));

					court = new Court(reader.nextLine(),null);
					reader.close();
				} catch(FileNotFoundException e) {
					court = new Court(1);
				}		
				MainUI.startEditior(court);
			}			
		});
				
		GUI.add(nameField);
		GUI.add(loadButton);
		GUI.pack();
		GUI.setVisible(true);
	}
	
}
