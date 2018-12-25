package view.mainUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import court.model.Tile;
import court.model.TileClass;
import view.CourtMapSavePopup;
import view.model.Coordinate;

public class MapEditorUISetup {

	public static void paintMapEditorControls() {
		MainUI.controlPanel = new JPanel();
		JPanel tileOptions = new JPanel();
		JPanel saveOptions = new JPanel();
		
		MainUI.controlPanel.setLayout(new GridLayout(2,1));
		MainUI.controlPanel.add(tileOptions);
		MainUI.controlPanel.add(saveOptions);
		
		tileOptions.setLayout(new FlowLayout());
		
		try {
			JButton emptyButton = new JButton(new ImageIcon(ImageIO.read(new File("assets/black.png"))));
			
			emptyButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					MainUI.selectedClass = null;
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
						MainUI.selectedClass = curClass;
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
		
		MainUI.clickAction = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				Coordinate coord = MainUIMapDisplay.pixelToMapCoord(arg0.getX(),arg0.getY());
				if(MainUI.selectedClass == null) {
					MainUI.court.removeTileAt(coord.x, coord.y);
					MainUIMapDisplay.repaintDisplay();
				}else if(MainUI.court.tileAt(coord.x, coord.y) == null) {
					MainUI.court.addTile(new Tile(coord.x,coord.y,TileClass.roughWood));
					MainUIMapDisplay.repaintDisplay();
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
	
}
