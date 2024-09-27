package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://leetcode.com/problems/binary-tree-level-order-traversal/description/
public class Q0102_BinaryTreeLevelOrderTraversal {
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
        System.out.println(new Q0102_BinaryTreeLevelOrderTraversal().levelOrder(root)); // [[3],[9,20],[15,7]]
    }

    //---------------------------------------------------------------------------------
    public List<List<Integer>> levelOrder(TreeNode root) {
        return doLevelOrder01(root);
    }

    // Runtime 1ms Beats 90.97% Memory 44.81MB Beats 60.49%
    private List<List<Integer>> doLevelOrder01(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (root != null) {
            recursiveLevelOrder(ret, List.of(root));
        }
        return ret;
    }

    private void recursiveLevelOrder(List<List<Integer>> collector, List<TreeNode> nodes) {
        List<Integer> levelVals = new ArrayList<>(nodes.size());
        List<TreeNode> kidNodes = new ArrayList<>(nodes.size() * 2);
        collector.add(levelVals);
        for (TreeNode node : nodes) {
            if (node != null) {
                levelVals.add(node.val);
                if (node.left != null) {
                    kidNodes.add(node.left);
                }
                if (node.right != null) {
                    kidNodes.add(node.right);
                }
            }
        }
        if (!kidNodes.isEmpty()) {
            recursiveLevelOrder(collector, kidNodes);
        }
    }

}
