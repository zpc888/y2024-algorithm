package algo.sort;

public class QuickSort implements ArrIntSort {
    public void sort(int data[]) {
        if (data == null || data.length < 2) {
            return;
        }
        quickSort(data, 0, data.length - 1);
    }

    private void quickSort(int data[], int bgn, int end) {
        if (bgn >= end) {
            return;
        }
        int eqPartLR[] = partition(data, bgn, end);         // less-than part, equal-to part (returning beginning and end index), greater-than part
        if (eqPartLR[0] > bgn) {        // having less-than part
            quickSort(data, bgn, eqPartLR[0] - 1);
        }
        if (eqPartLR[1] < end) {        // having greater-than part
            quickSort(data, eqPartLR[1] + 1, end);
        }
    }

    /**
     * Partition the data into 3 parts: left part is less than the pivot, right part is greater than the pivot,
     * and the middle part is equal to the pivot. In this algorithm, the pivot is the last element of the data, i.e. data[end].
     *
     * @param data
     * @param bgn
     * @param end
     * @return a 2-element array, the first element is the start index of the middle part,
     * which is the first element that is equal to the pivot, and the second element is the end index of the middle part.
     */
    private int[] partition(int data[], int bgn, int end) {
        int pivot = data[end];
        int less = bgn - 1;    // the index of the last element that is less than the pivot
        int greater = end;     // the index of the first element that is greater than the pivot
        for (int i = bgn; i < greater; i++) {
            if (data[i] < pivot) {
                swap(data, i, ++less);
            } else if (data[i] > pivot) {
                swap(data, i--, --greater);
            } // else equal to the pivot, do nothing (i++) for next loop
        }
        swap(data, greater, end);
        return new int[] {less + 1, greater};
    }

    private void swap(int data[], int i, int j) {
//        if (i == j) {
//            return;
//        }
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
