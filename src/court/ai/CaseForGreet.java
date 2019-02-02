package court.ai;

import java.util.ArrayList;
import java.util.List;

import court.model.Court;
import court.model.CourtCharacter;
import court.model.actions.Greet;
import court.model.actions.Move;
import court.model.actions.Wait;

public class CaseForGreet implements CaseFactory{

	@Override
	public Case caseForAction(Court game, CourtCharacter self, double scale) {
		List<Case> options = new ArrayList<Case>();
		
		if(game.convoForCharacter(self) != null) {
			return new Case(new Wait(self),-999);
		}
		
		for(CourtCharacter current: game.getCharacters()) {
			if(!current.equals(self)) {
				options.add(caseGivenTarget(game,self,current,scale));
			}
		}
		
		if(options.size() == 0) {
			return new Case(new Wait(self), -999.0);
		}
		Case bestCase = options.get(0);
		for(Case current: options) {
			if(current.getStrength() > bestCase.getStrength()) {
				bestCase = current;
			}
		}
		return bestCase;
	}

	private Case caseGivenTarget(Court game, CourtCharacter self, CourtCharacter target, double scale) {
		if(self.distanceTo(target) > 6) {
			return new Case(new Move(self,Move.LEFT), scale * (65.0 - (5*self.distanceTo(target))));
		}
		return new Case(new Greet(self,target), scale * (65.0 - (5*self.distanceTo(target))));
	}
	
}
