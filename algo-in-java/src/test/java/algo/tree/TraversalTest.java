package algo.tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TraversalTest {
    private ITraversal instances[] = null;
    private TreeNode[] nodes = null;

    @BeforeEach
    void setUp() {
        instances = new ITraversal[]{new Traversal(), new Traversal_V2_NoRecursive()};
        nodes = new TreeNode[10];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new TreeNode(i);
        }
        nodes[0].left = nodes[1];
        nodes[0].right = nodes[2];
        nodes[1].left = nodes[3];
        nodes[1].right = nodes[4];
        nodes[2].left = nodes[5];
        nodes[2].right = nodes[6];
        nodes[3].left = nodes[7];
        nodes[3].right = nodes[8];
        nodes[4].left = nodes[9];
    }

    @Test
    void testPreOrder() {
        for (int i = 0; i < instances.length; i++) {
            assertEquals("0, 1, 3, 7, 8, 4, 9, 2, 5, 6", instances[i].preOrder(nodes[0]));
        }
    }

    @Test
    void testInOrder() {
        for (int i = 0; i < instances.length; i++) {
            assertEquals("7, 3, 8, 1, 9, 4, 0, 5, 2, 6", instances[i].inOrder(nodes[0]));
        }
    }

    @Test
    void testPostOrder() {
        for (int i = 0; i < instances.length; i++) {
            assertEquals("7, 8, 3, 9, 4, 1, 5, 6, 2, 0", instances[i].postOrder(nodes[0]));
        }
    }

    @Test
    void testPostOrder_v2_2stacks() {
        assertEquals("7, 8, 3, 9, 4, 1, 5, 6, 2, 0", new Traversal_V2_NoRecursive().postOrder_v2_2stacks(nodes[0]));
    }

    @Test
    void testLevelOrder() {
        for (int i = 0; i < instances.length; i++) {
            assertEquals("0, 1, 2, 3, 4, 5, 6, 7, 8, 9", instances[i].levelOrder(nodes[0]));
        }
    }
}