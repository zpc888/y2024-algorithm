package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

/**
 * see: https://leetcode.com/problems/longest-palindromic-subsequence/
 * <p>
 * Given a string s, find the longest palindromic subsequence's length in s.
 * A subsequence is a sequence that can be derived from another sequence by deleting some or no elements
 * without changing the order of the remaining elements.
 * </p>
 */
public class Q0516_LongestPalindromicSubsequence {
    public int longestPalindromeSubseq(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int len = s.length();
        char[] a = s.toCharArray();
        // return processV1_noDP(a, 0, len - 1);
        return processV2_DP(a);
    }

	@MetricsRuntime(description = "DP solution", ms = 21, beats = 94.18)
	@MetricsMemory(mb = 55.20, beats = 32.59)
	int processV2_DP(char[] a) {
		int len = a.length;
		int[][] dp = new int[len][len];
		for (int i = 0; i < len; i++) {
			dp[i][i] = 1;
		}
		for (int toGap = 1; toGap < len; toGap++) {
			for (int i = 0; i < len - 1; i++) {
				int to = i + toGap;
				if (to >= len) {
					break;
				}
				if (a[i] == a[to]) {
					dp[i][to] = 2 + dp[i + 1][to - 1];
				} else {
					dp[i][to] = Math.max(dp[i][to-1], dp[i+1][to]);
				}
			}
		}
		return dp[0][len - 1];
	}

    // leetcode time limit exceeded after 61 / 86 testcases passed
    int processV1_noDP(char[] a, int from, int to) {
        if (from > to) {
            return 0;
        }
		if (from == to) {
			return 1;
		}
		if (a[from] == a[to]) {
			return 2 + processV1_noDP(a, from + 1, to - 1);
		} else {
			int p1 = processV1_noDP(a, from + 1, to);
			int p2 = processV1_noDP(a, from, to - 1);
			return Math.max(p1, p2);
		}
    }
}
