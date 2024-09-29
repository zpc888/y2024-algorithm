package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

import java.util.HashSet;
import java.util.Set;

/**
 * source: https://leetcode.com/problems/linked-list-cycle-ii/
 * date: 2020-09-06
 * <p>
 * Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null.
 * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously
 * following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is
 * connected to (0-indexed). It is -1 if there is no cycle. Note that pos is not passed as a parameter.
 * <p>
 * Do not modify the linked list.
 */
public class Q0142_LinkedListCycle_II {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode detectCycle(ListNode head) {
		if (head == null || head.next == null) {		// null or one node
			return null;
		}
//		return detectCycle_V1_WithMap_SpaceON(head);
		return detectCycle_V2_WithoutMap_SpaceO1(head);
    }

    @MetricsRuntime(ms = 3, beats = 14.46)
    @MetricsMemory(mb = 44.38, beats = 68.23)
	public ListNode detectCycle_V1_WithMap_SpaceON(ListNode head) {
		Set<ListNode> visited = new HashSet<>();
		ListNode cursor = head;
		while (cursor != null) {
			if (visited.contains(cursor)) {
				return cursor;
			}
			visited.add(cursor);
			cursor = cursor.next;
		}
		return null;
	}

    @MetricsRuntime(ms = 1, beats = 28.60)
    @MetricsMemory(mb = 44.43, beats = 52.10)
	public ListNode detectCycle_V2_WithoutMap_SpaceO1(ListNode head) {
		if (head.next.next == null) {		// having 2 nodes and 2nd next pointing null
			return null;
		}
		ListNode slow = head.next;
		ListNode fast = head.next.next;
		while (slow != fast) {
			if (fast.next == null || fast.next.next == null) {
				return null;
			}
			slow = slow.next;
			fast = fast.next.next;
		}
		fast = head;

		// it will meet the 1st circular node
		/*
		 * since slow and fast meets inside loop, assuming the number of nodes slow-move going through:
		 * k + s, where fast-move is: k + f, therefor k + f = 2(k + s), i.e. f = k + 2s. 
		 * Because f - s = a * loop-length = a*l where a >= 1. f - s = a*l, so a*l = k + s = k + b*l + offset
		 * so (a-b)*l = k + offset, so k = (a - b)*l - offset, so moving k node from head will meet the offset
		 * moving k node from where their meet point before.
		 */
		while (slow != fast) {
			slow = slow.next;
			fast = fast.next;
		}
		return slow;
	}

    private static Q0142_LinkedListCycle_II instance = new Q0142_LinkedListCycle_II();

	public static void main(String[] args) {
		testExample1();
		testExample2();
		testExample3();
        testExample4();
        testExample5();
	}

	private static void testExample1() {
		ListNode n1 = new ListNode(3);
		ListNode n2 = new ListNode(2);
		ListNode n3 = new ListNode(0);
		ListNode n4 = new ListNode(-4);
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n2;
		ListNode cycleStarting = instance.detectCycle(n1);
		if (cycleStarting != n2) {
			throw new IllegalStateException("failed on test example 1");
		}
	}

	private static void testExample2() {
		ListNode n1 = new ListNode(1);
		ListNode n2 = new ListNode(2);
		n1.next = n2;
		n2.next = n1;
		ListNode cycleStarting = instance.detectCycle(n1);
		if (cycleStarting != n1) {
			throw new IllegalStateException("failed on test example 2");
		}
	}

	private static void testExample3() {
		ListNode n1 = new ListNode(1);
		ListNode n2 = new ListNode(2);
		n1.next = n2;
		n2.next = n2;
		ListNode cycleStarting = instance.detectCycle(n1);
		if (cycleStarting != n2) {
			throw new IllegalStateException("failed on test example 3");
		}
	}

	private static void testExample4() {
		ListNode n1 = new ListNode(1);
		ListNode cycleStarting = instance.detectCycle(n1);
		if (cycleStarting != null) {
			throw new IllegalStateException("failed on test example 4");
		}
	}

	private static void testExample5() {
		ListNode n1 = new ListNode(1);
		n1.next = n1;
		ListNode cycleStarting = instance.detectCycle(n1);
		if (cycleStarting != n1) {
			throw new IllegalStateException("failed on test example 5");
		}
	}

}
