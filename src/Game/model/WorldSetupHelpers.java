package Game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import court.model.Category;
import court.model.Culture;
import court.model.Subject;

public class WorldSetupHelpers {

	public static Setting generateSetting(String setting){
		Map<String,Subject> subjects = new HashMap<String,Subject>();
		List<Category> categories = new ArrayList<Category>();
		Map<String,Culture> cultures = new HashMap<String,Culture>();
		
		Subject oats = new Subject("oats");
		Subject barley = new Subject("barley");
		Subject oatmeal = new Subject("oatmeal");

		subjects.put(oats.getName(),oats);
		subjects.put(barley.getName(),barley);
		subjects.put(oatmeal.getName(),oatmeal);
		
		categories.add(groupSubjects("Grain",oats,barley));
		
		Culture basic = new Culture("basic");
		basic.addLike(oatmeal);
		basic.addDislike(barley);
		cultures.put("basic", basic);
		
		relateSubjects(oats,oatmeal);
		
		return new Setting(subjects, categories,cultures);
	}

	private static Category groupSubjects(String name, Subject...subjects) {
		Category retval = new Category(name);
		for(Subject current: subjects) {
			retval.addSubject(current);
		}
		return retval;
	}
	
	private static void relateSubjects(Subject...subjects) {
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
