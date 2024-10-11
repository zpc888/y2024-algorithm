package algo.dp;

import assist.TestHelper;

import java.time.Duration;
import java.util.function.BiConsumer;

/**
 * Given a string of digits, find the number of ways to decode all digits into letters.
 * Each digit maps to a letter ('a' -> 1, 'b' -> 2, ..., 'z' -> 26).
 * <pre>
 * Example 1:
 *     Input: "12"
 *     Output: 2  ab, l
 * Example 2:
 *    Input: "405"
 *    Output: 0
 * Example 3:
 *    Input: "226"
 *    Output: 3  bbf, bz, vf
 * Example 3:
 *    Input: "227"
 *    Output: 2  bbg,  vg
 * Example 3:
 *    Input: "2270"
 *    Output: 0
 * </pre>
 */
public class NumToStrDecoder {
	private int versionToRun = 1;

	public int decodeDigitsToStr(String digits) {
		if (digits == null || digits.length() == 0) {
			return 0;
		}
		if (versionToRun == 1) {
			return dec_v1(digits, 0);
		} else if (versionToRun == 2) {
			return dec_v2_with_dp_cache(digits, 0);
		} else if (versionToRun == 3) {
			return dec_v3_with_dp(digits, 0);
		} else {
			throw new IllegalStateException("Not supported version: " + versionToRun);
		}
	}

	private int dec_v2_with_dp_cache(String digits, int idx) {
		int len = digits.length();
		int[] dp = new int[len + 1];
		return doDecV2(digits, 0, dp);
	}

	private int dec_v3_with_dp(String digits, int idx) {
		int len = digits.length();
		int[] dp = new int[len + 1];
		dp[len] = 1;
		for (int i = len - 1; i >=0; i--) {
			int c = digits.charAt(i) - '0';
			if (c == 0) {
				dp[i] = 0;
			} else {
				int next1 = dp[i + 1];
				int next2 = 0;
				if (i + 1 < len) {
					int c2 = digits.charAt(i + 1) - '0';
					if (c * 10 + c2 <= 26) {
						next2 = dp[i + 2];
					}
				}
				dp[i] = next1 + next2;
			}
		}
		return dp[0];
	}

	private int doDecV2(String digits, int idx, int[] dp) {
		int len = digits.length();
		int ans = 0;
		if (idx >= len) {
			ans = 1;
		} else {
			int c = digits.charAt(idx) - '0';
			if (c == 0) {
				ans = 0;
			} else {
				int next1 = dec_v1(digits, idx+1);
				int next2 = 0;
				if (idx + 1 < len) {
					int c2 = digits.charAt(idx+1) - '0';
					if (c*10 + c2 <= 26) {
						next2 = dec_v1(digits, idx+2);
					}
				}
				ans = next1 + next2;
			}
		}
		dp[idx] = ans;
		return ans;
	}

	private int dec_v1(String digits, int idx) {
		int len = digits.length();
		if (idx >= len) {
			return 1;
		}
		int c = digits.charAt(idx) - '0';
		if (c == 0) {
			return 0;
		}
		int next1 = dec_v1(digits, idx+1);
		int next2 = 0;
		if (idx + 1 < len) {
			int c2 = digits.charAt(idx+1) - '0';
			if (c*10 + c2 <= 26) {
				next2 = dec_v1(digits, idx+2);
			}
		}
		return next1 + next2;
	}

	public static void main(String[] args) {
		NumToStrDecoder n = new NumToStrDecoder();
		BiConsumer<String, Integer> f = (s, expect) -> {
			n.versionToRun = 1;
			int actual1 = n.decodeDigitsToStr(s);
			n.versionToRun = 2;
			int actual2 = n.decodeDigitsToStr(s);
			n.versionToRun = 3;
			int actual3 = n.decodeDigitsToStr(s);
			System.out.println(s + " => V1 = " + actual1 
					+ ", V2 = " + actual2 + ", V3 = " + actual3);
			if (actual1 != expect) {
				throw new IllegalStateException("expected: " + expect + ", but v1 got: " + actual1);
			}
			if (actual2 != expect) {
				throw new IllegalStateException("expected: " + expect + ", but v2 got: " + actual2);
			}
			if (actual3 != expect) {
				throw new IllegalStateException("expected: " + expect + ", but v3 got: " + actual3);
			}
		};
		f.accept("12", 2);
		f.accept("405", 0);
		f.accept("226", 3);
		f.accept("227", 2);
		f.accept("2270", 0);

		doPerfTest(10_000);
	}

	private static void doPerfTest(int testCycles) {
		NumToStrDecoder n = new NumToStrDecoder();
		n.versionToRun = 1;
		final String digits = "1234561237891";
		Duration d1 = TestHelper.repeatRun(testCycles, () -> {
			n.decodeDigitsToStr(digits);
		});
		n.versionToRun = 2;
		Duration d2 = TestHelper.repeatRun(testCycles, () -> {
			n.decodeDigitsToStr(digits);
		});
		n.versionToRun = 3;
		Duration d3 = TestHelper.repeatRun(testCycles, () -> {
			n.decodeDigitsToStr(digits);
		});
		System.out.println("Perf testing v1 vs v2 vs v3 ============");
		System.out.println("\tV1: " + d1.toMillis() + "ms, V2: " + d2.toMillis() + "ms, V3: " + d3.toMillis() + "ms");
		// Perf testing v1 vs v2 vs v3 ============
		//     V1: 8ms, V2: 3ms, V3: 3ms
	}
}
