package algo.str.ac;

import java.util.*;

public class ACAutomatonInMap implements IACAutomaton {
	public static boolean debugOn = false;
	private static final String ROOT_PATH_STR = "$ROOT";

	private final Node root;
   
	public ACAutomatonInMap(String[] keywords) {
		root = new Node();
		if (debugOn) {
			root.pathStr = ROOT_PATH_STR;
		}
		for (String kw: keywords) {
			Node curr = root;
			char[] chars = kw.toCharArray();
			for (char c: chars) {
				Node kid = curr.getOrCreateChild(c);
				curr = kid;
			}
			curr.setKeyword(kw);
		}
		buildFailLinks();
		buildDictLinks();
	}

	private void buildFailLinks() {
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			Node curr = queue.poll();
			for (Character c: curr.getChildChars()) {
				Node kid = curr.getChild(c);
				if (curr == root) {
					kid.setFailLink(root);
				} else {
					Node follow = curr.getFailLink();
					while (follow != null && !follow.hasChild(c)) {
						follow = follow.getFailLink();
					}
					kid.setFailLink(follow == null ? root : follow.getChild(c));
				}
				queue.offer(kid);
			}
		}
	}

	private void buildDictLinks() {
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			Node curr = queue.poll();
			for (Character c: curr.getChildChars()) {
				Node kid = curr.getChild(c);
				Node follow = kid.getFailLink();
				while (follow != null && !follow.isKeywordNode()) {
					follow = follow.getFailLink();
				}
				kid.setDictLink(follow); // null or keyword node by following fail-link
				queue.offer(kid);
			}
		}
	}


    @Override
    public Set<String> search(String text) {
		Set<String> ret = new HashSet<>();
		char[] chars = text.toCharArray();
		Node curr = root;
		for (Character c: chars) {
			Node kid = curr.getChild(c);
			while (kid == null && curr != root) {
				curr = curr.getFailLink();
				kid = curr.getChild(c);
			}
			if (kid == null) {		// reach to root
				continue;
			}
			if (kid.isKeywordNode() && !ret.contains(kid.getKeyword())) {
				ret.add(kid.getKeyword());
			}
			Node dict = kid.getDictLink();
			while (dict != null) {
				if (ret.contains(dict.getKeyword())) {
					break;
				}
				ret.add(dict.getKeyword());
				dict = dict.getDictLink();
			}
			curr = kid;
		}
		return ret;
    }

    private static class Node {
		private final Map<Character, Node> kids = new HashMap<>(8);
		private String pathStr;
		private Node failLink;
		private Node dictLink;	// can collect at search time on-demand
								// but build upfront will boost performance
		private String keyword;

		public Node getOrCreateChild(char c) {
			Node kid = kids.get(c);
			if (kid == null) {
				kid = new Node();
				kids.put(c, kid);
				if (debugOn) {
					kid.pathStr = ROOT_PATH_STR.equals(pathStr)
							? "" + c : pathStr + c;
				}
			}
			return kid;
		}

		public boolean hasChild(char c) {
			return kids.get(c) != null;
		}

		public Node getChild(char c) {
			return kids.get(c);
		}

		public Iterable<Character> getChildChars() {
			return kids.keySet();
		}

		@Override
		public String toString() {
			return debugOn ? pathStr : (keyword == null ? 
					"" + System.identityHashCode(this) : keyword);
		}

		public void setFailLink(Node node) {
			failLink = node;
		}

		public void setDictLink(Node node) {
			dictLink = node;
		}

		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}

		public Node getFailLink() {
			return failLink;
		}

		public Node getDictLink() {
			return dictLink;
		}

		public String getKeyword() {
			return keyword;
		}

		public boolean isKeywordNode() {
			return keyword != null;
		}
    }
}
