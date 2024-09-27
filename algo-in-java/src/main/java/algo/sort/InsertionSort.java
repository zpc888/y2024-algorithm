package algo.sort;

public class InsertionSort implements ArrIntSort {
    public void sort(int[] data) {
        if (data == null || data.length < 2) {
            return;
        }
        for (int i = 1; i < data.length; i++) {
            int value = data[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (data[j] > value) {
                    data[j + 1] = data[j];
                } else {
                    break;
                }
            }
            data[j + 1] = value;
        }
    }
}
