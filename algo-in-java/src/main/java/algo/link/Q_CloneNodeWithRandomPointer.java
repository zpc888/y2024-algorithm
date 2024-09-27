package algo.link;

import assist.DataHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * source: https://leetcode.com/problems/copy-list-with-random-pointer/
 *
 * Giving a linked list with each node having an additional random pointer which could point to any node in the list or null.
 * Return a deep copy of the list.
 */
public class Q_CloneNodeWithRandomPointer {
    public static class Node {
		public int val;
        public Node next;
        public Node random;

		public Node(int v) {
			this.val = v;
		}

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node cursor = this;
            while (cursor != null) {
                if (!sb.isEmpty()) {
                    sb.append(" -> ");
                }
                sb.append(cursor.val);
                String randomVal = cursor.random == null ? "null" : String.valueOf(cursor.random.val);
                sb.append("[").append(randomVal).append("]");
                cursor = cursor.next;
            }
            return sb.toString();
        }
    }

    public Node clone_v1_WithMap_SpaceON(Node head) {
		if (head == null) {
			return null;
		}
		Map<Node, Node> map = new HashMap<>(32);
		Node cursor = head;
		while (cursor != null) {
            map.put(cursor, new Node(cursor.val));
			cursor = cursor.next;
		}

		cursor = head;
		while (cursor != null) {
			map.get(cursor).next = map.get(cursor.next);
			map.get(cursor).random = map.get(cursor.random);
			cursor = cursor.next;
		}
		return map.get(head);
    }

    public Node clone_v2_WithoutMap_SpaceO1(Node head) {
		if (head == null) {
			return null;
		}

		// step.1 insert all cloned nodes right-after their original nodes
		Node cursor = head;
		Node copy = null;
		while (cursor != null) {
			copy = new Node(cursor.val);
			copy.next = cursor.next;
			cursor.next = copy;
			cursor = copy.next;
		}
		
		// step.2 populate the clonded nodes' random pointers
		cursor = head;
		while (cursor != null) {
			copy = cursor.next;
			copy.random = cursor.random == null ? null : cursor.random.next;
			cursor = copy.next;
		}

		// step. 3 split into 2 links, then to return the cloned one and keep original one as-is
		cursor = head;
		Node ret = cursor.next;
		while (cursor != null) {
			copy = cursor.next;
			cursor.next = copy.next;
			cursor = copy.next;
			copy.next = cursor == null ? null : cursor.next;
		}
		return ret;
    }

    public static void main(String[] args) {
        Node[] nodes = new Node[10];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(i);
        }
        for (int i = 0; i < nodes.length; i++) {
            int num = (int) (Math.random() * (nodes.length + 6));
            nodes[i].random = num >= nodes.length ? null : nodes[num];
            if (i < nodes.length - 1) {
                nodes[i].next = nodes[i + 1];
            }
        }
        Q_CloneNodeWithRandomPointer solution = new Q_CloneNodeWithRandomPointer();
        Node original = nodes[0];
        Node clone1 = solution.clone_v1_WithMap_SpaceON(original);
        Node clone2 = solution.clone_v2_WithoutMap_SpaceO1(original);
        System.out.println("Original: " + original);
        System.out.println("Clone1  : " + clone1);
        System.out.println("Clone2  : " + clone2);
        while (original != null) {
            DataHelper.assertTrue(original.val == clone1.val && original.val == clone2.val,
                    "Value should be the same, but original: " + original.val + ", clone1: " + clone1.val + ", clone2: " + clone2.val);
            if (original.random == null) {
                DataHelper.assertTrue(clone1.random == null && clone2.random == null,
                        "Random should be null, but clone1: " + clone1.random + ", clone2: " + clone2.random);
            } else {
                DataHelper.assertTrue(original.random.val == clone1.random.val && original.random.val == clone2.random.val,
                        "Random should be the same, but original: " + original.random.val
                                + ", clone1: " + clone1.random.val + ", clone2: " + clone2.random.val + " for node: " + original.val);
                DataHelper.assertTrue(original.random != clone1.random && original.random != clone2.random,
                        "Random should be different, but they are the same for node: " + original.val);
            }
            DataHelper.assertTrue(original != clone1 && original != clone2,
                    "Instance should be different, but they are the same for node: " + original.val);
            original = original.next;
            clone1 = clone1.next;
            clone2 = clone2.next;
        }
        DataHelper.assertTrue(clone1 == null && clone2 == null, "Both clones should be null, but clone1: " + clone1 + ", clone2: " + clone2);
        System.out.println("All passed :)");
    }

}
