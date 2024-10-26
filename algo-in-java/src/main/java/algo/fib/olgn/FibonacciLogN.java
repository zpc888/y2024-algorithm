package algo.fib.olgn;

import assist.BaseSolution;
import assist.DataHelper;

/**
 * Fibonacci in O(LogN) time complexity instead of O(N)
 */
public class FibonacciLogN extends BaseSolution<Integer> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 4;
    }

    public int fib(int n) {
        if (versionToRun == 1) {
            return fibRecursion(n);
        } else if (versionToRun == 2) {
            return fibDP1(n);
        } else if (versionToRun == 3) {
            return fibDP2(n);
        } else if (versionToRun == 4) {
            return fibLgN(n);
        } else {
            throw new RuntimeException("No such version: " + versionToRun);
        }
    }

    int fibRecursion(int n) {
		if (n == 1) {
			return 1;
		} else if (n == 2) {
			return 1;
		} else {
			return fibRecursion(n-1) + fibRecursion(n-2);
		}
    }

    int fibDP1(int n) {
		if (n == 1) {
			return 1;
		} else if (n == 2) {
			return 1;
		}
		int[] dp = new int[n];
		dp[0] = 1;
		dp[1] = 1;
		for (int i = 2; i < n; i++) {
			dp[i] = dp[i-1] + dp[i-2];
		}
		return dp[n - 1];
    }

    int fibDP2(int n) {
		if (n == 1) {
			return 1;
		} else if (n == 2) {
			return 1;
		}
		int prev1 = 1;
		int prev2 = 1;
		int ret = 0;
		for (int i = 2; i < n; i++) {
			ret = prev1 + prev2;
			prev1 = prev2;
			prev2 = ret;
		}
        return ret;
    }

    int fibLgN(int n) {
		if (n == 1) {
			return 1;
		} else if (n == 2) {
			return 1;
		}
		// N:      1  2  3  4  5  6
		// Result: 1, 1, 2, 3, 5, 8
		//
		// |2, 1| = |1, 1| * | a, c |     a +  b = 2
		//                   | b, d |     c +  d = 1
		// |3, 2| = |2, 1| * | a, c |    2a +  b = 3
		//                   | b, d |    2c +  d = 2
		// a = 1, b = 1      | 1, 1 |
		// c = 1, d = 0		 | 1, 0 |
		// 
		// if f(n) = a*f(n-1) + b*f(n-2) + ... j*f(n-i)
		// |f(n), f(n-1), ..., f(n-i+1)| = |f(i), f(i-1), .., f(1)| 
		//                                       * | i*i matrix | ^ (n - i)
		//
		// so fib |f(n), f(n-1)| = |1, 1| * |1, 1| ^ (n-2)
		//                                  |1, 0|
		int[][] matrix = new int[][]{
			{1, 1},
			{1, 0}
		};
		int[][] powered = MatrixMath.power(matrix, n - 2);
		return powered[0][0] + powered[1][0];
    }



	public static void main(String[] args) {
		FibonacciLogN sol = new FibonacciLogN();
		sol.runAllVersions("f(5) = 5", () -> sol.fib(5), 5);
		sol.runAllVersions("f(6) = 6", () -> sol.fib(6), 8);
		int[] testData = DataHelper.genFixedSizeIntArrUniq(20, 1, 40);
		for (int i = 0; i < testData.length; i++) {
			final int n = testData[i];
			sol.runAllVersions("#" + (i + 1) + " = " + n,
					() -> sol.fib(n), null);
		}
		sol.performMeasure("20 Tests with Random: (1-40)", () -> {
			for (int i = 0; i < testData.length; i++) {
				sol.fib(testData[i]);
			}
			return null;
		});
//====================== < 20 Tests with Random: (1-40) > Performance Report ==============
//		Version-1: Duration: PT0.452923894S
//		Version-2: Duration: PT0.000035787S
//		Version-3: Duration: PT0.000029085S
//		Version-4: Duration: PT0.000176463S  // linear algebra version is 6 times slower than DP when 20 * n = 1 - 40

		sol.performMeasure("Large 5,000 Performance Measure", () -> {
			return sol.fib(5_000);
		}, 1);
//====================== < Large 5,000 Performance Measure > Performance Report ==============
//		Version-2: Duration: PT0.000119165S
//		Version-3: Duration: PT0.000140646S
//		Version-4: Duration: PT0.000031079S		// linear algebra version is 4 times faster than DP when n = 5,000

		sol.performMeasure("Large 100,000 Performance Measure", () -> {
			return sol.fib(100_000);
		}, 1);
//====================== < Large 100,000 Performance Measure > Performance Report ==============
//		Version-2: Duration: PT0.001451254S
//		Version-3: Duration: PT0.000855527S
//		Version-4: Duration: PT0.000027883S		// linear algebra version is 50 times faster than DP when n = 100,000

	}
}
