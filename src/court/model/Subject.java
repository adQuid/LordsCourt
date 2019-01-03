package court.model;

import java.util.ArrayList;
import java.util.List;

public class Subject {

	String name;
	List<Subject> relatedSubjects = new ArrayList<Subject>();
	
	public Subject(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Subject> getRelatedSubjects(){
		return relatedSubjects;
	}
	
	public void addRelatedSubject(Subject toAdd) {
		relatedSubjects.add(toAdd);
	}
	
	public String toSaveState() {
		return name;
	}
	
	public boolean equals(Object other) {
		if(other == null) {
			return false;
		}
		if(other instanceof Subject) {
			return name.equals(((Subject) other).name);
		}
		
		return false;
	}
	
}
