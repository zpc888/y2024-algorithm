package algo.tree.ordered.skiplist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkipListTest {
    SkipList<Integer, Integer> skipList;

    @BeforeEach
    void setUp() {
        skipList = new SkipList(16);
    }

    @Test
    public void testAddQueryDelete() {
        assertTrue(skipList.add(1, 10));
        assertTrue(skipList.add(2, 20));
        assertEquals(2, skipList.size());
        assertEquals(20, skipList.getValue(2));
        assertFalse(skipList.add(2, 30));
        assertEquals(2, skipList.size());
        assertEquals(30, skipList.getValue(2));
        assertTrue(skipList.add(5, 50));
        assertEquals(3, skipList.size());

        assertEquals(2, skipList.getFloorKey(2));
        assertEquals(5, skipList.getAboveFloorKey(2));
        assertEquals(null, skipList.getAboveFloorKey(5));

        assertEquals(null, skipList.getBelowCeilingKey(1));
        assertEquals(1, skipList.getCeilingKey(1));
        assertEquals(1, skipList.getBelowCeilingKey(2));
        assertEquals(2, skipList.getBelowCeilingKey(5));
        assertEquals(5, skipList.getBelowCeilingKey(10));

        assertFalse(skipList.delete(3));
        assertEquals(3, skipList.size());
        assertTrue(skipList.delete(2));
        assertEquals(2, skipList.size());
        assertEquals(null, skipList.getValue(2));
        assertEquals(1, skipList.getBelowCeilingKey(5));
    }
}