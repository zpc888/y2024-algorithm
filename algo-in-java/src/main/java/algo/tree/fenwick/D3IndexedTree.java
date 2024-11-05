package algo.tree.fenwick;

public class D3IndexedTree implements D3BIT {
	private final int[][][] origin;	// 0-based
	private final int maxI, maxJ, maxK;
	private final int[][][] tree;	// 1-based

	public D3IndexedTree(int[][][] cubic) {
		origin = cubic;
		maxI = cubic.length;
		maxJ = cubic[0].length;
		maxK = cubic[0][0].length;
		tree = new int[maxI + 1][maxJ + 1][maxK + 1];
        for (int i = 1; i <= maxI; i++) {
            for (int j = 1; j <= maxJ; j++) {
                for (int k = 1; k <= maxK; k++) {
                    tree[i][j][k] = origin[i - 1][j - 1][k - 1];
                }
            }
        }
		buildTree();
	}

	private void buildTree() {
		for (int i = 1; i <= maxI; i++) {
			for (int j = 1; j <= maxJ; j++) {
				for (int k = 1; k <= maxK; k++) {
					int pk = k + (k & -k);
					if (pk <= maxK) {
						tree[i][j][pk] += tree[i][j][k];
					}
				}
			}
		}
		for (int i = 1; i <= maxI; i++) {
			for (int k = 1; k <= maxK; k++) {
				for (int j = 1; j <= maxJ; j++) {
					int pj = j + (j & -j);
					if (pj <= maxJ) {
						tree[i][pj][k] += tree[i][j][k];
					}
				}
			}
		}
		for (int k = 1; k <= maxK; k++) {
			for (int j = 1; j <= maxJ; j++) {
				for (int i = 1; i <= maxI; i++) {
					int pi = i + (i & -i);
					if (pi <= maxI) {
						tree[pi][j][k] += tree[i][j][k];
					}
				}
			}
		}
	}

    @Override
    public void add(int x, int y, int z, int val) {
		int tx = x;
		while (tx <= maxI) {
			int ty = y;
			while (ty <= maxJ) {
				int tz = z;
				while (tz <= maxK) {
					tree[tx][ty][tz] += val;
					tz += tz & -tz;
				}
				ty += ty & -ty;
			}
			tx += tx & -tx;
		}
    }

    @Override
    public int sum(int x, int y, int z) {
        int sum = 0;
		int tx = x;
		while (tx > 0) {
			int ty = y;
			while (ty > 0) {
				int tz = z;
				while (tz > 0) {
					sum += tree[tx][ty][tz];
					tz -= tz & -tz;
				}
				ty -= ty & -ty;
			}
			tx -= tx & -tx;
		}
		return sum;
    }

    @Override
    public int sum(int x1, int y1, int z1, int x2, int y2, int z2) {
		return sum(x2, y2, z2) - sum(x1 - 1, y2, z2) - sum(x2, y1 - 1, z2)
			+ sum(x1 - 1, y1 - 1, z2) - sum(x2, y2, z1 - 1) 
			+ sum(x1 - 1, y2, z1 - 1) + sum(x2, y1 - 1, z1 - 1)
			- sum(x1 - 1, y1 - 1, z1 - 1);
    }
}
