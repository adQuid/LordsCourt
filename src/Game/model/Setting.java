package Game.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import court.model.Category;
import court.model.Culture;
import court.model.Subject;

public class Setting {

	Map<String,Subject> conversationSubjects;
	List<Category> subjectCategories;
	Map<String,Culture> cultures;
		
	public Setting(Map<String, Subject> conversationSubjects, List<Category> subjectCategories,
			Map<String, Culture> cultures) {
		super();
		this.conversationSubjects = conversationSubjects;
		this.subjectCategories = subjectCategories;
		this.cultures = cultures;
	}

	public Map<String, Subject> getConversationSubjects() {
		return conversationSubjects;
	}

	public Subject getSubjectByName(String name) {
		return conversationSubjects.get(name);
	}
	
	public void setConversationSubjects(Map<String, Subject> conversationSubjects) {
		this.conversationSubjects = conversationSubjects;
	}

	public List<Category> getSubjectCategories() {
		return subjectCategories;
	}

	public void setSubjectCategories(List<Category> subjectCategories) {
		this.subjectCategories = subjectCategories;
	}

	public Map<String, Culture> getCultures() {
		return cultures;
	}

	public void setCultures(Map<String, Culture> cultures) {
		this.cultures = cultures;
	}

	public Set<Subject> getRelatedSubjects(Subject start){
		Set<Subject> retval = new HashSet<Subject>();
		
		retval.addAll(start.getRelatedSubjects());
		
		for(Category current: subjectCategories) {
			if(current.getSubjects().contains(start)) {
				retval.addAll(current.getSubjects());
			}
		}
		
		return retval;
	}
	
}
