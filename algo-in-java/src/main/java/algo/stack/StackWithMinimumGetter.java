package algo.stack;

import java.util.Stack;

public class StackWithMinimumGetter {
    private final Stack<Integer> data;
    private final Stack<Integer> mimimums;

    public StackWithMinimumGetter() {
        data = new Stack<>();
        mimimums = new Stack<>();
    }

    public int size() {
        return data.size();
    }

    public int peek() {
        return data.peek();
    }

    public int getMin() {
        return mimimums.peek();
    }

    public int pop() {
        mimimums.pop();
        return data.pop();
    }
    
    public void push(int i) {
        data.push(i);
        if (mimimums.isEmpty()) {
            mimimums.push(i);
        } else {
            mimimums.push(Math.min(i, mimimums.peek()));
        }
    }
}
