package court.model;

import view.GameEntity;

public class CourtObject {
	private int x;
	private int y;
	private CourtObjectClass type;
		
	public CourtObject(int x, int y, CourtObjectClass type) {
		super();
		this.x = x;
		this.y = y;
		this.type = type;
	}
	public CourtObject(String string) {
		this(Integer.parseInt(string.split(",")[0]),
				Integer.parseInt(string.split(",")[1]),
				CourtObjectClass.getClassById(Integer.parseInt(string.split(",")[2])));
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
	public CourtObjectClass getType() {
		return type;
	}
	public void setType(CourtObjectClass type) {
		this.type = type;
	}
	public GameEntity toEntity() {
		return new GameEntity(x,y,type.getImageName());
	}
	public boolean isWall() {
		return type.isWall();
	}
}
