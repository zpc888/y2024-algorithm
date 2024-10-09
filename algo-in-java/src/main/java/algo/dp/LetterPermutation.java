package algo.dp;

import java.util.*;
import java.util.function.Function;

public class LetterPermutation {
    public List<String> permute(String s) {
		return permute_v1(s);
	}

	public List<String> permute_noDup(String s) {
		return permute_noDup_v1(s);
	}

	List<String> permute_v2(String s) {
		if (s == null || s.length() == 0) {
			return Collections.emptyList();
		}
		char[] chars = s.toCharArray();
		List<Character> charList = new ArrayList<>(chars.length);
		for (char c : chars) {
			charList.add(c);
		}
		int size = 1;
		for (int i = 1; i < chars.length; i++) {
			size *= (i + 1);
		}
		List<String> ret = new ArrayList<>(size);
		doPermute_v2(charList, "", ret);
		return ret;
	}

	private void doPermute_v2(List<Character> chars, String path, List<String> holder) {
		if (chars.isEmpty()) {
			holder.add(path);
			return;
		}
		for (int i = 0; i < chars.size(); i++) {
			Character ch = chars.remove(i);
			doPermute_v2(chars, path + ch, holder);
			chars.add(i, ch);
		}
	}

	private List<String> permute_v1(String s) {
		if (s == null || s.length() == 0) {
			return Collections.emptyList();
		}
		char[] chars = s.toCharArray();
		int size = 1;
		for (int i = 1; i < chars.length; i++) {
			size *= (i + 1);
		}
		List<String> ret = new ArrayList<>(size);
		doPermute(chars, 0, ret);
		return ret;
    }

	private List<String> permute_noDup_v1(String s) {
		if (s == null || s.length() == 0) {
			return Collections.emptyList();
		}
		char[] chars = s.toCharArray();
		List<String> ret = new ArrayList<>(32);
		doPermute_noDup(chars, 0, ret);
		return ret;
	}

	private void doPermute_noDup(char[] chars, int index, List<String> holder) {
		int len = chars.length;
		if (index == len) {
			holder.add(new String(chars));
		}
		Set<Character> used = new HashSet<>();
		// or use boolean[] used = new boolean[256]; if only ascii chars (including extended ascii)
		for (int i = index; i < len; i++) {
			if (used.add(chars[i])) {
				swap(chars, i, index);
				doPermute_noDup(chars, index + 1, holder);
				swap(chars, i, index);	// swap back to restore previous state
			}
		}
	}

	private void doPermute(char[] chars, int index, List<String> holder) {
		int len = chars.length;
		if (index == len) {
			holder.add(new String(chars));
		}
		for (int i = index; i < len; i++) {
			swap(chars, i, index);
			doPermute(chars, index + 1, holder);
			swap(chars, i, index);	// swap back to restore previous state
		}
	}

	private void swap(char[] chars, int i, int j) {
		if (i != j) {
			char tmp = chars[i];
			chars[i] = chars[j];
			chars[j] = tmp;
		}
	}

	public static void main(String[] args) {
		LetterPermutation p = new LetterPermutation();
		List<String> permutations = p.permute("abc");

		System.out.println("abc Permutations in V1:");
		permutations.stream().forEach(System.out::println);
		System.out.println();

		System.out.println("abc Permutations in V2:");
		p.permute_v2("abc").stream().forEach(System.out::println);
		System.out.println();

		System.out.println("Perf testing v1 vs v2 ============");
		final int testCycles = 10_000;
		final String testStr = "abcdefgh";
		long bgnAt = System.currentTimeMillis();
		List<String> v1 = runNTimes(testCycles, testStr, p::permute);
		long msV1 = System.currentTimeMillis() - bgnAt;
		System.out.println("V1: " + msV1 + " ms");			// 6187

		bgnAt = System.currentTimeMillis();
		List<String> v2 = runNTimes(testCycles, testStr, p::permute_v2);
		long msV2 = System.currentTimeMillis() - bgnAt;
		System.out.println("V2: " + msV2 + " ms");			// 31693 (5 times slower due to List<Character> manipulation)
		assert v1.equals(v2);

		bgnAt = System.currentTimeMillis();
		List<String> v3 = runNTimes(testCycles, testStr, p::permute_noDup);
		long msV3 = System.currentTimeMillis() - bgnAt;
		System.out.println("No Dup: " + msV2 + " ms");			// 31693 (5 times slower due to List<Character> manipulation)
		assert v1.equals(v3);

		// although both is O(n!), but V2 is slower due to the large constant factor
		System.out.println();
		System.out.printf("CONCLUSION: V2 is %.2f times slower than V1\n", (double)msV2 / msV1);     // 5.29 times slower
		System.out.printf("            NoDup is %.2f times slower than V1\n", (double)msV3 / msV1);  // 3.09 slower due to uniqueness check

		System.out.println();
		System.out.println("acc Permutations in V1 (with Dup):");
		p.permute("acc").forEach(System.out::println);
		System.out.println("acc Permutations in V1 (without Dup):");
		p.permute_noDup("acc").forEach(System.out::println);
	}

	private static List<String> runNTimes(int times, String input, Function<String, List<String>> func) {
		List<String> ret = null;
		for (int i = 0; i < times; i++) {
			List<String> r = func.apply(input);
			if (i == 0) {
				ret = r;
			} else {
				assert ret.equals(r);
			}
		}
		return ret;
	}
}
