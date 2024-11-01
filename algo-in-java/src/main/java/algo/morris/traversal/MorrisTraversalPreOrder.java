package algo.morris.traversal;

import algo.tree.TreeNode;

public class MorrisTraversalPreOrder extends BaseMorrisTraversal {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 2;
    }

    public String preOrder(TreeNode root) {
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
		processMyVal(node, sb);

		if (node.left != null) {
			v1_recursive(node.left, sb);
		}

		if (node.right != null) {
			v1_recursive(node.right, sb);
		}
	}

	private void v2_morris(TreeNode node, StringBuilder sb) {
		TreeNode curr = node;
		TreeNode rightMost = null;
		while (curr != null) {
			if (curr.left != null) {
				rightMost = curr.left;
				while (rightMost.right != null && rightMost.right != curr) {
					rightMost = rightMost.right;
				}
				if (rightMost.right == curr) {	// 2nd time visited curr-node
					rightMost.right = null;		// undo modified link
					curr = curr.right;			// visited right side
				} else {
                    processMyVal(curr, sb);
                    rightMost.right = curr;     // first time visited curr-node
					curr = curr.left;
				}
			} else {
                processMyVal(curr, sb);
				curr = curr.right;	// only last right is null
									// the rest either has the original link
									//   or fake link to its top-left parent
			}
		}
	}

    public static void main(String[] args) {
        MorrisTraversalPreOrder sol = new MorrisTraversalPreOrder();
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
            return sol.preOrder(root);
        }, "1,2,3");
        sol.runAllVersions("test 2", () -> {
            TreeNode root = new TreeNode(1);
            root.left = new TreeNode(2);
            root.right = new TreeNode(3);
            return sol.preOrder(root);
        }, "1,2,3");
        sol.runAllVersions("test 3", () -> {
            TreeNode root = new TreeNode(1,
                    new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                    new TreeNode(3, new TreeNode(6), new TreeNode(7)));
            return sol.preOrder(root);
        }, "1,2,4,5,3,6,7");

        final int cycles = 100_000;
        TreeNode[] roots = new TreeNode[cycles];
        for (int i = 0; i < cycles; i++) {
            roots[i] = buildRandomTree(new TreeNode((int)(Math.random() * 10)));
            final int finalI = i;
            sol.runAllVersions("random test #" + (i+1), () -> sol.preOrder(roots[finalI]), null);
        }

        sol.performMeasure("random " + cycles + " tests", () -> {
            for (int i = 0; i < cycles; i++) {
                sol.preOrder(roots[i]);
            }
            return null;
        });
//        ====================== < random 100000 tests > Performance Report ==============
//        Version-1: Duration: PT0.056110642S
//        Version-2: Duration: PT0.071425879S       Morris seems slower than recursive
    }
}
