package algo.dp;

import java.util.Random;
import java.util.function.BiConsumer;

/**
 * Find the minimum path in a n*m grid from top-left to bottom-right. Each cell has a value representing the cost
 * to move to that cell. You can only move right or down.
 */
public class MinPathFinder {
    private int versionToRun = 1;

    public int minPath(int[][] grid) {
        if (versionToRun == 1) {
            return process_V1_no_DP(grid);
        } else if (versionToRun == 2) {
            return process_V2_DP_From_NxM_To_0x0(grid);
        } else if (versionToRun == 3) {
            return process_V3_DP_From_0x0_To_NxM(grid);
        } else if (versionToRun == 4) {
            return process_V4_DP_Array_M(grid);
        } else if (versionToRun == 5) {
            return process_V5_DP_Array_N(grid);
        } else {
            throw new IllegalStateException("Unknown version to run: " + versionToRun);
        }
    }

    private int process_V1_no_DP(int[][] grid) {
        return no_DP(grid, 0, 0);
    }

    private int process_V2_DP_From_NxM_To_0x0(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] dp = new int[row+1][col+1];
        for (int i = col - 1; i >= 0; i--) {
            dp[row - 1][i] = grid[row - 1][i] + dp[row - 1][i + 1];
        }
        for (int i = row - 2; i >= 0; i--) {
            dp[i][col - 1] = grid[i][col - 1] + dp[i + 1][col - 1];
        }
        for (int i = row - 2; i >= 0; i--) {
            for (int j = col - 2; j >= 0; j--) {
                dp[i][j] = grid[i][j] + Math.min(dp[i+1][j], dp[i][j+1]);
            }
        }
        return dp[0][0];
    }

    private int process_V3_DP_From_0x0_To_NxM(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[][] dp = new int[row][col];
		dp[0][0] = grid[0][0];
		for (int i = 1; i < col; i++) {
			dp[0][i] = grid[0][i] + dp[0][i - 1];
		}
		for (int i = 1; i < row; i++) {
			dp[i][0] = grid[i][0] + dp[i - 1][0];
		}
		for (int i = 1; i < row; i++) {
			for (int j = 1; j < col; j++) {
				dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
			}
		}
		return dp[row - 1][col - 1];
	}

    private int process_V4_DP_Array_M(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[] dp = new int[col];
        dp[0] = grid[0][0];
        for (int i = 1; i < col; i++) {
            dp[i] = grid[0][i] + dp[i - 1];
        }
        for (int i = 1; i < row; i++) {
            dp[0] += grid[i][0];
            for (int j = 1; j < col; j++) {
                dp[j] = grid[i][j] + Math.min(dp[j], dp[j - 1]);
            }
        }
        return dp[col - 1];
    }

	// in case the number of columns is way larger than the number of rows
    private int process_V5_DP_Array_N(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int[] dp = new int[row];
        dp[0] = grid[0][0];
        for (int i = 1; i < row; i++) {
            dp[i] = grid[i][0] + dp[i - 1];
        }
        for (int i = 1; i < col; i++) {
            dp[0] += grid[0][i];
            for (int j = 1; j < row; j++) {
                dp[j] = grid[j][i] + Math.min(dp[j], dp[j - 1]);
            }
        }
        return dp[row - 1];
    }

    private int no_DP(int[][] grid, int i, int j) {
		int row = grid.length;
		int col = grid[0].length;
		if (i >= row || j >= col) {
			return 0;
		}
		if (i == row - 1) {		// only allow to go right
			return grid[i][j] + no_DP(grid, i, j + 1);
		}
		if (j == col - 1) {		// only allow to go down
			return grid[i][j] + no_DP(grid, i + 1, j);
		}
		// not last row nor last col
		return grid[i][j] + Math.min(
				no_DP(grid, i, j + 1), 
				no_DP(grid, i + 1, j));
		
    }

    public static void main(String[] args) {
        MinPathFinder sol = new MinPathFinder();
        final int versions = 5;
        BiConsumer<int[][], Integer> runCheck = (g, expected) -> {
            int[] actuals = new int[versions];
            for (int v = 1; v <= versions; v++) {
                sol.versionToRun = v;
                actuals[v-1] = sol.minPath(g);
                System.out.println("Version: " + sol.versionToRun + ", actual: " + actuals[v-1]);
            }
            if (expected < 0) {
                expected = actuals[0];      // used the 1st one as expection
            }
            for (int i = 0; i < versions; i++) {
                if (actuals[i] != expected) {
                    throw new IllegalStateException("Expected: " + expected + ", but V" + (i+1) + " got: " + actuals[i]);
                }
            }
        };
        int[][] grid = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };
        runCheck.accept(grid, 7);
        final int runCycle = 100;
        Random random = new Random();
        for (int i = 0; i < runCycle; i++) {
            int n = random.nextInt(10) + 1;
            int m = random.nextInt(10) + 1;
            int[][] g = randomGen(random, n, m);
            System.out.println(">>>>>>>>> Run Cycle: " + (i+1) +  " for " + n + "x" + m);
            runCheck.accept(g, -1);
            System.out.println("<<<<<<<<<< OK");
            System.out.println();
        }
    }

    private static int[][] randomGen(Random random, int n, int m) {
        int[][] g = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                g[i][j] = random.nextInt(10) + 1;
            }
        }
        return g;
    }
}
