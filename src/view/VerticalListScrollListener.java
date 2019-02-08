package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class VerticalListScrollListener implements MouseWheelListener{

	LinearList parentList;
	
	public VerticalListScrollListener(LinearList parentList) {
		this.parentList = parentList;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent scroll) {
		if(scroll.getWheelRotation() > 0) {
			parentList.scroll(1);
		}else if(scroll.getWheelRotation() < 0){
			parentList.scroll(-1);
		}
	}


}
