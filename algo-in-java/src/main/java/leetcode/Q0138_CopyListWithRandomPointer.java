package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

import java.util.HashMap;
import java.util.Map;

/**
 * source: https://leetcode.com/problems/copy-list-with-random-pointer/
 *
 * Giving a linked list with each node having an additional random pointer which could point to any node in the list or null.
 * Return a deep copy of the list.
 */
public class Q0138_CopyListWithRandomPointer {
    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
//        return clone_v1_WithMap_SpaceON(head);
        return clone_v2_WithoutMap_SpaceO1(head);
    }

    @MetricsRuntime(ms = 0, beats = 100)
    @MetricsMemory(mb = 44.27, beats = 71.31)
    public Node clone_v1_WithMap_SpaceON(Node head) {
        if (head == null) {
            return null;
        }
        Map<Node, Node> map = new HashMap<>(32);
        Node cursor = head;
        while (cursor != null) {
            map.put(cursor, new Node(cursor.val));
            cursor = cursor.next;
        }

        cursor = head;
        while (cursor != null) {
            map.get(cursor).next = map.get(cursor.next);
            map.get(cursor).random = map.get(cursor.random);
            cursor = cursor.next;
        }
        return map.get(head);
    }

    @MetricsRuntime(ms = 0, beats = 100)
    @MetricsMemory(mb = 44.19, beats = 83.87)
    public Node clone_v2_WithoutMap_SpaceO1(Node head) {
        if (head == null) {
            return null;
        }

        // step.1 insert all cloned nodes right-after their original nodes
        Node cursor = head;
        Node copy = null;
        while (cursor != null) {
            copy = new Node(cursor.val);
            copy.next = cursor.next;
            cursor.next = copy;
            cursor = copy.next;
        }

        // step.2 populate the clonded nodes' random pointers
        cursor = head;
        while (cursor != null) {
            copy = cursor.next;
            copy.random = cursor.random == null ? null : cursor.random.next;
            cursor = copy.next;
        }

        // step. 3 split into 2 links, then to return the cloned one and keep original one as-is
        cursor = head;
        Node ret = cursor.next;
        while (cursor != null) {
            copy = cursor.next;
            cursor.next = copy.next;
            cursor = copy.next;
            copy.next = cursor == null ? null : cursor.next;
        }
        return ret;
    }

}
