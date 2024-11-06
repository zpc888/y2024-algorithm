package algo.str.ac;

import assist.BaseSolution;
import assist.DataHelper;

import java.util.HashSet;
import java.util.Set;

public class ACAutomatonVerifier extends BaseSolution<Set<String>> implements IACAutomaton {
    private final IACAutomaton[] acAutomatons;

    public ACAutomatonVerifier(String[] keywords) {
        ACAutomaton.debugOn = true;
        ACAutomatonInMap.debugOn = true;
        this.acAutomatons = new IACAutomaton[]{
            new SearchViaSystemStrIndexOf(keywords),
            new ACAutomaton(keywords),
            new ACAutomatonInMap(keywords)
        };
    }

    @Override
    protected int getNumberOfVersionsImplemented() {
        return acAutomatons.length;
    }

    @Override
    public Set<String> search(String text) {
        return acAutomatons[versionToRun - 1].search(text);
    }

    public static void main(String[] args) {
        troubleshooting05();
        troubleshooting04();
        troubleshooting03();
        troubleshooting02();
        troubleshooting01();
        highVolume();
    }

    private static void highVolume() {
        final int cycles = 100_000;
        final String charset = "abcde";
        String[][] keywords = new String[cycles][];
        String[] texts = DataHelper.genFixedSizeStrArr(cycles, 2, 100, charset);
        for (int i = 0; i < cycles; i++) {
            keywords[i] = DataHelper.genFixedSizeStrArr(5, 1, 8, charset);
            final int fi = i;
            ACAutomatonVerifier sol = new ACAutomatonVerifier(keywords[i]);
            sol.runAllVersions("High Volume test " + (i + 1)
//                            + ": " + texts[fi] + " keywords: " + Arrays.toString(keywords[fi])
                    , () -> sol.search(texts[fi]), null);
        }

        // performance test
        ACAutomatonVerifier sol = new ACAutomatonVerifier(DataHelper.genFixedSizeStrArr(100, 1, 20, "abc"));
        sol.performMeasure("High Volume Performance Test", () -> {
            for (int i = 0; i < cycles; i++) {
                sol.search(DataHelper.genFixedSizeStrArr(1, 100, 1000, "abc")[0]);
            }
            return null;
        });
//        ====================== < High Volume Performance Test > Performance Report ==============
//        Version-1: Duration: PT2.465356873S
//        Version-2: Duration: PT1.589876526S
//        Version-3: Duration: PT1.927410342S
    }

    private static void troubleshooting05() {
        String[] keywords = new String[]{"ba", "d", "ccebc", "ccdbe", "ce"};
        String text = "ecaacceda";
        ACAutomatonVerifier sol = new ACAutomatonVerifier(keywords);
        sol.runAllVersions("test 05: " + text, () -> sol.search(text), Set.of("ce", "d"));
    }

    private static void troubleshooting04() {
        String[] keywords = new String[]{"bc", "db", "beaa", "aab", "e"};
        String text = "cbe";
        ACAutomatonVerifier sol = new ACAutomatonVerifier(keywords);
        sol.runAllVersions("test 04: " + text, () -> sol.search(text), Set.of("e"));
    }

    private static void troubleshooting03() {
        String[] keywords = new String[]{"a", "ag", "c", "caa", "gag", "gc", "gca"};
        String text = "gcaa";
        ACAutomatonVerifier sol = new ACAutomatonVerifier(keywords);
        sol.runAllVersions("test 03: " + text, () -> sol.search(text), Set.of("a", "c", "gc", "caa", "gca"));
    }

    private static void troubleshooting02() {
        String[] keywords = new String[]{"a", "ab"};
        String text = "abccab";
        ACAutomatonVerifier sol = new ACAutomatonVerifier(keywords);
        sol.runAllVersions("test 02: " + text, () -> sol.search(text), Set.of("a", "ab"));
    }

    private static void troubleshooting01() {
        String[] keywords = new String[]{"a", "ab", "bab", "bc", "bca", "c", "caa"};
        String text = "abccab";
        ACAutomatonVerifier sol = new ACAutomatonVerifier(keywords);
        sol.runAllVersions("test 01: " + text, () -> sol.search(text), Set.of("a", "ab", "bc", "c"));
    }





    private record SearchViaSystemStrIndexOf(String[] keywords) implements IACAutomaton {

        @Override
            public Set<String> search(String text) {
                Set<String> ret = new HashSet<>();
                for (String keyword : keywords) {
                    if (text.contains(keyword)) {
                        ret.add(keyword);
                    }
                }
                return ret;
            }
        }
}
