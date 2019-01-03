package view.popups;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import court.model.CourtCharacter;
import court.model.Subject;
import court.model.actions.Greet;
import view.mainUI.MainUI;

public class GreetSubjectSelectPopup extends Popup{

	public static void create(CourtCharacter target, int x, int y) {
		System.out.println("got here");
		JFrame GUI = setupGUI("Greeting",x,y);
		
		List<Subject> subjects = target.getGreetingSubjectsForThis(MainUI.playingAs);
		
		GUI.setLayout(new GridLayout(subjects.size()+1,1));
		
		JButton basic = new JButton("No Topic");
		basic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainUI.addActionForPlayer(new Greet(MainUI.playingAs,target));
				GUI.setVisible(false);
			}			
		});
		GUI.add(basic);
		
		for(Subject current: subjects) {
			JButton toAdd = new JButton(current.getName());
			toAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					MainUI.addActionForPlayer(new Greet(MainUI.playingAs,target,current));
					GUI.setVisible(false);
				}				
			});
			GUI.add(toAdd);
		}
		
		GUI.pack();
		GUI.setVisible(true);
	}
	
}
