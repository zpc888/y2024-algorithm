package algo.bitwise;

import java.util.function.Consumer;

public class MergeSort {

    public static void main(String[] args) {
        Consumer<int[]> consumer = arr -> {
            MergeSort obj = new MergeSort();
            obj.mergeSort(arr);
            for (int i : arr) {
                System.out.print(i + " ");
            }
            System.out.println();
        };
        consumer.accept(new int[]{1, 2, 3});
        consumer.accept(new int[]{1, 2, 3, 4});
        consumer.accept(new int[]{2, 1, 4, 4});
        consumer.accept(new int[]{5, 2, 3, 1});
        consumer.accept(new int[]{5, 2, 3, 1, 4});
    }

    void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        doMergeSort(arr, 0, arr.length - 1);
    }

    private void doMergeSort(int[] arr, int index1, int index2) {
        if (index1 >= index2) {
            return;
        }
        int mid = index1 + ((index2 - index1)>> 1);
        doMergeSort(arr, index1, mid);
        doMergeSort(arr, mid + 1, index2);
        doMerge(arr, index1, mid, index2);
    }

    private void doMerge(int[] arr, int index1, int mid, int index2) {
        int[] tmp = new int[index2 - index1 + 1];
        int i = index1;
        int j = mid + 1;
        int k = 0;
        while (i <= mid || j <= index2) {
            if (i > mid) {
                tmp[k++] = arr[j++];
            } else if (j > index2) {
                tmp[k++] = arr[i++];
            } else if (arr[i] < arr[j]) {
                tmp[k++] = arr[i++];
            } else {
                tmp[k++] = arr[j++];
            }
        }
        k = 0;
        for (i = index1; i <= index2; i++) {
            arr[i] = tmp[k++];
        }
    }
}
