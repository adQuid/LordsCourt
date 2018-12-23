package Game.model.actions;

import Game.model.Action;
import Game.model.Game;
import court.model.Court;
import court.model.CourtCharacter;
import view.model.Coordinate;

public class Move implements Action{

	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;
	
	private int direction;
	
	public Move(int direction) {
		this.direction = direction;
	}

	@Override
	public void doAction(CourtCharacter character, Court court) {
		Coordinate futurePosition = new Coordinate(character.getX(),character.getY());
		if(direction == LEFT) {
			futurePosition = new Coordinate(character.getX()-1,character.getY());
		}
		if(direction == RIGHT) {
			futurePosition = new Coordinate(character.getX()+1,character.getY());
		}
		if(direction == DOWN) {
			futurePosition = new Coordinate(character.getX(),character.getY()+1);
		}
		if(direction == UP) {
			futurePosition = new Coordinate(character.getX(),character.getY()-1);
		}
		
		if(court.tileAt(futurePosition.x, futurePosition.y) != null) {
			character.setX(futurePosition.x);
			character.setY(futurePosition.y);
		}
	}
}
