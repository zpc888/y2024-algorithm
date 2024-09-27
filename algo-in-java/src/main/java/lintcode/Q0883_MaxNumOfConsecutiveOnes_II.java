package lintcode;

import run.MetricsMemory;
import run.MetricsRuntime;

// https://www.lintcode.com/problem/883

/**
 * 给出一个二进制数组，在最多翻转一位0的情况下，找到这个数组里最长的连续的1的个数。
 */
public class Q0883_MaxNumOfConsecutiveOnes_II {
    /**
     * @param nums: a list of integer
     * @return: return a integer, denote  the maximum number of consecutive 1s
     */
    public int findMaxConsecutiveOnes(int[] nums) {
        return findMaxConsecutiveOnes_01(nums);
    }

    @MetricsRuntime(ms = 389, beats = 64.80)
    @MetricsMemory(mb = 22.80)
    private int findMaxConsecutiveOnes_01(int[] nums) {
        int max = 0;
        int prePre0Idx = -1;
        int pre0Idx = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 1) {
                if (pre0Idx != -1) {
                    max = Math.max(max, i - prePre0Idx - 1);
                }
                prePre0Idx = pre0Idx;
                pre0Idx = i;
            }
        }
        return Math.max(max, nums.length - 1 - prePre0Idx);
    }

    // ---------------------- TEST ----------------------
    public static void main(String[] args) {
        Q0883_MaxNumOfConsecutiveOnes_II q = new Q0883_MaxNumOfConsecutiveOnes_II();
        System.out.println(q.findMaxConsecutiveOnes(new int[]{1, 1, 0, 1, 1, 1})); // 6
        System.out.println(q.findMaxConsecutiveOnes(new int[]{1, 0, 1, 1, 0, 1})); // 4
        System.out.println(q.findMaxConsecutiveOnes(new int[]{0, 0, 0, 0, 0, 0})); // 1
        System.out.println(q.findMaxConsecutiveOnes(new int[]{0, 0, 0, 0, 0, 1})); // 2
        System.out.println(q.findMaxConsecutiveOnes(new int[]{1, 0, 0, 0, 0, 0})); // 2
        System.out.println(q.findMaxConsecutiveOnes(new int[]{0, 1, 0, 0, 0, 0})); // 2
        System.out.println(q.findMaxConsecutiveOnes(new int[]{0, 0, 0, 1, 0, 1})); // 3
        System.out.println(q.findMaxConsecutiveOnes(new int[]{0, 0, 0, 0, 1, 1})); // 3
        System.out.println(q.findMaxConsecutiveOnes(new int[]{1, 1, 0, 0, 0, 0})); // 3
        System.out.println(q.findMaxConsecutiveOnes(new int[]{1, 0, 1, 0, 0, 0})); // 3
    }
}
