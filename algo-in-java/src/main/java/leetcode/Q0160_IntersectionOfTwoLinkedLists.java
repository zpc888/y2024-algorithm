package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

/**
 * https://leetcode.com/problems/intersection-of-two-linked-lists/
 *
 * Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect.
 * If the two linked lists have no intersection at all, return null.
 * It is guaranteed that there are no cycles anywhere in the entire linked structure.
 *
 * Note that the linked lists must retain their original structure after the function returns.
 */
public class Q0160_IntersectionOfTwoLinkedLists {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    @MetricsRuntime(ms = 1, beats = 99.88)
    @MetricsMemory(mb = 48.53, beats = 46.72)
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		if (headA == null || headB == null) {
			return null;
		}
		// this can be solved by storing all nodes from a-list to hashset
		// while iterating headB node, checking whether it is in the hashset
		// if yes, the first one is the intersect of 2 lists; else no intersection at all.

		// since no cycles, if there is an intersection, both ends should be the same.
		ListNode tailA = headA;
		int lenA = 1;
		while (tailA.next != null) {
			tailA = tailA.next;
			lenA++;
		}
		ListNode tailB = headB;
		int lenB = 1;
		while (tailB.next != null) {
			tailB = tailB.next;
			lenB++;
		}
		if (tailA != tailB) {
			// for sure, no intersection
			return null;
		}
		ListNode longNode = lenA >= lenB ? headA : headB;
		ListNode shortNode = longNode == headA ? headB : headA;
		int lenDiff = Math.abs(lenA - lenB);
		for (int i = 0; i < lenDiff; i++) {
			longNode = longNode.next;
		}
		while (shortNode != longNode) {
			shortNode = shortNode.next;
			longNode = longNode.next;
		}
		return shortNode;
    }

    public static void main(String[] args) {
        System.out.println("\nVerify the solution with examples from leet code");
        example1();
        example2();
        example3();
    }

    private static void example1() {
        Q0160_IntersectionOfTwoLinkedLists solution = new Q0160_IntersectionOfTwoLinkedLists();
        ListNode node4 = new ListNode(4);
        ListNode node1 = new ListNode(1);
        ListNode node8 = new ListNode(8);
        ListNode node4b = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node5b = new ListNode(5);
        ListNode node6 = new ListNode(6);
        ListNode node1b = new ListNode(1);

        //      4 -> 1--\
        //               \
        //               \/
        // 5 -> 6 -> 1 -> 8 -> 4 -> 5
        node4.next = node1;
        node1.next = node8;
        node8.next = node4b;
        node4b.next = node5;

        node5b.next = node6;
        node6.next = node1b;
        node1b.next = node8;

        ListNode intersection = solution.getIntersectionNode(node4, node5b);
        if (intersection != node8) {
            throw new IllegalStateException("test failed");
        }
        System.out.printf("test passed: %s%n", "example 1 from leet code");
    }

    private static void example2() {
        Q0160_IntersectionOfTwoLinkedLists solution = new Q0160_IntersectionOfTwoLinkedLists();
        ListNode node1 = new ListNode(1);
        ListNode node9 = new ListNode(9);
        ListNode node1b = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node4 = new ListNode(4);
        ListNode node3 = new ListNode(3);

        // 1 -> 9 ->  1--\
        //               \
        //               \/
        //           3 -> 2 -> 4
        node1.next = node9;
        node9.next = node1b;
        node1b.next = node2;
        node2.next = node4;
        node3.next = node2;
        ListNode intersection = solution.getIntersectionNode(node1, node3);
        if (intersection != node2) {
            throw new IllegalStateException("test failed");
        }
        System.out.println("test passed: example 2 from leet code");
    }

    private static void example3() {
        Q0160_IntersectionOfTwoLinkedLists solution = new Q0160_IntersectionOfTwoLinkedLists();
        ListNode node2 = new ListNode(2);
        ListNode node6 = new ListNode(6);
        ListNode node4 = new ListNode(4);
        ListNode node1 = new ListNode(1);
        ListNode node5 = new ListNode(5);

        // 2 -> 6 -> 4
        // 1 -> 5
        node2.next = node6;
        node6.next = node4;

        node1.next = node5;
        ListNode intersection = solution.getIntersectionNode(node2, node1);
        if (intersection != null) {
            throw new IllegalStateException("test failed");
        }
        System.out.println("test passed: example 3 from leet code");
    }
}
