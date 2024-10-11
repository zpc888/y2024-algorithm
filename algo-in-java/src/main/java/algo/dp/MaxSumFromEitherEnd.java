package algo.dp;

import assist.DataHelper;
import assist.TestHelper;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Function;

/**
 * Given an array of integers, 2 players take turns to pick a number from either end of the array.
 * Find the maximum sum of the numbers picked by player 1 or 2. Assuming both players are trying to get the max sum.
 */
public class MaxSumFromEitherEnd {
    private int versionToRun = 1;

    public int win(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (versionToRun == 1) {
            return win_v1(nums);
        } else if (versionToRun == 2) {
            return win_v2_cache(nums);
        } else if (versionToRun == 3) {
            return win_v3_dp(nums);
        } else {
            throw new IllegalStateException("Invalid version to run: " + versionToRun);
        }
    }

    private int win_v3_dp(int[] nums) {
		int len = nums.length;
        int[][] dp1 = new int[len][len];        // first player
        int[][] dp2 = new int[len][len];        // second player
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                dp1[i][j] = -1;
                dp2[i][j] = -1;
            }
        }
		// base cases
		for (int i = 0; i < len; i++) {
			dp1[i][i] = nums[i];
			dp2[i][i] = 0;
		}
		// f1(i, j) = max(nums[i] + f2(i+1, j), nums[j] + f2(i, j-1))
		// f2(i, j) = min(f1(i+1, j), f1(i, j-1))
        //   L        1  5  2  3  6
        //  \/  R ->  0  1  2  3  4                       0  1  2  3  4
        //   0        1  5                               0 0 1
        //   1           5                              1     0
        //   2              2                           2        0
        //   3                 3                        3           0
        //   4                     6                    4              0
        for (int s = 1; s < len; s++) {
            for (int i = 0; i < len - s; i++) {
                int j = i + s;
                if (j > len - 1) {
                    break;
                }
                dp1[i][j] = Math.max(nums[i] + dp2[i+1][j], nums[j] + dp2[i][j-1]);
                dp2[i][j] = Math.min(dp1[i+1][j], dp1[i][j-1]);
            }
        }
		for (int i = 0; i < len - 2; i++) {
            dp1[i][i+1] = Math.max(nums[i] + dp2[i+1][i+1], nums[i+1] + dp2[i][i]);
            dp2[i][i+1] = Math.min(dp1[i+1][i+1], dp1[i][i]);
		}
        return Math.max(dp1[0][len-1], dp2[0][len-1]);
    }

    private  int win_v2_cache(int[] nums) {
        int[][] dp1 = new int[nums.length][nums.length];        // first player
        int[][] dp2 = new int[nums.length][nums.length];        // second player
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                dp1[i][j] = -1;
                dp2[i][j] = -1;
            }
        }
        int sum1 = win_v2_player1(nums, 0, nums.length - 1, dp1, dp2);
        int sum2 = win_v2_player2(nums, 0, nums.length - 1, dp1, dp2);
        return Math.max(sum1, sum2);
    }

    private int win_v2_player1(int[] nums, int l, int r, int[][] dp1, int[][] dp2) {
        int ans = dp1[l][r];
        if (ans != -1) {
            return ans;
        }
        if (l == r) {
            ans = nums[l];
        } else {
            ans = Math.max(
                    nums[l] + win_v2_player2(nums, l + 1, r, dp1, dp2),
                    nums[r] + win_v2_player2(nums, l, r - 1, dp1, dp2)
            );
        }
        dp1[l][r] = ans;
        return ans;
    }

	private int win_v2_player2(int[] nums, int l, int r, int[][] dp1, int[][] dp2) {
		int ans = dp2[l][r];
		if (ans != -1) {
			return ans;
		}
		if (l == r) {
			ans = 0;
		} else {
			ans = Math.min(
					win_v2_player1(nums, l + 1, r, dp1, dp2),
					win_v2_player1(nums, l, r - 1, dp1, dp2)
				);
		}
		dp2[l][r] = ans;
		return ans;
	}

    private int win_v1(int[] nums) {
        int sum1 = win_v1_player1(nums, 0, nums.length - 1);
        int sum2 = win_v1_player2(nums, 0, nums.length - 1);
        return Math.max(sum1, sum2);
    }

    private int win_v1_player1(int[] nums, int l, int r) {
       if (l == r) {
           return nums[l];
       }
         return Math.max(
                 nums[l] + win_v1_player2(nums, l + 1, r),
                 nums[r] + win_v1_player2(nums, l, r - 1)
         );
    }

    private int win_v1_player2(int[] nums, int l, int r) {
        if (l == r) {
            return 0;
        }
        return Math.min(
                win_v1_player1(nums, l + 1, r),
                win_v1_player1(nums, l, r - 1)
        );
    }

    public static void main(String[] args) {
        Function<int[], Integer> f = getIntegerFunction();
        int w1 = f.apply(new int[]{1, 5, 2, 3, 6});
        int w2 = f.apply(new int[]{1, 100, 6});
        int w3 = f.apply(new int[]{1, 6, 100});
        final int[] input = new int[]{5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};
        int w4 = f.apply(input);

        DataHelper.assertTrue(w1 == 9, "failed on test - 1");
        DataHelper.assertTrue(w2 == 100, "failed on test - 2");
        DataHelper.assertTrue(w3 == 101, "failed on test - 3");
        DataHelper.assertTrue(w4 == 32, "failed on test - 4");

        perfTest(input);
    }

    private static Function<int[], Integer> getIntegerFunction() {
        final MaxSumFromEitherEnd m = new MaxSumFromEitherEnd();
        Function<int[], Integer> f = (a) -> {
            m.versionToRun = 1;
            int retV1 = m.win(a);
            m.versionToRun = 2;
            int retV2 = m.win(a);
            m.versionToRun = 3;
            int retV3 = m.win(a);
            System.out.println(Arrays.toString(a) + " ==> result: v1 = " + retV1 + ", v2 = " + retV2 + ", v3 = " + retV3);
            if (retV1 != retV2 || retV1 != retV3) {
                throw new IllegalStateException("failed on test with input " + Arrays.toString(a));
            }
            return retV1;
        };
        return f;
    }

    private static void perfTest(int[] input) {
        final MaxSumFromEitherEnd m = new MaxSumFromEitherEnd();
        final int testCycles = 10_000;
        m.versionToRun = 1;
        Duration d1 = TestHelper.repeatRun(testCycles, () -> {
            m.win(input);
        });
        m.versionToRun = 2;
        Duration d2 = TestHelper.repeatRun(testCycles, () -> {
            m.win(input);
        });
        m.versionToRun = 3;
        Duration d3 = TestHelper.repeatRun(testCycles, () -> {
            m.win(input);
        });
        TestHelper.repeatRunWithResultCheck(100, () -> {
            return m.win(input);
        }, ((result, runNo) -> {
            DataHelper.assertTrue(result == 32, "failed on perf test - " + runNo + " with result "
                    + result + " instead of 32");
        }));
        System.out.println();
        System.out.println("Performance test result: v1 = " + d1.toMillis() + ", v2 = " + d2.toMillis()+ ", v3 = " + d3.toMillis());
        // Performance test result: v1 = 347, v2 = 34, v3 = 30
        // Reflection: With DP/Cache, the performance is 10 times faster than the recursive version.
    }
}
