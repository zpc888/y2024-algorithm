package algo.slidewindow;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.*;

/**
 * Given a string, find the length of the longest substring in it with no more than N distinct characters.
 * You can assume that N is less than or equal to the length of the string.
 * <pre>
 * Example 1:
 * Input: String="araaci", N=2
 * Output: 4
 * Explanation: The longest substring with no more than '2' distinct characters is "araa".
 * Example 2:
 * Input: String="araaci", N=1
 * Output: 2
 * Explanation: The longest substring with no more than '1' distinct characters is "aa".
 * Example 3:
 * Input: String="cbbebi", N=3
 * Output: 5
 * Explanation: The longest substrings with no more than '3' distinct characters are "cbbeb" & "bbebi".
 * </pre>
 */
public class LongestSubStrContainingNChars extends BaseSolution<Integer> {
	@Override
	public int getNumberOfVersionsImplemented() {
		return 2;
	}

    public int solution(String str, int n) {
		if (str == null || str.length() == 0 || n <= 0) {
			return 0;
		}
		char[] chars = str.toCharArray();
		if (this.versionToRun == 1) {
			return process_v1_no_slide_window(chars, n);
		} else if (this.versionToRun == 2) {
				return process_v2_slide_window(chars, n);
		} else {
			throw new RuntimeException("Unsupported version: " + versionToRun);
		}
	}

	private int process_v1_no_slide_window(char[] chars, int n) {
		int len = chars.length;
		int ret = 0;
		for (int i = 0; i < len; i++) {
			Set<Character> set = new HashSet<>();
			for (int j = i; j < len; j++) {
				set.add(chars[j]);
				if (set.size() == n + 1) {
					ret = Math.max(ret, j - i);
					break;
				}
			}
			if (set.size() <= n) {
				ret = Math.max(ret, len - i);
			}
		}
		return ret;
	}

	private int process_v2_slide_window(char[] chars, int n) {
		int len = chars.length;
		int ret = 0;
		int slideWinBgn = 0;
		int slideWinEnd = 0;
		Map<Character, Integer> map = new HashMap<>(n + 1);
		while (slideWinEnd < len) {
			char c = chars[slideWinEnd++];
			map.put(c, map.getOrDefault(c, 0) + 1);
			if (map.size() == n + 1) {
				ret = Math.max(ret, slideWinEnd - slideWinBgn - 1);
			}
			while (map.size() == n + 1) {
				char bgnC = chars[slideWinBgn++];
				int count = map.get(bgnC);
				if (count == 1) {
					map.remove(bgnC);
				} else {
					map.put(bgnC, --count);
				}
			}
		}
        ret = Math.max(ret, slideWinEnd - slideWinBgn);
		return ret;
	}

    public static void main(String[] args) {
        LongestSubStrContainingNChars sol = new LongestSubStrContainingNChars();
		verify(sol, "araaci", 2, 4);
		verify(sol, "araaci", 1, 2);
		verify(sol, "cbbebi", 3, 5);
		verify(sol, "cbbebi", 7, 6);

		String[] words = DataHelper.generateRandomWords(10000, 1, 100,
				"abcdefghijklmnopqrstuvwxyz");
		Random random = new Random();
		int i = 0;
		for (String word : words) {
			i++;
			int n = random.nextInt(word.length() + 1);
			sol.runAllVersions(i + " word: " + word + ", n = " + n,
					() -> sol.solution(word, n), null);
		}
	}

	private static void verify(LongestSubStrContainingNChars sol,
							   String str, int n, int expected) {
		sol.runAllVersions("Run '" + str + "', n = " + n,
				() -> sol.solution(str, n), expected);
	}
}
