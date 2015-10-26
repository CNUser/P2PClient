package connection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mainframe.*;
import myutil.XMLUtil;

public class ConnectWithPeerThread extends Thread {
	public static boolean flag = true;
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
		
		this.start();
//		System.out.println("start");
	}
	
	public void run() {
		String receiveInfo = "";
		try {
			inBuf = new byte[BUFFER_SIZE];
			receivePacket = new DatagramPacket(inBuf, inBuf.length);
			int port = Integer.parseInt(XMLUtil.getReceivePort(XMLUtil.createDocument()));
			receiveSocket = new DatagramSocket(port);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		while (flag) {
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
					String port = XMLUtil.getReceivePort(XMLUtil.createDocument());
					String key = myutil.ProduceKey.getKey(name, ip, port);
					String hashKey = myutil.ProduceKey.getHashKey(key);
					
					String text = name + "[" + ip + "]:" + "\n" + receiveMsg + "\n";
					
					// 2. 判断这个IP的聊天窗口是否已经创建
					if ( panel.chattingWindowTable.containsKey(hashKey) ) {
						// 3. 如果创建了，就直接调用setDisplayAreaText函数	
//						System.out.println(text);
						
						(panel.chattingWindowTable.get(hashKey)).setDisplayAreaText(text, alignmentFlag);
						(panel.chattingWindowTable.get(hashKey)).setVisible(true);
					}
					else {
						// 4. 如果没有创建，给客户提示，然后创建窗口，调用函数setDisplayAreaText4
//						System.out.println(text);
						
						ChattingWindow cw = new ChattingWindow(key);
						panel.chattingWindowTable.put(hashKey, cw);
						(panel.chattingWindowTable.get(hashKey)).setDisplayAreaText(text, alignmentFlag);
					}
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}