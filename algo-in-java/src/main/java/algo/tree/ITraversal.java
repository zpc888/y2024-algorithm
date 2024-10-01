package algo.tree;

public interface ITraversal {
    String preOrder(TreeNode root);

    String inOrder(TreeNode root);

    String postOrder(TreeNode root);

    String levelOrder(TreeNode root);

    default void processNode(StringBuilder sb, TreeNode node) {
        if (!sb.isEmpty()) {
            sb.append(", ");
        }
        sb.append(node.val);
    }
}
