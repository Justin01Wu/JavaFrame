package onlinetest;

import java.util.Stack;

public class Node {
	Node left;
	Node right;
	private int value;

	public Node(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	/**
	 * assume the tree is Binary Search Tree
	 */
	public Node findOnRecursion(int v) {
		if (v == value) {
			return this;
		} else {
			if (v < value) {
				if (left != null) {
					Node leftFind = left.findOnRecursion(v);
					if (leftFind != null) {
						return leftFind;
					}
				}
				return null;
			}

			if (right != null) {
				Node rightFind = right.findOnRecursion(v);
				if (rightFind != null) {
					return rightFind;
				}
			}
			return null;
		}
	}

	/**
	 * assume the tree is Binary Search Tree
	 */
	public Node findOnStack(int v) {

		Stack<Node> stack = new Stack<Node>();

		Node nextNode = this;
		stack.push(this);
		while (nextNode != null) {
			if (v == nextNode.value) {
				return nextNode;
			}

			if (v < nextNode.value) {
				nextNode = nextNode.left;
				stack.push(nextNode);
			} else {
				nextNode = nextNode.right;
				stack.push(nextNode);
			}
			stack.pop();

		}
		return null;

	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	public static void main(String[] args) {
		
		Node small = new Node(7);
		Node left = new Node(6);
		Node right = new Node(9);

		small.left = left;
		small.right = right;

		Node result = small.findOnStack(6);
		System.out.print(result.value);

	}
}
