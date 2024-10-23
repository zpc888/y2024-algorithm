package algo.dp;

import assist.DataHelper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Given an int array, splitting it into 2 arrays, make the number of the 2 arrays as close as possible and
 * at the same time, make their sum as closest as possible too. Return the smaller sum of the 2 arrays.
 */
public class SplitArrayIntoTwoWithClosestItemNumberAndSum {
	private int versionToRun = 1;

    public int solution(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
		if (versionToRun == 1) {
			return process_v1_no_DP(nums);
		} else if (versionToRun == 2) {
			return process_v2_DP(nums);
		} else {
			throw new IllegalStateException("Unsupported version: " + versionToRun);
		}
    }

	private int process_v1_no_DP(int[] nums) {
        int sum = Arrays.stream(nums).sum();
		int halfSum = sum >> 1;
		int halfNum = (nums.length + 1) >> 1;
        return v1NoDP(nums, halfSum, halfNum, 0);
	}

	private int v1NoDP(int[] nums, int maxSum, int maxNum, int index) {
		if (maxNum == 0) {
			return 0;
		}
        int len = nums.length;
		if (index == len) {
			return 0;
		}
		int sum1 = v1NoDP(nums, maxSum, maxNum, index + 1);
		int sum2 = 0;
		if (nums[index] <= maxSum) {
			sum2 = nums[index] + v1NoDP(nums, maxSum - nums[index], maxNum - 1, index + 1);
		}
		return Math.max(sum1, sum2);
	}

	private int process_v2_DP(int[] nums) {
		int len = nums.length;
		int sum = Arrays.stream(nums).sum();
		int halfSum = sum >> 1;
		int halfNum = (len + 1) >> 1;
		int[][][] dp = new int[len + 1][halfNum + 1][halfSum + 1];
		for (int i = len -1; i >= 0; i--) {
			for (int j = 1; j <= halfNum; j++) {
				for (int k = 1; k <= halfSum; k++) {
					int sum1 = dp[i + 1][j][k];
					int sum2 = 0;
					if (nums[i] <= k) {
						sum2 = nums[i] + dp[i + 1][j - 1][k - nums[i]];
					}
					dp[i][j][k] = Math.max(sum1, sum2);
				}
			}
		}
		return dp[0][halfNum][halfSum];
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
		runAllVersions(new int[]{1, 199}, 1);
		runAllVersions(new int[]{1, 199, 2, 3}, 5);
		runAllVersions(new int[]{1, 199, 2, 3, 4}, 9);
		runAllVersions(new int[]{1, 199, 2, 3, 4, 5}, 12);

		verifyWithHighVolumes();
	}

	private static boolean silent = false;
	private static void verifyWithHighVolumes() {
//		silent = true;
		Random random = new Random();
		for (int run = 0; run < 500; run++) {
			runAllVersions(DataHelper.genRandomSizeIntArr(16, 1, 1000), -1);
		}
		for (int run = 0; run < 10; run++) {
			// impossible to run no-DP versions with 200 elements array
			runAllVersionsExcludes(DataHelper.genRandomSizeIntArr(100, 1, 1000),
					-1, 1);
		}
	}

	private static int runAllVersions(int[] nums, int expected) {
		return runAllVersionsExcludes(nums, expected, new int[0]);
	}

	private static int runAllVersionsExcludes(int[] nums, int expected, int... excludes) {
		if (!silent) {
			System.out.println();
			System.out.printf("Dividing array into two groups where their sum has min difference: %s\n", Arrays.toString(nums));
			if (expected >= 0) {
				System.out.println("\t\tExpected: " + expected + " is the smallest sum of the two groups");
			}
		}
		Set<Integer> excludeSet = new HashSet<>();
		if (excludes != null) {
			for (int exclude : excludes) {
				excludeSet.add(exclude);
			}
		}
		SplitArrayIntoTwoWithClosestItemNumberAndSum sol = new SplitArrayIntoTwoWithClosestItemNumberAndSum();
		int versions = 2;
		int[] actuals = new int[versions];
		for (int i = 0; i < versions; i++) {
			sol.versionToRun = i + 1;
			if (excludeSet.contains(sol.versionToRun)) {
				continue;
			}
			actuals[i] = sol.solution(nums);
			if (!silent) {
				System.out.printf("Version: %d, actual: %d\n", sol.versionToRun, actuals[i]);
			}
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
