package court.ai;

import Game.model.Game;
import court.model.Court;
import court.model.CourtCharacter;

public class BrainThread implements Runnable{

	Game game;
	
	public BrainThread(Game game) {
		this.game = game;
	}
	
	@Override
	public void run() {
		while(true) {
			for(Court curCourt: game.getActiveCourts()) {
				if(!curCourt.allPlayersHaveAction()) {
					for(CourtCharacter curCharacter: curCourt.getCharacters()) {
						if(curCharacter.getController() == 0 && curCharacter.getActionsThisTurn().size() == 0){
							curCharacter.setActionsThisTurn(BasicAI.doAI(curCharacter, curCourt));
						}
					}
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
