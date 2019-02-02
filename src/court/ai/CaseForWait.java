package court.ai;

import court.model.Conversation;
import court.model.Court;
import court.model.CourtCharacter;
import court.model.actions.Wait;

public class CaseForWait implements CaseFactory{

	@Override
	public Case caseForAction(Court game, CourtCharacter self, double scale) {
		Conversation convo = game.convoForCharacter(self);
		if(convo != null && convo.getLastAction().getInstigator().equals(self)) {
			return new Case(new Wait(self), scale * (30 - (10*convo.getLastActionAge())));
		}
		return new Case(new Wait(self), scale * 10.0);
	}

}
