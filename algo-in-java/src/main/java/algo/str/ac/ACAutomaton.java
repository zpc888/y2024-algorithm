package algo.str.ac;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * <p>
 * Source: https://en.wikipedia.org/wiki/Aho%E2%80%93Corasick_algorithm
 * </p>
 * <p>
 * Source: https://www.youtube.com/watch?v=OFKxWFew_L0
 * </p>
 * <p>
 * Aho-Corasick algorithm is a string searching algorithm that constructs a 
 * finite state machine that can be used to locate all occurrences of a set of
 * keywords in a text in O(N) time where N is the size of the text. 
 * The algorithm is efficient because it preprocesses the keywords 
 * into a trie data structure.
 * </p>
 */
public class ACAutomaton implements IACAutomaton {
	public static final String ROOT_NODE_STR = "$root";
	public static boolean debugOn = false;

	private final Node root;

    public ACAutomaton(String[] keywords) {
		root = new Node();
		if (debugOn) {
			root.pathStr = ROOT_NODE_STR;
		}
		Node parent = null;
		for (String kw: keywords) {
			char[] chs = kw.toCharArray();
			parent = root;
			for (int i = 0; i < chs.length; i++) {
				Node next = parent.getOrCreateNext(chs[i]);
				if (debugOn && next.pathStr == null) {
					next.pathStr = (ROOT_NODE_STR.equals(parent.pathStr) ? "" : parent.pathStr) + chs[i];
				}
				if (i == chs.length - 1) {	// a keyword
					next.setKeyword(kw);
				}
				parent = next;
			}
		}
		buildFailureLinks();		// pre-build max suffix links
		buildDictionaryLinks();		// pre-build once to save n-times for query
    }

	private void buildFailureLinks() {
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(root);
		// root.setFailLink(null);		// root pointing to null by default
		while (!queue.isEmpty()) {
			Node curr = queue.poll();
			Node[] kids = curr.getChildren();
			for (int i = 0; i < kids.length; i++) {
				if (kids[i] == null) {
					continue;
				}
				char c = (char)('a' + i);
				if (curr == root) {
					kids[i].setFailLink(root);    // first level failure to root
				} else {
					Node follow = curr.getFailLink();
					while (follow != null && !follow.hasChildPath(c)) {
						// follow.getChildren()[i] == null
						follow = follow.getFailLink();
					}
					kids[i].setFailLink(follow == null ? root : follow.getNext(c));
				}
				queue.offer(kids[i]);
			}
		}
	}

	private void buildDictionaryLinks() {
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			Node curr = queue.poll();
			Node[] kids = curr.getChildren();
			for (int i = 0; i < kids.length; i++) {
				if (kids[i] == null) {
					continue;
				}
				char c = (char)('a' + i);
				Node follow = kids[i];
				while (follow.getFailLink() != root 
						&& !follow.getFailLink().isWholeKeyword()) {	
					follow = follow.getFailLink();
				}
				kids[i].setDictLink(follow.getFailLink() == root 
						? null : follow.getFailLink());
				queue.offer(kids[i]);
			}
		}
	}

    @Override
    public Set<String> search(String text) {
		Set<String> ret = new HashSet<>();
		char[] str = text.toCharArray();
		Node curr = root;
		for (char c: str) {
			Node kid = curr.getNext(c);
			while (kid == null && curr != root) {	// no path for c
				curr = curr.getFailLink(); // start from last maxlen suffix
				kid = curr.getNext(c);
			}
			if (kid == null) {		// curr is root for sure
				continue;
			}
			if (kid.isWholeKeyword()) {
				if (!ret.contains(kid.getKeyword())) {
					// first time to add
					ret.add(kid.getKeyword());
				}
			}
			Node dictFollow = kid.getDictLink();
			while (dictFollow != null) {
				if (ret.contains(dictFollow.getKeyword())) {
					// add before
					break;
				} else {
					ret.add(dictFollow.getKeyword());
					dictFollow = dictFollow.getDictLink();
				}
			}
			curr = kid;
		}
		return ret;
    }

	private static class Node {
		// assuming a-z, otherwise use Map<Character, Node>
		private final Node[] next = new Node[26];
		private Node failLink;		// or suffix link
		private Node dictLink;		// or Dict suffix link
		private String keyword;		// whole keyword, not prefix segments
		public String pathStr;		// for debugging

		public Node getOrCreateNext(char c) {
			int idx = c - 'a';
			if (next[idx] == null) {
				next[idx] = new Node();
			}
			return next[idx];
		}

		public boolean hasChildPath(char c) {
			return getNext(c) != null;
		}

		public Node getNext(char c) {
			int idx = c - 'a';
			return next[idx];
		}

		@Override
		public String toString() {
			return pathStr == null ? "" + System.identityHashCode(this) : pathStr;
		}

		public Node[] getChildren() {
			return next;
		}

		public boolean isWholeKeyword() {
			return keyword != null;
		}
		
		public void setFailLink(Node node) {
			failLink = node;
		}

		public Node getFailLink() {
			return failLink;
		}

		public void setDictLink(Node node) {
			dictLink = node;
		}

		public Node getDictLink() {
			return dictLink;
		}

		public void setKeyword(String str) {
			keyword = str;
		}

		public String getKeyword() {
			return keyword;
		}
	}
}
