package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/path-sum/description/
public class Q0112_PathSum {
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
        TreeNode root = new TreeNode(5,
                new TreeNode(4, new TreeNode(11, new TreeNode(7), new TreeNode(2)), null),
                new TreeNode(8, new TreeNode(13), new TreeNode(4, null, new TreeNode(1))));
        System.out.println(new Q0112_PathSum().hasPathSum(root, 22)); // true
    }

    //---------------------------------------------------------------------------------
    public boolean hasPathSum(TreeNode root, int targetSum) {
        return hasPathSum01(root, targetSum);
    }

    @MetricsRuntime(ms = 0, beats = 100.00)
    @MetricsMemory(mb = 43.02, beats = 48.75)
    private boolean hasPathSum01(TreeNode node, int targetSum) {
        if (node == null) {
            return false;
        }
        if (node.left == null && node.right == null) {
            return targetSum == node.val;
        }
        return hasPathSum01(node.left, targetSum - node.val)
                || hasPathSum01(node.right, targetSum - node.val);
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
