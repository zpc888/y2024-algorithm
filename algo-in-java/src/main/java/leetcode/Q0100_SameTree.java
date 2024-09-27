package leetcode;

// https://leetcode.com/problems/same-tree/description/
public class Q0100_SameTree {
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
        TreeNode p = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        TreeNode q = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        System.out.println(new Q0100_SameTree().isSameTree(p, q)); // true

        p = new TreeNode(1, new TreeNode(2), null);
        q = new TreeNode(1, null, new TreeNode(2));
        System.out.println(new Q0100_SameTree().isSameTree(p, q)); // false

        p = new TreeNode(1, new TreeNode(2), new TreeNode(1));
        q = new TreeNode(1, new TreeNode(1), new TreeNode(2));
        System.out.println(new Q0100_SameTree().isSameTree(p, q)); // false
    }

    //---------------------------------------------------------------------------------

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

}
