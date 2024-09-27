package algo.sort;

public class SelectionSort implements ArrIntSort {
    public void sort(int[] data) {
        if (data == null || data.length < 2) {
            return;
        }
        for (int i = 0; i < data.length; i++) {
            int minPos = i;
            for (int j = i + 1; j < data.length; j++) {
                if (data[j] < data[minPos]) {
                    minPos = j;
                }
            }
            if (minPos != i) {
                swap(data, i, minPos);
            }
        }
    }

    private void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
