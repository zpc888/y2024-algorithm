package algo.sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TrieTree_Or_PrefixTree {
    private static class TrieNode {
        private final TrieNode[] children = new TrieNode[26];
        private int passCount = 0;
        private int endCount = 0;
    }

    private static class TrieNodeWithPrefix {
        TrieNode node;
        String prefix;

        public TrieNodeWithPrefix(TrieNode node, String prefix) {
            this.node = node;
            this.prefix = prefix;
        }
    }

    private TrieNode root;

    public TrieTree_Or_PrefixTree() {
        this.root = new TrieNode();
    }

    public List<String> listAllWords_DepthFirst() {
        List<String> ret = new ArrayList<>(32);
        for (int i = 0; i < root.children.length; i++) {
            collectInDepthFirst(ret, "", i, root.children[i]);
        }
        return ret;
    }

    private void collectInDepthFirst(List<String> holder, String prefix, int idx, TrieNode node) {
        if (node == null) {
            return;
        }
        String newPrefix = prefix + ((char)('a' + idx));
        for (int i = 0; i < node.endCount; i++) {
            holder.add(newPrefix);
        }
        for (int i = 0; i < node.children.length; i++) {
            collectInDepthFirst(holder, newPrefix, i, node.children[i]);
        }
    }

    public List<String> listAllWords_BreadthFirst() {
        List<String> ret = new ArrayList<>(32);
        Queue<TrieNodeWithPrefix> queue = new LinkedList<TrieNodeWithPrefix>();
        queue.offer(new TrieNodeWithPrefix(root, ""));
        while (!queue.isEmpty()) {
            TrieNodeWithPrefix wrapper = queue.poll();
            for (int i = 0; i < 26; i++) {
                if (wrapper.node.children[i] != null) {
                    String newPrefix = wrapper.prefix + ((char)('a' + i));
                    for (int j = 0; j < wrapper.node.children[i].endCount; j++) {
                        ret.add(newPrefix);
                    }
                    queue.offer(new TrieNodeWithPrefix(wrapper.node.children[i], newPrefix));
                }
            }
        }
        return ret;
    }

    public void addWord(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        TrieNode cur = root;
        cur.passCount++;
        for (int i = 0; i < word.length(); i++) {
            int childIdx = word.charAt(i) - 'a';
            if (cur.children[childIdx] == null) {
                cur.children[childIdx] = new TrieNode();
            }
            cur = cur.children[childIdx];
            cur.passCount++;
        }
        cur.endCount++;
    }

    public boolean deleteWord(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        if (!containsWord(word)) {
            return false;
        }
        TrieNode cur = root;
        cur.passCount--;
        for (int i = 0; i < word.length(); i++) {
            int childIdx = word.charAt(i) - 'a';
            cur.children[childIdx].passCount--;
            if (cur.children[childIdx].passCount == 0) {
                cur.children[childIdx] = null;
                return true;
            } else {
                cur = cur.children[childIdx];
            }
        }
        cur.endCount--;
        return true;
    }

    public boolean containsWord(String word) {
        return contains(word, false);
    }

    public boolean containsPrefix(String word) {
        return contains(word, true);
    }

    private boolean contains(String word, boolean containsPrefix) {
        if (word == null || word.length() == 0) {
            return false;
        }
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            int childIdx = word.charAt(i) - 'a';
            if (cur.children[childIdx] == null) {
                return false;
            }
            cur = cur.children[childIdx];
        }
        return containsPrefix || cur.endCount > 0;
    }

    public static void main(String[] args) {
        TrieTree_Or_PrefixTree trie = new TrieTree_Or_PrefixTree();
        boolean bool = false;
        trie.addWord("abc");
        trie.addWord("afg");
        trie.addWord("bfg");
        trie.addWord("abcd");
        trie.addWord("abme");
        trie.addWord("abc");
        assertTrue(trie.containsWord("abc"), "should contains word 'abc'");
        assertTrue(trie.containsWord("abc"), "should contains prefix 'abc'");
        assertFalse(trie.containsWord("abf"), "should not contains word 'abf'");
        assertFalse(trie.containsWord("abm"), "should not contains word 'abm'");
        assertTrue(trie.containsPrefix("abm"), "should contains prefix 'abm'");
        System.out.println(trie.listAllWords_DepthFirst());
        System.out.println(trie.listAllWords_BreadthFirst());
        System.out.println("================ delete afg ================");
        assertTrue(trie.deleteWord("afg"), "should delete word 'afg' ok");
        assertFalse(trie.deleteWord("afg"), "should delete word 'afg' failed");
        assertFalse(trie.containsPrefix("afg"), "should not contains prefix 'abm'");
        assertFalse(trie.listAllWords_DepthFirst().contains("afg"), "should not contains 'afg'");
        System.out.println(trie.listAllWords_BreadthFirst());
        System.out.println("================ delete abc 1st time ================");
        assertTrue(trie.deleteWord("abc"), "should delete word 'abc' 1st ok");
        assertTrue(trie.listAllWords_DepthFirst().contains("abc"), "should contains 'abc'");
        System.out.println(trie.listAllWords_BreadthFirst());
        System.out.println("================ delete abc 2nd time ================");
        assertTrue(trie.deleteWord("abc"), "should delete word 'abc' 2nd ok");
        System.out.println(trie.listAllWords_BreadthFirst());
        assertFalse(trie.listAllWords_DepthFirst().contains("abc"), "should not contains 'abc'");
        assertFalse(trie.deleteWord("abc"), "should delete word 'abc' 3nd failed");
        assertFalse(trie.listAllWords_DepthFirst().contains("abc"), "should not contains 'abc'");
        assertTrue(trie.listAllWords_BreadthFirst().size() == 3, "should have 3 words left");
        System.out.println("================ delete all words ================");
        assertTrue(trie.deleteWord("bfg"), "should delete word 'bfg' 1st ok");
        assertTrue(trie.deleteWord("abcd"), "should delete word 'abcd' 1st ok");
        assertTrue(trie.deleteWord("abme"), "should delete word 'abme' 1st ok");
        assertTrue(trie.listAllWords_BreadthFirst().size() == 0, "should have 0 word left");
    }

    private static void assertTrue(boolean bool, String msg) {
        if (!bool) {
            throw new RuntimeException(msg);
        }
    }

    private static void assertFalse(boolean bool, String msg) {
        if (bool) {
            throw new RuntimeException(msg);
        }
    }
}