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
	private DefaultMutableTreeNode terminalRoot = new DefaultMutableTreeNode("�ҵ��ն�");
	private DefaultMutableTreeNode friendsRoot = new DefaultMutableTreeNode("�ҵĺ���");
	private InetAddress netAddress;
	private String localIPAddress;
	private String hostName;
	private String privateInfo;
	private Vector<PeerStruct> peersVector = null;
	public Hashtable<String, ChattingWindow> chattingWindowTable = new Hashtable<String, ChattingWindow>();

	public PeerListPanel(Vector<PeerStruct> peersList) {
		// TODO Auto-generated constructor stub
		
		peersVector = peersList;

		// �ն���Ϣ
		netAddress = HostInfo.getInetAddress();
		localIPAddress = HostInfo.getHostIp(netAddress);
		hostName = HostInfo.getHostName(netAddress);
		privateInfo = hostName + "[" + localIPAddress + "]";

		terminalRoot.add(new DefaultMutableTreeNode(privateInfo));

		if (null != peersList) {
			// ������Ϣ
			for (int i = 0; i < peersList.size(); i++) {
				PeerStruct elemOfPeersList = peersList.get(i);
				String elemInfo = elemOfPeersList.getName() + " " + elemOfPeersList.getIpAddress() + " "
						+ elemOfPeersList.getPort();
				friendsRoot.add(new DefaultMutableTreeNode(elemInfo));
			}
		}		
		
		// ���ø��ڵ�
		ROOT = new DefaultMutableTreeNode("ROOT");
		ROOT.add(terminalRoot);
		ROOT.add(friendsRoot);
		treeModel = new DefaultTreeModel(ROOT);
		friendsTree = new JTree(ROOT);
		friendsTree.setRootVisible(false);
		friendsTree.setFont(new Font("����"	, Font.PLAIN, 18));
		

		// ��Ӽ����¼���˫��������Ϣ
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
			// ������Ϣ
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

			// ˫��
			if (e.getClickCount() == 2) {
				TreePath path = friendsTree.getPathForLocation(e.getX(), e.getY());
				TreeNode node = (TreeNode) path.getLastPathComponent();
				if (node.getParent() == friendsRoot) {
					int indexOfPeersList = node.getParent().getIndex(node);
					// ��ȡ��ֵ���Է�������+�Է�IP+�Է��˿ں�
					String windowKey = ProduceKey.getKey(peersVector.get(indexOfPeersList).getName(), 
							peersVector.get(indexOfPeersList).getIpAddress(),  peersVector.get(indexOfPeersList).getPort());
					String hashKey = ProduceKey.getHashKey(windowKey);	

					// ����������Ϣ����...
					ChattingWindow cw = new ChattingWindow(node.toString());
					chattingWindowTable.put(hashKey, cw);
				}				
			}
		}
	}
}
