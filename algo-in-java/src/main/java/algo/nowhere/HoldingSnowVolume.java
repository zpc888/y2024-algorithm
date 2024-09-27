package algo.nowhere;

/**
 * an array indicates the hill height, calculate the volume of snow it can hold. The snow will fall if the hill is higher than its neighbors.
 * <pre>
 *     Example: [0,1,0,2,1,0,1,3,2,1,2,1]
 *     The volume of snow it can hold is 6.
 *     0, 1 -- hold 0
 *     1, 0, 2 -- hold 1
 *     2, 1, 0, 1, 3 -- hold 4
 *     3, 2, 1, 2 -- hold 1
 *     2, 1 -- hold 0
 * </pre>
 */
public class HoldingSnowVolume {
    public static int calculate_02_layer(int[] hill) {
        return layerCounting(hill, 0, hill.length - 1);
    }

    private static int layerCounting(int[] hill, int left, int right) {
        if (left >= right) {
            return 0;
        }
        boolean hasHill = false;
        for (int i = left; i <= right; i++) {
            if (hill[i] > 0) {
                left = i;
                hasHill = true;
                break;
            }
        }
        for (int i = right; i >= left; i--) {
            if (hill[i] > 0) {
                right = i;
                hasHill = true;
                break;
            }
        }
        if (!hasHill) {
            return 0;
        }
        if (left >= right) {
            return 0;
        }
        int minHeight = Math.min(hill[left], hill[right]);
        int volume = 0;
        for (int i = left; i <= right; i++) {
            hill[i] -= minHeight;
            if (hill[i] < 0) {
                volume -= hill[i];
                hill[i] = 0;
            }
        }
        return volume + layerCounting(hill, left, right);
    }

    public static int calculate(int[] hill) {
        int n = hill.length;
        if (n < 3) {
            return 0;
        }
        int leftHigh = hill[0];
        int fromIdx = 1;
        int rightHeightIdx = findRightHighIndex(hill, fromIdx, leftHigh);
        int volume = 0;
        while (rightHeightIdx != -1) {
            int maxHeight = Math.min(leftHigh, hill[rightHeightIdx]);
            for (int i = fromIdx; i < rightHeightIdx; i++) {
                volume += maxHeight - hill[i];
            }
            leftHigh = hill[rightHeightIdx];
            fromIdx = rightHeightIdx + 1;
            rightHeightIdx = findRightHighIndex(hill, fromIdx, leftHigh);
        }
        return volume;
    }

    private static int findRightHighIndex(int[] hill, int fromIdx, int leftHigh) {
        int idx = -1;
        int maxRightHeight = 0;
        for (int i = fromIdx; i < hill.length; i++) {
            if (hill[i] > leftHigh) {
                idx = i;
                break;
            } else if (hill[i] >= maxRightHeight) {
                maxRightHeight = hill[i];
                idx = i;
            }
        }
        return idx;
    }

    static void verifyAllCalculations(int expected, int[] hill) {
        if (expected != calculate(hill)) {
            throw new AssertionError("Expect " + expected + ", but calculate 01 is :" + calculate(hill));
        }
        if (expected != calculate_02_layer(hill)) {
            throw new AssertionError("Expect " + expected + ", but calculate 02 is :" + calculate_02_layer(hill));
        }
    }

    public static void main(String[] args) {
        int[] hill = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        verifyAllCalculations(6 , hill);
        hill = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1, 0};
        verifyAllCalculations( 6 , hill);
        hill = new int[]{0, 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1, 0, 0};
        verifyAllCalculations( 6 , hill);
        hill = new int[]{0, 1, 3, 0, 0, 2, 1, 0, 0, 1, 0, 4, 0, 0, 1, 1};
        verifyAllCalculations( 22 , hill);
        hill = new int[]{0, 0, 1, 3, 0, 0, 2, 1, 0, 0, 1, 0, 4, 0, 0, 1, 1, 0, 0};
        verifyAllCalculations( 22 , hill);
        hill = new int[]{1, 3, 0, 0, 2, 1, 0, 0, 1, 0, 4, 0, 0, 1};
        verifyAllCalculations( 22 , hill);
        hill = new int[]{0, 0, 0, 0};
        verifyAllCalculations( 0 , hill);
        hill = new int[]{0, 1, 0, 0};
        verifyAllCalculations( 0 , hill);
        hill = new int[]{0, 1, 1, 0};
        verifyAllCalculations( 0 , hill);
        hill = new int[]{1, 1, 1, 0};
        verifyAllCalculations( 0 , hill);
        hill = new int[]{1, 1, 1, 1};
        verifyAllCalculations( 0 , hill);
        System.out.println("HoldingSnowVolume passed");
    }
}
