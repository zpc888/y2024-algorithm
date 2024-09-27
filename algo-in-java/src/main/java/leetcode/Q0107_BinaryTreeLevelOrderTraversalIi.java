package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

// https://leetcode.com/problems/binary-tree-level-order-traversal-ii/description/
public class Q0107_BinaryTreeLevelOrderTraversalIi {
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
        System.out.println(new Q0107_BinaryTreeLevelOrderTraversalIi().levelOrderBottom(root)); // [[15,7],[9,20],[3]]
    }

    //---------------------------------------------------------------------------------
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
//        return doLevelOrder01InList(root);
        return doLevelOrder01InQueue(root);
    }

    @MetricsRuntime(ms = 1, beats = 93.11)
    @MetricsMemory(mb = 42.86, beats = 55.01)
    private List<List<Integer>> doLevelOrder01InQueue(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (root != null) {
            Queue<TreeNode> queue = new java.util.LinkedList<>();
            queue.offer(root);
            recursiveLevelOrder(ret, queue);
        }
        return ret;
    }

    private void recursiveLevelOrder(List<List<Integer>> collector, Queue<TreeNode> nodes) {
        if (nodes.isEmpty()) {
            return;
        }
        int size = nodes.size();
        List<Integer> levelVals = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            TreeNode node = nodes.poll();
            if (node != null) {
                if (node.left != null) {
                    nodes.offer(node.left);
                }
                if (node.right != null) {
                    nodes.offer(node.right);
                }
                levelVals.add(node.val);
            }
        }
        recursiveLevelOrder(collector, nodes);
        collector.add(levelVals);
    }

    // Runtime 1ms Beats 93.11% Memory 43.32MB Beats 6.21%
    @MetricsRuntime(ms = 1, beats = 93.11)
    @MetricsMemory(mb = 43.32, beats = 6.21)
    private List<List<Integer>> doLevelOrder01InList(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (root != null) {
            recursiveLevelOrder(ret, List.of(root));
        }
        return ret;
    }

    private void recursiveLevelOrder(List<List<Integer>> collector, List<TreeNode> nodes) {
        List<Integer> levelVals = new ArrayList<>(nodes.size());
        List<TreeNode> kidNodes = new ArrayList<>(nodes.size() * 2);
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
        collector.add(levelVals);
    }

}
