package algo.tree.segment;

public interface ISumSegmentTree extends ISegmentTree {
    @Override
    default int query(int l, int r) {
        return querySum(l, r);
    }

    int querySum(int l, int r);
}
