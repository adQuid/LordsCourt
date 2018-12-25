package court.model;

import java.util.ArrayList;
import java.util.List;

public class Conversation {

	//order matters here
	private List<CourtCharacter> people = new ArrayList<CourtCharacter>();
	
	public Conversation(CourtCharacter...characters) {
		for(CourtCharacter current: characters) {
			people.add(current);
		}
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
	
	public String toSaveState() {
		String retval = "";
		
		for(CourtCharacter current: people) {
			retval+=current.ID+",";
		}
		
		return retval.substring(0, retval.length()-1);//trim last comma
	}
}
