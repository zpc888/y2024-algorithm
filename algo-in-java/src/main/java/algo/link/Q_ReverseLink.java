package algo.link;

import assist.DataHelper;

import java.util.Arrays;

public class Q_ReverseLink {

    public LinkedNode reverseSingleLink(LinkedNode head) {
        LinkedNode ret = null;
        LinkedNode curr = head;
        LinkedNode next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = ret;
            ret = curr;
            curr = next;
        }
        return ret;
    }

    public DoubleLinkedNode reverseDoubleLink(DoubleLinkedNode head) {
        DoubleLinkedNode ret = null;
        DoubleLinkedNode next = null;
        while (head != null) {
            next = head.next;
            head.next = ret;
            if (ret != null) {
                ret.prev = head;
            }
            ret = head;
            ret.prev = null;
            head = next;
        }
        return ret;
    }

    public DoubleLinkedNode reverseDoubleLink_GenByAI(DoubleLinkedNode head) {
        DoubleLinkedNode ret = null;
        DoubleLinkedNode curr = head;
        DoubleLinkedNode next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = ret;
            if (ret != null) {
                ret.prev = curr;
            }
            ret = curr;
            ret.prev = null;
            curr = next;
        }
        return ret;
    }

    public static void main(String[] args) {
        Q_ReverseLink reverseLink = new Q_ReverseLink();
        LinkedNode head = new LinkedNode(1, new LinkedNode(2, new LinkedNode(3, new LinkedNode(4, new LinkedNode(5, null)))));
        System.out.println(head);
        LinkedNode ret = reverseLink.reverseSingleLink(head);
        System.out.println(ret);
        System.out.println();
        head = new LinkedNode(1, new LinkedNode(2, null));
        System.out.println(head);
        ret = reverseLink.reverseSingleLink(head);
        System.out.println(ret);
        System.out.println();
        head = new LinkedNode(1, null);
        System.out.println(head);
        ret = reverseLink.reverseSingleLink(head);
        System.out.println(ret);
        System.out.println();
        head = null;
        System.out.println(head);
        ret = reverseLink.reverseSingleLink(head);
        System.out.println(ret);
        System.out.println();

        testDoubleLinkReversalManually();

        testHighVolumeInSingleLinkReversal();
        testHighVolumeInDoubleLinkReversal();

    }

    private static void testDoubleLinkReversalManually() {
        int[] arr = new int[]{1, 2, 3};
        DoubleLinkedNode head = new DoubleLinkedNode(arr[0]);
        DoubleLinkedNode cursor = head;
        for (int j = 1; j < arr.length; j++) {
            cursor.next = new DoubleLinkedNode(arr[j]);
            cursor.next.prev = cursor;
            cursor = cursor.next;
        }
        DoubleLinkedNode newHead = new Q_ReverseLink().reverseDoubleLink(head);
        System.out.println("head forwards:");
        DoubleLinkedNode tail = null;
        while (newHead != null) {
            System.out.print(newHead.val + " ");
            newHead = newHead.next;
            if (newHead != null) {
                tail = newHead;
            }
        }
        System.out.println();
        System.out.println("tail backwards:");
        while (tail != null) {
            System.out.print(tail.val + " ");
            tail = tail.prev;
        }
        System.out.println();
    }

    private static void testHighVolumeInDoubleLinkReversal() {
        Q_ReverseLink reverseLink = new Q_ReverseLink();
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = DataHelper.generateRandomData(100, -1000, 1000);
            if (arr.length == 0) {
                continue;
            }
            DoubleLinkedNode head = new DoubleLinkedNode(arr[0]);
            DoubleLinkedNode cursor = head;
            for (int j = 1; j < arr.length; j++) {
                cursor.next = new DoubleLinkedNode(arr[j]);
                cursor.next.prev = cursor;
                cursor = cursor.next;
            }
            // cursor is tail now
            int idx = arr.length;
            while (cursor != null) {
                if (cursor.val != arr[--idx]) {
                    System.out.println("Fail on previous pointer in double linked list");
                    return;
                } else {
                    cursor = cursor.prev;
                }
            }
            if (idx != 0) {
                System.out.println("Fail on size check for previous pointer");
                return;
            }
            DoubleLinkedNode newHead = reverseLink.reverseDoubleLink(head);
//            DoubleLinkedNode newHead = reverseLink.reverseDoubleLink_GenByAI(head);
            idx = arr.length;
            while (newHead != null) {
                if (newHead.val != arr[--idx]) {
                    System.out.println("Fail");
                    return;
                }
                if (newHead.prev != null) {
                    if (newHead.prev.val != arr[idx + 1]) {
                        System.out.println("Fail on previous pointer in double linked list after reversing at index " + idx + " with int[] \n\t" + Arrays.toString(arr));
                        return;
                    }
                }
                newHead = newHead.next;
            }
            if (idx != 0) {
                System.out.println("Fail");
                return;
            } else {
                System.out.print(".");
            }
        }
        System.out.println("\nPassed " + testTimes + " tests in double linked list reversal");
    }

    private static void testHighVolumeInSingleLinkReversal() {
        Q_ReverseLink reverseLink = new Q_ReverseLink();
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = DataHelper.generateRandomData(100, -1000, 1000);
            if (arr.length == 0) {
                continue;
            }
            LinkedNode head = new LinkedNode(arr[0]);
            LinkedNode cursor = head;
            for (int j = 1; j < arr.length; j++) {
                cursor.next = new LinkedNode(arr[j]);
                cursor = cursor.next;
            }
            LinkedNode newHead = reverseLink.reverseSingleLink(head);
            int idx = arr.length;
            while (newHead != null) {
                if (newHead.val != arr[--idx]) {
                    System.out.println("Fail");
                    return;
                }
                newHead = newHead.next;
            }
            if (idx != 0) {
                System.out.println("Fail");
                return;
            } else {
                System.out.print(".");
            }
        }
        System.out.println("\nPassed " + testTimes + " tests");
    }
}
