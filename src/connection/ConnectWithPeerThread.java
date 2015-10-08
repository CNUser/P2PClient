package connection;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConnectWithPeerThread extends Thread {
	private JFrame frame = null;
	private int receivePort = HostInfo.getPort();
	private DatagramSocket receiveSocket = null;	// ����������Ϣ�����ݱ��׽���
	private DatagramPacket receivePacket = null;	// ����������Ϣ�����ݰ�
	
	private static final int BUFFER_SIZE = 4096;	// ���ݻ���Ĵ�С
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
					// 1. �������ĸ�IP������
					// 2. �ж����IP�����촰���Ƿ��Ѿ�����
					// 3. ��������ˣ���ֱ�ӵ���setDisplayAreaText����
					// 4. ���û�д��������ͻ���ʾ��Ȼ�󴴽����ڣ����ú���setDisplayAreaText
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
}