package court.ai;

import java.util.ArrayList;
import java.util.List;

import court.model.Action;
import court.model.Conversation;
import court.model.Court;
import court.model.CourtCharacter;
import court.model.actions.ApproveOfSubject;
import court.model.actions.ChangeSubject;
import court.model.actions.Greet;
import court.model.actions.Move;
import court.model.actions.Wait;

public class BasicAI {

	public static List<Action> doAI(CourtCharacter character, Court court){
		List<Action> retval = new ArrayList<Action>();
		
		if(court.convoForCharacter(character) != null) {
			//I'm talking to someone
			Conversation convo = court.convoForCharacter(character);
			
			if(convo.getLastAction().getInstigator() == character 
					&& convo.getLastActionAge() < 2) {
				retval.add(new Wait(character));
				return retval;
			}
			if(convo.getSubject() != null) {
				retval.add(new ApproveOfSubject(character,convo.getSubject()));
			} else {
				retval.add(new ChangeSubject(character,court.getSetting().getSubjectByName("oats")));
			}
		}else {
			//I'm not talking to anyone
			if(character.getX() - court.getCharacters().get(0).getX() < 5) {
				retval.add(new Greet(character,court.getCharacters().get(0)));
			} else {
				retval.add(new Wait(character));
			}
		}
		return retval;
	}
	
}
