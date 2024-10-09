package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

public class Q0547_NumberOfProvinces {
    public int findCircleNum(int[][] isConnected) {
		return findCircleNum_V1(isConnected);
	}


    @MetricsRuntime(ms = 0, beats = 100.00)
    @MetricsMemory(mb = 48.07, beats = 20.51)
    private int findCircleNum_V1(int[][] connMatrix) {
		int len = connMatrix.length;		// len * len matrix and symmetric
		boolean[] connected = new boolean[len];
		int count = 0;
		for (int i = 0; i < len; i++) {
			if (!connected[i]) {
				dfs(i, connMatrix, connected);
				count++;
			}
		}
		return count;
	}

	private void dfs(int i, int[][] connMatrix, boolean[] connected) {
		connected[i] = true;
		for (int j = 0; j < connMatrix.length; j++) {
			// not connected yet, but [i, j] is connected, 
			// from i-th dfs to j-th dfs, then (j+1)-th dfs, ...
			if (!connected[j] && connMatrix[i][j] == 1) {
				dfs(j, connMatrix, connected);
			}
		}
	}

}
