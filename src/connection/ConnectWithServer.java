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
	private ClientFrame clientUI;
	private String serverIP = XMLUtil.getServerIP(XMLUtil.createDocument()) ;
	private final int servePort = Integer.parseInt(XMLUtil.getServerPort(XMLUtil.createDocument())); // 服务端口
	private final String REGISTER = "register";
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public ConnectWithServer(ClientFrame ui) throws IOException {
		clientUI = ui;

		try {
			socket = new Socket(serverIP, servePort);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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

			try {
				msg = in.readLine();

			} catch (Exception e) {
				e.printStackTrace();
			}

			peersStrList = msg.split(",");

			repaintPeersListOfClient(peersStrList);

			closeConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 重画clientUI的PeersListPanel
	public void repaintPeersListOfClient(String[] peersList) {
		Vector<PeerStruct> peersTabel = new Vector<PeerStruct>();
		int indexOfTabel = 0;

		for (int i = 0; i < peersList.length; i++) {
			if (0 == (i % 3)) {
				PeerStruct peer = new PeerStruct(peersList[i], peersList[i + 1], peersList[i + 2]);

				peersTabel.add(peer);

				indexOfTabel++;
			}
		}

		clientUI.getPeerListPanel().SetFriendsInfo(peersTabel);
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