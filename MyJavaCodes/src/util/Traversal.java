package util;

import java.util.LinkedList;
import java.util.Queue;

public class Traversal {

	class Node {
		int v;
		Node left;
		Node right;

		Node(int v) {
			this.v = v;
		}
	}

	public static void main(String[] args) {
		Traversal ins = new Traversal();
		ins.progress();
	}

	private void progress() {
		Node n1 = new Node(1);
		n1.left = new Node(2);
		n1.right = new Node(3);
		n1.left.left = new Node(4);
		n1.left.right = new Node(5);
		n1.right.left = new Node(6);
		n1.right.right = new Node(7);

		preOrder(n1);
		System.out.println();
		inOrder(n1);
		System.out.println();
		postOrder(n1);
		System.out.println();
		levelOrder(n1);
	}

	void preOrder(Node node) {
		System.out.print(node.v + " ");
		if (node.left != null)
			preOrder(node.left);
		if (node.right != null)
			preOrder(node.right);
	}

	void inOrder(Node node) {
		if (node.left != null)
			inOrder(node.left);
		System.out.print(node.v + " ");
		if (node.right != null)
			inOrder(node.right);
	}

	void postOrder(Node node) {
		if (node.left != null)
			postOrder(node.left);
		if (node.right != null)
			postOrder(node.right);
		System.out.print(node.v + " ");
	}

	void levelOrder(Node node) {
		Queue<Node> q = new LinkedList<>();
		q.offer(node);
		while (!q.isEmpty()) {
			Node n = q.poll();
			System.out.print(n.v + " ");
			if (n.left != null)
				q.offer(n.left);
			if (n.right != null)
				q.offer(n.right);
		}
	}
}
