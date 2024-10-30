package algo.bfprt;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.Arrays;

/**
 * Source: https://en.wikipedia.org/wiki/Median_of_medians
 * <p />
 * Find the kth smallest number in an unsorted array in O(n) time complexity.
 */
public class KthSmallerNumber extends BaseSolution<Integer> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 3;
    }

    public int findKthSmallest(int[] arr, int k) {
        if (arr == null || arr.length < k) {
            return -1;
        }
        if (versionToRun == 1) {
            return v1SortThenGet(arr, 0, arr.length - 1, k - 1);
		} else if (versionToRun == 2) {
            return v2ModifiedQuickSort(arr, 0, arr.length - 1, k - 1);
		} else if (versionToRun == 3) {
			return v3BFPRT(arr, 0, arr.length - 1, k - 1);
        } else {
            throw new RuntimeException("No such version: " + versionToRun);
        }
    }

	private int v3BFPRT(int[] arr, int from, int to, int kIdx) {
		int[] qSorted = v3Partition(arr, from, to);
		if (qSorted[0] <= kIdx && qSorted[1] >= kIdx) {	// kIdx hits pivot value
			return arr[qSorted[0]];
		}
		if (qSorted[0] > kIdx) {
			return v3BFPRT(arr, from, qSorted[0] - 1, kIdx);
		} else {
			// qSorted[1] < kIdx
			return v3BFPRT(arr, qSorted[1] + 1, to, kIdx);
		}
	}

	private int[] v3Partition(int[] arr, int from, int to) {
		int pivot = getMedianOfMedians(arr, from, to);
		int l = from - 1;
		int r = to + 1;
		int i = from;
		while (i < r) {
			if (arr[i] < pivot) {
				swap(arr, i++, ++l);
			} else if (arr[i] == pivot) {
				i++;
			} else {	// > pivot
				swap(arr, i, --r);
			}
		}
		return new int[]{l + 1, r - 1};
	}

	private int getMedianOfMedians(int[] arr, int from, int to) {
		final int len = to - from + 1;
		if (len <= 5) {
			return getMedianFromMax5(arr, from, to);
		} else {
			int size = len / 5;
			if (len % 5 != 0) {
				size++;
			}
			int[] medians = new int[size];
			for (int i = 0; i < size; i++) {
				medians[i] = getMedianFromMax5(arr, 
						from + i*5, Math.min(from + i*5 + 4, to));
			}
			return v3BFPRT(medians, 0, size - 1, size / 2);
		}
	}

	private int getMedianFromMax5(int[] arr, int from, int to) {
		// insertion sort
		for (int i = from + 1; i <= to; i++) {
			// try to place i item into [from, i-1] already sorted array
			for (int j = i; j > from; j--) {
				if (arr[j] < arr[j-1]) {
					swap(arr, j, j-1);
				} else {
					break;
				}
			}
		}
		return arr[from + (to - from) / 2];
	}

	private int v2ModifiedQuickSort(int[] arr, int from, int to, int kIdx) {
		int[] qSorted = v2Partition(arr, from, to);
		if (qSorted[0] <= kIdx && qSorted[1] >= kIdx) {	// kIdx hits pivot value
			return arr[qSorted[0]];		
		}
		if (qSorted[0] > kIdx) {
			return v2ModifiedQuickSort(arr, from, qSorted[0] - 1, kIdx);
		} else {
			// qSorted[1] < kIdx
			return v2ModifiedQuickSort(arr, qSorted[1] + 1, to, kIdx);
		}
	}

	private int[] v2Partition(int[] arr, int from, int to) {
		int pivotIdx = from + (int) (Math.random() * (to - from + 1));
		swap(arr, pivotIdx, to);
		int pivotVal = arr[to];
		int l = from - 1;
		int r = to;
		int i = from;
		while (i < r) {
			if (arr[i] < pivotVal) {
				swap(arr, i++, ++l);
			} else if (arr[i] == pivotVal) {
				i++;
			} else {		// bigger than pivotVal
				swap(arr, i, --r);
			}
		}
		swap(arr, r, to);
		return new int[]{l + 1, r};
	}

	private void swap(int[] arr, int idx1, int idx2) {
		int tmp = arr[idx1];
		arr[idx1] = arr[idx2];
		arr[idx2] = tmp;
	}

    private int v1SortThenGet(int[] arr, int from, int to, int kIdx) {
		doMergeSort(arr, from, to);
		return arr[kIdx];
    }

	private void doMergeSort(int[] arr, int from, int to) {
		if (from >= to) {
			return;
		}
		int mid = from + (to - from)/2;
		doMergeSort(arr, from, mid);
		doMergeSort(arr, mid + 1, to);
		merge(arr, from, mid, to);
	}

	private void merge(int[] arr, int from, int mid, int to) {
		int[] tmp = new int[to - from + 1];
		int idx = 0, idx1 = from, idx2 = mid + 1;
		while (idx1 <= mid && idx2 <= to) {
			tmp[idx++] = arr[idx1] < arr[idx2] ? arr[idx1++] : arr[idx2++];
		}
		while (idx1 <= mid) {
			tmp[idx++] = arr[idx1++];
		}
		while (idx2 <= to) {
			tmp[idx++] = arr[idx2++];
		}
		for (int i = 0; i < tmp.length; i++) {
			arr[from + i] = tmp[i];
		}
	}

	public static void main(String[] args) {
		KthSmallerNumber sol = new KthSmallerNumber();
		sol.runAllVersions("debug 01",
				() -> sol.findKthSmallest(new int[]{10, 5, 8, 9, 6, 4, 3, 7, 1, 2}, 8), 8);
		for (int i = 0; i < 10_000; i++) {
			final int[] arr = DataHelper.genFixedSizeIntArrUniq(30, 1, 30);
			final int k = (int) (Math.random() * 30) + 1;
			sol.runAllVersions("small sequence test " + (i + 1),
					() -> sol.findKthSmallest(arr, k), k);
		}
		sol.runAllVersions("example 1", () -> sol.findKthSmallest(new int[]{3, 2, 11, 5, 4}, 3), 4);
		sol.runAllVersions("example 2", () -> sol.findKthSmallest(new int[]{3, 2, 1, 5, 4}, 3), 3);

		final int cycles = 10_000;
		int[][] arr = new int[cycles][];
		int[] ks = new int[cycles];
		for (int i = 0; i < cycles; i++) {
			arr[i] = DataHelper.genRandomSizeIntArr(10_000, 1, 9_000);
			ks[i] = (int) (Math.random() * arr[i].length) + 1;
		}
		for (int i = 0; i < cycles; i++) {
			final int idx = i;
			sol.runAllVersions("Random " + (i + 1),
					() -> sol.findKthSmallest(arr[idx], ks[idx]), null);
		}
		sol.performMeasure("Run " + cycles + " times with random size between 0 and 10,000",
				() -> {
					for (int i = 0; i < cycles; i++) {
						sol.findKthSmallest(arr[i], ks[i]);
					}
					return null;
				});
//====================== < Run 10000 times with random size between 0 and 10,000 > Performance Report ==============
//		Version-1: Duration: PT1.442095682S
//		Version-2: Duration: PT0.188589136S
//		Version-3: Duration: PT0.407964356S			// bfprt spends lots of time/effort to select the median as pivot
													// but at the end, it's not faster than random pivot selection
	}

}
