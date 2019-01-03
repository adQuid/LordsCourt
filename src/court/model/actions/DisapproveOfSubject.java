package court.model.actions;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import court.model.Action;
import court.model.Conversation;
import court.model.Court;
import court.model.CourtCharacter;
import court.model.Subject;

public class DisapproveOfSubject extends Action{

	Subject subject;
	
	public DisapproveOfSubject(CourtCharacter character, Subject subject) {
		super(character);
		this.subject = subject;
	}

	public DisapproveOfSubject(Court court, Map<String,Object> map) {
		super(court.getCharacterById(((Double)map.get("instigator")).intValue()));
		subject = court.getSetting().getSubjectByName(map.get("subject").toString());
	}
	
	@Override
	public void doAction(Court game) {
		Conversation convo = game.convoForCharacter(instigator);
		
		//check if you were cut off this conversation
		if(convo.wasActionTakenThisTurn()) {
			System.out.println(instigator.getCharacterName()+" was cut off at round "+game.getRound());
			convo.addAwkwardness(2);
			instigator.addConfidence(-1);
			return;
		}
		
		//changing the subject by disapproval can be awkward
		if(convo.getSubject() == null || !convo.getSubject().equals(subject)) {
			convo.addAwkwardness(1);
		}
		
		instigator.addAttention(2);
		if(convo.getSubject().equals(subject)) {
			if(convo.getLastAction() instanceof DisapproveOfSubject) {
				convo.getLastAction().getInstigator().addConfidence(1);
			}
			if(convo.getLastAction() instanceof ApproveOfSubject) {
				instigator.addAttention(2);
			}
		}
		
		convo.setSubject(subject);
		convo.setLastAction(this);
		game.addActionMessage(description());
	}

	public static String saveCode() {
		return "disapprove";
	}
	@Override
	public String shortDescription() {
		return "Disapprove of topic";
	}
	@Override
	public String tooltip() {
		return "Gives one confidence to the last player to act if they also disapproved of this subject, grants you extra attention if they just approved";
	}
	@Override
	public String description() {
		return instigator.getCharacterName()+" expressed disapproval of "+subject.getName();
	}

	@Override
	public boolean isConversationAction() {
		return true;
	}

	@Override
	public String toSaveState() {
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("type", saveCode());
		map.put("instigator", instigator.getID());
		map.put("subject", subject.getName());
		
		return gson.toJson(map);
	}
}
