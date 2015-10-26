package mainframe;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.event.*;
import java.io.IOException;
import java.util.Vector;

import connection.*;
import peers.PeerStruct;

// 使用单例模式，客户端只能有一个

public class ClientFrame extends JFrame
	implements Runnable {
	
	private static ClientFrame instance = null; 	// 唯一实例
	private PeerListPanel panel;
	public Vector<PeerStruct> peersList = null;	
	
	
	private ClientFrame() {
		panel = new PeerListPanel(peersList);
		
		setLayout(new BorderLayout());
		
		add(panel, BorderLayout.CENTER);
			
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension dimension = kit.getScreenSize();
		int screenHeight = dimension.height;
		int screenWidth = dimension.width;

		setLocation(screenWidth * 3 / 4, screenHeight / 6);
		setSize(300, 600);
		setTitle("P2PCLient");
		setIconImage(kit.createImage("icon/frame.png"));
		setVisible(true);
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
	}
	
	public PeerListPanel getPeerListPanel() {
		return this.panel;
	}
	
	public static void main(String[] args) throws Exception {
		ClientFrame f = ClientFrame.getInstance();
		FlagClass.flag = true;
		ConnectWithServer t1 = new ConnectWithServer(ClientFrame.getInstance());
		ConnectWithPeerThread t2 = new ConnectWithPeerThread(ClientFrame.getInstance().getPeerListPanel());
		
		f.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				t1.flag = false;
				t2.flag = false;
			}
		});
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}