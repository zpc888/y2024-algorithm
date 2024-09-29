package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/minimum-index-sum-of-two-lists/
 *
 * Suppose Andy and Doris want to choose a restaurant for dinner, and they both have a list of favorite restaurants
 * represented by strings.
 * You need to help them find out their common interest with the least list index sum. If there is a choice tie between
 * answers, output all of them with no order requirement. You could assume there always exists an answer.
 * <p />
 * Given two arrays of strings list1 and list2, find the common strings with the least index sum.
 * A common string is a string that appeared in both list1 and list2.
 * A common string with the least index sum is a common string such that if it appeared at list1[i] and list2[j]
 * then i + j should be the minimum value among all the other common strings.
 * Return all the common strings with the least index sum. Return the answer in any order.
 */
public class Q0599_MinimumIndexSumOfTwoLists {
    public String[] findRestaurant(String[] list1, String[] list2) {
        return findRestaurant_V1(list1, list2);
    }

    @MetricsRuntime(ms = 5, beats = 95.89)
    @MetricsMemory(mb = 45.57, beats = 18.38)
    public String[] findRestaurant_V1(String[] list1, String[] list2) {
		if (list1 == null || list1.length == 0 || list2 == null || list2.length == 0) {
			return new String[0];
		}
		String[] shortList = list1.length > list2.length ? list2 : list1;
		String[] longList = shortList == list1 ? list2 : list1;
		Map<String, Integer> map = new HashMap<>(shortList.length);
		for (int i = 0; i < shortList.length; i++) {
			map.put(shortList[i], i);
		}
		List<String> retList = new ArrayList<>(10);
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < longList.length; i++) {
			String word = longList[i];
			Integer shortIdx = map.get(word);
			if (shortIdx == null) {
				continue;
			}
			int idxSum = i + shortIdx;
			if (idxSum < min) {
				retList.clear();
				retList.add(word);
                min = idxSum;
			} else if (idxSum == min) {
				retList.add(word);
			}
		}
        if (retList.isEmpty()) {
            return new String[0];
		} else {
			return retList.toArray(new String[retList.size()]);
		}
    }

    public static void main(String[] args) {
        testExample1();
        testExample2();
        testExample3();
    }

    private static void testExample1() {
        String[] list1 = new String[]{"Shogun", "Tapioca Express", "Burger King", "KFC"};
        String[] list2 = new String[]{"Piatti", "The Grill at Torrey Pines", "Hungry Hunter Steakhouse", "Shogun"};
        String[] expected = new String[]{"Shogun"};
        String[] result = new Q0599_MinimumIndexSumOfTwoLists().findRestaurant(list1, list2);
        System.out.println("Example1: " + (expected.length == result.length && expected[0].equals(result[0]) ? "PASS" : "FAIL"));
    }

    private static void testExample2() {
        String[] list1 = new String[]{"Shogun","Tapioca Express","Burger King","KFC"};
        String[] list2 = new String[]{"KFC","Shogun","Burger King"};
        String[] expected = new String[]{"Shogun"};
        String[] result = new Q0599_MinimumIndexSumOfTwoLists().findRestaurant(list1, list2);
        System.out.println("Example2: " + (expected.length == result.length && expected[0].equals(result[0])
                ? "PASS" : "FAIL"));
    }

    private static void testExample3() {
        String[] list1 = new String[]{"happy", "sad", "good"};
        String[] list2 = new String[]{"sad", "happy", "good"};
        String[] expected = new String[]{"sad", "happy"};
        String[] result = new Q0599_MinimumIndexSumOfTwoLists().findRestaurant(list1, list2);
        System.out.println("Example3: " + (expected.length == result.length
                && ((expected[0].equals(result[0]) && expected[1].equals(result[1]))
                || ((expected[1].equals(result[0]) && expected[0].equals(result[1]))))
                ? "PASS" : "FAIL"));
    }
}
