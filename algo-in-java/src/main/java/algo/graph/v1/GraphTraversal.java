package algo.graph.v1;

import java.util.*;

public class GraphTraversal {
    public List<Integer> dfs(Graph graph, int start) {
        return dfs_v1_recursive(graph, start);
    }

    public List<Integer> bfs(Graph graph, int start) {
        return bfs_v1(graph, start);
    }

    List<Integer> dfs_v2_stack(Graph graph, int start) {
        Node startNode = graph.getNode(start);
        if (startNode == null) {
            return Collections.emptyList();
        }
        List<Integer> ret = new ArrayList<>(graph.size());
        Stack<Node> stack = new Stack<>();
        stack.push(startNode);
        HashSet<Node> visited = new HashSet<>(graph.size());
		while (!stack.isEmpty()) {
			Node curr = stack.pop();
			if (!visited.contains(curr)) {
				ret.add(curr.getVal());
				visited.add(curr);
			}
			List<Node> nextNodes = curr.getUnmodifiableNextNodes();
			for (Node nextNode: nextNodes) {
				if (!visited.contains(nextNode)) {	// very important to avoid infinite loop
					stack.push(curr);
					stack.push(nextNode);
					break;			// very important to avoid trapping into bfs
				}
			}
		}

        return ret;
    }

    List<Integer> dfs_v1_recursive(Graph graph, int start) {
        Node startNode = graph.getNode(start);
        if (startNode == null) {
            return Collections.emptyList();
        }
        List<Integer> ret = new ArrayList<>(graph.size());
        doDfs_v1(ret, startNode, new HashSet<Node>());
        return ret;
    }

    private void doDfs_v1(List<Integer> holder, Node node, HashSet<Node> visited) {
        if (visited.contains(node)) {
            return;
        }
        visited.add(node);
        holder.add(node.getVal());
        List<Node> nextNodes = node.getUnmodifiableNextNodes();
		for (Node nextNode : nextNodes) {
			doDfs_v1(holder, nextNode, visited);
		}
    }

    private List<Integer> bfs_v1(Graph graph, int start) {
        Node startNode = graph.getNode(start);
        if (startNode == null) {
            return Collections.emptyList();
        }
        List<Integer> ret = new ArrayList<>(graph.size());
		Set<Node> visited = new HashSet<>();
		Queue<Node> queue = new ArrayDeque<>();
		queue.offer(startNode);
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (!visited.contains(node)) {
				ret.add(node.getVal());
                visited.add(node);
				List<Node> nextNodes = node.getUnmodifiableNextNodes();
				for (Node nextNode: nextNodes) {
					queue.add(nextNode);
				}
			}
		}
		return ret;
    }
}
