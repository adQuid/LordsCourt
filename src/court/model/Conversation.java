package court.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.gson.Gson;

import court.model.actions.ActionFactory;

public class Conversation {

	//order matters here
	private List<CourtCharacter> people = new ArrayList<CourtCharacter>();
	private Subject subject = null;
	private Action lastAction;
	private int lastActionAge = 999; 
	
	public Conversation(Action lastAction, CourtCharacter...characters) {
		this.lastAction = lastAction;
		for(CourtCharacter current: characters) {
			people.add(current);
		}
	}

	public Conversation(Subject startingSubject, Action lastAction, CourtCharacter...characters) {
		this(lastAction, characters);
		this.subject = startingSubject;
	}
	
	public Conversation(Court court, String saveState) {
		Gson gson = new Gson();
		
		Map<String,Object> jsonObject = gson.fromJson(saveState, Map.class);

		lastAction = ActionFactory.fromSaveState(court, gson.fromJson(jsonObject.get("action").toString(),Map.class));
		lastActionAge = ((Double)jsonObject.get("actionAge")).intValue();
		
		if(jsonObject.get("subject") != null) {
			subject = court.getSetting().getSubjectByName(jsonObject.get("subject").toString());
		}
					
		for(Double current: (List<Double>)jsonObject.get("people")) {
			people.add(court.getCharacterById(current.intValue()));
		}
	}
	
	public List<CourtCharacter> getPeople() {
		return people;
	}

	public void setPeople(List<CourtCharacter> people) {
		this.people = people;
	}
	
	public Subject getSubject() {
		return subject;
	}
	
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	public Action getLastAction() {
		return lastAction;
	}
	
	public void setLastAction(Action action) {
		this.lastActionAge = 0;
		this.lastAction = action;
	}

	public int getLastActionAge() {
		return lastActionAge;
	}
	
	public boolean wasActionTakenThisTurn() {
		return lastActionAge == 0;
	}

	public void endRound() {
		lastActionAge++;
	}
	
	public String toSaveState() {
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String,Object>();

		map.put("action", lastAction.toSaveState());
		map.put("actionAge", lastActionAge);
		if(subject != null) {
			map.put("subject", subject.getName());
		}
		int[] peopleArr = new int[people.size()];
		for(int i = 0; i < people.size(); i++) {
			peopleArr[i] = people.get(i).getID();
		}
		map.put("people", peopleArr);
		
		return gson.toJson(map);
	}
}
