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

import javax.swing.*;


class ChattingWindow extends JFrame 
	implements Runnable {
	private JScrollPane displayScrollPane;
	private JScrollPane inputScrollPane;
	private JTextArea displayMsgArea;
	private JTextArea inputMsgArea;
	private JButton enterButton;
	private JPanel msgPanel;
	private JPanel buttonPanel;
	protected static String id;
	
	public ChattingWindow(String id) {
		
		this.id = id;
		
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
			}
			
		});
		
		msgPanel = new JPanel();
		msgPanel.setLayout(null);
//		msgPanel.setBackground(Color.lightGray);
		msgPanel.add(displayScrollPane);
		msgPanel.add(inputScrollPane);
		msgPanel.add(enterButton);
		

		setResizable(false);
		setLayout(new BorderLayout());
		add(msgPanel, BorderLayout.CENTER);
		setVisible(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}