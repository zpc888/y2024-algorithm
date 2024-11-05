package algo.tree.fenwick;

public interface D2BIT {
    /**
     * add value to (x, y) (1-based index)
     */
    void add(int x, int y, int val);

    /**
     * sum value from (1, 1) to (x, y) (inclusive and 1-based index)
     */
    int sum(int x, int y);

    /**
     * sum value from (x1, y1) to (x2, y2) region (inclusive and 1-based index)
     */
    int sum(int x1, int y1, int x2, int y2);
}
