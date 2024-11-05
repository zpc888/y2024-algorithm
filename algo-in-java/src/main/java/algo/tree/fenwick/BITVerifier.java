package algo.tree.fenwick;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.Arrays;

public class BITVerifier extends BaseSolution<Integer> implements BIT {
    private final BIT[] bits;

    public BITVerifier(int[] arr) {
        bits = new BIT[]{
            new BITBruteForce(arr),
            new BinaryIndexedTreeOrFenwickTree(arr)
        };
    }

    @Override
    protected int getNumberOfVersionsImplemented() {
        return bits.length;
    }

    @Override
    public int sum(int r) {
        return bits[versionToRun - 1].sum(r);
    }

    @Override
    public int sum(int l, int r) {
        return bits[versionToRun - 1].sum(l, r);
    }

    @Override
    public void add(int idx, int val) {
        bits[versionToRun - 1].add(idx, val);
    }

    @Override
    public void set(int idx, int val) {
        bits[versionToRun - 1].set(idx, val);
    }

    public static void main(String[] args) {
//        troubleshooting1();
        int[] arr = {1, 3, 5, 2, 6, 4};
        BITVerifier sol = new BITVerifier(arr);
        for (int i = 0; i < arr.length; i++) {
            final int j = i + 1;
            sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (" + j + "-" + j + ")",
                    () -> sol.sum(j, j), arr[i]);
        }
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (1-2)",
                () -> sol.sum(1, 2), 4);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (1-3)",
                () -> sol.sum(1, 3), 9);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (1-4)",
                () -> sol.sum(1, 4), 11);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (1-6)",
                () -> sol.sum(1, 6), 21);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (2-6)",
                () -> sol.sum(2, 6), 20);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (2-5)",
                () -> sol.sum(2, 5), 16);

        sol.runAllVersions("after multiple random additions " + Arrays.toString(arr) + ": (1-3)",
                () -> {
                    sol.add(1, 1);
                    sol.add(2, 2);
                    sol.add(4, 5);
                    return sol.sum(1, 3);
                }, 12);

        sol.runAllVersions("after multiple random add for " + Arrays.toString(arr) + ": (2-6)",
                () -> sol.sum(2, 6), 27);

        highVolumeAndPerfTest();
    }

    private static void highVolumeAndPerfTest() {
        final int cylces = 30_000;
        int[][] arrs = new int[cylces][];
        int[][] lr = new int[cylces][];
        final int addCount = 8;
        int[][][] addActionLRs = new int[cylces][addCount][];
        final int[] addActionFlags = DataHelper.genFixedSizeIntArr(addCount, 1, 3);
        final int ARRAY_MAX_SIZE = 1_000;
        final int ARRAY_MIN_VAL = -1_000;
        final int ARRAY_MAX_VAL = 1_000;
        final int[] addActionVals = DataHelper.genFixedSizeIntArr(addCount, ARRAY_MIN_VAL, ARRAY_MAX_VAL);
        for (int i = 0; i < cylces; i++) {
            arrs[i] = DataHelper.genRandomSizeIntArr(ARRAY_MAX_SIZE, ARRAY_MIN_VAL, ARRAY_MAX_VAL);
            while (arrs[i].length == 0) {
                arrs[i] = DataHelper.genRandomSizeIntArr(ARRAY_MAX_SIZE, ARRAY_MIN_VAL, ARRAY_MAX_VAL);
            }
            BITVerifier testSol = new BITVerifier(arrs[i]);
            lr[i] = DataHelper.genFixedSizeIntArr(2, 1, arrs[i].length);
            Arrays.sort(lr[i]);
            for (int j = 0; j < addCount; j++) {
                addActionLRs[i][j] = DataHelper.genFixedSizeIntArr(2, 1, arrs[i].length);
                Arrays.sort(addActionLRs[i][j]);
            }
//            System.out.println("\nDetail Debug: Test #" + (i + 1) + " " + Arrays.toString(arrs[i]) + " lr: " + Arrays.toString(lr[i]));
            final int run = i;
            for (int j = 0; j < addCount; j++) {
                final int addJ = j;
//                System.out.printf("add info: flag=%s, lr=%s, val=%s%n",
//                        addActionFlags[addJ], Arrays.toString(addActionLRs[run][addJ]), addActionVals[addJ]);
                testSol.runAllVersions("Random case # " + (run + 1) + " add #" + (j + 1),
                        () -> {
                            if (addActionFlags[addJ] != 1) {
                                for (int k = addActionLRs[run][addJ][0]; k <= addActionLRs[run][addJ][1]; k++) {
                                    testSol.add(k, addActionVals[addJ]);
                                }
                            }
                            return testSol.sum(lr[run][0], lr[run][1]);
                        }, null);
            }
        }

        int[] pfArr = DataHelper.genFixedSizeIntArr(ARRAY_MAX_SIZE * 100, ARRAY_MIN_VAL, ARRAY_MAX_VAL);
        int pfAddQueryCount = 5_000;
        int[][] pfAddLRs = new int[pfAddQueryCount][];
        int[] pfAddVals = DataHelper.genFixedSizeIntArr(pfAddQueryCount, ARRAY_MIN_VAL, ARRAY_MAX_VAL);
        int[][] pfQueryLRs = new int[pfAddQueryCount][];
        for (int i = 0; i < pfAddLRs.length; i++) {
            pfAddLRs[i] = DataHelper.genFixedSizeIntArr(2, 1, pfArr.length);
            Arrays.sort(pfAddLRs[i]);
            pfQueryLRs[i] = DataHelper.genFixedSizeIntArr(2, 1, pfArr.length);
            Arrays.sort(pfQueryLRs[i]);
        }
        BITVerifier perfSol = new BITVerifier(pfArr);
        perfSol.performMeasure(pfAddQueryCount + " add-actions and " + pfAddQueryCount + " queries",
                () -> {
                    int sum = 0;
                    for (int i = 0; i < pfAddQueryCount; i++) {
//                        for (int j = pfAddLRs[i][0]; j <= pfAddLRs[i][1]; j++) {          // no add
//                            perfSol.add(j, pfAddVals[i]);
//                        }
                        sum += perfSol.sum(pfQueryLRs[i][0], pfQueryLRs[i][1]);
                    }
                    return sum;
                });
//        ====================== < 5000 add-actions and 5000 queries > Performance Report ==============
//        Version-1: Duration: PT1.060181545S
//        Version-2: Duration: PT1.852373132S

        // no add since add in BIT costs O(LogN), but in brute force it costs O(1)
//====================== < 5000 add-actions and 5000 queries > Performance Report ==============
//        Version-1: Duration: PT0.088690165S
//        Version-2: Duration: PT0.000445803S
    }

    static class BITBruteForce implements BIT{
        private final int[] data;

        public BITBruteForce(int[] arr) {
            data = new int[arr.length + 1];
            for (int i = 0; i < arr.length; i++) {
                data[i + 1] = arr[i];
            }
        }

        @Override
        public int sum(int r) {
            if (r < 0) {
                return 0;
            }
            if (r >= data.length ) {
                r = data.length - 1;
            }
            int sum = 0;
            for (int i = 1; i <= r; i++) {
                sum += data[i];
            }
            return sum;
        }

        @Override
        public int sum(int l, int r) {
            l = Math.max(0, l);
            r = Math.min(data.length - 1, r);
            int sum = 0;
            for (int i = l; i <= r; i++) {
                sum += data[i];
            }
            return sum;
        }

        @Override
        public void add(int idx, int val) {
            data[idx] += val;
        }

        @Override
        public void set(int idx, int val) {
            data[idx] = val;
        }
    }
}
