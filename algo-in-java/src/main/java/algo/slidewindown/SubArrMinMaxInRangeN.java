package algo.slidewindown;


import assist.BaseSolution;
import assist.DataHelper;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class SubArrMinMaxInRangeN extends BaseSolution<Integer> {

    @Override
    protected int getNumberOfVersionsImplemented() {
        return 3;
    }

    public int numOfSubArrs(int[] arr, int maxDiffGap) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (versionToRun == 1) {
            return process_v1_no_slide_window(arr, maxDiffGap);
        } else if (versionToRun == 2) {
            return process_v2_no_slide_window(arr, maxDiffGap);
        } else if (versionToRun == 3) {
            return process_v3_slide_window(arr, maxDiffGap);
        } else {
            throw new IllegalStateException("Unsupported version: " + versionToRun);
        }
    }

    private int process_v1_no_slide_window(int[] arr, int maxDiffGap) {
        int len = arr.length;
		int ret = 0;
		for (int i = 0; i < len; i++) {
			for (int j = i; j < len; j++) {
				int min = arr[i];
				int max = arr[i];
				for (int k = i + 1; k <= j; k++) {
					min = Math.min(min, arr[k]);
					max = Math.max(max, arr[k]);
				}
				if (max - min <= maxDiffGap) {
					ret++;
				}
			}

		}
        return ret;
	}

    private int process_v2_no_slide_window(int[] arr, int maxDiffGap) {
        int len = arr.length;
		int ret = 0;
		for (int i = 0; i < len; i++) {
			int min = arr[i];
			int max = arr[i];
			for (int j = i; j < len; j++) {
				min = Math.min(min, arr[j]);
				max = Math.max(max, arr[j]);
				if (max - min <= maxDiffGap) {
					ret++;
				} else {
					break;
				}
			}

		}
        return ret;
    }


    private int process_v3_slide_window(int[] arr, int maxDiffGap) {
        int len = arr.length;
        int winBgn = 0;  // sliding window left
        int winEnd = 0;  // sliding window right
		int ret = 0;
		LinkedList<Integer> maxSW = new LinkedList<Integer>();
		LinkedList<Integer> minSW = new LinkedList<Integer>();
		while (winBgn < len) {
			int val = arr[winEnd];
			while (!maxSW.isEmpty() && arr[maxSW.peekLast()] <= val) {
				maxSW.pollLast();
			}
			maxSW.offerLast(winEnd);
			while (!minSW.isEmpty() && arr[minSW.peekLast()] >= val) {
				minSW.pollLast();
			}
			minSW.offerLast(winEnd);
			while (arr[maxSW.peekFirst()] - arr[minSW.peekFirst()] > maxDiffGap) {
				int n = winEnd - winBgn;
				ret += n;
				if (maxSW.peekFirst() == winBgn) {
					maxSW.pollFirst();
				}
				if (minSW.peekFirst() == winBgn) {
					minSW.pollFirst();
				}
				winBgn++;
			}
			winEnd++;
			if (winEnd == len) {
				int n = winEnd - winBgn;
				ret += n * (n + 1) / 2;
				break;
			}
		}
		return ret;
    }

    public static void main(String[] args) {
        SubArrMinMaxInRangeN sol = new SubArrMinMaxInRangeN();
        sol.runAllVersions("Example 1", () -> sol.numOfSubArrs(new int[]{53, 100, 12, 91}, 48), 5);
        int runCycles = 10_000;
        Random random = new Random();
        for (int i = 0; i < runCycles; i++) {
            int[] arr = DataHelper.generateRandomData(100, 1, 10000);
            int n = random.nextInt(500);
            sol.runAllVersions("Random " + (i + 1) + " with n=" + n + " and arr=" + Arrays.toString(arr),
                    () -> sol.numOfSubArrs(arr, n),
                    null);
        }
    }

}
