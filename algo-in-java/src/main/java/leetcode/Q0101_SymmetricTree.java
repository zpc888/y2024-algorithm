package leetcode;

// https://leetcode.com/problems/symmetric-tree/description/
public class Q0101_SymmetricTree {
    static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
    }

    public static void main(String[] args) {
        TreeNode p = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), new TreeNode(4)),
                new TreeNode(2, new TreeNode(4), new TreeNode(3)));
        TreeNode q = new TreeNode(1,
                new TreeNode(2, null, new TreeNode(3)),
                new TreeNode(2, null, new TreeNode(3)));
        System.out.println(new Q0101_SymmetricTree().isSymmetric(p)); // true
        System.out.println(new Q0101_SymmetricTree().isSymmetric(q)); // false
    }

    //---------------------------------------------------------------------------------

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.val == right.val && isMirror(left.left, right.right) && isMirror(left.right, right.left);
    }
}
