package leetcode;

import java.util.ArrayList;
import java.util.List;

/*
 * Problem: 431. Encode N-ary Tree to Binary Tree
 * Source: https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree/
 *
 * Design an algorithm to encode an N-ary tree into a binary tree and decode the binary tree to get
 * the original N-ary tree. An N-ary tree is a rooted tree in which each node has no more than N children. Similarly,
 * a binary tree is a rooted tree in which each node has no more than 2 children. There is no restriction on
 * how your encode/decode algorithm should work. You just need to ensure that an N-ary tree can be encoded to a binary
 * tree and this binary tree can be decoded to the original N-nary tree structure.
 *
 */
public class Q0431_EncodeNaryTreeToBinaryTree {
    static class Node {
        public int val;
        public Node[] children;

        public Node() {}

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node[] children) {
            this.val = val;
            this.children = children;
        }

		public static boolean hasSameValue(Node n1, Node n2) {
			if (n1 == n2) {
				return true;
			}
			if (n1 == null || n2 == null) {
				return false;
			}
			return n1.val == n2.val 
				&& hasSameChildrenValue(n1.children, n2.children);
		}

		private static boolean hasSameChildrenValue(Node[] kids1, Node[] kids2) {
			if (kids1 == kids2) {
				return true;
			}
			if (kids1 == null || kids2 == null) {
				return false;
			}
			if (kids1.length != kids2.length) {
				return false;
			}
			for (int i = 0; i < kids1.length; i++) {
				if (!hasSameValue(kids1[i], kids2[i])) {
					return false;
				}
			}
			return true;
		}
    }

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    // Encodes an n-ary tree to a binary tree.
    public TreeNode encode(Node root) {
		if (root == null) {
			return null;
		}
		TreeNode head = new TreeNode(root.val);
		head.left = enChildren(root.children);
		return head;
    }

	private TreeNode enChildren(Node[] children) {
		if (children == null || children.length == 0) {
			return null;
		}
		TreeNode firstKid = null;
		TreeNode currKid = null;
		for (Node kid: children) {
			TreeNode treeK = new TreeNode(kid.val);
			treeK.left = enChildren(kid.children);
			if (firstKid == null) {
				firstKid = treeK;
			} else {
				currKid.right = treeK;
			}
			currKid = treeK;
		}
		return firstKid;
	}

    // Decodes your binary tree to an n-ary tree.
    public Node decode(TreeNode root) {
		if (root == null) {
			return null;
		}
		return dec(root);
    }

	private Node dec(TreeNode tree) {
		Node node = new Node(tree.val);
		if (tree.left != null) {
			// has children
			List<Node> kids = new ArrayList<>();
			TreeNode childCursor = tree.left;
			while (childCursor != null) {
				Node kid = dec(childCursor);
				kids.add(kid);
				childCursor = childCursor.right;
			}
			node.children = kids.toArray(new Node[kids.size()]);
		}	// else no children
		return node;
	}
}
