package algo.nowhere.subarr;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.Arrays;

/**
 * Given an array of non-negative integers, find the maximum length of a subarray with K sum.
 */
public class NonNegSumKMaxLen extends BaseSolution<Integer> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 2;
    }

    public int maxSubArrayLen(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (versionToRun == 1) {
            return process_V1_BruteForce(nums, k);
        } else if (versionToRun == 2) {
            return process_V2_SlidingWindow(nums, k);
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
//					break;                  // cannot break since the next item can be zero
				} else if (sum > k) {
					break;
				}
			}
		}
		return ret;
	}

	public int process_V2_SlidingWindow(int[] nums, int k) {
		int len = nums.length;
		int ret = 0;
		int winL = 0;
		int winR = 0;
		int winSum = nums[0];
		while (winR < len && winL < len) {
			if (winSum == k) {
				ret = Math.max(ret, winR - winL + 1);
                if (++winR >= len) {
                    break;
                }
				winSum += nums[winR];
			} else if (winSum > k) {
				winSum -= nums[winL++];
			} else {	// < k
                if (++winR >= len) {
                    break;
                }
				winSum += nums[winR];
			}
		}
		return ret;
	}

    public static void main(String[] args) {
        debug01();

        int[] nums = new int[]{1, 5, 3, 2, 1, 1, 1, 2, 1, 7};
        NonNegSumKMaxLen sol = new NonNegSumKMaxLen();
        sol.runAllVersions("Test-1", () -> sol.maxSubArrayLen(nums, 7), 5);
        sol.runAllVersions("Test-2", () -> sol.maxSubArrayLen(nums, 9), 3);

        final int cycles = 10_000;
        for (int i = 0; i < cycles; i++) {
            final int[] input = DataHelper.genRandomSizeIntArr(10_000, 0, 1_000);
            System.out.println("input: " + Arrays.toString(input));
            sol.runAllVersions("Random-" + (i + 1), () -> sol.maxSubArrayLen(input, 3_000), null);
        }

        int[] perfData = DataHelper.genFixedSizeIntArr(100_000, 0, 100);
        sol.performMeasure("performance test", () -> sol.maxSubArrayLen(perfData, 3_000));
        //====================== < performance test > Performance Report ==============
        //        Version-1: Duration: PT0.00281196S
        //        Version-2: Duration: PT0.000599452S
    }

    private static void debug01() {
        int[] nums = new int[]{2, 4, 1, 10, 3, 0, 9, 1, 8};
        NonNegSumKMaxLen sol = new NonNegSumKMaxLen();
        sol.runAllVersions("Debug-01", () -> sol.maxSubArrayLen(nums, 20), 6);
    }
}
