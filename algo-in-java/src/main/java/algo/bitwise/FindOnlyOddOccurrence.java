package algo.bitwise;

/**
 * an array where every element occurs even number of times except one element, find out that element
 */
public class FindOnlyOddOccurrence {
    public static void main(String[] args) {
        int data[] = {1, 2, 3, 2, 3, 1, 3};
        System.out.println(findOnlyOddOccurrence(data));
    }

    private static int findOnlyOddOccurrence(int data[]) {
        int result = 0;
        for (int i = 0; i < data.length; i++) {
            result ^= data[i];
        }
        return result;
    }
}
