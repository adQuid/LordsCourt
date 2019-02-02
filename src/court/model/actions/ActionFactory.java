package court.model.actions;

import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;

import court.model.Action;
import court.model.Court;

public class ActionFactory {
	
	public static Action fromSaveState(Court court, Map<String,Object> jsonObject) {
		String type = (String) jsonObject.get("type");
		
		if(type.equals(Wait.saveCode())) {
			return new Wait(court, jsonObject);
		}
		if(type.equals(Greet.saveCode())) {
			return new Greet(court, jsonObject);
		}
		if(type.equals(ChangeSubject.saveCode())) {
			return new ChangeSubject(court, jsonObject);
		}
		if(type.equals(ApproveOfSubject.saveCode())) {
			return new ApproveOfSubject(court, jsonObject);
		}
		if(type.equals(DisapproveOfSubject.saveCode())) {
			return new DisapproveOfSubject(court, jsonObject);
		}
		if(type.equals(LeaveConversation.saveCode())) {
			return new LeaveConversation(court, jsonObject);
		}
		
		return null;
	}
	
}
