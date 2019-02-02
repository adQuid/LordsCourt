package view.model;

import court.model.CourtCharacter;

public class Coordinate {

	public int x;
	public int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Coordinate left() {
		return new Coordinate(x-1,y);
	}
	public Coordinate right() {
		return new Coordinate(x+1,y);
	}
	public Coordinate up() {
		return new Coordinate(x,y-1);
	}
	public Coordinate down() {
		return new Coordinate(x,y+1);
	}
	
	public int distanceTo(Coordinate other) {
		return new Double(Math.ceil(Math.sqrt(Math.pow(x-other.x, 2) + Math.pow(y-other.y, 2)))).intValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
