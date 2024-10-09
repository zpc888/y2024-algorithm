package algo.graph.v1;

import java.util.Collections;
import java.util.List;

public class Node {
    private final int val;
    private List<Node> nextNodes;
    private List<Edge> nextEdges;
    private int inCount;

    public Node(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    public List<Node> getUnmodifiableNextNodes() {
        return nextNodes == null ? Collections.emptyList() : Collections.unmodifiableList(nextNodes);
    }

    public List<Edge> getUnmodifiableNextEdges() {
        return nextEdges == null ? Collections.emptyList() : Collections.unmodifiableList(nextEdges);
    }

    public void incrementInwardCount() {
        inCount++;
    }

    public int getInwardCount() {
        return inCount;
    }

    public int getOutwardCount() {
        return nextNodes == null ? 0 : nextNodes.size();
    }

    public void addNextEdge(Edge edge) {
        if (nextEdges == null) {
            nextEdges = new java.util.ArrayList<>(16);
            nextNodes = new java.util.ArrayList<>(16);
        }
        nextEdges.add(edge);
        nextNodes.add(edge.getTo());
    }
}
