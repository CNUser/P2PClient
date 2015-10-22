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
	private DatagramSocket receiveSocket = null;	// ����������Ϣ�����ݱ��׽���
	private DatagramPacket receivePacket = null;	// ����������Ϣ�����ݰ�
	
	private static final int BUFFER_SIZE = 4096;	// ���ݻ���Ĵ�С
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
					// 1. �������ĸ�IP������
					String ip = receivePacket.getAddress().getHostAddress();		
					String name = receivePacket.getAddress().getHostName();
					String port = Integer.toString(receivePacket.getPort());
					String key = myutil.ProduceKey.getKey(name, ip, port);
					
					
					String text = name + "[" + ip + "]" + "\n" + receiveMsg;
					
					// 2. �ж����IP�����촰���Ƿ��Ѿ�����
					if ( panel.chattingWindowTable.contains(key) ) {
						// 3. ��������ˣ���ֱ�ӵ���setDisplayAreaText����						
						(panel.chattingWindowTable.get(key)).setDisplayAreaText(text, alignmentFlag);
					}
					else {
						// 4. ���û�д��������ͻ���ʾ��Ȼ�󴴽����ڣ����ú���setDisplayAreaText4
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