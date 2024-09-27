package algo.link;

public class LinkedNode {
    public int val;
    public LinkedNode next;

    public LinkedNode() {
    }

    public LinkedNode(int val) {
        this.val = val;
    }

    public LinkedNode(int val, LinkedNode next) {
        this.val = val;
        this.next = next;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof LinkedNode)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        LinkedNode cursor1 = this;
        LinkedNode cursor2 = (LinkedNode) obj;
        while (cursor1 != null && cursor2 != null) {
            if (cursor1.val != cursor2.val) {
                return false;
            }
            cursor1 = cursor1.next;
            cursor2 = cursor2.next;
        }
        return cursor1 == null && cursor2 == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        LinkedNode cursor = this;
        while (cursor != null) {
            if (!sb.isEmpty()) {
                sb.append(" -> ");
            }
            sb.append(cursor.val);
            cursor = cursor.next;
        }
        return sb.toString();
    }

    public static LinkedNode parse(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String[] parts = str.split("->");
        LinkedNode head = new LinkedNode();
        LinkedNode cursor = head;
        for (String part : parts) {
            cursor.next = new LinkedNode(Integer.parseInt(part.trim()));
            cursor = cursor.next;
        }
        return head.next;
    }
}