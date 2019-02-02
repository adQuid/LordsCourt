package court.ai;

import court.model.Action;

public class Case {

	protected Action action;
	protected double strength;
	
	public Case(Action action, double strength) {
		super();
		this.action = action;
		this.strength = strength;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public double getStrength() {
		return strength;
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}
}
