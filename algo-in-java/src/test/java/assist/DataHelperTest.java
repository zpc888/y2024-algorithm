package assist;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DataHelperTest {

    @Test
    void genRandomSizeIntArr() {
        for (int i = 0; i < 1000; i++) {
            int[] data = DataHelper.genRandomSizeIntArr(10, 1, 5);
            assertTrue(data.length <= 10);
            for (int d : data) {
                assertTrue(d >= 1 && d <= 5);
            }
        }
    }

    @Test
    void copyArray() {
        int[] data = {1, 2, 3, 4, 5};
        int[] copy = DataHelper.copyArray(data);
        assertNotSame(data, copy);
        assertArrayEquals(data, copy);
        assertTrue(DataHelper.isArrayEqual(data, copy));
    }

    @Test
    void isAscending() {
        assertTrue(DataHelper.isAscending(null));
        assertTrue(DataHelper.isAscending(new int[0]));
        assertTrue(DataHelper.isAscending(new int[]{1}));
        int[] data = {1, 2, 3, 4, 5};
        assertTrue(DataHelper.isAscending(data));
        data = new int[]{1, 2, 3, 5, 4};
        assertFalse(DataHelper.isAscending(data));
        data = new int[]{5, 4, 3, 2, 1};
        assertFalse(DataHelper.isAscending(data));
    }

    @Test
    void isDescending() {
        assertTrue(DataHelper.isDescending(null));
        assertTrue(DataHelper.isDescending(new int[0]));
        assertTrue(DataHelper.isDescending(new int[]{1}));
        int[] data = {1, 2, 3, 4, 5};
        assertFalse(DataHelper.isDescending(data));
        data = new int[]{1, 2, 3, 5, 4};
        assertFalse(DataHelper.isDescending(data));
        data = new int[]{5, 4, 3, 2, 1};
        assertTrue(DataHelper.isDescending(data));
    }

    @Test
    void genFixedSizeStrArr() {
        String[] words = DataHelper.genFixedSizeStrArr(10, 0, 8, "abcdefghijklmnopqrstuvwxyz");
        assertEquals(10, words.length);
        boolean isDirty = false;
        for (String word : words) {
            if (isDirty) {
                System.out.print(", ");
            } else {
                isDirty = true;
            }
            System.out.print("\"" + word + "\"");
            assertTrue(word.length() <= 8);
        }
        System.out.println();
    }

    @Test
    void genFixedSizeStrArrUniq() {
        String[] words = DataHelper.genFixedSizeStrArrUniq(10, 1, 10, "abcdefghijklmnopqrstuvwxyz");
        assertEquals(10, words.length);
        boolean isDirty = false;
        for (String word : words) {
            if (isDirty) {
                System.out.print(", ");
            } else {
                isDirty = true;
            }
            System.out.print("\"" + word + "\"");
            assertTrue(word.length() <= 10 && word.length() >= 1);
        }
        System.out.println();
    }

    @Test
    void generateFixedLenWords() {
        String[] words = DataHelper.genFixedSizeStrArrUniq(1_000_000, 10, 10, "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
        assertEquals(1_000_000, words.length);
        boolean isDirty = false;
        for (String word : words) {
            if (isDirty) {
                System.out.print(", ");
            } else {
                isDirty = true;
            }
            System.out.print("\"" + word + "\"");
            assertTrue(word.length() <= 10 && word.length() >= 1);
        }
        Random random = new Random();
        System.out.println();
        System.out.println("Testing to put 1M string to LocalDate into java map");
        Map<String, LocalDate> cache = new HashMap<>(1_000_000);
        for (String word : words) {
            LocalDate date = LocalDate.now();
            if (random.nextBoolean()) {
                date = date.minusDays( ((int) (2000 * random.nextDouble())) );
            } else {
                date = date.plusDays( ((int) (2000 * random.nextDouble())) );
            }
            cache.put(word, date);
        }
        System.out.println("Finished putting 1M string to LocalDate into java map size = " + cache.size());
    }
}