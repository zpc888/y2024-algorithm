package assist;

import java.time.Duration;
import java.util.function.Consumer;

public class PerfTest {
    public static Duration test(int[][] listOfNums, Consumer<int[]> consumer) {
        long start = System.nanoTime();
        for (int[] nums : listOfNums) {
            consumer.accept(nums);
        }
        return Duration.ofNanos(System.nanoTime() - start);
    }
}
