package leetcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Q0085_MaximalRectangle_MonotoneStackTest {
    Q0085_MaximalRectangle_MonotoneStack sol;

    @BeforeEach
    void setUp() {
        sol = new Q0085_MaximalRectangle_MonotoneStack();
    }

    @Test
    void leetcodeCase1() {
        char[][] matrix = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };
        assertEquals(6, sol.maximalRectangle(matrix));
    }

    @Test
    void leetcodeCase2() {
        char[][] matrix = {
                {'0'}
        };
        assertEquals(0, sol.maximalRectangle(matrix));
    }

    @Test
    void leetcodeCase3() {
        char[][] matrix = {
                {'1'}
        };
        assertEquals(1, sol.maximalRectangle(matrix));
    }
}