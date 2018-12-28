package Game.model.actions;

import Game.model.Action;
import court.model.Conversation;
import court.model.Court;
import court.model.CourtCharacter;
import court.model.Subject;

public class Greet extends Action{
	
	private CourtCharacter target;
	private Subject subject = null;
	
	public Greet(CourtCharacter initiator, CourtCharacter target) {
		super(initiator);
		this.target = target;
	}

	public Greet(CourtCharacter initiator, CourtCharacter target, Subject subject) {
		this(initiator,target);
		this.subject = subject;
	}
	
	public void doAction(Court court) {
		
		if(!court.isTalkingTo(instigator, target)) {
			Conversation newConvo = new Conversation(this,instigator,target);
			if(subject != null) {
				newConvo.setSubject(subject);
			}
			court.addConversation(newConvo);
			court.addActionMessage(instigator.getShortDisplayName()+" greeted "+target.getShortDisplayName());
		} else {
			System.err.println(instigator.getCharacterName()+" tried to greet "+target.getCharacterName()+" even though they were already talking!");
		}
	}
	
	public String description() {
		String retval = instigator.getShortDisplayName()+" greeted "+target.getShortDisplayName();
		if(subject != null) {
			retval += " and brought up the topic of "+subject.getName();
		}
		return retval;
	}
}
