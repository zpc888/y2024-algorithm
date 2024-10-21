package algo.slidewindow;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.*;

/**
 * Source: https://leetcode.com/problems/gas-station/ assumes that there is a unique solution or no solution.
 * <p />
 * This question allows there are 0, 1 or n solutions, please find all solutions starting index. If no solution,
 * return empty list.
 */
public class GasStationExtended extends BaseSolution<List<Integer>> {

	@Override
	public int getNumberOfVersionsImplemented() {
		return 2;
	}

    public List<Integer> canCompleteCircuitStartingIndices(int[] gas, int[] cost) {
		if (gas == null || cost == null 
				|| gas.length != cost.length || gas.length == 0) {
			return Collections.emptyList();
		}
		if (versionToRun == 1) {
			return v1_noSlidingWin(gas, cost);
		} else if (versionToRun == 2) {
			return v2_slidingWin(gas, cost);
		} else {
			throw new RuntimeException("Not supported version: #" + versionToRun);
		}
    }

	private List<Integer> v1_noSlidingWin(int[] gas, int[] cost) {
		int len = gas.length;
		List<Integer> ret = new ArrayList<Integer>(len);
		// O(N^2)
		for (int i = 0; i < len; i++) {
			int loopSum = 0;
			for (int j = 0; j < len; j++) {
				int j2 = (i + j) % len;
				loopSum += gas[j2] - cost[j2];
				if (loopSum < 0) {
					// start from i, cann't reach j2 for sure, try i+1
					break;
				}
			}
			if (loopSum >= 0) {
				ret.add(i);
			}
		}
		return ret;
	}

	private List<Integer> v2_slidingWin(int[] gas, int[] cost) {
		int len = gas.length;
		int[] preSum = new int[len + len - 1];
		preSum[0] = gas[0] - cost[0];
		for (int i = 1; i < len + len - 1; i++) {
			int i2 = i % len;
			preSum[i] = preSum[i - 1] + gas[i2] - cost[i2];
		}

		List<Integer> ret = new ArrayList<Integer>(len);
		LinkedList<Integer> minSlidingWin = new LinkedList<>();
		int preLoopSum = 0;
		int winBgn = 0;
		// nearly O(N) instead of O(N^2)
		for (int winEnd = 0; winEnd < len + len - 1; winEnd++) {
			int val = preSum[winEnd];
			while (!minSlidingWin.isEmpty() 
					&& preSum[minSlidingWin.peekLast()] >= val) {
				minSlidingWin.pollLast();
			}
			minSlidingWin.offerLast(winEnd);
			if (winEnd - winBgn + 1 == len) {
				int minLoopIdx = minSlidingWin.peekFirst();
				int weakestLoopTotal = preSum[minLoopIdx] - preLoopSum;
				if (weakestLoopTotal >= 0) {
					// this loop should be ok since the weakest station is >= 0
					ret.add(winBgn);
				}	// else at least this station cannot be reach
				if (minLoopIdx == winBgn) {
					minSlidingWin.pollFirst();
				}
                preLoopSum = preSum[winBgn++];
			}
		}
		return ret;
	}

    public static void main(String[] args) {
        GasStationExtended gs = new GasStationExtended();
        gs.runAllVersions("Example 1",
            () -> gs.canCompleteCircuitStartingIndices(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}),
            List.of(3));
        gs.runAllVersions("Example 2",
                () -> gs.canCompleteCircuitStartingIndices(new int[]{2, 3, 4}, new int[]{3, 4, 3}),
                Collections.emptyList());
		final int cycles = 10_000;
		final int[][] ints = new int[cycles][];
		for (int i = 0; i < cycles; i++) {
			int[] tmp = new int[0];
			while (tmp.length < 2) {
				tmp = DataHelper.generateRandomData(32, 1, 20);
			}
			ints[i] = tmp;
		}

		// large volume test
		gs.silent = true;
        for (int i = 0; i < cycles; i++) {
            int len = ints[i].length / 2;
            if (len <= 0) {
                continue;
            }
            final int[] gas = new int[len];
            final int[] cost = new int[len];
            for (int j = 0; j < len; j++) {
                gas[j] = ints[i][2 * j];
                cost[j] = ints[i][2 * j + 1];
            }
            gs.runAllVersions("Random test #" + (i + 1) + " with gas/cost: " + Arrays.toString(ints),
                    () -> gs.canCompleteCircuitStartingIndices(gas, cost),
                    null);
        }

		// performance test
		gs.performMeasure("Performance test with " + cycles + " random data",
				() -> {
					for (int i = 0; i < cycles; i++) {
						int len = ints[i].length / 2;
						if (len <= 0) {
							continue;
						}
						final int[] gas = new int[len];
						final int[] cost = new int[len];
						for (int j = 0; j < len; j++) {
							gas[j] = ints[i][2 * j];
							cost[j] = ints[i][2 * j + 1];
						}
						gs.canCompleteCircuitStartingIndices(gas, cost);
					}
					return null;
				});

//		================================ performance Report ==========================
//		Version-1: Duration: PT0.004287814S			// O(N^2)   N^2 is for worst case
//		Version-2: Duration: PT0.00455567S			// O(N)
    }
}
