package algo.dp;

import assist.DataHelper;

import java.util.Random;

public class OutOfBoundaryAfterKPercentage {
    private int versionToRun = 1;

	/**
	 * n*m grid, starting from (row, col) move up, down, left, right randomly 
	 * with same percentage of possibility, after k steps, what is the 
	 * percentage of out of boundary
	 */
    public double findProbability(int n, int m, int row, int col, int k) {
        if (versionToRun == 1) {
            return process_V1_no_DP(n, m, row, col, k);
        } else if (versionToRun == 2) {
            return process_V2_DP(n, m, row, col, k);
        } else {
            throw new IllegalStateException("Unsupported version: " + versionToRun);
        }
    }

	double process_V1_no_DP(int n, int m, int r, int c, int k) {
		double total = Math.pow(4, k);
		long in = v1_no_DP(n, m, r, c, k);
		return 1.0 - in / total;
	}

	double process_V2_DP(int n, int m, int r, int c, int k) {
        long[][] dp1 = new long[n][m];
        long[][] dp2 = null;
        dp1[r][c] = 1;
        for (int step = 0; step < k; step++) {
			dp2 = new long[n][m];
			for (int  i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					long val = dp1[i][j];
					if (val > 0) {
						int top = i - 1;
						int left = j + 1;
						int down = i + 1;
						int right = j - 1;
						if (top >= 0) {
							dp2[top][j] += val;
						}
						if (left < m) {
							dp2[i][left] += val;
						}
						if (down < n) {
							dp2[down][j] += val;
						}
						if (right >= 0) {
							dp2[i][right] += val;
						}
					}
				}
			}
			dp1 = dp2;
        }
		double total = Math.pow(4, k);
		long in = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				in += dp2[i][j];
			}
		}
		return 1 - ((double)in) / total;
	}

	private long v1_no_DP(int n, int m, int r, int c, int k) {
		if (r < 0 || r >= n || c < 0 || c >= m) {
			return 0;
		}
		if (k == 0) {
			return 1;
		}
		long up = v1_no_DP(n, m, r - 1, c, k - 1);	// up
		long left = v1_no_DP(n, m, r, c + 1, k - 1);	// left
		long down = v1_no_DP(n, m, r + 1, c, k - 1);	// down
		long right = v1_no_DP(n, m, r, c - 1, k - 1);	// right
		return up + left + down + right;
	}

	public static void main(String[] args) {
		runAllVersions(5, 4, 0, 0, 1, 0.5);
		runAllVersions(5, 4, 0, 2, 1, 0.25);
		runAllVersions(5, 4, 1, 0, 1, 0.25);
		runAllVersions(5, 4, 1, 1, 1, 0);
		int testCycles = 1000;
		Random random = new Random();
		System.out.println();
		for (int i = 0; i < testCycles; i++) {
			int n  = random.nextInt(8) + 2;
			int m  = random.nextInt(8) + 2;
			int r = 20;
			while (r >= n) {
				r = random.nextInt(9);
			}
			int c = 20;
			while (c >= m) {
				c = random.nextInt(9);
			}
			int k = random.nextInt(8) + 1;
			System.out.println();
			runAllVersions(n, m, r, c, k, -1);
		}
	}

	private static void runAllVersions(int n, int m, int r, int c, int k, double expected) {
		OutOfBoundaryAfterKPercentage sol = new OutOfBoundaryAfterKPercentage();
		int versions = 2;
		double[] actuals = new double[versions];
		for (int i = 0; i < versions; i++) {
			sol.versionToRun = i + 1;
			actuals[i] = sol.findProbability(n, m, r, c, k);
			System.out.printf("Version %s probability = %.2f%%%n",
					sol.versionToRun, actuals[i]*100);
		}
		for (int i = 1; i < versions; i++) {
			DataHelper.assertTrue(actuals[i - 1] == actuals[i],
					"Version " + (i - 1) + " doesn't equal v" + i);
		}
		if (expected >= 0) {
			DataHelper.assertTrue(expected == actuals[0], 
					"Expect " + expected + " doesn't equal v" + actuals[0]);
		}
	}
}
