package algo.manacher;

import assist.BaseSolution;
import assist.DataHelper;

/**
 * source: https://en.wikipedia.org/wiki/Longest_palindromic_substring
 * <p/>
 * The Manacher's algorithm is an algorithm to find the longest palindromic 
 * substring in a linear time complexity O(n).
 * <p/>
 * This solution is to find the length of the longest palindromic substring 
 * in a given string.
 */
public class LongestPalindrome extends BaseSolution<Integer> {
    @Override
    protected int getNumberOfVersionsImplemented() {
        return 4;
    }

    public int longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (versionToRun == 1) {
            return doBruteForceV1(s);
        } else if (versionToRun == 2) {
            return doManacherV2(s);
		} else if (versionToRun == 3) {
			return doManacherV3(s);
		} else if (versionToRun == 4) {
			return doManacherV4(s);
        } else {
            throw new RuntimeException("No such version: " + versionToRun);
        }
    }
	
	// O(n * n/2) = O(N^2)
	private int doBruteForceV1(String s) {
		char[] newS = interweave(s, '|');
		int len = newS.length;
		int max = 0;
		for (int center = 0; center < len; center++) {
			// based on newS[i], go to both side to check
			int radius = 0;
			while (eq(newS, center, radius)) {
				radius++;
			}
			max = Math.max(max, radius);
		}
		return max;
	}

	// to solve even palindrome issue
	private char[] interweave(String s, char insert) {
		char[] oldS = s.toCharArray();
		int len = oldS.length;
		char[] newS = new char[len * 2 + 1];
		newS[0] = insert;
		for (int i = 0; i < len; i++) {
			newS[2*i + 1] = oldS[i];
			newS[2*i + 2] = insert;
		}
		return newS;
	}

	private boolean eq(char[] s, int center, int radius) {
		return center - radius - 1 >= 0
			&& center + radius + 1 < s.length
			&& s[center - radius - 1] == s[center + radius + 1];
	}

	private int doManacherV2(String s) {
		char[] newS = interweave(s, '|');
		int len = newS.length;
		// hold radius for the i-th char as center
		int[] radiuses = new int[len];
		int max = 0;
		int rightMostIdx = -1;	// the most right boundary index
		int rightMostCenter = -1;	// the center index to support the above
		for (int center = 0; center < len; center++) {
			if (center < rightMostIdx) {
				int leftMostIdx = rightMostCenter - (rightMostIdx - rightMostCenter);
				int mirrorCenter = rightMostCenter - (center - rightMostCenter);
				// mirrorParlindrome length
				int mirrorPLen = radiuses[mirrorCenter];
				int mirrorLeftMostIdx = mirrorCenter - mirrorPLen;
				if (mirrorLeftMostIdx > leftMostIdx) {
					// option 1: within a longer palindrome
					radiuses[center] = mirrorPLen;
				} else if (mirrorLeftMostIdx < leftMostIdx) {
					// option 2: cross the boundary of the longer palindrome
					// mirror center and right most center ones overlapped
					radiuses[center] = mirrorCenter - leftMostIdx;
				} else {
					// option 3: on the boundary
					// at least mirrorPLen, i.e. >= mirrorPLen
					int radius = mirrorPLen;
					while (eq(newS, center, radius)) {
						radius++;
					}
					radiuses[center] = radius;
					if (radius > mirrorPLen) {
						// update rightMostIdx and rightMostCenter
						rightMostCenter = center;
						rightMostIdx = center + radius;
						max = Math.max(max, radius);
					}
				}
			} else if (center == rightMostIdx) {	// can be treated as above
				int mirrorCenter = rightMostCenter - (rightMostIdx - rightMostCenter);
				if (radiuses[mirrorCenter] > 0) {
					radiuses[center] = 0;
				} else {	// like option 3 of the above with mirrorPLen = 0
					int radius = 0;
					while (eq(newS, center, radius)) {
						radius++;
					}
					radiuses[center] = radius;
					if (radius > 0) {
						rightMostCenter = center;
						rightMostIdx = center + radius;
						max = Math.max(max, radius);
					}
				}
			} else {	// out of boundary, never being in any palindrome before
				int radius = 0;
				while (eq(newS, center, radius)) {
					radius++;
				}
				radiuses[center] = radius;
				if (radius > 0) {
					rightMostCenter = center;
					rightMostIdx = center + radius;
					max = Math.max(max, radius);
				}
			}
		}
		return max;
	}

	private int doManacherV3(String s) {
		char[] newS = interweave(s, '|');
		int len = newS.length;
		// hold radius for the i-th char as center
		int[] radiuses = new int[len];
		int max = 0;
		int rightMostIdx = -1;	// the most right boundary index
		int rightMostCenter = -1;	// the center index to support the above
		for (int center = 0; center < len; center++) {
			int baseRadius = 0;
			if (center <= rightMostIdx) {
				int leftMostIdx = rightMostCenter - (rightMostIdx - rightMostCenter);
				int mirrorCenter = rightMostCenter - (center - rightMostCenter);
				// mirrorParlindrome length
				int mirrorPLen = radiuses[mirrorCenter];
				int mirrorLeftMostIdx = mirrorCenter - mirrorPLen;
				if (mirrorLeftMostIdx > leftMostIdx) {
					// option 1: within a longer palindrome
					radiuses[center] = mirrorPLen;
					continue;
				} else if (mirrorLeftMostIdx < leftMostIdx) {
					// option 2: cross the boundary of the longer palindrome
					// mirror center and right most center ones overlapped
					radiuses[center] = mirrorCenter - leftMostIdx;
					continue;
				} else {
					// option 3: on the boundary
					// at least mirrorPLen, i.e. >= mirrorPLen
					baseRadius = mirrorPLen;
				}
			}
			int radius = baseRadius;
			while (eq(newS, center, radius)) {
				radius++;
			}
			radiuses[center] = radius;
			if (radius > baseRadius) {
				// update rightMostIdx and rightMostCenter
				rightMostCenter = center;
				rightMostIdx = center + radius;
				max = Math.max(max, radius);
			}
		}
		return max;
	}

	// from wiki pseudo code
	private int doManacherV4(String s) {
		char[] newS = interweave(s, '|');
		int len = newS.length;
		int[] palindromeRadii = new int[len];
		int farMostRadius = -1, farMostCenter = -1;
		int center = 0, radius = 0;
		int longest = 0;
		while (center < len) {
			while (eq(newS, center, radius)) {
				radius++;
			}
			palindromeRadii[center] = radius;
			farMostRadius = radius;
			longest = Math.max(longest, radius);
			// while if-elseif radius will be smaller than there
			farMostCenter = center;
			center++;
			radius = 0; // reset radius unless it is on the boundary below
			while (center <= farMostCenter + farMostRadius) {
				int mirroredCenter = farMostCenter - (center - farMostCenter);
				int maxMirroredRadius = farMostCenter + farMostRadius - center;
				int mirroredRadius = palindromeRadii[mirroredCenter];
				if (mirroredRadius < maxMirroredRadius) {
					palindromeRadii[center] = mirroredRadius;
					center++;
				} else if (mirroredRadius > maxMirroredRadius) {
					palindromeRadii[center] = maxMirroredRadius;
					center++;
				} else {// mirrored radius and far-most radius shared the boundary
					radius = maxMirroredRadius;
					break;		// jump to while (eq(newS, center, radius)) line
				}
			}
		}
		return longest;
	}

	public static void main(String[] args) {
		LongestPalindrome lp = new LongestPalindrome();
		lp.runAllVersions("deaeeedaebcbcebb", () -> lp.longestPalindrome("deaeeedaebcbcebb"), 3);
		lp.runAllVersions("cbedaaabacbbcbceecaadeaeeedaebcbcebb", () -> lp.longestPalindrome("cbedaaabacbbcbceecaadeaeeedaebcbcebb"), 4);
		lp.runAllVersions("abcde", () -> lp.longestPalindrome("abcde"), 1);
		lp.runAllVersions("abac", () -> lp.longestPalindrome("abac"), 3);
		lp.runAllVersions("abbac", () -> lp.longestPalindrome("abbac"), 4);
		String[] abcdeRandoms = DataHelper.genFixedSizeStrArr(100_000, 1, 100, "abcde");
		String[] abcRandoms = DataHelper.genFixedSizeStrArr(100_000, 1, 100, "abc");
		for (int i = 0; i < abcdeRandoms.length; i++) {
			String s1 = abcdeRandoms[i];
			String s2 = abcRandoms[i];
			lp.runAllVersions(s1, () -> lp.longestPalindrome(s1), null);
			lp.runAllVersions(s2, () -> lp.longestPalindrome(s2), null);
		}
		lp.performMeasure("100,000 Tests with Random: (abcde, abc)", () -> {
			for (int i = 0; i < abcdeRandoms.length; i++) {
				String s1 = abcdeRandoms[i];
				String s2 = abcRandoms[i];
				lp.longestPalindrome(s1);
				lp.longestPalindrome(s2);
			}
			return null;
		});
//====================== < 100,000 Tests with Random: (abcde, abc) > Performance Report ==============
//		Version-1: Duration: PT0.119165873S
//		Version-2: Duration: PT0.155195333S
//		Version-3: Duration: PT0.142093703S
//		Version-4: Duration: PT0.124151522S

		String a10K = DataHelper.genFixedSizeStrArr(1, 10_000, 10_000, "a")[0];
		lp.runAllVersions("10,000 a's", () -> lp.longestPalindrome(a10K), 10_000);
		lp.performMeasure("10,000 a's", () -> lp.longestPalindrome(a10K));
//====================== < 10,000 a's > Performance Report ==============
//		Version-1: Duration: PT0.133435743S
//		Version-2: Duration: PT0.000082736S	    Manacher algorithm is 1000 times faster than brute force for 10k dup-a's
//		Version-3: Duration: PT0.000076635S
//		Version-4: Duration: PT0.00009536S
	}

}
