package algo.sort;

import assist.DataHelper;

import java.util.ArrayDeque;
import java.util.Queue;

public class RadixSort_BasicVersion implements ArrIntSort {
	// arr only contains non-negative numbers
    public void sort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
        int max = getMax(arr);
		int maxDigitCounts = countDigit(max);
		Queue<Integer>[] radix = new Queue[10];
        for (int i = 0; i < maxDigitCounts; i++) {
			// sort by i-th digital into radix
			for (int item: arr) {
				int digitI = getDigitAt(item, i+1);
				if (radix[digitI] == null) {
					radix[digitI] = new ArrayDeque<>(Math.max(4, arr.length/4));
				}
				radix[digitI].offer(item);
			}
			int idx = 0;
			for (Queue<Integer> q: radix) {
				if (q != null) {
					while (!q.isEmpty()) {
						arr[idx++] = q.poll();
					}
				}
			}
        }
    }

	static int getDigitAt(int num, int pos) {
		int divisor = 10;
		for (int i = 1; i < pos; i++) {
			divisor *= 10;
		}
		int remainder = num % divisor;
		return remainder * 10 / divisor;
	}

	static int countDigit(int num) {
		int ret = 1;
		int divisor = 10;
		while (num >= divisor) {
			divisor *= 10;
			ret++;
		}
		return ret;
	}

	static int getMax(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, arr[i]);
		}
		return max;
	}

	public static void main(String[] args) {
		RadixSort_BasicVersion rs = new RadixSort_BasicVersion();
		int[] arr = {3, 2, 1, 4, 11, 5, 6, 20, 7, 8, 9, 10};
		DataHelper.printArray("Before sort: ", arr);
		rs.sort(arr);
		DataHelper.printArray("After  sort: ", arr);
		if (DataHelper.isAscending(arr)) {
			System.out.println("Succeed");
		} else {
			System.out.println("Failed");
		}
	}
}
