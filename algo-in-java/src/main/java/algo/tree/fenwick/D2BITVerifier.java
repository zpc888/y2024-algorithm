package algo.tree.fenwick;

import assist.BaseSolution;
import assist.DataHelper;

public class D2BITVerifier extends BaseSolution<Integer> implements D2BIT {
    private final D2BIT[] bit2Ds;

    public D2BITVerifier(int[][] matrix) {
        bit2Ds = new D2BIT[]{
            new D2BITBruteForce(matrix),
            new D2IndexedTree(matrix)
        };
    }

    @Override
    protected int getNumberOfVersionsImplemented() {
        return bit2Ds.length;
    }

    @Override
    public void add(int x, int y, int val) {
        bit2Ds[versionToRun - 1].add(x, y, val);
    }

    @Override
    public int sum(int x, int y) {
        return bit2Ds[versionToRun - 1].sum(x, y);
    }

    @Override
    public int sum(int x1, int y1, int x2, int y2) {
        return bit2Ds[versionToRun - 1].sum(x1, y1, x2, y2);
    }

    public static void main(String[] args) {
        int[][] matrix = {
            {3, 0, 1, 4, 2},
            {5, 6, 3, 2, 1},
            {1, 2, 0, 1, 5},
            {4, 1, 0, 1, 7},
            {1, 0, 3, 0, 5}
        };
        D2BITVerifier sol = new D2BITVerifier(matrix);
        sol.runAllVersions("No add (1, 1)", () -> sol.sum(1, 1), 3);
        sol.runAllVersions("No add (2, 2)", () -> sol.sum(2, 2), 14);
        sol.runAllVersions("No add (3, 3)", () -> sol.sum(3, 3), 21);
        sol.runAllVersions("No add (4, 4)", () -> sol.sum(4, 4), 34);
        sol.runAllVersions("No add (5, 5)", () -> sol.sum(5, 5), 58);
        sol.runAllVersions("No add (1, 1, 2, 2)", () -> sol.sum(1, 1, 2, 2), 14);
        sol.runAllVersions("No add (2, 2, 3, 3)", () -> sol.sum(2, 2, 3, 3), 11);
        sol.runAllVersions("No add (3, 3, 4, 4)", () -> sol.sum(3, 3, 4, 4), 2);
        sol.runAllVersions("No add (4, 4, 5, 5)", () -> sol.sum(4, 4, 5, 5), 13);
        sol.runAllVersions("No add (2, 2, 4, 4)", () -> sol.sum(2, 2, 4, 4), 16);

        sol.runAllVersions("add (1,1,2) then (2, 2, 4, 4)", () -> {
            sol.add(1, 1, 2);
            return sol.sum(2, 2, 4, 4);
        }, 16);

        sol.runAllVersions("add (2,3,3) then (2, 2, 4, 4)", () -> {
            sol.add(2, 3, 3);
            return sol.sum(2, 2, 4, 4);
        }, 19);

        sol.runAllVersions("add (4,4,4) then (2, 2, 4, 4)", () -> {
            sol.add(4, 4, 4);
            return sol.sum(2, 2, 4, 4);
        }, 23);

        highVolumeTest();
    }

    private static void highVolumeTest() {
        final int MAX_ROW_COL = 10;
        final int cycles = 5_000;
        final int MIN_VAL = 0;
        final int MAX_VAL = 10;
        int[][][] matrixes = new int[cycles][][];
        int[][] sumXY = new int[cycles][2];

        for (int t = 0; t < cycles; t++) {
            int[] rc = DataHelper.genFixedSizeIntArr(2, 1, MAX_ROW_COL);
            matrixes[t] = new int[rc[0]][];
            for (int i = 0; i < rc[0]; i++) {
                matrixes[t][i] = DataHelper.genFixedSizeIntArr(rc[1], MIN_VAL, MAX_VAL);
            }
            sumXY[t][0] = DataHelper.genFixedSizeIntArr(1, 1, rc[0])[0];
            sumXY[t][1] = DataHelper.genFixedSizeIntArr(1, 1, rc[1])[0];
        }
        for (int t = 0; t < cycles; t++) {
            final int ft = t;
            D2BITVerifier sol = new D2BITVerifier(matrixes[t]);
            sol.runAllVersions("Random: " + (t + 1), () -> sol.sum(sumXY[ft][0], sumXY[ft][1]), null);
        }
    }


    private static class D2BITBruteForce implements D2BIT {
        private final int[][] origin;   // 0-based
        private final int rows;
        private final int cols;
        private final int[][] refined;     // 1-based

        public D2BITBruteForce(int[][] matrix) {
            this.origin = matrix;
            this.rows = matrix.length;
            this.cols = matrix[0].length;
            this.refined = new int[rows + 1][cols + 1];
            for (int i = 1; i <= rows; i++) {
                for (int j = 1; j <= cols; j++) {
                    refined[i][j] = matrix[i - 1][j - 1];
                }
            }
        }

        @Override
        public void add(int x, int y, int val) {
            refined[x][y] += val;
        }

        @Override
        public int sum(int x, int y) {
            int sum = 0;
            for (int i = 1; i <= x; i++) {
                for (int j = 1; j <= y; j++) {
                    sum += refined[i][j];
                }
            }
            return sum;
        }

        @Override
        public int sum(int x1, int y1, int x2, int y2) {
            int sum = 0;
            for (int i = x1; i <= x2; i++) {
                for (int j = y1; j <= y2; j++) {
                    sum += refined[i][j];
                }
            }
            return sum;
        }
    }
}
