package leetcode;

import assist.BaseSolution;
import run.MetricsMemory;
import run.MetricsRuntime;

/**
 * Source: https://leetcode.com/problems/count-submatrices-with-all-ones/
 * <p />
 * Given a rows * columns matrix mat of ones and zeros, return how many submatrices have all ones.
 * <pre>
 *     Example 1:
 *     Input: mat = [
 *                      [1,0,1],
 *                      [1,1,0],
 *                      [1,1,0]
 *                  ]
 *    Output: 13
 *
 *    Example 2:
 *    Input: mat = [
 *                  [0,1,1,0],
 *                  [0,1,1,1],
 *                  [1,1,1,0]
 *                  ]
 *   Output: 24
 *
 *   Constraints:
 *   1 <= rows, cols <= 150
 *   0 <= mat[i][j] <= 1
 * </pre>
 */
public class Q1504_CountSubmatricesWithAllOnes_MonotoneStack_VS_DP extends BaseSolution<Integer> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 2;
    }

    public int numSubmat(int[][] mat) {
        if (versionToRun == 1) {
            return process_v1_dp(mat);
        } else if (versionToRun == 2) {
            return process_v2_monotoneStack(mat);
        } else {
            throw new IllegalArgumentException("Invalid version to run: " + versionToRun);
        }
    }

	/**
	 * Monotone stack is different from dp. It counts row by row to see how 
	 * many submatrices ending row-i with monotone stack to count.
	 */
    @MetricsRuntime(ms = 4, beats = 99.27)
    @MetricsMemory(mb = 46.07, beats = 9.85)
    int process_v2_monotoneStack(int[][] mat) {
		int rows = mat.length;
		int cols = mat[0].length;
		int[] heights = new int[cols];
		int[] stack = new int[cols];
		int ret = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				int cell = mat[i][j];
				heights[j] = cell == 0 ? 0 : heights[j] + 1;
			}
			ret += countSubMatEndingRowI(heights, stack);
		}
        return ret;
    }

	int countSubMatEndingRowI(int[] heights, int[] stack) {
		int len = heights.length;
		int si = 0;		// next push index
		int ret = 0;
		for (int i = 0; i < len; i++) {
			int h = heights[i];
			while (si != 0 && heights[stack[si - 1]] >= h) {
				int idx = stack[--si];
				if (heights[idx] > h) {
					// right smaller idx = i, height = h
					int leftIdx = si == 0 ? -1 : stack[si - 1];
					int leftHeight = leftIdx == -1 ? 0 : heights[leftIdx];
					int width = i - leftIdx - 1;
					int raisedHieght = heights[idx] - Math.max(h, leftHeight);
					// the lower submatrices calculation will be triggered 
					// when popping out the lower height value
					ret += raisedHieght * (width + 1) * width / 2;
				}	// else = h, skip prev dup, let this dup to do calc later
			}
			stack[si++] = i;
		}

		while (si != 0) {
			int idx = stack[--si];
			// right smaller idx = len, height = 0
			int leftIdx = si == 0 ? -1 : stack[si - 1];
			int leftHeight = leftIdx == -1 ? 0 : heights[leftIdx];
			int width = len - leftIdx - 1;
			int raisedHieght = heights[idx] - leftHeight;
			ret += raisedHieght * (width + 1) * width / 2;
		}
		return ret;
	}

	/**
	 * dp[i][j] means i-row and j-col stores the number of matrix for i-row. 
	 * Its calculation will loop k from [i, j], [i-1, j], [i-2, j], ..., [0, j]
	 * to add-up by keeping stateful min = Math.min(min, dp[k][j]). In another
	 * word, how many matrix will end with [i, j] corner? By count += min, 
	 * final count will have all submatrices from ending [0,0] to [n-1, m-1]
	 */
    @MetricsRuntime(ms = 7, beats = 69.71)
    @MetricsMemory(mb = 44.72, beats = 99.64)
    int process_v1_dp(int[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;
        int[][] dp = new int[rows][cols];
        int count = 0;
        for (int i = 0; i < rows; i++) {
            int sum = 0;
            for (int j = 0; j < cols; j++) {
                if (mat[i][j] == 0) {
                    sum = 0;
                } else {
                    sum++;
                }
                dp[i][j] = sum;
                int min = sum;
                for (int k = i; k >= 0; k--) {
                    // count matrices ending with (i, j):
                    // 1. from (i, j), to (i - 1, j), (i - 2, j), ..., (0, j)
                    // 2. min is stateful for this loop, that is the most important factor for this solution
                    min = Math.min(min, dp[k][j]);
                    if (min == 0) {
                        break;      // no need to continue since it is impossible to form a matrix
                                    // from now on ending with (i, j)
                    }
                    count += min;
                }
            }
        }
        return count;
    }
}

