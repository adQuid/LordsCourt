package Game.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import court.model.CourtCharacter;
import court.model.Subject;
import court.ai.BrainThread;
import court.model.Court;
import court.model.Tile;

public class Game {

	private List<Court> activeCourts = new ArrayList<Court>();
	private Setting setting;
	
	public Game() {
		setting = WorldSetupHelpers.generateSetting("this don't matter yet");
		activeCourts.add(new Court(1,"test"));
		activeCourts.get(0).getCharacters().add(new CourtCharacter(1,activeCourts.get(0).getID(),5,5,"sir Hastings","Reg.png",1));
		startAI();
	}	
	
	public Game(String fileName) {
		
		File saveFile = new File("saves/"+fileName+".savgam");
		String saveState;
		try {
			saveState = (new Scanner(saveFile)).nextLine();//this SHOULD all be one line

			setting = WorldSetupHelpers.generateSetting("this don't matter yet");
			
			saveState = saveState.substring("[[START COURT]]".length(), saveState.length());
			saveState = saveState.substring(0, saveState.length()-"[[END COURT]]".length());

			String[] courts = saveState.split(Pattern.quote("[[END COURT]][[START COURT]]"));

			for(String current: courts) {
				activeCourts.add(new Court(current));
			}
			startAI();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void startAI() {
		Thread brainThread = new Thread(new BrainThread(this));
		brainThread.start();
	}
	
	public Setting getSetting() {
		return setting;
	}
	
	public List<Court> getActiveCourts(){
		return activeCourts;
	}
		
	public void saveState(String name) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("saves/"+name+".savgam")));
			for(Court curCourt: activeCourts) {
				writer.write(curCourt.saveState());		
			}

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}