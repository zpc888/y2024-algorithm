package algo.tree;

import java.util.ArrayDeque;
import java.util.Queue;

public class Traversal implements ITraversal {
	@Override
	public String preOrder(TreeNode root) {
		StringBuilder sb = new StringBuilder(64);
		doPreOrderTravel(sb, root);
		return sb.toString();
	}

	@Override
	public String inOrder(TreeNode root) {
		StringBuilder sb = new StringBuilder(64);
		doInOrderTravel(sb, root);
		return sb.toString();
	}


	@Override
	public String postOrder(TreeNode root) {
		StringBuilder sb = new StringBuilder(64);
		doPostOrderTravel(sb, root);
		return sb.toString();
	}

	@Override
	public String levelOrder(TreeNode root) {
		StringBuilder sb = new StringBuilder(64);
		doLevelOrderTravel(sb, root);
		return sb.toString();
	}

	private void doPreOrderTravel(StringBuilder sb, TreeNode node) {
		if (node == null) {
			return;
		}
		processNode(sb, node);
		doPreOrderTravel(sb, node.left);
		doPreOrderTravel(sb, node.right);
	}

	private void doInOrderTravel(StringBuilder sb, TreeNode node) {
		if (node == null) {
			return;
		}
		doInOrderTravel(sb, node.left);
		processNode(sb, node);
		doInOrderTravel(sb, node.right);
	}

	private void doPostOrderTravel(StringBuilder sb, TreeNode node) {
		if (node == null) {
			return;
		}
		doPostOrderTravel(sb, node.left);
		doPostOrderTravel(sb, node.right);
		processNode(sb, node);
	}

	private void doLevelOrderTravel(StringBuilder sb, TreeNode node) {
		if (node == null) {
			return;
		}
		Queue<TreeNode> queue = new ArrayDeque<>();
		queue.offer(node);
		while (!queue.isEmpty()) {
			TreeNode current = queue.poll();
			processNode(sb, current);
			if (current.left != null) {
				queue.offer(current.left);
			}
			if (current.right != null) {
				queue.offer(current.right);
			}
		}
	}


}
