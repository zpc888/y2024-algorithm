package algo.tree.fenwick;

public class D2IndexedTree implements D2BIT {
	private final int[][] origin;	// 0-based
	private final int rows;
	private final int cols;
	private final int[][] tree;		// 1-based;

	public D2IndexedTree(int[][] matrix) {
		origin = matrix;
		rows = origin.length;
		cols = origin[0].length;
		tree = new int[rows + 1][cols + 1];
		for (int i = 1; i <= rows; i++) {
			for (int j = 1; j <= cols; j++) {
				tree[i][j] = origin[i - 1][j - 1];
			}
		}
		buildTree();
	}

	private void buildTree() {
		for (int i = 1; i <= rows; i++) {
			for (int j = 1; j <= cols; j++) {
				int pj = j + (j & -j);	// parent j
                if (pj > cols) {
                    continue;
                }
                tree[i][pj] += tree[i][j];
			}
		}
        for (int j = 1; j <= cols; j++) {
            for (int i = 1; i <= rows; i++) {
                int pi = i + (i & -i);	// parent i
                if (pi > rows) {
                    continue;
                }
                tree[pi][j] += tree[i][j];
            }
        }
	}

    @Override
    public void add(int x, int y, int val) {
		while (x <= rows) {
            int ty = y;
            while (ty <= cols) {
                tree[x][ty] += val;
                ty += ty & -ty;
            }
			x += x & -x;
		}
    }

    @Override
    public int sum(int x, int y) {
		int sum = 0;
		while (x > 0) {
            int ty = y;
            while (ty > 0) {
                sum += tree[x][ty];
                ty -= ty & -ty;
            }
			x -= x & -x;
		}
		return sum;
    }

    @Override
    public int sum(int x1, int y1, int x2, int y2) {
		return sum(x2, y2) - sum(x2, y1 - 1) - sum(x1 - 1, y2) 
			+ sum(x1 - 1, y1 - 1);
    }
}
