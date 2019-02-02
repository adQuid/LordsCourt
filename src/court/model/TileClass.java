package court.model;

import java.util.ArrayList;
import java.util.List;

public class TileClass {

	public static List<TileClass> allClasses = new ArrayList<TileClass>();
	
	public static TileClass roughWood = new TileClass(1,"assets/floor.png",false);
	public static TileClass crapWall = new TileClass(2,"assets/wall.png",true);
	
	static {
		allClasses.add(roughWood);
		allClasses.add(crapWall);
	}
	
	private int ID;
	private String imageName;
	private boolean wall;
	
	public TileClass(int ID, String imageName,boolean wall) {
		this.ID = ID;
		this.imageName = imageName;
		this.wall = wall;
	}
	
	public String getImageName() {
		return imageName;
	}
	
	public int getID() {
		return ID;
	}
	
	public boolean isWall() {
		return wall;
	}
	
	public static TileClass getClassById(int id) {
		for(TileClass curClass: allClasses) {
			if(curClass.ID == id) {
				return curClass;
			}
		}
		return null;
	}
}
