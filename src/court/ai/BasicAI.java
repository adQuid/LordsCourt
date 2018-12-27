package court.ai;

import java.util.ArrayList;
import java.util.List;

import Game.model.Action;
import Game.model.actions.Greet;
import Game.model.actions.Move;
import Game.model.actions.Wait;
import court.model.Court;
import court.model.CourtCharacter;

public class BasicAI {

	public static List<Action> doAI(CourtCharacter character, Court court){
		List<Action> retval = new ArrayList<Action>();
		
		if(character.getX() - court.getCharacters().get(0).getX() < 5) {
			retval.add(new Greet(character,court.getCharacters().get(0)));
		} else {
			retval.add(new Wait());
		}
		return retval;
	}
	
}
