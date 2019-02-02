package court.ai;

import court.model.Court;
import court.model.CourtCharacter;

public interface CaseFactory {

	public Case caseForAction(Court game, CourtCharacter self, double scale);
	
}
