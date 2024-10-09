package assist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicTest {
    @Test
    void testUnicode() {
        String s = "Aa, \u4E16\u754C";
        System.out.println(s);
        System.out.println(s.length());
        char[] charArray = s.toCharArray();
        for (char c : charArray) {
            System.out.println(c + " - " + (int) c);
        }
        assertEquals(6, s.length());
        assertEquals(6, charArray.length);
        assertEquals('A', charArray[0]);
        assertEquals(65, (int) charArray[0]);
        assertEquals('a', charArray[1]);
        assertEquals(97, (int) charArray[1]);
        assertEquals(',', charArray[2]);
        assertEquals(44, (int) charArray[2]);
        assertEquals(' ', charArray[3]);
        assertEquals(32, (int) charArray[3]);
        assertEquals('世', charArray[4]);
        assertEquals(19990, (int) charArray[4]);
        assertEquals('界', charArray[5]);
        assertEquals(30028, (int) charArray[5]);
    }
}
