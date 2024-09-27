package algo.heap;

import assist.DataHelper;

import java.util.Arrays;
import java.util.PriorityQueue;

public class TestHeap {
    public static void main(String[] args) {
        int[] data = {3, 2, 1, 4, 5};
        testPriorityQueue(data);
        int testCycles = 100_000;
        for (int i = 0; i < testCycles; i++) {
            int[] data1 = DataHelper.generateRandomData(100, -100, 1000);
            if (data1.length == 0) {
                i--;
                continue;
            }
            testPriorityQueue(data1);
        }
    }

    private static void testPriorityQueue(int data[]) {
        PriorityQueue_In_Array minHeap1 = new PriorityQueue_In_Array(data.length, false);
        PriorityQueue_In_Array maxHeap1 = new PriorityQueue_In_Array(data.length, true);
        PriorityQueue<Integer> minHeap2 = new PriorityQueue<Integer>(data.length);
        PriorityQueue<Integer> maxHeap2 = new PriorityQueue<Integer>(data.length, (a, b) -> b - a);
        for (int i : data) {
            minHeap1.add(i);
            maxHeap1.add(i);
            minHeap2.add(i);
            maxHeap2.add(i);
        }
        int[] data1 = new int[data.length];
        int[] data2 = new int[data.length];
        System.arraycopy(data, 0, data1, 0, data.length);
        System.arraycopy(data, 0, data2, 0, data.length);
        PriorityQueue_In_Array minHeap3 = new PriorityQueue_In_Array(data1, false);
        PriorityQueue_In_Array maxHeap3 = new PriorityQueue_In_Array(data2, true);
        for (int i = 0; i < data.length; i++) {
            int min1 = minHeap1.poll();
            int min2 = minHeap2.poll();
            int min3 = minHeap3.poll();
            int max1 = maxHeap1.poll();
            int max2 = maxHeap2.poll();
            int max3 = maxHeap3.poll();
            if (min1 != min2 || min1 != min3) {
                System.out.printf("FAILED: min1=%d, min2=%d, min3=%d with data=%s%n", min1, min2, min3, Arrays.toString(data));
                return;
            }
            if (max1 != max2 || max1 != max3) {
                System.out.printf("FAILED: max1=%d, max2=%d, max3=%d with data=%s%n", max1, max2, max3, Arrays.toString(data));
                return;
            }
        }
    }
}
