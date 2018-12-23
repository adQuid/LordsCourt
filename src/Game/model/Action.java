package Game.model;

import court.model.Court;
import court.model.CourtCharacter;

public interface Action {

	public void doAction(CourtCharacter character, Court game);
	
}
