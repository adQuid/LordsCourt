package court.ai;

import court.model.Conversation;
import court.model.Court;
import court.model.CourtCharacter;
import court.model.actions.ApproveOfSubject;
import court.model.actions.Wait;

public class CaseForApproveCurrent implements CaseFactory{

	@Override
	public Case caseForAction(Court game, CourtCharacter self, double scale) {
		Conversation convo = game.convoForCharacter(self);
		if(convo != null && convo.getSubject() != null) {
			return new Case(new ApproveOfSubject(self,convo.getSubject()), scale * (10 + (10*self.likeModifier(convo.getSubject()))));
		}
		return new Case(new Wait(self),-999);
	}
}
