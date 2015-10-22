package mainframe;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.event.*;
import java.io.IOException;
import java.util.Vector;

import connection.*;
import peers.PeerStruct;

// ʹ�õ���ģʽ���ͻ���ֻ����һ��

public class ClientFrame extends JFrame
	implements Runnable {
	
	private static ClientFrame instance = null; 	// Ψһʵ��
	private PeerListPanel panel;
	public Vector<PeerStruct> peersList = null;	
	
	
	private ClientFrame() {
		panel = new PeerListPanel(peersList);
		
		setLayout(new BorderLayout());
		
		add(panel, BorderLayout.CENTER);
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosed(e);
			}
			
		});		
		
		setSize(300, 600);
		setTitle("P2PCLient");
		setVisible(true);
	}
	
	public static ClientFrame getInstance() {
		if (instance == null) {
			instance = new ClientFrame();
			instance.run();
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
				t.start();
				Thread.sleep(15000);
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public PeerListPanel getPeerListPanel() {
		return this.panel;
	}
	
	public static void main(String[] args) {
		ClientFrame.getInstance();
	}
}