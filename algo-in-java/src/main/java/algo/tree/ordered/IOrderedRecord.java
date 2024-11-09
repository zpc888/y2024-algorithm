package algo.tree.ordered;

public interface IOrderedRecord<K extends Comparable<K>, V> {
    boolean add(K key, V value);
    boolean delete(K key);
    V getValue(K key);
    int size();

    /**
     * Returns the smallest key greater than or equal to the given key, or null if there is no such key.
     */
    K getFloorKey(K key);
    /**
     * Returns the greatest key less than or equal to the given key, or null if there is no such key.
     */
    K getCeilingKey(K key);
    /**
     * Returns the smallest key greater than the given key (no equal to), or null if there is no such key.
     */
    K getAboveFloorKey(K key);
    /**
     * Returns the greatest key less than the given key (no equal to), or null if there is no such key.
     */
    K getBelowCeilingKey(K key);

    default boolean isEmpty() {
        return size() == 0;
    }

    default boolean isNotEmpty() {
        return size() != 0;
    }

    default boolean contains(K key) {
        return getValue(key) != null;
    }
}
