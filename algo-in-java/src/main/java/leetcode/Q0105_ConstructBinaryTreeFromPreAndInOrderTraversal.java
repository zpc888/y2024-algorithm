package leetcode;

import java.util.*;

// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/
public class Q0105_ConstructBinaryTreeFromPreAndInOrderTraversal {
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
        System.out.println(new Q0105_ConstructBinaryTreeFromPreAndInOrderTraversal().buildTree(
                new int[]{3,9,20,15,7},
                new int[]{9,3,15,20,7}
        ));
    }

    //---------------------------------------------------------------------------------
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }

        // Runtime 3ms beats 40.15%   Memory 44.21 MB beats 55.68%
//        TreeNode root = new TreeNode(preorder[0]);
//        insertChildren(root, preorder, 0, preorder.length, inorder, 0);
//        return root;

        // Runtime 3ms beats 40.15%   Memory 44.21 MB beats 55.68%
        // return doBuildTree(preorder, 0, preorder.length, inorder, 0, inorder.length);

        // Runtime 2ms beats 68.12%   Memory 44.28 MB beats 55.68%
        return doBuildTreeWithInOrderLookupCache(preorder, inorder);
    }

    private void insertChildren(TreeNode root, int[] preorder, int preIdx, int len, int[] inorder, int inIdx) {
        int leftKidsCnt = 0;
        while (inorder[inIdx + leftKidsCnt] != root.val) {
            leftKidsCnt++;
        }
        if (leftKidsCnt > 0) {
            root.left = new TreeNode(preorder[preIdx + 1]);
            insertChildren(root.left, preorder, preIdx + 1, leftKidsCnt, inorder, inIdx);
        }
        if (leftKidsCnt + 1 < len) {
            root.right = new TreeNode(preorder[preIdx + leftKidsCnt + 1]);
            insertChildren(root.right, preorder, preIdx + leftKidsCnt + 1, len - leftKidsCnt - 1, inorder, inIdx + leftKidsCnt + 1);
        }
    }

    private TreeNode doBuildTree(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
        if (preStart >= preEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preStart]);
        int leftKidsCnt = 0;
        while (inorder[inStart + leftKidsCnt] != root.val) {
            leftKidsCnt++;
        }
        root.left = doBuildTree(preorder, preStart + 1, preStart + 1 + leftKidsCnt, inorder, inStart, inStart + leftKidsCnt);
        root.right = doBuildTree(preorder, preStart + 1 + leftKidsCnt, preEnd, inorder, inStart + leftKidsCnt + 1, inEnd);
        return root;
    }

    private TreeNode doBuildTreeWithInOrderLookupCache(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0 || preorder.length != inorder.length) {
            return null;
        }
        Map<Integer, Integer> inOrderLookup = new HashMap<>(preorder.length);
        for (int i = 0; i < inorder.length; i++) {
            inOrderLookup.put(inorder[i], i);
        }
        return doBuildTreeWithInOrderLookupCache(preorder, 0, preorder.length, inorder, 0, inorder.length, inOrderLookup);
    }


    private TreeNode doBuildTreeWithInOrderLookupCache(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd, Map<Integer, Integer> cache) {
        if (preStart >= preEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preStart]);
        int inRootIdx = cache.get(root.val);
        int leftKidsCnt = inRootIdx - inStart;
        root.left = doBuildTreeWithInOrderLookupCache(preorder, preStart + 1, preStart + 1 + leftKidsCnt, inorder, inStart, inStart + leftKidsCnt, cache);
        root.right = doBuildTreeWithInOrderLookupCache(preorder, preStart + 1 + leftKidsCnt, preEnd, inorder, inStart + leftKidsCnt + 1, inEnd, cache);
        return root;
    }



}
