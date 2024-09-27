package algo.link;

import assist.DataHelper;

public class Q_FindTheCenterNode {
    public LinkedNode findCenter(LinkedNode head) {
        if (head == null) {
            return null;
        }
        LinkedNode slow = head;
        LinkedNode fast = head;
        while (fast != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public LinkedNode findCenter_PreviousOne(LinkedNode head) {
        if (head == null) {
            return null;
        }
        LinkedNode slow = head;
        LinkedNode fast = head;
        LinkedNode prev = null;
        while (fast != null && fast.next != null && fast.next.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        return prev;
    }

    private static int safeGetVal(LinkedNode node) {
        return node == null ? -1 : node.val;
    }

    public static void main(String[] args) {
        Q_FindTheCenterNode q = new Q_FindTheCenterNode();
        LinkedNode[] heads = new LinkedNode[] {
                new LinkedNode(1),
                new LinkedNode(1, new LinkedNode(2)),
                new LinkedNode(1, new LinkedNode(2, new LinkedNode(3))),
                new LinkedNode(1, new LinkedNode(2, new LinkedNode(3, new LinkedNode(4)))),
                new LinkedNode(1, new LinkedNode(2, new LinkedNode(3, new LinkedNode(4, new LinkedNode(5)))))
        };
        int[] expectedCenters = {1, 1, 2, 2, 3};
        int[] expectedCenterPrevs = {-1, -1, 1, 1, 2};
        int idx = 0;
        for (LinkedNode h: heads) {
            int centerVal = safeGetVal(q.findCenter(h));
            int centerPrevVal = safeGetVal(q.findCenter_PreviousOne(h));
            System.out.println("Center node of " + h + " is " + centerVal + " and previous is: " + centerPrevVal);
            DataHelper.assertTrue(centerVal == expectedCenters[idx],
                    "Expecting center " + expectedCenters[idx] + " but got " + centerVal);
            DataHelper.assertTrue(centerPrevVal == expectedCenterPrevs[idx],
                    "Expecting center previous " + expectedCenterPrevs[idx] + " but got " + centerPrevVal);
            idx++;
        }
    }
}
