package court.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import Game.model.Action;
import view.MainUI;

public class Court {

	private int ID;
	private String mapName = null;
	private List<Tile> tiles;//this would be faster as a normal array, but a little tougher to code
	private List<CourtCharacter> characters = new ArrayList<CourtCharacter>();
	private List<String> lastActions = new ArrayList<String>();
	
	//creates a new empty map for map editing
	public Court(int ID) {
		this.ID = ID;
		tiles = new ArrayList<Tile>();
		characters = new ArrayList<CourtCharacter>();
	}
	
	public Court(int ID, String fileName) {
		loadMap(ID,fileName);
	}
	
	public Court(String saveState) {
		
		String[] parts = saveState.split(Pattern.quote("[[STRTARR]]"));
		
		String[] courtStats = parts[0].split(",");
		loadMap(Integer.parseInt(courtStats[0]),courtStats[1]);
		
		parts[1] = parts[1].substring("[[START CHARACTER]]".length(), parts[1].length()-"[[END CHARACTER]]".length());
		String[] characters = parts[1].split(Pattern.quote("[[END CHARACTER]][[START CHARACTER]]"));
		for(String current: characters) {
			this.characters.add(new CourtCharacter(current));
		}
	}
	
	private void loadMap(int ID, String fileName) {
		this.ID = ID;
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
	
	public String getMapName() {
		return mapName;
	}
	
	public List<CourtCharacter> getCharacters(){
		return characters;
	}
	
	public void appendActionsForPlayer(List<Action> actions, CourtCharacter player) {
		//I SHOULD have some kind of check to make sure this character is actually in this court
		player.addActionsThisTurn(actions);
		if(allPlayersHaveAction()) {
			endRound();
		}
	}
	
	public void appendActionForPlayer(Action action, CourtCharacter player) {
		player.addActionsThisTurn(action);
		if(allPlayersHaveAction()) {
			endRound();
		}
	}
	
	public boolean allPlayersHaveAction() {
		for(CourtCharacter current: characters) {
			if(current.getActionsThisTurn().size() == 0) {
				return false;
			}
		}
		return true;
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
	
	public List<String> getActionMessages(){
		return lastActions;
	}
	
	public void addActionMessage(String message) {
		lastActions.add(message);
	}
	
	public void endRound() {
		System.out.println("ending round");
		for(CourtCharacter current: characters) {
			for(Action curAction: current.getActionsThisTurn()) {
				curAction.doAction(current, this);
			}
			current.setActionsThisTurn(new ArrayList<Action>());
		}
		MainUI.repaintDisplay();
		MainUI.paintGameControls();
		MainUI.GUI.validate();
	}
	
	public String saveState() {
		String retval = "[[START COURT]]";
		retval += getID()+","+getMapName()+"[[STRTARR]]";
		for(CourtCharacter curChar: characters) {
			retval+="[[START CHARACTER]]";
			retval+=curChar.toSaveState();
			retval+="[[END CHARACTER]]";
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
