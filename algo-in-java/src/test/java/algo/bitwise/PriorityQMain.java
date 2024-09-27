package algo.bitwise;

import java.util.PriorityQueue;

public class PriorityQMain {
    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(5);
        pq.add(7);
        pq.add(4);
        pq.add(3);
        pq.add(0);
        pq.add(6);
        pq.add(0);
        PriorityQueue<Integer> pqDesc = new PriorityQueue<>((Integer n1, Integer n2) -> n2 - n1);
        System.out.print("Ascending order:\t");
        while (!pq.isEmpty()) {
            pqDesc.add(pq.peek());
            System.out.print(pq.poll() + " ");
        }
        System.out.println();
        System.out.print("Descending order:\t");
        while (!pqDesc.isEmpty()) {
            System.out.print(pqDesc.poll() + " ");
        }
        System.out.println();
    }
}
