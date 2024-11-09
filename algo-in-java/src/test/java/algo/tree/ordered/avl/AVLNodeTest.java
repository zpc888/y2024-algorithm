package algo.tree.ordered.avl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// sample source: https://en.wikipedia.org/wiki/AVL_tree
// sample source: https://stackoverflow.com/questions/20912461/more-than-one-rotation-needed-to-balance-an-avl-tree
class AVLNodeTest {
    private AVLNode<Integer, Integer> delete15Trigger2RotationAtDiffPositionsInitAVL;
    private AVLNode<Integer, Integer> delete15Trigger2RotationAtDiffPositionsFinalAVL;
    private AVLNode<Integer, Integer> i50, i25, i75, i15, i40, i60, i80, i35, i55, i65, i90, i62;   // initial AVL node
    private AVLNode<Integer, Integer> f50, f25, f75, f15, f40, f60, f80, f35, f55, f65, f90, f62;   // final AVL node
    /*
                               50
                            /      \
                           /         \
                         25           75
                       /    \        /   \
                      15    40      60    80
                            /      /  \     \
                           35     55  65     90
                                      /
                                     62
       The above is a valid AVL since it meets 1) BST; and 2) AVL height rules.
       However, deleting 15 will trigger 25 RL violation, which will trigger:
       1. 35, 40 Right rotation
       2. 25, 35 Left rotation to fix 50 left side balanced
       3. but 50 right height is 4, left is 2, so 50 balance is broken too -- RL violation
         3.1. 75, 60 right rotation;
         3.2. 50, 60 left rotation to fix 50 balance

         The final AVL tree should be:
                               60
                            /      \
                           /         \
                         50           75
                       /    \        /   \
                      35    55      65    80
                     /  \           /      \
                    25  40         62      90
     */

    @BeforeEach
    void setUp() {
        // L1
        i62 = new AVLNode<>(62, 62);

        // L2
        i35 = new AVLNode<>(35, 35);
        i55 = new AVLNode<>(55, 55);
        i65 = new AVLNode<>(65, 65, i62, null);
        i90 = new AVLNode<>(90, 90);

        // L3
        i15 = new AVLNode<>(15, 15);
        i40 = new AVLNode<>(40, 40, i35, null);
        i60 = new AVLNode<>(60, 60, i55, i65);
        i80 = new AVLNode<>(80, 80, null, i90);

        // L4
        i25 = new AVLNode<>(25, 25, i15, i40);
        i75 = new AVLNode<>(75, 75, i60, i80);

        // L5
        i50 = new AVLNode<>(50, 50, i25, i75);


        //  final state L1
        f25 = new AVLNode<>(25, 25);
        f40 = new AVLNode<>(40, 40);
        f62 = new AVLNode<>(62, 62);
        f90 = new AVLNode<>(90, 90);

        // L2
        f35 = new AVLNode<>(35, 35, f25, f40);
        f55 = new AVLNode<>(55, 55);
        f65 = new AVLNode<>(65, 65, f62, null);
        f80 = new AVLNode<>(80, 80, null, f90);

        // L3
        f50 = new AVLNode<>(50, 50, f35, f55);
        f75 = new AVLNode<>(75, 75, f65, f80);

        // L4
        f60 = new AVLNode<>(60, 60, f50, f75);

        f15 = new AVLNode<>(15, 15);        // was deleted

        delete15Trigger2RotationAtDiffPositionsInitAVL = i50;
        delete15Trigger2RotationAtDiffPositionsFinalAVL = f60;
    }

    @Test
    void testInitialAVL() {
        assertTrue(delete15Trigger2RotationAtDiffPositionsInitAVL.isValid());
        int actual = delete15Trigger2RotationAtDiffPositionsInitAVL.getActualHeightAndReportMismatched(
                (node, height) -> {
                    fail("Height mismatched at node " + node.getKey()
                            + ", expected " + height + ", but got " + node.getHeight());
                });
        assertEquals(5, actual);
    }

    @Test
    void testFinalAVL() {
        assertTrue(delete15Trigger2RotationAtDiffPositionsFinalAVL.isValid());
        int actual = delete15Trigger2RotationAtDiffPositionsFinalAVL.getActualHeightAndReportMismatched(
                (node, height) -> {
                    fail("Height mismatched at node " + node.getKey()
                            + ", expected " + height + ", but got " + node.getHeight());
        });
        assertEquals(4, actual);
    }

    @Test
    void testBuildInitialAVL() {
        AVLNode<Integer, Integer> root = new AVLNode<>(50, 50);
        root = root.add(25, 25);
        root = root.add(75, 75);
        root = root.add(15, 15);
        root = root.add(40, 40);
        root = root.add(60, 60);
        root = root.add(80, 80);
        root = root.add(35, 35);
        root = root.add(55, 55);
        root = root.add(65, 65);
        root = root.add(90, 90);
        root = root.add(62, 62);
        assertTrue(AVLNode.avlEquals(root, delete15Trigger2RotationAtDiffPositionsInitAVL));
    }

    @Test
    void testDelete15Trigger2RotationAtDiffPositions() {
        delete15Trigger2RotationAtDiffPositionsInitAVL = delete15Trigger2RotationAtDiffPositionsInitAVL.remove(15);
        assertTrue(AVLNode.avlEquals(delete15Trigger2RotationAtDiffPositionsInitAVL, delete15Trigger2RotationAtDiffPositionsFinalAVL));
    }

    /**
     * test https://upload.wikimedia.org/wikipedia/commons/f/fd/AVL_Tree_Example.gif
     * with each add operation, the AVL tree should be balanced.
     * @see <a href="https://en.wikipedia.org/wiki/AVL_tree">AVL tree</a>
     * Adding M, N, O, L, K, Q, P, H, I, A
     */
    @Test
    void testAddASeriesOfNodes() {
        AVLNode<Character, Character> root = new AVLNode<>('M', 'M');
        root = root.add('N', 'N');
        assertTrue(AVLNode.avlEquals(root, newNode('M', null, newNode('N'))));
        root = root.add('O', 'O');
        assertTrue(AVLNode.avlEquals(root, newNode('N', newNode('M'), newNode('O'))));
        root = root.add('L', 'L');
        assertTrue(AVLNode.avlEquals(root, newNode('N',
                newNode('M', newNode('L'), null), newNode('O'))));
        root = root.add('K', 'K');
        assertTrue(AVLNode.avlEquals(root, newNode('N',
                newNode('L', newNode('K'), newNode('M')), newNode('O'))));
        root = root.add('Q', 'Q');
        assertTrue(AVLNode.avlEquals(root, newNode('N',
                newNode('L', newNode('K'), newNode('M')),
                newNode('O', null, newNode('Q')))));
        root = root.add('P', 'P');
        assertTrue(AVLNode.avlEquals(root, newNode('N',
                newNode('L', newNode('K'), newNode('M')),
                newNode('P', newNode('O'), newNode('Q')))));
        root = root.add('H', 'H');
        assertTrue(AVLNode.avlEquals(root, newNode('N',
                newNode('L', newNode('K', newNode('H'), null), newNode('M')),
                newNode('P', newNode('O'), newNode('Q')))));
        root = root.add('I', 'I');
        assertTrue(AVLNode.avlEquals(root, newNode('N',
                newNode('L', newNode('I', newNode('H'), newNode('K')), newNode('M')),
                newNode('P', newNode('O'), newNode('Q')))));
        root = root.add('A', 'A');
        assertTrue(AVLNode.avlEquals(root,
                newNode('N',
                    newNode('I', newNode('H', newNode('A'), null),
                            newNode('L', newNode('K'), newNode('M'))),
                    newNode('P', newNode('O'), newNode('Q'))
        )));
    }

    private AVLNode<Integer, Integer> newNode(int kv) {
        return new AVLNode<>(kv, kv);
    }

    private AVLNode<Integer, Integer> newNode(int kv, AVLNode<Integer, Integer> left, AVLNode<Integer, Integer> right) {
        return new AVLNode<>(kv, kv, left, right);
    }

    private AVLNode<Character, Character> newNode(char kv) {
        return new AVLNode<>(kv, kv);
    }

    private AVLNode<Character, Character> newNode(char kv, AVLNode<Character, Character> left, AVLNode<Character, Character> right) {
        return new AVLNode<>(kv, kv, left, right);
    }
}