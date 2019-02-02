package court.ai;

import court.model.Conversation;
import court.model.Court;
import court.model.CourtCharacter;
import court.model.actions.ChangeSubject;
import court.model.actions.Wait;

public class CaseForChangeToFavoriteTopic implements CaseFactory{

	@Override
	public Case caseForAction(Court game, CourtCharacter self, double scale) {
		Conversation convo = game.convoForCharacter(self);
		
		if(convo == null) {
			return new Case(new Wait(self),-999);
		}		
		if(convo.getSubject() == null || self.likeModifier(convo.getSubject()) < 1) {
			return new Case(new ChangeSubject(self,game.getSetting().getSubjectByName("oatmeal")),scale * 50.0);
		}
		return new Case(new Wait(self),-999);
	}
}
