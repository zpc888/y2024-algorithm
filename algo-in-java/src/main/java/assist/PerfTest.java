package assist;

import java.time.Duration;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PerfTest {
    public static Duration test(int[][] listOfNums, Consumer<int[]> consumer) {
        long start = System.nanoTime();
        for (int[] nums : listOfNums) {
            consumer.accept(nums);
        }
        return Duration.ofNanos(System.nanoTime() - start);
    }

    public static Duration repeatRun(int n, Runnable r) {
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            r.run();
        }
        return Duration.ofNanos(System.nanoTime() - start);
    }

    public static <T> Duration repeatRunWithResultCheck(int n, Supplier<T> resultProducer, BiConsumer<T, Integer> resultChecker) {
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            T result = resultProducer.get();
            resultChecker.accept(result, i);
        }
        return Duration.ofNanos(System.nanoTime() - start);
    }
}
