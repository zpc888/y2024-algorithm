package algo.dp;

import assist.DataHelper;

/**
 * assuming 10 * 10 chessboard
 *
 */
public class ChessHorseJump {
    private int versionToRun = 1;
	private static final int N = 10;

    public int jump(int fromX, int fromY, int steps, int toX, int toY) {
        if (versionToRun == 1) {
            return process_V1_no_DP(fromX, fromY, steps, toX, toY);
        } else if (versionToRun == 2) {
            return process_V2_DP(fromX, fromY, steps, toX, toY);
        } else {
            throw new IllegalStateException("Unknown version to run: " + versionToRun);
        }
    }

    int process_V2_DP(int fromX, int fromY, int steps, int toX, int toY) {
        int[][][] dp = new int[steps + 1][N][N];
        dp[steps][fromX][fromY] = 1;
        for (int s = steps; s > 0; s--) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int curr = dp[s][i][j];
                    if (curr > 0) {
                        if (i - 2 >= 0 && j - 1 >= 0) {
                            dp[s - 1][i - 2][j - 1] += curr;
                        }
                        if (i - 2 >= 0 && j + 1 < N) {
                            dp[s - 1][i - 2][j + 1] += curr;
                        }
                        if (i - 1 >= 0 && j - 2 >= 0) {
                            dp[s - 1][i - 1][j - 2] += curr;
                        }
                        if (i + 1 < N && j + 2 < N) {
                            dp[s - 1][i + 1][j + 2] += curr;
                        }
                        if (i + 2 < N && j - 1 >= 0) {
                            dp[s - 1][i + 2][j - 1] += curr;
                        }
                        if (i + 2 < N && j + 1 < N) {
                            dp[s - 1][i + 2][j + 1] += curr;
                        }
                        if (i + 1 < N && j - 2 >= 0) {
                            dp[s - 1][i + 1][j - 2] += curr;
                        }
                        if (i - 1 >= 0 && j + 2 < N) {
                            dp[s - 1][i - 1][j + 2] += curr;
                        }
                    }
                }
            }
        }
        return dp[0][toX][toY];
    }

	int process_V1_no_DP(int fromX, int fromY, int steps, int toX, int toY) {
		if (fromX < 0 || fromX > N || fromY < 0 || fromY > N) {
			return 0;
		}
		if (steps == 0) {
			return fromX == toX && fromY == toY ? 1 : 0;
		} else {
			int rest = steps - 1;
			return process_V1_no_DP(fromX - 2, fromY - 1, rest, toX, toY)
				+ process_V1_no_DP(fromX - 2, fromY + 1, rest, toX, toY)
				+ process_V1_no_DP(fromX - 1, fromY - 2, rest, toX, toY)
				+ process_V1_no_DP(fromX + 1, fromY + 2, rest, toX, toY)
				+ process_V1_no_DP(fromX + 2, fromY - 1, rest, toX, toY)
				+ process_V1_no_DP(fromX + 2, fromY + 1, rest, toX, toY)
				+ process_V1_no_DP(fromX + 1, fromY - 2, rest, toX, toY)
				+ process_V1_no_DP(fromX - 1, fromY + 2, rest, toX, toY);
		}
   	}

    public static void main(String[] args) {
        ChessHorseJump sol = new ChessHorseJump();
        for (int i = 0; i < 4; i++) {
            for (int step = 1; step < 10; step++) {
                sol.versionToRun = 1;
                int v1 = sol.jump(0, 0, step, i, i);
                System.out.println("v1=" + v1 + " for step=" + step + " and i=" + i);
                sol.versionToRun = 2;
                int v2 = sol.jump(0, 0, step, i, i);
                System.out.println("v2=" + v2 + " for step=" + step + " and i=" + i);
                DataHelper.assertTrue(v1 == v2, "Failed for step=" + step + " and i=" + i);
            }
        }
        System.out.println("ChessHorseJump passed");
    }
}
