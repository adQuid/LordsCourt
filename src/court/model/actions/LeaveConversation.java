package court.model.actions;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import court.model.Action;
import court.model.Court;
import court.model.CourtCharacter;

public class LeaveConversation extends Action{

	public LeaveConversation(CourtCharacter character) {
		super(character);
	}
	
	public LeaveConversation(Court court, Map<String,Object> map) {
		super(court.getCharacterById(((Double)map.get("instigator")).intValue()));
	}
	
	@Override
	public void doAction(Court game) {
		game.convoForCharacter(instigator).removePerson(instigator);
		game.addActionMessage(description());
	}

	public static String saveCode() {
		return "leave";
	}
	@Override
	public String shortDescription() {
		return "Leave Conversation";
	}
	
	@Override
	public String description() {
		return instigator.getShortDisplayName()+" left the conversation";
	}
	@Override
	public String tooltip() {
		return "Excuse yourself from this conversation politely.";
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
		map.put("instigator", instigator);
		
		return gson.toJson(map);
	}
}
