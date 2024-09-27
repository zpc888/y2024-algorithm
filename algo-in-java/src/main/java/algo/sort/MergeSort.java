package algo.sort;

public class MergeSort implements ArrIntSort {
    @Override
    public void sort(int[] data) {
        if (data == null || data.length < 2) {
            return;
        }
        mergeSort(data, 0, data.length - 1);
    }

    private void mergeSort(int[] data, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(data, low, mid);
            mergeSort(data, mid + 1, high);
            merge(data, low, mid, high);
        }
    }

    private void merge(int[] data, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= high) {
            temp[k++] = data[i] <= data[j] ? data[i++] : data[j++];
        }
        while (i <= mid) {
            temp[k++] = data[i++];
        }
        while (j <= high) {
            temp[k++] = data[j++];
        }
        for (int m = 0; m < temp.length; m++) {
            data[low + m] = temp[m];
        }
    }
}
