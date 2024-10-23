package algo.link;

import assist.DataHelper;

import java.util.Arrays;

public class Q_RemoveNumXFromLink {
    public LinkedNode removeNumX(LinkedNode head, int x) {
        while (head != null && head.val == x) {
            head = head.next;
        }
        if (head == null) {
            return null;
        }
        LinkedNode prev = head;
        LinkedNode curr = head.next;
        LinkedNode next = null;
        while (curr != null) {
            next = curr.next;
            if (curr.val != x) {
                prev.next = curr;
                prev = curr;
                curr.next = null;
            }
            curr = next;
        }
        return head;
    }

    public static void main(String[] args) {
        Q_RemoveNumXFromLink removeNumXFromLink = new Q_RemoveNumXFromLink();
        for (int i = 0; i < 6; i++) {
            LinkedNode head = new LinkedNode(1, new LinkedNode(2, new LinkedNode(3, new LinkedNode(4, new LinkedNode(5, null)))));
            System.out.println("Trying to remove number value: " + i + " from the link list: " + head);
            LinkedNode ret = removeNumXFromLink.removeNumX(head, i);
            System.out.println("Result is: " + ret);
            System.out.println("================================================");
        }
        for (int i = 0; i < 4; i++) {
            LinkedNode head = new LinkedNode(1, new LinkedNode(1, new LinkedNode(2, new LinkedNode(2, new LinkedNode(3, new LinkedNode(3, null))))));
            System.out.println("Trying to remove number value: " + i + " from the link list: " + head);
            LinkedNode ret = removeNumXFromLink.removeNumX(head, i);
            System.out.println("Result is: " + ret);
            System.out.println("================================================");
        }

        testWithHighVolume();
    }

    private static void testWithHighVolume() {
        System.out.println("================================================");
        System.out.println("Test with a number of random array: 1000 times");
        Q_RemoveNumXFromLink removeNumXFromLink = new Q_RemoveNumXFromLink();
        for (int i = 0; i < 1000; i++) {
            int[] arr = DataHelper.genRandomSizeIntArr(100, -10000, 10000);
            if (arr.length == 0) {
                continue;
            }
            LinkedNode head = new LinkedNode(arr[0]);
            LinkedNode curr = head;
            for (int j = 1; j < arr.length; j++) {
                curr.next = new LinkedNode(arr[j]);
                curr = curr.next;
            }
            int idxToRemove = (int)(Math.random() * arr.length);
            int removedVal = arr[idxToRemove];
//            System.out.println("Trying to remove number value: " + removedVal + " from the link list: " + head);
            LinkedNode newHead = removeNumXFromLink.removeNumX(head, removedVal);
            int idx = 0;
            boolean emptyAfterRemove = true;
            while (newHead != null) {
                emptyAfterRemove = false;
                int vFromArr = arr[idx++];
                while (vFromArr == removedVal && idx < arr.length) {
                    vFromArr = arr[idx++];
                }
                if (vFromArr != newHead.val) {
                    System.out.println("Fail on array value: " + vFromArr + " vs link value: " + newHead.val);
                    return;
                }
                newHead = newHead.next;
            }
            if (!emptyAfterRemove) {
                while (idx < arr.length) {
                    if (arr[idx] != removedVal) {
                        System.out.println("Fail on length: " + idx + " vs " + arr.length);
                        return;
                    }
                    idx++;
                }
                if (idx != arr.length) {
                    System.out.println("Fail on length: " + idx + " vs " + arr.length);
                    return;
                }
            } else {
                for (int j = 0; j < arr.length; j++) {
                    if (arr[j] != removedVal) {
                        System.out.println("Fail on fixed value link list: " + Arrays.toString(arr) + " where to remove number: " + removedVal);
                        return;
                    }
                }
            }
            System.out.print(".");
        }
        System.out.println();
        System.out.println("Passed 1000 tests");
    }
}
