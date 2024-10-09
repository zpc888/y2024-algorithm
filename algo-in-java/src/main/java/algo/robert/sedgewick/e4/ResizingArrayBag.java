package algo.robert.sedgewick.e4;

import java.util.Iterator;

public class ResizingArrayBag<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] items;
    private int n;

    public ResizingArrayBag() {
        items = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public ResizingArrayBag add(Item item) {
        if (n == items.length) {
            resize(2 * items.length);
        }
        items[n++] = item;
        return this;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, copy, 0, n);
        items = copy;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < n;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return items[i++];
        }
    }
}
