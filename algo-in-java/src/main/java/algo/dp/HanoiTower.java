package algo.dp;

public class HanoiTower {
	private final static String PEG_A = "A";
	private final static String PEG_B = "B";
	private final static String PEG_C = "C";

    private int step = 0;

	public void hanoi(int nDisks) {
		if (nDisks <= 0) {
			throw new IllegalStateException("cannot be zero/negative");
		}
        step = 0;
		move(nDisks, PEG_A, PEG_C, PEG_B);
	}

	private void move(int n, String fromPeg, String toPeg, String helpPeg) {
		if (n == 1) {
			System.out.printf("STEP %2d: Moving Disk-%s from Peg-%s to Peg-%s%n", ++step, 1, fromPeg, toPeg);
			return;
		}
		move(n - 1, fromPeg, helpPeg, toPeg);
		System.out.printf("STEP %2d: Moving Disk-%s from Peg-%s to Peg-%s%n", ++step, n, fromPeg, toPeg);
		move(n - 1, helpPeg, toPeg, fromPeg);
	}

	public static void main(String[] args) {
		HanoiTower h = new HanoiTower();
		for (int i = 1; i < 6; i++) {
			System.out.println("Hanoi Tower (" + i + ") steps:");
			h.hanoi(i);
            if (h.step != (int) Math.pow(2, i) - 1) {
                throw new IllegalStateException("steps mismatch");
            }
			System.out.println();
		}
	}

}
