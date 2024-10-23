package assist;

import java.util.HashSet;
import java.util.Set;

public class DataHelper {
    private DataHelper() {
    }

    public static int[] copyArray(int[] data) {
        if (data == null) {
            return null;
        }
        int[] copy = new int[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return copy;
    }

    public static String[] generateRandomWords(int fixedSize, int minLen, int maxLen, String wordChars) {
        return doGenWords(fixedSize, minLen, maxLen, wordChars, false);
    }

    public static String[] generateRandomUniqueWords(int fixedSize, int minLen, int maxLen, String wordChars) {
        return doGenWords(fixedSize, minLen, maxLen, wordChars, true);

    }

    private static String[] doGenWords(int fixedSize, int minLen, int maxLen, String wordChars, boolean unique) {
        if (minLen < 0 || maxLen < 0 || minLen > maxLen || wordChars == null || wordChars.isEmpty()) {
            throw new IllegalStateException("Invalid input on generating random words");
        }
        String[] ret = new String[fixedSize];
        Set<String> uniqChecker = null;
        if (unique) {
            uniqChecker = new HashSet<>();
            int maxUniq = 0;
            for (int i = minLen; i <= maxLen; i++) {
                maxUniq += (int) Math.pow(wordChars.length(), i);
            }
            if (fixedSize > maxUniq) {
                throw new IllegalStateException("Cannot generate unique words more than " + maxUniq);
            }
        }
        for (int i = 0; i < fixedSize; i++) {
            ret[i] = generateOneWord(minLen, maxLen, wordChars, uniqChecker);
        }
        return ret;
    }

    private static String generateOneWord(int minLen, int maxLen, String wordChars, Set<String> uniqChecker) {
        StringBuilder sb = new StringBuilder();
        int len = (int) (Math.random() * (maxLen - minLen + 1)) + minLen;
        for (int j = 0; j < len; j++) {
            int idx = (int) (Math.random() * wordChars.length());
            sb.append(wordChars.charAt(idx));
        }
        String ret = sb.toString();
        if (uniqChecker == null || !uniqChecker.contains(ret)) {
            if (uniqChecker != null) {
                uniqChecker.add(ret);
            }
            return ret;
        }
        return generateOneWord(minLen, maxLen, wordChars, uniqChecker);
    }

    public static int[] generateRandomUniqData(int maxSize, int min, int max) {
        if (max - min + 1 < maxSize) {
            throw new IllegalStateException("Cannot generate unique data more than " + (max - min + 1) + " with size " + maxSize);
        }
        return doGenerateRandomData(maxSize, min, max, new HashSet<Integer>());
    }

    public static int[] generateRandomData(int maxSize, int min, int max) {
        return doGenerateRandomData(maxSize, min, max, null);
    }

    public static int[] generateFixedSizeData(int size, int min, int max) {
        return doGenerateFixedSizeData(size, min, max, null);
    }

    public static int[] generateFixedSizeUniqData(int size, int min, int max) {
        return doGenerateFixedSizeData(size, min, max, new HashSet<>(size));
    }

    private static int[] doGenerateFixedSizeData(int size, int min, int max, HashSet<Integer> uniqChecker) {
        if (uniqChecker != null && max - min + 1 < size) {
            throw new IllegalStateException("Cannot generate unique random data more than "
                    + (max - min + 1) + " with size " + size);
        }
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            int tmp = (int) (Math.random() * (max - min + 1)) + min;
            if (uniqChecker != null) {
                while (uniqChecker.contains(tmp)) {
                    tmp = (int) (Math.random() * (max - min + 1)) + min;
                }
                uniqChecker.add(tmp);
            }
            data[i] = tmp;
        }
        return data;
    }

    private static int[] doGenerateRandomData(int maxSize, int min, int max, HashSet<Integer> uniqChecker) {
        int size = (int) (Math.random() * (maxSize + 1));
        if (uniqChecker != null && max - min + 1 < size) {
            return doGenerateRandomData(maxSize, min, max, uniqChecker);        // re-gen since caller already checks it has solution for maxSize
        }
        return doGenerateFixedSizeData(size, min, max, uniqChecker);
    }

    public static boolean isArrayEqual(int[] data1, int[] data2) {
        if (data1 == null || data2 == null) {
            return data1 == data2;
        }
        if (data1.length != data2.length) {
            return false;
        }
        for (int i = 0; i < data1.length; i++) {
            if (data1[i] != data2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAscending(int[] data) {
        return isSorted(data, true);
    }

    private static boolean isSorted(int[] data, boolean ascending) {
        if (data == null || data.length < 2) {
            return true;
        }
        for (int i = 1; i < data.length; i++) {
            if ((ascending && data[i - 1] > data[i]) || (!ascending && data[i - 1] < data[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDescending(int[] data) {
        return isSorted(data, false);
    }

    public static void printArray(int[] data) {
        if (data == null) {
            System.out.println("null");
            return;
        }
        if (data.length == 0) {
            System.out.println("[]");
            return;
        }
        System.out.print("[");
        for (int i = 0; i < data.length - 1; i++) {
            System.out.print(data[i] + ", ");
        }
        System.out.println(data[data.length - 1] + "]");
    }

    public static void printArray(String msg, int[] data) {
        System.out.print(msg);
        if (!msg.endsWith(":") && !msg.endsWith(" ") && !msg.endsWith("\t") && !msg.endsWith("\n")) {
            System.out.print(": ");
        }
        printArray(data);
    }

    public static void assertFalse(boolean condition, String msg) {
        if (condition) {
            throw new IllegalStateException(msg);
        }
    }

    public static void assertTrue(boolean condition, String msg) {
        if (!condition) {
            throw new IllegalStateException(msg);
        }
    }
}
