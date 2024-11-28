package algo.nowhere.subarr;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers (positive, zero, negative), find the maximum length of a subarray with K sum.
 */
public class PossibleNegSumKMaxLen extends BaseSolution<Integer> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 2;
    }

    // since the following items can be negative, sliding-window is not applicable since the window cannot be expanded
    // by moving the right pointer to the right, nor be shrunk by moving the left pointer to the right.
    public int maxSubArrayLen(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (versionToRun == 1) {
            return process_V1_BruteForce(nums, k);
        } else if (versionToRun == 2) {
            return process_V2_PreSum(nums, k);
        } else {
            throw new RuntimeException("The version is not implemented yet: v" + versionToRun);
        }
    }

    public int process_V1_BruteForce(int[] nums, int k) {
        int ret = 0;
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            int sum = 0;
            for (int j = i; j < len; j++) {
                sum += nums[j];
                if (sum == k) {
                    ret = Math.max(ret, j - i + 1);
                }
            }
        }
        return ret;
    }

    public int process_V2_PreSum(int[] nums, int k) {
        int len = nums.length;
        int ret = 0;
		// key = preSum, value = the first index for that preSum
		Map<Integer, Integer> firstPreSumIdx = new HashMap<Integer, Integer>();
		int sum = 0;
		firstPreSumIdx.put(sum, -1);
		for (int i = 0; i < len; i++) {
			sum += nums[i];
			int expectSum = sum - k;
			if (firstPreSumIdx.containsKey(expectSum)) {
				// find sum = k when ending index i
				int preIdx = firstPreSumIdx.get(expectSum);
				ret = Math.max(ret, i - preIdx);
			}
			// not matter findi / not find sum = k when ending index i
			if (!firstPreSumIdx.containsKey(sum)) {
				firstPreSumIdx.put(sum, i);
			}
		}

        return ret;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 5, 3, 2, 1, 1, 1, 2, 1, 7};
        PossibleNegSumKMaxLen sol = new PossibleNegSumKMaxLen();
        sol.runAllVersions("Test-1", () -> sol.maxSubArrayLen(nums, 7), 5);
        sol.runAllVersions("Test-2", () -> sol.maxSubArrayLen(nums, 9), 3);

        final int cycles = 10_000;
        for (int i = 0; i < cycles; i++) {
            final int[] input = DataHelper.genRandomSizeIntArr(5_000, -1_000, 1_000);
            System.out.println("input: " + Arrays.toString(input));
            sol.runAllVersions("Random-" + (i + 1), () -> sol.maxSubArrayLen(input, 800), null);
        }

        int[] perfData = DataHelper.genFixedSizeIntArr(10_000, -1_000, 1_000);
        sol.performMeasure("performance test", () -> sol.maxSubArrayLen(perfData, 2_000));
//        ====================== < performance test > Performance Report ==============
//        Version-1: Duration: PT0.012152312S
//        Version-2: Duration: PT0.000598581S
    }
}
