package lintcode;

import run.MetricsMemory;
import run.MetricsRuntime;

// https://www.lintcode.com/problem/1212
/**
 * 给定一个二进制数组，找出该数组中最大连续1的个数。
 */
public class Q1212_MaxNumOfConsecutiveOnes {
    /**
     * @param nums: a binary array
     * @return:  the maximum number of consecutive 1s
     */
    public int findMaxConsecutiveOnes(int[] nums) {
//        return findMaxConsecutiveOnes_01(nums);
        return findMaxConsecutiveOnes_02(nums);
    }

    @MetricsRuntime(ms = 339, beats = 74.80)
    @MetricsMemory(mb = 21.45)
    private int findMaxConsecutiveOnes_02(int[] nums) {
        int max = 0;
        int pre0Idx = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 1) {
                max = Math.max(max, i - pre0Idx - 1);
                pre0Idx = i;
            }
        }
        return Math.max(max, nums.length - pre0Idx - 1);
    }


    @MetricsRuntime(ms = 358, beats = 56.20)
    @MetricsMemory(mb = 21.07)
    private int findMaxConsecutiveOnes_01(int[] nums) {
        int max = 0;
        int currCnt = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                currCnt++;
            } else {
                max = Math.max(max, currCnt);
                currCnt = 0;
            }
        }
        return Math.max(max, currCnt);
    }

    // ---------------------- TEST ----------------------
    public static void main(String[] args) {
        Q1212_MaxNumOfConsecutiveOnes q = new Q1212_MaxNumOfConsecutiveOnes();
        System.out.println(q.findMaxConsecutiveOnes(new int[]{1, 1, 0, 1, 1, 1})); // 3
        System.out.println(q.findMaxConsecutiveOnes(new int[]{1, 0, 1, 1, 0, 1})); // 2
        System.out.println(q.findMaxConsecutiveOnes(new int[]{0, 0, 0, 0, 0, 0})); // 0
        System.out.println(q.findMaxConsecutiveOnes(new int[]{0, 0, 0, 0, 0, 1})); // 1
        System.out.println(q.findMaxConsecutiveOnes(new int[]{1, 0, 0, 0, 0, 0})); // 1
        System.out.println(q.findMaxConsecutiveOnes(new int[]{0, 1, 0, 0, 0, 0})); // 1
    }
}
