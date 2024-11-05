package algo.tree.fenwick;

/**
 * Source: <a href="https://en.wikipedia.org/wiki/Fenwick_tree">Wikipedia</a>
 * <p>
 * This is also known as Fenwick Tree, or Binary Indexed Tree (BIT).
 * </p>
 */
public class BinaryIndexedTreeOrFenwickTree implements BIT {
	private final int[] origin;	// 0-based
	private final int   len;
	private final int[] tree;	// 1-based

	public BinaryIndexedTreeOrFenwickTree(int[] arr) {
		this.origin = arr;
		this.len = arr.length;
		tree = new int[len + 1];
		System.arraycopy(arr, 0, tree, 1, len);
		buildBIT();
	}

	private void buildBIT() {
		// for each i, let its parent to add its tree value (not original one)
		// tree value will cover its previous range of orginal ones
		for (int i = 1; i <= len; i++) {
			// i & -i (or i & (~i + 1)) is the rightmost 1
			// i + (i & -i) means i-val will be added to it (first time)
			// parent's parent will contain i-val indirectly via parent's sum
			int p = i + (i & -i);
			if (p <= len) {
				tree[p] = tree[p] + tree[i];
			}
		}
	}

	@Override
	public void add(int i, int val) {
		// add val to tree-i, tree-i-parent, tree-i-grandparent, ... 
		while (i <= len) {
			tree[i] += val;
			i += i & -i;	// logic op preference is lower than arithic
							// but += will finish its right expression first
		}
	}


	@Override
	public int sum(int to) {
		if (to <= 0) {
			return 0;
		}
		if (to > len) {
			to = len;
		}
		int sum = 0;
		while (to != 0) {
			sum += tree[to];
			to -= to & -to;
		}
		return sum;
	}

	@Override
	public int sum(int l, int r) {
		return sum(r) - sum(l-1);
	}

	@Override
	public void set(int i, int val) {
		if (i > len) {
			return;
		}
		int delta = val - origin[i - 1];
		add(i, delta);
	}
}
