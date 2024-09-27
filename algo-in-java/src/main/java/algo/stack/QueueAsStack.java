package algo.stack;

import java.util.Queue;

public class QueueAsStack {
    Queue<Integer> q1 = new java.util.LinkedList<>();
    Queue<Integer> q2 = new java.util.LinkedList<>();

    public void push(int data) {
        if (q2.isEmpty()) {
            q1.add(data);
        } else {
            q2.add(data);
        }
    }

    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        if (q1.isEmpty()) {
            while (q2.size() > 1) {
                q1.add(q2.remove());
            }
            return q2.remove();
        } else {
            while (q1.size() > 1) {
                q2.add(q1.remove());
            }
            return q1.remove();
        }
    }

    public boolean isEmpty() {
        return q1.isEmpty() && q2.isEmpty();
    }

    public int size() {
        return q1.size() + q2.size();
    }

    public static void main(String[] args) {
        QueueAsStack stack = new QueueAsStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        stack.push(4);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}
