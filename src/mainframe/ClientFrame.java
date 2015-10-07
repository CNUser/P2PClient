package mainframe;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.event.*;
import java.io.IOException;

import connection.*;

// 使用单例模式，客户端只能有一个

public class ClientFrame extends JFrame
	implements Runnable {
	
	private static ClientFrame instance = null; 	// 唯一实例
	private static int counter = 0;				// 作为 15s 间隔的计数器
	private JTextField searchFriendsField;
	private JButton searchButton;
	private JPanel panelForSearch;
	
	
	private ClientFrame() {
		
	}
	
	public static ClientFrame getInstance() {
		if (instance == null) {
			instance = new ClientFrame();
		}
		
		return instance;
	}

	// 每隔 15s，向服务器登记，更新peersList
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread t = new ConnectWithServer(this.getInstance());
				
				Thread.sleep(15000);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}