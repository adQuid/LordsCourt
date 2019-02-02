package court.model;

import view.GameEntity;

public class Tile {

	private int x;
	private int y;
	private TileClass type;
	public Tile(int x, int y, TileClass type) {
		super();
		this.x = x;
		this.y = y;
		this.type = type;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public TileClass getType() {
		return type;
	}
	public void setType(TileClass type) {
		this.type = type;
	}
	public GameEntity toEntity() {
		return new GameEntity(x,y,type.getImageName());
	}
	public boolean isWall() {
		return type.isWall();
	}
}
