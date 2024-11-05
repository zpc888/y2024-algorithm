package algo.tree.fenwick;

import assist.BaseSolution;

public class D3BITVerifier extends BaseSolution<Integer> implements D3BIT {
    private final D3BIT[] implementations;

    @Override
    protected int getNumberOfVersionsImplemented() {
        return implementations.length;
    }

    public D3BITVerifier(int[][][] cubic) {
        this.implementations = new D3BIT[] {
            new D3BITBruteForce(cubic),
            new D3IndexedTree(cubic)
        };
    }

    @Override
    public void add(int x, int y, int z, int val) {
        implementations[versionToRun - 1].add(x, y, z, val);
    }

    @Override
    public int sum(int x, int y, int z) {
        return implementations[versionToRun - 1].sum(x, y, z);
    }

    @Override
    public int sum(int x1, int y1, int z1, int x2, int y2, int z2) {
        return implementations[versionToRun - 1].sum(x1, y1, z1, x2, y2, z2);
    }


    public static void main(String[] args) {
        int[][][] cube = {
            {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
            },
            {
                {10, 11, 12},
                {13, 14, 15},
                {16, 17, 18}
            },
            {
                {19, 20, 21},
                {22, 23, 24},
                {25, 26, 27}
            }
        };
        D3BITVerifier sol = new D3BITVerifier(cube);
        sol.runAllVersions("(1,2,3)", () -> sol.sum(1, 2, 3), 21);
        sol.runAllVersions("(2,2,3)", () -> sol.sum(2, 2, 3), 21 + 81 - 6);
        sol.runAllVersions("(3,2,3)", () -> sol.sum(3, 2, 3), 21 + 81 - 6 + 81 - 6 + 60 - 6);
        sol.runAllVersions("(2, 2, 2, 3,2,3)", () -> sol.sum(2, 2, 2, 3, 2, 3), 29 + 47);
        sol.runAllVersions("(2, 2, 2, 3,3,3)", () -> sol.sum(2, 2, 2, 3, 3, 3), 29 + 47 + 35 + 53);

        sol.runAllVersions("add first then (3,2,3)", () -> {
            sol.add(1, 1, 2, 20);
            sol.add(3, 1, 2, 10);
            sol.add(1, 3, 2, 55);
            return sol.sum(3, 2, 3);
        }, 21 + 81 + 81 + 60 - 18 + 20 + 10);
    }




    private static class D3BITBruteForce implements D3BIT {
        private final int[][][] data;

        public D3BITBruteForce(int[][][] cube) {
            this.data = new int[cube.length + 1][cube[0].length + 1][cube[0][0].length + 1];
            for (int i = 1; i <= cube.length; i++) {
                for (int j = 1; j <= cube[0].length; j++) {
                    for (int k = 1; k <= cube[0][0].length; k++) {
                        data[i][j][k] = cube[i - 1][j - 1][k - 1];
                    }
                }
            }
        }

        @Override
        public void add(int x, int y, int z, int val) {
            data[x][y][z] += val;
        }

        @Override
        public int sum(int x, int y, int z) {
            int sum = 0;
            for (int i = 1; i <= x; i++) {
                for (int j = 1; j <= y; j++) {
                    for (int k = 1; k <= z; k++) {
                        sum += data[i][j][k];
                    }
                }
            }
            return sum;
        }

        @Override
        public int sum(int x1, int y1, int z1, int x2, int y2, int z2) {
            int sum = 0;
            for (int i = x1; i <= x2; i++) {
                for (int j = y1; j <= y2; j++) {
                    for (int k = z1; k <= z2; k++) {
                        sum += data[i][j][k];
                    }
                }
            }
            return sum;
        }
    }
}
