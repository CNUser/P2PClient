package mainframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import peers.PeerStruct;
import connection.HostInfo;
import myutil.ProduceKey;

public class PeerListPanel extends JPanel {
	private JScrollPane friendsScrollPanel;
	private DefaultTreeModel treeModel; 
	private JTree friendsTree;
	private DefaultMutableTreeNode ROOT;
	private DefaultMutableTreeNode terminalRoot = new DefaultMutableTreeNode("我的终端");
	private DefaultMutableTreeNode friendsRoot = new DefaultMutableTreeNode("我的好友");
	private InetAddress netAddress;
	private String localIPAddress;
	private String hostName;
	private String privateInfo;
	private Vector<PeerStruct> peersVector = null;
	public Hashtable<String, ChattingWindow> chattingWindowTable = new Hashtable<String, ChattingWindow>();

	public PeerListPanel(Vector<PeerStruct> peersList) {
		// TODO Auto-generated constructor stub
		
		peersVector = peersList;

		// 终端信息
		netAddress = HostInfo.getInetAddress();
		localIPAddress = HostInfo.getHostIp(netAddress);
		hostName = HostInfo.getHostName(netAddress);
		privateInfo = hostName + "[" + localIPAddress + "]";

		terminalRoot.add(new DefaultMutableTreeNode(privateInfo));

		if (null != peersList) {
			// 好友信息
			for (int i = 0; i < peersList.size(); i++) {
				PeerStruct elemOfPeersList = peersList.get(i);
				String elemInfo = elemOfPeersList.getName() + " " + elemOfPeersList.getIpAddress() + " "
						+ elemOfPeersList.getPort();
				friendsRoot.add(new DefaultMutableTreeNode(elemInfo));
			}
		}		
		
		// 设置根节点
		ROOT = new DefaultMutableTreeNode("ROOT");
		ROOT.add(terminalRoot);
		ROOT.add(friendsRoot);
		treeModel = new DefaultTreeModel(ROOT);
		friendsTree = new JTree(ROOT);
		friendsTree.setRootVisible(false);
		friendsTree.setFont(new Font("宋体"	, Font.PLAIN, 18));
		

		// 添加监听事件：双击发送消息
		friendsTree.addMouseListener(new DoubleClickPanelListener());

		friendsScrollPanel = new JScrollPane(friendsTree);
		friendsScrollPanel.setAutoscrolls(true);
				
		this.setLayout(new BorderLayout());
		this.add(friendsScrollPanel, BorderLayout.CENTER);
	}

	public void SetFriendsInfo(Vector<PeerStruct> peersList, boolean flag) {
		peersVector = peersList;
		
		friendsRoot.removeAllChildren();
		
		if (flag) {
			// 好友信息
			for (int i = 0; i < peersList.size(); i++) {
				PeerStruct elemOfPeersList = peersList.get(i);
				String elemInfo = elemOfPeersList.getName() + " " + elemOfPeersList.getIpAddress() + " "
						+ elemOfPeersList.getPort();
				friendsRoot.add(new DefaultMutableTreeNode(elemInfo));
			}
		}
		
	}

	class DoubleClickPanelListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseClicked(e);

			// 双击
			if (e.getClickCount() == 2) {
				TreePath path = friendsTree.getPathForLocation(e.getX(), e.getY());
				TreeNode node = (TreeNode) path.getLastPathComponent();
				if (node.getParent() == friendsRoot) {
					int indexOfPeersList = node.getParent().getIndex(node);
					// 获取键值：对方主机名+对方IP+对方端口号
					String windowKey = ProduceKey.getKey(peersVector.get(indexOfPeersList).getName(), 
							peersVector.get(indexOfPeersList).getIpAddress(),  peersVector.get(indexOfPeersList).getPort());
					String hashKey = ProduceKey.getHashKey(windowKey);	

					// 弹出发送消息界面...
					ChattingWindow cw = new ChattingWindow(node.toString());
					chattingWindowTable.put(hashKey, cw);
				}				
			}
		}
	}
}
