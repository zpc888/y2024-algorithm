package algo.dp;

import assist.DataHelper;

import java.util.function.BiFunction;

/**
 * Given a list of N coin face values, their values (V1, V2, … , VN), and a given value goal.
 * How many ways can we achieve the goal value using these coins?
 * Each coin can be used multiple times and has unlimited supply.
 */
public class CoinFaceValuesPossibility {
    private int versionToRun = 1;

    public int permuations(int[] coins, int goal) {
        if (coins == null || coins.length == 0 || goal <= 0) {
            return 0;
        }
        if (versionToRun == 1) {
            return process_V1_no_DP(coins, goal, 0);
        } else if (versionToRun == 2) {
            return process_V2_DP(coins, goal);
        } else if (versionToRun == 3) {
            return process_V3_DP_Aggressive(coins, goal);
        } else {
            throw new IllegalStateException("Unsupported version: " + versionToRun);
        }
    }

    private int process_V3_DP_Aggressive(int[] coins, int goal) {
        int len = coins.length;
        int[][] dp = new int[len + 1][goal + 1];
        dp[len][0] = 1;
        for (int i = len - 1; i >= 0; i--) {         // coins
			dp[i][0] = dp[i+1][0];
            for (int j = 1; j <= goal; j++) {        // rest goal value
                dp[i][j] = dp[i+1][j];
                if (j >= coins[i]) {
                    dp[i][j] += dp[i][j - coins[i]];	 // OPTIMIZE loop to constant
                }
            }
        }
		return dp[0][goal];

    }

    private int process_V2_DP(int[] coins, int goal) {
        int len = coins.length;
        int[][] dp = new int[len + 1][goal + 1];
        dp[len][0] = 1;
        for (int i = len - 1; i >= 0; i--) {         // coins
            for (int j = 0; j <= goal; j++) {        // rest goal value
                int sol = 0;
				// n-times of copies -- which can be optimized in V3
                for (int copies = 0; copies * coins[i] <= j; copies++) {
                    sol += dp[i + 1][j - copies * coins[i]];
                }
                dp[i][j] = sol;
            }
        }
		return dp[0][goal];
    }

    private int process_V1_no_DP(int[] coins, int goal, int index) {
		int len = coins.length;
		if (index == len) {
			return goal == 0 ? 1 : 0;
		}
		int sol = 0;
		for (int copies = 0; copies * coins[index] <= goal; copies++) {
			sol += process_V1_no_DP(coins, goal - copies * coins[index], index + 1);
		}
        return sol;
    }

    public static void main(String[] args) {
        final CoinFaceValuesPossibility sol = new CoinFaceValuesPossibility();
        final int versions = 3;
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
        int[] coins = new int[]{1, 2, 5, 10};
        int actual = func.apply(coins, 6);
        DataHelper.assertTrue(actual == 5, "Expected: 5, but got: " + actual);

        System.out.println();
        for (int i = 1; i <= 30; i++) {
            System.out.println(">> Goal: " + i);
            actual = func.apply(coins, i);
            System.out.println("<< There are/is " + actual + " combinations to the goal.");
        }
    }

}
