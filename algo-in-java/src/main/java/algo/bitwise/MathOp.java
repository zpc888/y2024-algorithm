package algo.bitwise;

public class MathOp {
    public static int add(int a, int b) {
        int sum = a;
        while (b != 0) {
            sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }
        return sum;
    }

    public static int substract(int a, int b) {
        return add(a, negate(b));
    }

    public static int negate(int a) {
        return add(~a, 1);
    }

    public static int multiply(int a, int b) {
        int result = 0;
        while (b != 0) {
            if ((b & 1) != 0) {
                result = add(result, a);
            }
            a <<= 1;
            b >>>= 1;
        }
        return result;
    }

    private static int doDivide(int a, int b) {
        int x = isNeg(a) ? negate(a) : a;
        int y = isNeg(b) ? negate(b) : b;
        int result = 0;
        for (int i = 30; i >= 0; i = substract(i, 1)) {
            if ((x >> i) >= y) {
                result |= 1 << i;
                x = substract(x, y << i);
            }
        }
        return isNeg(a) ^ isNeg(b) ? negate(result) : result;
    }

    public static int divide(int a, int b) {
        if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) {
            return 1;
        }
        if (b == Integer.MIN_VALUE) {
            return 0;
        }
        if (a == Integer.MIN_VALUE) {
            if (b == negate(1)) {
                return Integer.MAX_VALUE;
            }
            int result = doDivide(add(a, 1), b);
            return add(result, doDivide(substract(a, multiply(result, b)), b));
        }
        if (b == 0) {
            throw new ArithmeticException("Divisor cannot be 0");
        }
        return doDivide(a, b);
    }

    public static boolean isNeg(int a) {
        return a < 0;
    }
}
