package algo.dp;

import assist.DataHelper;

import java.util.function.BiFunction;

/**
 * Given a list of N coins, their values (V1, V2, … , VN), and a given value goal.
 * How many ways can we achieve the goal value using these coins?
 * <p/>
 * Each coin cannot be used multiple times. They will be treated different even if they have the same value.
 */
public class CoinsPossibility {
	private int versionToRun = 1;

	public int permuations(int[] coins, int goal) {
		if (coins == null || coins.length == 0 || goal <= 0) {
			return 0;
		}
		if (versionToRun == 1) {
			return process_V1_no_DP(coins, goal, 0);
		} else if (versionToRun == 2) {
			return process_V2_DP(coins, goal);
		} else {
			throw new IllegalStateException("Unsupported version: " + versionToRun);
		}
	}

	int process_V1_no_DP(int[] coins, int goal, int index) {
		if (goal < 0) {
			return 0;
		}
		int len = coins.length;
		if (index == len) {
			return goal == 0 ? 1 : 0;
		}
		return process_V1_no_DP(coins, goal - coins[index], index + 1)
			+ process_V1_no_DP(coins, goal, index + 1);
	}

	int process_V2_DP(int[] coins, int goal) {
        int len = coins.length;
        int[][] dp = new int[len + 1][goal + 1];
		dp[len][0] = 1;
		for (int i = len - 1; i >= 0; i--) {		// coins
			for (int j = 0; j <= goal; j++) {		// rest goal value
				dp[i][j] = dp[i+1][j];
				int rest = j - coins[i];
				if (rest >= 0) {
					dp[i][j] += dp[i+1][rest];
				}
			}
		}
		return dp[0][goal];
	}

	public static void main(String[] args) {
        final CoinsPossibility sol = new CoinsPossibility();
        final int versions = 2;
        BiFunction<int[], Integer, Integer> func = (coins, goal) -> {
            int[] actuals = new int[versions];
            for (int i = 1; i <= versions; i++) {
                sol.versionToRun = i;
                actuals[i-1] = sol.permuations(coins, goal);
                System.out.println("Version: " + sol.versionToRun + ", actual: " + actuals[i-1]);
            }
            for (int i = 1; i < versions; i++) {
                if (actuals[i] != actuals[i-1]) {
                    throw new IllegalStateException("V" + i + " got: " + actuals[i-1] + ", but V" + (i+1) + " got: " + actuals[i]);
                }
            }
            return actuals[0];
        };
        int[] coins = new int[]{1, 1, 2, 5, 1, 4};
        int actual = func.apply(coins, 4);
		DataHelper.assertTrue(actual == 4, "Expected: 4, but got: " + actual);

		actual = func.apply(coins, 5);
		DataHelper.assertTrue(actual == 5, "Expected: 5, but got: " + actual);

		actual = func.apply(coins, 20);
		DataHelper.assertTrue(actual == 0, "Expected: 0, but got: " + actual);

		System.out.println();
		for (int g = 0; g < 20; g++) {
			System.out.println(">> To obtain $ " + g + " value");
			actual = func.apply(coins, g);
			System.out.println("<< There is/are : " + actual + " way(s).");
			System.out.println();
		}
	}

}
