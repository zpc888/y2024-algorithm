package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

/**
 * https://leetcode.com/problems/jump-game-iii/
 *
 * Given an array of non-negative integers arr, you are initially positioned at start index of the array.
 * When you are at index i, you can jump to i + arr[i] or i - arr[i], check if you can reach to any index with value 0.
 *
 * Notice that you can not jump outside of the array at any time.
 */
public class Q1306_JumpGame_III {
    public boolean canReach(int[] arr, int start) {
        return canReach_V1(arr, start);
    }

    @MetricsRuntime(ms = 1, beats = 99.39)
    @MetricsMemory(mb = 59.22, beats = 26.13)
    private boolean canReach_V1(int[] arr, int start) {
        if (start < 0 || start >= arr.length || arr[start] < 0) {
            return false;
        }
        if (arr[start] == 0) {
            return true;
        }
        arr[start] = -arr[start];
        return canReach_V1(arr, start + arr[start]) || canReach_V1(arr, start - arr[start]);
    }
}
