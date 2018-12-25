package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import view.mainUI.MainUI;

public class MainMenu {

	public static void main(String[] args) {
		JFrame GUI = new JFrame("Lord's Court");
		
		JButton playButton = new JButton("Play Game");
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUI.setVisible(false);
				MainUI.startGame();
			}			
		});
		
		JButton editorButton = new JButton("Court Editor");
		editorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUI.setVisible(false);
				CourtMapLoadPopup.createLoadPopup();
			}			
		});
		
		GUI.setLayout(new GridLayout(2,1));
		GUI.add(playButton);
		GUI.add(editorButton);
		
		GUI.pack();
		GUI.setVisible(true);
	}
	
}
