package view.mainUI;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import court.model.CourtCharacter;
import court.model.Tile;
import view.model.Coordinate;
import view.model.Path;
import view.GameEntity;

public class MainUIMapDisplay {

	static JPanel displayPanel;
	static BufferedImage map;
	static JLabel imageDisplay = new JLabel();
	
	static Coordinate focus = new Coordinate(0,0);
	
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
		
		for(Tile curTile: MainUI.court.getTiles()) {
			if(canSeeTarget(new Coordinate(curTile.getX(),curTile.getY()))) {
				paintGameEntity(curTile.toEntity());
			}
		}
		for(CourtCharacter curChar: MainUI.court.getCharacters()) {
			if(canSeeTarget(curChar.getCoord())) {
				paintGameEntity(curChar.toEntity());
			}
		}
		
		imageDisplay.setIcon(new ImageIcon(map));
		imageDisplay.repaint();
	}

	private static boolean canSeeTarget(Coordinate coord) {
		if(MainUI.editorMode) {
			return true;
		}
		
		Path los = MainUI.court.pathingBetween(MainUI.playingAs.getCoord(),new Coordinate(coord.x,coord.y));
		
		if(los == null) {
			return false;
		}
		
		//this needs improvement
		int lastDirection = -1;
		int duplicateDirection = -1;
		for(int index = 1; index < los.steps.size(); index++) {
			int thisDirection = Path.direction(los.steps.get(index), los.steps.get(index-1));
			if(lastDirection != -1) {
				if(duplicateDirection == -1) {			
					if(thisDirection == lastDirection) {
						duplicateDirection = thisDirection;
					}
				} else {
					if(thisDirection != duplicateDirection) {
						return false;
					}
				}
			}
			
			
			lastDirection = thisDirection;
		}
		return true;
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
		image = scale(image,image.getType(),(int)Math.ceil(getMapHeight()/(1.0*MainUI.visionDistance)),(int)Math.ceil(getMapHeight()/(1.0*MainUI.visionDistance)));

		for(int x = coord.x; x < coord.x + (getMapHeight()/MainUI.visionDistance); x++) {
			for(int y = coord.y; y < coord.y + (getMapHeight()/MainUI.visionDistance); y++) {
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
	
	private static int getMapWidth() {
		int retval = Math.max(MainUI.GUI.getWidth(),400);
		return retval - (retval%MainUI.visionDistance);
	}
	
	private static int getMapHeight() {
		int retval = Math.max((int)(MainUI.GUI.getHeight()*0.75),300);
		return retval - (retval%MainUI.visionDistance);
	}
	
	private static Coordinate mapToPixelCoord(int x, int y) {
		return new Coordinate((x - focus.x) * getMapHeight() / MainUI.visionDistance,(y - focus.y) * getMapHeight() / MainUI.visionDistance);
	}
	
	public static Coordinate pixelToMapCoord(int x, int y) {
		return new Coordinate((x * MainUI.visionDistance / getMapHeight()) + focus.x,(y * MainUI.visionDistance / getMapHeight()) + focus.y);
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
