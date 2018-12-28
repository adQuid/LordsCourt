package view.popups;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import court.model.Court;
import court.model.CourtCharacter;
import view.mainUI.MainUIMapDisplay;
import view.model.Coordinate;

public class TargetSelectPopup extends Popup{

	public static void create(Court court, int x, int y) {
		Coordinate coord = MainUIMapDisplay.pixelToMapCoord(x,y);
		JFrame GUI = setupGUI("Select Target",x,y);
		
		List<CourtCharacter> charactersHere = court.getCharactersAt(coord.x, coord.y);
		GUI.setLayout(new GridLayout(charactersHere.size(),1));
		
		if(charactersHere.size() > 0) {

			for(CourtCharacter current: charactersHere) {
				JButton selectButton = new JButton(current.getShortDisplayName());
				
				selectButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ActionSelectPopup.create(court, current,x,y);
					}					
				});
				
				GUI.add(selectButton);
			}

			GUI.pack();
			GUI.setVisible(true);
		}
	}
	
}
