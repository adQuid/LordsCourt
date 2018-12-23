package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
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
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import Game.model.Game;
import Game.model.actions.Move;
import court.model.CourtCharacter;
import court.model.Court;
import court.model.Tile;
import court.model.TileClass;
import view.model.Coordinate;

public class MainUI {

	public static JFrame GUI = new JFrame();
	static JPanel displayPanel;
	static JPanel controlPanel;
	
	static BufferedImage map;
	static JLabel imageDisplay = new JLabel();
	
	static JPanel oldActionPanel = new JPanel();
	static VerticalList oldActionList = new VerticalList(oldActionPanel,lastActionLabels(),5);
	
	static int visionDistance=12;
	static int focusX=0;
	static int focusY=0;
	static MouseListener clickAction;
	
	static Court court;
	
	//testing code
	static Game testGame = null;
	static CourtCharacter playingAs = null;
	
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
		paintMapEditorControls();
		setupWindow();
	}
	
	public static void setupWindow() {
		setupPanel();
		GUI.pack();
		GUI.setSize(1200, 800);
		GUI.setVisible(true);
	}
	
	public static void setupPanel() {
		paintDisplay();
		
		GUI = new JFrame();
		
		//resize on dragging the window
		GUI.addComponentListener(new ComponentAdapter( ) {
		  public void componentResized(ComponentEvent ev) {
			resizeDisplay();
		  }
		});
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		    	  	if(e.getID() != KeyEvent.KEY_PRESSED) {
		    	  		return false;
		    	  	}
		    	  	if(e.getKeyCode() == KeyEvent.VK_UP) {
						focusY--;
					}
					if(e.getKeyCode() == KeyEvent.VK_DOWN) {
						focusY++;
					}
					if(e.getKeyCode() == KeyEvent.VK_LEFT) {
						focusX--;
					}
					if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
						focusX++;
					}
					if(e.getKeyChar() == 'd') {
						court.appendActionForPlayer(new Move(Move.RIGHT),playingAs);
					}
					if(e.getKeyChar() == 'a') {
						court.appendActionForPlayer(new Move(Move.LEFT),playingAs);
					}
					if(e.getKeyChar() == 's') {
						court.appendActionForPlayer(new Move(Move.DOWN),playingAs);
					}
					if(e.getKeyChar() == 'w') {
						court.appendActionForPlayer(new Move(Move.UP),playingAs);
					}
		        return false;
		      }
		});
		
		imageDisplay.addMouseListener(clickAction);
		
		GUI.add(displayPanel, BorderLayout.NORTH);
		GUI.add(controlPanel, BorderLayout.SOUTH);
		
		GUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public static void paintDisplay() {
		displayPanel = new JPanel();
		
		map = new BufferedImage(400, 300, BufferedImage.TYPE_INT_ARGB);
		paintItBlack(map);
		imageDisplay.setIcon(new ImageIcon(map));
		
		displayPanel.add(imageDisplay);
	}
	
	private static void paintItBlack(BufferedImage image) {
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				
				image.setRGB(x, y, 0xFF000000);
			}
		}
	}
	
	public static void repaintDisplay() {
		
		paintItBlack(map);
		
		for(Tile curTile: court.getTiles()) {
			paintGameEntity(curTile.toEntity());
		}
		for(CourtCharacter curChar: court.getCharacters()) {
			paintGameEntity(curChar.toEntity());
		}
		
		imageDisplay.setIcon(new ImageIcon(map));
		imageDisplay.repaint();
	}
	
	public static void resizeDisplay() {
		map = new BufferedImage(getMapWidth(), getMapHeight(), BufferedImage.TYPE_INT_ARGB);
		repaintDisplay();
	}
	
	public static void paintGameEntity(GameEntity entity) {
		Coordinate coord = mapToPixelCoord(entity.getGridX(),entity.getGridY());

		BufferedImage image;
		try {
			image = ImageIO.read(new File(entity.getImageName()));
		} catch (IOException e) {
			e.printStackTrace();
			image = new BufferedImage(50,50, BufferedImage.TYPE_INT_ARGB);
		}
		image = scale(image,image.getType(),getMapHeight()/visionDistance,getMapHeight()/visionDistance);

		for(int x = coord.x; x < coord.x + (getMapHeight()/visionDistance); x++) {
			for(int y = coord.y; y < coord.y + (getMapHeight()/visionDistance); y++) {
				if(x > 0 && y > 0 && x < getMapWidth() && y < getMapHeight()) {
					try {
						int alpha = image.getRGB(x - coord.x, y - coord.y) >> 24 & 0xFF;
						if(alpha > 0) {
							map.setRGB(x, y, image.getRGB(x - coord.x, y - coord.y));
						}
					}catch(ArrayIndexOutOfBoundsException e) {
						System.err.println("drawing error!");
					}					
				}
			}
		}
	}

	public static void paintMapEditorControls() {
		controlPanel = new JPanel();
		JPanel tileOptions = new JPanel();
		JPanel saveOptions = new JPanel();
		
		controlPanel.setLayout(new GridLayout(2,1));
		controlPanel.add(tileOptions);
		controlPanel.add(saveOptions);
		
		tileOptions.setLayout(new FlowLayout());
		
		try {
			JButton emptyButton = new JButton(new ImageIcon(ImageIO.read(new File("assets/black.png"))));
			
			emptyButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectedClass = null;
				}					
			});
			
			tileOptions.add(emptyButton);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		for(TileClass curClass: TileClass.allClasses) {
			try {
				JButton toAdd = new JButton(new ImageIcon(ImageIO.read(new File(curClass.getImageName()))));
				
				toAdd.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						selectedClass = curClass;
					}					
				});
				
				tileOptions.add(toAdd);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new CourtMapSavePopup()).start();
			}			
		});
		saveOptions.add(saveButton);
		
		clickAction = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				Coordinate coord = pixelToMapCoord(arg0.getX(),arg0.getY());
				if(selectedClass == null) {
					court.removeTileAt(coord.x, coord.y);
					repaintDisplay();
				}else if(court.tileAt(coord.x, coord.y) == null) {
					court.addTile(new Tile(coord.x,coord.y,TileClass.roughWood));
					repaintDisplay();
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
	}
	
	public static void paintGameControls() {
		controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		
		oldActionList.updatePanel(lastActionLabels());
		
		JPanel gameControls = new JPanel();
		gameControls.setLayout(new BorderLayout());
		gameControls.add(new JButton("Do the thing!"));
		gameControls.add(oldActionPanel, BorderLayout.EAST);
		
		
		controlPanel.add(gameControls, BorderLayout.NORTH);
		controlPanel.add(generateMetaControls(), BorderLayout.SOUTH);
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
	
	private static int getMapWidth() {
		return Math.max(GUI.getWidth(),400);
	}
	
	private static int getMapHeight() {
		return Math.max((int)(GUI.getHeight()*0.75),300);
	}
	
	private static Coordinate mapToPixelCoord(int x, int y) {
		return new Coordinate((x - focusX) * getMapHeight() / visionDistance,(y - focusY) * getMapHeight() / visionDistance);
	}
	
	private static Coordinate pixelToMapCoord(int x, int y) {
		return new Coordinate((x * visionDistance / getMapHeight()) + focusX,(y * visionDistance / getMapHeight()) + focusY);
	}

	/**
	 * scale image
	 * 
	 * @param sbi image to scale
	 * @param imageType type of image
	 * @param dWidth width of destination image
	 * @param dHeight height of destination image
	 * @param fWidth x-factor for transformation / scaling
	 * @param fHeight y-factor for transformation / scaling
	 * @return scaled image
	 */
	private static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight) {
		
		BufferedImage dbi = null;
	    if(sbi != null) {
			double fWidth = dWidth / (1.0 * sbi.getWidth());
			double fHeight = dHeight / (1.0 * sbi.getHeight());
	        dbi = new BufferedImage(dWidth, dHeight, imageType);
	        Graphics2D g = dbi.createGraphics();
	        AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
	        g.drawRenderedImage(sbi, at);
	    }
	    return dbi;
	}
	
}