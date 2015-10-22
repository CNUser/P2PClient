package connection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mainframe.*;

public class ConnectWithPeerThread extends Thread {
	private PeerListPanel panel = null;
	private int receivePort = HostInfo.getPort();
	private DatagramSocket receiveSocket = null;	// 声明接收信息的数据报套接字
	private DatagramPacket receivePacket = null;	// 声明接收信息的数据包
	
	private static final int BUFFER_SIZE = 4096;	// 数据缓存的大小
	private byte[] inBuf = null;
	
	private boolean alignmentFlag = true;
	
	public ConnectWithPeerThread(PeerListPanel panel) {
		super();
		this.panel = panel;
	}
	
	public void run() {
		String receiveInfo = "";
		try {
			inBuf = new byte[BUFFER_SIZE];
			receivePacket = new DatagramPacket(inBuf, inBuf.length);
			receiveSocket = new DatagramSocket(receivePort);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		while (true) {
			if (receiveSocket == null) {
				break;
			}
			else {
				try {
					receiveSocket.receive(receivePacket);
					String receiveMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());
					// 1. 分析是哪个IP发来的
					String ip = receivePacket.getAddress().getHostAddress();		
					String name = receivePacket.getAddress().getHostName();
					String port = Integer.toString(receivePacket.getPort());
					String key = myutil.ProduceKey.getKey(name, ip, port);
					
					
					String text = name + "[" + ip + "]" + "\n" + receiveMsg;
					
					// 2. 判断这个IP的聊天窗口是否已经创建
					if ( panel.chattingWindowTable.contains(key) ) {
						// 3. 如果创建了，就直接调用setDisplayAreaText函数						
						(panel.chattingWindowTable.get(key)).setDisplayAreaText(text, alignmentFlag);
					}
					else {
						// 4. 如果没有创建，给客户提示，然后创建窗口，调用函数setDisplayAreaText4
						panel.chattingWindowTable.put(key, new ChattingWindow(key));
						(panel.chattingWindowTable.get(key)).setDisplayAreaText(text, alignmentFlag);
					}
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}