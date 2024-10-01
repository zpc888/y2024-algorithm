package algo.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SerDeserTest {
    SerDeser serDeser;
    TreeNode root;
    String  expectedPreOrder;
    String  expectedInOrder;
    String  expectedPostOrder;
    String  expectedLevelOrder;

    @BeforeEach
    void setUp() {
        serDeser = new SerDeser();
        /**
         * Construct a tree like this:
         *                      1
         *                     / \
         *                    /   \
         *                   /     \
         *                  2       3
         *                 / \       \
         *                /   \       \
         *              4      5       6
         *             /        \
         *            /          \
         *           7            8
         */
        expectedPreOrder = "1,2,4,7,#,#,#,5,#,8,#,#,3,#,6,#,#,";
        expectedInOrder = "#,7,#,4,#,2,#,5,#,8,#,1,#,3,#,6,#,";
        expectedPostOrder = "#,#,7,#,4,#,#,#,8,5,2,#,#,#,6,3,1,";
        expectedLevelOrder = "1,2,3,4,5,#,6,7,#,#,8,#,#,#,#,#,#,";  // last 4 # is for 7 and 8
        // deserialize in order based on string has ambiguity, so we don't implement it
        root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);
        root.left.left.left = new TreeNode(7);
        root.left.right.right = new TreeNode(8);
    }

    @Test
    void serializePreOrder() {
        String serialized = serDeser.serializePreOrder(root);
        assertEquals(expectedPreOrder, serialized);
        TreeNode rootCopy = serDeser.deserializePreOrder(serialized);
        assertTrue(TreeNode.hasSameValue(root, rootCopy));
    }

    @Test
    void deserializePreOrder_WithoutRecursive() {
        TreeNode rootCopy = serDeser.deserializePreOrder_V2_noRecursive(expectedPreOrder);
        assertTrue(TreeNode.hasSameValue(root, rootCopy));
    }

    @Test
    void serializeInOrder() {
        String serialized = serDeser.serializeInOrder(root);
        assertEquals(expectedInOrder, serialized);
    }

    @Test
    void serializeLevelOrder() {
        String serialized = serDeser.serializeLevelOrder(root);
        assertEquals(expectedLevelOrder, serialized);
    }

    @Test
    void serializePostOrder() {
        String serialized = serDeser.serializePostOrder(root);
        assertEquals(expectedPostOrder, serialized);
    }

//    @Test
//    void deserializeInOrder() {
//        TreeNode rootCopy = serDeser.deserializeInOrder(expectedInOrder);
//        assertTrue(TreeNode.hasSameValue(root, rootCopy));
//    }

    @Test
    void deserializePostOrder() {
        TreeNode rootCopy = serDeser.deserializePostOrder(expectedPostOrder);
        assertTrue(TreeNode.hasSameValue(root, rootCopy));
    }

    @Test
    void deserializeLevelOrder() {
        TreeNode rootCopy = serDeser.deserializeLevelOrder(expectedLevelOrder);
        assertTrue(TreeNode.hasSameValue(root, rootCopy));
    }
}