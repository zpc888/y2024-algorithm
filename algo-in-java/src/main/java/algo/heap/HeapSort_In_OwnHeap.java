package algo.heap;

import algo.sort.ArrIntSort;

public class HeapSort_In_OwnHeap implements ArrIntSort {
    @Override
    public void sort(int[] data) {
        if (data == null || data.length < 2) {
            return;
        }
        PriorityQueue_In_Array heap = new PriorityQueue_In_Array(data, true);
        for (int i = data.length - 1; i > 0; i--) {
            heap.swap(0, i);
            heap.heapsize--;
            heap.heapify(0);
        }
    }

    public static void main(String[] args) {
        int[] data = {4446, 7007, 3143, 1172, 5357};
        for (int i : data) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println();
        ArrIntSort heapSort = new HeapSort_In_OwnHeap();
//        ArrIntSort heapSort = new HeapSort_In_OwnHeap_2();
        heapSort.sort(data);
        for (int i : data) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}

