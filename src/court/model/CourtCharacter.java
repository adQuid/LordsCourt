package court.model;

import java.util.ArrayList;
import java.util.List;

import Game.model.Action;
import view.GameEntity;

public class CourtCharacter {

	int ID;
	int location;
	private int x;
	private int y;
	private String characterName;
	private String imageName;
	private int controller;
	
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
		String[] parts = saveState.split(",");
		this.ID = Integer.parseInt(parts[0]);
		this.location = Integer.parseInt(parts[1]);
		this.x = Integer.parseInt(parts[2]);
		this.y = Integer.parseInt(parts[3]);
		this.characterName = parts[4];
		this.imageName = parts[5];
		this.controller = Integer.parseInt(parts[6]);
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
	
	public List<Action> getActionsThisTurn() {
		return actionsThisTurn;
	}

	public void setActionsThisTurn(List<Action> actionsThisTurn) {
		this.actionsThisTurn = actionsThisTurn;
	}
	
	public void addActionsThisTurn(Action toAdd) {
		if(actionsThisTurn.size() == 0) {//for the time being, only one action per turn		
			actionsThisTurn.add(toAdd);
		}
	}
	
	public void addActionsThisTurn(List<Action> toAdd) {
		if(actionsThisTurn.size() == 0) {//for the time being, only one action per turn		
			actionsThisTurn.add(toAdd.get(0));//fails silently
		}
	}

	public GameEntity toEntity() {
		return new GameEntity(x,y,"assets/"+imageName);
	}
	
	public String toSaveState() {
		return ID+","+location+","+x+","+y+","+characterName+","+imageName+","+controller;
	}
}
