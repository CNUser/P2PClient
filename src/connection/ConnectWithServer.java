package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

import mainframe.*;
import peers.PeerStruct;

// �ͻ���������������Ӳ��� tcp Э��
public class ConnectWithServer extends Thread {
	private ClientFrame clientUI;
	private String serverIP;
	private final int servePort = 4567;		// ����˿�
	private final String REGISTER = "register";
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	
	public ConnectWithServer(ClientFrame ui) throws IOException {
		clientUI = ui;
		
		try {
			socket = new Socket(serverIP, servePort);
			in = new BufferedReader( new InputStreamReader(
														socket.getInputStream()) );
			out = new PrintWriter(socket.getOutputStream());
		
		} catch (Exception e) {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (socket != null)
				socket.close();
			
			e.printStackTrace();
		}
		
		this.start();
	}
	
	public void run() {
		
		try {
			String msg = ""; 
			String[] peersStrList = null;
			do {				
				try {
					msg = in.readLine();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			} while (msg != null);	
			
					
			peersStrList = msg.split(",");
				
			repaintPeersListOfClient(peersStrList);
				
			closeConnection();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	// �ػ�clientUI��PeersListPanel
	public void repaintPeersListOfClient(String[] peersList) {
		
	}
	
	public void closeConnection() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (socket != null)
				socket.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}