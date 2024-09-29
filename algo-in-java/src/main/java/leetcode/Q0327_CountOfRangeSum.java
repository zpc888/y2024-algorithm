package leetcode;

import assist.DataHelper;
import assist.PerfTest;

import java.time.Duration;
import java.util.Arrays;

/**
 * https://leetcode.com/problems/count-of-range-sum/
 *
 * <p>
 * Given an integer array nums and two integers lower and upper, return the number of range sums that lie in [lower, upper] inclusive.
 * </p>
 * <p>
 * Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j inclusive, where i <= j.
 * </p>
 * <p>
 *     <b>Example 1:</b>
 *     <pre>
 *         Input: nums = [-2,5,-1], lower = -2, upper = 2
 *         Output: 3
 *         Explanation: The three ranges are : [0,0], [2,2], [0,2] and their respective sums are: -2, -1, 2.
 *     </pre>
 *     <b>Example 2:</b>
 *     <pre>
 *         Input: nums = [0], lower = 0, upper = 0
 *         Output: 1
 *         Explanation: The only range is [0,0] and its sum is 0.
 *     </pre>
 *     <b>Constraints:</b>
 *     <ul>
 *         <li>1 <= nums.length <= 10^4</li>
 *         <li>-2^31 <= nums[i] <= 2^31 - 1</li>
 *         <li>-10^5 <= lower <= upper <= 10^5</li>
 *         <li>The answer is guaranteed to fit in a 32-bit integer.</li>
 *     </ul>
 * </p>
 *
 */
public class Q0327_CountOfRangeSum {

    public int countRangeSum(int[] nums, int lower, int upper) {
        return countRangeSum_in_mergeSort(nums, lower, upper);
    }

    private int countRangeSum_in_mergeSort(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long[] prefixSum = new long[nums.length];
        prefixSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i];
        }
        return mergeSort(prefixSum, 0, prefixSum.length - 1, lower, upper);
    }

    private int mergeSort(long[] prefixSum, int l, int r, int lower, int upper) {
        if (l >= r) {
            if (prefixSum[l] >= lower && prefixSum[l] <= upper) {
                return 1;
            } else {
                return 0;
            }
        }
        int m = l + ((r - l) >> 1);
        return mergeSort(prefixSum, l, m, lower, upper)
                + mergeSort(prefixSum, m + 1, r, lower, upper)
                + merge(prefixSum, l, m, r, lower, upper);
    }

    private int merge(long[] prefixSum, int l, int m, int r, int lower, int upper) {
        int count = 0;
        int winL = l;
        int winR = l;
        for (int ri = m + 1; ri <= r; ri++) {
            long min = prefixSum[ri] - upper;
            long max = prefixSum[ri] - lower;
            while (prefixSum[winL] < min && winL <= m) {
                winL++;
            }
            while (prefixSum[winR] <= max && winR <= m) {
                winR++;
            }
            count += (winR - winL);
        }

        long[] copy = new long[r - l + 1];
        int li = l;
        int ri = m + 1;
        int i = 0;
        while (li <= m && ri <= r) {
            copy[i++] = prefixSum[li] < prefixSum[ri] ? prefixSum[li++] : prefixSum[ri++];
        }
        while (li <= m) {
            copy[i++] = prefixSum[li++];
        }
        while (ri <= r) {
            copy[i++] = prefixSum[ri++];
        }
        System.arraycopy(copy, 0, prefixSum, l, copy.length);
        return count;
    }

    public int countRangeSum_in_bruteForce(int[] nums, int lower, int upper) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            long sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum >= lower && sum <= upper) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Q0327_CountOfRangeSum q = new Q0327_CountOfRangeSum();
        int cnt1 = 0;
        int cnt2 = 0;
        int[] nums = new int[]{-2147483647,0,-2147483647,2147483647};
        cnt1 = q.countRangeSum_in_bruteForce(nums, -564, 3864);
        cnt2 = q.countRangeSum(nums, -564, 3864);
        if (cnt1 != cnt2) {         // 4
            System.out.println(Arrays.toString(nums) + " test failed: " + cnt1 + " != " + cnt2);
            return;
        }


        cnt1 = q.countRangeSum(new int[]{-52, 36, -19, -95, 58}, -20, 20);
        cnt2 = q.countRangeSum_in_bruteForce(new int[]{-52, 36, -19, -95, 58}, -20, 20);
        if (cnt1 != cnt2) {         // 4
            System.out.println("test failed: " + cnt1 + " != " + cnt2);
            return;
        }
        cnt1 = q.countRangeSum(new int[]{36, -56, -10, 5, -86, 31, -83, -66}, -20, 20);
        cnt2 = q.countRangeSum_in_bruteForce(new int[]{36, -56, -10, 5, -86, 31, -83, -66}, -20, 20);
        if (cnt1 != cnt2) {         // 4
            System.out.println("test 2 failed");
            return;
        }
        System.out.println(q.countRangeSum(new int[]{-2, 5, -1}, -2, 2)); // 3
        System.out.println(q.countRangeSum(new int[]{0}, 0, 0)); // 1
        System.out.println(q.countRangeSum_in_bruteForce(new int[]{-2, 5, -1}, -2, 2)); // 3
        System.out.println(q.countRangeSum_in_bruteForce(new int[]{0}, 0, 0)); // 1

        int testCycle = 10000;
        int maxLen = 2000;
        int[][] inputData = new int[testCycle][];
        final int lower = -20;
        final int upper = 20;
        System.out.println("========== test in large scale ==========");
        for (int i = 0; i < testCycle; i++) {
            inputData[i] = DataHelper.generateRandomData(maxLen, -100, 100);
            int c1 = q.countRangeSum_in_bruteForce(inputData[i], lower, upper);
            int c2 = q.countRangeSum(inputData[i], lower, upper);
            if (c1 != c2) {
                System.out.println("large scale test failed");
                System.out.println("input: " + Arrays.toString(inputData[i]));
                System.out.println("expected: " + c1);
                System.out.println("returned: " + c2);
                return;
            }
        }
        System.out.println("large scale test passed");
        System.out.println();

        System.out.println("========== performance in large scale ==========");
        // when < 20, brute force is twice faster than merge sort
        // when ~ 200, they are similar
        // but when > 1000, merge sort is much faster than brute force.
        // // when length = 2000, Brute force PT4.9 vs merge sort PT0.99, i.e. 5 times faster
        Duration d1 = PerfTest.test(inputData, arr -> q.countRangeSum_in_bruteForce(arr, lower, upper));
        Duration d2 = PerfTest.test(inputData, arr -> q.countRangeSum(arr, lower, upper));
        System.out.println("brute force: " + d1);
        System.out.println("merge sort:  " + d2);
    }
}
