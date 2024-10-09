package leetcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Q0431_EncodeNaryTreeToBinaryTreeTest {
    Q0431_EncodeNaryTreeToBinaryTree.Node n1;

    @BeforeEach
    void setUp() {
        n1 = new Q0431_EncodeNaryTreeToBinaryTree.Node(1);
        Q0431_EncodeNaryTreeToBinaryTree.Node n2 = new Q0431_EncodeNaryTreeToBinaryTree.Node(2);
        Q0431_EncodeNaryTreeToBinaryTree.Node n3 = new Q0431_EncodeNaryTreeToBinaryTree.Node(3);
        Q0431_EncodeNaryTreeToBinaryTree.Node n4 = new Q0431_EncodeNaryTreeToBinaryTree.Node(4);
        Q0431_EncodeNaryTreeToBinaryTree.Node n5 = new Q0431_EncodeNaryTreeToBinaryTree.Node(5);
        Q0431_EncodeNaryTreeToBinaryTree.Node n6 = new Q0431_EncodeNaryTreeToBinaryTree.Node(6);
        Q0431_EncodeNaryTreeToBinaryTree.Node n7 = new Q0431_EncodeNaryTreeToBinaryTree.Node(7);
        Q0431_EncodeNaryTreeToBinaryTree.Node n8 = new Q0431_EncodeNaryTreeToBinaryTree.Node(8);

        n1.children = new Q0431_EncodeNaryTreeToBinaryTree.Node[] {n2, n3, n4};
        n3.children = new Q0431_EncodeNaryTreeToBinaryTree.Node[] {n5, n6};
        n4.children = new Q0431_EncodeNaryTreeToBinaryTree.Node[] {n7, n8};
    }

    @Test
    void encodeThenDecode() {
        Q0431_EncodeNaryTreeToBinaryTree.TreeNode root = new Q0431_EncodeNaryTreeToBinaryTree().encode(n1);
        Q0431_EncodeNaryTreeToBinaryTree.Node decoded = new Q0431_EncodeNaryTreeToBinaryTree().decode(root);
        assertTrue(Q0431_EncodeNaryTreeToBinaryTree.Node.hasSameValue(n1, decoded));
    }
}