package court.conversationlogic;

import java.util.Comparator;

import court.model.Conversation;
import court.model.CourtCharacter;

public class CharacterComparator implements Comparator<CourtCharacter>{

	private Conversation convo;
	
	public CharacterComparator(Conversation convo) {
		this.convo = convo;
	}
	
	@Override
	public int compare(CourtCharacter character1, CourtCharacter character2) {
		return 0;
	}	
}
