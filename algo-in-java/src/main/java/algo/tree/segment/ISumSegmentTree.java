package algo.tree.segment;

public interface ISumSegmentTree {
    void add(int l, int r, int val);
    int querySum(int l, int r);
}
