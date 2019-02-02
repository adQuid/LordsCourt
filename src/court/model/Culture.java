package court.model;

import java.util.ArrayList;
import java.util.List;

public class Culture {

	private List<Subject> likes = new ArrayList<Subject>();
	private List<Subject> dislikes = new ArrayList<Subject>();
	
	public Culture() {
		
	}
	
	public void addLike(Subject subject) {
		likes.add(subject);
	}
	
	public void addDislike(Subject subject) {
		dislikes.add(subject);
	}
	
	public boolean likesSubject(Subject subject) {
		return likes.contains(subject);
	}
	
	public boolean dislikesSubject(Subject subject) {
		return dislikes.contains(subject);
	}
}
