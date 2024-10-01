package algo.tree;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class Traversal_V2_NoRecursive implements ITraversal {

    @Override
    public String preOrder(TreeNode root) {
		if (root == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(128);
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			processNode(sb, node);
			if (node.right != null) {
				stack.push(node.right);
			}
			if (node.left != null) {
				stack.push(node.left);
			}
		}
		return sb.toString();
    }

    @Override
    public String inOrder(TreeNode root) {
		if (root == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(128);
		Stack<TreeNode> stack = new Stack<>();
		TreeNode leftN = root;
		while (leftN != null) {
			stack.push(leftN);
			leftN = leftN.left;
		}
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			processNode(sb, node);
			leftN = node.right;
			while (leftN != null) {
				stack.push(leftN);
				leftN = leftN.left;
			}
		}
        return sb.toString();
    }

    @Override
    public String postOrder(TreeNode root) {
		if (root == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(128);
		Stack<TreeNode> stack = new Stack<>();
		TreeNode leftN = root;
		while (leftN != null) {
			stack.push(leftN);
			leftN = leftN.left;
		}
		Stack<TreeNode> rightVisited = new Stack<>();
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			if (!rightVisited.isEmpty() && rightVisited.peek() == node) {
				// left and right are all visited
				processNode(sb, node);
				rightVisited.pop();
			} else {
				// visiting right side
				stack.push(node);
				rightVisited.push(node);
				leftN = node.right;
				while (leftN != null) {
					stack.push(leftN);
					leftN = leftN.left;
				}
			}
		}
        return sb.toString();
    }

	public String postOrder_v2_2stacks(TreeNode root) {
		if (root == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(128);
		Stack<TreeNode> stack = new Stack<>();
		stack.push(root);
		Stack<TreeNode> output = new Stack<>();
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			output.push(node);
			if (node.left != null) {
				stack.push(node.left);
			}
			if (node.right != null) {
				stack.push(node.right);
			}
		}
		while (!output.isEmpty()) {
			processNode(sb, output.pop());
		}
		return sb.toString();
	}

    @Override
    public String levelOrder(TreeNode root) {
		if (root == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(128);
		Queue<TreeNode> queue = new ArrayDeque<>(32);
		queue.offer(root);
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
			processNode(sb, node);
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
        return sb.toString();
    }
}
