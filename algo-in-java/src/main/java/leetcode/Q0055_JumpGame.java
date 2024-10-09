package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/jump-game/
 *
 * You are given an integer array nums. You are initially positioned at the array's first index,
 * and each element in the array represents your maximum jump length at that position.
 *
 * Return true if you can reach the last index, or false otherwise.
 */
public class Q0055_JumpGame {
    public boolean canJump(int[] nums) {
//        return canJump_V1(nums);
        return canJump_V2(nums);
    }

    @MetricsRuntime(ms = 2, beats = 78.11)
    @MetricsMemory(mb = 45.39, beats = 84.38)
    private boolean canJump_V2(int[] nums) {
        for (int i = 0, max = 0; i < nums.length; i++) {
			if (max >= nums.length - 1) {
				return true;
			}
			if (max < i) {
				return false;
			}
			max = Math.max(max, i + nums[i]);
        }
		return false;
    }

    @MetricsRuntime(ms = 156, beats = 11.96)
    @MetricsMemory(mb = 45.74, beats = 32.45)
    private boolean canJump_V1(int[] nums) {
		if (nums.length == 1) {
			return true;
		}
		int goalPos = nums.length - 1;
		int prevPos = -1;
		while (true) {
			prevPos = -1;
			for (int p = goalPos - 1; p >= 0; p--) {
				if (nums[p] + p >= goalPos) {
					prevPos = p;
				}
			}
			if (prevPos == -1) {
				return false;
			} else if (prevPos == 0) {
				return true;
			}
			goalPos = prevPos;
		}
    }

    public static void main(String[] args) {
        verify(true, new int[]{2,3,1,1,4});
        verify(false, new int[]{3,2,1,0,4});
    }

    private static void verify(boolean expected, int[] nums) {
        Q0055_JumpGame q = new Q0055_JumpGame();
        boolean result = q.canJump(nums);
        System.out.printf("%s => %s%n", Arrays.toString(nums), result);
        if (expected != result) {
            System.out.println("❌");
            System.out.println("  expected: " + expected);
            System.out.println("  result: " + result);
        } else {
            System.out.println("✅");
        }
    }
}
