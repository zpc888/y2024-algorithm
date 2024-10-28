package algo.str.manacher;


import assist.BaseSolution;
import assist.DataHelper;

/**
 * Give a string, append as few characters as possible to make it a palindrome.
 */
public class MinAppendForWholePalindrome extends BaseSolution<Integer> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 2;
    }

    public int minAppendForWholePalindrome(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }
        if (versionToRun == 1) {
            return processV1(s);
		} else if (versionToRun == 2) {
            return processV2(s);
        } else {
            throw new RuntimeException("No such version: " + versionToRun);
        }
    }

	// O(N ^ 2)
    private int processV1(String s) {
        char[] newS = interweave(s, '|');
        int len = newS.length;
        for (int center = 0; center < len; center++) {
            // based on newS[i], go to both side to check
            int radius = 0;
            while (eq(newS, center, radius)) {
                radius++;
            }
			if (center + radius == len) {		// including last char
				return (center - radius + 1) / 2;
			}
        }
		throw new IllegalStateException("Impossible to reach here");
    }

	// O(N)
	private int processV2(String s) {
        char[] newS = interweave(s, '|');
        int len = newS.length;
		int[] radiuses = new int[len];
		int radius = 0;
        for (int center = 0; center < len; center++) {
            while (eq(newS, center, radius)) {
                radius++;
            }
			radiuses[center] = radius;
			if (center + radius == len) {		// including last char
                return (center - radius + 1) / 2;
			}
            int oldCenter = center;
            int oldRadius = radius;
            radius = 0;
			while (center + 1 < oldCenter + oldRadius) {
                center++;
				int mirroredI = oldCenter - (center - oldCenter);
				int mirroredRadius = radiuses[mirroredI];
				if (center + mirroredRadius < oldCenter + oldRadius - 1) {
					radiuses[center] = mirroredRadius;
				} else if ( center + mirroredRadius > oldCenter + oldRadius) {
					radiuses[center] = oldCenter + oldRadius - center;
				} else {	// boundary overlapped and need to check and expand
					radius = oldCenter + oldRadius - center;
					center--;		// compensate center++;
                    break;
				}
			}
		}
		throw new IllegalStateException("Impossible to reach here");
	}

    // to solve even palindrome issue
    private char[] interweave(String s, char insert) {
        char[] oldS = s.toCharArray();
        int len = oldS.length;
        char[] newS = new char[len * 2 + 1];
        newS[0] = insert;
        for (int i = 0; i < len; i++) {
            newS[i * 2 + 1] = oldS[i];
            newS[i * 2 + 2] = insert;
        }
        return newS;
    }

    private boolean eq(char[] s, int center, int radius) {
        int len = s.length;
        if (center - radius < 0 || center + radius >= len) {
            return false;
        }
        return s[center - radius] == s[center + radius];
    }

    public static void main(String[] args) {
        MinAppendForWholePalindrome sol = new MinAppendForWholePalindrome();
        sol.runAllVersions("ab", () -> sol.minAppendForWholePalindrome("ab"), 1);
        sol.runAllVersions("abc", () -> sol.minAppendForWholePalindrome("abc"), 2);
        sol.runAllVersions("aba", () -> sol.minAppendForWholePalindrome("aba"), 0);
        sol.runAllVersions("abca", () -> sol.minAppendForWholePalindrome("abca"), 3);
        sol.runAllVersions("abbaba", () -> sol.minAppendForWholePalindrome("abbaba"), 3);
        String[] abcdeRandoms = DataHelper.genFixedSizeStrArr(100_000, 1, 100, "abcde");
        String[] abcRandoms = DataHelper.genFixedSizeStrArr(100_000, 1, 100, "abc");
        for (int i = 0; i < abcdeRandoms.length; i++) {
            String s1 = abcdeRandoms[i];
            String s2 = abcRandoms[i];
            sol.runAllVersions(s1, () -> sol.minAppendForWholePalindrome(s1), null);
            sol.runAllVersions(s2, () -> sol.minAppendForWholePalindrome(s2), null);
        }
        sol.performMeasure("100,000 Tests with Random: (abcde, abc)", () -> {
            for (int i = 0; i < abcdeRandoms.length; i++) {
                String s1 = abcdeRandoms[i];
                String s2 = abcRandoms[i];
                sol.minAppendForWholePalindrome(s1);
                sol.minAppendForWholePalindrome(s2);
            }
            return null;
        });
//====================== < 100,000 Tests with Random: (abcde, abc) > Performance Report ==============
//        Version-1: Duration: PT0.146881023S
//        Version-2: Duration: PT0.178145003S

        String a10K = DataHelper.genFixedSizeStrArr(1, 10_000, 10_000, "a")[0];
        sol.runAllVersions("10,000 a's", () -> sol.minAppendForWholePalindrome(a10K), 0);
        sol.performMeasure("10,000 a's", () -> sol.minAppendForWholePalindrome(a10K));
//====================== < 10,000 a's > Performance Report ==============
//        Version-1: Duration: PT0.048051422S
//        Version-2: Duration: PT0.00011124S

        sol.runAllVersions("10,000 a's then z", () -> sol.minAppendForWholePalindrome(a10K + 'z'), 10_000);
        sol.performMeasure("10,000 a's then z", () -> sol.minAppendForWholePalindrome(a10K + 'z'));
//        ====================== < 10,000 a's then z > Performance Report ==============
//        Version-1: Duration: PT0.096322832S
//        Version-2: Duration: PT0.000123824S
    }
}
