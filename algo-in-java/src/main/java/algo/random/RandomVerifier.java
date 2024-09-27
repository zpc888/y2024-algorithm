package algo.random;

public class RandomVerifier {
    public static void main(String[] args) {
        int limit = 1000000;
        double threshold = 0.6;
        thresholdPercentage(limit, threshold);
        thresholdSquarePercentage(limit, threshold);
        thresholdCubicPercentage(limit, threshold);
    }

    private static void thresholdPercentage(int limit, double threshold) {
        int count = 0;
        for (int i = 0; i < limit; i++) {
            if (Math.random() < threshold) {
                count++;
            }
        }
        double percent = (double) count / limit * 100;
        System.out.println( percent + " is less than " + threshold);
//        System.out.println( (100 - percent) + " is greater than " + threshold);
    }

    private static void thresholdSquarePercentage(int limit, double threshold) {
        int count = 0;
        for (int i = 0; i < limit; i++) {
            if (Math.max(Math.random(), Math.random()) < threshold) {
                // value: 0 --> threshold  0 --> threshold
                // percentage: threshold   threshold
                count++;
            }
        }
        double percent = (double) count / limit * 100;
        System.out.println( percent + " is less than " + threshold + " where square of threshold is " + threshold * threshold);
    }

    private static void thresholdCubicPercentage(int limit, double threshold) {
        int count = 0;
        for (int i = 0; i < limit; i++) {
            if (Math.max(Math.max(Math.random(), Math.random()), Math.random()) < threshold) {
                count++;
            }
        }
        double percent = (double) count / limit * 100;
        System.out.println( percent + " is less than " + threshold + " where cubic of threshold is " + threshold * threshold * threshold);
    }
}
