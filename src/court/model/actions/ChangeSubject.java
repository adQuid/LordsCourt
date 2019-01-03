package court.model.actions;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import court.model.Action;
import court.model.Conversation;
import court.model.Court;
import court.model.CourtCharacter;
import court.model.Subject;

public class ChangeSubject extends Action{

	Subject newSubject;
	
	public ChangeSubject(CourtCharacter character, Subject newSubject) {
		super(character);
		this.newSubject = newSubject;
	}

	public ChangeSubject(Court court, Map<String,Object> map) {
		super(court.getCharacterById(((Double)map.get("instigator")).intValue()));
		newSubject = court.getSetting().getSubjectByName(map.get("subject").toString());
	}
	
	@Override
	public void doAction(Court game) {
		Conversation convo = game.convoForCharacter(instigator);
		
		if(convo.wasActionTakenThisTurn()) {
			return;//somebody cut you off
		}
		
		convo.setSubject(newSubject);
		convo.setLastAction(this);
		game.addActionMessage(description());
	}

	@Override
	public boolean isConversationAction() {
		return true;
	}

	public static String saveCode() {
		return "change";
	}
	@Override
	public String shortDescription() {
		return "Change topic to "+newSubject.getName();
	}
	@Override
	public String tooltip() {
		return "Does stuff";
	}
	@Override
	public String description() {
		return instigator.getCharacterName()+" brought up the topic of "+newSubject.getName();
	}

	@Override
	public String toSaveState() {
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("type", saveCode());
		map.put("instigator", instigator.getID());
		map.put("subject", newSubject.getName());
		
		return gson.toJson(map);
	}

}
