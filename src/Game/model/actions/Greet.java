package Game.model.actions;

import Game.model.Action;
import Game.model.Game;
import court.model.Court;
import court.model.CourtCharacter;
import view.model.Coordinate;

public class Greet implements Action{
	
	private CourtCharacter target;
	
	public Greet(CourtCharacter target) {
		this.target = target;
	}

	@Override
	public void doAction(CourtCharacter character, Court court) {
		
		court.addActionMessage(character.getCharacterName()+" hails you");
	}
}
