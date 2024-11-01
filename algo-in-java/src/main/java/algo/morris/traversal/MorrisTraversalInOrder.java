package algo.morris.traversal;

import algo.tree.TreeNode;

public class MorrisTraversalInOrder extends BaseMorrisTraversal {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 2;
    }

    public String inOrder(TreeNode root) {
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

		processMyVal(node, sb);

		if (node.right != null) {
			v1_recursive(node.right, sb);
		}
	}



	private void v2_morris(TreeNode node, StringBuilder sb) {
		TreeNode curr = node;
		while (curr != null) {
			if (curr.left != null) {
				TreeNode rightMost = curr.left;
				while (rightMost.right != null && rightMost.right != curr) {
					rightMost = rightMost.right;
				}
				if (rightMost.right == curr) {	// visited earlier for left side
					rightMost.right = null;
					processMyVal(curr, sb);	// parent node
					curr = curr.right;
				} else {        // first time, go left
					rightMost.right = curr;
					curr = curr.left;
				}
			} else { // no left
				processMyVal(curr, sb);
				curr = curr.right;
			}
		}
	}

    public static void main(String[] args) {
        MorrisTraversalInOrder sol = new MorrisTraversalInOrder();
        sol.runAllVersions("test 1", () -> {
            TreeNode root = new TreeNode(1);
            root.right = new TreeNode(2);
            root.right.left = new TreeNode(3);
            return sol.inOrder(root);
        }, "1,3,2");
        sol.runAllVersions("test 2", () -> {
            TreeNode root = new TreeNode(1);
            root.left = new TreeNode(2);
            root.right = new TreeNode(3);
            return sol.inOrder(root);
        }, "2,1,3");
        sol.runAllVersions("test 3", () -> {
            TreeNode root = new TreeNode(1,
                    new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                    new TreeNode(3, new TreeNode(6), new TreeNode(7)));
            return sol.inOrder(root);
        }, "4,2,5,1,6,3,7");

        final int cycles = 100_000;
        TreeNode[] roots = new TreeNode[cycles];
        for (int i = 0; i < cycles; i++) {
            roots[i] = buildRandomTree(new TreeNode((int)(Math.random() * 10)));
            final int finalI = i;
            sol.runAllVersions("random test #" + (i+1), () -> sol.inOrder(roots[finalI]), null);
        }

        sol.performMeasure("random " + cycles + " tests", () -> {
            for (int i = 0; i < cycles; i++) {
                sol.inOrder(roots[i]);
            }
            return null;
        });
//====================== < random 100000 tests > Performance Report ==============
//        Version-1: Duration: PT0.052973658S
//        Version-2: Duration: PT0.052111518S
    }
}
