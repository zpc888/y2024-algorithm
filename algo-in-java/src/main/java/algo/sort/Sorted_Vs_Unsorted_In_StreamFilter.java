package algo.sort;

import assist.DataHelper;

import java.util.ArrayList;
import java.util.List;

public class Sorted_Vs_Unsorted_In_StreamFilter {
    private static final List<Integer> unsorted = new ArrayList<>(20);
    private static final List<Integer> sorted = new ArrayList<>(20);

    public static void main(String[] args) {
        int[] ints = DataHelper.generateRandomData(8, 0, 20);
        for (int i : ints) {
            unsorted.add(i);
            sorted.add(i);
        }
        sorted.sort(Integer::compareTo);
        System.out.println("Unsorted: " + unsorted);
        System.out.println("Sorted: " + sorted);
        System.out.println("============= filter > 10 =============");
        System.out.println("Unsorted: ");
        List<Integer> filter1 = unsorted.stream().filter(i -> {
            boolean ret = i > 10;
            System.out.println("\t" + i + "\t> 10 : " + ret);
            return ret;
        }).toList();
        System.out.println("\nSorted: ");
        List<Integer> filter2 = sorted.stream().filter(i -> {
            boolean ret = i > 10;
            System.out.println("\t" + i + "\t> 10 : " + ret);
            return ret;
        }).toList();
        System.out.println();
        System.out.println("Unsorted: " + filter1);
        System.out.println("Sorted: " + filter2);
    }
}
