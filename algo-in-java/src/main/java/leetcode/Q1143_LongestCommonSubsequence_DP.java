package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

/**
 * source: https://leetcode.com/problems/longest-common-subsequence/
 * Given two strings text1 and text2, return the length of their longest common subsequence. If there is no common subsequence, return 0.
 *
 * A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.
 *
 * For example, "ace" is a subsequence of "abcde".
 * A common subsequence of two strings is a subsequence that is common to both strings.
 */
public class Q1143_LongestCommonSubsequence_DP {
    public int longestCommonSubsequence(String text1, String text2) {
		if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
			return 0;
		}
		if (text1.equals(text2)) {
			return text1.length();
		}
		char[] a1 = text1.toCharArray();
		char[] a2 = text2.toCharArray();
//        return processV1_noDP(a1, a2, a1.length - 1, a2.length - 1);
		return processV2_DP(a1, a2);
    }

	@MetricsRuntime(ms = 13, beats = 95.76)
	@MetricsMemory(mb = 48.91, beats = 90.63)
	int processV2_DP(char[] a1, char[] a2) {
		int n1 = a1.length;
		int n2 = a2.length;
		int[][] dp = new int[n1][n2];
		dp[0][0] = a1[0] == a2[0] ? 1 : 0;
		for (int i = 1; i < n2; i++) {
			dp[0][i] = a1[0] == a2[i] ? 1 : dp[0][i - 1];
		}
		for (int i = 1; i < n1; i++) {
			dp[i][0] = a1[i] == a2[0] ? 1 : dp[i - 1][0];
		}
		for (int i1 = 1; i1 < n1; i1++) {
			for (int i2 = 1; i2 < n2; i2++) {
				dp[i1][i2] = Math.max(dp[i1-1][i2], dp[i1][i2-1]);
				int p3 = (a1[i1] == a2[i2] ? 1 : 0) + dp[i1-1][i2-1];
				dp[i1][i2] = Math.max(dp[i1][i2], p3);
			}
		}
		return dp[n1 - 1][n2 - 1];
	}

    // time limit exceeded
	/**
	 * find the max common subsequence between a1[0...i1] and a2[0...i2]
	 */
	int processV1_noDP(char[] a1, char[] a2, int i1, int i2) {
		if (i1 == 0) {
			if (i2 == 0) {
				return a1[i1] == a2[i2] ? 1 : 0;
			} else if (a1[i1] == a2[i2]) {
				return 1;
			} else {
				return processV1_noDP(a1, a2, i1, i2 - 1);
			}
		} else if (i2 == 0) {
			if (a1[i1] == a2[i2]) {
				return 1;
			} else {
				return processV1_noDP(a1, a2, i1 - 1, i2);
			}
		} else {		// both have 2 or more letters
			// 1. ignore a1[i1]
			int p1 = processV1_noDP(a1, a2, i1 - 1, i2);	
			// 2. ignore a2[i2]
			int p2 = processV1_noDP(a1, a2, i1, i2 - 1);
			// 3. ignore both can be archived by p1 or p2 next run
			// 4. consider both a1[i1] and a2[i2]
			int p3 = processV1_noDP(a1, a2, i1 - 1, i2 - 1);
			if (a1[i1] == a2[i2]) {
				p3++;
			}
			return Math.max(Math.max(p1, p2), p3);
		}
	}
}
