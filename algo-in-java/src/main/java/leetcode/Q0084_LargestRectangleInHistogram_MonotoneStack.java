package leetcode;

import assist.BaseSolution;
import run.MetricsMemory;
import run.MetricsRuntime;

import java.util.Stack;

/**
 * source: https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
 * <p />
 * Given an array of integers heights representing the histogram's bar height where the width of each bar is 1,
 * return the area of the largest rectangle in the histogram.
 * <pre>
 *     Input: heights = [2,1,5,6,2,3]
 *     Output: 10
 *     Explanation: [5, 6] has the largest rectangle with an area of 5 * 2 = 10.
 * </pre>
 * <p />
 * Constraints:
 * <ul>
 *     <li>1 <= heights.length <= 10^5</li>
 *     <li>0 <= heights[i] <= 10^4</li>
 * </ul>
 */
public class Q0084_LargestRectangleInHistogram_MonotoneStack extends BaseSolution<Integer> {

    @Override
    protected int getNumberOfVersionsImplemented() {
        return 3;
    }

    public int largestRectangleArea(int[] heights) {
        if (versionToRun == 1) {
            return process_v1_no_stack(heights);
        } else if (versionToRun == 2) {
            return process_v2_in_system_stack(heights);
        } else if (versionToRun == 3) {
            return process_v3_in_custom_stack(heights);
        } else {
            throw new RuntimeException("No such version: " + versionToRun);
        }
    }

	// 93 / 99 testcases passed. Time Limit Exceeded
    private int process_v1_no_stack(int[] heights) {
		int len = heights.length;
		int ret = 0;		//  0 is min based on constraints
		// O(N^2) and lots of duplicated calculation
		for (int i = 0; i < len; i++) {
			int h = heights[i];

			int left = -1;
			for (int j = i - 1; j >= 0; j--) {
				if (heights[j] < h) {
					left = j;
					break;
				}
			}
			
			int right = -1;
			for (int j = i + 1; j < len; j++) {
				if (heights[j] < h) {
					right = j;
					break;
				}
			}
			int width = (right == -1 ? len : right) - left - 1;
			ret = Math.max(ret, width * h);
		}
        return ret;
    }

	@MetricsRuntime(ms = 53, beats = 78.83)
	@MetricsMemory(mb = 57.30, beats = 67.43)
    private int process_v2_in_system_stack(int[] heights) {
		int len = heights.length;
		int ret = 0;		
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < len; i++) {
			int h = heights[i];
			while (!stack.isEmpty() && heights[stack.peek()] >= h) {
				int targetIdx = stack.pop();
				if (heights[targetIdx] > h) {
					// right = i;
					int left = stack.isEmpty() ? -1 : stack.peek();
					int width = i - left - 1;
					ret = Math.max(ret, width * heights[targetIdx]);
				}	// else = h, skip this time, let i-th index h to calc.
			}
			stack.push(i);
		}

		// leftover which has no smaller in their right
		while (!stack.isEmpty()) {
			int idx = stack.pop();
			// right = len
			int left = stack.isEmpty() ? -1 : stack.peek();
			int width = len - left - 1;
			ret = Math.max(ret, width * heights[idx]);
		}

        return ret;
    }

	@MetricsRuntime(ms = 5, beats = 99.85)
	@MetricsMemory(mb = 59.82, beats = 10.81)
    private int process_v3_in_custom_stack(int[] heights) {
		int len = heights.length;
		int ret = 0;		
		int[] stack = new int[len];
		int si = 0;		// next stack.push, so the size = si
		for (int i = 0; i < len; i++) {
			int h = heights[i];
			while (si != 0 && heights[stack[si - 1]] >= h) {
				int targetIdx = stack[--si];
				if (heights[targetIdx] > h) {
					// right = i;
					int left = si == 0 ? -1 : stack[si - 1];
					int width = i - left - 1;
					ret = Math.max(ret, width * heights[targetIdx]);
				}	// else = h, skip this time, let i-th index h to calc.
			}
			stack[si++] = i;
		}

		// leftover which has no smaller in their right
		while (si != 0) {
			int idx = stack[--si];
			// right = len
			int left = si == 0 ? -1 : stack[si - 1];
			int width = len - left - 1;
			ret = Math.max(ret, width * heights[idx]);
		}

        return ret;
    }
}
