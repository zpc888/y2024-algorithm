package algo.tree.segment;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.Arrays;

public class MaxSegmentTreeVerifier extends BaseSolution<Integer> implements IMaxSegmentTree {
    private final IMaxSegmentTree[] maxSegmentTrees;

    public MaxSegmentTreeVerifier(int[] arr) {
        maxSegmentTrees = new IMaxSegmentTree[]{
                new VerifierBruteForce(arr),
                new MaxSegmentTree(arr)
        };
    }

    @Override
    protected int getNumberOfVersionsImplemented() {
        return maxSegmentTrees.length;
    }

    @Override
    public void add(int l, int r, int val) {
        maxSegmentTrees[versionToRun - 1].add(l, r, val);
    }

    @Override
    public int queryMax(int l, int r) {
        return maxSegmentTrees[versionToRun - 1].queryMax(l, r);
    }

    private static void troubleshooting1() {
        int[] arr = {5};
        MaxSegmentTreeVerifier sol = new MaxSegmentTreeVerifier(arr);
        sol.versionToRun = 2;
        sol.add(1, 1, 2);
        int actual = sol.queryMax(1, 1);
        DataHelper.assertTrue(7 == actual, "Troubleshooting failed: expecting 7, but actual = " + actual);
    }

    private static void troubleshooting2() {
        int[] arr = {5, 4, 4};
        MaxSegmentTreeVerifier sol = new MaxSegmentTreeVerifier(arr);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (1-3)",
                () -> sol.queryMax(1, 3), 5);
        sol.versionToRun = 2;
        sol.add(2, 3, 7);
        int actual = sol.queryMax(1, 3);
        DataHelper.assertTrue(11 == actual, "Troubleshooting failed: expecting 11, but actual = " + actual);
    }

    private static void troubleshooting3() {
        int[] arr = {3, 3, 3, 9, 7};
        MaxSegmentTreeVerifier sol = new MaxSegmentTreeVerifier(arr);
        sol.versionToRun = 2;
        sol.add(4, 4, 0);
        sol.add(1, 5, 2);
        int actual = sol.queryMax(1, 5);
        DataHelper.assertTrue(11 == actual, "Troubleshooting failed: expecting 11, but actual = " + actual);
    }

    private static void troubleshooting4() {
        int[] arr = {4, 8, 8, 4};
        MaxSegmentTreeVerifier sol = new MaxSegmentTreeVerifier(arr);
        sol.versionToRun = 2;
        sol.add(2, 2, 9);
        sol.add(1, 4, 3);
        sol.add(1, 1, 2);
        int actual = sol.queryMax(1, 3);
        DataHelper.assertTrue(20 == actual, "Troubleshooting failed: expecting 20, but actual = " + actual);
    }

    private static void troubleshooting5() {
        int[] arr = {4, 0};
        MaxSegmentTreeVerifier sol = new MaxSegmentTreeVerifier(arr);
        sol.versionToRun = 2;
        sol.add(1, 2, 0);
        sol.add(1, 2, 9);
        sol.add(2, 2, 1);
        int actual = sol.queryMax(1, 2);
        DataHelper.assertTrue(13 == actual, "Troubleshooting failed: expecting 13, but actual = " + actual);
    }

    public static void main(String[] args) {
        troubleshooting5();
        troubleshooting4();
        troubleshooting3();
        troubleshooting2();
        troubleshooting1();
        int[] arr = {1, 3, 5, 2, 6, 4};
        MaxSegmentTreeVerifier sol = new MaxSegmentTreeVerifier(arr);
        for (int i = 0; i < arr.length; i++) {
            final int j = i + 1;
            sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (" + j + "-" + j + ")",
                    () -> sol.queryMax(j, j), arr[i]);
        }
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (1-2)",
                () -> sol.queryMax(1, 2), 3);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (1-3)",
                () -> sol.queryMax(1, 3), 5);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (1-4)",
                () -> sol.queryMax(1, 4), 5);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (1-6)",
                () -> sol.queryMax(1, 6), 6);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (2-6)",
                () -> sol.queryMax(2, 6), 6);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (2-5)",
                () -> sol.queryMax(2, 5), 6);

        sol.runAllVersions("after multiple random additions " + Arrays.toString(arr) + ": (1-3)",
                () -> {
                    sol.add(1, 3, 1);
                    sol.add(2, 5, 2);
                    sol.add(4, 6, 5);
                    return sol.queryMax(1, 3);
                }, null);

        sol.runAllVersions("after multiple random add for " + Arrays.toString(arr) + ": (2-6)",
                () -> sol.queryMax(2, 6), null);

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
            MaxSegmentTreeVerifier testSol = new MaxSegmentTreeVerifier(arrs[i]);
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
                                testSol.add(addActionLRs[run][addJ][0], addActionLRs[run][addJ][1], addActionVals[addJ]);
                            }
                            return testSol.queryMax(lr[run][0], lr[run][1]);
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
        MaxSegmentTreeVerifier perfSol = new MaxSegmentTreeVerifier(pfArr);
        perfSol.performMeasure(pfAddQueryCount + " add-actions and " + pfAddQueryCount + " queries",
                () -> {
                    int sum = 0;
                    for (int i = 0; i < pfAddQueryCount; i++) {
                        perfSol.add(pfAddLRs[i][0], pfAddLRs[i][1], pfAddVals[i]);
                        sum += perfSol.queryMax(pfQueryLRs[i][0], pfQueryLRs[i][1]);
                    }
                    return sum;
                });
//        ====================== < 5000 add-actions and 5000 queries > Performance Report ==============
//        Version-1: Duration: PT0.002699519S
//        Version-2: Duration: PT0.003743989S  // SegmentTree does outperform brute-force for 1_000 elements

//====================== < 5000 add-actions and 5000 queries > Performance Report ==============
//        Version-1: Duration: PT0.219777148S
//        Version-2: Duration: PT0.007491134S  // SegmentTree outperforms 30 times brute-force for 100_000 elements
    }

    public static class VerifierBruteForce implements IMaxSegmentTree {
        private final int[] origin;
        private final int[] refined;

        public VerifierBruteForce(int[] arr) {
            this.origin = arr;
            refined = ISegmentTree.refineTo1Base(arr);
        }

        @Override
        public void add(int l, int r, int val) {
            for (int i = l; i <= r; i++) {
                refined[i] += val;
            }
        }

        @Override
        public int queryMax(int l, int r) {
            int max = Integer.MIN_VALUE;
            for (int i = l; i <= r; i++) {
                max = Math.max(max, refined[i]);
            }
            return max;
        }
    }
}
