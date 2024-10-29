package algo.str.kmp;

import assist.BaseSolution;
import assist.DataHelper;

/**
 * Given two strings s1 and s2, write a function to check if s2 is a rotation of s1.
 */
public class StrRotation extends BaseSolution<Boolean> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 2;
    }

    public boolean isRotation(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }
        if (versionToRun == 1) {
            return processV1(s1, s2);
		} else if (versionToRun == 2) {
            return processV2_KMP(s1, s2);
        } else {
            throw new RuntimeException("No such version: " + versionToRun);
        }
    }

    private boolean processV1(String s1, String s2) {
        return (s1 + s1).contains(s2);
    }

	// KMP
    private boolean processV2_KMP(String s1, String s2) {
		char[] s1Chars = (s1 + s1).toCharArray();
		char[] s2Chars = s2.toCharArray();
		int len = s2Chars.length;
		int[] nextArr = buildNextArray(s2Chars);
		int i = 0, j = 0;
		while (i < 2 * len && j < len) {
			if (s1Chars[i] == s2Chars[j]) {
				i++;
				j++;
			} else {
				if (nextArr[j] == -1) {
					i++;
					j = 0;
				} else {
					j = nextArr[j];
				}
			}
		}
		return j == len;
	}

	private int[] buildNextArray(char[] str) {
		int len = str.length;
		int[] ret = new int[len];
		ret[0] = -1;
		if (len >= 2) {
			ret[1] = 0;
		}
		for (int i = 2; i < len; i++) {
			char prevCh = str[i - 1];
			int prevNext = i - 1;
			while (ret[prevNext] != -1 && prevCh != str[ret[prevNext]]) {
				prevNext = ret[prevNext];
			}
			ret[i] = ret[prevNext] + 1;
		}
		return ret;
	}

	public static void main(String[] args) {
		StrRotation sol = new StrRotation();
		sol.runAllVersions("baaa vs aaba",
				() -> sol.isRotation("baaa", "aaba"), true);
		sol.runAllVersions("test 01",
				() -> sol.isRotation("erbottlewat", "waterbottle"), true);
		sol.runAllVersions("test 01",
				() -> sol.isRotation("abcd", "abcd"), true);
		sol.runAllVersions("test 01",
				() -> sol.isRotation("abcd", "bcda"), true);
		sol.runAllVersions("test 01",
				() -> sol.isRotation("abcd", "cdab"), true);
		sol.runAllVersions("test 01",
				() -> sol.isRotation("abcd", "dabc"), true);
		sol.runAllVersions("test 01",
				() -> sol.isRotation("abcd", "abc"), false);
		sol.runAllVersions("test 01",
				() -> sol.isRotation("abcd", "abca"), false);

		String[] abWords1 = DataHelper.genFixedSizeStrArr(100_000, 4, 4, "ab");
		String[] abWords2 = DataHelper.genFixedSizeStrArr(100_000, 4, 4, "ab");
		for (int i = 0; i < abWords1.length; i++) {
			String s1 = abWords1[i];
			String s2 = abWords2[i];
			sol.runAllVersions(s1 + " vs " + s2,
					() -> sol.isRotation(s1, s2), null);
		}

	}
}
