package algo.slidewindow;

import assist.DataHelper;
import assist.BaseSolution;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * Given an array of integers, find the maximum number in every n numbers.
 * <pre>
 *     Example: arr = {1, 3, -1, -3, 5, 3, 6, 7}, n = 3, return {3, 3, 5, 5, 6, 7}
 *     Example: arr = {4, 3,  5,  4, 3, 3, 6, 7}, n = 3, return {5, 5, 5, 4, 6, 7}
 * </pre>
 */
public class MaxNumEveryN extends BaseSolution<int[]> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 2;
    }

    public int[] solution(int[] arr, int n) {
        if (arr == null || arr.length == 0 || n < 1 || arr.length < n) {
            return new int[0];
        }
        if (versionToRun == 1) {
            return process_v1_no_slide_window(arr, n);
        } else if (versionToRun == 2) {
            return process_v2_slide_window(arr, n);
        } else {
            throw new IllegalStateException("Unsupported version: " + versionToRun);
        }
    }

    private int[] process_v1_no_slide_window(int[] arr, int n) {
		int len = arr.length;
		int[] ret = new int[len - n + 1];
		for (int i = n - 1; i < len; i++) {
			int max = arr[i];
			for (int j = i - n + 1; j < i; j++) {
				max = Math.max(max, arr[j]);
			}
			ret[i - n + 1] = max;
		}
		return ret;
    }

    private int[] process_v2_slide_window(int[] arr, int n) {
        int len = arr.length;
        int[] ret = new int[len - n + 1];
        LinkedList<Integer> deque = new LinkedList<>();
		int idx = 0;
		for (int i = 0; i < len; i++) {
			int val = arr[i];
			while (!deque.isEmpty() && arr[deque.peekLast()] <= val) {
				deque.pollLast();
			}
			deque.offerLast(val);
			if (deque.peekFirst() == i - n) {
				deque.pollFirst();
			}
			if (i >= n -1) {
				ret[idx++] = arr[deque.peekFirst()];
			}
		}
		return ret;
    }

    @Override
    protected String resultStringifier(int[] result) {
        return result == null ? null : Arrays.toString(result);
    }

    @Override
    protected boolean resultEquals(int[] expected, int[] actual) {
        return Arrays.equals(expected, actual);
    }

    public static void main(String[] args) {
        MaxNumEveryN sol = new MaxNumEveryN();
        sol.runAllVersions("Example 1",
                () -> sol.solution(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3),
                new int[]{3, 3, 5, 5, 6, 7});
        sol.runAllVersions("Example 2",
                () -> sol.solution(new int[]{4, 3, 5, 4, 3, 3, 6, 7}, 3),
                new int[]{5, 5, 5, 4, 6, 7});

        verifyWithHighVolumes();

        final int runCycles = 100;
        final int[][] testData = new int[runCycles][];
        final int[] nData = new int[runCycles];
        final Random random = new Random();
        for (int i = 0; i < runCycles; i++) {
            testData[i] = DataHelper.generateRandomData(10000, -100, 10000);
            nData[i] = random.nextInt(100);
        }
        sol.performMeasure("Run " + runCycles + " cycles with random data", () -> {
            for (int i = 0; i < runCycles; i++) {
                sol.solution(testData[i], nData[i]);
            }
            return null;
        });
//================================ performance Report ======== 100 cycles random max 10000 elements with n max 100
//        Version-1: Duration: PT0.026609325S
//        Version-2: Duration: PT0.017587566S
    }

    private static void verifyWithHighVolumes() {
        MaxNumEveryN sol = new MaxNumEveryN();
//		sol.silent = true;
        Random random = new Random();
        for (int run = 0; run < 100; run++) {
            int[] arr = DataHelper.generateRandomData(random.nextInt(100), -100, 100);
            int n = random.nextInt(12);
            sol.runAllVersions("Random #" + (run+1) + ": n=" + n + ", arr=" + Arrays.toString(arr),
                    () -> sol.solution(arr, n), null);
        }
    }

}
