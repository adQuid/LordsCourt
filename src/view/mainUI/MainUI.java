package view.mainUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import Game.model.Action;
import Game.model.Game;
import Game.model.actions.Move;
import court.model.CourtCharacter;
import court.model.Court;
import court.model.TileClass;
import layout.TableLayout;
import view.popups.TargetSelectPopup;
import view.VerticalList;

public class MainUI {

	static JFrame GUI = new JFrame();
	static JPanel descriptionPanel = new JPanel();
	static JLabel descriptionLabel = new JLabel("This is a window"); 
	
	static JPanel controlPanel;
	
	static JPanel reactionPanel = new JPanel();
	static JPanel reactionListPanel = new JPanel();
	static VerticalList reactionList = new VerticalList(reactionListPanel, getReactions(),4);
	
	static JPanel oldActionPanel = new JPanel();
	static VerticalList oldActionList = new VerticalList(oldActionPanel,lastActionLabels(),8);
	
	static int visionDistance=12;
	static MouseListener clickAction;
	
	static Court court;
	
	//testing code
	static Game testGame = null;
	public static CourtCharacter playingAs = null;
	
	static TileClass selectedClass = null;
	
	//will be replaced
	public static void startGame() {
		testGame = new Game("dagame");
		court = testGame.getActiveCourts().get(0);
		playingAs = court.getCharacters().get(0);
		paintGameControls();
		setupWindow();
	}
	
	public static void startEditior(Court providedCourt) {		
		if(providedCourt == null) {
			court = new Court(1);
		} else {
			court = providedCourt; 
		}
		MapEditorUISetup.paintMapEditorControls();
		setupWindow();
	}
	
	public static void setupWindow() {
		setupPanel();
		GUI.pack();
		GUI.setSize(1200, 800);
		GUI.setVisible(true);
	}
	
	public static void setupPanel() {
		MainUIMapDisplay.paintDisplay();
		
		GUI = new JFrame();
		
		//resize on dragging the window
		GUI.addComponentListener(new ComponentAdapter( ) {
		  public void componentResized(ComponentEvent ev) {
			  MainUIMapDisplay.resizeDisplay();
		  }
		});
				
		MainUIMapDisplay.imageDisplay.addMouseListener(clickAction);
		
		descriptionPanel.add(descriptionLabel);
		
		GUI.add(MainUIMapDisplay.displayPanel, BorderLayout.NORTH);
		GUI.add(descriptionPanel, BorderLayout.CENTER);
		GUI.add(controlPanel, BorderLayout.SOUTH);
		
		GUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public static void recheck() {
		GUI.validate();
	}
	
	public static void saveActiveMap(String name) {
		court.saveMap(name);
	}
	
	public static void changeDescription(String text) {
		descriptionLabel.setText(text);
	}
	
	static int timesPaintControlsCalled=0;
	
	public static void paintGameControls() {
		if(timesPaintControlsCalled++ > 0) {
			updateActionList();
			return;
		}	
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		    	  	if(e.getID() != KeyEvent.KEY_PRESSED) {
		    	  		return false;
		    	  	}
		    	  	if(e.getKeyCode() == KeyEvent.VK_UP) {
		    	  		MainUIMapDisplay.focusY--;
					}
					if(e.getKeyCode() == KeyEvent.VK_DOWN) {
						MainUIMapDisplay.focusY++;
					}
					if(e.getKeyCode() == KeyEvent.VK_LEFT) {
						MainUIMapDisplay.focusX--;
					}
					if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
						MainUIMapDisplay.focusX++;
					}
					if(e.getKeyChar() == 'd') {
						addActionForPlayer(new Move(Move.RIGHT));
					}
					if(e.getKeyChar() == 'a') {
						addActionForPlayer(new Move(Move.LEFT));
					}
					if(e.getKeyChar() == 's') {
						addActionForPlayer(new Move(Move.DOWN));
					}
					if(e.getKeyChar() == 'w') {
						addActionForPlayer(new Move(Move.UP));
					}
		        return false;
		      }
		});
		
		clickAction = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				TargetSelectPopup.create(court, arg0.getX(), arg0.getY());
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}			
		};

		controlPanel = new JPanel();	
		controlPanel.setLayout(new BorderLayout());
		
		updateActionList();
		
		JPanel gameControls = new JPanel();
		double[][] size = {{0.25,0.75},{TableLayout.FILL}};
		gameControls.setLayout(new TableLayout(size));
		setupReactionPanel();
		gameControls.add(reactionPanel,"0,0");
		gameControls.add(oldActionPanel,"1,0");
		
		controlPanel.add(gameControls, BorderLayout.NORTH);
		controlPanel.add(generateMetaControls(), BorderLayout.SOUTH);
	}
	
	public static void updateActionList() {
		oldActionList.updatePanel(lastActionLabels());
		oldActionList.scrollToBottom();
	}
	
	public static List<Component> lastActionLabels() {
		List<Component> retval = new ArrayList<Component>();
	
		if(court != null) {
			retval.add(new JLabel("Previous Actions:"));
			for(String current: court.getActionMessages()) {
				retval.add(new JLabel(current));
			}
		} else {
			retval.add(new JLabel("test"));
			retval.add(new JLabel("test"));
			retval.add(new JLabel("test"));
			retval.add(new JLabel("test"));
		}
		
		return retval;
	}
			
	public static void setupReactionPanel() {
		updateReactions();
		
		double[][] size = {{TableLayout.FILL},{0.2,0.8}};
		
		reactionPanel.setLayout(new TableLayout(size));
		reactionPanel.add(new JLabel("The guy did a thing!"),"0,0");
		reactionPanel.add(reactionListPanel,"0,1");
	}
	
	public static void updateReactions() {
		reactionList.updatePanel(getReactions());
	}
	
	public static List<Component> getReactions() {
		List<Component> retval =  new ArrayList<Component>();
				
		retval.add(new JButton("Test reaction"));
		retval.add(new JButton("Test reaction"));
		retval.add(new JButton("Test reaction"));
		
		return retval;
	}
	
	public static JPanel generateMetaControls() {
		JPanel metaControls = new JPanel();//for like save/load options. This needs a better name
		metaControls.setLayout(new GridLayout(1,4));
		metaControls.add(new JButton("Options"));
		JButton saveButton = new JButton("Save Game");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				testGame.saveState("dagame");
			}			
		});
		metaControls.add(saveButton);
		metaControls.add(new JButton("Placeholder"));
		metaControls.add(new JButton("Quit"));
		
		return metaControls;
	}
	
	public static void addActionForPlayer(Action action) {
		court.appendActionForPlayer(action,playingAs);
	}
	
}