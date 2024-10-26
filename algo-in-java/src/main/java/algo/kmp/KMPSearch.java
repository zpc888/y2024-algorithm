package algo.kmp;

import assist.BaseSolution;
import assist.DataHelper;

/**
 * Source: https://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm
 * <p />
 * Source: https://hwlang.de/algorithmen/pattern/kmpen.htm
 * <p/>
 * Knuth-Morris-Pratt (KMP) algorithm for pattern searching in a string
 */
public class KMPSearch extends BaseSolution<Integer> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 3;
    }

    public int search(String text, String pattern) {
        if (text == null || pattern == null || text.length() < pattern.length()) {
            return -1;
        }
        if (versionToRun == 1) {
            return text.indexOf(pattern);
        } else if (versionToRun == 2) {
            return searchBruteForce(text, pattern);
        } else if (versionToRun == 3) {
            return searchKMP(text, pattern);
        } else {
            throw new RuntimeException("No such version: " + versionToRun);
        }
    }

    private int searchBruteForce(String text, String pattern) {
		char[] str1 = text.toCharArray();
		char[] str2 = pattern.toCharArray();
        int n = str1.length;
        int m = str2.length;
        for (int i = 0; i <= n - m; i++) {
			for (int j = 0; j < m; j++) {
				if (str1[i + j] != str2[j]) {
					break;
				} else if (j == m - 1) {
					return i;
				}
			}
        }
        return -1;
    }

	// O(N) instead of O(N*M)
	// But according to the test, only much faster than java JDK String.indexOf() for large duplication
	// about 30 times faster. For normal cases, it's about 5 times slower than JDK String.indexOf()
	private int searchKMP(String text, String pattern) {
		char[] str1 = text.toCharArray(), str2 = pattern.toCharArray();
        int n = str1.length, m = str2.length;
        int[] nextArray = getNextArray(str2);
		int i = 0, j = 0;
		while (i < n  && j < m) {
			if (str1[i] == str2[j]) {
				i++;
				j++;
			} else {
				j = nextArray[j];
				if (j == -1) {
					i++;
					j = 0;
				}
			}
		}
		return j == m ? (i - m) : -1;
	}

	private int[] getNextArray(char[] str) {
		int m = str.length;
		int[] ret = new int[m];
		ret[0] = -1;
		if (m >= 2) {
			ret[1] = 0;
			// O(M)
			for (int i = 2; i < m; i++) {
				int j = i - 1;
				int prevChar = str[j];
				// if (prevChar == str[ret[j]]) {
				// 	// abcdabcde
				// 	//        3^
				// 	ret[i] = ret[j] + 1;
				// } else {
				// 	// agabagage
				// 	//    1   3^
				// 	j = ret[j];
				// 	while (ret[j] != prevChar && j != 0) {
				// 		j = ret[j];
				// 		ret[i] = ret[j] + 1;
				// 	}
				if (prevChar != str[ret[j]]) {
					j = ret[j];
					while (j != 0 && str[ret[j]] != prevChar) {
						j = ret[j];
					}
				}
				ret[i] = ret[j] + 1;
			}
		}
		return ret;
	}

    public static void main(String[] args) {
        KMPSearch sol = new KMPSearch();
		
		sol.testNextArray();

        sol.runAllVersions("example 1", () -> sol.search("hello", "ll"), 2);
        sol.runAllVersions("example 2", () -> sol.search("hello", "lle"), -1);
        sol.runAllVersions("example 3", () -> sol.search("hello", "he"), 0);
        sol.runAllVersions("example 4", () -> sol.search("hello", "hello"), 0);
        sol.runAllVersions("example 5", () -> sol.search("hello", "lo"), 3);
        sol.runAllVersions("example 6", () -> sol.search("hello", "o"), 4);
        sol.runAllVersions("example 7", () -> sol.search("hello", "a"), -1);
        sol.runAllVersions("example 8", () -> sol.search("h", "hello"), -1);
		sol.runAllVersions("example 9", () -> sol.search("abcabcabcghij", "abcabcgh"), 3);

		String word = sol.dupA(100_000) + "b";
		String pattern = sol.dupA(1_000) + "b";
		sol.runAllVersions("100k Duplication", () -> sol.search(word, pattern), 99_000);

        String[] a2eWords = DataHelper.genFixedSizeStrArr(100_000, 20, 100, "abcde");
        String[] a2ePatterns = DataHelper.genFixedSizeStrArr(100_000, 1, 10, "abcde");
		String[] abcWords = DataHelper.genFixedSizeStrArr(100_000, 20, 100, "abc");
		String[] abcPatterns = DataHelper.genFixedSizeStrArr(100_000, 1, 10, "abc");
        for (int i = 0; i < a2eWords.length; i++) {
            String s1 = a2eWords[i];
            String s2 = a2ePatterns[i];
            sol.runAllVersions("Random #" + (i+1) + ": " + s1 + ".indexOf(" + s2 + ")",
                    () -> sol.search(s1, s2), null);
			String s3 = abcWords[i];
			String s4 = abcPatterns[i];
			sol.runAllVersions("Random #" + (i+1) + ": " + s3 + ".indexOf(" + s4 + ")",
					() -> sol.search(s3, s4), null);
        }

        sol.performMeasure("abcde combination", () -> {
            for (int i = 0; i < a2eWords.length; i++) {
                sol.search(a2eWords[i], a2ePatterns[i]);
            }
            return null;
        });
//====================== < abcde combination > Performance Report ==============
//		Version-1: Duration: PT0.006962668S
//		Version-2: Duration: PT0.032455427S
//		Version-3: Duration: PT0.029439866S

		sol.performMeasure("abc combination", () -> {
			for (int i = 0; i < abcWords.length; i++) {
				sol.search(abcWords[i], abcPatterns[i]);
			}
			return null;
		});
//====================== < abcde combination > Performance Report ==============
//		Version-1: Duration: PT0.007233778S
//		Version-2: Duration: PT0.037802326S
//		Version-3: Duration: PT0.035273793S

		sol.performMeasure("large Duplication", () -> {
			sol.search(word, pattern);
			return null;
		});
//====================== < large Duplication > Performance Report ==============
//		Version-1: Duration: PT0.009818936S
//		Version-2: Duration: PT0.045170026S
//		Version-3: Duration: PT0.00029278S
	}

	private String dupA(int n) {
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			sb.append("a");
		}
		return sb.toString();
	}

	private void testNextArray() {
		int[] actual = null;
		String pattern = null;

		pattern = "ABCDABD";
		actual = getNextArray(pattern.toCharArray());
		DataHelper.assertTrue(DataHelper.isArrayEqual(
					new int[]{-1, 0, 0, 0, 0, 1, 2}, actual), pattern);

		pattern = "ABACABABC";
		actual = getNextArray(pattern.toCharArray());
		DataHelper.assertTrue(DataHelper.isArrayEqual(
					new int[]{-1, 0, 0, 1, 0, 1, 2, 3, 2}, actual), pattern);

		pattern = "ABACABABA";
		actual = getNextArray(pattern.toCharArray());
		DataHelper.assertTrue(DataHelper.isArrayEqual(
					new int[]{-1, 0, 0, 1, 0, 1, 2, 3, 2}, actual), pattern);

		pattern = "PARTICIPATE IN PARACHUTE";
		actual = getNextArray(pattern.toCharArray());
		DataHelper.assertTrue(DataHelper.isArrayEqual(
					new int[]{-1, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 1, 2, 3, 0, 0, 0, 0, 0}, actual), pattern);
	}
}
