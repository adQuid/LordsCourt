package Game.model;

import java.util.List;

import court.model.Category;
import court.model.Subject;

public class Setting {

	List<Subject> conversationSubjects;
	List<Category> subjectCategories;
	
	public Setting(List<Subject> conversationSubjects, List<Category> subjectCategories) {
		super();
		this.conversationSubjects = conversationSubjects;
		this.subjectCategories = subjectCategories;
	}
	public List<Subject> getConversationSubjects() {
		return conversationSubjects;
	}
	public void setConversationSubjects(List<Subject> conversationSubjects) {
		this.conversationSubjects = conversationSubjects;
	}
	public List<Category> getSubjectCategories() {
		return subjectCategories;
	}
	public void setSubjectCategories(List<Category> subjectCategories) {
		this.subjectCategories = subjectCategories;
	}
	
	
	
}
