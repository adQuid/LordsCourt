package view;

public class GameEntity {

	private int gridX;
	private int gridY;
	
	private String imageName;

	public GameEntity(int gridX, int gridY, String imageName) {
		super();
		this.gridX = gridX;
		this.gridY = gridY;
		this.imageName = imageName;
	}

	public int getGridX() {
		return gridX;
	}

	public void setGridX(int gridX) {
		this.gridX = gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public void setGridY(int gridY) {
		this.gridY = gridY;
	}

	public String getImageName() {
		return imageName;
	}
	
	
}