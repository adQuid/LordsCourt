package court.model;

import java.util.ArrayList;
import java.util.List;

public class TileClass {

	public static List<TileClass> allClasses = new ArrayList<TileClass>();
	
	public static TileClass roughWood = new TileClass(1,"assets/floor.png");
	
	static {
		allClasses.add(new TileClass(1,"assets/floor.png"));
	}
	
	private int ID;
	private String imageName;
	
	public TileClass(int ID, String imageName) {
		this.ID = ID;
		this.imageName = imageName;
	}
	
	public String getImageName() {
		return imageName;
	}
	
	public int getID() {
		return ID;
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
