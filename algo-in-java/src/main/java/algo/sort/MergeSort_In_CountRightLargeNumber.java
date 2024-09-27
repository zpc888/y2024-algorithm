package algo.sort;

import assist.DataHelper;

import java.util.Arrays;

public class MergeSort_In_CountRightLargeNumber {
    public int count(int[] a) {
        if (a == null || a.length < 2) {
            return 0;
        }
        int[] copy = new int[a.length];
        System.arraycopy(a, 0, copy, 0, a.length);
        return mergeSort(copy, 0, copy.length - 1);
    }

    private int mergeSort(int[] a, int left, int right) {
        if (left >= right) {
            return 0;
        }
        int mid = left + (right - left) / 2;
        int count = 0;
        count += mergeSort(a, left, mid);
        count += mergeSort(a, mid + 1, right);
        count += merge(a, left, mid, right);
        return count;
    }

    private int merge(int[] a, int left, int mid, int right) {
        int count = 0;
        int[] sorted = new int[right - left + 1];
        int li = left;
        int ri = mid + 1;
        int si = 0;
        while (li <= mid && ri <= right) {
            if (a[li] < a[ri]) {
                sorted[si++] = a[li];
                count += right - ri + 1;
                li++;
            } else {        // >=
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
        return count;
    }

    private int bruteForce(int[] a) {
        int count = 0;
        for (int i = 0; i < a.length; i++) {
            int rightCount = 0;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] > a[i]) {
                    rightCount++;
                }
            }
            count += rightCount;
        }
        return count;
    }

    public static void main(String[] args) {
        MergeSort_In_CountRightLargeNumber m = new MergeSort_In_CountRightLargeNumber();
        int[] a = {1, 3, 2, 4, 5};
        System.out.println(m.count(a));     // 4 + 2 + 2 + 1 = 9
        System.out.println(m.bruteForce(a));

        System.out.println("----- test with high volume samples -----");
        int testCycles = 100_000;
        for (int i = 0; i < testCycles; i++) {
            int[] b = DataHelper.generateRandomData(1_000, -1_000, 10_000);
            if (m.count(b) != m.bruteForce(b)) {
                System.out.println("test failed on data: " + Arrays.toString(b));
                return;
            }
        }
        System.out.println("test passed with " + testCycles + " cycles");
    }
}
