package algo.dp;

import assist.DataHelper;

import java.util.function.BiFunction;

public class NumOfPathToTarget {
    private int chosenVersionToRun = 1;

    /**
     * Given a number of positions, and a start position and a target position.
     * Find the number of paths from start to target after the number of moves.
     * A path is a sequence of positions starting from 1 to n. When at position i, it can move to i+1 or i-1.
     * But if at position 1, it can only move to 2. If at position n, it can only move to n-1.
     *
     * @param numOfPosition
     * @param startPos
     * @param targetPos
     * @param numOfMoves
     * @return number of paths from start to target after numOfMoves. 0 means no way to reach target.
     */
    public int countPath(int numOfPosition, int startPos, int targetPos, int numOfMoves) {
        if (numOfPosition < 2
                || startPos < 1 || startPos > numOfPosition
                || targetPos < 1 || targetPos > numOfPosition
                || numOfMoves < 0) {
            return 0;
        }
        if (chosenVersionToRun == 1) {
            return countPath_v1_no_dp(numOfPosition, startPos, targetPos, numOfMoves);
        } else if (chosenVersionToRun == 2) {
            return countPath_v2_basic_dp(numOfPosition, startPos, targetPos, numOfMoves);
        } else {
            return countPath_v3_optimize_dp(numOfPosition, startPos, targetPos, numOfMoves);
        }
    }

    private int countPath_v2_basic_dp(int n, int from, int to, int steps) {
		int[][] dp = new int[n+1][steps+1];
		for (int i = 0; i < n+1; i++) {
			for (int j = 0; j < steps+1; j++) {
				dp[i][j] = -1;
			}
		}
        return doBasicDP(n, from, to, steps, dp);
    }

    private int countPath_v3_optimize_dp(int n, int from, int to, int steps) {
		int[][] dp = new int[n+1][steps+1];
		for (int i = 0; i < n+1; i++) {
			for (int j = 0; j < steps+1; j++) {
				dp[i][j] = -1;
			}
		}
        return doOptimizedDP(n, from, to, steps, dp);
    }

	/*
			to-pos is fixed, assuming it is 4 for run
			from-pos and steps are dynamic

	    Steps > 0     1     2    3     4       Recursive explanation:
    FromPos
	   \/   +------+-----+-----+-----+-----+   (1, 1) -> (2, 0)
	    0	|   *  |  *  |  *  |  *  |  +  |   (2, 1) -> (1, 0) + (3, 0)
			+------+-----+-----+-----+-----+   (3, 1) -> (2, 0) + (4, 0)
		1	|   0  |  0  |  0  |  1  |  0  |   (4, 1) -> (3, 0) + (5, 0)
			+------+-----+-----+-----+-----+   (5, 1) -> (4, 0)
		2	|   0  |  0  |  1  |  0  |  4  |
			+------+-----+-----+-----+-----+   (1, 2) -> (2, 1)
		3	|   0  |  1  |  0  |  3  |  0  |   (2, 2) -> (1, 1) + (3, 1)
			+------+-----+-----+-----+-----+   (3, 2) -> (2, 1) + (4, 1)
		4	|   1  |  0  |  2  |  0  |  5  |   (4, 2) -> (3, 1) + (5, 1)
			+------+-----+-----+-----+-----+   (5, 2) -> (4, 1)
		5	|   0  |  1  |  0  |  2  |  0  |
			+------+-----+-----+-----+-----+   ... ...
	
	 */
    private int doOptimizedDP(int n, int from, int to, int steps, int[][] dp) {
		// pre-fill step 0 for base cases
		for (int p = 1; p < n+1; p++) {
			dp[p][0] = p == to ? 1 : 0;
		}
		for (int s = 1; s < steps + 1; s++) {
			dp[1][s] = dp[2][s-1];
			for (int p = 2; p < n; p++) {
				dp[p][s] = dp[p+1][s-1] + dp[p-1][s-1];
			}
			dp[n][s] = dp[n-1][s-1];
		}
		return dp[from][steps];
	}

    private int doBasicDP(int n, int from, int to, int steps, int[][] dp) {
		int ret = dp[from][steps];
		if (ret != -1) {
			return ret;
		}
		if (steps == 0) {
			ret = from == to ? 1 : 0;
		} else if (from == 1) {
			ret = doBasicDP(n, from + 1, to, steps - 1, dp);
		} else if (from == n) {
			ret = doBasicDP(n, from - 1, to, steps - 1, dp);
		} else {
			ret = doBasicDP(n, from + 1, to, steps - 1, dp)
					+ doBasicDP(n, from - 1, to, steps - 1, dp);
		}
		dp[from][steps] = ret;
		return ret;
	}

    private int countPath_v1_no_dp(int n, int from, int to, int steps) {
		if (steps == 0) {
			return from == to ? 1 : 0;
		}
		if (from == 1) {
			return countPath_v1_no_dp(n, from + 1, to, steps - 1);
		}
		if (from == n) {
			return countPath_v1_no_dp(n, from - 1, to, steps - 1);
		}
		return countPath_v1_no_dp(n, from + 1, to, steps - 1)
			+ countPath_v1_no_dp(n, from - 1, to, steps - 1);
    }

	public static void main(String[] args) {
		NumOfPathToTarget sol = new NumOfPathToTarget();
		sol.chosenVersionToRun = 1;
		int pathV1 = sol.countPath(7, 2, 4, 4);
		System.out.println(pathV1);

		sol.chosenVersionToRun = 2;
		int pathV2 = sol.countPath(7, 2, 4, 4);
		System.out.println(pathV2);

		sol.chosenVersionToRun = 3;
		int pathV3 = sol.countPath(7, 2, 4, 4);
		System.out.println(pathV3);

        DataHelper.assertTrue(pathV1 == 4, "failed on test - v1");
        DataHelper.assertTrue(pathV2 == 4, "failed on test - v2");
        DataHelper.assertTrue(pathV3 == 4, "failed on test - v3");

        performStressTest();
	}

    private static void performStressTest() {
        BiFunction<Integer, Runnable, Long> repeatRun = (n, r) -> {
            long start = System.currentTimeMillis();
            for (int i = 0; i < n; i++) {
                r.run();
            }
            long end = System.currentTimeMillis();
            return end - start;
        };
        int steps = 16;
        NumOfPathToTarget sol = new NumOfPathToTarget();
        sol.chosenVersionToRun = 1;
        final int testCycles = 10_000;
        long timeV1 = repeatRun.apply(testCycles, () -> {
            sol.countPath(7, 2, 4, steps);
        });

        sol.chosenVersionToRun = 2;
        long timeV2 = repeatRun.apply(testCycles, () -> {
            sol.countPath(7, 2, 4, steps);
        });

        sol.chosenVersionToRun = 3;
        long timeV3 = repeatRun.apply(testCycles, () -> {
            sol.countPath(7, 2, 4, steps);
        });
        System.out.println();
        System.out.println("Performance Testing: V1 vs V2 vs V3 when steps = " + steps);
        System.out.println("V1: " + timeV1 + " ms");
        System.out.println("V2: " + timeV2 + " ms");
        System.out.println("V3: " + timeV3 + " ms");

        /*
         Performance Testing: V1 vs V2 vs V3 when steps = 4
            V1: 1 ms
            V2: 7 ms
            V3: 8 ms

         Performance Testing: V1 vs V2 vs V3 when steps = 16
            V1: 754 ms
            V2: 14 ms
            V3: 17 ms

         Conclusion / Reflection: When steps is smaller, dynamic planning is slower than recursive.
         But when steps is larger, dynamic planning is faster. Dynamic planning is O(n^2) while recursive is O(2^n).
         Also dynamic planning as needed will be faster than pre-planning all possible steps since it only do work when needed.
         But pre-planning does have the benefit to show the best or worst result from all possible steps.
         */
    }


}
