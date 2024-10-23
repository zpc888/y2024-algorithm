package algo.stack.monotone;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.Arrays;
import java.util.Stack;

/**
 * Given an array of positive integers, find the maximum product of the sum of any subarray and the minimum element in the subarray.
 *
 * Example:
 * Input: [2, 3, 1, 2]
 * Output: 10
 * Explanation: The maximum product is 10 = 5 * 2, with the subarray [2, 3].
 *
 * Constraints:
 * - The length of the input array is in range [1, 10^5].
 * - The input array is in range [0, 10^5].
 * - The answer is guaranteed to fit in a 32-bit integer.
 */
public class MaxProductOfSubArrSumAndMinElement extends BaseSolution<Integer> {

    @Override
    protected int getNumberOfVersionsImplemented() {
        return 2;
    }

    public int maxProductOfSumAndMinElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int[] prevSums = new int[n];
        prevSums[0] = nums[0];
        for (int i = 1; i < n; i++) {
            prevSums[i] = prevSums[i - 1] + nums[i];
        }
        if (versionToRun == 1) {
            return processV1(nums, prevSums);
        } else if (versionToRun == 2) {
            return processV2(nums, prevSums);
        } else {
            throw new RuntimeException("No version implemented for: " + versionToRun);
        }
    }

	private int processV1(int[] nums, int[] prevSums) {
		int len = nums.length;
		int ret = -1;
		for (int i = 0; i < len; i++) {
			// assuming nums[i] is the min in the subarr
			// since min is fixed, its sum should contain as many more elems
			// as possible. And since it is the min elem, inside subarr, 
			// there should be no such a smaller one in left nor right 
			int v = nums[i];

			// step.1 find left smaller element index if there is
			int left = -1;
			for (int j = i - 1; j >= 0; j--) {
				if (nums[j] < v) {	// found
					left = j;
					break;
				}
			}
			// step.2 find right smaller elem index
			int right = -1;
			for (int j = i + 1; j < len; j++) {
				if (nums[j] < v) {
					right = j;
					break;
				}
			}

			int prevSumLeft = left == -1 ? 0 : prevSums[left];
			int prevSumRight = right == -1 ? prevSums[i] : prevSums[right - 1];
			int prod = (prevSumRight - prevSumLeft) * v;
			ret = Math.max(prod, ret);
		}
		return ret;
	}

	private int processV2(int[] nums, int[] prevSums) {
        int ret = -1;
		int len = nums.length;
		Stack<Integer> stack = new Stack<>();
		// treat dup to pop prev one since new one will be wider, its product
		// will be bigger than previous one. Although when popping prev dup,
		// it calculates (or optimized to ignore calculation), at the end,
		// the new dup will have the large product than the previous when 
		// popping out the new dup one
		for (int i = 0; i < len; i++) {
			int v = nums[i];
			while (!stack.isEmpty() && nums[stack.peek()] >= v) {
				int centerIdx = stack.pop();
				if (nums[centerIdx] > v) {
					int leftIdx = stack.isEmpty() ? -1 : stack.peek();
					int prevSumLeft = leftIdx == -1 ? 0 : prevSums[leftIdx];
					int prevSumRight = prevSums[i - 1];
					int prod = (prevSumRight - prevSumLeft) * nums[centerIdx];
					ret = Math.max(prod, ret);
				}	// else = can be skipped since v will win no matter what
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int centerIdx = stack.pop();
			int leftIdx = stack.isEmpty() ? -1 : stack.peek();
			int prevSumLeft = leftIdx == -1 ? 0 : prevSums[leftIdx];
			int prevSumRight = prevSums[centerIdx];
			int prod = (prevSumRight - prevSumLeft) * nums[centerIdx];
			ret = Math.max(prod, ret);
		}
		return ret;
	}

	public static void main(String[] args) {
		MaxProductOfSubArrSumAndMinElement sol = new MaxProductOfSubArrSumAndMinElement();
		final int[] nums = {2, 3, 1, 2};
		sol.runAllVersions(Arrays.toString(nums),
				() -> sol.maxProductOfSumAndMinElement(nums), 10);
		int cycles = 10_000;
		int[][] inputs = new int[cycles][];
		for (int i = 0; i < cycles; i++) {
			int[] randomData = DataHelper.genRandomSizeIntArr(20, 0, 100);
			sol.runAllVersions("Random " + (i+1) + ": " + Arrays.toString(randomData),
					() -> sol.maxProductOfSumAndMinElement(randomData), null);
			inputs[i] = randomData;
		}
		sol.performMeasure("Random data test * " + cycles,
				() -> {
					for (int i = 0; i < cycles; i++) {
						sol.maxProductOfSumAndMinElement(inputs[i]);
					}
					return null;
				});
//================================ performance Report ==========================
//		Version-1: Duration: PT0.004065696S
//		Version-2: Duration: PT0.004422468S    <-- monotone stack version is slower

		// v1 takes O(N^2) time complexity, v2 takes O(N) time complexity in the worst case
		// but in average case, v1 is faster than v2 since its constant time is much smaller
	}
}
