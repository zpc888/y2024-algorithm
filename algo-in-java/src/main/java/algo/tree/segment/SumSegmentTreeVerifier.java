package algo.tree.segment;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.Arrays;

import static algo.tree.segment.SumSegmentTree.refineTo1Base;

public class SumSegmentTreeVerifier extends BaseSolution<Integer> implements ISumSegmentTree {
    private final ISumSegmentTree[] sumSegmentTrees;

    public SumSegmentTreeVerifier(int[] arr) {
        sumSegmentTrees = new ISumSegmentTree[]{
                new VerifierBruteForce(arr),
                new VerifierPreSum(arr),
                new SumSegmentTree(arr)
        };
    }

    @Override
    protected int getNumberOfVersionsImplemented() {
        return sumSegmentTrees.length;
    }

    @Override
    public void add(int l, int r, int val) {
        sumSegmentTrees[versionToRun - 1].add(l, r, val);
    }

    @Override
    public int querySum(int l, int r) {
        return sumSegmentTrees[versionToRun - 1].querySum(l, r);
    }

    public static class VerifierBruteForce implements ISumSegmentTree {
        private final int[] origin;
        private final int[] refined;

        public VerifierBruteForce(int[] arr) {
            this.origin = arr;
            refined = refineTo1Base(arr);
        }

        @Override
        public void add(int l, int r, int val) {
            for (int i = l; i <= r; i++) {
                refined[i] += val;
            }
        }

        @Override
        public int querySum(int l, int r) {
            int sum = 0;
            for (int i = l; i <= r; i++) {
                sum += refined[i];
            }
            return sum;
        }
    }

    public static class VerifierPreSum implements ISumSegmentTree {
        private final int[] origin;
        private final int[] preSum;

        public VerifierPreSum(int[] arr) {
            this.origin = arr;
            preSum = new int[arr.length + 1];
            for (int i = 1; i <= arr.length; i++) {
                preSum[i] = preSum[i - 1] + arr[i - 1];
            }
        }

        @Override
        public void add(int l, int r, int val) {
            int times = 1;
            for (int i = l; i <= r; i++) {
                preSum[i] += val * times;
                times += 1;
            }
            for (int j = r + 1; j < preSum.length; j++) {
                preSum[j] += val * (times - 1);
            }
        }

        @Override
        public int querySum(int l, int r) {
            return preSum[r] - preSum[l - 1];
        }
    }

    public static void main(String[] args) {
        troubleshooting();
        int[] arr = {1, 3, 5, 2, 6, 4};
        SumSegmentTreeVerifier sol = new SumSegmentTreeVerifier(arr);
        for (int i = 0; i < arr.length; i++) {
            final int j = i + 1;
            sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (" + j + "-" + j + ")",
                    () -> sol.querySum(j, j), arr[i]);
        }
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (1-2)",
                () -> sol.querySum(1, 2), 1 + 3);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (1-3)",
                () -> sol.querySum(1, 3), 9);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (1-4)",
                () -> sol.querySum(1, 4), 11);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (1-6)",
                () -> sol.querySum(1, 6), 21);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (2-6)",
                () -> sol.querySum(2, 6), 20);
        sol.runAllVersions("No add for " + Arrays.toString(arr) + ": (2-6)",
                () -> sol.querySum(2, 5), 16);

        sol.runAllVersions("after multiple random additions " + Arrays.toString(arr) + ": (1-3)",
                () -> {
                    sol.add(1, 3, 1);
                    sol.add(2, 5, 2);
                    sol.add(4, 6, 5);
                    return sol.querySum(1, 3);
                }, null);

        sol.runAllVersions("after multiple random add for " + Arrays.toString(arr) + ": (2-6)",
                () -> sol.querySum(2, 6), null);

        highVolumeAndPerfTest();
    }

    private static void troubleshooting() {
        int[] arr = {3, 2, 7, 2, 1, 1, 8, 4, 4};
        SumSegmentTreeVerifier sol = new SumSegmentTreeVerifier(arr);
        sol.versionToRun = 3;       // SumSegmentTree
        int actual1 = sol.querySum(3, 8);
        DataHelper.assertTrue(23 == actual1, "Troubleshooting failed expecting 23 but got " + actual1);
        sol.add(4, 5, 10);
        int actual2 = sol.querySum(3, 8);
        DataHelper.assertTrue(43 == actual2, "Troubleshooting failed expecting 43 but got " + actual2);
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
            SumSegmentTreeVerifier testSol = new SumSegmentTreeVerifier(arrs[i]);
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
                testSol.runAllVersions("Random add # " + (run + 1) + " add #" + (j + 1),
                () -> {
                    if (addActionFlags[addJ] != 1) {
                        testSol.add(addActionLRs[run][addJ][0], addActionLRs[run][addJ][1], addActionVals[addJ]);
                    }
                    return testSol.querySum(lr[run][0], lr[run][1]);
                }, null);
            }
        }

        int[] pfArr = DataHelper.genFixedSizeIntArrUniq(ARRAY_MAX_SIZE, ARRAY_MIN_VAL, ARRAY_MAX_VAL);
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
        SumSegmentTreeVerifier perfSol = new SumSegmentTreeVerifier(pfArr);
        perfSol.performMeasure(pfAddQueryCount + " add-actions and " + pfAddQueryCount + " queries",
                () -> {
                    int sum = 0;
                    for (int i = 0; i < pfAddQueryCount; i++) {
                        perfSol.add(pfAddLRs[i][0], pfAddLRs[i][1], pfAddVals[i]);
                        sum += perfSol.querySum(pfQueryLRs[i][0], pfQueryLRs[i][1]);
                    }
                    return sum;
                });
//====================== < 5000 add-actions and 5000 queries > Performance Report ==============
//        Version-1: Duration: PT0.002392482S
//        Version-2: Duration: PT0.001621698S       // PreSum wins a lot for query more, it may loss for add-more cases
//        Version-3: Duration: PT0.002825694S
    }
}
