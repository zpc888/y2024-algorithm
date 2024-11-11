package algo.tree.ordered.sb;

import algo.tree.ordered.avl.AVLNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SBNodeTest {
    private SBNode<Integer, Integer> validAVLButNotSB;
    private SBNode<Integer, Integer> validAVLButNotSBRotated;

    @BeforeEach
    void setup() {
        /*             50
         *          /     \
         *         30      70
         *        /  \    /
         *       10  40  60
         *      /  \
         *     5   20
         */
        validAVLButNotSB = new SBNode<>(50, 50,
                new SBNode<>(30, 30,
                        new SBNode<>(10, 10, new SBNode<>(5, 5), new SBNode<>(20, 20)),
                        new SBNode<>(40, 40)),
                new SBNode<>(70, 70, new SBNode<>(60, 60), null));
        validAVLButNotSBRotated = new SBNode<>(30, 30,
                new SBNode<>(10, 10, new SBNode<>(5, 5), new SBNode<>(20, 20)),
                new SBNode<>(50, 50, new SBNode<>(40, 40),
                        new SBNode<>(70, 70, new SBNode<>(60, 60), null)));
    }

    @Test
    void testAdd() {
        SBNode<Integer, Integer> root = new SBNode<>(50, 50);
        root = root.add(30, 30);
        root = root.add(70, 70);
        root = root.add(10, 10);
        root = root.add(40, 40);
        root = root.add(60, 60);
        root = root.add(5, 5);
        root = root.add(20, 20);
        assertTrue(root.isValid());
        assertTrue(root.isBalanced());
        assertTrue(SBNode.sbEquals(root, validAVLButNotSBRotated));
    }

    @Test
    void testQuery() {
        assertEquals(10, validAVLButNotSBRotated.getFloorKey(10));
        assertEquals(20, validAVLButNotSBRotated.getFloorKey(12));
        assertEquals(20, validAVLButNotSBRotated.getAboveFloorKey(10));

        assertEquals(10, validAVLButNotSBRotated.getCeilingKey(10));
        assertEquals(5, validAVLButNotSBRotated.getCeilingKey(9));
        assertEquals(5, validAVLButNotSBRotated.getBelowCeilingKey(10));
    }

    @Test
    void isBalanced() {
        SBNode<Integer, Integer> root = new SBNode<>(30, 30,
                new SBNode<>(20, 20, new SBNode<>(10, 10), null),
        null);
        assertTrue(root.isValid());
        assertFalse(root.isBalanced());
    }

    @Test
    void isValidAVLButNotSB() {
        assertTrue(validAVLButNotSB.isValid());
        assertFalse(validAVLButNotSB.isBalanced());

        AVLNode<Integer, Integer> avlTree = new AVLNode<>(50, 50,
                new AVLNode<>(30, 30,
                        new AVLNode<>(10, 10, new AVLNode<>(5, 5), new AVLNode<>(20, 20)),
                        new AVLNode<>(40, 40)),
                new AVLNode<>(70, 70, new AVLNode<>(60, 60), null));
        assertTrue(avlTree.isBalanced());
        assertTrue(avlTree.isValid());
    }

    @Test
    void isValidAVLButNotSBRotated() {
        assertTrue(validAVLButNotSBRotated.isValid());
        assertTrue(validAVLButNotSBRotated.isBalanced());

        AVLNode<Integer, Integer> avl = new AVLNode<>(30, 30,
                new AVLNode<>(10, 10, new AVLNode<>(5, 5), new AVLNode<>(20, 20)),
                new AVLNode<>(50, 50, new AVLNode<>(40, 40),
                        new AVLNode<>(70, 70, new AVLNode<>(60, 60), null)));
        assertTrue(avl.isBalanced());
        assertTrue(avl.isValid());
    }

}