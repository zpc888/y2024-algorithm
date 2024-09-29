package leetcode;

public class Q0160Plus_IntersectionOfTwoPossibleCircularLinkedLists {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /*
     * based on https://leetcode.com/problems/intersection-of-two-linked-lists/ , here 2 lists can have circular loop.
     * <p />
     * There are 4 possible cases:
     * <ul>
     *     <li>Case 1: both lists have no loop</li>
     *     <li>Case 2: A has loop, but B has no loop</li>
     *     <li>Case 3: A has no loop, but B has loop</li>
     *     <li>Case 4: both lists have loop</li>
     * </ul>
     * <p />
     * For case 1, it is the same as the original problem from leetcode Q0160_IntersectionOfTwoLinkedLists.java
     * For case 2 and case 3, there is no intersection at all.
     * For case 4, there are 3 sub-cases:
     * <ul>
     *     <li>sub-case 1: both lists have the same loop with the same intersection where the intersection node is/is not in the loop.</li>
     *     <li>sub-case 2: both lists have different loops, but have the intersection</li>
     *     <li>sub-case 3: both lists have different loops, but have no intersection</li>
     * </ul>
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
		ListNode circleA = getCircle(headA);
		ListNode circleB = getCircle(headB);
		if ((circleA == null) ^ (circleB == null)) {
			return null;
		} 
		if (circleA == null) {		// both have no circle
			return solveForBothHaveNoCircles(headA, headB);
		} else {                	// both have circle
			if (circleA == circleB) {	// have intersection for sure
				return findIntersectionForHavingSameCircle(headA, headB, circleA);
			} else {					// diff circle, may or may not have intersection
				// if having, both circleA or circleB will the intersection nodes
				ListNode cursorA = circleA.next;
				while (cursorA != circleA) {
					if (cursorA == circleB) {	// circle-a/b has intersection
						return circleA;
					}
					cursorA = cursorA.next;
				}
				// circle-a has no intersection with circle-b, for sure, no intersaction
				return null;
			}
		}
    }

	private ListNode findIntersectionForHavingSameCircle(ListNode headA, ListNode headB, ListNode circle) {
		int lenA = getLengthTo(headA, circle);
		int lenB = getLengthTo(headB, circle);
		if (lenA > lenB) {
			headA = moveSteps(headA, lenA - lenB);
		} else if (lenA < lenB) {
			headB = moveSteps(headB, lenB - lenA);
		}
		while (headA != headB) {
			headA = headA.next;
			headB = headB.next;
		}
		return headA;
	}

	private ListNode moveSteps(ListNode head, int numOfSteps) {
		for (int i = 0; i < numOfSteps; i++) {
			head = head.next;
		}
		return head;
	}

	private ListNode solveForBothHaveNoCircles(ListNode headA, ListNode headB) {
		Object[] aLenAndTail = getLengthAndTailNode(headA);
		int aLen = ((Integer)aLenAndTail[0]).intValue();
		ListNode aTail = (ListNode)aLenAndTail[1];
		Object[] bLenAndTail = getLengthAndTailNode(headB);
		int bLen = ((Integer)bLenAndTail[0]).intValue();
		ListNode bTail = (ListNode)bLenAndTail[1];
		if (aTail != bTail) {
			return null;
		}
		// for sure both have intersection
		ListNode cursorA = headA;
		ListNode cursorB = headB;
		if (aLen >= bLen) {
			cursorA = move(cursorA, aLen - bLen);
		} else {	// bList longer
			cursorB = move(cursorB, bLen - aLen);
		}
		while (cursorA != cursorB) {
			cursorA = cursorA.next;
			cursorB = cursorB.next;
		}
		return cursorA;
	}

	private ListNode move(ListNode cursor, int steps) {
		for (int i = 0; i < steps; i++) {
			cursor = cursor.next;
		}
		return cursor;
	}

	private Object[] getLengthAndTailNode(ListNode head) {
		ListNode tail = head;
		int len = 1;
		while (tail.next != null) {
			len++;
			tail = tail.next;
		}
		return new Object[]{len, tail};
	}

	private int getLengthTo(ListNode from, ListNode to) {
		ListNode cursor = from;
		int len = 1;
		while (cursor != to) {
			len++;
			cursor = cursor.next;
		}
		return len;
	}

	private ListNode getCircle(ListNode head) {
		if (head == null || head.next == null || head.next.next == null) {
			return null;		// 0, 1, 2 nodes, no circle
		}
		ListNode slow = head.next;
		ListNode fast = head.next.next;
		while (slow != fast) {
			if (fast.next == null || fast.next.next == null) {
				return null;				// no circle
			}
			slow = slow.next;
			fast = fast.next.next;
		}
		// has circle for sure
		// slow-steps = a + i*l + o		where a is the length of nodes who have no circle 
		//                                    i is how many times looping through the circle >= 0
		//                                    l is the length of circle
		//                                    o is the length of offset from circle 1st node 
		// fast-steps = a + j*l + o     where j > 2i
		//
		// so a + j*l + o = 2 * (a + i*l + o) 
		// a = (j - 2*i) * l - o        
		// Since slow is at o position in the circular nodes, after a times move, they will meet
		// at the 1st node of the circle
		//
		fast = head;
		while (slow != fast) {
			slow = slow.next;
			fast = fast.next;
		}
		return slow;
	}

	private boolean hasCircularLoop(ListNode head) {
		if (head == null || head.next == null || head.next.next == null) { //0, 1, 2 nodes
			return false;
		}
		ListNode slow = head.next;
		ListNode fast = head.next.next;
		while (slow != fast) {
			if (fast.next == null || fast.next.next == null) {
				return false;
			}
			slow = slow.next;
			fast = fast.next.next;
		}
		return true;
	}

    public static void main(String[] args) {
        Q0160_IntersectionOfTwoLinkedLists.main(args);

		testExample_HavingSameCircle_01();
		testExample_HavingDiffCircle_02_plus_with_without_circle();
    }

	private static void testExample_HavingSameCircle_01() {
		/*
		   1 -> 2 -> 3 -> 4 -> 5 -> 6
		           /       \        /
	              8         \--<<--/
		 */
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
		ListNode node4 = new ListNode(4);
		ListNode node5 = new ListNode(5);
		ListNode node6 = new ListNode(6);
		ListNode node8 = new ListNode(8);

		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		node6.next = node4;

		node8.next = node3;

		Q0160Plus_IntersectionOfTwoPossibleCircularLinkedLists sol = new Q0160Plus_IntersectionOfTwoPossibleCircularLinkedLists();
		ListNode intersectionNode = sol.getIntersectionNode(node1, node8);
		final String s1 = "scenario #1 - having the same circle with the intersection node is outside the circle: ";
		if (intersectionNode != node3) {
			throw new IllegalStateException(s1 + "test failed");
		}
		System.out.println(s1 + "test passed");

		node8.next = node4;
		intersectionNode = sol.getIntersectionNode(node1, node8);
		final String s2 = "scenario #2 - having the same circle with the intersection node is in the circle: ";
		if (intersectionNode != node4) {
			throw new IllegalStateException(s2 + "test failed");
		}
		System.out.println(s2 + "test passed");
	}

	private static void testExample_HavingDiffCircle_02_plus_with_without_circle() {
		/*
		                   8     or ---------> 9 --> 10
		                    \                  \-<<-/
		                      \
		   1 -> 2 -> 3 -> 4 -> 5 -> 6
		                   \        /
	                        \--<<--/
		 */
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
		ListNode node4 = new ListNode(4);
		ListNode node5 = new ListNode(5);
		ListNode node6 = new ListNode(6);
		ListNode node8 = new ListNode(8);

		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		node6.next = node4;

		node8.next = node5;

		Q0160Plus_IntersectionOfTwoPossibleCircularLinkedLists sol = new Q0160Plus_IntersectionOfTwoPossibleCircularLinkedLists();
		ListNode intersectionNode = sol.getIntersectionNode(node1, node8);
		final String s3 = "scenario #3 - having the diff circle with the diff intersection nodes from a/b list nodes: ";
		if (intersectionNode != node4 && intersectionNode != node5) {
			throw new IllegalStateException(s3 + " test failed");
		}
		System.out.println(s3 + " test passed");

		ListNode node9 = new ListNode(9);
		ListNode node10 = new ListNode(10);
		node8.next = node9;
		node9.next = node10;
		node10.next = node9;
		intersectionNode = sol.getIntersectionNode(node1, node8);
		final String s4 = "scenario #4 - having the diff circle without the intersection nodes: ";
		if (intersectionNode != null) {
			throw new IllegalStateException(s4 + " test failed");
		}
		System.out.println(s4 + " test passed");

		// node8 has no circle now
		node10.next = null;
		intersectionNode = sol.getIntersectionNode(node1, node8);
		final String s5 = "scenario #5 - one has circle, but the other has no circle: ";
		if (intersectionNode != null) {
			throw new IllegalStateException(s5 + " test failed");
		}
		System.out.println(s5 + " test passed");
	}

}
