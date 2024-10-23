package algo.sort;

import assist.DataHelper;
import assist.TestHelper;

import java.time.Duration;

public class MergeSort_In_CountTwiceStillLtRightNumber {
    // 1, 4, 3, 2, 5  => 0
    // 1, 5, 6, 3, 1, 2 => 5 has 1, 2; 6 has 1, 2 and 3 has 1; so total = 2 + 2 + 1 = 5

    public int solution(int[] a) {
        return mergeSort(a, 0, a.length - 1);
    }

    private int mergeSort(int[] a, int l, int r) {
        if (l >= r) {
            return 0;
        }
        int m = l + (r - l) / 2;
        int count = 0;
        count += mergeSort(a, l, m);
        count += mergeSort(a, m + 1, r);
        count += merge(a, l, m, r);
        return count;
    }

    private int merge(int[] a, int l, int m, int r) {
        int count = doCounting(a, l, m, r);
        int[] s = new int[r - l + 1];
        int li = l;
        int ri = m + 1;
        int i = 0;
        while (li <= m && ri <= r) {
            s[i++] = a[li] < a[ri] ? a[li++] : a[ri++];
        }
        while (li <= m) {
            s[i++] = a[li++];
        }
        while (ri <= r) {
            s[i++] = a[ri++];
        }
        System.arraycopy(s, 0, a, l, s.length);
        return count;
    }

    private int doCounting(int[] a, int l, int m, int r) {
        int li = l;
        int ri = m + 1;
        int count = 0;
        while (li <= m && ri <= r) {
            if (a[li] > (2 * a[ri])) {
                ri++;
            } else {
                count += ri - m - 1;
                li++;
            }
        }
        if (ri > r) {
            count += (m - li + 1) * (ri - m - 1);
        }
        return count;
    }

    public int bruteForce(int[] a) {
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > 2 * a[j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        MergeSort_In_CountTwiceStillLtRightNumber counter = new MergeSort_In_CountTwiceStillLtRightNumber();
        System.out.println(counter.solution(new int[]{1, 4, 3, 2, 5}));     // 0
        System.out.println(counter.solution(new int[]{1, 5, 6, 3, 1, 2}));  // 5
        System.out.println(counter.solution(new int[]{1, 6, 4, 9, 8, 1, 1, 2, 3, 3, 4}));  // 16

        System.out.println("------- high volume test -------");
        int testCycles = 100000;
        for (int i = 0; i < testCycles; i++) {
            int[] a = DataHelper.genRandomSizeIntArr(1000, -1000, 1000000);
            int r1 = counter.bruteForce(a);
            int r2 = counter.solution(a);
            if (r1 != r2) {
                System.out.println("Failed!!! Mismatched: " + r1 + " != " + r2);
                return;
            }
        }
        System.out.println("Test passed with large data set");

        System.out.println("------- performance test -------");
        int[][] data = new int[testCycles][];
        for (int i = 0; i < testCycles; i++) {
            data[i] = DataHelper.genRandomSizeIntArr(1000, -1000, 1000000);
        }
        Duration bf = TestHelper.test(data, counter::bruteForce);
        Duration sol = TestHelper.test(data, counter::solution);
        System.out.println("Brute force: " + bf.toMillis() + " ms");
        System.out.println("Solution : " + sol.toMillis() + " ms");
    }
}
