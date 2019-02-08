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
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import Game.model.Game;
import court.model.CourtCharacter;
import court.model.Action;
import court.model.Conversation;
import court.model.Court;
import court.model.TileClass;
import court.model.actions.Move;
import court.model.actions.Wait;
import layout.TableLayout;
import view.popups.TargetSelectPopup;
import view.LinearList;
import view.model.Coordinate;

public class MainUI {

	static JFrame GUI = new JFrame();
	static JPanel descriptionPanel = new JPanel();
	static JLabel descriptionLabel = new JLabel("This is a window"); 
	
	static JPanel selfDetailsPanel = new JPanel();
	static JLabel attentionLabel = new JLabel("Attention:");
	static JLabel confidenceLabel = new JLabel("Confidence:");
	static JLabel energyLabel = new JLabel("Energy:");
	
	static JPanel controlPanel = new JPanel();
	
	static JPanel reactionPanel = new JPanel();
	static JLabel reactionLabel = new JLabel();
	static JPanel reactionListPanel = new JPanel();
	static LinearList reactionList = new LinearList(reactionListPanel, new ArrayList<Component>(),4);
	
	static JPanel oldActionPanel = new JPanel();
	static LinearList oldActionList = new LinearList(oldActionPanel,lastActionLabels(),8);
	
	static int visionDistance=12;
	static MouseListener clickAction;
	static MouseMotionListener hoverAction;
	
	public static Court court;
	
	public static Game game = null;
	public static CourtCharacter playingAs = null;
	public static boolean editorMode = false;
	
	static TileClass selectedClass = null;
	
	//will be replaced
	public static void startGame() {
		game = new Game("dagame");
		court = game.getActiveCourts().get(0);
		playingAs = court.getCharacters().get(0);
		paintUniversalControls();
		paintGameControls();
		setupWindow();
	}
	
	public static void startEditior(Court providedCourt) {		
		if(providedCourt == null) {
			court = new Court(1);
		} else {
			court = providedCourt; 
		}
		paintUniversalControls();
		MapEditorUISetup.paintMapEditorControls();
		setupWindow();

	}
	
	public static void setupWindow() {
		setupPanel();
		GUI.pack();
		GUI.setSize(1300, 900);
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
		MainUIMapDisplay.imageDisplay.addMouseMotionListener(hoverAction);
		
		selfDetailsPanel.setLayout(new GridLayout(3,1));
		selfDetailsPanel.add(attentionLabel);
		selfDetailsPanel.add(confidenceLabel);
		selfDetailsPanel.add(energyLabel);
		
		double[][] size = {{0.25,0.75},{TableLayout.FILL}};
		descriptionPanel.setLayout(new TableLayout(size));
		descriptionPanel.add(selfDetailsPanel,"0,0");
		descriptionPanel.add(descriptionLabel,"1,0");
		
		GUI.add(MainUIMapDisplay.displayPanel, BorderLayout.NORTH);
		if(editorMode) {
			GUI.add(controlPanel, BorderLayout.CENTER);			
		} else {
			GUI.add(descriptionPanel, BorderLayout.CENTER);
			GUI.add(controlPanel, BorderLayout.SOUTH);			
		}
		
		GUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
		
	public static void saveActiveMap(String name) {
		court.saveMap(name);
	}
	
	public static void updateSelfStats(int attention, int confidence, int energy) {
		attentionLabel.setText("Attention: "+attention);
		confidenceLabel.setText("Confidence: "+confidence);
		energyLabel.setText("Energy: "+energy);
	}
	
	public static void changeDescription(String text) {
		descriptionLabel.setText(text);
	}
	
	public static void defaultDescription() {
		Conversation convo = court.convoForCharacter(playingAs);
		if(convo != null) {
			descriptionLabel.setText("Conversing with "+convo.getPeople().size()+" people (awk "+convo.getAwkwardness()+" age "+convo.getLastActionAge()+")");
			return;
		}
		descriptionLabel.setText("");		
	}
	
	public static void paintUniversalControls() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		    	  	if(e.getID() != KeyEvent.KEY_PRESSED) {
		    	  		return false;
		    	  	}
		    	  	if(e.getKeyCode() == KeyEvent.VK_UP) {
		    	  		MainUIMapDisplay.focus = MainUIMapDisplay.focus.up();
		    	  		MainUIMapDisplay.repaintDisplay();
					}
					if(e.getKeyCode() == KeyEvent.VK_DOWN) {
						MainUIMapDisplay.focus = MainUIMapDisplay.focus.down();
						MainUIMapDisplay.repaintDisplay();
					}
					if(e.getKeyCode() == KeyEvent.VK_LEFT) {
						MainUIMapDisplay.focus = MainUIMapDisplay.focus.left();
						MainUIMapDisplay.repaintDisplay();
					}
					if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
						MainUIMapDisplay.focus = MainUIMapDisplay.focus.right();
						MainUIMapDisplay.repaintDisplay();
					}
		        return false;
		      }
		});
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
					if(e.getKeyChar() == 'd') {
						addActionForPlayer(new Move(playingAs,Move.RIGHT));
					}
					if(e.getKeyChar() == 'a') {
						addActionForPlayer(new Move(playingAs,Move.LEFT));
					}
					if(e.getKeyChar() == 's') {
						addActionForPlayer(new Move(playingAs,Move.DOWN));
					}
					if(e.getKeyChar() == 'w') {
						addActionForPlayer(new Move(playingAs,Move.UP));
					}
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						addActionForPlayer(new Wait(playingAs));
					}
		        return false;
		      }
		});
		
		clickAction = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getButton() == MouseEvent.BUTTON3) {
					TargetSelectPopup.create(court, arg0.getX(), arg0.getY());
				}
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
		
		hoverAction = new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				Coordinate coord = MainUIMapDisplay.pixelToMapCoord(arg0.getX(),arg0.getY());
				if(!court.describeTile(coord.x, coord.y).equals("")) {
					changeDescription(court.describeTile(coord.x, coord.y));
				} else {
					defaultDescription();
				}
			}			
		};
		
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
		} 
		
		return retval;
	}
			
	public static void setupReactionPanel() {
		updateReactions();
		
		double[][] size = {{TableLayout.FILL},{0.2,0.8}};
		
		reactionPanel.setLayout(new TableLayout(size));
		
		reactionPanel.add(reactionLabel,"0,0");	
				
		reactionPanel.add(reactionListPanel,"0,1");
	}
	
	public static void updateReactions() {
		Conversation currentConvo = court.convoForCharacter(playingAs);
		
		if(currentConvo != null && currentConvo.getLastAction() != null) {
			reactionLabel.setText(currentConvo.getLastAction().description());
		} else {
			reactionLabel.setText("");
		}
		
		reactionList.updatePanel(getReactions());
	}
	
	public static List<Component> getReactions() {
		List<Component> retval =  new ArrayList<Component>();
		List<Action> reactions = court.getReactions(playingAs);		

		for(Action current: reactions) {
			JButton toAdd = new JButton(current.shortDescription());
			toAdd.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					defaultDescription();
					court.appendActionForPlayer(current,playingAs);					
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					changeDescription(current.tooltip());
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					defaultDescription();
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
				}				
			});
			retval.add(toAdd);
		}
				
		return retval;
	}
	
	public static JPanel generateMetaControls() {
		JPanel metaControls = new JPanel();//for like save/load options. This needs a better name
		metaControls.setLayout(new GridLayout(1,5));
		metaControls.add(new JButton("Options"));
		JButton saveButton = new JButton("Save Game");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(new File("saves/dagame.savgam")));
					writer.write(game.saveState());
					writer.close();
				} catch(IOException e) {
					e.printStackTrace();
				}				
			}			
		});
		metaControls.add(saveButton);
		metaControls.add(new JButton("Placeholder"));
		metaControls.add(new JButton("Placeholder2"));
		metaControls.add(new JButton("Quit"));
		
		return metaControls;
	}
	
	public static void addActionForPlayer(Action action) {
		court.appendActionForPlayer(action,playingAs);
	}
	
}