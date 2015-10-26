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
	private DatagramSocket receiveSocket = null;	// ����������Ϣ�����ݱ��׽���
	private DatagramPacket receivePacket = null;	// ����������Ϣ�����ݰ�
	
	private static final int BUFFER_SIZE = 4096;	// ���ݻ���Ĵ�С
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
					// 1. �������ĸ�IP������
					String ip = receivePacket.getAddress().getHostAddress();		
					String name = receivePacket.getAddress().getHostName();
					String port = XMLUtil.getReceivePort(XMLUtil.createDocument());
					String key = myutil.ProduceKey.getKey(name, ip, port);
					String hashKey = myutil.ProduceKey.getHashKey(key);
					
					String text = name + "[" + ip + "]:" + "\n" + receiveMsg + "\n";
					
					// 2. �ж����IP�����촰���Ƿ��Ѿ�����
					if ( panel.chattingWindowTable.containsKey(hashKey) ) {
						// 3. ��������ˣ���ֱ�ӵ���setDisplayAreaText����	
//						System.out.println(text);
						
						(panel.chattingWindowTable.get(hashKey)).setDisplayAreaText(text, alignmentFlag);
						(panel.chattingWindowTable.get(hashKey)).setVisible(true);
					}
					else {
						// 4. ���û�д��������ͻ���ʾ��Ȼ�󴴽����ڣ����ú���setDisplayAreaText4
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