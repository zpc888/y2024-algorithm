package algo.link;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedNodeTest {

    @Test
    void testParseAndToString() {
        String str = "1 -> 2 -> 3 -> 4 -> 5";
        LinkedNode head = LinkedNode.parse(str);
        assertEquals(str, head.toString());
        LinkedNode cursor = head;
        assertEquals(1, cursor.val);
        cursor = cursor.next;
        assertEquals(2, cursor.val);
        cursor = cursor.next;
        assertEquals(3, cursor.val);
        cursor = cursor.next;
        assertEquals(4, cursor.val);
        cursor = cursor.next;
        assertEquals(5, cursor.val);
        assertNull(cursor.next);
    }

}