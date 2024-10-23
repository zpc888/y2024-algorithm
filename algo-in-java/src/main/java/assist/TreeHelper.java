package assist;

import algo.tree.TreeNode;

import java.util.*;

public class TreeHelper {
    private static boolean testing = false;

    public static TreeNode generateTree(int maxSize, int minVal, int maxVal) {
        return doGenTree(maxSize, minVal, maxVal, false);
    }

    public static TreeNode generateUniqTree(int maxSize, int minVal, int maxVal) {
        return doGenTree(maxSize, minVal, maxVal, true);
    }

    private static TreeNode doGenTree(int maxSize, int minVal, int maxVal, boolean uniq) {
        int[] data = uniq ? DataHelper.genRandomSizeIntArrUniq(maxSize, minVal, maxVal)
                : DataHelper.genRandomSizeIntArr(maxSize, minVal, maxVal);
        if (data.length == 0) {
            return null;
        }
        TreeNode[] nodes = Arrays.stream(data).mapToObj(TreeNode::new).toArray(TreeNode[]::new);
        int currIdx = 0;
        int childIdx = 1;
        while (childIdx < nodes.length) {
            nodes[currIdx].left = nodes[childIdx++];
            if (childIdx < nodes.length) {
                nodes[currIdx].right = nodes[childIdx++];
            }
            currIdx++;
        }
        if (testing) {
            List<TreeNode> list = new ArrayList<>(nodes.length);
            levelTraversal(nodes[0], list);
            if (list.size() != nodes.length) {
                throw new IllegalStateException("Generated tree is not correct");
            }
            for (int i = 0; i < nodes.length; i++) {
                if (nodes[i] != list.get(i)) {
                    throw new IllegalStateException("Generated tree is not correct");
                }
            }
        }
        return nodes[0];
    }

    private static void levelTraversal(TreeNode node, List<TreeNode> list) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();
            list.add(curr);
            if (curr.left != null) {
                queue.add(curr.left);
            }
            if (curr.right != null) {
                queue.add(curr.right);
            }
        }
    }

    public static void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("     ^");
            System.out.println("    * *");
            return;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(node.val);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        testing = true;
        for (int i = 0; i < 10_000; i++) {
            TreeNode root1 = generateTree(20, 1, 1000);
            TreeNode root2 = generateUniqTree(18, 1, 1000);
            printTree(root1);
            printTree(root2);
        }
    }
}
