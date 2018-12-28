package court.model;

import java.util.ArrayList;
import java.util.List;

import Game.model.Action;

public class Conversation {

	//order matters here
	private List<CourtCharacter> people = new ArrayList<CourtCharacter>();
	private Subject subject = null;
	private Action lastAction;
	
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
		String[] parts = saveState.split(",");
		
		for(String current: parts) {
			people.add(court.getCharacterById(Integer.parseInt(current)));
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
	
	public List<Action> getReactionsForCharacter(CourtCharacter character){
		List<Action> retval = new ArrayList<Action>();
		
				
		
		return retval;
	}
	
	public String toSaveState() {
		String retval = "";
		
		for(CourtCharacter current: people) {
			retval+=current.ID+",";
		}
		
		return retval.substring(0, retval.length()-1);//trim last comma
	}
}
