package algo.fib.olgn;

public class MatrixMath {
    private MatrixMath() {
    }

    public static int[][] power(int[][] matrix, int n) {
        int len = matrix.length;		// n * n
        int[][] ret = new int[len][len];
        // initialize ret to matrix = 1
        for (int i = 0; i < len; i++) {
            ret[i][i] = 1;
        }
        int[][] tmp = matrix;
        while (n != 0) {
            if ( (n & 1) == 1) {
                ret = multiply(ret, tmp);
            }
            tmp = multiply(tmp, tmp);
            n >>= 1;
        }
        return ret;
    }

    public static int[][] multiply(int[][] m1, int[][] m2) {
        int len = m1.length;
        int[][] ret = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                int tmp = 0;
                for (int k = 0; k < len; k++) {
                    tmp += m1[i][k] * m2[k][j];
                }
                ret[i][j] = tmp;
            }
        }
        return ret;
    }
}
