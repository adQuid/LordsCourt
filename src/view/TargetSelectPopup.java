package view;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import court.model.Court;
import court.model.CourtCharacter;

public class TargetSelectPopup {

	public static void create(Court court, int x, int y) {
		JFrame GUI = new JFrame("Select Target");
		
		List<CourtCharacter> charactersHere = court.getCharactersAt(x, y);
		GUI.setLayout(new GridLayout(charactersHere.size(),1));
		
		for(CourtCharacter current: charactersHere) {
			JButton selectButton = new JButton(current.getShortDisplayName());
			GUI.add(selectButton);
		}
		
		ViewUtilities.setPopupBehavior(GUI);
		GUI.pack();
		GUI.setVisible(true);
	}
	
}
