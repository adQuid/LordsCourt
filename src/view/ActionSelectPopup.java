package view;

import javax.swing.JFrame;

import court.model.Court;
import court.model.CourtCharacter;

public class ActionSelectPopup {

	public static void create(Court court, CourtCharacter target) {
		JFrame GUI = new JFrame("Select Action");
		
		ViewUtilities.setPopupBehavior(GUI);
		
		
	}
	
}
