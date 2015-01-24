package onlinetest;

/**
 * 
 * 二叉排序树（Binary Sort Tree）又称二叉查找树（Binary Search Tree）， 亦称二叉搜索树。
 * 它或者是一棵空树；或者是具有下列性质的二叉树： 
 * （1）若左子树不空，则左子树上所有结点的值均小于它的根结点的值；
 * （2）若右子树不空，则右子树上所有结点的值均大于它的根结点的值； 
 * （3）左、右子树也分别为二叉排序树；
 *
 */
public class BinarySearchTreeCreator {

	/** 将参数newNode的目标节点按照 左小右大 的Binary Search Tree规范 插入到以参数root的目标节点为根的子树中 
	 * if new node value already exist, then do nothing and return false 
	 * otherwise return true 
	 * */
	static boolean insert(Node newNode, Node root) {

		if(root.getValue() == newNode.getValue()){
			System.out.println(String.format(" value  %d exists in the tree, so skip it", newNode.getValue()));
			return false;
		}
		
		if (newNode.getValue() < root.getValue()) {
			if (root.left == null) {
				root.left = newNode;
				return true;
			} else {
				return insert(newNode, root.left);
			}

		} else {
			if (root.right == null) {
				root.right = newNode;
				return true;
			} else {
				return insert(newNode, root.right);
			}
		}
	}

	/* 插入 */
	static boolean bstree_insert(Node root, int value) {
		Node newNode = new Node(value);
		return insert(newNode, root);
	}

	public static void main(String[] args) {
		Node root2 = new Node(7);

		bstree_insert(root2, 5);
		bstree_insert(root2, 4);
		bstree_insert(root2, 6);
		bstree_insert(root2, 10);
		bstree_insert(root2, 9);
		root2.findOnRecursion(5);
	}
}
