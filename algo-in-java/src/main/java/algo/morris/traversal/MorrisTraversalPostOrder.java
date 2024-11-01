package algo.morris.traversal;

import algo.tree.TreeNode;

public class MorrisTraversalPostOrder extends BaseMorrisTraversal {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 2;
    }

    public String postOrder(TreeNode root) {
        if (root == null) {
            return null;
        }
		StringBuilder sb = new StringBuilder(1024);
        if (versionToRun == 1) {
            v1_recursive(root, sb);
        } else if (versionToRun == 2) {
            v2_morris(root, sb);
        } else {
            throw new RuntimeException("Not implemented version: " + versionToRun);
        }
		return sb.toString();
    }

	private void v1_recursive(TreeNode node, StringBuilder sb) {
		if (node.left != null) {
			v1_recursive(node.left, sb);
		}
		if (node.right != null) {
			v1_recursive(node.right, sb);
		}
        processMyVal(node, sb);
	}

	private void v2_morris(TreeNode node, StringBuilder sb) {
		TreeNode curr = node;
		TreeNode rightMost = null;
		while (curr != null) {
			rightMost = curr.left;
			if (rightMost != null) {		// having left node
				while (rightMost.right != null && rightMost.right != curr) {
					rightMost = rightMost.right;
				}
				if (rightMost.right == curr) {
					// 2nd time visiting curr which has left node
					rightMost.right = null;
					reverseRightNodeThenPrint(curr.left, sb);
					curr = curr.right;
				} else {	// 1st time
					rightMost.right = curr;
					curr = curr.left;
				}
			} else {	// go to right node
				// only last right is null, the rest either has origin right 
				// node, or points to the modified one of its top left's parent
				curr = curr.right;
			}	
		}
		reverseRightNodeThenPrint(node, sb);
	}

	private TreeNode reverseRightNode(TreeNode node) {
		TreeNode curr = node;
		TreeNode right = curr.right;
		curr.right = null;
		TreeNode right2 = null;
		while (right != null) {
			right2 = right.right;
			right.right = curr;
			curr = right;
			right = right2;
		}
		return curr;
	}

	private void reverseRightNodeThenPrint(TreeNode node, StringBuilder sb) {
		TreeNode reversed = reverseRightNode(node);
		TreeNode right = reversed;
		while (right != null) {
			processMyVal(right, sb);
			right = right.right;
		}
		reverseRightNode(reversed);
	}

    public static void main(String[] args) {
        MorrisTraversalPostOrder sol = new MorrisTraversalPostOrder();
		/*
		 *           1
		 *            \
		 *             2
		 *            /
		 *           3
		 *
		 */
        sol.runAllVersions("test 1", () -> {
            TreeNode root = new TreeNode(1);
            root.right = new TreeNode(2);
            root.right.left = new TreeNode(3);
            return sol.postOrder(root);
        }, "3,2,1");
        sol.runAllVersions("test 2", () -> {
            TreeNode root = new TreeNode(1);
            root.left = new TreeNode(2);
            root.right = new TreeNode(3);
            return sol.postOrder(root);
        }, "2,3,1");
        sol.runAllVersions("test 3", () -> {
            TreeNode root = new TreeNode(1,
                    new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                    new TreeNode(3, new TreeNode(6), new TreeNode(7)));
            return sol.postOrder(root);
        }, "4,5,2,6,7,3,1");

        final int cycles = 100_000;
        TreeNode[] roots = new TreeNode[cycles];
        for (int i = 0; i < cycles; i++) {
            roots[i] = buildRandomTree(new TreeNode((int)(Math.random() * 10)));
            final int finalI = i;
            sol.runAllVersions("random test #" + (i+1), () -> sol.postOrder(roots[finalI]), null);
        }

        sol.performMeasure("random " + cycles + " tests", () -> {
            for (int i = 0; i < cycles; i++) {
                sol.postOrder(roots[i]);
            }
            return null;
        });
//====================== < random 100000 tests > Performance Report ==============
//        Version-1: Duration: PT0.055465904S
//        Version-2: Duration: PT0.077824289S       Morris traversal is slower than recursive, but not always

    }
}
