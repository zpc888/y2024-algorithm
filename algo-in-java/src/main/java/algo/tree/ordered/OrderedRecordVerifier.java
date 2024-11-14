package algo.tree.ordered;

import algo.tree.ordered.avl.AVLTree;
import algo.tree.ordered.sb.SizeBalancedTree;
import algo.tree.ordered.skiplist.SkipList;
import assist.DataHelper;

import java.util.function.BiFunction;

public class OrderedRecordVerifier {
    private static IOrderedRecord<Integer, Integer>[] solutions;

    private static void troubleshooting01() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        int[] data = new int[]{7, 13, 6, 2, 3, 14, 10, 12, 1, 5};
        for (int i = 0; i < data.length; i++) {
            avlTree.add(data[i], data[i]);
            for (int j = 0; j < i; j++) {
                DataHelper.assertTrue(avlTree.contains(data[j]), "Data not found at i = " + i + ", j = " + j);
            }
            DataHelper.assertTrue(avlTree.contains(data[i]) && avlTree.size() == i + 1,
                    "Data not found at i = " + i);
        }
        for (int i = 0; i < data.length; i++) {
            avlTree.delete(data[i]);
            for (int j = i + 1; j < data.length; j++) {
                DataHelper.assertTrue(avlTree.contains(data[j]), "Data not found at i = " + i + ", j = " + j);
            }
            DataHelper.assertTrue(!avlTree.contains(data[i]) && avlTree.size() == data.length - i - 1,
                    "Data found at i = " + i);
        }
    }

    public static void main(String[] args) {
        troubleshooting01();

        int cycles = 1000;
        int[][] input = new int[cycles][];
        int[][] queryKeys = new int[cycles][];
        for (int i = 0; i < cycles; i++) {
//            input[i] = DataHelper.genRandomSizeIntArrUniq(10, 1, 20);
//            queryKeys[i] = DataHelper.genRandomSizeIntArr(5, -10000, 10000);
            input[i] = DataHelper.genRandomSizeIntArrUniq(100, -10000, 10000);
            queryKeys[i] = DataHelper.genRandomSizeIntArr(50, -10000, 10000);
            solutions = new IOrderedRecord[]{
                    new AVLTree<>(), new SizeBalancedTree<>(), new SkipList<>() };
            for (int j = 0; j < solutions.length; j++) {
                for (int k = 0; k < input[i].length; k++) {
                    solutions[j].add(input[i][k], input[i][k]);
                    for (int l = 0; l <= k; l++) {
                        DataHelper.assertTrue(solutions[j].contains(input[i][l])
                                        && solutions[j].size() == k + 1,
                                "Data not found at i = " + i + ", j = " + j + ", k = " + k + ", l = " + l);
                    }
                }
                DataHelper.assertTrue(solutions[j].size() == input[i].length, "Size mismatch!");
            }
            for (int j = 0; j < queryKeys[i].length; j++) {
                verify(queryKeys[i][j], IOrderedRecord::getValue);
                verify(queryKeys[i][j], IOrderedRecord::getFloorKey);
                verify(queryKeys[i][j], IOrderedRecord::getCeilingKey);
                verify(queryKeys[i][j], IOrderedRecord::getAboveFloorKey);
                verify(queryKeys[i][j], IOrderedRecord::getBelowCeilingKey);
            }
            for (int j = 0; j < solutions.length; j++) {
                for (int k = 0; k < input[i].length; k++) {
                    DataHelper.assertTrue(solutions[j].delete(input[i][k]),
                            "Delete failed at j = " + j + ", k = " + k);
                    for (int l = k + 1; l < input[i].length; l++) {
                        DataHelper.assertTrue(solutions[j].contains(input[i][l])
                                && !solutions[j].contains(input[i][k])
                                && solutions[j].size() == input[i].length - k - 1,
                                "Data found/not found at i = " + i + ", j = " + j + ", k = " + k + ", l = " + l);
                    }
                }
                DataHelper.assertTrue(solutions[j].isEmpty(), "Size should be empty after deletion, but got "
                        + solutions[j].size() + "! at j = " + j);
            }
        }

        timingTest(new AVLTree<Integer, Integer>(), input, queryKeys);
        timingTest(new SizeBalancedTree<Integer, Integer>(), input, queryKeys);
        timingTest(new SkipList<Integer, Integer>(), input, queryKeys);

        // random size 100 * 1000
//        AVLTree time: 24 ms
//        SizeBalancedTree time: 28 ms
//        SkipList time: 26 ms
//
//        AVLTree time: 24 ms
//        SizeBalancedTree time: 25 ms
//        SkipList time: 26 ms

    }

    private static void timingTest(IOrderedRecord<Integer, Integer> orderedList, int[][] input, int[][] queryKeys) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                orderedList.add(input[i][j], input[i][j]);
            }
            for (int j = 0; j < queryKeys[i].length; j++) {
                int key = queryKeys[i][j];
                orderedList.getValue(key);
                orderedList.getFloorKey(key);
                orderedList.getAboveFloorKey(key);
                orderedList.getCeilingKey(key);
                orderedList.getBelowCeilingKey(key);
            }
            for (int j = 0; j < input[i].length; j++) {
                orderedList.delete(input[i][j]);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(orderedList.getClass().getSimpleName() + " time: " + (end - start) + " ms");
    }

    private static void verify(int queryKey, BiFunction<IOrderedRecord<Integer, Integer>, Integer, Integer> biFunc) {
        Integer[] ret = new Integer[solutions.length];
        for (int i = 0; i < solutions.length; i++) {
//            ret[i] = solutions[i].getValue(queryKey);
            ret[i] = biFunc.apply(solutions[i], queryKey);
        }
        for (int i = 1; i < solutions.length; i++) {
            if (!intEqs(ret[i], ret[i - 1])) {
                throw new RuntimeException("Mismatch found on get value!");
            }
        }
    }

    private static boolean intEqs(Integer i1, Integer i2) {
        if (i1 == null) {
            return i2 == null;
        }
        return i1.equals(i2);
    }

}
