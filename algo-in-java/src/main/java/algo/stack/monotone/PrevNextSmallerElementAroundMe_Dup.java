package algo.stack.monotone;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * {@link algo.stack.monotone.PrevNextSmallerElementAroundMe_NoDup} has no duplicate elements. This allows duplicates.
 */
public class PrevNextSmallerElementAroundMe_Dup extends BaseSolution<int[][]> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 3;
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
            return process_v2_monotone_stack_dup_in_stack(arr);
        } else if (versionToRun == 3) {
            return process_v3_monotone_stack_list_in_stack(arr);
        } else {
            throw new IllegalStateException("Unsupported version: " + versionToRun);
        }
    }

	private int[][] process_v1_no_monotone_stack(int[] arr) {
		int len = arr.length;
		int[][] ret = new int[len][2];
		for (int i = 0; i < len; i++) {
			int v = arr[i];

			int left = -1;
			for (int j = i - 1; j >= 0; j--) {
				if (arr[j] < v) {
					left = j;
					break;
				}
			}
			ret[i][0] = left;

			int right = -1;
			for (int j = i + 1; j < len; j++) {
				if (arr[j] < v) {
					right = j;
					break;
				}
			}
			ret[i][1] = right;
		}
		return ret;
	}

	// still need list structure, but is needed when pop-ing, not push-ing
	private int[][] process_v2_monotone_stack_dup_in_stack(int[] arr) {
		int len = arr.length;
		int[][] ret = new int[len][2];
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < len; i++) {
			int v = arr[i];
			while (!stack.isEmpty() && arr[stack.peek()] > v) {
				int gt = stack.pop();
				ret[gt][1] = i;
				if (stack.isEmpty()) {
					ret[gt][0] = -1;
				} else if (arr[stack.peek()] == arr[gt]) { // dup one
					List<Integer> dups = new ArrayList<>(8);
					while (!stack.isEmpty() && arr[stack.peek()] == arr[gt]) {
						int idx = stack.pop();
						ret[idx][1] = i;
						dups.add(idx);
					}
					int left = stack.isEmpty() ? -1 : stack.peek();
					ret[gt][0] = left;
					for (Integer idx: dups) {
						ret[idx][0] = left;
					}
				} else {		// smaller
					ret[gt][0] = stack.peek();
				}
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int idx = stack.pop();
			ret[idx][1] = -1;
			if (stack.isEmpty()) {
				ret[idx][0] = -1;
			} else if (arr[stack.peek()] == arr[idx]) { // dup
				List<Integer> dups = new ArrayList<>(8);
				while (!stack.isEmpty() && arr[stack.peek()] == arr[idx]) {
					int di = stack.pop();
					ret[di][1] = -1;
					dups.add(di);
				}
				int left = stack.isEmpty() ? -1 : stack.peek();
				ret[idx][0] = left;
				for (Integer di: dups) {
					ret[di][0] = left;
				}
			} else {	// smaller than
				ret[idx][0] = stack.peek();
			}
		}
		return ret;
	}

	private int[][] process_v3_monotone_stack_list_in_stack(int[] arr) {
		int len = arr.length;
		int[][] ret = new int[len][2];
		Stack<List<Integer>> stack = new Stack<>();
		for (int i = 0; i < len; i++) {
			int v = arr[i];
			while (!stack.isEmpty() && arr[stack.peek().getFirst()] > v) {
				List<Integer> gtList = stack.pop();
				int left = stack.isEmpty() ? -1 : stack.peek().getLast();
				for (Integer gtI: gtList) {
					ret[gtI][0] = left;
					ret[gtI][1] = i;
				}
			}
			if (!stack.isEmpty() && arr[stack.peek().getFirst()] == v) {
				stack.peek().add(i);
			} else {
				List<Integer> list = new ArrayList<Integer>(8);
				list.add(i);
				stack.push(list);
			}
		}
		while (!stack.isEmpty()) {
			List<Integer> list = stack.pop();
			final int left = stack.isEmpty() ? -1 : stack.peek().getLast();
			final int right = -1;
			for (Integer i: list) {
				ret[i][0] = left;
				ret[i][1] = right;
			}
		}
		return ret;
	}

    public static void main(String[] args) {
        PrevNextSmallerElementAroundMe_Dup sol = new PrevNextSmallerElementAroundMe_Dup();
        sol.runAllVersions("Example 1",
                () -> sol.nearestSmallerElemIndexes(new int[]{1, 3, 4, 2, 5}),
                new int[][]{{-1, -1}, {0, 3}, {1, 3}, {0, -1}, {3, -1}});
        sol.runAllVersions("Example 2",
                () -> sol.nearestSmallerElemIndexes(new int[]{1, 3, 4, 3, 2}),
                new int[][]{{-1, -1}, {0, 4}, {1, 3}, {0, 4}, {0, -1}});
        // high volume test to ensure all versions verify each other
        final int cycles = 10_000;
        final int[][] testData = new int[cycles][];
        long totalSize = 0;
        for (int i = 0; i < cycles; i++) {
            final int[] tmp = DataHelper.genRandomSizeIntArr(10, 0, 50);
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
//        Version-1: Duration: PT0.005149103S
//        Version-2: Duration: PT0.003678458S
//        Version-3: Duration: PT0.003822679S
    }

}
