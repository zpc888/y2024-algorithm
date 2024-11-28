package algo.nowhere.subarr;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers (positive, zero, negative), find the length of
 * the longest subarray with an equal number of 1s and -1s.
 */
public class PairedOneAndNegOneMaxLen extends BaseSolution<Integer> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 2;
//		return 3;			// v3 won't work for 1, -1, 0, 1 case since the first 2 already empty the stack
    }

    public int maxLen(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (versionToRun == 1) {
            return process_V1_BruteForce(arr);
        } else if (versionToRun == 2) {
            return process_V2_SumZero(arr);
        } else if (versionToRun == 3) {
            return process_V3_Stack(arr);
        } else {
            throw new RuntimeException("Not implemented for version: " + versionToRun);
        }
    }

    public int process_V1_BruteForce(int[] arr) {
        int len = arr.length;
        int ret = 0;
		for (int i = 0; i < len; i++) {
			if (arr[i] != 1 && arr[i] != -1) {
				continue;
			}
			int sum1Only = arr[i];
			for (int j = i + 1; j < len; j++) {
				if (arr[j] != 1 && arr[j] != -1) {
					continue;
				}
				sum1Only += arr[j];
				if (sum1Only == 0) {
					ret = Math.max(ret, j - i + 1);
				}
			}
		}
        return ret;
    }

    public int process_V2_SumZero(int[] arr) {
        int len = arr.length;
		Map<Integer, Integer> preSum1Only = new HashMap<>(len / 2);
		// key = the sum of 1 or -1; value = the 1st index of that sum
		preSum1Only.put(0, -1);
		Map<Integer, Integer> preNext1Only = new HashMap<>(len / 2);
        int ret = 0;
		int sum = 0;
		int pre1 = -1;
		for (int i = 0; i < len; i++) {
			if (arr[i] != 1 && arr[i] != -1) {
				continue;
			}
			sum += arr[i];
			if (preSum1Only.containsKey(sum)) {
				int preIdx = preSum1Only.get(sum);
				int nextIdx = preNext1Only.get(preIdx);
				ret = Math.max(ret, i - nextIdx + 1);
			} else {
				preSum1Only.put(sum, i);
			}
			preNext1Only.put(pre1, i);
			pre1 = i;
		}
        return ret;
    }

	// won't work for {1, -1, 0, 1, 1}
    public int process_V3_Stack(int[] arr) {
        int len = arr.length;
        int ret = 0;
		int[] stack = new int[len];
		int stackIdx = 0;		// next position; curr = idx -1; size = idx
		for (int i = 0; i < len; i++) {
			if (arr[i] != 1 && arr[i] != -1) {
				continue;
			}
			// paired 1 and -1 (not empty + stack-top paired with curr-item)
			if (stackIdx != 0 && (arr[stack[stackIdx - 1]] + arr[i] == 0)) { // peek
				ret = Math.max(ret, i - stack[--stackIdx] + 1); // pop
			} else {
				stack[stackIdx++] = i;	// push
			}
		}
        return ret;
    }

	public static void main(String[] args) {
		PairedOneAndNegOneMaxLen sol = new PairedOneAndNegOneMaxLen();
		troubleshooting02(sol);
		troubleshooting01(sol);
		int[] nums = new int[]{-1, 5, 3, 2, -1, 1, 1, 2, 1, 7};
		sol.runAllVersions("Test-1", () -> sol.maxLen(nums), 7);
		sol.runAllVersions("Test-2", () -> sol.maxLen( new int[]{5, 3, 2, -1, 1, 1, 2, 1, 7}), 2);
		sol.runAllVersions("Test-2", () -> sol.maxLen( new int[]{5, 3, 2, 1, 1, 1, 2, 1, 7}), 0);

		final int cycles = 10_000;
		for (int i = 0; i < cycles; i++) {
			final int[] input = DataHelper.genRandomSizeIntArr(10, -3, 5);
			System.out.println("input: " + Arrays.toString(input));
			sol.runAllVersions("Random-" + (i + 1), () -> sol.maxLen(input), null);
		}

		int[] perfData = DataHelper.genFixedSizeIntArr(10_000, -10, 10);
		sol.performMeasure("performance test", () -> sol.maxLen(perfData));
//		====================== < performance test > Performance Report ==============
//		Version-1: Duration: PT0.00377329S
//		Version-2: Duration: PT0.000951122S
	}

	private static void troubleshooting02(PairedOneAndNegOneMaxLen sol) {
		int[] nums = new int[]{1, -1, 0, 1, 1};
		sol.runAllVersions("troubleshooting-02-a", () -> sol.maxLen(nums), 3);
		int[] nums2 = new int[]{1, -1, 0, 1, -1};
		sol.runAllVersions("troubleshooting-02-a", () -> sol.maxLen(nums2), 5);
	}

	private static void troubleshooting01(PairedOneAndNegOneMaxLen sol) {
		int[] nums = new int[]{1, -2, 1, -1, 1};
		sol.runAllVersions("troubleshooting-01", () -> sol.maxLen(nums), 2);
	}

}
