package view.popups;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import court.model.CourtCharacter;
import court.model.Subject;
import court.model.actions.ApproveOfSubject;
import court.model.actions.ChangeSubject;
import court.model.actions.DisapproveOfSubject;
import court.model.actions.Greet;
import view.mainUI.MainUI;

public class SubjectSelectPopup extends Popup{

	public static final int GREET = 0;
	public static final int CHANGE = 1;	
	public static final int APPROVE = 2;
	public static final int DISAPPROVE = 3;
	
	public static void create(CourtCharacter target, int x, int y, int type) {
		JFrame GUI = setupGUI("Greeting",x,y);
		
		List<Subject> subjects = target.getGreetingSubjectsForThis(MainUI.playingAs);
		
		if(type == GREET) {
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
		} else {
			GUI.setLayout(new GridLayout(subjects.size(),1));
		}		
		
		for(Subject current: subjects) {
			JButton toAdd = new JButton(current.getName());
			toAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					switch(type) {
					case GREET:
						MainUI.addActionForPlayer(new Greet(MainUI.playingAs,target,current));
						break;
					case CHANGE:
						MainUI.addActionForPlayer(new ChangeSubject(MainUI.playingAs,current));
						break;
					case APPROVE:
						MainUI.addActionForPlayer(new ApproveOfSubject(MainUI.playingAs,current));
						break;
					case DISAPPROVE:
						MainUI.addActionForPlayer(new DisapproveOfSubject(MainUI.playingAs,current));
						break;
					}
					GUI.setVisible(false);
				}				
			});
			GUI.add(toAdd);
		}
		
		GUI.pack();
		GUI.setVisible(true);
	}
	
}
