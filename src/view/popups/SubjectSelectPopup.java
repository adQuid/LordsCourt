package view.popups;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import court.model.CourtCharacter;
import court.model.Subject;
import court.model.actions.ApproveOfSubject;
import court.model.actions.ChangeSubject;
import court.model.actions.DisapproveOfSubject;
import court.model.actions.Greet;
import view.LinearList;
import view.mainUI.MainUI;

public class SubjectSelectPopup extends Popup{

	public static final int GREET = 0;
	public static final int CHANGE = 1;	
	public static final int APPROVE = 2;
	public static final int DISAPPROVE = 3;
	
	public static void create(CourtCharacter target, int x, int y, int type) {
		JFrame GUI = setupGUI("Greeting",x,y);
		
		Collection<Subject> subjects = MainUI.game.getSetting().getConversationSubjects().values();
		JPanel panel = new JPanel();
		List<Component> subjectButtons = new ArrayList<Component>();
		LinearList subjectVertList = new LinearList(panel,subjectButtons,3);
		
		GUI.add(panel);
		
		if(type == GREET) {
			
			JButton basic = new JButton("No Topic");
			basic.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					MainUI.addActionForPlayer(new Greet(MainUI.playingAs,target));
					GUI.setVisible(false);
				}			
			});
			subjectButtons.add(basic);
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
			subjectButtons.add(toAdd);
		}
		
		subjectVertList.updatePanel();
		GUI.pack();
		GUI.setVisible(true);
	}
	
}
