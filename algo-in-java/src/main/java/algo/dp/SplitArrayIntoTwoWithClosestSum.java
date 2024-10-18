package algo.dp;

import assist.DataHelper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Given an int array, splitting it into 2 arrays, make the sum of the 2 arrays as close as possible. Return the smaller
 * sum of the 2 arrays.
 */
public class SplitArrayIntoTwoWithClosestSum {
	private int versionToRun = 1;

    public int minDiffSum(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
		if (versionToRun == 1) {
			return process_v1_no_DP(nums);
		} else if (versionToRun == 2) {
			return process_v2_no_DP(nums);
		} else if (versionToRun == 3) {
			return process_v3_DP(nums);
		} else {
			throw new IllegalStateException("Unsupported version: " + versionToRun);
		}
    }

	private int process_v1_no_DP(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        int ret = v1NoDP(nums, sum, 0, 0);
        int half = sum >> 1;
		return ret <= half ? ret : (sum - ret);
	}

	private int v1NoDP(int[] nums, int allSum, int preSum, int index) {
        int half = allSum >> 1;
        int len = nums.length;
		if (index == len) {
			return preSum;
		}
		int sum1 = v1NoDP(nums, allSum, preSum + nums[index], index + 1);
		int sum2 = v1NoDP(nums, allSum, preSum, index + 1);
		return Math.abs(sum1 - half) <= Math.abs(sum2 - half) ? sum1 : sum2;
	}

	private int process_v2_no_DP(int[] nums) {
		int sum = Arrays.stream(nums).sum();
		return v2NoDP(nums, sum/2, 0);
	}

	private int v2NoDP(int[] nums, int maxSum, int index) {
		if (index == nums.length) {
			return 0;
		}
		if (maxSum == 0) {
			return 0;
		}
		int sum1 = v2NoDP(nums, maxSum, index + 1);
		int sum2 = 0;
		if (nums[index] <= maxSum) {
			sum2 = nums[index] + v2NoDP(nums, maxSum - nums[index], index + 1);
		}
		return Math.max(sum1, sum2);
	}

	private int process_v3_DP(int[] nums) {
		int sum = Arrays.stream(nums).sum();
		int half = sum >> 1;
		int len = nums.length;
		int[][] dp = new int[len + 1][half + 1];
		for (int i = len - 1; i >= 0; i--) {
			for (int j = 0; j <= half; j++) {
				dp[i][j] = dp[i+1][j];
				if (nums[i] <= j) {
					dp[i][j] = Math.max(dp[i][j], nums[i] + dp[i+1][j-nums[i]]);
				}
			}
		}
		return dp[0][half];
	}

	public static void main(String[] args) {
		runAllVersions(new int[]{489, 946, 761, 253, 373}, 1387);
		runAllVersions(null, 0);
		runAllVersions(new int[]{}, 0);
		runAllVersions(new int[]{1}, 0);
		runAllVersions(new int[]{1, 2}, 1);
		runAllVersions(new int[]{1, 2, 2}, 2);
		runAllVersions(new int[]{1, 2, 3}, 3);
		runAllVersions(new int[]{1, 2, 3, 4}, 5);
		runAllVersions(new int[]{1, 2, 3, 4, 1}, 5);
		runAllVersions(new int[]{1, 2, 3, 4, 2}, 6);
		runAllVersions(new int[]{1, 199, 2, 3}, 6);

		verifyWithHighVolumes();
	}

	private static void verifyWithHighVolumes() {
		Random random = new Random();
		for (int run = 0; run < 200; run++) {
			runAllVersions(DataHelper.generateRandomData(16, 1, 1000), -1);
		}
		for (int run = 0; run < 200; run++) {
			// impossible to run no-DP versions with 200 elements array
			runAllVersionsExcludes(DataHelper.generateRandomData(200, 1, 6000),
					-1, 1, 2);
		}
	}

	private static int runAllVersions(int[] nums, int expected) {
		return runAllVersionsExcludes(nums, expected, new int[0]);
	}

	private static int runAllVersionsExcludes(int[] nums, int expected, int... excludes) {
		System.out.println();
		System.out.printf("Dividing array into two groups where their sum has min difference: %s\n", Arrays.toString(nums));
		if (expected >= 0) {
			System.out.println("\t\tExpected: " + expected + " is the smallest sum of the two groups");
		}
		Set<Integer> excludeSet = new HashSet<>();
		if (excludes != null) {
			for (int exclude : excludes) {
				excludeSet.add(exclude);
			}
		}
		SplitArrayIntoTwoWithClosestSum sol = new SplitArrayIntoTwoWithClosestSum();
		int versions = 3;
		int[] actuals = new int[versions];
		for (int i = 0; i < versions; i++) {
			sol.versionToRun = i + 1;
			if (excludeSet.contains(sol.versionToRun)) {
				continue;
			}
			actuals[i] = sol.minDiffSum(nums);
			System.out.printf("Version: %d, actual: %d\n", sol.versionToRun, actuals[i]);
		}
		if (expected < 0) {
			for (int i = 0; i < versions; i++) {
				if (excludeSet.contains(i + 1)) {
					continue;
				}
				expected = actuals[i];        // used the 1st run one as expectation
				break;
			}
		}
		for (int i = 0; i < versions; i++) {
			if (excludeSet.contains(i + 1)) {
				continue;
			}
			if (actuals[i] != expected) {
				throw new IllegalStateException("Expected: " + expected + ", but V" + (i+1) + " got: " + actuals[i]);
			}
		}
		return expected;
	}
}
