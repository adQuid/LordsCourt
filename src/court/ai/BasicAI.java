package court.ai;

import java.util.ArrayList;
import java.util.List;

import court.model.Action;
import court.model.Conversation;
import court.model.Court;
import court.model.CourtCharacter;
import court.model.actions.ApproveOfSubject;
import court.model.actions.ChangeSubject;
import court.model.actions.DisapproveOfSubject;
import court.model.actions.Greet;
import court.model.actions.Move;
import court.model.actions.Wait;

public class BasicAI {

	public static List<Action> doAI(CourtCharacter character, Court court){
		List<Action> retval = new ArrayList<Action>();
		List<Case> options = new ArrayList<Case>();
		
		options.add(new CaseForWait().caseForAction(court, character,1.00));
		options.add(new CaseForGreet().caseForAction(court, character,1.00));
		options.add(new CaseForApproveCurrent().caseForAction(court, character,1.00));
		options.add(new CaseForChangeToFavoriteTopic().caseForAction(court, character,1.00));
		
		Case bestCase = options.get(0);
		for(Case current: options) {
			if(current.getStrength() > bestCase.getStrength()) {
				bestCase = current;
			}
		}
		
		retval.add(bestCase.getAction());
		return retval;
	}
	
}
