package leetcode;

import assist.BaseSolution;

import java.util.*;

/**
 * source: https://leetcode.com/problems/concatenated-words/
 *
 * Q472. Concatenated Words.
 * Given a list of words (without duplicates), please write a program that returns all concatenated words in the given list of words.
 * A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given array.
 * Constraints:
 *  1 <= words.length <= 10^4
 *  0 <= words[i].length <= 1000
 *  0 <= words[i].length <= 1000
 *  All strings consist of lowercase English letters only.
 *
 *  <p />
 *  Example 1:
 *  <pre>
 *  Input: ["cat","cats","catsdogcats","dog","dogcatsdog","hippopotamuses","rat","ratcatdogcat"]
 *  Output: ["catsdogcats","dogcatsdog","ratcatdogcat"]
 *  Explanation: "catsdogcats" can be concatenated by "cats", "dog" and "cats";
 *  "dogcatsdog" can be concatenated by "dog", "cats" and "dog";
 *  "ratcatdogcat" can be concatenated by "rat", "cat", "dog" and "cat".
 *  </pre>
 *
 *  <p />
 *  Example 2:
 *  <pre>
 *      Input: ["cat","dog","catdog"]
 *      Output: ["catdog"]
 *      Explanation: "catdog" can be concatenated by "cat" and "dog".
 *  </pre>
 *
 */
public class Q0472_Concatenated_Words extends BaseSolution<List<String>> {
	@Override
	protected int getNumberOfVersionsImplemented() {
		return 2;
	}

    public List<String> findAllConcatenatedWordsInADict(String[] words) {
		if (versionToRun == 1) {
			return processV1_Timeout(words);
		} else if (versionToRun == 2) {
			return processV2(words);
		} else {
			throw new RuntimeException("No such version: " + versionToRun);
		}
	}

	public List<String> processV2(String[] words) {
		Map<Character, Map<Integer, List<String>>> byFirstLetterThenLen = new HashMap<>();
		for (String w: words) {
			Character firstLetter = w.charAt(0);
			Map<Integer, List<String>> byLen = byFirstLetterThenLen.getOrDefault(w.charAt(0), new HashMap<>(16));
			int len = w.length();
			List<String> list = byLen.getOrDefault(len, new ArrayList<>(16));
			list.add(w);
			byLen.put(len, list);
			byFirstLetterThenLen.put(firstLetter, byLen);
		}
		List<String> ret = new ArrayList<>(16);
		for (String w: words) {
			if (isConcatenated_V2(w, byFirstLetterThenLen, true)) {
				ret.add(w);
			}
		}
		return ret;
	}

	private boolean isConcatenated_V2(String w, Map<Character, Map<Integer, List<String>>> map, boolean isRoot) {
		if (w.length() == 0) {
			return !isRoot;
		}
		char c = w.charAt(0);
		Map<Integer, List<String>> byLen = map.get(c);
		if (byLen == null || byLen.isEmpty()) {
			return false;
		}
		int len = w.length();
		if (isRoot) {			// concatenated word must have at least 2 words
			len = len - 1;
		}
		for (int i = len; i > 0; i--) {
			List<String> list = byLen.get(i);
			if (list == null || list.isEmpty()) {
				continue;
			}
			for (String s: list) {
				if (w.startsWith(s)) {
					if (isConcatenated_V2(w.substring(i), map, false)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public List<String> processV1_Timeout(String[] words) {
		Comparator<String> sortingWordByLenDesc = (s1, s2) -> s2.length() - s1.length();
//		Set<String> set = new HashSet<>();
//		for (String w: words) {
//			set.add(w);
//		}
		List[] byFirstLetters = new List[26];
		for (String w: words) {
			int idx = (int)(w.charAt(0) - 'a');
			List<String> myList = byFirstLetters[idx];
			if (myList == null) {
				myList = new ArrayList<String>(32);
				byFirstLetters[idx] = myList;
			}
			myList.add(w);
		}
		for (int i = 0; i < byFirstLetters.length; i++) {
			if (byFirstLetters[i] == null) {
				byFirstLetters[i] = Collections.emptyList();
			} else {
				byFirstLetters[i].sort(sortingWordByLenDesc);
			}
		}
		List<String> ret = new ArrayList<>(16);
		for (String w: words) {
			if (isConcatenatedWords_V1_Timeout(w, byFirstLetters)) {
				ret.add(w);
			}
		}
        return ret;
    }

	// time out
	private boolean isConcatenatedWords_V1_Timeout(String word, List[] byFirstLetters) {
		int idx = (int)(word.charAt(0) - 'a');
		List<String> startings = (List<String>) byFirstLetters[idx];
		// descending order
		if (startings.isEmpty() || startings.get(startings.size() - 1).length() >= word.length()) {
			return false;
		}
		for (int i = 0; i < startings.size(); i++) {
			String start = startings.get(i);
			if (start.length() >= word.length()) {
				continue;
			}
			if (word.startsWith(start)) {
				if (containsAtLeastOne_V1_Timeout(word.substring(start.length()), byFirstLetters)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean containsAtLeastOne_V1_Timeout(String rest, List[] byFirstLetters) {
		int restLen = rest.length();
		if (restLen == 0) {
			return true;
		}
		int idx = (int)(rest.charAt(0) - 'a');
		List<String> startings = (List<String>) byFirstLetters[idx];
		for (int i = 0; i < startings.size(); i++) {
			String start = startings.get(i);
			int startLen = start.length();
			if (startLen > restLen) {
				continue;
			}
			if (rest.charAt(startLen - 1) == start.charAt(startLen -1 ) && rest.startsWith(start)) {
				if (containsAtLeastOne_V1_Timeout(rest.substring(startLen), byFirstLetters)) {
					return true;
				}
			}
		}
		return false;
	}

}
