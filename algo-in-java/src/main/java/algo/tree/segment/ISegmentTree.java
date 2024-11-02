package algo.tree.segment;

public interface ISegmentTree {
    void add(int l, int r, int val);
    int query(int l, int r);

    static int[] refineTo1Base(int[] arr) {
        int len = arr.length;
        int[] ret = new int[len + 1];
        for (int i = 0; i < len; i++) {
            ret[i + 1] = arr[i];
        }
        return ret;
    }
}
