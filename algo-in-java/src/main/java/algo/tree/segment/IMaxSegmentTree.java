package algo.tree.segment;

public interface IMaxSegmentTree extends ISegmentTree {
    int queryMax(int l, int r);

    @Override
    default int query(int l, int r) {
        return queryMax(l, r);
    }
}
