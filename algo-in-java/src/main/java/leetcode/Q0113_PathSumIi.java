package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// https://leetcode.com/problems/path-sum-ii/description/
public class Q0113_PathSumIi {
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
                new TreeNode(8, new TreeNode(13), new TreeNode(4, new TreeNode(5), new TreeNode(1))));
        System.out.println(new Q0113_PathSumIi().pathSum(root, 22)); // true
    }

    //---------------------------------------------------------------------------------
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        return pathSum01(root, targetSum);
    }

    @MetricsRuntime(ms = 1, beats = 99.82)
    @MetricsMemory(mb = 44.80, beats = 27.20)
    private List<List<Integer>> pathSum01(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();
        if (root != null) {
            doPathSum01(root, targetSum, path, result);
        }
        return result;
    }

    private void doPathSum01(TreeNode node, int rest, LinkedList<Integer> path, List<List<Integer>> collector) {
        if (node.left == null && node.right == null) {
            if (rest == node.val) {
                path.addLast(node.val);
                collector.add(new ArrayList<>(path));
                path.pollLast();
            }
        } else {
            if (node.left != null) {
                path.addLast(node.val);
                doPathSum01(node.left, rest - node.val, path, collector);
                path.pollLast();
            }
            if (node.right != null) {
                path.addLast(node.val);
                doPathSum01(node.right, rest - node.val, path, collector);
                path.pollLast();
            }
        }
    }

}
