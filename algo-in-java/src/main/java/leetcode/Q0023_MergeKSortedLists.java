package leetcode;

import java.util.PriorityQueue;

// https://leetcode.com/problems/merge-k-sorted-lists/description/
public class Q0023_MergeKSortedLists {
    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        ListNode[] lists = new ListNode[3];
        lists[0] = new ListNode(1, new ListNode(4, new ListNode(5)));
        lists[1] = new ListNode(1, new ListNode(3, new ListNode(4)));
        lists[2] = new ListNode(2, new ListNode(6));
        ListNode result = new Q0023_MergeKSortedLists().mergeKLists(lists);
        StringBuilder sb = new StringBuilder();
        while (result != null) {
            if (!sb.isEmpty()) {
                sb.append(" -> ");
            }
            sb.append(result.val);
            result = result.next;
        }
        System.out.println("Actual result: " + sb);
        if ("1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6".contentEquals(sb)) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }
    }


    // ========================================================================
    public ListNode mergeKLists(ListNode[] lists) {
//        return mergeInPriorityQueue(lists);               // Runtime 4ms   beats 73.58%   Memory 44.51 MB Beats 30.72%
//        return mergeInManualCompare(lists);               // Runtime 169ms beats 5.10%    Memory 44.12 MB Beats 83.40%
//        return mergeInPriorityQueueWithoutNewNode(lists); // Runtime 4ms   beats 73.58%   Memory 44.45 MB Beats 40.73%
        return mergeIn2ListsSequentially(lists);            // Runtime 67ms  beats 16.63%   Memory 45.82 MB Beats 5.39%
    }

    private ListNode mergeInManualCompare(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        } else if (lists.length == 1) {
            return lists[0];
        } else {
            ListNode ret = null;
            ListNode cursor = null;
            int exhaustedListCount = 0;
            while (true) {
                int minIdx = findMinValueIndex(lists);
                if (minIdx == -1) {
                    break;
                }
                if (ret == null) {
                    cursor = new ListNode(lists[minIdx].val);
                    ret = cursor;
                } else {
                    if (exhaustedListCount == lists.length - 1) {
                        cursor.next = lists[minIdx];
                        break;
                    } else {
                        cursor.next = new ListNode(lists[minIdx].val);
                        cursor = cursor.next;
                    }
                }
                lists[minIdx] = lists[minIdx].next;
                if (lists[minIdx] == null) {
                    exhaustedListCount++;
                }
            }
            return ret;
        }
    }

    private int findMinValueIndex(ListNode[] lists) {
        int minIndex = -1;
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                if (minIndex == -1 || lists[i].val < lists[minIndex].val) {
                    minIndex = i;
                }
            }
        }
        return minIndex;
    }

    private ListNode mergeInPriorityQueue(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((ListNode n1, ListNode n2) -> n1.val - n2.val);
        for (ListNode list : lists) {
            if (list != null) {
                pq.add(list);
            }
        }
        ListNode ret = null;
        ListNode cursor = null;
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            if (ret == null) {
                cursor = new ListNode(node.val);
                ret = cursor;
            } else {
                cursor.next = new ListNode(node.val);
                cursor = cursor.next;
            }
            if (node.next != null) {
                pq.add(node.next);
            }
        }
        return ret;
    }

    private ListNode mergeInPriorityQueueWithoutNewNode(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((ListNode n1, ListNode n2) -> n1.val - n2.val);
        for (ListNode list : lists) {
            if (list != null) {
                pq.add(list);
            }
        }
        if (pq.isEmpty()) {
            return null;
        }
        ListNode ret = pq.poll();
        ListNode cursor = ret;
        if (cursor.next != null) {
            pq.add(cursor.next);
        }
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            cursor.next = node;
            if (node.next != null) {
                pq.add(node.next);
            }
            cursor = cursor.next;
        }
        return ret;
    }

    private ListNode mergeIn2ListsSequentially(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        ListNode ret = lists[0];
        for (int i = 1; i < lists.length; i++) {
            ret = merge2Lists(ret, lists[i]);
        }
        return ret;
    }

    private ListNode merge2Lists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode ret = l1.val <= l2.val ? new ListNode(l1.val) : new ListNode(l2.val);
        ListNode cursor = ret;
        if (l1.val <= l2.val) {
            l1 = l1.next;
        } else {
            l2 = l2.next;
        }
        while (l1 != null || l2 != null) {
            if (l1 == null) {
                cursor.next = l2;
                break;
            } else if (l2 == null) {
                cursor.next = l1;
                break;
            } else {
                if (l1.val <= l2.val) {
                    cursor.next = new ListNode(l1.val);
                    l1 = l1.next;
                } else {
                    cursor.next = new ListNode(l2.val);
                    l2 = l2.next;
                }
                cursor = cursor.next;
            }
        }
        return ret;
    }

}
