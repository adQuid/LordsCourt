package view.model;

import java.util.ArrayList;
import java.util.List;

import court.model.actions.Move;

public class Path {

	public List<Coordinate> steps = new ArrayList<Coordinate>();
		
	public Path() {
		
	}
	
	public Path(Coordinate start) {
		steps.add(start);
	}
	
	public Path(List<Coordinate> steps) {
		this.steps = steps;
	}
	
	public Path(Path other) {
		this(new ArrayList<Coordinate>(other.steps));
	}
	
	public Path addStep(Coordinate toAdd) {
		List<Coordinate> newSteps = new ArrayList<Coordinate>(steps);
		newSteps.add(toAdd);
		
		return new Path(newSteps);
	}
	
	public Coordinate finalStep() {
		return steps.get(steps.size()-1);
	}
	
	//should be in a library
	public static int direction(Coordinate step1, Coordinate step2) {
		if(step2.equals(step1.left())) {
			return Move.LEFT;
		}
		if(step2.equals(step1.right())) {
			return Move.RIGHT;
		}
		if(step2.equals(step1.up())) {
			return Move.UP;
		}
		if(step2.equals(step1.down())) {
			return Move.DOWN;
		}
		return -1;
	}
}
