package court.model.actions;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import court.model.Action;
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
	
	public Greet(Court court, Map<String,Object> jsonObject) {
		super(court.getCharacterById(((Double)jsonObject.get("instigator")).intValue()));
		target = court.getCharacterById(((Double)jsonObject.get("target")).intValue());
		if(jsonObject.get("subject") != null) {
			subject = court.getSetting().getSubjectByName(jsonObject.get("subject").toString());
		}
	}
	
	public void doAction(Court court) {
		if(!court.isTalkingTo(instigator, target)) {
			Conversation newConvo = new Conversation(this,instigator,target);
			if(newConvo.wasActionTakenThisTurn()) {
				return;//somebody cut you off
			}
			if(subject != null) {
				newConvo.setSubject(subject);
			}
			instigator.addAttention(5);
			instigator.addEnergy(-2);
			
			court.addConversation(newConvo);
			court.addActionMessage(instigator.getShortDisplayName()+" greeted "+target.getShortDisplayName());
		} else {
			System.err.println(instigator.getCharacterName()+" tried to greet "+target.getCharacterName()+" even though they were already talking!");
		}
	}

	public static String saveCode() {
		return "greet";
	}
	@Override
	public String shortDescription() {
		return "Greet "+target.getCharacterName();
	}
	@Override
	public String tooltip() {
		return "Starts a conversation with the character, or includes character in existing conversation.";
	}
	public String description() {
		String retval = instigator.getShortDisplayName()+" greeted "+target.getShortDisplayName();
		if(subject != null) {
			retval += " and brought up the topic of "+subject.getName();
		}
		return retval;
	}

	public String toSaveState() {
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("type", saveCode());
		map.put("instigator", instigator.getID());
		map.put("target", target.getID());
		if(subject != null) {
			map.put("subject", subject.getName());
		}
		
		return gson.toJson(map);
	}
	
	@Override
	public boolean isConversationAction() {
		return true;
	}
}
