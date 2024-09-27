package algo.link;

import java.util.Stack;

public class Q_Parlindrome {
    public boolean isParlindrome_V1_BasicWithStack(LinkedNode root) {
		if (root == null) {
			return false;
		}
		Stack<Integer> stack = new Stack<>();
		LinkedNode curr = root;
		while (curr != null) {
			stack.push(curr.val);
			curr = curr.next;
		}
		curr = root;
		while (curr != null) {
			if (curr.val != stack.pop().intValue()) {
				return false;
			}
			curr = curr.next;
		}
		return true;
    }

	public boolean isParlindrome_V2_InPlaceChangeLinkDirection(LinkedNode root) {
		if (root == null) {
			return false;
		}
		if (root.next == null) {
			return true;	// only one node
		}
		if (root.next.next == null) {
			return root.val == root.next.val;	// only 2 nodes
		}
		LinkedNode slow = root;
		LinkedNode fast = root;
		while (fast != null && fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		// slow is the center now
		// reverse the direction from the next of the center
		LinkedNode curr = slow.next;
		slow.next = null;
		LinkedNode prev = null;
		LinkedNode next = null;
		while (curr != null && curr.next != null) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
			next = null;
		}
        if (curr != null) {
            curr.next = prev;
        }
		// curr is the new head of the second half (tail of the original link)

		prev = root;
		LinkedNode tail = curr;			// curr is the tail of the original link
		boolean isParlindrome = true;
		while (tail != null) {
			if (prev.val != tail.val) {
				isParlindrome = false;
				break;
			}
            prev = prev.next;
            tail = tail.next;
		}
		isParlindrome = isParlindrome && (prev == null || prev == slow);		// if the length is odd, slow is the center, if the length is even, prev is the center
		
		// fix the direction
		tail = curr;
		prev = null;
		while (curr != null && curr.next != null) {		// reaching the center point
			next = curr.next;
			curr.next = prev;

			prev = curr;
			curr = next;
		}
		if (curr != null) {
			curr.next = prev;
		}
		slow.next = curr;
		return isParlindrome;
	}

    public static void main(String[] args) {
        Q_Parlindrome parlindrome = new Q_Parlindrome();
        LinkedNode[] head = new LinkedNode[] {
                null,
                new LinkedNode(1),
                new LinkedNode(1, new LinkedNode(1)),
                new LinkedNode(1, new LinkedNode(1, new LinkedNode(1))),
                new LinkedNode(1, new LinkedNode(2, new LinkedNode(1))),
                new LinkedNode(1, new LinkedNode(2, new LinkedNode(2, new LinkedNode(1)))),
                new LinkedNode(1, new LinkedNode(2, new LinkedNode(3, new LinkedNode(2, new LinkedNode(1, null))))),
                new LinkedNode(1, new LinkedNode(2, new LinkedNode(3, new LinkedNode(3, new LinkedNode(2, new LinkedNode(1, null)))))),

                new LinkedNode(1, new LinkedNode(2, new LinkedNode(3, new LinkedNode(4, new LinkedNode(2, new LinkedNode(1, null)))))),
                new LinkedNode(1, new LinkedNode(2, new LinkedNode(3, new LinkedNode(3, new LinkedNode(1, null))))),
                new LinkedNode(1, new LinkedNode(2, new LinkedNode(3, new LinkedNode(2, new LinkedNode(2, null))))),
                new LinkedNode(1, new LinkedNode(2, new LinkedNode(3, new LinkedNode(1)))),
                new LinkedNode(1, new LinkedNode(2, new LinkedNode(2, new LinkedNode(7)))),
                new LinkedNode(1, new LinkedNode(2, new LinkedNode(3))),
                new LinkedNode(1, new LinkedNode(3))
        };
        boolean[] expected = new boolean[] {false, true, true, true, true, true, true, true,
                false, false, false, false, false, false, false
        };
        for (int i = 0; i < head.length; i++) {
            boolean v1 = parlindrome.isParlindrome_V1_BasicWithStack(head[i]);
            boolean v2 = parlindrome.isParlindrome_V2_InPlaceChangeLinkDirection(head[i]);
            System.out.printf("Input: %s, expected: %b, got: %b, %b%n", head[i], expected[i], v1, v2);
            if (v1 != expected[i] || v2 != expected[i]) {
                System.out.println();
                System.out.println("Test Failed !!! ");
                return;
            }
        }
        System.out.println();
        System.out.println("Test Passed !!! ");
    }
}
