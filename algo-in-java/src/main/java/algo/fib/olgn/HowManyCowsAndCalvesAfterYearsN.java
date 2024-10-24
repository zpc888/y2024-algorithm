package algo.fib.olgn;

import assist.BaseSolution;
import assist.DataHelper;

/**
 * source: https://stackoverflow.com/questions/59926908/count-the-number-of-cows-after-n-number-of-years
 * <p />
 * A cow has a calf every year. A calf becomes a cow in 2 years, i.e. on the third year of a calf,
 * which will have its own calf. Starting with one cow we have to count how many animals are there
 * in N years. Suppose no cow nor calf ever dies...
 * <p />
 * <pre>
 *     y-1     y-2   y-3   y-4     y-5    y-6    y-7   y-8   y-9   y-10
 *     A1      A1    A1    A1      A1     A1
 *             B1    B1    B1->A2  A2     A2
 *                   B2    B2      B2->A3 A3
 *                         B3      B3     B3->A4
 *                                 B4     B4
 *                                 B5     B5
 *                                        B6
 *                                        B7
 *                                        B8
 *   1         2     3     4       6      9        13   19   28   41
 *
 *   y(n) = y(n-1) + y(n-3)   y(n) means the number of animals(cow + calf) in year n
 * </pre>
 */
public class HowManyCowsAndCalvesAfterYearsN extends BaseSolution<Integer> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 3;
    }

    public int count(int n) {
        if (versionToRun == 1) {
            return countRecursive(n);
        } else if (versionToRun == 2) {
            return countDP(n);
        } else if (versionToRun == 3) {
            return countLogN(n);
        } else {
            throw new RuntimeException("No such version: " + versionToRun);
        }
    }

	private int countRecursive(int n) {
		if (n <= 4) {
			return n;
		}
		return countRecursive(n - 1) + countRecursive(n - 3);
	}

	private int countDP(int n) {
		if (n <= 4) {
			return n;
		}
		int y1 = 1;
		int y2 = 2;
		int y3 = 3;
		int ret = 0;
		for (int i = 4; i <= n; i++) {
			ret = y3 + y1;
			y1 = y2;
			y2 = y3;
			y3 = ret;
		}
		return ret;
	}

	private int countLogN(int n) {
		if (n <= 4) {
			return n;
		}
		// |4, 3, 2| = |3, 2, 1| * |a, d, g|   3a + 2b +  c = 4
		//                         |b, e, h|   3d + 2e +  f = 3
		//                         |c, f, i|   3g + 2h +  i = 2
		// |6, 4, 3| = |4, 3, 2| * |a, d, g|   4a + 3b + 2c = 6
		//                         |b, e, h|   4d + 3e + 2f = 4
		//                         |c, f, i|   4g + 3h + 2i = 3
		// |9, 6, 4| = |6, 4, 3| * |a, d, g|   6a + 4b + 3c = 9
		//                         |b, e, h|   6d + 4e + 3f = 6
		//                         |c, f, i|   6g + 4h + 3i = 4
		//  a = 1                  |1, 1, 0|
		//  b = 0                  |0, 0, 1|
		//  c = 1                  |1, 0, 0|
		int[][] matrix = new int[][]{
			{1, 1, 0},
			{0, 0, 1},
			{1, 0, 0}
		};
		int[][] powered = MatrixMath.power(matrix, n - 3);
		return 3*powered[0][0] + 2*powered[1][0] + powered[2][0];
	}

	public static void main(String[] args) {
		HowManyCowsAndCalvesAfterYearsN sol = new HowManyCowsAndCalvesAfterYearsN();
		sol.runAllVersions("y(7)  = 13", () -> sol.count(7), 13);
		sol.runAllVersions("y(8)  = 19", () -> sol.count(8), 19);
		sol.runAllVersions("y(9)  = 28", () -> sol.count(9), 28);
		sol.runAllVersions("y(10) = 41", () -> sol.count(10), 41);

		int[] testData = DataHelper.genFixedSizeIntArrUniq(20, 1, 40);
		for (int i = 0; i < testData.length; i++) {
			final int n = testData[i];
			sol.runAllVersions("y(" + n + ")", () -> sol.count(n), null);
		}
		sol.performMeasure("20 random numbers 1~40", () -> {
			for (int i = 0; i < testData.length; i++) {
				sol.count(testData[i]);
			}
			return null;
		});
//		================================ performance Report ==========================
//		Version-1: Duration: PT0.009173566S
//		Version-2: Duration: PT0.000038362S   DP version is fastest for small n, but slower then v3 for large n
//		Version-3: Duration: PT0.000171564S   Matrix version is fastest for large n.
//	                                          Its constant time is much bigger than DP version. See below for large n.


		// v1 is slow when n is over 50 and grows exponentially
		// 100_000 also causes StackOverflowError due to system limitation of call stack frames
		sol.performMeasure("large number exclude v1", () -> sol.count(100_000), 1);
//		================================ performance Report ==========================
//		Version-2: Duration: PT0.001219899S
//		Version-3: Duration: PT0.000035637S  Matrix version is fastest for large n, near 40 times faster than DP version
	}
}
