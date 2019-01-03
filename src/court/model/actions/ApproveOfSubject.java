package court.model.actions;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import court.model.Action;
import court.model.Conversation;
import court.model.Court;
import court.model.CourtCharacter;
import court.model.Subject;

public class ApproveOfSubject extends Action{

	Subject subject;
	
	public ApproveOfSubject(CourtCharacter character, Subject subject) {
		super(character);
		this.subject = subject;
	}
	
	public ApproveOfSubject(Court court, Map<String,Object> map) {
		super(court.getCharacterById(((Double)map.get("instigator")).intValue()));
		subject = court.getSetting().getSubjectByName(map.get("subject").toString());
	}

	@Override
	public void doAction(Court game) {
		Conversation convo = game.convoForCharacter(instigator);
		
		if(convo.wasActionTakenThisTurn()) {
			System.out.println(instigator.getCharacterName()+" was cut off");
			return;//somebody cut you off
		}
		
		convo.setSubject(subject);
		convo.setLastAction(this);
		game.addActionMessage(description());
	}

	public static String saveCode() {
		return "approve";
	}
	@Override
	public String shortDescription() {
		return "Approve of topic";
	}
	
	@Override
	public String description() {
		return instigator.getCharacterName()+" expressed approval of "+subject.getName();
	}

	@Override
	public boolean isConversationAction() {
		return true;
	}

	@Override
	public String toSaveState() {
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("type", saveCode());
		map.put("instigator", instigator.getID());
		map.put("subject", subject.getName());
		
		return gson.toJson(map);
	}
}
