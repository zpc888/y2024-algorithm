package algo.tree;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;

public class SerDeser {
    public String serializePreOrder(TreeNode root) {
		if (root == null) {
			return "#,";
		}
        StringBuilder sb = new StringBuilder();
        doSerializePreOrder(root, sb);
        return sb.toString();
    }

	public String serializeInOrder(TreeNode root) {
		if (root == null) {
			return "#,";
		}
		StringBuilder sb = new StringBuilder();
		doSerializeInOrder(root, sb);
		return sb.toString();
	}

	public String serializePostOrder(TreeNode root) {
		if (root == null) {
			return "#,";
		}
		StringBuilder sb = new StringBuilder();
		doSerializePostOrder(root, sb);
		return sb.toString();
	}

	public String serializeLevelOrder(TreeNode root) {
		if (root == null) {
			return "#,";
		}
		StringBuilder sb = new StringBuilder();
		Queue<Optional<TreeNode>> queue = new ArrayDeque<>();
		queue.offer(Optional.of(root));
		while (!queue.isEmpty()) {
			Optional<TreeNode> node = queue.poll();
			if (node.isEmpty()) {
				sb.append("#,");
			} else {
				sb.append(node.get().val).append(",");
				queue.offer(node.get().left == null ? Optional.empty() : Optional.of(node.get().left));
				queue.offer(node.get().right == null ? Optional.empty() : Optional.of(node.get().right));
			}
		}
		return sb.toString();
	}

	private void doSerializeInOrder(TreeNode node, StringBuilder sb) {
		if (node == null) {
			sb.append("#,");
			return;
		}
		doSerializeInOrder(node.left, sb);
		sb.append(node.val).append(",");
		doSerializeInOrder(node.right, sb);
	}

	private void doSerializePostOrder(TreeNode node, StringBuilder sb) {
		if (node == null) {
			sb.append("#,");
			return;
		}
		doSerializePostOrder(node.left, sb);
		doSerializePostOrder(node.right, sb);
		sb.append(node.val).append(",");
	}

	private void doSerializePreOrder(TreeNode node, StringBuilder sb) {
		if (node == null) {
			sb.append("#,");
			return;
		}
		sb.append(node.val).append(",");
		doSerializePreOrder(node.left, sb);
		doSerializePreOrder(node.right, sb);
	}

	public TreeNode deserializePreOrder(String s) {
		if (s == null) {
			return null;
		}
		String[] tokens = s.split(",");
		TreeNode head = deser(tokens[0]);
		doDeserPreOrderForNode(head, 1, tokens);
		return head;
	}

	public TreeNode deserializePreOrder_V2_noRecursive(String s) {
		if (s == null) {
			return null;
		}
		String[] tokens = s.split(",");
		Stack<TreeNode> stack = new Stack<>();
		TreeNode head = deser(tokens[0]);
		stack.push(head);
		int i = 1;
		while (i < tokens.length) {
			TreeNode cursor = stack.peek();
			if (cursor.left == null) {
				cursor.left = deser(tokens[i++]);
				if (cursor.left != null) {
					stack.push(cursor.left);
					continue;
				}
			}
			cursor = stack.pop();
			cursor.right = deser(tokens[i++]);
			if (cursor.right != null) {
				stack.push(cursor.right);
			}
		}
		// expecting stack is empty
		System.out.printf("Expecting stack is empty, actually it is %s%n", stack.isEmpty());
		return head;
	}

	private int doDeserPreOrderForNode(TreeNode cursor, int idx, String[] tokens) {
		if (idx >= tokens.length) {
			return idx;
		}
		// left node
		cursor.left = deser(tokens[idx++]);
		if (cursor.left != null) {
			idx = doDeserPreOrderForNode(cursor.left, idx, tokens);
		}
		// right node
		cursor.right = deser(tokens[idx++]);
		if (cursor.right != null) {
			idx = doDeserPreOrderForNode(cursor.right, idx, tokens);
		}
		return idx;
	}

	// TODO: note in order string can't be deserialized into a tree since it can be in different tree structure
	// i.e. 1 to many mapping, not one to one mapping
	//
	// deserialize in order based on string has ambiguity, so we don't implement it
	// one string can be deserialized into multiple trees
	public TreeNode deserializeInOrder(String s) {
		if (s == null) {
			return null;
		}
		String[] tokens = s.split(",");
		int idx = 0;
		Stack<TreeNode> stack = new Stack<TreeNode>();
		// TODO: implement this
//		while (idx < tokens.length) {
//			idx = doDeserInOrder(tokens, idx, stack);
//		}
		return stack.pop();
	}

	public TreeNode deserializePostOrder(String s) {
		if (s == null) {
			return null;
		}
		String[] tokens = s.split(",");
		if (tokens.length <= 1) {
			return null;
		}
		TreeNode head = deser(tokens[tokens.length - 1]);
		doDeserPostOrderForNode(head, tokens.length - 2, tokens);
		return head;
	}

	public TreeNode deserializeLevelOrder(String s) {
		if (s == null) {
			return null;
		}
		String[] tokens = s.split(",");
		if (tokens.length <= 1) {
			return null;
		}
		TreeNode head = deser(tokens[0]);
		Queue<TreeNode> queue = new ArrayDeque<>();
		queue.offer(head);
		for (int i = 1; i < tokens.length; i++) {
			TreeNode curr = queue.poll();
			curr.left = deser(tokens[i++]);
			curr.right = deser(tokens[i]);
			if (curr.left != null) {
				queue.offer(curr.left);
			}
			if (curr.right != null) {
				queue.offer(curr.right);
			}
		}
		return head;
	}

	private int doDeserPostOrderForNode(TreeNode parent, int pos, String[] tokens) {
		TreeNode node = deser(tokens[pos--]);
		parent.right = node;
		if (node != null) {
			pos = doDeserPostOrderForNode(node, pos, tokens);
		}
		node = deser(tokens[pos--]);
		parent.left = node;
		if (node != null) {
			pos = doDeserPostOrderForNode(node, pos, tokens);
		}
		return pos;
	}

	private TreeNode deser(String val) {
		if ("#".equals(val)) {
			return null;
		} else {
			return new TreeNode(Integer.parseInt(val));
		}
	}
}
