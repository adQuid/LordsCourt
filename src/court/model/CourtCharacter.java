package court.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import court.model.actions.Greet;
import court.model.actions.Wait;
import view.GameEntity;
import view.mainUI.MainUI;

public class CourtCharacter {

	int ID;
	int location;
	private int x;
	private int y;
	private String characterName;
	private String imageName;
	private int controller;
	
	private int attention = 0;
	private int confidence = 0;
	
	private List<Action> actionsThisTurn = new ArrayList<Action>();
	
	public CourtCharacter(int ID, int location, int x, int y, String characterName, String imageName, int controller) {
		this.ID = ID;
		this.location = location;
		this.x = x;
		this.y = y;
		this.characterName = characterName;
		this.imageName = imageName;
		this.controller = controller;
	}
	
	public CourtCharacter(String saveState) {
		Gson gson = new Gson();
		
		Map<String,Object> map = gson.fromJson(saveState, Map.class);
		
		this.ID = ((Double)map.get("ID")).intValue();
		this.location = ((Double)map.get("location")).intValue();
		this.x = ((Double)map.get("x")).intValue();
		this.y = ((Double)map.get("y")).intValue();
		this.characterName =map.get("name").toString();
		this.imageName = map.get("img").toString();
		this.controller = ((Double)map.get("control")).intValue();
		
		this.attention = ((Double)map.get("attn")).intValue();
		this.confidence = ((Double)map.get("conf")).intValue();
	}
		
	public int getController() {
		return controller;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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
		
	public int getAttention() {
		return attention;
	}

	public void setAttention(int attention) {
		this.attention = attention;
	}

	public void addAttention(int change) {
		this.attention += change;
	}
	
	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}
	
	public void addConfidence(int change) {
		this.confidence += change;
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
		} else {
			if(!court.isTalkingTo(actor, this)) {
				retval.add(new Greet(actor, this));
			}
		}
		
		return retval;
	}
	
	public List<Subject> getGreetingSubjectsForThis(CourtCharacter greeter){
		List<Subject> retval = new ArrayList<Subject>();
		
		retval.addAll(MainUI.game.getSetting().getConversationSubjects().values());
		
		return retval;
	}
	
	public int distanceTo(CourtCharacter other) {
		return new Double(Math.ceil(Math.sqrt(Math.pow(x-other.x, 2) + Math.pow(y-other.y, 2)))).intValue();
	}
	
	public GameEntity toEntity() {
		return new GameEntity(x,y,"assets/"+imageName);
	}
	
	public String toSaveState() {
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("ID", ID);
		map.put("location", location);
		map.put("x", x);
		map.put("y", y);
		map.put("name", characterName);
		map.put("img", imageName);
		map.put("control", controller);
		
		map.put("attn", attention);
		map.put("conf", confidence);
		
		return gson.toJson(map);
	}
}
