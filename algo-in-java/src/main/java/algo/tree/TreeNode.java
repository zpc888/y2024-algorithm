package algo.tree;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int x) {
        val = x;
    }

    public TreeNode(int x, TreeNode left, TreeNode right) {
        val = x;
        this.left = left;
        this.right = right;
    }

    public static boolean hasSameValue(TreeNode a, TreeNode b) {
        if (a == b) {       // including a == null && b == null
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.val == b.val && hasSameValue(a.left, b.left) && hasSameValue(a.right, b.right);
    }
}
