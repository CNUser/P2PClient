package mainframe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.*;

import connection.HostInfo;
import myutil.*;

public class ChattingWindow extends JFrame 
	implements Runnable {
	private JScrollPane displayScrollPane;
	private JScrollPane inputScrollPane;
	private JTextArea displayMsgArea;
	private JTextArea inputMsgArea;
	private JButton enterButton;
	private JPanel msgPanel;
	private JPanel buttonPanel;
	protected static String id; // 窗口id，以聊天对方的ip+name+port作为标志
	
	private String serverName;
	private String serverIP;
	private String serverPort;
//	private int receivePort = HostInfo.getPort();
	private DatagramSocket sendSocket = null;		// 声明发送信息的数据报套接字
	private DatagramPacket sendPacket = null; 		// 声明发送信息的数据包
//	private DatagramSocket receiveSocket = null;	// 声明接收信息的数据报套接字
//	private DatagramPacket receivePacket = null;	// 声明接收信息的数据包
	
	private boolean alighmentFlag = false;
	
//	private static final int BUFFER_SIZE = 4096;	// 数据缓存的大小
//	private byte[] inBuf = null;
	
	public ChattingWindow(String id) {
		
		this.id = id;
		
		// id 包含对方的主机名，IP，Port信息
		String[] info = id.split(" ");
		if (3 == info.length) {
			serverName = info[0];
			serverIP   = info[1];
			serverPort = info[2];
			
			// 设置window的title
			setTitle(serverName + "[" + serverIP + "]");
		}
		else {
			System.out.println("split error-->" + id);
		}
		
		// 绝对布局
		setBounds(150, 150, 600, 500);
		
//		displayMsgArea = new TextArea("", 20, 18, TextArea.SCROLLBARS_BOTH);
		displayMsgArea = new JTextArea();
		displayMsgArea.setEditable(false);
		displayMsgArea.setBackground(Color.white);
		displayMsgArea.setForeground(Color.black);
//		displayMsgArea.setBounds(4, 0, 590, 280);
		displayMsgArea.setFont(new Font("楷体", Font.PLAIN, 14));
		
		displayScrollPane = new JScrollPane(displayMsgArea);
		displayScrollPane.setAutoscrolls(true);
		displayScrollPane.setBounds(4, 0, 590, 280);
		
		inputMsgArea = new JTextArea();
		inputMsgArea.setBackground(Color.white);
		inputMsgArea.setForeground(Color.black);
		inputMsgArea.setFont(new Font("楷体", Font.PLAIN, 14));
//		inputMsgArea.setBounds(4, 310, 590, 100);		
		
		inputScrollPane = new JScrollPane(inputMsgArea);
		inputScrollPane.setAutoscrolls(true);
		inputScrollPane.setBounds(4, 310, 590, 115);
		
		enterButton = new JButton("Enter");
		Color bkColor = enterButton.getBackground();
		enterButton.setBounds(510, 430, 80, 30);
		enterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				// 通信...
				String msg = inputMsgArea.getText();
				sendMsg(msg);
				String text = HostInfo.getHostIp(HostInfo.getInetAddress())+ ":" + "\n" +
				              msg + "\n";
				setDisplayAreaText(text, alighmentFlag);
			}
			
		});
		
		msgPanel = new JPanel();
		msgPanel.setLayout(null);
//		msgPanel.setBackground(Color.lightGray);
		msgPanel.add(displayScrollPane);
		msgPanel.add(inputScrollPane);
		msgPanel.add(enterButton);
		

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
//				closeSocket();
			}
		});
		
		setResizable(false);
		setLayout(new BorderLayout());
		add(msgPanel, BorderLayout.CENTER);
		setVisible(true);
	}

	// 接收消息
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		String receiveInfo = "";
//		try {
//			inBuf = new byte[BUFFER_SIZE];
//			receivePacket = new DatagramPacket(inBuf, inBuf.length);
//			receiveSocket = new DatagramSocket(receivePort);
//		
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(this, e.getMessage(), 
//					"ChattingWindows Error", JOptionPane.ERROR_MESSAGE);
//		}
//		
//		while (true) {
//			if (receiveSocket == null) {
//				break;
//			}
//			else {
//				try {
//					receiveSocket.receive(receivePacket);
//					String receiveMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());
//					displayMsgArea.append(serverName + ":" + "(" + serverIP + ")" +
//											receiveMsg + "\n");
//					
//				} catch (Exception e) {
//					JOptionPane.showMessageDialog(this, e.getMessage(), 
//							"ChattingWindows Error", JOptionPane.ERROR_MESSAGE);
//				}
//			}
//		}
	}
	
	private void sendMsg(String msg) {
		try {
			InetAddress address = InetAddress.getByName(serverIP);
			byte[] msgOfBytes = msg.getBytes();
			sendPacket = new DatagramPacket(msgOfBytes, msgOfBytes.length, address, Integer.parseInt(serverPort));
			sendSocket = new DatagramSocket();
			sendSocket.send(sendPacket);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), 
					"ChattingWindows Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// 设置显示内容，flag = true时左侧显示，蓝色
	public void setDisplayAreaText(String text, boolean flag) {
		displayMsgArea.setAlignmentY(RIGHT_ALIGNMENT);
		displayMsgArea.setForeground(Color.BLACK);
		
		if (flag) {
			displayMsgArea.setAlignmentY(LEFT_ALIGNMENT);
			displayMsgArea.setForeground(Color.BLUE);
		}
		
		displayMsgArea.setText(text);
	}
	
//	private void closeSocket() {
//		receiveSocket.close();
//	}
}