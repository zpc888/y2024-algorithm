package algo.tree.fenwick;

/**
 * Both Indexed Tree (Fenwick Tree) and Segment Tree are used to solve the problem of range sum query and update in
 * O(LogN) instead of O(N). However, Indexed Tree is more space efficient and easier to expand to higher dimensions,
 * such as 2D or 3D.
 */
public interface BIT {
    /**
     * sum value from 1 to r (inclusive and 1-based index)
     */
    int sum(int r);

    /**
     * sum value from l to r (inclusive and 1-based index)
     */
    int sum(int l, int r);

    /**
     * add value to index idx (1-based index)
     */
    void add(int idx, int val);

    /**
     * set value to index idx (1-based index)
     */
    void set(int idx, int val);
}
