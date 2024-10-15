package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

/**
 * see: https://leetcode.com/problems/knight-probability-in-chessboard/
 * <p>
 * On an n x n chessboard, a knight starts at the cell (row, column) and 
 * attempts to make exactly k moves.
 * The rows and columns are 0-indexed, so the top-left cell is (0, 0), and the 
 * bottom-right cell is (n - 1, n - 1).
 * A chess knight has eight possible moves it can make, as illustrated below. 
 * Each move is two cells in a cardinal direction, then one cell in an 
 * orthogonal direction.
 * </p>
 */
public class Q0688_KnightProbabilityInChessBoard_DP {
    public double knightProbability(int n, int k, int row, int column) {
		if (row >= n || column >= n || row < 0 || column < 0) {
			return 0;
		}
		if (k == 0) {
			return 1;	
		}
		double allPath = Math.pow(8, k);
		// double validPath = process_V1_noDP(n, k, row, column);
		double validPath = process_V2_DP(n, k, row, column);
		return validPath/allPath;
    }

	@MetricsRuntime(ms = 10, beats = 31.24, runAt = "2024-10-11 20:30 EDT")
	@MetricsMemory(mb = 41.82, beats = 82.73)
	double process_V2_DP(int n, int k, int row, int col) {
		double[][][] dp = new double[k + 1][n][n];
		dp[k][row][col] = 1;
		for (int i = k; i > 0; i--) {
			for (int r = 0; r < n; r++) {
				for (int c = 0; c < n; c++) {
					double curr = dp[i][r][c];
					if (curr > 0) {
						if (r >= 2) {
						    if (c >= 1) {	
								dp[i-1][r-2][c-1] += curr;
						    }
							if ( c + 1 < n) {
								dp[i-1][r-2][c+1] += curr;
							}
						}
						if (r >= 1) {
							if ( c >= 2) {
								dp[i-1][r-1][c-2] += curr;
							}
							if ( c + 2 < n) {
								dp[i-1][r-1][c+2] += curr;
							}
						}
						if (r + 1 < n) {
							if ( c >= 2) {
								dp[i-1][r+1][c-2] += curr;
							}
							if ( c + 2 < n) {
								dp[i-1][r+1][c+2] += curr;
							}
						}
						if (r + 2 < n) {
							if ( c >= 1) {
								dp[i-1][r+2][c-1] += curr;
							}
							if ( c + 1 < n) {
								dp[i-1][r+2][c+1] += curr;
							}
						}
					}
				}
			}
		}
		double ret = 0;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				ret += dp[0][r][c];
			}
		}
		return ret;
	}

    // Time Limit Exceeded after 11 / 22 testcases passed with (8, 30, 6, 4)
	double process_V1_noDP(int n, int k, int row, int col) {
		if (row >= n || col >= n || row < 0 || col < 0) {
			return 0;
		}
		if (k == 0) {
			return 1;
		} else {
			return process_V1_noDP(n, k - 1, row - 2, col - 1)
			   	+ process_V1_noDP(n, k - 1, row - 2, col + 1)
			   	+ process_V1_noDP(n, k - 1, row - 1, col + 2)
			   	+ process_V1_noDP(n, k - 1, row + 1, col + 2)
			   	+ process_V1_noDP(n, k - 1, row + 1, col - 2)
			   	+ process_V1_noDP(n, k - 1, row + 2, col + 1)
			   	+ process_V1_noDP(n, k - 1, row + 2, col - 1)
			   	+ process_V1_noDP(n, k - 1, row - 1, col - 2);
		}
	}
}
