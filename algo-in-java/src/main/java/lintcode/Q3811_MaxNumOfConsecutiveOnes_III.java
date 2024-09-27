package lintcode;

import run.MetricsMemory;
import run.MetricsRuntime;

import java.util.LinkedList;

// https://www.lintcode.com/problem/3811

/**
 * 给定一个二进制数组 nums（数组中仅包含元素 0 或 1）和一个整数 k，如果可以翻转最多 k 个 0，则返回数组中连续的 1 的最大个数。
 */
public class Q3811_MaxNumOfConsecutiveOnes_III {
    /**
     * @param nums: An integer array
     * @param k: At most k 0 can be flipped
     * @return: maximum number of consecutive 1
     */
    public int longestOnes(int[] nums, int k) {
//        return longestOnes_01(nums, k);
        return longestOnes_02(nums, k);
    }

    @MetricsRuntime(ms = 583, beats = 41.94)
    @MetricsMemory(mb = 35.09)
    private int longestOnes_02(int[] nums, int k) {
        int max = 0;
        LinkedList<Integer> zeroIndexes = new LinkedList<>();
        zeroIndexes.addLast(-1);
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            if (nums[i] != 1) {
                if (zeroIndexes.size() == k + 1) {
                    max = Math.max(max, i - zeroIndexes.pollFirst() - 1);
                }
                zeroIndexes.addLast(i);
            }
        }
        max = Math.max(max, len - zeroIndexes.pollFirst() - 1);
        return max;
    }


    @MetricsRuntime(ms = 573, beats = 58.06)
    @MetricsMemory(mb = 35.95)
    private int longestOnes_01(int[] nums, int k) {
        int max = 0;
        int zeroIndexes[] = new int[k + 1];
        int zeroCnt = 1;
        int endIdx = 0;
        zeroIndexes[0] = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 1) {
                if (zeroCnt == k + 1) {
                    int bgnIdx = endIdx + 1;
                    if (bgnIdx == k + 1) {
                        bgnIdx = 0;
                    }
                    max = Math.max(max, i - zeroIndexes[bgnIdx] - 1);
                } else {
                    zeroCnt++;
                }
                if (++endIdx == k + 1) {
                    endIdx = 0;
                }
                zeroIndexes[endIdx] = i;
            }
        }
        if (zeroCnt == k + 1) {
            int bgnIdx = endIdx + 1;
            if (bgnIdx == k + 1) {
                bgnIdx = 0;
            }
            max = Math.max(max, nums.length - 1 - zeroIndexes[bgnIdx]);
        } else {
            max = Math.max(max, nums.length - zeroIndexes[0] - 1);
        }
        return max;
    }

    // ---------------------- TEST ----------------------
    public static void main(String[] args) {
        Q3811_MaxNumOfConsecutiveOnes_III q = new Q3811_MaxNumOfConsecutiveOnes_III();
        System.out.println(q.longestOnes(new int[]{1, 1, 0, 1, 1, 1}, 1)); // 6
        System.out.println(q.longestOnes(new int[]{1, 0, 1, 1, 0, 1}, 1)); // 4
        System.out.println(q.longestOnes(new int[]{0, 0, 0, 0, 0, 0}, 1)); // 1
        System.out.println(q.longestOnes(new int[]{0, 0, 0, 0, 0, 1}, 1)); // 2
        System.out.println(q.longestOnes(new int[]{1, 0, 0, 0, 0, 0}, 1)); // 2
        System.out.println(q.longestOnes(new int[]{0, 1, 0, 0, 0, 0}, 1)); // 2
        System.out.println(q.longestOnes(new int[]{0, 0, 0, 1, 0, 1}, 1)); // 3
        System.out.println(q.longestOnes(new int[]{0, 0, 0, 0, 1, 1}, 1)); // 3
        System.out.println(q.longestOnes(new int[]{1, 1, 0, 0, 0, 0}, 1)); // 3
        System.out.println(q.longestOnes(new int[]{1, 0, 1, 0, 0, 0}, 1)); // 3
        System.out.println(q.longestOnes(new int[]{1, 1, 1, 1, 1, 1}, 1)); // 6
        System.out.println(q.longestOnes(new int[]{1, 1, 1, 1, 1, 1}, 2)); // 6
        System.out.println(q.longestOnes(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2)); // 6
        System.out.println(q.longestOnes(new int[]{0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1,  1}, 3)); // 10
    }
}
