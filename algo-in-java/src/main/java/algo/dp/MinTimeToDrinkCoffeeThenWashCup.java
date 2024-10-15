package algo.dp;

import assist.TestHelper;

import java.time.Duration;
import java.util.*;

/**
 * There are lots of coffee machines in a row. Each machine takes some time to make a cup of coffee.
 * The ith machine takes time[i] to make a cup of coffee.
 * The machines are placed in a row. The machines work simultaneously.
 * <p/>
 * There are n people that want to drink coffee. Each person will drink exactly one cup of coffee (drinking time can be zero).
 * <p/>
 * After drinking, the cup needs to be cleaned. There is one and only one cup cleaning machine which can only take one cup at a time.
 * It will take machineCleaningTime time to clean a cup.
 * <p/>
 * Cup can be cleaned in 2 ways: 1) via cleaning machine 2) via hand wash (which takes handWashTime).
 * 0..n people can hand-wash  their own cups right after finishing their drinks, and they can wash concurrently.
 * Of course,  machine cleaning can also happen at the same time.
 * <p/>
 * <B>Assumpation: hand-wash will take much longer than machine-wash, otherwise solution
 * to let every one to hand wash will be fastest.</B>
 * <p/>
 * Please find the best strategy to minimize the total time to drink all the coffee and clean all the cups for n people.
 */
public class MinTimeToDrinkCoffeeThenWashCup {
    private int versionToRun = 1;

    public int minTime(int numOfPeople, int[] coffeeMachineMakingTime, int cupMachineCleaningTime, int cupHandWashTime) {
        if (versionToRun == 1) {
            return process_V1_no_DP(numOfPeople, coffeeMachineMakingTime, cupMachineCleaningTime, cupHandWashTime);
        } else if (versionToRun == 2) {
            return process_V2_DP(numOfPeople, coffeeMachineMakingTime, cupMachineCleaningTime, cupHandWashTime);
		} else if (versionToRun == 3) {
			return process_V3_cache_DP(numOfPeople, coffeeMachineMakingTime, cupMachineCleaningTime, cupHandWashTime);
        } else {
            throw new IllegalStateException("Unknown version to run: " + versionToRun);
        }
    }

	private static class CoffeeMaker {
		private int makingTime;
		public int availableAt;

		public CoffeeMaker(int makingTime, int availableAt) {
			this.makingTime = makingTime;
			this.availableAt = availableAt;
		}

		private int nextAvailableAt() {
			return availableAt + makingTime;
		}

		public static Comparator<CoffeeMaker> compareByNextAvailableTime() {
			return (CoffeeMaker cm1, CoffeeMaker cm2) -> cm1.nextAvailableAt() - cm2.nextAvailableAt();
		}
	}


	int[] earliestTimeToFinishDrink(int n, int[] makingTimes) {
		PriorityQueue<CoffeeMaker> queue = new PriorityQueue<>(CoffeeMaker.compareByNextAvailableTime());
		for (int i = 0; i < makingTimes.length; i++) {
			queue.offer(new CoffeeMaker(makingTimes[i], 0));
		}
		int[] ret = new int[n];	// item-i means i-th person finishes his/her drink at item-i moment
		for (int i = 0; i < n; i++) {
			CoffeeMaker cm = queue.poll();
			ret[i] = cm.nextAvailableAt();
			cm.availableAt = ret[i];
			queue.offer(cm);
		}
		return ret;
	}

	private int process_V1_no_DP(int n, int[] makingTimes, int machineWashingTime, int handWashingTime) {
		int[] minTimeToDrink = earliestTimeToFinishDrink(n, makingTimes);
		return wash_no_DP(minTimeToDrink, machineWashingTime, handWashingTime, 0, 0);
	}

	private int process_V3_cache_DP(int n, int[] makingTimes, int machineWashingTime, int handWashingTime) {
		int[] minTimeToDrink = earliestTimeToFinishDrink(n, makingTimes);
		return wash_cache_DP(minTimeToDrink, machineWashingTime, handWashingTime, 0, 0, new HashMap<String, Integer>(64));
	}

	private int wash_cache_DP(int[] finishDrinksAt, int machineWashTime, int handWashTime, int machineAvailableAt, int index, Map<String, Integer> cache) {
		String key = machineAvailableAt + "_" + index;
		if (cache.containsKey(key)) {
			return cache.get(key);
		}
		int n = finishDrinksAt.length;
		// 1. machine wash
		int machineWashSelf = Math.max(finishDrinksAt[index], machineAvailableAt) + machineWashTime;
		// 2. hand wash
		int handWashSelf = finishDrinksAt[index] + handWashTime;
		if (index == n - 1) {
			return Math.min(machineWashSelf, handWashSelf);
		} else {
			int washRest1 = wash_cache_DP(finishDrinksAt, machineWashTime, handWashTime, machineWashSelf, index + 1, cache);
			// machine wash self and wash rest
			int sol1 = Math.max(machineWashSelf, washRest1);

			int washRest2 = wash_cache_DP(finishDrinksAt, machineWashTime, handWashTime, machineAvailableAt, index + 1, cache);
			// hand wash self and wash rest
			int sol2 = Math.max(handWashSelf, washRest2);

			// min time to wash self and the rest
			int ret = Math.min(sol1, sol2);
			cache.put(key, ret);
			return ret;
		}
	}

	private int wash_no_DP(int[] finishDrinksAt, int machineWashTime, int handWashTime, int machineAvailableAt, int index) {
		int n = finishDrinksAt.length;
		// 1. machine wash
		int machineWashSelf = Math.max(finishDrinksAt[index], machineAvailableAt) + machineWashTime;
		// 2. hand wash
		int handWashSelf = finishDrinksAt[index] + handWashTime;
		if (index == n - 1) {
			return Math.min(machineWashSelf, handWashSelf);
		} else {
			int washRest1 = wash_no_DP(finishDrinksAt, machineWashTime, handWashTime, machineWashSelf, index + 1);
			// machine wash self and wash rest
			int sol1 = Math.max(machineWashSelf, washRest1);

			int washRest2 = wash_no_DP(finishDrinksAt, machineWashTime, handWashTime, machineAvailableAt, index + 1);
			// hand wash self and wash rest
			int sol2 = Math.max(handWashSelf, washRest2);
			
			// min time to wash self and the rest
			return Math.min(sol1, sol2);
		}
	}

	/**
	 * DP solution to find the minimum time to let all n people drink coffee and make their cups all in cleaned status.
	 * <p/>
	 *
	 * @param n	number of people
	 * @param makingTimes	time to make a cup of coffee for each coffee machine
	 * @param machineWashingTime	time to clean a cup by machine
	 * @param handWashingTime time to clean a cup by hand which is much longer than machine washing
	 * @return minimum time to drink and clean all cups
	 */
	private int process_V2_DP(int n, int[] makingTimes, int machineWashingTime, int handWashingTime) {
		int[] minTimeToDrink = earliestTimeToFinishDrink(n, makingTimes);
		// assuming all cups are cleaned via machine, the max time will be the time to finish the last drink cup washing
		int maxTime = 0;
		for (int i = 0; i < n; i++) {
			maxTime = Math.max(maxTime, minTimeToDrink[i]) + machineWashingTime;
		}
		int[][] dp = new int[n][maxTime];
		for (int t = 0; t < maxTime; t++) {
			dp[n - 1][t] = Math.min(Math.max(t, minTimeToDrink[n-1]) + machineWashingTime,
					minTimeToDrink[n-1] + handWashingTime);
		}
		for (int i = n - 2; i >= 0; i--) {
			for (int j = 0; j <= maxTime; j++) {
				// 1. machine wash me
				int machineWashSelf = Math.max(minTimeToDrink[i], j) + machineWashingTime;
				if (machineWashSelf >= maxTime) {
					break;
				}
				int washRest1 = dp[i + 1][machineWashSelf];
				int sol1 = Math.max(machineWashSelf, washRest1);
				// 2. hand wash me
				int handWashSelf = minTimeToDrink[i] + handWashingTime;
				int washRest2 = dp[i + 1][j];
				int sol2 = Math.max(handWashSelf, washRest2);
				dp[i][j] = Math.min(sol1, sol2);
			}
		}
		return dp[0][0];
	}

	public static void main(String[] args) {
		verify(7, 3, new int[]{4, 4, 4}, 1, 5);
		verify(9, 3, new int[]{4, 4, 4}, 3, 5);
		verify(11, 3, new int[]{4, 2, 5}, 3, 10);
		verify(10, 3, new int[]{4, 2, 5}, 3, 8);
		verify(19, 10, new int[]{4, 2, 5}, 3, 10);
		verify(18, 10, new int[]{4, 2, 5}, 3, 8);
		performanceTest(100);
	}

	private static void performanceTest(int testCycle) {
		final MinTimeToDrinkCoffeeThenWashCup sol = new MinTimeToDrinkCoffeeThenWashCup();
		Runnable r = () -> {
			sol.minTime(20, new int[]{4, 2, 5}, 3, 10);
			sol.minTime(20, new int[]{4, 2, 5}, 3, 8);
		};
		sol.versionToRun  = 1;
		Duration d1 = TestHelper.repeatRun(testCycle, r);
		sol.versionToRun  = 2;
		Duration d2 = TestHelper.repeatRun(testCycle, r);
		sol.versionToRun  = 3;
		Duration d3 = TestHelper.repeatRun(testCycle, r);
		System.out.println("Performance Test:\n\tV1=" + d1.toMillis() + "ms\n\tV2=" + d2.toMillis() + "ms"
				+ "\n\tV3=" + d3.toMillis() + "ms");
//		Performance Test:
//				V1=348ms
//				V2=9ms
//				V3=36ms
//      Conclusion: DP solution is much faster than the non-DP solution. When numOfPeople is 30, no-DP solution is too slow
	}

	private static void verify(int expected, int n, int[] makingTimes, int machineWashingTime, int handWashingTime) {
		MinTimeToDrinkCoffeeThenWashCup sol = new MinTimeToDrinkCoffeeThenWashCup();
		sol.versionToRun  = 1;
		int actual1 = sol.minTime(n, makingTimes, machineWashingTime, handWashingTime);
		sol.versionToRun  = 2;
		int actual2 = sol.minTime(n, makingTimes, machineWashingTime, handWashingTime);
		sol.versionToRun  = 3;
		int actual3 = sol.minTime(n, makingTimes, machineWashingTime, handWashingTime);
		System.out.println("expected=" + expected + " actual1=" + actual1 + " actual2=" + actual2 + " actual3=" + actual3
				+ " for input=" + Arrays.toString(makingTimes)
				+ " machine-wash=" + machineWashingTime + " hand-wash=" + handWashingTime + " n=" + n);
		if (expected != actual1) {
			throw new IllegalStateException("Expected=" + expected + " but actual1=" + actual1);
		}
		if (expected != actual2) {
			throw new IllegalStateException("Expected=" + expected + " but actual2=" + actual2);
		}
		if (expected != actual3) {
			throw new IllegalStateException("Expected=" + expected + " but actual3=" + actual3);
		}
	}
}
