package algo.queue;

public class QueueInLoopArray {
    private int[] arr;
    private final int capacity;
    private int head;
    private int tail;
    private int size = 0;

    public QueueInLoopArray(int capacity) {
        this.capacity = capacity;
        arr = new int[capacity];
        head = 0;
        tail = 0;
        size = 0;
    }

    public boolean enqueue(int val) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        size++;
        arr[tail] = val;
        tail = (tail + 1) % capacity;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(" -> ");
            }
            sb.append(arr[(head + i) % capacity]);
        }
        return sb.toString();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return arr[head];
    }

    public int dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        int ret = arr[head];
        head = (head + 1) % capacity;
        size--;
        return ret;
    }

    public static void main(String[] args) {
        QueueInLoopArray queue = new QueueInLoopArray(5);
        System.out.println(queue.enqueue(1));
        System.out.println(queue);
        System.out.println(queue.enqueue(2));
        System.out.println(queue);
        System.out.println(queue.enqueue(3));
        System.out.println(queue);
        System.out.println(queue.enqueue(4));
        System.out.println(queue);
        System.out.println(queue.enqueue(5));
        System.out.println(queue);
        try {
            System.out.println(queue.enqueue(6));
            System.out.println("Failed. Size is 5, but tried to enqueue 6-th element");
            return;
        } catch (Exception e) {
            System.out.println("Excepted exception: " + e.getMessage());
        }
        System.out.println(queue);
        System.out.println("====================================");
        while (!queue.isEmpty()) {
            System.out.println("after dequeue " + queue.dequeue() + " operation, the queue is: " + queue);
        }
    }
}
