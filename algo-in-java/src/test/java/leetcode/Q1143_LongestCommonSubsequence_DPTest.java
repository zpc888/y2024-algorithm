package leetcode;

import assist.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class Q1143_LongestCommonSubsequence_DPTest {
    private Q1143_LongestCommonSubsequence_DP sol;
    private String text1 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaa";
    private String text2 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    @BeforeEach
    void setUp() {
        sol = new Q1143_LongestCommonSubsequence_DP();
    }

    @Test
    void withLeetCodeExample1() {
        verify(3, "abcde", "ace"
                , "The longest common subsequence is \"ace\" and its length is 3");
    }

    @Test
    void withLeetCodeExample2() {
        verify(3, "abc", "abc"
                , "The longest common subsequence is \"abc\" and its length is 3");
    }

    @Test
    void withLeetCodeExample3() {
        verify(0, "abc", "def"
                , "There is no such common subsequence, so the result is 0");
    }

    @Disabled("Time limit exceeded if no DP")
    @Test
    void testTimeLimitExceededNoDP() {
        Duration d = TestHelper.timeRun(() -> sol.processV1_noDP(text1.toCharArray(), text2.toCharArray()
                , text1.length() - 1, text2.length() - 1));
        System.out.println("Duration: " + d.toSeconds());
    }

    @Test
    void testTimeLimitExceededNoDP_InDP() {
        Duration d = TestHelper.timeRun(() -> sol.processV2_DP(text1.toCharArray(), text2.toCharArray()));
        System.out.println("Duration: " + d.toMillis());    // DP takes 6 ms; NO DP takes over 30 minutes (still not finished and was killed by me)
    }

    void verify(int expected, String text1, String text2, String message) {
        assertEquals(expected, sol.longestCommonSubsequence(text1, text2), message);
    }

}