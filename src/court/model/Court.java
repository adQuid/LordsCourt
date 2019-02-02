package court.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.JPanel;

import Game.model.Setting;
import court.model.actions.ApproveOfSubject;
import court.model.actions.ChangeSubject;
import court.model.actions.DisapproveOfSubject;
import court.model.actions.Greet;
import court.model.actions.LeaveConversation;
import court.model.actions.Move;
import court.model.actions.Wait;
import view.mainUI.MainUI;
import view.mainUI.MainUIMapDisplay;
import view.model.Coordinate;
import view.model.Path;

public class Court {

	private int ID;
	private Setting setting;
	private String mapName = null;
	private List<Tile> tiles;//this would be faster as a normal array, but a little tougher to code
	private List<CourtCharacter> characters = new ArrayList<CourtCharacter>();
	private List<Conversation> conversations = new ArrayList<Conversation>();
	private List<String> lastActions = new ArrayList<String>();
	private int round = 0;
	
	//creates a new empty map for map editing
	public Court(int ID) {
		this.ID = ID;
		tiles = new ArrayList<Tile>();
		characters = new ArrayList<CourtCharacter>();
	}
	
	public Court(int ID, Setting setting, String fileName) {
		loadMap(ID,setting,fileName);
	}
	
	public Court(String saveState, Setting setting) {
		
		String[] parts = saveState.split(Pattern.quote("[[STRTARR]]"));
		
		String[] courtStats = parts[0].split(",");
		loadMap(Integer.parseInt(courtStats[0]),setting,courtStats[1]);
		
		String[] arrays = parts[1].split(Pattern.quote("[[END CHARACTER]][[START CONVO]]"));

		if(arrays.length > 1) {
			arrays[0] = arrays[0].substring("[[START CHARACTER]]".length(), arrays[0].length());
		} else {
			arrays[0] = arrays[0].substring("[[START CHARACTER]]".length(), arrays[0].length()-"[[END CHARACTER]]".length());
		}
		String[] characters = arrays[0].split(Pattern.quote("[[END CHARACTER]][[START CHARACTER]]"));
		for(String current: characters) {
			this.characters.add(new CourtCharacter(current, setting));
		}
		
		if(arrays.length > 1) {
			arrays[1] = arrays[1].substring(0, arrays[1].length()-"[[END CONVO]]".length());
			String[] conversations = arrays[1].split(Pattern.quote("[[END CONVO]][[START CONVO]]"));
			for(String current: conversations) {
				this.conversations.add(new Conversation(this,current));
			}
		}
		
		
	}
	
	private void loadMap(int ID, Setting setting, String fileName) {
		this.ID = ID;
		this.setting = setting;
		tiles = new ArrayList<Tile>();
		characters = new ArrayList<CourtCharacter>();
		
		this.mapName = fileName;
		File mapFile = new File("maps/"+fileName+".cort");
		
		Scanner reader;
		try {
			reader = new Scanner(mapFile);

			while(reader.hasNextLine()) {
				String nextLine = reader.nextLine();
				String[] splitLine = nextLine.split(",");
				tiles.add(new Tile(Integer.parseInt(splitLine[0]),Integer.parseInt(splitLine[1]),
						TileClass.getClassById(Integer.parseInt(splitLine[2]))));		
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getID() {
		return ID;
	}
	
	public int getRound() {
		return round;
	}
	
	public String getMapName() {
		return mapName;
	}
	
	public List<CourtCharacter> getCharacters(){
		return characters;
	}
	
	public List<CourtCharacter> getCharactersAt(int x, int y){
		List<CourtCharacter> retval = new ArrayList<CourtCharacter>();
		
		for(CourtCharacter current: characters) {
			if(current.getX() == x && current.getY() == y) {
				retval.add(current);
			}
		}
		
		return retval;
	}
	
	public CourtCharacter getCharacterById(int ID) {
		for(CourtCharacter current: characters) {
			if(current.ID == ID) {
				return current;
			}
		}
		
		return null;
	}
	
	public String describeTile(int x, int y) {
		List<CourtCharacter> characters = getCharactersAt(x,y);
		
		String retval = "";
		for(CourtCharacter current: characters) {
			retval += current.getShortDisplayName()+",";
		}
		if(retval.length() > 0) {
			return retval.substring(0, retval.length()-1);
		} else {
			return "";
		}
	}
	
	public void appendActionsForPlayer(List<Action> actions, CourtCharacter player) {
		//I SHOULD have some kind of check to make sure this character is actually in this court
		player.addActionsThisTurn(actions);
		if(allPlayersHaveAction()) {
			endRound();
		}
	}
	
	public void appendActionForPlayer(Action action, CourtCharacter player) {
		List<Action> list = new ArrayList<Action>();
		list.add(action);
		appendActionsForPlayer(list,player);
	}
	
	public boolean allPlayersHaveAction() {
		for(CourtCharacter current: characters) {
			if(current.getActionsThisTurn().size() == 0) {
				return false;
			}
		}
		return true;
	}
	
	public Setting getSetting() {
		return setting;
	}
	
	public List<Tile> getTiles(){
		return tiles;
	}
	
	public Tile tileAt(int x, int y) {
		for(Tile curTile: tiles) {
			if(curTile.getX() == x && curTile.getY() == y) {
				return curTile;
			}
		}
		return null;
	}
	
	public void addTile(Tile toAdd) {
		tiles.add(toAdd);
	}
	
	public void removeTileAt(int x, int y) {
		tiles.remove(tileAt(x,y));
	}
	
	public Conversation convoForCharacter(CourtCharacter character) {
		for(Conversation current: conversations) {
			if(current.getPeople().contains(character)) {
				return current;
			}
		}
		return null;
	}
	
	public void addConversation(Conversation toAdd) {
		
		for(CourtCharacter character: toAdd.getPeople()) {
			if(convoForCharacter(character) != null) {
				convoForCharacter(character).removePerson(character);
			}
		}
		
		conversations.add(toAdd);
	}
	
	public List<String> getActionMessages(){
		return lastActions;
	}
	
	public void addActionMessage(String message) {
		lastActions.add(message);
	}
	
	public boolean isTalkingTo(CourtCharacter char1, CourtCharacter char2) {
		for(Conversation current: conversations) {
			if(current.getPeople().contains(char1) &&
					current.getPeople().contains(char2)) {
				return true;
			}
		}
		return false;
	}	
	
	public Path pathingBetween(Coordinate coord1, Coordinate coord2) {
		if(coord1.equals(coord2)) {
			return new Path(coord1);
		}
		
		final int MAX_SIZE = 25;
		
		List<List<Path>> paths = new ArrayList<List<Path>>();
		Map<Coordinate,Integer> alreadyReached = new HashMap<Coordinate,Integer>();
		
		for(int i=0; i<MAX_SIZE; i++) {
			paths.add(new ArrayList<Path>());
		}
		paths.get(0).add(new Path(coord1));
		alreadyReached.put(coord1,1);
		
		while(pathsLeft(paths)) {//find a better place to define max pathing size
			//find lowest rung
			//yes this is super gross
			int index = 0;
			while(paths.get(index).isEmpty()) {
				index++;
			}
			
			Path lowPath = paths.get(index).get(0);
			paths.get(index).remove(lowPath);

			List<Path> newPaths = new ArrayList<Path>();
			newPaths.add(lowPath.addStep(lowPath.finalStep().right()));
			newPaths.add(lowPath.addStep(lowPath.finalStep().left()));
			newPaths.add(lowPath.addStep(lowPath.finalStep().up()));
			newPaths.add(lowPath.addStep(lowPath.finalStep().down()));
			
			if(lowPath.steps.size() > 1) {
				//this is SUPER TIGHTLY COUPLED with the move action
				Collections.swap(newPaths, Path.direction(lowPath.finalStep(), lowPath.steps.get(lowPath.steps.size()-2))-1, 3);
			}
			
			for(Path curPath: newPaths) {
				if(curPath.finalStep().equals(coord2)) {
					return curPath;
				} else if(index+1 < paths.size()){
					addPathConditionally(paths.get(index+1),curPath,coord2,alreadyReached);
				}			
			}
		}
		return null;
	}
	
	private boolean pathsLeft(List<List<Path>> paths) {
		for(List<Path> list: paths) {
			if(list.size() > 0) {
				return true;
			}
		}
		return false;
	}
	
	private void addPathConditionally(List<Path> list, Path pathToAdd, Coordinate dest, Map<Coordinate,Integer> alreadyReached) {
		if((alreadyReached.get(pathToAdd.finalStep()) == null 
				|| alreadyReached.get(pathToAdd.finalStep()) < 2)
				&& isPassible(pathToAdd.finalStep())) {
			list.add(pathToAdd);
			if(alreadyReached.get(pathToAdd.finalStep()) == null) {
				alreadyReached.put(pathToAdd.finalStep(),1);
			}else {
				alreadyReached.put(pathToAdd.finalStep(),alreadyReached.get(pathToAdd.finalStep())+1);
			}
		}
	}

	public boolean isPassible(Coordinate coord) {
		return tileAt(coord.x,coord.y) != null && !tileAt(coord.x,coord.y).isWall(); 
	}
	
	//might want to move this into the actions somehow, or merge with actionsOnThis from character...
	public List<Action> getReactions(CourtCharacter character) {
		List<Action> retval = new ArrayList<Action>();
		
		Conversation convo = convoForCharacter(character);
		
		if(convo != null) {
			if(convo.getLastAction().getInstigator() == character) {
				retval.add(new Wait(character));
			}
			if(convo.getSubject() != null) {
				retval.add(new ApproveOfSubject(character,convo.getSubject()));
				retval.add(new DisapproveOfSubject(character,convo.getSubject()));
				for(Subject subject: setting.getRelatedSubjects(convo.getSubject())) {
					if(subject != convo.getSubject()) {
						retval.add(new ChangeSubject(character,subject));
					}
				}
			} else {
				for(Subject subject: setting.getConversationSubjects().values()) {
					retval.add(new ChangeSubject(character,subject));
				}
			}
			retval.add(new LeaveConversation(character));
		}
		
		return retval;
	}
	
	public void endRound() {
		for(Conversation current: conversations) {
			for(CourtCharacter character: current.getPeople()) {
				for(Action curAction: character.getActionsThisTurn()) {
					if(curAction.isConversationAction()) {
						curAction.doAction(this);
					}
				}
			}
		}
		List<Conversation> newConvos = new ArrayList<Conversation>();
		for(Conversation current: conversations) {
			current.endRound();
			if(current.getPeople().size() >= 2) {
				newConvos.add(current);
			}
		}
		conversations = newConvos;
		
		for(CourtCharacter current: characters) {
			for(Action curAction: current.getActionsThisTurn()) {
				if(!curAction.isConversationAction() ||
						curAction instanceof Greet) {
					curAction.doAction(this);
				}
			}
			current.setActionsThisTurn(new ArrayList<Action>());
			current.endTurn();
		}
		lastActions.add("ROUND "+round++);

		//This needs to move, I'm just not sure where
		MainUI.defaultDescription();
		MainUI.updateActionList();
		MainUIMapDisplay.repaintDisplay();
		MainUI.paintGameControls();
		MainUI.updateReactions();
		MainUI.updateSelfStats(MainUI.playingAs.getAttention(), MainUI.playingAs.getConfidence(), MainUI.playingAs.getEnergy());
	}
	
	public String saveState() {
		String retval = "[[START COURT]]";
		retval += getID()+","+getMapName()+"[[STRTARR]]";
		for(CourtCharacter curChar: characters) {
			retval+="[[START CHARACTER]]";
			retval+=curChar.toSaveState();
			retval+="[[END CHARACTER]]";
		}
		for(Conversation curCon: conversations) {
			retval+="[[START CONVO]]";
			retval+=curCon.toSaveState();
			retval+="[[END CONVO]]";
		}
		retval+="[[END COURT]]";
		return retval;
	}
	
	public void saveMap(String name) {
		this.mapName = name;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("maps/"+name+".cort")));
			for(Tile curTile: tiles) {
				writer.write(curTile.getX()+","+curTile.getY()+","+curTile.getType().getID()+"\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
