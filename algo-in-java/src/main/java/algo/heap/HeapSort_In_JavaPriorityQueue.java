package algo.heap;

import algo.sort.ArrIntSort;

import java.util.PriorityQueue;

public class HeapSort_In_JavaPriorityQueue implements ArrIntSort {
    @Override
    public void sort(int[] data) {
        if (data == null || data.length < 2) {
            return;
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>();        // default is min heap
        for (int i = 0; i < data.length; i++) {
            heap.add(data[i]);
        }
        for (int i = 0; i < data.length; i++) {
            data[i] = heap.poll();
        }
    }
}

