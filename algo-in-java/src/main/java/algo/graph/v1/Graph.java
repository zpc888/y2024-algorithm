package algo.graph.v1;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    private final Map<Integer, Node> nodes;

    public Graph() {
        this(32);
    }

    public Graph(int initCapacity) {
        this.nodes = new HashMap<>(initCapacity);
    }

    // Create a graph from a 2D array of edges, where each edge is represented by an array of 3 integers: [from, to, weight]
    public static Graph create(int[][] edges) {
        Graph graph = new Graph(edges.length * 2);
        for (int[] edge : edges) {
            graph.addEdge(edge[0], edge[1], edge[2]);
        }
        return graph;
    }

    public Graph addEdge(int from, int to, int weight) {
        Node fromNode = nodes.computeIfAbsent(from, Node::new);
        Node toNode = nodes.computeIfAbsent(to, Node::new);
        Edge edge = new Edge(fromNode, toNode, weight);
        fromNode.addNextEdge(edge);
        toNode.incrementInwardCount();
        return this;
    }

    public Node getNode(int val) {
        return nodes.get(val);
    }

    public int size() {
        return nodes.size();
    }
}
