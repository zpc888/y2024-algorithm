package leetcode;

import assist.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Q0688_KnightProbabilityInChessBoard_DPTest {
    private Q0688_KnightProbabilityInChessBoard_DP sol;

    @BeforeEach
    void setup() {
        sol = new Q0688_KnightProbabilityInChessBoard_DP();
    }

    @Test
    void leetcodeExample1() {
        assertEquals(0.0625, sol.knightProbability(3, 2, 0, 0));
    }

    @Test
    void leetcodeExample2() {
        assertEquals(1, sol.knightProbability(1, 0, 0, 0));
    }

    @Disabled("Time Limit Exceeded and killed by me after 30+ minutes")
    @Test
    void leetcodeTimeLimitExceeded_Without_DP() {
        TestHelper.timeRun(() -> {
            double paths = sol.process_V1_noDP(8, 30, 6, 4);
            System.out.println("Paths: " + paths);
        });
    }

    @Test
    void leetcodeTimeLimitExceeded_With_DP() {
        TestHelper.timeRun(() -> {
            double paths = sol.process_V2_DP(8, 30, 6, 4);
            System.out.println("Paths: " + paths);

            double probability = sol.knightProbability(8, 30, 6, 4);
            System.out.println("Probability: " + probability);
        });
    }

}