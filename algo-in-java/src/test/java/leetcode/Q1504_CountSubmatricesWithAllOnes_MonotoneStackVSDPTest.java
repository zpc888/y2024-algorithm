package leetcode;

import assist.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class Q1504_CountSubmatricesWithAllOnes_MonotoneStackVSDPTest {
    Q1504_CountSubmatricesWithAllOnes_MonotoneStack_VS_DP sol;

    @BeforeEach
    void setUp() {
        sol = new Q1504_CountSubmatricesWithAllOnes_MonotoneStack_VS_DP();
    }

    @Test
    void test1() {
        int[][] mat = {
            {1, 0, 1},
            {1, 1, 0},
            {1, 1, 0}
        };
        sol.runAllVersions(printArray(mat), () -> sol.numSubmat(mat), 13);
    }

    @Test
    void test2() {
        int[][] mat = {
                {1, 1, 1},
                {1, 0, 0},
                {1, 1, 1}
        };
        sol.runAllVersions(printArray(mat), () -> sol.numSubmat(mat), 16);
    }

    @Test
    void test3() {
        int[][] mat = {
                {0, 1, 1, 0},
                {0, 1, 1, 1},
                {1, 1, 1, 0}
        };
        sol.runAllVersions(printArray(mat), () -> sol.numSubmat(mat), 24);
    }

    @Test
    void highVolumeAndPerfTest() {
        int cycles = 10_000;
        int[][][] data = new int[cycles][][];
        for (int i = 0; i < cycles; i++) {
            // rows * cols matrix mat of ones and zeros
            int[] dim = DataHelper.generateFixedSizeData(2, 1, 150);
            int rows = dim[0];
            int cols = dim[1];
            int[][] tmp = new int[rows][];
            for (int j = 0; j < rows; j++) {
                tmp[j] = DataHelper.generateFixedSizeData(cols, 0, 1);
            }
            data[i] = tmp;
            sol.runAllVersions("Random run: " + (i+1), () -> sol.numSubmat(tmp), null);
        }
        // performance test
        sol.performMeasure("Performance test", () -> {
            for (int i = 0; i < cycles; i++) {
                sol.numSubmat(data[i]);
            }
            return null;
        });

//        ================================ performance Report ==========================
//        Version-1: Duration: PT0.803695367S           // DP version
//        Version-2: Duration: PT0.528376595S           // Monotone stack is faster than DP
    }

    private String printArray(int[][] data) {
        if (data == null) {
            return "null";
        }
        if (data.length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < data.length; i++) {
            sb.append("  ").append(Arrays.toString(data[i])).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

}