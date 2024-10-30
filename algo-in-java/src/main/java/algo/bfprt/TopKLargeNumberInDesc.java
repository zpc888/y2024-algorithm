package algo.bfprt;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Find the top K large numbers in descending order from an unsorted array.
 */
public class TopKLargeNumberInDesc extends BaseSolution<int[]> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 3;
    }

	@Override
	protected String resultStringifier(int[] result) {
		return Arrays.toString(result);
	}

	@Override
	protected boolean resultEquals(int[] expected, int[] actual) {
		return DataHelper.isArrayEqual(expected, actual);
	}

	public int[] findTopKLargeNumbers(int[] arr, int k) {
        if (arr == null || arr.length < k) {
            return new int[0];
        }
        if (versionToRun == 1) {
            return v1SortThenGet(arr, k);
        } else if (versionToRun == 2) {
			return v2HeapGet(arr, k);
        } else if (versionToRun == 3) {
			return v3QuickSortThenSortK(arr, k);
		} else {
			throw new IllegalStateException("Unsupported version: " + versionToRun);
		}
    }

	private int[] v3QuickSortThenSortK(int[] arr, int k) {
		int[] topK = doModifiedQuickSort(arr, 0, arr.length - 1, k);
		doBubbleSort(topK);		// sorting
		return topK;
	}

	private int[] doModifiedQuickSort(int[] arr, int l, int r, int k) {
		int[] eqRange = partition(arr, l, r);
		int k0 = k - 1;
		if (eqRange[0] <= k0 && eqRange[1] >= k0) {
			int[] ret = new int[k];
			System.arraycopy(arr, 0, ret, 0, k);
			return ret;
		}
		if (eqRange[0] > k0) {
			return doModifiedQuickSort(arr, l, eqRange[0] - 1, k);
		} else {	// eqRange[1] < k
			return doModifiedQuickSort(arr, eqRange[1] + 1, r, k);
		}
	}

	private int[] partition(int[] arr, int l, int r) {
		int mid = l + (int)Math.random() * (r - l + 1);
		swap(arr, mid, r);
		int lessI = l - 1;
		int greatI = r;
		int pivot = arr[r];
		int i = l;
		while (i < greatI) {
			// descending order
			if (arr[i] > pivot) {
				swap(arr, i++, ++lessI);
			} else if (arr[i] == pivot) {
				i++;
			} else {	// less
				swap(arr, i, --greatI);
			}
		}
		swap(arr, r, greatI);		// pivot one replace the 1st greatI
		return new int[]{lessI + 1, greatI};
	}

	private int[] v2HeapGet(int[] arr, int k) {
		PriorityQueue<Integer> heap = new PriorityQueue<>( (n1, n2) -> n2 - n1 );
		for (int i = 0; i < arr.length; i++) {
			heap.offer(arr[i]);
		}
		int[] ret = new int[k];
		for (int i = 0; i < k; i++) {
			ret[i] = heap.poll();
		}
		return ret;
	}

	private int[] v1SortThenGet(int[] arr, int k) {
		doSelectionSort(arr);
		int[] ret = new int[k];
		for (int i = 0; i < k; i++) {
			ret[i] = arr[i];
		}
		return ret;
	}

	private void doSelectionSort(int[] arr) {
		int len = arr.length;
		for (int i = 0; i < len; i++) {
			int maxIdx = i;
			for (int j = i + 1; j < len; j++) {
				if (arr[maxIdx] < arr[j]) {
					maxIdx = j;
				}
			}
			swap(arr, i, maxIdx);
		}
	}

	private void doBubbleSort(int[] arr) {
		// descending order
		int len = arr.length;
		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				if (arr[i] < arr[j]) {
					swap(arr, i, j);
				}
			}
		}
	}

	private void swap(int[] arr, int idx1, int idx2) {
		int tmp = arr[idx1];
		arr[idx1] = arr[idx2];
		arr[idx2] = tmp;
	}


	public static void main(String[] args) {
		TopKLargeNumberInDesc sol = new TopKLargeNumberInDesc();
		for (int i = 1; i <= 10; i++) {
			final int k = i;
			sol.runAllVersions("debug " + i,
					() -> sol.findTopKLargeNumbers(new int[]{10, 5, 8, 9, 6, 4, 3, 7, 1, 2}, k),
					largeTopKFrom(k, 10));
		}
		final int largest = 30;
		for (int i = 0; i < 5_000; i++) {
			final int[] arr = DataHelper.genFixedSizeIntArrUniq(largest, 1, largest);
			final int k = (int) (Math.random() * largest) + 1;
			sol.runAllVersions("small sequence test " + (i + 1),
					() -> sol.findTopKLargeNumbers(arr, k), largeTopKFrom(k, largest));
		}
		sol.runAllVersions("example 1", () -> sol.findTopKLargeNumbers(new int[]{3, 2, 11, 5, 4}, 3),
				new int[]{11, 5, 4});
		sol.runAllVersions("example 2", () -> sol.findTopKLargeNumbers(new int[]{3, 2, 1, 5, 4}, 3),
				new int[]{5, 4, 3});

		final int cycles = 5_000;
		int[][] arr = new int[cycles][];
		int[] ks = new int[cycles];
		for (int i = 0; i < cycles; i++) {
			arr[i] = DataHelper.genRandomSizeIntArr(10_000, 1, 9_000);
			ks[i] = (int) (Math.random() * arr[i].length) + 1;
		}
		for (int i = 0; i < cycles; i++) {
			final int idx = i;
			sol.runAllVersions("Random " + (i + 1),
					() -> sol.findTopKLargeNumbers(arr[idx], ks[idx]), null);
		}
		sol.performMeasure("Run " + cycles + " times with random size between 0 and 10,000",
				() -> {
					for (int i = 0; i < cycles; i++) {
						sol.findTopKLargeNumbers(arr[i], ks[i]);
					}
					return null;
				});
//====================== < Run 5000 times with random size between 0 and 10,000 > Performance Report ==============
//		Version-1: Duration: PT25.26863889S
//		Version-2: Duration: PT1.169071997S	  in this case, heap is the fastest
//		Version-3: Duration: PT6.811801476S
	}

	private static int[] largeTopKFrom(int k, int largest) {
		int[] ret = new int[k];
		for (int i = 0; i < k; i++) {
			ret[i] = largest - i;
		}
		return ret;
	}
}
