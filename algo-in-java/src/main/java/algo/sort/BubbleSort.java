package algo.sort;

public class BubbleSort implements ArrIntSort {
    public void sort(int[] data) {
        if (data == null || data.length < 2) {
            return;
        }
        for (int end = data.length - 1; end > 0; end--) {
            for (int i = 0; i < end; i++) {
                if (data[i] > data[i + 1]) {
                    swap(data, i, i + 1);
                }
            }
        }
    }

    private void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
