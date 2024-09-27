package algo.sort;

import java.util.Stack;

public class QuickSort_In_NoRecursive implements ArrIntSort {

    @Override
    public void sort(int[] data) {
        if (data == null || data.length < 2) {
            return;
        }
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[] { 0, data.length - 1 });
        quickSort(data, stack);
    }

    private void quickSort(int[] data, Stack<int[]> stack) {
        while (!stack.isEmpty()) {
            int[] pair = stack.pop();
            int low = pair[0];
            int high = pair[1];
            if (low < high) {
                int[] pi = partition(data, low, high);
                stack.push(new int[] { low, pi[0] - 1 });
                stack.push(new int[] { pi[pi.length - 1] + 1, high });
            }
        }
    }

    private int[] partition(int[] data, int low, int high) {
        int mid = low + ((high - low) >> 1);
        int pivot = data[mid];
        data[mid] = data[high];
        data[high] = pivot;
        int ltCursor = low - 1;
        int gtCursor = high;
        for (int j = low; j < gtCursor; j++) {
            if (data[j] < pivot) {
                swap(data, ++ltCursor, j);
            } else if (data[j] == pivot) {
               // j++
            } else {
               swap(data, --gtCursor, j--);
            }
        }
        swap(data, gtCursor, high);
        return new int[]{ltCursor + 1, gtCursor};
    }

    private void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
