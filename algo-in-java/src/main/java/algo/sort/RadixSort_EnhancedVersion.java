package algo.sort;

import assist.DataHelper;

import static algo.sort.RadixSort_BasicVersion.getDigitAt;
import static algo.sort.RadixSort_BasicVersion.getMax;
import static algo.sort.RadixSort_BasicVersion.countDigit;

public class RadixSort_EnhancedVersion implements ArrIntSort {
	// arr only contains non-negative numbers
    public void sort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
        int max = getMax(arr);
		int maxDigitCounts = countDigit(max);
		int[] tmpResults = new int[arr.length];
		// for each cycle to store the i-th count at position k.
		// e.g. tmpCounts[2] = 5  for counting position 3. It means in arr, there are 5 items whose 3rd digit is 3 = 2 + 1 (0-based).
		// later this tmpCounts will store previous sum, e.g. 2, 6, 5, 0, 2, 0, 0, 1, 2, 3  
		// will become 2, 8, 13, 13, 15, 15, 15, 16, 18, 21 where 21 equals arr.length
		int[] tmpCounts = new int[10];
        for (int i = 0; i < maxDigitCounts; i++) {
			for (int j = 0; j < tmpCounts.length; j++) {
				tmpCounts[j] = 0;
			}
			// count i-th digit for each number
			for (int item: arr) {
				int digitI = getDigitAt(item, i+1);
				tmpCounts[digitI]++;
			}
			for (int j = 1; j < tmpCounts.length; j++) {
				tmpCounts[j] += tmpCounts[j-1];
			}
			for (int j = arr.length; j > 0; j--) {
				int item = arr[j - 1];
				int digitI = getDigitAt(item, i+1);
				int pos = tmpCounts[digitI] - 1;
				tmpResults[pos] = item;
				tmpCounts[digitI]--;		// next digitI if having should be right in front of current-pos
			}
			System.arraycopy(tmpResults, 0, arr, 0, arr.length);
        }
    }

	public static void main(String[] args) {
		RadixSort_EnhancedVersion rs = new RadixSort_EnhancedVersion();
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
