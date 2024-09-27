package algo.link;

import assist.DataHelper;

import java.util.Arrays;

public class Q_ReArrange {
    /**
     * Given a linked node to rearrange first -> center+1 -> 2nd -> center + 2 -> ... -> center -> end.
     * <pre>
     *     example 1:
     *      input: 1
     *      output: 1
     *     example 2:
     *      input: 1 -> 2
     *      output: 1 -> 2
     *     example 3:
     *      input: 1 -> 2 -> 3
     *      output: 1 -> 3 -> 2
     *     example 4:
     *      input: 1 -> 2 -> 3 -> 4
     *      output: 1 -> 3 -> 2 -> 4
     *     example 5:
     *      input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10
     *      output: 1 -> 6 -> 2 -> 7 -> 3 -> 8 -> 4 -> 9 -> 5 -> 10
     * </pre>
     */
    public LinkedNode rearrange_fromStartThenCenterMarchingToEnd(LinkedNode head) {
		if (head == null || head.next == null || head.next.next == null) {	// null, 1 node or 2 nodes
			return head;
		}
		LinkedNode center = locateCenterNode(head);
		LinkedNode half1 = head;
		LinkedNode half2 = center.next;
		center.next = null;
		LinkedNode next1 = null;
		LinkedNode next2 = null;
		while (half2 != null) {
			next1 = half1.next;
			next2 = half2.next;
			half1.next = half2;
			half2.next = next1;
			half1 = next1;
			half2 = next2;
		}
		return head;
    }

	private LinkedNode locateCenterNode(LinkedNode head) {
		LinkedNode slow = head;
		LinkedNode fast = head;
		while (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;	// center now
	}

    /**
     * Given a linked node to rearrange first -> last -> 2nd -> last 2nd -> ... -> center.
     * <pre>
     *     example 1:
     *      input: 1
     *      output: 1
     *     example 2:
     *      input: 1 -> 2
     *      output: 1 -> 2
     *     example 3:
     *      input: 1 -> 2 -> 3
     *      output: 1 -> 3 -> 2
     *     example 4:
     *      input: 1 -> 2 -> 3 -> 4
     *      output: 1 -> 4 -> 2 -> 3
     *     example 5:
     *      input: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10
     *      output: 1 -> 10 -> 2 -> 9 -> 3 -> 8 -> 4 -> 7 -> 5 -> 6
     * </pre>
     */
    public LinkedNode rearrange_fromBothEndsToCenter(LinkedNode head) {
		if (head == null || head.next == null || head.next.next == null) {	// null, 1 node or 2 nodes
			return head;
		}
		LinkedNode center = locateCenterNode(head);
		LinkedNode half1 = head;
		LinkedNode half2 = center.next;
		center.next = null;
		half2 = flipDirection(half2);
		LinkedNode next1 = null;
		LinkedNode next2 = null;
		while (half2 != null) {
			next1 = half1.next;
			next2 = half2.next;
			half1.next = half2;
			half2.next = next1;
			half1 = next1;
			half2 = next2;
		}
		return head;
    }

	private LinkedNode flipDirection(LinkedNode head) {
		LinkedNode prev = null;
		LinkedNode curr = head;
		LinkedNode next = null;
		while (curr != null) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		return prev;
	}

    public static void main(String[] args) {
        Q_ReArrange reArrange = new Q_ReArrange();
        String[] inputs = new String[] {
                "1",
                "1 -> 2",
                "1 -> 2 -> 3",
                "1 -> 2 -> 3 -> 4",
                "1 -> 2 -> 3 -> 4 -> 5",
                "1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10"
        };
        LinkedNode[] heads = Arrays.stream(inputs).map(LinkedNode::parse).toArray(LinkedNode[]::new);
        String[] expects = new String[]{
          "1",
          "1 -> 2",
          "1 -> 3 -> 2",
          "1 -> 3 -> 2 -> 4",
          "1 -> 4 -> 2 -> 5 -> 3",
          "1 -> 6 -> 2 -> 7 -> 3 -> 8 -> 4 -> 9 -> 5 -> 10"
        };
        for (int idx = 0; idx < heads.length; idx++) {
            LinkedNode h = heads[idx];
            LinkedNode ret = reArrange.rearrange_fromStartThenCenterMarchingToEnd(h);
            System.out.printf("%36s  =>  %36s%n", inputs[idx], ret);
            DataHelper.assertTrue(expects[idx].equals(ret.toString()), "Expecting " + expects[idx] + " but got " + ret);
        }
        System.out.println("----------- from start then center marching to end OK -----------");
        System.out.println();

        heads = Arrays.stream(inputs).map(LinkedNode::parse).toArray(LinkedNode[]::new);
        expects = new String[]{
                "1",
                "1 -> 2",
                "1 -> 3 -> 2",
                "1 -> 4 -> 2 -> 3",
                "1 -> 5 -> 2 -> 4 -> 3",
                "1 -> 10 -> 2 -> 9 -> 3 -> 8 -> 4 -> 7 -> 5 -> 6"
        };
        for (int idx = 0; idx < heads.length; idx++) {
            LinkedNode h = heads[idx];
            LinkedNode ret = reArrange.rearrange_fromBothEndsToCenter(h);
            System.out.printf("%36s  =>  %36s%n", inputs[idx], ret);
            DataHelper.assertTrue(expects[idx].equals(ret.toString()), "Expecting " + expects[idx] + " but got " + ret);
        }
        System.out.println("----------- from both ends OK -----------");
    }
}
