package algo.bitwise;

public class FindTheRightMostBitOne {
    public static void main(String[] args) {
        System.out.println(findTheRightMostBitOne(18));
    }

    private static int findTheRightMostBitOne(int n) {
        // 001010110   : n
        // 110101001   : ~n
        // 110101010   : ~n + 1
        // 000000010   : n & (~n + 1)
        return n & -n;          // n & (~n + 1)
    }
}
