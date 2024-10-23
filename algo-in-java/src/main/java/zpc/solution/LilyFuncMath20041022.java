package zpc.solution;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;

/**
 * Grade 11 Math for vertical / horizontal stretch / compression, reflection, translation, and combinations.
 */
public class LilyFuncMath20041022 {
    private final LinkedHashMap<Double, Double> baseFuncPoints;

    public LilyFuncMath20041022() {
        this.baseFuncPoints = new LinkedHashMap<>(16);
        this.baseFuncPoints.put(14.5, 17d);
        this.baseFuncPoints.put(11d, 15d);
        this.baseFuncPoints.put(18d, 15d);
        this.baseFuncPoints.put(10.5, 10d);
        this.baseFuncPoints.put(18.5, 10d);
        this.baseFuncPoints.put(9.5, 7d);
        this.baseFuncPoints.put(19.5, 7d);
        this.baseFuncPoints.put(12.5, 4d);
        this.baseFuncPoints.put(16.5, 4d);
        this.baseFuncPoints.put(12d, 3d);
        this.baseFuncPoints.put(17d, 3d);
    }

    // y = -3/2*f(x - 3) + 6
    public LinkedHashMap<Double, Double> func1() {
        LinkedHashMap<Double, Double> ret = new LinkedHashMap<>(16);
        for (Double x: baseFuncPoints.keySet()) {
            double y = baseFuncPoints.get(x);
            ret.put(x + 3, -1.5 * y + 6);
        }
        return ret;
    }

    // y = f(-2/3 * (x - 1)) + 2
    public LinkedHashMap<Double, Double> func2() {
        LinkedHashMap<Double, Double> ret = new LinkedHashMap<>(16);
        for (Double x: baseFuncPoints.keySet()) {
            double y = baseFuncPoints.get(x);
            double newX = -1.5 * x + 1;
            ret.put(newX, y + 2);
        }
        return ret;
    }

    // y = -3/2 * f(-1/2 * (x - 15)) + 5
    public LinkedHashMap<Double, Double> func3() {
        LinkedHashMap<Double, Double> ret = new LinkedHashMap<>(16);
        for (Double x: baseFuncPoints.keySet()) {
            double y = baseFuncPoints.get(x);
            double newX = -2 * x + 15;
            ret.put(newX, -1.5 * y + 5);
        }
        return ret;
    }


    public static void main(String[] args) {
        LilyFuncMath20041022 sol = new LilyFuncMath20041022();
        System.out.println();
        showFuncPointsAndDomainRanges(sol.baseFuncPoints, "f(x)");
        showFuncPointsAndDomainRanges(sol.func1(), "-3/2*f(x - 3) + 6");
        showFuncPointsAndDomainRanges(sol.func2(), "f(-2/3 * (x - 1)) + 2");
        showFuncPointsAndDomainRanges(sol.func3(), "-3/2 * f(-1/2 * (x - 15)) + 5");
    }

    private static void showFuncPointsAndDomainRanges(LinkedHashMap<Double, Double> map, String title) {
        DecimalFormat df = new DecimalFormat("0.##");
        System.out.println("============== " + title + " ==============");
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        System.out.println("   x   |   y   ");
        System.out.println("-------|-------");
        for (Double x: map.keySet()) {
            double y = map.get(x);
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            System.out.printf("%6s | %6s%n", df.format(x), df.format(y));
        }
        System.out.println("-------|-------");
        System.out.println();
        System.out.printf("Domain: %s <= x <= %s%n", df.format(minX), df.format(maxX));
        System.out.printf("Range : %s <= y <= %s%n", df.format(minY), df.format(maxY));
        System.out.println();
    }


}
