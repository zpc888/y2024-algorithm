package algo.dp;

import java.util.Random;

/**
 * Given a number, decompose it into a list of numbers from less to large.
 * The list should be unique, and the order of the numbers in the list does not matter, and it can contain duplicated numbers.
 * How many ways are there to decompose the number?
 * <p/>
 * <pre>
 * Example #1: 1 can only be decomposed into [1] (only one way)
 * Example #2: 2 can only be decomposed into [1, 1], [2] (two ways)
 * Example #3: 3 can only be decomposed into [1, 1, 1], [1, 2], [3] (three ways)
 * Example #4: 4 can only be decomposed into [1, 1, 1, 1], [1, 1, 2], [1, 3], [2, 2], [4] (five ways)
 * </pre>
 */
public class NumberDecomposerFromLessToLarge {
    private int versionToRun = 1;

    public int decompose(int num) {
        if (num <= 0) {
            return 0;
        }
        if (versionToRun == 1) {
            return process_V1_no_DP(num, 1);
        } else if (versionToRun == 2) {
            return process_V2_DP(num);
        } else if (versionToRun == 3) {
            return process_V3_DP_optimized(num);
        } else {
            throw new IllegalStateException("Unsupported version: " + versionToRun);
        }
    }

    private int process_V1_no_DP(int num, int start) {
        if (num == 0) {
            return 1;
        }
		if (start > num) {
			return 0;
		}
        int ret = 0;
        for (int i = start; i <= num; i++) {
            ret += process_V1_no_DP(num - i, i);
        }
        return ret;
    }

    private int process_V2_DP(int num) {
        int[][] dp = new int[num + 1][num + 1];
		for (int i = 0; i <= num; i++) {
			dp[0][i] = 1;
		}
//		for (int i = 1; i <= num; i++) {		// num
//			for (int j = i+1; j <= num; j++) {	// start > num
//				dp[i][j] = 0;
//			}
//		}
        for (int i = 1; i <= num; i++) {
            for (int j = 1; j <= i; j++) {
				int ans = 0;
				for (int s = j; s <= i; s++) {
					int rest = i - s;
					if (s <= num) {
						ans += dp[rest][s];
					}
				}
				dp[i][j] = ans;
            }
        }
        return dp[num][1];
    }

    private int process_V3_DP_optimized(int num) {
        int[][] dp = new int[num + 1][num + 1];
		for (int i = 0; i <= num; i++) {
			dp[0][i] = 1;
		}
        for (int i = 1; i <= num; i++) {
            for (int j = i; j >= 1; j--) {
				dp[i][j] = dp[i - j][j];
                if (j + 1 <= num) {
                    dp[i][j] += dp[i][j + 1];
                }
// assuming i = 10 j = 2
// [10, 2] = [8,2] + [7, 3] + [6, 4] + [5, 5]
// [10, 3] = [7, 3] + [6, 4] + [5, 5]
// [10, 4] = [6, 4] + [5, 5]
// [10, 5] = [5, 5]
// [10, 6] = [4, 6] impossible
            }
        }
        return dp[num][1];
    }

    public static void main(String[] args) {
        runAllVersions(1, 1);
        runAllVersions(2, 2);
        runAllVersions(3, 3);
        runAllVersions(4, 5);
        runAllVersions(5, 7);

        verifyWithHighVolumes();
    }

    private static void verifyWithHighVolumes() {
        Random random = new Random();
        for (int run = 0; run < 200; run++) {
            int number = random.nextInt(50) + 1;
            runAllVersions(number, -1);
        }
    }

    private static int runAllVersions(int number, int expected) {
        return runAllVersionsExcludes(number, expected, new int[0]);
    }

    private static int runAllVersionsExcludes(int number, int expected, int... excludes) {
        System.out.println();
        System.out.printf("Decomposing number: %d\n", number);
        if (expected >= 0) {
            System.out.println("\t\tExpected: " + expected + " ways");
        }
        NumberDecomposerFromLessToLarge sol = new NumberDecomposerFromLessToLarge();
        int versions = 3;
        int[] actuals = new int[versions];
        for (int i = 0; i < versions; i++) {
            sol.versionToRun = i + 1;
            actuals[i] = sol.decompose(number);
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
