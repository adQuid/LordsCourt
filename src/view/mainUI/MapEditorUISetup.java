package view.mainUI;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import court.model.CourtObjectClass;
import court.model.Tile;
import court.model.TileClass;
import layout.TableLayout;
import view.popups.CourtMapSavePopup;
import view.LinearList;
import view.model.Coordinate;

public class MapEditorUISetup {

	public static void paintMapEditorControls() {
		MainUI.editorMode = true;
		
		JPanel tileOptionPanel = new JPanel();
		List<Component> tileOptionList = new ArrayList<Component>();
		LinearList tileOptionHorzList = new LinearList(tileOptionPanel,tileOptionList,6,true);
		
		JPanel objectOptionPanel = new JPanel();
		List<Component> objectOptionList = new ArrayList<Component>();
		LinearList objectOptionHorzList = new LinearList(objectOptionPanel,objectOptionList,6,true);
		
		JPanel saveOptions = new JPanel();
		
		double[][] size = {{TableLayout.FILL},{0.4,0.4,0.2}};
		MainUI.controlPanel.setLayout(new TableLayout(size));
		MainUI.controlPanel.add(tileOptionPanel,"0,0");
		MainUI.controlPanel.add(objectOptionPanel,"0,1");
		MainUI.controlPanel.add(saveOptions,"0,2");
				
		try {
			JButton emptyButton = new JButton(new ImageIcon(ImageIO.read(new File("assets/black.png"))));

			emptyButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					MainUI.clickAction = getPlaceTileAction(null);
					MainUIMapDisplay.imageDisplay.addMouseListener(MainUI.clickAction);
				}					
			});

			tileOptionList.add(emptyButton);


			for(TileClass curClass: TileClass.allClasses) {
				JButton toAdd = new JButton(new ImageIcon(ImageIO.read(new File(curClass.getImageName()))));
				toAdd.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						MainUI.clickAction = getPlaceTileAction(curClass);
						MainUIMapDisplay.imageDisplay.addMouseListener(MainUI.clickAction);
					}					
				});
				tileOptionList.add(toAdd);
			}		
			tileOptionHorzList.updatePanel();	

			for(CourtObjectClass curClass: CourtObjectClass.allClasses) {
				JButton toAdd = new JButton(new ImageIcon(ImageIO.read(new File(curClass.getImageName()))));
				toAdd.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.out.println("TEST");
					}					
				});
				objectOptionList.add(toAdd);
			}		
			objectOptionHorzList.updatePanel();	
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new CourtMapSavePopup()).start();
			}			
		});
		saveOptions.add(saveButton);
	}
	
	private static MouseListener getPlaceTileAction(TileClass type) {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				System.out.println("test");
				Coordinate coord = MainUIMapDisplay.pixelToMapCoord(arg0.getX(),arg0.getY());
				MainUI.court.removeTileAt(coord.x, coord.y);
				if(type != null) {
					MainUI.court.addTile(new Tile(coord.x,coord.y,type));	
				} else {
					MainUI.court.removeTileAt(coord.x,coord.y);
				}
				MainUIMapDisplay.repaintDisplay();
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}			
		};
	}
}
