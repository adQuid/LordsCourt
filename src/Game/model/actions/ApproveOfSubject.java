package Game.model.actions;

import Game.model.Action;
import court.model.Court;
import court.model.CourtCharacter;

public class ApproveOfSubject extends Action{

	public ApproveOfSubject(CourtCharacter character) {
		super(character);
	}

	@Override
	public void doAction(Court game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String description() {
		return "";
	}
}
