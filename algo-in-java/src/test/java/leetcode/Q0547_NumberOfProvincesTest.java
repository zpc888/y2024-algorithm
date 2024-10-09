package leetcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Q0547_NumberOfProvincesTest {
    private Q0547_NumberOfProvinces solution;

    @BeforeEach
    void setUp() {
        solution = new Q0547_NumberOfProvinces();
    }

    @Test
    void findCircleNum_Input1() {
        int actual1 = solution.findCircleNum( new int[][] {
                {1, 0, 0, 1},
                {0, 1, 1, 0},
                {0, 1, 1, 1},
                {1, 0, 1, 1}
        });
        assertEquals(1, actual1);
    }

    @Test
    void findCircleNum_Input2() {
        int actual1 = solution.findCircleNum( new int[][] {
                {1, 1, 0},
                {1, 1, 0},
                {0, 0, 1}
        });
        assertEquals(2, actual1);
    }

    @Test
    void findCircleNum_Input3() {
        int actual1 = solution.findCircleNum( new int[][] {
                {1, 1, 0},
                {1, 1, 1},
                {0, 1, 1}
        });
        assertEquals(1, actual1);
    }

    @Test
    void findCircleNum_Input4() {
        int actual1 = solution.findCircleNum( new int[][] {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
        assertEquals(3, actual1);
    }
}