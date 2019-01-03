package court.model.actions;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import court.model.Action;
import court.model.Court;
import court.model.CourtCharacter;

public class Wait extends Action{

	public Wait(CourtCharacter character) {
		super(character);
	}

	@Override
	public void doAction(Court game) {
		//Do nothing
	}

	public static String saveCode() {
		return "wait";
	}
	@Override
	public String shortDescription() {
		return "Wait";
	}
	
	@Override
	public String description() {
		return instigator+" waited for somebody else to do something";
	}
	@Override
	public String tooltip() {
		return "Allows other characters to act. It can be more polite to not just ramble on all day.";
	}
	@Override
	public boolean isConversationAction() {
		return false;
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
