package view.popups;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import court.model.Action;
import court.model.Court;
import court.model.CourtCharacter;
import court.model.actions.ApproveOfSubject;
import court.model.actions.ChangeSubject;
import court.model.actions.DisapproveOfSubject;
import court.model.actions.Greet;
import court.model.actions.Wait;
import view.mainUI.MainUI;

public class ActionSelectPopup extends Popup{

	public static void create(Court court, CourtCharacter target, int x, int y) {

		JFrame GUI = setupGUI("Select Action",x,y);

		List<Action> actions = target.getActionsOnThis(court, MainUI.playingAs);

		if(actions.size() > 0) {
			int count = 0;
			boolean greetActions = false;
			boolean changeSubjectActions = false;
			boolean approveActions = false;
			boolean disapproveActions = false;
			for(Action current: actions) {
				if(current instanceof Greet && greetActions==false) {
					greetActions = true;
					count++;
				}
				if(current instanceof ChangeSubject && changeSubjectActions==false) {
					changeSubjectActions = true;
					count++;
				}
				if(current instanceof ApproveOfSubject && approveActions==false) {
					approveActions = true;
					count++;
				}
				if(current instanceof DisapproveOfSubject && disapproveActions==false) {
					disapproveActions = true;
					count++;
				}
				if(current instanceof Wait){
					JButton doActionButton = new JButton(current.shortDescription());
					
					doActionButton.addMouseListener(new MouseListener() {
						@Override
						public void mouseClicked(MouseEvent arg0) {
							court.appendActionForPlayer(current, target);
							GUI.setVisible(false);
						}

						@Override
						public void mouseEntered(MouseEvent arg0) {
							MainUI.changeDescription(current.tooltip());
						}

						@Override
						public void mouseExited(MouseEvent arg0) {
							MainUI.defaultDescription();
						}
						@Override
						public void mousePressed(MouseEvent arg0) {
						}
						@Override
						public void mouseReleased(MouseEvent arg0) {
						}				
					});			
					GUI.add(doActionButton);
				}
			}
			GUI.setLayout(new GridLayout(count,1));
			
			if(greetActions) {
				JButton doActionButton = new JButton("Greet");
				
				doActionButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						SubjectSelectPopup.create(target, x, y, SubjectSelectPopup.GREET);
					}				
				});			
				GUI.add(doActionButton);
			}
			
			if(changeSubjectActions) {
				JButton doActionButton = new JButton("Change Subject");
				
				doActionButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						SubjectSelectPopup.create(target, x, y,SubjectSelectPopup.CHANGE);
					}				
				});			
				GUI.add(doActionButton);
			}
			
			if(approveActions) {
				JButton doActionButton = new JButton("Approve of...");
				
				doActionButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						SubjectSelectPopup.create(target, x, y,SubjectSelectPopup.APPROVE);
					}				
				});			
				GUI.add(doActionButton);
			}
			
			if(disapproveActions) {
				JButton doActionButton = new JButton("Dispprove of...");
				
				doActionButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						SubjectSelectPopup.create(target, x, y,SubjectSelectPopup.DISAPPROVE);
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
