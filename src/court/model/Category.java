package court.model;

import java.util.ArrayList;
import java.util.List;

public class Category {

	String name;
	List<Subject> subjects = new ArrayList<Subject>();
	
	public Category(String name) {
		this.name=name;
	}
	
	public List<Subject> getSubjects(){
		return subjects;
	}
	
	public void addSubject(Subject toAdd) {
		subjects.add(toAdd);
	}
	
}
