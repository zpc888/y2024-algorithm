package algo.random;

/**
 * f() function provides the similar percentage to produce 1-5 integer, please use f() only to implement g() function
 * which provides the similar percentage to produce 1-7 integer.
 */
public class Map1_5To1_7 {
    public int f1_5() {
        return (int) (Math.random() * 5) + 1;
    }

    public int g1_7() {
//        return g01();
//        return g02();
        return g03_binary();
    }

    private int g03_binary() {
        return to3Bits();
    }

    private int to3Bits() {
        int ret = (toBinary() << 2) + (toBinary() << 1) + toBinary();
        if (ret == 0) {
            return to3Bits();
        } else {
            if (ret > 7) {
                throw new IllegalStateException("Invalid value: " + ret);
            }
            return ret;
        }
    }

    private int toBinary() {
        while (true) {
            int n = f1_5();
            if (n == 1 || n == 2) {
                return 0;
            } else if (n == 3 || n == 4) {
                return 1;
            }
        }
    }

    private int g02() {
        while (true) {
            int n1 = f1_5();
            int n2 = f1_5();
            if (n1 == 1 || n1 == 2) {
                if (n2 == 1) {
                    return 1;
                } else if (n2 == 2) {
                    return 2;
                } else if (n2 == 3) {
                    return 3;
                } else if (n2 == 4) {
                    return 4;
                } else if (n2 == 5) {
                    return 5;
                }
            } else if (n1 == 4 || n1 == 5) {
                if (n2 == 1) {
                    return 6;
                } else if (n2 == 2) {
                    return 7;
                }
            }
        }
    }

    private int g01() {
       while (true) {
           int n1 = f1_5();
           int n2 = f1_5();
           if (n1 == 1) {
               if (n2 == 1) {
                   return 1;
               } else if (n2 == 2) {
                   return 2;
               } else if (n2 == 3) {
                   return 3;
               } else if (n2 == 4) {
                   return 4;
               } else if (n2 == 5) {
                   return 5;
               }
           } else if (n1 == 2) {
               if (n2 == 1) {
                   return 6;
               } else if (n2 == 2) {
                   return 7;
               }
           }
       }
    }

    public static void main(String[] args) {
        int limit = 1000000;
        int[] counts1_5 = new int[5];
        int[] counts1_7 = new int[7];
        Map1_5To1_7 obj = new Map1_5To1_7();
        for (int i = 0; i < limit; i++) {
            int n1 = obj.f1_5();
            counts1_5[n1 - 1]++;
        }
        for (int i = 0; i < limit; i++) {
            int n1 = obj.g1_7();
            counts1_7[n1 - 1]++;
        }
        for (int i = 0; i < counts1_5.length; i++) {
            System.out.println("1-5: " + (i + 1) + " appears " + counts1_5[i] + " percentage: " + (double) counts1_5[i] / limit * 100);
        }
        for (int i = 0; i < counts1_7.length; i++) {
            System.out.println("1-7: " + (i + 1) + " appears " + counts1_7[i] + " percentage: " + (double) counts1_7[i] / limit * 100);
        }
    }

}
