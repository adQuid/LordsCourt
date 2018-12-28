package view.popups;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Game.model.Action;
import court.model.Court;
import court.model.CourtCharacter;
import view.mainUI.MainUI;

public class ActionSelectPopup extends Popup{

	public static void create(Court court, CourtCharacter target, int x, int y) {
		
		JFrame GUI = setupGUI("Select Action",x,y);
		
		List<Action> actions = target.getActionsOnThis(court, MainUI.playingAs);
		
		if(actions.size() > 0) {
		GUI.setLayout(new GridLayout(actions.size(),1));
		
		for(Action current: actions) {
			JButton doActionButton = new JButton("Greet");
			
			doActionButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					GreetSubjectSelectPopup.create(target, x, y);
				}				
			});			
			GUI.add(doActionButton);
		}
		} else {
			GUI.setLayout(new GridLayout(1,1));
			GUI.add(new JLabel("No actions for target"));
		}
		
		GUI.pack();
		GUI.setVisible(true);
	}
	
}
