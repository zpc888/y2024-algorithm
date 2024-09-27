package algo.bitwise;

/**
 * An array contains all the integers from 0 to n, except for two numbers which occurs odd number of times,
 * the rest are all even. Find the two numbers that occurs odd number of times.
 */
public class FindTwoOddOccurrence {
    public int[] findTwoOddOccurrence(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }
        int xor = 0;
        for (int i = 0; i < arr.length; i++) {
            xor ^= arr[i];
        }
        int rightMostOneBit = xor & (~xor + 1);
        int ret[] = new int[2];
        for (int i = 0; i < arr.length; i++) {
            if ((rightMostOneBit & arr[i]) == 0) {
                ret[0] ^= arr[i];
            } else {
                ret[1] ^= arr[i];
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        FindTwoOddOccurrence findTwoOddOccurrence = new FindTwoOddOccurrence();
        int[] arr = {1, 2, 3, 4, 5, 6, 4, 4, 4, 1, 2, 3};
        int[] ret = findTwoOddOccurrence.findTwoOddOccurrence(arr);
        System.out.println(ret[0] + " " + ret[1]);
    }
}
