package leetcode;

import algo.slidewindow.GasStationExtended;
import run.MetricsMemory;
import run.MetricsRuntime;

/**
 * Source: https://leetcode.com/problems/gas-station/
 * <p />
 * Assumption: If there is a solution, it is guaranteed to be unique, i.e. 0 or 1 solution.
 * <p />
 * If there are multiple solutions, see {@link GasStationExtended}.
 * See {@link algo.slidewindow.GasStationExtended} for multiple solutions, i.e. 0, 1, or n solutions.
 */
public class Q0134_GasStation {

    @MetricsRuntime(ms = 2, beats = 97.11)
    @MetricsMemory(mb = 56.21, beats = 43.59)
    public int canCompleteCircuit(int[] gas, int[] cost) {
		int len = gas.length;
		int total = 0;
		int resetableTank = 0;
		int start = 0;
		// Avoid O(N^2) ---> achieve O(N)
		for (int i = 0; i < len; i++) {
			int diff = gas[i] - cost[i];
			resetableTank += diff;
			total += diff;
			if (resetableTank < 0) {
				start = i + 1;		// definitely prev one cann't reach this
				resetableTank = 0;
			}
		}
		return total >= 0 ? start : -1;
    }
}
