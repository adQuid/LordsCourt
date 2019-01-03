package court.model.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import Game.model.Game;
import court.model.Action;
import court.model.Court;
import court.model.CourtCharacter;
import view.model.Coordinate;

public class Move extends Action{

	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;
	
	private int direction;
	
	public Move(CourtCharacter instigator, int direction) {
		super(instigator);
		this.direction = direction;
	}

	@Override
	public void doAction(Court court) {
		Coordinate futurePosition = new Coordinate(instigator.getX(),instigator.getY());

		if(direction == LEFT) {
			futurePosition = new Coordinate(instigator.getX()-1,instigator.getY());
		}
		if(direction == RIGHT) {
			futurePosition = new Coordinate(instigator.getX()+1,instigator.getY());
		}
		if(direction == DOWN) {
			futurePosition = new Coordinate(instigator.getX(),instigator.getY()+1);
		}
		if(direction == UP) {
			futurePosition = new Coordinate(instigator.getX(),instigator.getY()-1);
		}

		List<CourtCharacter> charactersHere = court.getCharactersAt(futurePosition.x, futurePosition.y);
		if(charactersHere.size() == 0) {
			if(court.tileAt(futurePosition.x, futurePosition.y) != null) {
				instigator.setX(futurePosition.x);
				instigator.setY(futurePosition.y);
			}
		}
	}

	@Override
	public String description() {
		return "walked";
	}

	@Override
	public boolean isConversationAction() {
		return false;
	}

	public static String saveCode() {
		return "move";
	}
	@Override
	public String shortDescription() {
		return "Move";
	}
	@Override
	public String tooltip() {
		return "Does stuff";
	}
	@Override
	public String toSaveState() {
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("type", saveCode());
		map.put("instigator", instigator);
		map.put("direction", direction);
		
		return gson.toJson(map);
	}
}
