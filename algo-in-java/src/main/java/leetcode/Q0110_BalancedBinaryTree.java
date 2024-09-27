package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/balanced-binary-tree/description/
public class Q0110_BalancedBinaryTree {
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
        System.out.println(new Q0110_BalancedBinaryTree().isBalanced(root)); // [[15,7],[9,20],[3]]
    }

    //---------------------------------------------------------------------------------
    public boolean isBalanced(TreeNode root) {
        //return isBalanced01(root);
        //return isBalanced02(root);
//        return isBalanced03(root);
        return isBalanced04(root);
    }

    @MetricsRuntime(ms = 1, beats = 28.36)
    @MetricsMemory(mb = 44.66, beats = 15.89)
    private boolean isBalanced01(TreeNode root) {
        return checkNode(root).isBalanced;
    }

    @MetricsRuntime(ms = 1, beats = 28.36)
    @MetricsMemory(mb = 44.25, beats = 60.08)
    private boolean isBalanced02(TreeNode root) {
        return checkNodeShortCircuit(root).isBalanced;
    }

    @MetricsRuntime(ms = 1, beats = 28.36)
    @MetricsMemory(mb = 44.04, beats = 86.94)
    private boolean isBalanced03(TreeNode root) {
        return checkNodeShortCircuit(root, new BooleanHolder(true)).isBalanced;
    }

    @MetricsRuntime(ms = 1, beats = 28.36)
    @MetricsMemory(mb = 44.27, beats = 60.08)
    public boolean isBalanced04(TreeNode root) {
        try {
            ensureBalancedAndGetHeight(root);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private int ensureBalancedAndGetHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftHeight  = ensureBalancedAndGetHeight(node.left);
        int rightHeight = ensureBalancedAndGetHeight(node.right);
        boolean isBalancedValue = Math.abs(leftHeight - rightHeight) <= 1;
        if (!isBalancedValue) {
            throw new IllegalStateException();
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static class BooleanHolder {
        public boolean value;
        public BooleanHolder(boolean value) {
            this.value = value;
        }
    }

    public static record Result(boolean isBalanced, int height) {}

    public static Result UNBALANCED = new Result(false, -1);

    private Result checkNode(TreeNode node) {
        if (node == null) {
            return new Result(true, 0);
        }
        Result leftResult  = checkNode(node.left);
        Result rightResult = checkNode(node.right);
        int height = Math.max(leftResult.height, rightResult.height) + 1;
        boolean isBalanced = leftResult.isBalanced && rightResult.isBalanced
                && Math.abs(leftResult.height - rightResult.height) <= 1;
        return new Result(isBalanced, height);
    }

    private Result checkNodeShortCircuit(TreeNode node) {
        if (node == null) {
            return new Result(true, 0);
        }
        Result leftResult  = checkNode(node.left);
        Result rightResult = checkNode(node.right);
        if (!leftResult.isBalanced || !rightResult.isBalanced) {
            return new Result(false, -1);
        }
        boolean isBalanced = Math.abs(leftResult.height - rightResult.height) <= 1;
        if (!isBalanced) {
            return new Result(false, -1);
        }
        return new Result(true, Math.max(leftResult.height, rightResult.height) + 1);
    }

    private Result checkNodeShortCircuit(TreeNode node, BooleanHolder globalBalanced) {
        if (node == null) {
            return new Result(true, 0);
        }
        if (!globalBalanced.value) {
            return UNBALANCED;
        }
        Result leftResult  = checkNodeShortCircuit(node.left, globalBalanced);
        Result rightResult = checkNodeShortCircuit(node.right, globalBalanced);
        if (!leftResult.isBalanced || !rightResult.isBalanced) {
            globalBalanced.value = false;
            return UNBALANCED;
        }
        boolean isBalancedValue = Math.abs(leftResult.height - rightResult.height) <= 1;
        if (!isBalancedValue) {
            globalBalanced.value = false;
            return UNBALANCED;
        }
        return new Result(true, Math.max(leftResult.height, rightResult.height) + 1);
    }
}
