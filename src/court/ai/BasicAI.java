package court.ai;

import java.util.ArrayList;
import java.util.List;

import Game.model.Action;
import Game.model.actions.Greet;
import Game.model.actions.Move;
import court.model.Court;
import court.model.CourtCharacter;

public class BasicAI {

	public static List<Action> doAI(CourtCharacter character, Court court){
		List<Action> retval = new ArrayList<Action>();
		if(Math.random() * 2.0 > 1.0) {
			retval.add(new Move(Move.LEFT));
		}else {
			retval.add(new Greet(character));
		}
		
		return retval;
	}
	
}
