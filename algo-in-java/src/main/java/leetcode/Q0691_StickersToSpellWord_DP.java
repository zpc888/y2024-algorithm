package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * source: https://leetcode-cn.com/problems/stickers-to-spell-word/
 * </p>
 * <p>
 * We are given n different types of stickers. Each sticker has a lowercase English word on it.
 * </p>
 * <p>
 * You would like to spell out the given string target by cutting individual letters from your collection of
 * stickers and rearranging them. You can use each sticker more than once if you want, and you have infinite quantities of each sticker.
 * </p>
 * <p>
 * Return the minimum number of stickers that you need to spell out target. If the task is impossible, return -1.
 * </p>
 * <p>
 * Note: In all test cases, all words were chosen randomly from the 1000 most common US English words,
 * and target was chosen as a concatenation of two random words.
 * </p>
 * <pre>
 * Example 1:
 *      Input: stickers = ["with","example","science"], target = "thehat"
 *      Output: 3
 *      Explanation:
 *          We can use 2 "with" stickers, and 1 "example" sticker.
 *          After cutting and rearrange the letters of those stickers, we can form the target "thehat".
 *          Also, this is the minimum number of stickers necessary to form the target string.

 *  Example 2:
 *      Input: stickers = ["notice","possible"], target = "basicbasic"
 *      Output: -1
 *      Explanation:
 *          We cannot form the target "basicbasic" from cutting letters from the given stickers.
 * </pre>
 */
public class Q0691_StickersToSpellWord_DP {
    public int minStickers(String[] stickers, String target) {
//        return minStickers_v1(stickers, target);
//		return minStickers_v2(stickers, target);		// 15 times faster than v1 with letter frequency
		return minStickers_v3(stickers, target);		// slightly slower than v2 due to restore word frequency for every sticker applied
		// v3 save little space than v2, but recovery of word frequency is slower than v2 StringBuilder
		// v1 - 256ms, v2 - 17ms, v3 - 21ms
		// changing v3-applySticker to return a new int[26] and comment out reverseApplyStickerV3, which shows 19ms
    }

	@MetricsRuntime(ms = 21, beats = 61.15)
	@MetricsMemory(mb = 45.04, beats = 66.70)
	private int minStickers_v3(String[] stickers, String target) {
		int len = stickers.length;
		int[][] sArr = new int[len][];
		for (int i = 0; i < len; i++) {
			sArr[i] = getWordFreq(stickers[i]);
		}
		int ret = processV3(sArr, getWordFreq(target), new HashMap<String, Integer>());
		return ret == Integer.MAX_VALUE ? -1 : ret;
	}


	@MetricsRuntime(ms = 17, beats = 69.99)
	@MetricsMemory(mb = 45.09, beats = 66.70)
	private int minStickers_v2(String[] stickers, String target) {
		int len = stickers.length;
		int[][] sArr = new int[len][];
		for (int i = 0; i < len; i++) {
			sArr[i] = getWordFreq(stickers[i]);
		}
		int ret = processV2(sArr, target, new HashMap<String, Integer>());
		return ret == Integer.MAX_VALUE ? -1 : ret;
	}


	// Time Limit Exceeded for test case with dp cache
	// stickers = ["control","heart","interest","stream","sentence","soil","wonder","them","month","slip","table","miss","boat","speak","figure","no","perhaps","twenty","throw","rich","capital","save","method","store","meant","life","oil","string","song","food","am","who","fat","if","put","path","come","grow","box","great","word","object","stead","common","fresh","the","operate","where","road","mean"]
	// target = "stoodcrease"
	// @MetricsRuntime(ms = 398, beats = 12.15)		// using String.charAt(i)
	@MetricsRuntime(ms = 256, beats = 18.00)		// using String.toCharArray()   constant time is faster
	@MetricsMemory(mb = 45.47, beats = 29.93)
    private int minStickers_v1(String[] stickers, String target) {
        int ret = processV1(stickers, target, new HashMap<String, Integer>());
		return ret == Integer.MAX_VALUE ? -1 : ret;
    }


	private String buildLetterFreqStr(int[] freq) {
		StringBuilder ret = new StringBuilder(32);
		for (int i = 0; i < freq.length; i++) {
			if (freq[i] > 0) {
				ret.append((char)('a' + i)).append(freq[i]);
			}
		}
		return ret.toString();
	}

	private int processV3(int[][] stickers, int[] word, Map<String, Integer> dp) {
		String target = buildLetterFreqStr(word);
		if (dp.containsKey(target)) {
			return dp.get(target);
		}
		int tLen = target.length();
		if (tLen == 0) {
			return 0;
		}
		int ret = Integer.MAX_VALUE;
		int ch1Idx = target.charAt(0) - 'a';
		for (int i = 0; i < stickers.length; i++) {
			int[] sArr = stickers[i];
			if (sArr[ch1Idx] > 0) {				// must contains 1st letter
				applyStickerV3(sArr, word);
				int nextProcess = processV3(stickers, word, dp);
				if (nextProcess != Integer.MAX_VALUE) {
					ret = Math.min(ret, nextProcess + 1);
				}
				reverseApplyStickerV3(sArr, word);
			}
		}
		dp.put(target, ret);
		return ret;
	}

	private void applyStickerV3(int[] sticker, int[] word) {
		for (int i = 0; i < sticker.length; i++) {
			word[i] -= sticker[i];
		}
	}

	private void reverseApplyStickerV3(int[] sticker, int[] word) {
		for (int i = 0; i < sticker.length; i++) {
			word[i] += sticker[i];
		}
	}


	private int[] getWordFreq(String word) {
		int[] freq = new int[26];
		for (char ch: word.toCharArray()) {
			freq[ch - 'a']++;
		}
		return freq;
	}

	private String buildFromFreq(int[] chars) {
		StringBuilder ret = new StringBuilder(32);
		for (int i = 0; i < chars.length; i++) {
			int freq = chars[i];
			if (freq > 0) {
				char c = (char)('a' + i);
				for (int j = 0; j < freq; j++) {
					ret.append(c);
				}
			}
		}
		return ret.toString();
	}

    private int processV2(int[][] stickers, String target, Map<String, Integer> dp) {
		if (dp.containsKey(target)) {
			return dp.get(target);
		}
		int tLen = target.length();
		if (tLen == 0) {
			return 0;
		}
		int ch1Idx = target.charAt(0) - 'a';
		int ret = Integer.MAX_VALUE;
		for (int i = 0; i < stickers.length; i++) {
			int[] sArr = stickers[i];
			if (sArr[ch1Idx] > 0) {				// must contains 1st letter
				String remain = applyStickerV2(sArr, target);
				int nextProcess = processV2(stickers, remain, dp);
				if (nextProcess != Integer.MAX_VALUE) {
					ret = Math.min(ret, nextProcess + 1);
				}
			}
		}
		dp.put(target, ret);
		return ret;
	}

	private String applyStickerV2(int[] sticker, String word) {
		int[] wArr = getWordFreq(word);
		for (int i = 0; i < sticker.length; i++) {
			wArr[i] -= sticker[i];
		}
		return buildFromFreq(wArr);
	}

    private int processV1(String[] stickers, String target, Map<String, Integer> dp) {
		if (dp.containsKey(target)) {
			return dp.get(target);
		}
        if (target.length() == 0) {
            return 0;
        }
        int ret = Integer.MAX_VALUE;
        for (String sticker: stickers) {
            String remain = applySticker(sticker, target);
            if (!remain.equals(target)) {
				int nextMinStickers = processV1(stickers, remain, dp);
				if (nextMinStickers != Integer.MAX_VALUE) {
					ret = Math.min(ret, 1 + nextMinStickers);
				}				// else no solution
			}
		}
		// MAX_VALUE means impossible
		dp.put(target, ret);
		return ret;
    }

	String applySticker(String sticker, String word) {
		char[] wArr = word.toCharArray();
		int wLen = wArr.length;
		char[] sArr = sticker.toCharArray();
		int sLen = sArr.length;
		boolean[] removedIdx = new boolean[wLen];
		int removeCnt = 0;
		for (int i = 0; i < sLen; i++) {
			char ch1 = sArr[i];
			for (int j = 0; j < wLen; j++) {
				char ch2 = wArr[j];
				if (ch1 == ch2 && !removedIdx[j]) {
					removedIdx[j] = true;
					removeCnt++;
					if (removeCnt == wLen) {
						return "";
					}
					break;
				}
			}
		}
		if (removeCnt == 0) {
			return word;
		} else {
			char[] ret = new char[wLen - removeCnt];
			int idx = 0;
			for (int i = 0; i < wLen; i++) {
				if (!removedIdx[i]) {
					ret[idx++] = wArr[i];
				}
			}
			return new String(ret);
		}
	}

	public static void main(String[] args) {
		Q0691_StickersToSpellWord_DP sol = new Q0691_StickersToSpellWord_DP();
		verify(3, new String[]{"with", "example", "science"}, "thehat", sol);
		verify(-1, new String[]{"notice", "possible"}, "basicbasic", sol);
		verify(2, new String[]{"ab", "c", "cometoba"}, "ababc", sol);
		reachTimeLimitIfNoDPCache(sol);
		debugAccuracy(sol);
	}

	private static void debugAccuracy(Q0691_StickersToSpellWord_DP sol) {
		String[] stickers = {"this","island","keep","spring","problem","subject"};
		String target = "gasproper";
		int ret = sol.minStickers(stickers, target);
		System.out.println("ret=" + ret);
		if (ret != 3) {
			throw new RuntimeException("expecting: 3; but actual is: " + ret);
		}
	}

	private static void reachTimeLimitIfNoDPCache(Q0691_StickersToSpellWord_DP sol) {
		String[] stickers = new String[]{"control","heart","interest","stream","sentence","soil","wonder","them",
				"month","slip","table","miss","boat","speak","figure","no","perhaps","twenty","throw","rich","capital",
				"save","method","store","meant","life","oil","string","song","food","am","who","fat","if","put","path",
				"come","grow","box","great","word","object","stead","common","fresh","the","operate","where","road","mean"};
		String target = "stoodcrease";
		long begin = System.currentTimeMillis();
		int ret = sol.minStickers(stickers, target);
		long end = System.currentTimeMillis();
		System.out.println("ret=" + ret + ", time=" + ((end - begin) / 1000d) + "seconds.");
		// without dp, it takes over 1 hour
		// with dp, it takes over 0.023 seconds
	}

	private static void verify(int expect, String[] stickers, String target, Q0691_StickersToSpellWord_DP sol) {
		int actual = sol.minStickers(stickers, target);
		System.out.println("input: stickers=" + Arrays.toString(stickers) + ", target=" + target
				+ "; expect=" + expect + ", actual=" + actual);
		if (expect != actual) {
			throw new RuntimeException("input: stickers=" + Arrays.toString(stickers) + ", target=" + target
					+ "; expect=" + expect + ", actual=" + actual);
		}
	}
}
