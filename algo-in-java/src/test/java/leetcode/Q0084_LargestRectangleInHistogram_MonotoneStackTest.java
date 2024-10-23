package leetcode;

import assist.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
class Q0084_LargestRectangleInHistogram_MonotoneStackTest {
    private Q0084_LargestRectangleInHistogram_MonotoneStack sol;

    @BeforeEach
    void setUp() {
        sol = new Q0084_LargestRectangleInHistogram_MonotoneStack();
    }

    @Test
    void test1() {
        int[] heights = {2,1,5,6,2,3};
        int expected = 10;
        int result = sol.runAllVersions(Arrays.toString(heights), () -> sol.largestRectangleArea(heights), expected);
        assertEquals(expected, result);
    }

    @Test
    void test2() {
        int[] heights = {2,4};
        int expected = 4;
        int result = sol.runAllVersions(Arrays.toString(heights), () -> sol.largestRectangleArea(heights), expected);
        assertEquals(expected, result);
    }

    @Test
    void highVolumeAndPerfTest() {
        final int cycles = 10_000;
        int[][] inputs = new int[cycles][];
        int avgSize = 0;
        for (int i = 0; i < cycles; i++) {
            int[] tmp = DataHelper.genRandomSizeIntArr(10_000, 0, 1_000);
            sol.runAllVersions("Random " + (i+1), () -> sol.largestRectangleArea(tmp), null);
            inputs[i] = tmp;
            avgSize += tmp.length;
        }
        avgSize /= cycles;
        sol.performMeasure("Avg Size: " + avgSize + " running " + cycles + " times", () -> {
            for (int i = 0; i < cycles; i++) {
                sol.largestRectangleArea(inputs[i]);
            }
            return null;
        });

//        ================================ performance Report ==========================
//        Version-1: Duration: PT0.633882693S     O(N^2) with no stack   -- this will time out on leetcode test #94/99
//        Version-2: Duration: PT0.826255494S     O(N) with system stack -- this will beat leetcode 78.85% submission
//        Version-3: Duration: PT0.441051099S     O(N) with own stack    -- this will beat leetcode 99.83% submission
    }

}