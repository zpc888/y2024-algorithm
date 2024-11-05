package algo.tree.fenwick;

public interface D3BIT {
    void add(int x, int y, int z, int val);

    int sum(int x, int y, int z);

    int sum(int x1, int y1, int z1, int x2, int y2, int z2);
}
