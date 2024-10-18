package algo.dp;

import assist.TestHelper;

import java.time.Duration;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Given a chess board N * N, place N queens on it so that no 2 queens can attack each other.
 * Return the number of ways to place them.
 * <p/>
 * 2 queens can attack each other if they are in the same row, same column or same diagonal.
 * <pre>
 *     Example: n = 1, return 1
 *     Example: n = 2, return 0
 *     Example: n = 3, return 0
 *     Example: n = 4, return 2
 *     Example: n = 5, return 10
 *     Example: n = 6, return 4
 *     Example: n = 7, return 40
 *     Example: n = 8, return 92
 * </pre>
 */
public class ChessNQueen {
    private int versionToRun = 1;

    public int solution(int n) {
        if (n < 1) {
            return 0;
        }
        if (versionToRun == 1) {
			return process_v1_no_DP(n, new int[n], 0);
		} else if (versionToRun == 2) {
				return process_v2_no_DP_Constant_Time_Optimized(n);
        } else {
            throw new IllegalStateException("Unsupported version: " + versionToRun);
        }
    }

	/*
	 * No DP version since it needs (int[] cols, int row). Its time complexity is O(n^n), space O(n)
	 */
    private int process_v1_no_DP(int n, int[] cols, int row) {
        if (row == n) {
            return 1;
        }
		int count = 0;
		for (int i = 0; i < n; i++) {
			cols[row] = i;
			if (isValid(cols, row)) {
				count += process_v1_no_DP(n, cols, row + 1);
			}
		}
		return count;
    }

	/**
	 *  Still O(n^n) time complexity, but optimized to O(1) space complexity.
	 *  The constant time during n^n operations will be dramatically reduced.
	 */
	private int process_v2_no_DP_Constant_Time_Optimized(int n) {
		if (n < 1) {
			return 0;
		} else if (n > 32) {
			throw new IllegalArgumentException("n is too large: " + n);
		}
		int target = n == 32 ? -1 : (1 << n) - 1;		// 32 bits, all 1s
		return v2NoDP(target, 0, 0, 0);
	}

	// left diagonal and right diagonal are only applicable to the next row, not all rows in matrix
	private int v2NoDP(int target, int sel, int leftDiag, int rightDiag) {
		if (target == sel) {
			return 1;
		}
		int ret = 0;
		int allowed = target & (~(sel | leftDiag | rightDiag));
		while (allowed != 0) {
			int rightMostOne = allowed & (~allowed + 1);
			allowed -= rightMostOne;
			ret += v2NoDP(target, sel | rightMostOne, 
					(leftDiag | rightMostOne) << 1,			// 0 1 .. to 1 ..
					(rightDiag | rightMostOne) >>> 1);      // 1 0 .. to 0 1 0 ..
		}
		return ret;
	}

	private boolean isValid(int[] cols, int row) {
		int col = cols[row];
		for (int i = 0; i < row; i++) {
			int againstCol = cols[i];
			if (col == againstCol || Math.abs(row - i) == Math.abs(col - againstCol)) {
				return false;
			}
		}
		return true;
	}


	public static void main(String[] args) {
		runAllVersions(1, 1);
		runAllVersions(2, 0);
		runAllVersions(3, 0);
		runAllVersions(4, 2);
		runAllVersions(5, 10);
		runAllVersions(6, 4);
		runAllVersions(7,  40);
		runAllVersions(8, 92);

		verifyWithHighVolumes();
	}

	private static boolean silent = false;
	private static void verifyWithHighVolumes() {
//		silent = true;
		Random random = new Random();
		for (int run = 0; run < 30; run++) {
			runAllVersions(random.nextInt(10), -1);
		}
		for (int run = 0; run < 10; run++) {
			runAllVersionsExcludes(random.nextInt(16),
					-1, 1);
		}
		performMeasure();

	}

	private static void performMeasure() {
		System.out.println("===================================== Measure performance ===============================");
		final ChessNQueen sol = new ChessNQueen();
		final int n = 15;
		Duration d1 = TestHelper.timeRun(() -> {
			sol.versionToRun = 1;
			int result = sol.solution(n);
			System.out.printf("Version-%d: %d queens on %dX%d board, ways: %d\n", sol.versionToRun, n, n, n, result);
		});
		System.out.println("\tDuration: " + d1.toMillis() );
		System.out.println("---------------------- .VS. ----------------------------");
		Duration d2 = TestHelper.timeRun(() -> {
			sol.versionToRun = 2;
			int result = sol.solution(n);
			System.out.printf("Version-%d: %d queens on %dX%d board, ways: %d\n", sol.versionToRun, n, n, n, result);
		});
		System.out.println("\tDuration: " + d2.toMillis() );	// D1 = 25.039 seconds; D2 = 1.101 second  (n = 15) (22.7 times faster)
	}

	private static int runAllVersions(int n, int expected) {
		return runAllVersionsExcludes(n, expected, new int[0]);
	}

	private static int runAllVersionsExcludes(int n, int expected, int... excludes) {
		if (!silent) {
			System.out.println();
			System.out.printf("Chess board matrix: %sX%s\n", n, n);
			if (expected >= 0) {
				System.out.println("\t\tExpected: " + expected + " ways to place " + n + " queens");
			}
		}
		Set<Integer> excludeSet = new HashSet<>();
		if (excludes != null) {
			for (int exclude : excludes) {
				excludeSet.add(exclude);
			}
		}
		ChessNQueen sol = new ChessNQueen();
		int versions = 2;
		int[] actuals = new int[versions];
		for (int i = 0; i < versions; i++) {
			sol.versionToRun = i + 1;
			if (excludeSet.contains(sol.versionToRun)) {
				continue;
			}
			actuals[i] = sol.solution(n);
			if (!silent) {
				System.out.printf("Version: %d, actual: %d\n", sol.versionToRun, actuals[i]);
			}
		}
		if (expected < 0) {
			for (int i = 0; i < versions; i++) {
				if (excludeSet.contains(i + 1)) {
					continue;
				}
				expected = actuals[i];        // used the 1st run one as expectation
				break;
			}
		}
		for (int i = 0; i < versions; i++) {
			if (excludeSet.contains(i + 1)) {
				continue;
			}
			if (actuals[i] != expected) {
				throw new IllegalStateException("Expected: " + expected + ", but V" + (i+1) + " got: " + actuals[i]);
			}
		}
		return expected;
	}
}
