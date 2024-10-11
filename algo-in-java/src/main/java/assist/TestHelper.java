package assist;

import java.time.Duration;
import java.time.LocalTime;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TestHelper {
    public static Duration test(int[][] listOfNums, Consumer<int[]> consumer) {
        long start = getAndPrintStartTime();
        for (int[] nums : listOfNums) {
            consumer.accept(nums);
        }
        return getDurationAndPrintEndTime(start);
    }

    private static long getAndPrintStartTime() {
        System.out.println(">>> Start at: " + LocalTime.now());
        return System.nanoTime();
    }

    private static Duration getDurationAndPrintEndTime(long start) {
        Duration ret = Duration.ofNanos(System.nanoTime() - start);
        System.out.println("<<< End at: " + LocalTime.now() + ", duration: " + ret);
        return ret;
    }

    public static <T> void checkRun(Supplier<T> resultProducer, Consumer<T> resultChecker) {
        T result = resultProducer.get();
        if (resultChecker != null) {
            resultChecker.accept(result);
        }
    }

    public static <T> Duration timeRunWithCheck(Supplier<T> resultProducer, Consumer<T> resultChecker) {
        long start = getAndPrintStartTime();
        T result = resultProducer.get();
        Duration ret = getDurationAndPrintEndTime(start);
        if (resultChecker != null) {
            resultChecker.accept(result);
        }
        return ret;
    }

    public static Duration timeRun(Runnable r) {
        return timeRunWithCheck(() -> {
            r.run();
            return null;
        }, null);
    }

    public static Duration repeatRun(int n, Runnable r) {
        long start = getAndPrintStartTime();
        for (int i = 0; i < n; i++) {
            r.run();
        }
        return getDurationAndPrintEndTime(start);
    }

    public static <T> Duration repeatRunWithResultCheck(int n, Supplier<T> resultProducer, BiConsumer<T, Integer> resultChecker) {
        long start = getAndPrintStartTime();
        for (int i = 0; i < n; i++) {
            T result = resultProducer.get();
            resultChecker.accept(result, i);
        }
        return getDurationAndPrintEndTime(start);
    }
}
