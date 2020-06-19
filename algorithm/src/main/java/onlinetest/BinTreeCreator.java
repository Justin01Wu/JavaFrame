package onlinetest;

import java.util.LinkedList;
import java.util.List;

/**
 * 功能：把一个数组的值存入二叉树中，然后进行3种方式的遍历
 *
 */
public class BinTreeCreator {

	private int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private List<Node> nodeList = null;

	protected Node root; // 根
	private int size;    // 树的节点数

	public void createBinTree(int[] array) {
		nodeList = new LinkedList<Node>();
		// 将一个数组的值依次转换为Node节点
		for (int nodeIndex = 0; nodeIndex < array.length; nodeIndex++) {
			Node node = new Node(array[nodeIndex]);
			nodeList.add(node);
		}

		// 对前lastParentIndex-1个父节点按照父节点与孩子节点的数字关系建立二叉树
		for (int parentIndex = 0; parentIndex < array.length / 2 - 1; parentIndex++) {
			// 左孩子
			nodeList.get(parentIndex).left = nodeList.get(parentIndex * 2 + 1);
			// 右孩子
			nodeList.get(parentIndex).right = nodeList.get(parentIndex * 2 + 2);
		}
		// 最后一个父节点:因为最后一个父节点可能没有右孩子，所以单独拿出来处理
		int lastParentIndex = array.length / 2 - 1;
		// 左孩子
		nodeList.get(lastParentIndex).left = nodeList
				.get(lastParentIndex * 2 + 1);
		// 右孩子,如果数组的长度为奇数才建立右孩子
		if (array.length % 2 == 1) {
			nodeList.get(lastParentIndex).right = nodeList
					.get(lastParentIndex * 2 + 2);
		}
	}

	/**
	 * 
	 * 根据给定的节点数创建一个完全二叉树或是满二叉树
	 * 
	 * @param nodeCount
	 *            要创建节点总数
	 */

	public Node createFullBiTree(int nodeCount) {

		return recurCreateFullBiTree(1, nodeCount);

	}

	/**
	 * 递归创建完全二叉树 num 节点编号 nodeCount 节点总数 TreeNode 返回创建的节点
	 */

	private Node recurCreateFullBiTree(int num, int nodeCount) {

		size++;

		Node rootNode = new Node(num); // 根节点

		// 如果有左子树则创建左子树
		if (num * 2 <= nodeCount) {

			rootNode.left = recurCreateFullBiTree(num * 2, nodeCount);

			// 如果还可以创建右子树，则创建

			if (num * 2 + 1 <= nodeCount) {

				rootNode.right = recurCreateFullBiTree(num * 2 + 1, nodeCount);

			}

		}

		return (Node) rootNode;

	}



	public static void main(String[] args) {

		BinTreeCreator creator = new BinTreeCreator();
		Node root = creator.createFullBiTree(15);

		System.out.println(root.getValue());

	}

}
