package view.popups;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public abstract class Popup {

	protected static JFrame setupGUI(String name, int x, int y) {
		JFrame GUI = new JFrame(name);
		GUI.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {
			}
			@Override
			public void windowClosed(WindowEvent arg0) {
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
			}
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				GUI.setVisible(false);
			}
			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}
			@Override
			public void windowIconified(WindowEvent arg0) {
			}
			@Override
			public void windowOpened(WindowEvent arg0) {
			}			
		});
		
		GUI.setLocation(x, y);
		return GUI;
	}
	
}
