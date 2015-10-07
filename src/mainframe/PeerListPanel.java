package mainframe;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import peers.PeerStruct;
import connection.HostInfo;

class PeerListPanel extends JPanel {
	private JScrollPane friendsScrollPanel;
	private JTree friendsTree;
	private DefaultMutableTreeNode terminalRoot = new DefaultMutableTreeNode("我的终端");
	private DefaultMutableTreeNode friendsRoot = new DefaultMutableTreeNode("我的好友");
	private InetAddress netAddress;
	private String localIPAddress;
	private String hostName;
	private String privateInfo;
	
	public PeerListPanel(Vector<PeerStruct> peersList) {
		// TODO Auto-generated constructor stub
		netAddress = HostInfo.getInetAddress();
		localIPAddress = HostInfo.getHostIp(netAddress);
		hostName = HostInfo.getHostName(netAddress);
		privateInfo = localIPAddress + hostName;
		
		// 终端信息
		terminalRoot.add(new DefaultMutableTreeNode(privateInfo));
		
		// 好友信息
		for (int i = 0; i < peersList.size(); i++) {
			PeerStruct elemOfPeersList = peersList.get(i);
			String elemInfo = elemOfPeersList.getName() + " "
							+ elemOfPeersList.getIpAddress() + " " 
							+ elemOfPeersList.getPort();
			friendsRoot.add(new DefaultMutableTreeNode(elemInfo));
		}
		
		// 设置根节点
		DefaultMutableTreeNode[] root = {terminalRoot, friendsRoot};
		friendsTree = new JTree(root);
		
		// 添加监听事件：双击发送消息
		friendsTree.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				
				// 双击
				if (e.getClickCount() == 2) {
					TreePath path = friendsTree.getPathForLocation(e.getX(), e.getY());
					TreeNode node = (TreeNode)path.getLastPathComponent();

					// 弹出发送消息界面...
					new ChattingWindow(localIPAddress);
				}
			}
			
		});
		
		friendsScrollPanel = new JScrollPane(friendsTree);
		friendsScrollPanel.setAutoscrolls(true);		
	}
}
