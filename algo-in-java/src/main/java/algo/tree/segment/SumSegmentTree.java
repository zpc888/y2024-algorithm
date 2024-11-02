package algo.tree.segment;

/**
 * Source: https://en.wikipedia.org/wiki/Segment_tree
 * <p>
 *    A segment tree is a tree data structure for storing intervals, or segments. It allows querying
 *    which of the stored segments contains a given point. A similar data structure is the <b>interval tree</b>.
 * </p>
 */
public class SumSegmentTree implements ISumSegmentTree {
	private final int[]     origin;
	private final int       originLen;
	private final int[]     refined;	// 1-base for easy api call
	private final int[]     sum;
	private final int[]     lazyAddPropagations;

	public SumSegmentTree(int[] arr) {
		this.origin = arr;		
		// if origin len is even = 2 * N + 1
		// if odd = 2 * N
		this.originLen = arr.length;
		refined = refineTo1Base(arr);
		int len = arr.length * 4;
		sum = new int[len];
		buildSum(1, originLen, 1);
		lazyAddPropagations = new int[len];
	}

	static int[] refineTo1Base(int[] arr) {
		int len = arr.length;
		int[] ret = new int[len + 1];
		for (int i = 0; i < len; i++) {
			ret[i + 1] = arr[i];
		}
		return ret;
	}

	/**
	 * sum the origin array from l-1 to r-1 (or refined l to r inclusive)
	 * and store to sum[pos]  (1-based)
	 */
	private void buildSum(int l, int r, int pos) {
		if (l == r) {
			sum[pos] = refined[l];
			return;
		}
		int mid = l + (r - l) / 2;
		buildSum(l, mid, pos * 2);
		buildSum(mid + 1, r, pos * 2 + 1);
		updateSumFromKids(pos);
	}

	private void updateSumFromKids(int pos) {
		sum[pos] = sum[pos * 2] + sum[pos * 2 + 1];
	}

	@Override
	public void add(int l, int r, int val) {
		doAdd(l, r, val, 1, originLen, 1);
	}

	@Override
	public int querySum(int l, int r) {
		return doQuerySum(l, r, 1, originLen, 1);
	}

	private int doQuerySum(int l, int r, int from, int to, int pos) {
		// ensure no pending actions due to lazy propagation strategy
		if (lazyAddPropagations[pos] != 0) {
			// having old value
			sum[pos] += (to - from + 1) * lazyAddPropagations[pos];
			lazyPropagateDownOneLevel(pos, lazyAddPropagations[pos]);
			lazyAddPropagations[pos] = 0;
		}
		if (l <= from && r >= to) {	// cover the whole range
			return sum[pos];
		}
		int mid = from + (to - from) / 2;
		int sum = 0;
//		if ( (l >= from && l <= mid) || (r >= from && r <= mid) ) {
		if (r >= from && mid >= l) {
			sum += doQuerySum(l, r, from, mid, pos*2);
		}
		if (r > mid) {
			sum += doQuerySum(l, r, mid + 1, to, pos*2 + 1);
		}
		return sum;
	}

	private void lazyPropagateDownOneLevel(int pos, int val) {
		if (2 * pos + 1 < lazyAddPropagations.length) {	// leaf node row already
			lazyAddPropagations[2*pos] = val;
			lazyAddPropagations[2*pos + 1] = val;
		}
	}

	private void doAdd(int l, int r, int v, int from, int to, int pos) {
		if (lazyAddPropagations[pos] != 0) {
			// having old value
			sum[pos] += (to - from + 1) * lazyAddPropagations[pos];
			lazyPropagateDownOneLevel(pos, lazyAddPropagations[pos]);
			lazyAddPropagations[pos] = 0;
		}
		if (l <= from && r >= to) {	// cover the whole range
			int deltaSum = (to - from + 1) * v;
			lazyPropagateDownOneLevel(pos, v);
			deltaSumUpdateTillTop(pos, deltaSum);
			return;
		}
		int mid = from + (to - from) / 2;
//		if ( (l >= from && l <= mid) || (r >= from && r <= mid) ) {
		if (r >= from && mid >= l) {
			doAdd(l, r, v, from, mid, pos*2);
		}
		if (r > mid) {
			doAdd(l, r, v, mid + 1, to, pos*2 + 1);
		}
	}

	private void deltaSumUpdateTillTop(int fromPos, int delta) {
		while (fromPos != 0) {
			sum[fromPos] += delta;
			fromPos = fromPos >> 1;
		}
	}
}
