package assist;

import java.time.Duration;
import java.util.*;
import java.util.function.Supplier;

public abstract class BaseSolution<R> {
    protected static boolean silent = false;
    protected int versionToRun = 1;

    protected abstract int getNumberOfVersionsImplemented();

//    public void verifyWithHighVolumes() {
////		silent = true;
//        Random random = new Random();
//        for (int run = 0; run < 30; run++) {
//            runAllVersions(random.nextInt(10), -1);
//        }
//        for (int run = 0; run < 10; run++) {
//            runAllVersionsExcludes(random.nextInt(16),
//                    -1, 1);
//        }
//        performMeasure();
//
//    }

    public void performMeasure(String title, Supplier<R> run, int... excludeVersions) {
        Set<Integer> excludeSet = new HashSet<>();
        if (excludeVersions != null) {
            for (int exclude : excludeVersions) {
                excludeSet.add(exclude);
            }
        }
        System.out.println();
        System.out.printf("============== Measure performance %s ===================%n", title);
        LinkedHashMap<Integer, Duration> times = new LinkedHashMap<>();
        for (int i = 0; i < getNumberOfVersionsImplemented(); i++) {
            this.versionToRun = i + 1;
            if (excludeSet.contains(this.versionToRun)) {
                continue;
            }
            Duration dur = TestHelper.timeRun(() -> {
                R r = run.get();
                System.out.printf("Version-%s: run result: %s%n", versionToRun, resultStringifier(r));
            });
            System.out.println("\tDuration: " + dur.toMillis() );
            System.out.println();
            times.put(this.versionToRun, dur);
        }
        System.out.println("================================ performance Report ==========================");
        for (Integer version : times.keySet()) {
            Duration dur = times.get(version);
            System.out.printf("\tVersion-%s: Duration: %s%n", version, dur);
        }
        System.out.println();
    }

    public R runAllVersions(String title, Supplier<R> run, R expected) {
        return runAllVersionsExcludes(title, run, expected, new int[0]);
    }

    protected String resultStringifier(R result) {
        return result == null ? null : result.toString();
    }

    protected boolean resultEquals(R expected, R actual) {
        return expected == actual || (expected != null && expected.equals(actual));
    }

    public R runAllVersionsExcludes(String title, Supplier<R> run, R expected, int... excludeVersions) {
        if (!silent) {
            System.out.println();
            System.out.printf("===================== %s ===============%n", title);
            if (expected != null) {
                System.out.println("\t\tExpected Result: " + resultStringifier(expected));
            }
        }
        Set<Integer> excludeSet = new HashSet<>();
        if (excludeVersions != null) {
            for (int exclude : excludeVersions) {
                excludeSet.add(exclude);
            }
        }
        LinkedHashMap<Integer, R> actuals = new LinkedHashMap<>();
        for (int i = 0; i < getNumberOfVersionsImplemented(); i++) {
            this.versionToRun = i + 1;
            if (excludeSet.contains(this.versionToRun)) {
                continue;
            }
            R actual = run.get();
            actuals.put(this.versionToRun, actual);
            if (!silent) {
                System.out.printf("Version: %d, actual result: %s\n", this.versionToRun, resultStringifier(actual));
            }
        }
        if (expected == null) {
            expected = actuals.entrySet().iterator().next().getValue();
        }
        for (Integer version : actuals.keySet()) {
            R actual = actuals.get(version);
            if (!resultEquals(expected, actual)) {
                throw new IllegalStateException("Expected: " + expected + ", but V" + version + " got: " + actual);
            }
        }
        return expected;
    }
}
