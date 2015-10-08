package connection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConnectWithPeerThread extends Thread {
	private JFrame frame = null;
	private int receivePort = HostInfo.getPort();
	private DatagramSocket receiveSocket = null;	// 声明接收信息的数据报套接字
	private DatagramPacket receivePacket = null;	// 声明接收信息的数据包
	
	private static final int BUFFER_SIZE = 4096;	// 数据缓存的大小
	private byte[] inBuf = null;
	
	public ConnectWithPeerThread(JFrame frame) {
		super();
		this.frame = frame;
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
					// 2. 判断这个IP的聊天窗口是否已经创建
					// 3. 如果创建了，就直接调用setDisplayAreaText函数
					// 4. 如果没有创建，给客户提示，然后创建窗口，调用函数setDisplayAreaText
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}