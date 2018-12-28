package Game.model;

import court.model.Court;
import court.model.CourtCharacter;

public abstract class Action {
	
	protected CourtCharacter instigator;
	
	public Action(CourtCharacter character) {
		this.instigator = character;
	}
	
	public abstract void doAction(Court game);

	public abstract String description();
	
}
