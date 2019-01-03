package court.model;

import java.util.Map;
import java.util.TreeMap;

import com.google.gson.Gson;

import court.model.actions.Greet;

public abstract class Action {
	
	protected CourtCharacter instigator;
	
	//only to be used by loading from save state
	public Action() {
		
	}
	
	public Action(CourtCharacter character) {
		this.instigator = character;
	}
	
	public CourtCharacter getInstigator() {
		return instigator;
	}
	
	public abstract void doAction(Court game);

	public abstract boolean isConversationAction();
	
	//used when shown as a menu option
	public abstract String shortDescription();
	//used in logs after the action was taken
	public abstract String description();
	
	public abstract String toSaveState();
	
}
