package mainframe;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.event.*;
import java.io.IOException;

import connection.*;

// ʹ�õ���ģʽ���ͻ���ֻ����һ��

public class ClientFrame extends JFrame
	implements Runnable {
	
	private static ClientFrame instance = null; 	// Ψһʵ��
	private static int counter = 0;				// ��Ϊ 15s ����ļ�����
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

	// ÿ�� 15s����������Ǽǣ�����peersList
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