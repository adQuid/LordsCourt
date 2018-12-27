package Game.model;

import java.util.ArrayList;
import java.util.List;

import court.model.Category;
import court.model.Subject;

public class WorldSetupHelpers {

	public Setting getSubjectsForSetting(String setting){
		List<Subject> subjects = new ArrayList<Subject>();
		List<Category> categories = new ArrayList<Category>();
		
		Subject oats = new Subject("oats");
		Subject barley = new Subject("barley");
		Subject oatmeal = new Subject("oatmeal");

		categories.add(groupSubjects("Grain",oats,barley));
		
		relateSubjects(oats,oatmeal);
		
		return new Setting(subjects, categories);
	}

	private Category groupSubjects(String name, Subject...subjects) {
		Category retval = new Category(name);
		for(Subject current: subjects) {
			retval.addSubject(current);
		}
		return retval;
	}
	
	private void relateSubjects(Subject...subjects) {
		for(Subject i: subjects) {
			for(Subject j: subjects) {
				if(i != j) {
					i.addRelatedSubject(j);
					j.addRelatedSubject(i);
				}
			}
		}
	}
}
