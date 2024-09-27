package algo.sort;

import algo.heap.HeapSort_In_JavaPriorityQueue;
import algo.heap.HeapSort_In_OwnHeap;
import algo.heap.HeapSort_In_OwnHeap_2;
import assist.DataHelper;

import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestingAllSorts {
    private static int maxSize = 10000;
    private static int min = 0;
    private static int max = 10000;
    private static int numberOfTestCycles = 10_000;

    private static ArrIntSort[] sorts = new ArrIntSort[]{
            new SelectionSort(), new BubbleSort(), new QuickSort(), new QuickSort2(), new QuickSort_In_NoRecursive()
            , new MergeSort(), new InsertionSort(), new HeapSort_In_OwnHeap()
            , new HeapSort_In_OwnHeap_2(), new HeapSort_In_JavaPriorityQueue()
            , new RadixSort_BasicVersion(), new RadixSort_EnhancedVersion()
    };

    public static void main(String[] args) {
        System.out.println();
        System.out.println("============== Starting Accuracy Test ==============");
        if (!testAccuracy()) {
            return;
        }
        System.out.println();
        System.out.println("============== Starting Performance Test ==============");
        testPerformance();
        System.out.println();
    }

    private static boolean testAccuracy() {
        int[][] copies = new int[sorts.length][];
        boolean succeed = true;
        outer: for (int i = 0; i < numberOfTestCycles; i++) {
            int[] data = DataHelper.generateRandomData(maxSize, min, max);
            for (int j = 0; j < copies.length; j++) {
                copies[j] = DataHelper.copyArray(data);
                sorts[j].sort(copies[j]);
                if (!DataHelper.isAscending(copies[j])) {
                    System.out.println("Error: ");
                    System.out.println("input: " + Arrays.toString(data));
                    System.out.println(sorts[j].getClass().getSimpleName() + " output: " + Arrays.toString(copies[j]));
                    succeed = false;
                    break outer;
                }
                if (j > 0 && !DataHelper.isArrayEqual(copies[j], copies[j - 1])) {
                    System.out.println("Error: ");
                    System.out.println("input: " + Arrays.toString(data));
                    System.out.println(sorts[j].getClass().getSimpleName() + " output: " + Arrays.toString(copies[j]));
                    System.out.println(sorts[j - 1].getClass().getSimpleName() + " output: " + Arrays.toString(copies[j - 1]));
                    succeed = false;
                    break outer;
                }
            }
        }
        if (succeed) {
            System.out.println("Accuracy test passed");
        } else {
            System.out.println("Accuracy test failed");
        }
        return succeed;
    }

    private static void testPerformance() {
        int[][] dataList = new int[numberOfTestCycles][];
        int size = 0;
        for (int i = 0; i < numberOfTestCycles; i++) {
            dataList[i] = DataHelper.generateRandomData(maxSize, min, max);
            size += dataList[i].length;
        }
        System.out.printf("Average array size: %s with max size can be: %s by running %s cycles%n"
                , size / numberOfTestCycles, maxSize, numberOfTestCycles);
        Map<String, Long> map = new LinkedHashMap<>(sorts.length);
        long min = Long.MAX_VALUE;
        for (ArrIntSort sort : sorts) {
            long start = System.nanoTime();
            for (int i = 0; i < numberOfTestCycles; i++) {
                sort.sort(DataHelper.copyArray(dataList[i]));
            }
            long time = System.nanoTime() - start;
            Duration duration = Duration.ofNanos(time/ numberOfTestCycles);
            map.put(sort.getClass().getSimpleName(), duration.toNanos());
            min = Math.min(min, duration.toNanos());
        }
        System.out.printf("%40s : %10s : TIMES (BASE = 1)%n", "Sort Name", "Avg Nanos");
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            double times = 1.0d * entry.getValue() / min;
            System.out.printf("%40s : %10s : %5.2f %4s%n", entry.getKey(), entry.getValue(),
                    times, times == 1.0 ? "BASE ==>> FASTEST" : "SLOW");
        }
    }
}
