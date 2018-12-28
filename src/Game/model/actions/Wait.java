package Game.model.actions;

import Game.model.Action;
import court.model.Court;
import court.model.CourtCharacter;

public class Wait extends Action{

	public Wait(CourtCharacter character) {
		super(character);
	}

	@Override
	public void doAction(Court game) {
		//Do nothing
	}

	@Override
	public String description() {
		return instigator+" did nothing";
	}
}
