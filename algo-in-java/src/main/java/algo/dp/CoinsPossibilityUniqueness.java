package algo.dp;

import assist.DataHelper;

import java.util.TreeMap;
import java.util.function.BiFunction;

/**
 * Given a list of N coins, their values (V1, V2, … , VN), and a given value goal.
 * How many ways can we achieve the goal value using these coins?
 * <p/>
 * Each coin cannot be used multiple times. They will be treated as one way even if they have the same value.
 * <p/>
 * For example, if coins = {1, 2, 2, 3, 1} and goal = 3, the result is 2, which are: {1, 2}, {3}.
 */
public class CoinsPossibilityUniqueness {
    private int versionToRun = 1;

    public int solution(int[] coins, int goal) {
        if (coins == null || coins.length == 0 || goal <= 0) {
            return 0;
        }
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int coin : coins) {
            map.put(coin, map.getOrDefault(coin, 0) + 1);
        }
        int[][] coinsAndCopies = new int[map.size()][2];
        int index = 0;
        for (int coin : map.keySet()) {
            coinsAndCopies[index][0] = coin;
            coinsAndCopies[index][1] = map.get(coin);
            index++;
        }
        if (versionToRun == 1) {
            return process_V1_no_DP(coinsAndCopies, goal, 0);
        } else if (versionToRun == 2) {
            return process_V2_DP(coinsAndCopies, goal);
        } else if (versionToRun == 3) {
            return process_V3_DP_Aggressively(coinsAndCopies, goal);
        } else {
            throw new IllegalStateException("Unsupported version: " + versionToRun);
        }
    }

    private int process_V2_DP(int[][] coinsAndCopies, int goal) {
        int len = coinsAndCopies.length;
        int[][] dp = new int[len + 1][goal + 1];
        dp[len][0] = 1;
        for (int i = len - 1; i >= 0; i--) {
            int coinValue = coinsAndCopies[i][0];
            int coinCopy  = coinsAndCopies[i][1];
            for (int j = 0; j <= goal; j++) {
                int sol = 0;
                for (int c = 0; c <= coinCopy; c++) {
                    int rest = j - c*coinValue;
                    if (rest >= 0) {
                        sol += dp[i + 1][rest];
                    } else {
                        break;
                    }
                }
                dp[i][j] = sol;
            }
        }
        return dp[0][goal];
    }

    private int process_V3_DP_Aggressively(int[][] coinsAndCopies, int goal) {
        int len = coinsAndCopies.length;
        int[][] dp = new int[len + 1][goal + 1];
        dp[len][0] = 1;
		for (int i = len - 1; i >= 0; i--) {
			int coinValue = coinsAndCopies[i][0];
			int coinCopy  = coinsAndCopies[i][1];
			for (int j = 0; j <= goal; j++) {

                // dp[i][j] = dp[i+1][j] + dp[i+1][j - coinValue] + dp[i+1][j - 2*coinValue] + ... + dp[i+1][j - coinCopy*coinValue]
                // but dp[i][j - coinValue] = dp[i+1][j - coinValue] + dp[i][j - 2*coinValue] + ... + dp[i][j - (1 + coinCopy)*coinValue]
                // so dp[i][j] = dp[i+1][j] + dp[i][j - coinValue] - dp[i+1][j - (1 + coinCopy)*coinValue]
                // in that way, we can avoid the inner loop of c from 0 to coinCopy

                dp[i][j] = dp[i+1][j];      // copy from next row, meaning no use of current coin
                // reuse previous calculated value for the same row where col = j - coinValue
                if (j >= coinValue) {
                    dp[i][j] += dp[i][j - coinValue];
                }
                // at the same time, since it has only max coinCopy, dp[i][j - coinValue] cell will have
                // extra dp[i + 1][j - (coinCopy + 1) * coinValue]
                int extraCellIndex = j - (coinCopy + 1) * coinValue;
                if (extraCellIndex >= 0) {
                    dp[i][j] -= dp[i + 1][extraCellIndex];
                }
			}
		}
        return dp[0][goal];
    }

    private int process_V1_no_DP(int[][] coinsAndCopies, int goal, int index) {
		int len = coinsAndCopies.length;
		if (index == len) {
			return goal == 0 ? 1 : 0;
		}
		int coinValue = coinsAndCopies[index][0];
		int coinCopy  = coinsAndCopies[index][1];
		int sol = 0;
		for (int i = 0; i <= coinCopy; i++) {
			int restGoal = goal - i * coinValue;
			if (restGoal >= 0) {
				sol += process_V1_no_DP(coinsAndCopies, restGoal, index + 1);
			} else {
				break;
			}
		}
		return sol;
    }

    public static void main(String[] args) {
        final CoinsPossibilityUniqueness sol = new CoinsPossibilityUniqueness();
        final int versions = 3;
        BiFunction<int[], Integer, Integer> func = (coins, goal) -> {
            int[] actuals = new int[versions];
            for (int i = 1; i <= versions; i++) {
                sol.versionToRun = i;
                actuals[i-1] = sol.solution(coins, goal);
                System.out.println("Version: " + sol.versionToRun + ", actual: " + actuals[i-1]);
            }
            for (int i = 1; i < versions; i++) {
                if (actuals[i] != actuals[i-1]) {
                    throw new IllegalStateException("V" + i + " got: " + actuals[i-1] + ", but V" + (i+1) + " got: " + actuals[i]);
                }
            }
            return actuals[0];
        };
        int[] coins = {1, 2, 2, 3, 1};
        Integer actual = func.apply(coins, 3);
        DataHelper.assertTrue(actual == 2, "Expected: 2, but got: " + actual);

        Integer actual2 = func.apply(coins, 4);
        DataHelper.assertTrue(actual2 == 3, "Expected: 3, but got: " + actual2);

        System.out.println();
        for (int goal = 0; goal < 20; goal++) {
            System.out.println(">> Goal: " + goal);
            System.out.println(">> Actual: " + func.apply(new int[]{1, 2, 2, 1, 1, 1, 2, 5, 10, 5, 3, 3 }, goal));
        }
    }
}
