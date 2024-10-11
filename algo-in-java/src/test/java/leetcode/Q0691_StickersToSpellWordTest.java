package leetcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class Q0691_StickersToSpellWordTest {
    private Q0691_StickersToSpellWord_DP sol;

    @BeforeEach
    void setUp() {
        sol = new Q0691_StickersToSpellWord_DP();
    }

    @Test
    void testArrayEquals() {
        int[] a1 = new int[]{1, 2, 3};
        int[] a2 = new int[]{1, 2, 3};
        int[] a3 = new int[]{1, 3, 2};
        assertArrayEquals(a1, a2);
        assertNotEquals(a1, a2);
        assertNotEquals(a1, a3);
    }

    @Test
    void perfTestRemoveStrFromAnotherString() {
        Duration d1 = perfRun(1, 10_000);
        Duration d2 = perfRun(2, 10_000);
        Duration d3 = perfRun(3, 10_000);
        System.out.println("Duration 1: " + d1.toMillis());
        System.out.println("Duration 2: " + d2.toMillis());
        System.out.println("Duration 3: " + d3.toMillis());
        // use toCharArray is nearly 2 times faster than charAt, remove from LinkedList is the slowest one
//        Duration 1: 22
//        Duration 2: 37
//        Duration 3: 38
    }

    private Duration perfRun(int version, int times) {
        long start = System.nanoTime();
        for (int i = 0; i < times; i++) {
            if (version == 1) {
                removeV1("spring", "gasproper");
                removeV1("problem", "aoper");
                removeV1("pr", "spring");
                removeV1("abeflkajfalkjas", "zhmtoxyz");
            } else if (version == 2) {
                removeV2("spring", "gasproper");
                removeV2("problem", "aoper");
                removeV2("pr", "spring");
                removeV2("abeflkajfalkjas", "zhmtoxyz");
            } else {
                removeV3("spring", "gasproper");
                removeV3("problem", "aoper");
                removeV3("pr", "spring");
                removeV3("abeflkajfalkjas", "zhmtoxyz");
            }
            if (version == 2) {
                removeV2("spring", "gasproper");
                removeV2("problem", "aoper");
                removeV2("pr", "spring");
                removeV2("abeflkajfalkjas", "zhmtoxyz");
            } else if (version == 3) {
                removeV3("spring", "gasproper");
                removeV3("problem", "aoper");
                removeV3("pr", "spring");
                removeV3("abeflkajfalkjas", "zhmtoxyz");
            } else {
                removeV1("spring", "gasproper");
                removeV1("problem", "aoper");
                removeV1("pr", "spring");
                removeV1("abeflkajfalkjas", "zhmtoxyz");
            }
        }
        return Duration.ofNanos(System.nanoTime() - start);
    }

    @Test
    void accuracyTest() {
        verifyRemoves("gasproper", "spring", "aoper");
        verifyRemoves("aoper", "problem", "a");
        verifyRemoves("pr", "spring", "");
        verifyRemoves("abeflkajfalkjas", "zhmtoxyz", "abeflkajfalkjas");
    }

    private void verifyRemoves(String word, String sticker, String remains) {
        assertEquals(remains, removeV1(sticker, word));
        assertEquals(remains, removeV2(sticker, word));
        assertEquals(remains, removeV3(sticker, word));
    }

    String removeV1(String sticker, String word) {
        return sol.applySticker(sticker, word);
    }

    String removeV3(String sticker, String word) {
        char[] wArr = word.toCharArray();
        int wLen = wArr.length;
        char[] sArr = sticker.toCharArray();
        int sLen = sArr.length;
        LinkedList<Character> list = new LinkedList<>();
        for (char c: wArr) {
            list.add(c);
        }
        for (int i = 0; i < sLen; i++) {
            char ch1 = sArr[i];
            list.remove((Character) ch1);
        }
        if (list.isEmpty()) {
            return "";
        } else if (list.size() == wLen) {
            return word;
        } else {
            char[] ret = new char[list.size()];
            int idx = 0;
            for (char c: list) {
                ret[idx++] = c;
            }
            return new String(ret);
        }
    }

    String removeV2(String sticker, String word) {
        int wLen = word.length();
        int sLen = sticker.length();
        boolean[] removedIdx = new boolean[wLen];
        int removeCnt = 0;
        for (int i = 0; i < sLen; i++) {
            char ch1 = sticker.charAt(i);
            for (int j = 0; j < wLen; j++) {
                char ch2 = word.charAt(j);
                if (ch1 == ch2 && !removedIdx[j]) {
                    removedIdx[j] = true;
                    removeCnt++;
                    if (removeCnt == wLen) {
                        return "";
                    }
                    break;
                }
            }
        }
        if (removeCnt == 0) {
            return word;
        } else {
            StringBuilder ret = new StringBuilder(wLen - removeCnt);
            for (int i = 0; i < wLen; i++) {
                if (!removedIdx[i]) {
                    ret.append(word.charAt(i));
                }
            }
            return ret.toString();
        }
    }
}