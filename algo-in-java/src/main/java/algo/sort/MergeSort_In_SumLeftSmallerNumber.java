package algo.sort;

import assist.DataHelper;

import java.util.Arrays;

public class MergeSort_In_SumLeftSmallerNumber {
    /**
     * for each element in the array, sum the left side elements are smaller than it and return the total grand sum at the end
     * e.g. [2, 6, 3, 5, 2, 8, 1] => 0 + 2 + 2 + (2 + 3) + 0 + (2 + 6 + 3 + 5 + 2) + 0 = 27
     * @param a unsorted array
     * @return sum of each element whose left side elements are smaller than it
     */
    public int solution(int[] a) {
        if (a == null || a.length == 0 || a.length == 1) {
            return 0;
        }
        int[] copy = new int[a.length];
        System.arraycopy(a, 0, copy, 0, a.length);
        return mergeSort(copy, 0, copy.length - 1);
    }

    public int bruteForce(int[] a) {
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            int leftSum = 0;
            for (int j = 0; j < i; j++) {
                if (a[j] < a[i]) {
                    leftSum += a[j];
                }
            }
            sum += leftSum;
        }
        return sum;
    }

    public int mergeSort(int[] a, int left, int right) {
        if (left >= right) {
            return 0;
        }
        int mid = left + (right - left) / 2;
        int sum = 0;
        sum += mergeSort(a, left, mid);
        sum += mergeSort(a, mid + 1, right);
        sum += merge(a, left, mid, right);
        return sum;
    }

    public int merge(int[] a, int left, int mid, int right) {
        int sum = 0;
        int[] sorted = new int[right - left + 1];
        int li = left;
        int ri = mid + 1;
        int si = 0;
        while (li <= mid && ri <= right) {
            if (a[li] < a[ri]) {
                sorted[si++] = a[li];
                sum += a[li] * (right - ri + 1);
                li++;
            } else if (a[li] == a[ri]) {
                sorted[si++] = a[ri++];
            } else {
                sorted[si++] = a[ri++];
            }
        }
        while (li <= mid) {
            sorted[si++] = a[li++];
        }
        while (ri <= right) {
            sorted[si++] = a[ri++];
        }
        System.arraycopy(sorted, 0, a, left, sorted.length);
        return sum;
    }

    public static void main(String[] args) {
        MergeSort_In_SumLeftSmallerNumber solution = new MergeSort_In_SumLeftSmallerNumber();
        System.out.println("----- merge sort -----");
        int sol = solution.solution(new int[]{2, 6, 3, 5, 2, 8, 1});
        System.out.println(sol);      // 27
        System.out.println("----- brute force -----");
        int bf = solution.bruteForce(new int[]{2, 6, 3, 5, 2, 8, 1});
        System.out.println(bf);      // 27
        if (sol != bf) {
            throw new RuntimeException("solution is wrong");
        }
        System.out.println("----- stress test -----");
        final int testCycles = 100_000;
        for (int i = 0; i < testCycles; i++) {
            int[] a = DataHelper.genRandomSizeIntArr(1_000, -1_000, 10_000);
            int s = solution.solution(a);
            int b = solution.bruteForce(a);
            if (s != b) {
                throw new RuntimeException("solution is wrong for data: " + Arrays.toString(a));
            }
        }
        System.out.println("----- performance test -----");
        int[][] arrOfArr = new int[testCycles][];
        for (int i = 0; i < testCycles; i++) {
            arrOfArr[i] = DataHelper.genRandomSizeIntArr(1_000, -1_000, 10_000);
        }
        long start = System.nanoTime();
        for (int i = 0; i < testCycles; i++) {
            solution.solution(arrOfArr[i]);
        }
        long solTime = System.nanoTime() - start;
        start = System.nanoTime();
        for (int i = 0; i < testCycles; i++) {
            solution.bruteForce(arrOfArr[i]);
        }
        long bfTime = System.nanoTime() - start;
        System.out.println("solution time: " + solTime / 1_000_000 + "ms");
        System.out.println("brute force time: " + bfTime / 1_000_000 + "ms");
    }
}
