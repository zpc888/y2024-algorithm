package algo.bitwise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MathOpTest {

    @Test
    void testAdd01() {
        assertEquals(5, MathOp.add(2, 3));
        assertEquals(5, MathOp.add(3, 2));
        assertEquals(518, MathOp.add(211, 307));
        assertEquals(528, MathOp.add(319, 209));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "92, 29",
            "39, 74",
            "211, 307",
            "319, 209"
    })
    void testAdd02(int a, int b) {
        System.out.println("a = " + a + ", b = " + b);
        final int expected = a + b;
        assertEquals(expected, MathOp.add(a, b));
    }

    @Test
    void testNegate01() {
        assertEquals(-5, MathOp.negate(5));
        assertEquals(5, MathOp.negate(-5));
        assertEquals(0, MathOp.negate(0));
    }

    @Test
    void substract01() {
        assertEquals(5, MathOp.substract(8, 3));
        assertEquals(3, MathOp.substract(8, 5));
        assertEquals(0, MathOp.substract(8, 8));
    }

    @Test
    void multiply01() {
        assertEquals(15, MathOp.multiply(3, 5));
        assertEquals(15, MathOp.multiply(5, 3));
        assertEquals(15, MathOp.multiply(-5, -3));
        assertEquals(-15, MathOp.multiply(-5, 3));
        assertEquals(-15, MathOp.multiply(5, -3));
        assertEquals(0, MathOp.multiply(0, 5));
        assertEquals(0, MathOp.multiply(5, 0));
        assertEquals(0, MathOp.multiply(0, 0));
    }

    @Test
    void divide01() {
        System.out.println(Integer.MIN_VALUE);          // -2147483648
        System.out.println(Integer.MIN_VALUE / -1);     // -2147483648   not right, it should be positive, but overflow
    }

    @Test
    void divide02() {
        assertEquals(3, MathOp.divide(10, 3));
        assertEquals(1, MathOp.divide(Integer.MIN_VALUE, Integer.MIN_VALUE));
        assertEquals(-1, MathOp.divide(Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(0, MathOp.divide(Integer.MAX_VALUE, Integer.MIN_VALUE));
        assertEquals(-3, MathOp.divide(-10, 3));
        assertEquals(-3, MathOp.divide(10, -3));
        assertEquals(3, MathOp.divide(-10, -3));
    }

//    @Test
    void divide03() {
        for (int i = Integer.MIN_VALUE; i <= Integer.MAX_VALUE; i++) {
            for (int j = Integer.MIN_VALUE; j <= Integer.MAX_VALUE; j++) {
                if (j == 0) {
                    try {
                        MathOp.divide(i, j);
                        fail("Should have thrown ArithmeticException");
                    } catch (ArithmeticException e) {
                        continue;
                    }
                }
                if ( i == Integer.MIN_VALUE && j == -1) {
                    assertEquals(Integer.MAX_VALUE, MathOp.divide(i, j));
                    continue;
                }
                final int expected = i / j;
                assertEquals(expected, MathOp.divide(i, j));
                if (j == Integer.MAX_VALUE) {
                    break;
                }
            }
            System.out.println("i = " + i);
            if (i == Integer.MAX_VALUE) {
                break;
            }
        }
    }
}