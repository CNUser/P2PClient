package listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// 模拟双击树节点响应事件
class DoubleClickTreeListener extends MouseAdapter {
	private boolean flagOfDoubleClick;
	
	public void mouseClick(MouseEvent e) {
		// 双击
		if (e.getClickCount() == 2) {
			 
		}
	}
}