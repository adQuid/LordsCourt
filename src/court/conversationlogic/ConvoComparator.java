package court.conversationlogic;

import java.util.Comparator;

import court.model.Conversation;
import court.model.CourtCharacter;

public class ConvoComparator implements Comparator<CourtCharacter>{

	private Conversation convo;
	
	public ConvoComparator(Conversation convo) {
		this.convo = convo;
	}
	
	@Override
	public int compare(CourtCharacter character1, CourtCharacter character2) {
		return character2.getConfidence() + character2.getAttention() 
		- character1.getConfidence() - character1.getAttention();
	}	
}
