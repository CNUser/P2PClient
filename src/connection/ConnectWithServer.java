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
import myutil.XMLUtil;

// 客户端与服务器的连接采用 tcp 协议
public class ConnectWithServer extends Thread {
	public static boolean flag = true;
	private ClientFrame clientUI;
	private String serverIP = XMLUtil.getServerIP(XMLUtil.createDocument()) ;
	private final int servePort = Integer.parseInt(XMLUtil.getServerPort(XMLUtil.createDocument())); // 服务端口
	private String receivePort = XMLUtil.getReceivePort(XMLUtil.createDocument());
	private final String REGISTER = "register";
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public ConnectWithServer(ClientFrame ui) throws IOException {
		clientUI = ui;

		this.start();
	}

	public void run() {
		
		while (flag) {
//			System.out.println("run");
			try {
				socket = new Socket(serverIP, servePort);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				String msg = null;
				String[] peersStrList = null;

				try {
					out.println(REGISTER+receivePort);
					msg = in.readLine();

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (msg == null || msg.equals("")) {
//					System.out.println("empty info");
					repaintPeersListOfClient(null, false);
				}
				
				if ((null != msg) && !msg.equals("")) {
					peersStrList = msg.split(",");

					repaintPeersListOfClient(peersStrList, true);
				}
				
				// 每隔 5s，向服务器登记，更新peersList
				sleep(5000);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		closeConnection();

	}

	// 重画clientUI的PeersListPanel
	public void repaintPeersListOfClient(String[] peersList, boolean flag) {
//		System.out.println("repaintPeersListOfClient");
		Vector<PeerStruct> peersTabel = new Vector<PeerStruct>();
		
		if (false == flag)
			clientUI.getPeerListPanel().SetFriendsInfo(null, false);
		
		if (flag) {
			
			int indexOfTabel = 0;

			for (int i = 0; i < peersList.length; i++) {
				if (0 == (i % 3)) {
					PeerStruct peer = new PeerStruct(peersList[i], peersList[i + 1], peersList[i + 2]);

					peersTabel.add(peer);

					indexOfTabel++;
				}
			}

			clientUI.getPeerListPanel().SetFriendsInfo(peersTabel, true);
		}
		
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