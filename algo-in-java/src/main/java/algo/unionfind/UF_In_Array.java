package algo.unionfind;

public class UF_In_Array {
	private final int[] parents;
	private final int[] sizes;		// only parent's size has meaning, children's size is obseleted
	private final int[] assits;		// used to optimize parent lookup
	private int numOfSets;			// or number of parents
	private int row;
	private int col;

	public UF_In_Array(int[][] data) {
		row = data.length;
		col = data[0].length;
		int total = row * col;
		parents = new int[total];
		sizes = new int[total];
		assits = new int[total];
		numOfSets = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (data[i][j] == 1) {
					numOfSets++;
					int idx = index(i, j);
					parents[idx] = idx;
					sizes[idx] = 1;
					numOfSets++;
				}
			}
		}
		for (int c = 1; c < col; c++) {
			if (data[0][c-1] == 1 && data[0][c] == 1) {
				union(0, c-1, 0, c);
			}
		}
		for (int r = 1; r < row; r++) {
			if (data[r-1][0] == 1 && data[r][0] == 1) {
				union(r-1, 0, r, 0);
			}
		}
		for (int r = 1; r < row; r++) {
			for (int c = 1; c < col; c++) {
				if (data[r][c] == 1) {
					if (data[r-1][c] == 1) {
						union(r, c, r-1, c);
					}
					if (data[r][c-1] == 1) {
						union(r, c, r, c-1);
					}
				}
			}
		}
	}

	public int getNumberOfSets() {
		return numOfSets;
	}

	public void union(int r1, int c1, int r2, int c2) {
		int idx1 = index(r1, c1);
		int idx2 = index(r2, c2);
		int p1 = findAncestorIndex(idx1);
		int p2 = findAncestorIndex(idx2);
		if (p1 != p2) {
			int size1 = sizes[idx1];
			int size2 = sizes[idx2];
			if (size1 >= size2) {
				sizes[idx1] = size1 + size2;
				parents[idx2] = idx1;
			} else {
				sizes[idx2] = size1 + size2;
				parents[idx1] = idx2;
			}
			numOfSets--;
		}
	}

	public int getRowFromIndex(int idx) {
		return idx / row;
	}

	public int getColFromIndex(int idx) {
		return idx % row;
	}

	public int findAncestorIndex(int r, int c) {
		int idx = index(r, c);
		return findAncestorIndex(idx);
	}

	public int findAncestorIndex(int idx) {
		int i = 0;
		while (idx != parents[idx]) {
			assits[i++] = idx;
			idx = parents[idx];
		}
		// optimize the ancestor lookup path to O(1) instead of O(path-depth)
		for (i--; i>=0; i--) {
			parents[assits[i]] = idx;
		}
		return idx;
	}

	private int index(int r, int c) {
		return r * row + c;
	}
}
