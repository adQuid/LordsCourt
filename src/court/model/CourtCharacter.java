package court.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import Game.model.Setting;
import court.model.actions.ApproveOfSubject;
import court.model.actions.ChangeSubject;
import court.model.actions.DisapproveOfSubject;
import court.model.actions.Greet;
import court.model.actions.LeaveConversation;
import court.model.actions.Wait;
import view.GameEntity;
import view.mainUI.MainUI;
import view.model.Coordinate;

public class CourtCharacter {

	int ID;
	int location;
	private Coordinate coordinate;
	private String characterName;
	private String imageName;
	private int controller;
	
	private int attention = 0;
	private int confidence = 0;
	private int energy = 0;
	
	private Culture culture;
	
	private List<Action> actionsThisTurn = new ArrayList<Action>();
	
	public CourtCharacter(int ID, int location, Coordinate coord, String characterName, String imageName, int controller, Culture culture) {
		this.ID = ID;
		this.location = location;
		this.coordinate = coord;
		this.characterName = characterName;
		this.imageName = imageName;
		this.controller = controller;
		this.culture = culture;
	}
	
	public CourtCharacter(String saveState, Setting setting) {
		Gson gson = new Gson();
		
		Map<String,Object> map = gson.fromJson(saveState, Map.class);
		
		this.ID = ((Double)map.get("ID")).intValue();
		this.location = ((Double)map.get("location")).intValue();
		this.coordinate = new Coordinate(((Double)map.get("x")).intValue(),((Double)map.get("y")).intValue());
		this.characterName =map.get("name").toString();
		this.imageName = map.get("img").toString();
		this.controller = ((Double)map.get("control")).intValue();
		
		this.culture = setting.getCultures().get(map.get("culture").toString());
		
		this.attention = ((Double)map.get("attn")).intValue();
		this.confidence = ((Double)map.get("conf")).intValue();
		this.energy = ((Double)map.get("eng")).intValue();
	}
		
	public int getController() {
		return controller;
	}
	
	public Coordinate getCoord() {
		return coordinate;
	}
	
	public int getX() {
		return coordinate.x;
	}

	public void setX(int x) {
		this.coordinate.x = x;
	}

	public int getY() {
		return coordinate.y;
	}

	public void setY(int y) {
		this.coordinate.y = y;
	}

	public String getCharacterName() {
		return characterName;
	}
	
	public String getShortDisplayName() {
		if(MainUI.playingAs == this) {
			return "you";
		}
		return characterName;
	}
	
	public int getID() {
		return ID;
	}
	
	/**
	 * 
	 * @param subject
	 * @return -1 for dislike, 1 for like, 0 for neutral
	 */
	public int likeModifier(Subject subject) {
		if(culture.likesSubject(subject)) {
			return 1;
		}
		if(culture.dislikesSubject(subject)) {
			return -1;
		}
		return 0;
	}
		
	public int getAttention() {
		return attention;
	}

	public void setAttention(int attention) {
		this.attention = attention;
	}

	public void addAttention(int change) {
		if(change < 0 || energy > 0) {
			this.attention += change;
		} else {
			this.attention += change/2;
		}
	}
	
	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}
	
	public void addConfidence(int change) {
		if(change < 0 || energy > 0) {
			this.confidence += change;
		}
	}
	
	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}
	
	public void addEnergy(int change) {
		if(energy + change > 0) {
			this.energy += change;
		} else {
			this.energy = 0;
		}
	}

	public List<Action> getActionsThisTurn() {
		return actionsThisTurn;
	}

	public void setActionsThisTurn(List<Action> actionsThisTurn) {
		this.actionsThisTurn = actionsThisTurn;
	}
	
	void addActionsThisTurn(Action toAdd) {
		if(actionsThisTurn.size() == 0) {//for the time being, only one action per turn		
			actionsThisTurn.add(toAdd);
		}
	}
	
	public void addActionsThisTurn(List<Action> toAdd) {
		if(actionsThisTurn.size() == 0) {//for the time being, only one action per turn		
			actionsThisTurn.add(toAdd.get(0));//fails silently
		}
	}

	public void endTurn() {
		attention = Math.max(0,attention-1);
	}
	
	public List<Action> getActionsOnThis(Court court, CourtCharacter actor){
		List<Action> retval = new ArrayList<Action>();
		
		if(actor == this) {
			retval.add(new Wait(actor));
		} else if(court.isTalkingTo(actor, this)) {
			retval.add(new LeaveConversation(actor));
			retval.addAll(universalConversationActions(court,actor));
			for(Subject current: court.getSetting().getConversationSubjects().values()) {
				if(court.convoForCharacter(actor).getSubject() != null) {
					retval.add(new ChangeSubject(actor,current));
				}
			}
		} else {
			if(actor.distanceTo(this) <= Conversation.MAX_CONVO_RANGE) {
				retval.add(new Greet(actor, this));
			}
		}
		
		return retval;
	}
	
	//should this be here?
	public List<Action> universalConversationActions(Court court, CourtCharacter actor){
		List<Action> retval = new ArrayList<Action>();
		for(Subject current: court.getSetting().getConversationSubjects().values()) {
			retval.add(new ApproveOfSubject(actor,current));
			retval.add(new DisapproveOfSubject(actor,current));
		}		
		return retval;
	}
	
	
	public List<Subject> getGreetingSubjectsForThis(CourtCharacter greeter){
		List<Subject> retval = new ArrayList<Subject>();
		
		retval.addAll(MainUI.game.getSetting().getConversationSubjects().values());
		
		return retval;
	}
	
	public int distanceTo(CourtCharacter other) {
		return coordinate.distanceTo(other.coordinate);
	}
	
	public GameEntity toEntity() {
		return new GameEntity(coordinate.x,coordinate.y,"assets/"+imageName);
	}
	
	public String toSaveState() {
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("ID", ID);
		map.put("location", location);
		map.put("x", coordinate.x);
		map.put("y", coordinate.y);
		map.put("name", characterName);
		map.put("img", imageName);
		map.put("control", controller);
		
		map.put("culture", culture);
		
		map.put("attn", attention);
		map.put("conf", confidence);
		map.put("eng", energy);
		
		return gson.toJson(map);
	}
}
