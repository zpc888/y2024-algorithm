package algo.tree.segment;

public class MaxSegmentTree implements IMaxSegmentTree {
    private final int[] origin;
    private final int[] refined;
    private final int originLen;
    private final int[] maxTree;
    private final int[] lazyAdd;    // lazy propagation
	// when propagtion, it will be one level down below maxTree
	// unless maxTree reaches left nodes

    public MaxSegmentTree(int[] arr) {
        this.origin = arr;
        this.originLen = arr.length;
        refined = ISegmentTree.refineTo1Base(arr);
        int n = 1;
        while (n < originLen) {
            n <<= 1;
        }
        n <<= 1;        // len should be 2n - 1, but since we use 1-based index, we need to add 1
        maxTree = new int[n];
        lazyAdd = new int[n];
        buildMaxTree(1, originLen, 1);
    }

	// build max tree element at pos which has max value from from to r inclusive
	// l and r are the indexes from refined-arr (1-based)
	private void buildMaxTree(int l, int r, int pos) {
		if (l == r) {
			maxTree[pos] = refined[l];
			return;
		}
		int mid = l + (r - l) / 2;
		buildMaxTree(l, mid, pos * 2);
		buildMaxTree(mid + 1, r, pos * 2 + 1);
		maxTree[pos] = Math.max(maxTree[pos * 2], maxTree[pos * 2 + 1]);
	}

    @Override
    public void add(int l, int r, int val) {
		doAdd(1, originLen, 1, l, r, val);
    }

	private void doAdd(int l, int r, int pos, int addL, int addR, int val) {
		if (r < addL || addR < l) {
			return;
		}
		doPropagateIfNeeded(l, r, pos);
		if (l >= addL && r <= addR) {		// fully covered
			lazyAdd[pos] += val;
			doPropagateIfNeeded(l, r, pos);
			return;
		}
		int mid = l + (r - l) / 2;
		doAdd(l, mid, pos * 2, addL, addR, val);	// left-child
		doAdd(mid + 1, r, pos * 2 + 1, addL, addR, val);	// right-child
	}

	private void doPropagateIfNeeded(int l, int r, int pos) {
		int lazyVal = lazyAdd[pos];
		if (lazyVal == 0) {
			return;
		}
//		maxTree[pos] += (r - l + 1) * lazyAdd[pos];
// wrong, it ask for max value, not sum - no need to times (r - l + 1)
		maxTree[pos] += lazyVal;
		lazyAdd[pos] = 0;
		// maybe its parent is not selected from me before,
		// now since I grow bigger/smaller, parent should re-select between me and my sibling
		int tmp = pos;
		while (tmp != 1) {
			int parentPos = tmp >> 1;
			int oldParentMax = maxTree[parentPos];
			maxTree[parentPos] = Math.max(maxTree[parentPos * 2] + lazyAdd[parentPos * 2],
					maxTree[parentPos * 2 + 1] + lazyAdd[parentPos * 2 + 1]);	// its sibling may have pending lazy add
			tmp = parentPos;
			if (maxTree[parentPos] == oldParentMax) {
				break;
			}
		}
		if (tmp != pos && tmp * 2 + 1 < maxTree.length) {    // fix for only 1 node or pos = 1
			maxTree[tmp] = Math.max(maxTree[tmp * 2] + lazyAdd[tmp * 2], maxTree[tmp * 2 + 1] + lazyAdd[tmp * 2 + 1]);
		}
		if (l != r) {
			lazyAdd[pos * 2] += lazyVal;
			lazyAdd[pos * 2 + 1] += lazyVal;
		}
	}

    @Override
    public int queryMax(int l, int r) {
		return doQuery(1, originLen, 1, l, r);
    }

	private int doQuery(int l, int r, int pos, int queryL, int queryR) {
		if (r < queryL || queryR < l) {
			return Integer.MIN_VALUE;
		}
		doPropagateIfNeeded(l, r, pos);
		if (l >= queryL && r <= queryR) {
			return maxTree[pos];
		}
		int mid = l + (r - l) / 2;
		int leftMax = doQuery(l, mid, pos * 2, queryL, queryR);
		int rightMax = doQuery(mid + 1, r, pos * 2 + 1, queryL, queryR);
		return Math.max(leftMax, rightMax);
	}
}
