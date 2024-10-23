package algo.heap;

import assist.DataHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * a range is defined as an array of two integers, the first integer is the start point and the second integer is the end point.
 * Given a list of ranges, find the maximum number of ranges that overlap. [1, 2] and [2, 3] are not considered to be overlapping.
 * But [1, 3] and [2, 4] are overlapping. Each range is open and closed at both ends.
 * <p>
 *     Example:
 *     Input: [[1, 3], [2, 4], [3, 7], [2, 5], [5, 7]]
 *     Output: 3  == [1, 3], [2, 4], [2, 5]  or [2, 4], [3, 7], [2, 5]
 * </p>
 */
public class MaxCountOfOverlapRange {
    public int maxCountOfOverlapRange(int[][] ranges) {
        if (ranges == null || ranges.length == 0) {
            return 0;
        }
        Arrays.sort(ranges, (a, b) -> a[0] - b[0]);     // sort by start point
        PriorityQueue<Integer> heap = new PriorityQueue<>();        // min heap by end point
        heap.add(ranges[0][1]);
        int max = 1;
        for (int i = 1; i < ranges.length; i++) {
            int[] range = ranges[i];
            while (!heap.isEmpty() && range[0] >= heap.peek()) {
                heap.poll();
            }
            heap.add(range[1]);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    // time complexity: O(n), space complexity: O(n)
    public int maxCountOfOverlapRange_bruteForce(int[][] ranges) {
        if (ranges == null || ranges.length == 0) {
            return 0;
        }
        Map<Integer, Integer> counter = new HashMap<>(1024);
        for (int[] range : ranges) {
            for (int i = range[0]; i < range[1]; i++) {
                int phase = i * 10 + 1;
                counter.put(phase, counter.getOrDefault(phase, 0) + 1);
            }
        }
        int max = 0;
        for (int count : counter.values()) {
            max = Math.max(max, count);
        }
        return max;
    }

    public static void main(String[] args) {
        int[][] ranges = {{1, 3}, {2, 4}, {3, 7}, {2, 5}, {5, 7}};
        if (verify(ranges)) {
            System.out.println("Passed");
        }
        int testCycles = 100_000;
        for (int i = 0; i < testCycles; i++) {
            int[] data1 = DataHelper.genRandomSizeIntArr(200, -100, 1000);
            if (data1.length < 2) {
                continue;
            }
            int[][] ranges1 = new int[data1.length / 2][2];
            for (int j = 0; j < ranges1.length; j++) {
                int min = Math.min(data1[j], data1[ranges1.length + j]);
                int max = Math.max(data1[j], data1[ranges1.length + j]);
                if (min == max) {
                    max++;
                }
                ranges1[j][0] = min;
                ranges1[j][1] = max;
            }
            if (!verify(ranges1)) {
                return;
            }
        }
        System.out.println("Passed with large randomly generated test cases");
    }

    private static boolean verify(int[][] ranges) {
        MaxCountOfOverlapRange solution = new MaxCountOfOverlapRange();
        int bc = solution.maxCountOfOverlapRange_bruteForce(ranges);    // 3
        int sc = solution.maxCountOfOverlapRange(ranges);    // 3
        if (bc != sc) {
            System.out.println("Failed: " + bc + " != " + sc + " for " + Arrays.deepToString(ranges));
            return false;
        }
        return true;
    }
}
