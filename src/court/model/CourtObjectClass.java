package court.model;

import java.util.ArrayList;
import java.util.List;

public class CourtObjectClass {

	public static List<CourtObjectClass> allClasses = new ArrayList<CourtObjectClass>();
	
	public static CourtObjectClass playerStart = new CourtObjectClass(1,"assets/redlines.png",false);
	
	static {
		allClasses.add(playerStart);
	}
	
	private int ID;
	private String imageName;
	private boolean wall;
	
	public CourtObjectClass(int ID, String imageName,boolean wall) {
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
	
	public static CourtObjectClass getClassById(int id) {
		for(CourtObjectClass curClass: allClasses) {
			if(curClass.ID == id) {
				return curClass;
			}
		}
		return null;
	}
}
