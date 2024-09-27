package algo.heap;

import algo.sort.ArrIntSort;

public class HeapSort_In_OwnHeap_2 implements ArrIntSort {
    @Override
    public void sort(int[] data) {
        if (data == null || data.length < 2) {
            return;
        }
        PriorityQueue_In_Array heap = new PriorityQueue_In_Array(data.length, true);
        for (int i = 0; i < data.length; i++) {
            heap.add(data[i]);
        }
        for (int i = data.length - 1; i > 0; i--) {
            heap.swap(0, i);
            heap.heapsize--;
            heap.heapify(0);
        }
        System.arraycopy(heap.arr, 0, data, 0, data.length);
    }
}

