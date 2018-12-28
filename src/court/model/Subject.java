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
	
}
