package algo.morris.traversal;

import algo.tree.TreeNode;
import assist.BaseSolution;

public abstract class BaseMorrisTraversal extends BaseSolution<String> {
    protected void processMyVal(TreeNode node, StringBuilder sb) {
        if (!sb.isEmpty()) {
            sb.append(",");
        }
        sb.append(node.val);
    }

    protected static TreeNode buildRandomTree(TreeNode parent) {
        int ri = (int)(Math.random() * 6);
        if (ri == 0) {
            parent.left = new TreeNode((int)(Math.random() * 10));
            buildRandomTree(parent.left);
        } else if (ri == 1) {
            parent.right = new TreeNode((int)(Math.random() * 10));
            buildRandomTree(parent.right);
        } else if (ri == 2) {
            parent.left = new TreeNode((int)(Math.random() * 10));
            parent.right = new TreeNode((int)(Math.random() * 10));
            buildRandomTree(parent.left);
            buildRandomTree(parent.right);
        }
        return parent;
    }
}
