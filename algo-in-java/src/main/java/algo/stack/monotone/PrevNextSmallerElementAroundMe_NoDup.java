package algo.stack.monotone;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Given an array of integers, find the previous and next smaller element around me. If there is no smaller element,
 * return -1. If there are multiple smaller elements, return the closest one. The array has no duplicate elements.
 * The return array is a 2D array, each row is a pair of previous and next smaller element index in the original input.
 * <p />
 * Example: arr = {1, 3, 4, 2, 5}, return {{-1, -1}, {0, 3}, {1, 3}, {0, -1}, {3, -1}}
 */
public class PrevNextSmallerElementAroundMe_NoDup extends BaseSolution<int[][]> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 2;
    }

    @Override
    public String resultStringifier(int[][] result) {
        if (result == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < result.length; i++) {
            sb.append("{").append(result[i][0]).append(", ").append(result[i][1]).append("}");
            if (i < result.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean resultEquals(int[][] expected, int[][] result) {
        if (expected == result) {
            return true;
        }
        if (expected.length != result.length) {
            return false;
        }
        for (int i = 0; i < expected.length; i++) {
            if (expected[i].length != result[i].length) {
                return false;
            }
            for (int j = 0; j < expected[i].length; j++) {
                if (expected[i][j] != result[i][j]) {
                    System.out.printf("Mismatch at [%d][%d]: expected=%d, actual=%d%n", i, j, expected[i][j], result[i][j]);
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] nearestSmallerElemIndexes(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0][0];
        }
        if (versionToRun == 1) {
            return process_v1_no_monotone_stack(arr);
        } else if (versionToRun == 2) {
            return process_v2_monotone_stack(arr);
        } else {
            throw new IllegalStateException("Unsupported version: " + versionToRun);
        }
    }

    private int[][] process_v1_no_monotone_stack(int[] arr) {
		int len = arr.length;
		int[][] ret = new int[len][2];
		for (int i = 0; i < len; i++) {
			int v = arr[i];

			int leftSmall = -1;
			for (int j = i - 1; j >= 0; j--) {
				if (v > arr[j]) {
					leftSmall = j;
					break;
				}
			}

			int rightSmall = -1;
			for (int j = i + 1; j < len; j++) {
				if (v > arr[j]) {
					rightSmall = j;
					break;
				}
			}
			
			ret[i][0] = leftSmall;
			ret[i][1] = rightSmall;
		}
		return ret;
    }

    private int[][] process_v2_monotone_stack(int[] arr) {
		int len = arr.length;
		int[][] ret = new int[len][2];
		Stack<Integer> stack = new Stack<>();	// increasing large
		// O(N) instead of O(N^2)
		for (int i = 0; i < len; i++) {
			int v = arr[i];
			while (!stack.isEmpty() && arr[stack.peek()] > v) {
				int idx = stack.pop();
				ret[idx][1] = i;		// right/next
				ret[idx][0] = stack.isEmpty() ? -1 : stack.peek(); // left/prev
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int idx = stack.pop();
			ret[idx][1] = -1;
			ret[idx][0] = stack.isEmpty() ? -1 : stack.peek();	
		}
		return ret;
    }

    public static void main(String[] args) {
        PrevNextSmallerElementAroundMe_NoDup sol = new PrevNextSmallerElementAroundMe_NoDup();
        sol.runAllVersions("Example 1",
                () -> sol.nearestSmallerElemIndexes(new int[]{1, 3, 4, 2, 5}),
                new int[][]{{-1, -1}, {0, 3}, {1, 3}, {0, -1}, {3, -1}});
        // high volume test to ensure all versions verify each other
        final int cycles = 10_000;
        final int[][] testData = new int[cycles][];
        long totalSize = 0;
        for (int i = 0; i < cycles; i++) {
            final int[] tmp = DataHelper.generateRandomUniqData(10, 0, 10000);
            totalSize += tmp.length;
            sol.runAllVersions("Random " + (i + 1) + ": " + Arrays.toString(tmp),
                    () -> sol.nearestSmallerElemIndexes(tmp),
                    null);
            testData[i] = tmp;
        }
        // performance test
        sol.performMeasure("Performance test with " + cycles + " random data with avg size " + (totalSize / cycles),
                () -> {
                    for (int i = 0; i < cycles; i++) {
                        sol.nearestSmallerElemIndexes(testData[i]);
                    }
                    return null;
                });

//        ================================ performance Report ==========================
//        Version-1: Duration: PT0.006021639S       // O(N^2)
//        Version-2: Duration: PT0.004386417S       // O(N) with monotone stack 1.5x faster or roughly same
    }
}
