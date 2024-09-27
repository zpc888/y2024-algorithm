package algo.bitwise;

import java.util.*;

/**
 * Find the element that appears k times in an array where all other elements appear exactly n times.
 * Where N > 1 and K < N.
 * <p>
 * Requirements: space complexity O(1), time complexity O(n)
 */
public class FindKTimesOccurence {
    public int findKTimesOccurence(int[] arr, int n, int k) {
//        return findKTimesOccurence01(arr, n, k);
        return findKTimesOccurence02(arr, n, k);
    }

    private int findKTimesOccurence02(int[] arr, int n, int k) {
        if (arr == null || arr.length < 2 || n < 2 || k >= n) {
            return -1;
        }
        int[] counts = new int[32];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < 32; j++) {
                counts[j] += (arr[i] >> j) & 1;
            }
        }
        int ret = 0;
        for (int i = 0; i < 32; i++) {
            if (counts[i] % n == k) {	// it can only be 0, k, n, 2n, 3n, ..., Kn, ...
//                ret += (1 << i);
                 ret |= (1 << i);         // this will be faster than the above line although both are correct
            }
        }
        return ret;
    }

    private int findKTimesOccurence01(int[] arr, int n, int k) {
        if (arr == null || arr.length < 2 || n < 2 || k >= n) {
            return -1;
        }
        int[] count = new int[32];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < 32; j++) {
                count[j] += (arr[i] >> j) & 1;
            }
        }
        int ret = 0;
        for (int i = 0; i < 32; i++) {
            if (count[i] % n != 0) {
                ret |= 1 << i;
            }
        }
        return ret;
    }

    private int hashMapWay(int[] arr, int n, int k) {
        if (arr == null || arr.length < 2 || n < 2 || k >= n) {
            return -1;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == k) {
                return entry.getKey();
            }
        }
        return -1;
    }

    private static int[] randomArray(int maxLen, int from, int to, int kTimes, int mTimes) {
        int numOfDistinct = (int)(Math.random() * maxLen);
        if (numOfDistinct == 0) {
            return new int[0];
        }
        int[] arr = new int[(numOfDistinct - 1) * mTimes + kTimes];
        Set<Integer> set = new HashSet<>();
        int num = from + (int) (Math.random() * (to - from + 1));
        set.add(num);
        int idx = 0;
        for (int i = 0; i < kTimes; i++) {
            arr[idx++] = num;
        }
        for (int i = 1; i < numOfDistinct; i++) {
            num = from + (int) (Math.random() * (to - from + 1));
            while (set.contains(num)) {
                num = from + (int) (Math.random() * (to - from + 1));
            }
            set.add(num);
            for (int j = 0; j < mTimes; j++) {
                arr[idx++] = num;
            }
        }
        return arr;
    }

    private static int[] randomArray() {
        int n1 = ((int)(Math.random() * 10) + 1);
        int n2 = ((int)(Math.random() * 10) + 1);
        int kTimes = Math.min(n1, n2);
        int mTimes = Math.max(n1, n2);
        if (kTimes == mTimes) {
            mTimes++;
        }
        return randomArray(100, -1000, 1000, kTimes, mTimes);
    }

    public static void main(String[] args) {
        FindKTimesOccurence findKTimesOccurence = new FindKTimesOccurence();
        int[] arr = {6, 8, 6, 6, 4, 4, 8, 4, 1, 1, 8, 1, 2, 2, 9, 2, 9, 3, 3, 11, 3, 5, 9, 5, 5, 11};
        System.out.println(findKTimesOccurence.findKTimesOccurence(arr, 3, 2));     // 11
        arr = new int[]{6, 8, 6, 6, 4, 4, 8, 4, 1, 1, 8, 1, 2, 2, 9, 2, 9, 3, 3, 0, 3, 5, 9, 5, 5, 0};
        System.out.println(findKTimesOccurence.findKTimesOccurence(arr, 3, 2));     // 0
        arr = new int[]{6, 8, 6, 6, 4, 4, 8, 4, 1, 1, 8, 1, 2, 2, 9, 2, 9, 3, 3, -11, 3, 5, 9, 5, 5, -11};
        System.out.println(findKTimesOccurence.findKTimesOccurence(arr, 3, 2));     // -11

        verifyRandomArray();
        testWithLargeSample();
    }

    private static void testWithLargeSample() {
        System.out.println("================================================");
        int testTimes = 1000;
        System.out.println("Test with a number of random array: " + testTimes + " times");
        FindKTimesOccurence findKTimesOccurence = new FindKTimesOccurence();
        boolean ok = true;
        for (int i = 0; i < testTimes; i++) {
            int n1 = ((int)(Math.random() * 10) + 1);
            int n2 = ((int)(Math.random() * 10) + 1);
            int kTimes = Math.min(n1, n2);
            int mTimes = Math.max(n1, n2);
            if (kTimes == mTimes) {
                mTimes++;
            }
            // maxLen should be much less than to - from + 1; otherwise it will be endless loop since all numbers are used and failed to get a new number
            int[] arr1 = randomArray(300, -100_000, 1_000_000, kTimes, mTimes);
            int result1 = findKTimesOccurence.findKTimesOccurence(arr1, kTimes, mTimes);
            int result2 = findKTimesOccurence.hashMapWay(arr1, kTimes, mTimes);
            if (result1 != result2) {
                ok = false;
                System.out.println("Different results");
                System.out.println("arr1: " + Arrays.toString(arr1));
                System.out.println("result1: " + result1);
                System.out.println("result2: " + result2);
                break;
            } else {
                System.out.print(".");
            }
        }
        System.out.println();
        if (!ok) {
            System.out.println("Failed");
        } else {
            System.out.println("Passed");
        }
    }

    private static void verifyRandomArray() {
        System.out.println("================================================");
        System.out.println("Verify random array");
        int[] arr1 = randomArray();
        int[] arr2 = randomArray();
        System.out.println("arr1: " + Arrays.toString(arr1));
        System.out.println("arr2: " + Arrays.toString(arr2));
    }
}
