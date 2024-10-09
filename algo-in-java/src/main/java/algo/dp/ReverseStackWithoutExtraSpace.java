package algo.dp;

import java.util.Arrays;
import java.util.Stack;

public class ReverseStackWithoutExtraSpace {
	public int[] reverse(int[] in) {
		if (in == null || in.length <= 1) {
			return in;
		}
		Stack<Integer> stack = new Stack<>();
		for (int i = in.length; i > 0; i--) {
			stack.push(in[i - 1]);
		}
		reverseStackWithoutExtraSpace(stack);
		int[] ret = new int[stack.size()];
		int idx = 0;
		while (!stack.isEmpty()) {
			ret[idx++] = stack.pop();
		}
		return ret;
	}


	/**
	 * Reverse a stack without using extra space. More accurately, it should be no an extra variable,
	 * space should be there in the call stack/frame.
	 *
	 * @param stack
	 */
	public void reverseStackWithoutExtraSpace(Stack<Integer> stack) {
		if (!stack.isEmpty()) {
			int top = stack.pop();
			injectToBottom(top, stack);
		}
	}

	/*
	[1, 2, 3] => [3, 2, 1]
	1 [2, 3]         ->      2 [3]      ->  3 []
	                                          \/
	[3, 2, 1]        <-       [3, 2]    <-    [3]

	 */
    private void injectToBottom(int top, Stack<Integer> stack) {
        if (stack.isEmpty()) {
            stack.push(top);
            return;
        }
        int temp = stack.pop();
        injectToBottom(temp, stack);

        pushBackToBottom(top, stack);
    }

    private void pushBackToBottom(int bottom, Stack<Integer> stack) {
        if (stack.isEmpty()) {
			stack.push(bottom);
		} else {
            int temp = stack.pop();
            pushBackToBottom(bottom, stack);
			stack.push(temp);
        }
    }


    public static void main(String[] args) {
		ReverseStackWithoutExtraSpace r = new ReverseStackWithoutExtraSpace();
		int[] input = new int[]{1, 2, 3, 4};
		int[] output = r.reverse(input);
		System.out.println("input  : " + Arrays.toString(input));
		System.out.println("output : " + Arrays.toString(output));
		if (input.length != output.length) {
			throw new IllegalStateException("length mismatch");
		}
		for (int i = 0; i < input.length; i++) {
			if (input[i] != output[input.length - i - 1]) {
				throw new IllegalStateException("element mismatch");
			}
		}
		System.out.println("reverse test passed");
	}
}
