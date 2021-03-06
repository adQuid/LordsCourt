package view;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.mainUI.MainUI;

public class LinearList {

	private List<Component> components;
	private int position = 0;
	VerticalListScrollListener listener = new VerticalListScrollListener(this);
	private JPanel parentPanel;
	private int size;
	
	public LinearList(JPanel parentPanel, List<Component> components, int size) {
		this(parentPanel, components, size, false);
	}
	
	public LinearList(JPanel parentPanel, List<Component> components, int size, boolean horizontal) {
		this.parentPanel = parentPanel;
		if(horizontal) {
			parentPanel.setLayout(new GridLayout(1,size));
		} else {
			parentPanel.setLayout(new GridLayout(size,1));	
		}
		this.components = components;
		this.size = size;
	}
	
	public void updatePanel() {
		updatePanel(components);
	}
	
	public void updatePanel(List<Component> components) {
		parentPanel.removeAll();
		parentPanel.removeMouseWheelListener(listener);
		
		this.components = components;
		
		for(int index = position; index < position+size; index++) {
			if(index<components.size()) {
				parentPanel.add(components.get(index));
			} else {
				parentPanel.add(new JLabel(""));
			}
		}
		
		parentPanel.addMouseWheelListener(listener);
		parentPanel.validate();
	}
	
	public void scroll(int scroll) {
		position += scroll;
		if(position < 0) {
			position = 0;
		}
		if(position >= components.size()) {
			position = Math.max(components.size()-1,0);
		}
		updatePanel();
	}
	
	public void scrollToBottom() {
		position = Math.max(0, components.size()-size+1);
	}
}
