package algo.dp;

import assist.DataHelper;

import java.util.Random;

/**
 * A monster has n health points. Each attack will cause it to lose 0-m damage points
 * (assuming the probability of each value is the same).
 * <p/>
 * After k times of attacks, what is the probability that the monster is killed?
 * Health points being less or equal to 0 is considered killed.
 */
public class KillingMonster {
    private int versionToRun = 1;

    public double deadPercentage(int n, int m, int k) {
        if (versionToRun == 1) {
            return process_V1_no_DP_with_killed(n, m, k);
        } else if (versionToRun == 2) {
            return process_V2_no_DP_with_alive(n, m, k);
        } else if (versionToRun == 3) {
            return process_V3_DP_with_killed(n, m, k);
        } else if (versionToRun == 4) {
            return process_V4_DP_with_alive(n, m, k);
        } else if (versionToRun == 5) {
            return process_V5_DP_optimized_with_killed(n, m, k);
        } else if (versionToRun == 6) {
            return process_V6_DP_optimized_with_alive(n, m, k);
        } else {
            throw new IllegalStateException("Unsupported version: " + versionToRun);
        }
    }

    private double process_V1_no_DP_with_killed(int n, int m, int k) {
        double total = Math.pow(m + 1, k);
        long killed = v1_no_DP_killed(n, m, k);
        return killed / total;
    }

    private long v1_no_DP_killed(int n, int m, int k) {
		if (k == 0) {
			return n <= 0 ? 1 : 0;
		}
		long ret = 0;
		for (int i = 0; i <= m; i++) {
			ret += v1_no_DP_killed(n - i, m, k - 1);
		}
		return ret;
	}

    private double process_V3_DP_with_killed(int n, int m, int k) {
        double total = Math.pow(m + 1, k);
        long[][] dp = new long[k + 1][n + 1];
        dp[0][0] = 1;           // dp[0][1..n] = 0
		for (int i = 1; i <= k; i++) {
			for (int j = 0; j <= n; j++) {

				long killed = 0;
				for (int damaged = 0; damaged <= m; damaged++) {
					int restHealthPoint = j - damaged;
					if (restHealthPoint < 0) {
						restHealthPoint = 0;
					}
					killed += dp[i - 1][restHealthPoint];
				}

				dp[i][j] = killed;
			}
		}
        return dp[k][n] / total;
    }

    private double process_V5_DP_optimized_with_killed(int n, int m, int k) {
        double total = Math.pow(m + 1, k);
        long[][] dp = new long[k + 1][n + 1];
        dp[0][0] = 1;           // dp[0][1..n] = 0
        for (int i = 1; i <= k; i++) {
			// already dead, damaged 0, 1, 2, ... m will be dead 
			// status too for i-powered
			dp[i][0] = (long)Math.pow(m + 1, i);
            for (int j = 1; j <= n; j++) {

  				// dp[i][j] = dp[i-1][j] + dp[i][j-1] - dp[i-1][j - m -1]
				dp[i][j] = dp[i-1][j] + dp[i][j - 1];
				int prevExtra = j - m - 1;
				if (prevExtra < 0) {
					prevExtra = 0;
				}
				dp[i][j] -= dp[i-1][prevExtra];

            }
        }
        return dp[k][n] / total;
    }

    private double process_V2_no_DP_with_alive(int n, int m, int k) {
        double total = Math.pow(m + 1, k);
        long alive = v2_no_DP_alive(n, m, k);
        return 1 - alive / total;
    }

    private double process_V4_DP_with_alive(int n, int m, int k) {
        double total = Math.pow(m + 1, k);
        long[][] dp = new long[k + 1][n + 1];
        dp[0][0] = 0;           // dp[0][1..n] = 1
        for (int i = 1; i <= n; i++) {
            dp[0][i] = 1;
        }
		for (int i = 1; i <= k; i++) {
			for (int j = 0; j <= n; j++) {
				int alive = 0;
				for (int damaged = 0; damaged <= m; damaged++) {
					int restHealthPoints = j - damaged;
					// ignored killed since calculating alive status
					if (restHealthPoints > 0) {
						alive += dp[i-1][restHealthPoints];
					} else {	// damaged is increase, rest health goes worse
						break;
					}
				}
				dp[i][j] = alive;
			}
		}
		long aliveCases = dp[k][n];
        return 1 - aliveCases / total;
    }

    private double process_V6_DP_optimized_with_alive(int n, int m, int k) {
        double total = Math.pow(m + 1, k);
        long[][] dp = new long[k + 1][n + 1];
        dp[0][0] = 0;           // dp[0][1..n] = 1
        for (int i = 1; i <= n; i++) {
            dp[0][i] = 1;
        }
		for (int i = 1; i <= k; i++) {
			dp[i][0] = 0;					// dead already
			for (int j = 1; j <= n; j++) {
				// dp[i][j] = dp[i][j-1] + dp[i-1][j] - dp[i-1][j-m-1]
				dp[i][j] = dp[i][j-1] + dp[i-1][j];
				int prevExtra = j - m - 1;
				if (prevExtra >= 0) {
					dp[i][j] -= dp[i-1][prevExtra];
				}
			}
		}
		long aliveCases = dp[k][n];
        return 1 - aliveCases / total;
    }

	private long v2_no_DP_alive(int n, int m, int k) {
		if (n <= 0) {
			return 0;
		}
		if (k == 0) {
			return 1;
		}
		long ret = 0;
		for (int i = 0; i <= m; i++) {
			ret += v2_no_DP_alive(n - i, m, k - 1);
		}
		return ret;
	}

    public static void main(String[] args) {
        runAllVersions(4, 3, 1, 0);
        runAllVersions(4, 4, 1, 0.2);
        runAllVersions(4, 7, 1, 0.50);
        runAllVersions(4, 4, 2, 0.60);      // 15 / 25

        highVolumeTest();
    }

    private static void highVolumeTest() {
        Random random = new Random();
        for (int run = 0; run < 10_000; run++) {
            int n1  = random.nextInt(8) + 1;
            int n2  = random.nextInt(8) + 1;
            int n3  = random.nextInt(8) + 1;
            runAllVersions(n1, n2, n3, -1);
        }
    }

    private static double runAllVersions(int n, int m, int k, double expected) {
        System.out.println();
        System.out.printf("Monster with %s health points, each attack causes 0-%s damage points, after %s attacks, probability of being killed.%n",
                n, m, k);
        if (expected >= 0) {
            System.out.printf("\t\tExpected probability = %.2f%%%n", expected * 100);
        }
        KillingMonster sol = new KillingMonster();
        int versions = 6;
        double[] actuals = new double[versions];
        for (int i = 1; i <= versions; i++) {
            sol.versionToRun = i;
            actuals[i - 1] = sol.deadPercentage(n, m, k);
            System.out.printf("Version %s probability = %.2f%%%n",
                    i, actuals[i - 1] * 100);
        }
        for (int i = 1; i < versions; i++) {
            if (!doubleEq(actuals[i], actuals[i - 1])) {
                throw new IllegalStateException("V" + i + " got: " + actuals[i - 1]
                        + ", but V" + (i + 1) + " got: " + actuals[i]);
            }
        }
        if (expected >= 0) {
            DataHelper.assertTrue(doubleEq(expected, actuals[0]),
                    "Expected: " + expected + ", but got: " + actuals[0]);
        }
        return actuals[0];
    }

    private static boolean doubleEq(double a, double b) {
        return Math.abs(a - b) < 1e-6;
    }
}
