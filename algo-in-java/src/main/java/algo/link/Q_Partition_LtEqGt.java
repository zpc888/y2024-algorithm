package algo.link;

import assist.DataHelper;

import java.util.Arrays;

/**
 * Giving a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
 * i.e. head: less than x -> equal to x -> greater than x
 */
public class Q_Partition_LtEqGt {
    public LinkedNode partition(LinkedNode head, int x) {
		LinkedNode ltHead = null, ltTail = null;
		LinkedNode eqHead = null, eqTail = null;
		LinkedNode gtHead = null, gtTail = null;
		LinkedNode cursor = head;
		while (cursor != null) {
			if (cursor.val < x) {
				if (ltHead == null) {
					ltHead = cursor;
					ltTail = cursor;
				} else {
					ltTail.next = cursor;
					ltTail = cursor;
				}
			} else if (cursor.val == x) {
				if (eqHead == null) {
					eqHead = cursor;
					eqTail = cursor;
				} else {
					eqTail.next = cursor;
					eqTail = cursor;
				}
			} else {		// greater than
				if (gtHead == null) {
					gtHead = cursor;
					gtTail = cursor;
				} else {
					gtTail.next = cursor;
					gtTail = cursor;
				}
			}
            cursor = cursor.next;
		}
		head = ltHead;
		LinkedNode tail = ltTail;
		if (eqHead != null) {
			if (tail == null) {
				head = eqHead;
			} else {
				tail.next = eqHead;
			}
            tail = eqTail;
		}
		if (gtHead != null) {
			if (tail == null) {
				head = gtHead;
			} else {
				tail.next = gtHead;
			}
            tail = gtTail;
		}
        if (tail != null) {
            tail.next = null;       // to avoid cycling
        }
		return head;
    }

    public static void main(String[] args) {
        Q_Partition_LtEqGt solution = new Q_Partition_LtEqGt();
        String[] inputs = new String[] {
          null, "", "1", "1 -> 2", "2 -> 1", "1 -> 2 -> 3", "3 -> 2 -> 1", "1 -> 3 -> 2", "2 -> 1 -> 3", "2 -> 3 -> 1", "3 -> 1 -> 2", "3 -> 2 -> 1",
                "1 -> 3 -> 5 -> 4 -> 2", "1 -> 2 -> 3 -> 4 -> 5", "5 -> 4 -> 3 -> 2 -> 1", "5 -> 4 -> 3 -> 2 -> 1 -> 0",
                "1 -> 1 -> 1", "2 -> 2 -> 2",
                "3 -> 3 -> 5 -> 6 -> 5 -> 4 -> 4 -> 2 -> 1"
        };
        String[] lastInputExpects = new String[] {
                "3 -> 3 -> 5 -> 6 -> 5 -> 4 -> 4 -> 2 -> 1",        // 0
                "1 -> 3 -> 3 -> 5 -> 6 -> 5 -> 4 -> 4 -> 2",        // 1
                "1 -> 2 -> 3 -> 3 -> 5 -> 6 -> 5 -> 4 -> 4",        // 2
                "2 -> 1 -> 3 -> 3 -> 5 -> 6 -> 5 -> 4 -> 4",        // 3
                "3 -> 3 -> 2 -> 1 -> 4 -> 4 -> 5 -> 6 -> 5",        // 4
                "3 -> 3 -> 4 -> 4 -> 2 -> 1 -> 5 -> 5 -> 6",        // 5
                "3 -> 3 -> 5 -> 5 -> 4 -> 4 -> 2 -> 1 -> 6",        // 6
                "3 -> 3 -> 5 -> 6 -> 5 -> 4 -> 4 -> 2 -> 1"         // 7
        };
        for (int v = 0; v < 8; v++) {
            LinkedNode[] heads = Arrays.stream(inputs).map(LinkedNode::parse).toArray(LinkedNode[]::new);
            System.out.println();
            System.out.println("================= partition based on value: " + v);
            for (int i = 0; i < heads.length; i++) {
                LinkedNode output = solution.partition(heads[i], v);
                System.out.printf("input: %s, output: %s\n", inputs[i], output);
                if (i == heads.length - 1) {
                    DataHelper.assertTrue(lastInputExpects[v].equals(output.toString()),
                            "Failed because it expects " + lastInputExpects[v]);
                }
            }
        }
    }

}
