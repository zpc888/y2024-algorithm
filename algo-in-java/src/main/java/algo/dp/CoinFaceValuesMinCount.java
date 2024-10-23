package algo.dp;

import assist.DataHelper;

import java.util.Arrays;
import java.util.Random;

/**
 * Given a list of N coin face values, their values (V1, V2, … , VN), and a given value goal.
 * Each coin can be used multiple times and has unlimited supply. Find the minimum number of coins needed
 * to achieve the goal value.
 */
public class CoinFaceValuesMinCount {
    private int versionToRun = 1;

    public int minCount(int[] coins, int goal) {
        if (coins == null || coins.length == 0 || goal <= 0) {
            return 0;
        }
        if (versionToRun == 1) {
            return process_V1_no_DP(coins, goal, 0);
        } else if (versionToRun == 2) {
            return process_V2_DP(coins, goal);
		} else if (versionToRun == 3) {
			return process_V3_DP_optimized(coins, goal);
        } else {
            throw new IllegalStateException("Unsupported version: " + versionToRun);
        }
    }

	private int process_V1_no_DP(int[] coins, int goal, int index) {
		int len = coins.length;
		if (index == len) {
			return goal == 0 ? 0 : Integer.MAX_VALUE;
		}
		int allBest = Integer.MAX_VALUE;
		int faceValue = coins[index];
		for (int times = 0; times * faceValue <= goal; times++) {
			int nextBest = process_V1_no_DP(coins, goal - times * faceValue, index + 1);
			if (nextBest != Integer.MAX_VALUE) {	// has solution
				allBest = Math.min(allBest, times + nextBest);
			}
		}
		return allBest;
	}

	private int process_V2_DP(int[] coins, int goal) {
		int len = coins.length;
		int dp[][] = new int[len+1][goal+1];
		dp[len][0] = 0;
		for (int i = 1; i <= goal; i++) {
			dp[len][i] = Integer.MAX_VALUE;
		}
		for (int i = len - 1; i >= 0; i--) {
			int faceValue = coins[i];
			for (int j = 0; j <= goal; j++) {
				int allBest = Integer.MAX_VALUE;
				for (int times = 0; times * faceValue <= j; times++) {
					int nextBest = dp[i + 1][j - times * faceValue];
					if (nextBest != Integer.MAX_VALUE) {	// has solution
						allBest = Math.min(allBest, times + nextBest);
					}
				}
				dp[i][j] = allBest;
			}
		}
		return dp[0][goal];
	}

	private int process_V3_DP_optimized(int[] coins, int goal) {
		int len = coins.length;
		int dp[][] = new int[len+1][goal+1];
		dp[len][0] = 0;
		for (int i = 1; i <= goal; i++) {
			dp[len][i] = Integer.MAX_VALUE;
		}
		for (int i = len - 1; i >= 0; i--) {
			int faceValue = coins[i];
			for (int j = 0; j <= goal; j++) {

				// dp[i][j] = min(dp[i][j-faceValue] + 1, dp[i-1][j])
				// in this optimization, we don't need to check for all times of faceValue -- O(n) to O(1)
				dp[i][j] = dp[i+1][j];
				int prev = j - faceValue;
				if (prev >= 0 && dp[i][prev] != Integer.MAX_VALUE) {
					dp[i][j] = Math.min(dp[i][j], dp[i][prev] + 1);
				}

			}
		}
		return dp[0][goal];
	}

	public static void main(String[] args) {
		runAllVersions(new int[]{1, 2, 5}, 11, 3);
		runAllVersions(new int[]{10, 3, 5}, 12, 4);
		runAllVersions(new int[]{10, 2, 4, 6, 8}, 7, Integer.MAX_VALUE);

		verifyWithHighVolumes();
	}

	private static void verifyWithHighVolumes() {
		Random random = new Random();
		for (int run = 0; run < 10_000; run++) {
			int[] coins = DataHelper.genRandomSizeIntArr(8, 1, 20);
			int goal = random.nextInt(50) + 2;
			runAllVersions(coins, goal, -1);
		}
	}

	private static int runAllVersions(int[] coins, int goal, int expected) {
		System.out.println();
		System.out.printf("Coins: %s, Goal: %d\n", Arrays.toString(coins), goal);
		if (expected >= 0) {
			System.out.println("\t\tExpected: " + expected);
		}
		CoinFaceValuesMinCount sol = new CoinFaceValuesMinCount();
		int versions = 3;
		int[] actuals = new int[versions];
		for (int i = 0; i < versions; i++) {
			sol.versionToRun = i + 1;
			actuals[i] = sol.minCount(coins, goal);
			System.out.printf("Version: %d, actual: %d\n", sol.versionToRun, actuals[i]);
		}
		if (expected < 0) {
			expected = actuals[0];		// used the 1st one as expection
		}
		for (int i = 0; i < versions; i++) {
			if (actuals[i] != expected) {
				throw new IllegalStateException("Expected: " + expected + ", but V" + (i+1) + " got: " + actuals[i]);
			}
		}
		return actuals[0];
	}
}
