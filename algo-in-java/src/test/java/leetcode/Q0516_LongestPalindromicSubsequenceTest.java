package leetcode;

import assist.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Q0516_LongestPalindromicSubsequenceTest {
    private Q0516_LongestPalindromicSubsequence sol;

    private String timeLimitExceededStr = "euazbipzncptldueeuechubrcourfpftcebikrxhybkymimgvldiwqvkszfycvqyvtiwfckexmowcx" +
            "ztkfyzqovbtmzpxojfofbvwnncajvrvdbvjhcrameamcfmcoxryjukhpljwszknhiypvyskmsujkuggpztltpgocz" +
            "afmfelahqwjbhxtjmebnymdyxoeodqmvkxittxjnlltmoobsgzdfhismogqfpfhvqnxeuosjqqalvwhsidgiavcat" +
            "jjgeztrjuoixxxoznklcxolgpuktirmduxdywwlbikaqkqajzbsjvdgjcnbtfksqhquiwnwflkldgdrqrnwmshdpy" +
            "kicozfowmumzeuznolmgjlltypyufpzjpuvucmesnnrwppheizkapovoloneaxpfinaontwtdqsdvzmqlgkdxlbeg" +
            "uackbdkftzbnynmcejtwudocemcfnuzbttcoew";

    @BeforeEach
    void setUp() {
        sol = new Q0516_LongestPalindromicSubsequence();
    }

    private void runCheck(int expected, String input, String errMsg) {
        assertEquals(expected, sol.longestPalindromeSubseq(input), errMsg);
    }

    @Test
    void leetcodeTimeLimitExceededWithDP() {
        TestHelper.timeRun(() -> {
            int i = sol.longestPalindromeSubseq(timeLimitExceededStr);
            assertEquals(159, i);
        });
    }

    @Disabled("Time Limit Exceeded and killed by me after 30 minutes")
    @Test
    void leetcodeTimeLimitExceeded() {
        TestHelper.timeRun(() -> {
            int i = sol.processV1_noDP(timeLimitExceededStr.toCharArray(), 0, timeLimitExceededStr.length() - 1);
            assertEquals(159, i);
        });
    }

    @Test
    void leetcodeExample1() {
        runCheck(4, "bbbab", "One possible longest palindromic subsequence is \"bbbb\".");
    }

    @Test
    void leetcodeExample2() {
        runCheck(2, "cbbd", "One possible longest palindromic subsequence is \"bb\".");
    }

    @Test
    void test3() {
        runCheck(1, "a", "The longest palindromic subsequence is \"a\".");
    }

    @Test
    void test4() {
        assertEquals(1, sol.longestPalindromeSubseq("ab"));
    }

    @Test
    void test5() {
        assertEquals(1, sol.longestPalindromeSubseq("abc"));
    }

    @Test
    void test6() {
        assertEquals(3, sol.longestPalindromeSubseq("aba"));
    }

    @Test
    void test7() {
        assertEquals(3, sol.longestPalindromeSubseq("abca"));
    }

    @Test
    void test8() {
        assertEquals(3, sol.longestPalindromeSubseq("abcda"));
    }

    @Test
    void test9() {
        assertEquals(5, sol.longestPalindromeSubseq("abcdba"));
    }

    @Test
    void test10() {
        assertEquals(5, sol.longestPalindromeSubseq("abcdbca"));
    }

    @Test
    void test11() {
        assertEquals(7, sol.longestPalindromeSubseq("abcdbdca"));
    }

    @Test
    void test12() {
        assertEquals(9, sol.longestPalindromeSubseq("abcdbdcba"));
    }

    @Test
    void test13() {
        assertEquals(9, sol.longestPalindromeSubseq("abcdbdcbca"));
    }

    @Test
    void test14() {
        assertEquals(11, sol.longestPalindromeSubseq("abcdbdcbdcba"));
    }

    @Test
    void test15() {
        assertEquals(11, sol.longestPalindromeSubseq("abcdbdcbdcbaa"));
    }

    @Test
    void test16() {
        assertEquals(11, sol.longestPalindromeSubseq("abcdbdcbdcbaaa"));
    }

}