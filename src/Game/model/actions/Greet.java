package Game.model.actions;

import Game.model.Action;
import court.model.Conversation;
import court.model.Court;
import court.model.CourtCharacter;

public class Greet implements Action{
	
	private CourtCharacter initiator;
	private CourtCharacter target;
	
	public Greet(CourtCharacter initiator, CourtCharacter target) {
		this.initiator = initiator;
		this.target = target;
	}

	@Override
	public void doAction(CourtCharacter character, Court court) {
		
		if(!court.isTalkingTo(initiator, target)) {
			court.addConversation(new Conversation(initiator,target));
			court.addActionMessage(initiator.getShortDisplayName()+" hailed "+target.getShortDisplayName());
		} else {
			System.err.println(initiator.getCharacterName()+" tried to greet "+target.getCharacterName()+" even though they were already talking!");
		}
	}
}
