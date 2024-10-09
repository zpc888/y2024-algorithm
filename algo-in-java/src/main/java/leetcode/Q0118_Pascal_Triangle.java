package leetcode;

import run.MetricsMemory;
import run.MetricsRuntime;

import java.util.ArrayList;
import java.util.List;

/**
 * Q118. Pascal's Triangle.
 * Source: https://leetcode.com/problems/pascals-triangle/description/
 * Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
 * In Pascal's triangle, each number is the sum of the two numbers directly above it.
 * Constraints:
 *  1 <= numRows <= 30
 */
public class Q0118_Pascal_Triangle {
    public List<List<Integer>> generate(int numRows) {
//        return generate_V1(numRows);
        return generate_V2(numRows);
    }

    @MetricsRuntime(ms = 1, beats = 84.09)
    @MetricsMemory(mb = 42.11, beats = 16.07)
    private List<List<Integer>> generate_V1(int numRows) {
        List<List<Integer>> ret = new ArrayList<>(32);
        if (numRows > 0) {
            ret.add(List.of(1));
        }
        if (numRows > 1) {
            ret.add(List.of(1, 1));
        }
        for (int i = 2; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            row.add(1);
            List<Integer> prevRow = ret.get(i - 1);
            for (int j = 1; j < prevRow.size(); j++) {
                row.add(prevRow.get(j - 1) + prevRow.get(j));
            }
            row.add(1);
            ret.add(row);
        }
        return ret;
    }

    @MetricsRuntime(ms = 1, beats = 84.09)
    @MetricsMemory(mb = 41.74, beats = 79.41)
    private List<List<Integer>> generate_V2(int numRows) {
        List<List<Integer>> ret = new ArrayList<>(32);
		List<Integer> row = null;
		List<Integer> prevRow = null;
		for (int i = 0; i < numRows; i++) {
			row = new ArrayList<>(i+1);
			for (int j = 0; j <= i; j++) {
				if (j == 0 || j == i) {
					row.add(1);
				} else {
					row.add(prevRow.get(j - 1) + prevRow.get(j));
				}
			}
			ret.add(row);
			prevRow = row;
		}
        return ret;
    }
}
