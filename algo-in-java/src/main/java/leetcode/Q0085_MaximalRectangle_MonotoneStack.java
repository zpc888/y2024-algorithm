package leetcode;

import assist.BaseSolution;
import run.MetricsMemory;
import run.MetricsRuntime;

/**
 * source: https://leetcode-cn.com/problems/maximal-rectangle/
 * <p/>
 * Maximal Rectangle
 * <p/>
 * Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.
 * <pre>
 * Example 1:
 * Input: matrix = [
 *                  ["1","0","1","0","0"],
 *                  ["1","0","1","1","1"],
 *                  ["1","1","1","1","1"],
 *                  ["1","0","0","1","0"]
 *                 ]
 * Output: 6
 *
 * Example 2:
 * Input: matrix = []
 * Output: 0
 *
 * Example 3:
 * Input: matrix = [["0"]]
 * Output: 0
 *
 * Example 4:
 * Input: matrix = [["1"]]
 * Output: 1
 *
 * Example 5:
 * Input: matrix = [["0","0"]]
 * Output: 0
 *
 * Constraints:
 * rows == matrix.length
 * cols == matrix[i].length
 * 0 <= row, cols <= 200
 * matrix[i][j] is '0' or '1'.
 * </pre>
 */
public class Q0085_MaximalRectangle_MonotoneStack extends BaseSolution<Integer> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 1;
    }

    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
		if (versionToRun == 1) {
			return processV1(matrix);
		} else {
			throw new RuntimeException("Unsupported version: " + versionToRun);
		}
    }

    @MetricsRuntime(ms = 3, beats = 94.74)
    @MetricsMemory(mb = 47.48, beats = 15.84)
    int processV1(char[][] matrix) {
		int row = matrix.length;
		int col = matrix[0].length;
		int[] heights = new int[col];

		int[] stack = new int[col];
		int ret = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				char cell = matrix[i][j];
				heights[j] = cell == '0' ? 0 : (heights[j] + 1);
			}
			ret = Math.max(ret, maxArea(heights, stack));
		}
		return ret;
	}

	int maxArea(int[] heights, int[] stack) {
		int len = heights.length;
		int si = 0;		// next idx for push; peek is (si - 1)
		int ret = 0;
		for (int i = 0; i < len; i++) {
			int h = heights[i];
			while (si != 0 && heights[stack[si - 1]] >= h) {
				int idx = stack[--si];
				if (heights[idx] > h) {
					// right = i;
					int left = si == 0 ? -1 : stack[si - 1];
					int width = i - left - 1;
					int area = width * heights[idx];
					ret = Math.max(ret, area);
				}	// else skip = h, since i-th idx h will do that
			}
			stack[si++] = i;
		}

		while (si != 0) {
			int idx = stack[--si];
			// right = len;
			int left = si == 0 ? -1 : stack[si - 1];
			int width = len - left - 1;
			int area = width * heights[idx];
			ret = Math.max(ret, area);
		}
		return ret;
	}
}
