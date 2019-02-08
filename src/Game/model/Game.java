package Game.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.google.gson.Gson;

import court.model.CourtCharacter;
import court.model.Subject;
import court.ai.BrainThread;
import court.model.Court;
import court.model.Tile;
import view.model.Coordinate;

public class Game {

	private List<Court> activeCourts = new ArrayList<Court>();
	private Setting setting;
	
	public Game() {
		setting = WorldSetupHelpers.generateSetting("this don't matter yet");
		// TODO fix all of this
		try {
			activeCourts.add(new Court(new Scanner(new File("maps/inn.cort")).nextLine(),setting));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		activeCourts.get(0).getCharacters().add(new CourtCharacter(1,activeCourts.get(0).getID(),new Coordinate(12,5),"sir Hastings","Reg.png",1,setting.getCultures().get("basic")));
		activeCourts.get(0).getCharacters().add(new CourtCharacter(2,activeCourts.get(0).getID(),new Coordinate(15,5),"Aaron","Nobleman.png",0,setting.getCultures().get("basic")));
		startAI();
	}	
	
	public Game(String fileName) {
		
		File saveFile = new File("saves/"+fileName+".savgam");
		String saveState;
		try {
			saveState = (new Scanner(saveFile)).nextLine();//this SHOULD all be one line

			Gson gson = new Gson();		
			Map<String,Object> map = gson.fromJson(saveState, Map.class);

			setting = WorldSetupHelpers.generateSetting("this don't matter yet");
			
			List<String> courts = (List<String>)map.get("courts");

			for(String current: courts) {
				activeCourts.add(new Court(current,setting));
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
		
	public String saveState() {
			Gson gson = new Gson();
			Map<String,Object> map = new HashMap<String,Object>();

			List<String> courtStrings = new ArrayList<String>();
			for(Court curCourt: activeCourts) {
				courtStrings.add(curCourt.saveState());		
			}
			
			map.put("courts", courtStrings);
			
			return gson.toJson(map);
	}
}