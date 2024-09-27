package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/validate-binary-search-tree/description/
public class Q0098_ValidateBinarySearchTree {
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

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(val);
            List<TreeNode> kids = List.of(left, right);
            buildKidsString(sb, kids);
            return sb.toString();
        }

        private void buildKidsString(StringBuilder sb, List<TreeNode> list) {
            if (list.stream().allMatch(k -> k == null)) {
                return;
            }
            List<TreeNode> kids = new ArrayList<>(list.size() * 2);
            for (TreeNode node : list) {
                sb.append(", ");
                if (node != null) {
                    sb.append(node.val);
                    kids.add(node.left);
                    kids.add(node.right);
                } else {
                    sb.append("null");
                    kids.add(null);
                    kids.add(null);
                }
            }
            buildKidsString(sb, kids);
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        System.out.println(new Q0098_ValidateBinarySearchTree().isValidBST(root)); // [[15,7],[9,20],[3]]
    }

    //---------------------------------------------------------------------------------
    public boolean isValidBST(TreeNode root) {
        return isValidBST01(root);
    }

    @MetricsRuntime(ms = 0, beats = 100.00)
    @MetricsMemory(mb = 44.33, beats = 22.67)
    private boolean isValidBST01(TreeNode root) {
        return checkNode(root).bst;
    }

    public static record Result(boolean bst, int min, int max) {}

    private Result checkNode(TreeNode node) {
        if (node == null) {
            return null;
        }
        Result leftResult  = checkNode(node.left);
        Result rightResult = checkNode(node.right);
        boolean isBST = (leftResult == null || leftResult.bst) &&
                (rightResult == null || rightResult.bst) &&
                (leftResult == null || leftResult.max < node.val) &&
                (rightResult == null || rightResult.min > node.val);
        return new Result(isBST, leftResult == null ? node.val : leftResult.min, rightResult == null ? node.val : rightResult.max);
    }

}
