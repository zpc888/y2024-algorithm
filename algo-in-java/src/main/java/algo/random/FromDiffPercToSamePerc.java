package algo.random;

/**
 * f() function will return 0 or 1 with x% and (100 - x)% respectively.
 * Implement g() function which will return 0 or 1 with 50% probability, which can only use f() function.
 */
public class FromDiffPercToSamePerc {
    public static int f() {
        return Math.random() < 0.86 ? 0 : 1;
    }

    public static int g() {
        return g_01_wasteNot50PercentFCall();
        // 0->0  1->1 probability is not the same as 0->1 1->0
        // only 0->1 1->0 is the similar probability
//        return g_02_noWasteFCall_WRONG();
    }

    public static int g_02_noWasteFCall_WRONG() {
        int n1 = f();   // because 0 and 1 are not equally distributed
        int n2 = f();
        return n1 ^ n2;
    }

    // 0->0 1->1 will re-do, but their probability is not around 50%  it should x*x + (1-x)*(1-x) = 1 - 2x + 2x^2
    public static int g_01_wasteNot50PercentFCall() {
        int n1 = f();
        int n2 = f();
        if (n1 == 0 && n2 == 1) {
            return 0;
        } else if (n1 == 1 && n2 == 0) {
            return 1;
        } else {
            return g();
        }
    }

    public static void main(String[] args) {
        int limit = 1000000;
        int count = 0;
        for (int i = 0; i < limit; i++) {
            if (g() == 1) {
                count++;
            }
        }
        double percent = (double) count / limit * 100;
        System.out.println(percent + " produces 1 in g()");

        count = 0;
        for (int i = 0; i < limit; i++) {
            if (f() == 1) {
                count++;
            }
        }
        percent = (double) count / limit * 100;
        System.out.println(percent + " produces 1 in f()");
    }
}
