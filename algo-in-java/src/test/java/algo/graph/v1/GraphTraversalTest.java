package algo.graph.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTraversalTest {
    private Graph graph;
    private List<Integer> expectedBfs;
    private List<Integer> expectedDfs;

    @BeforeEach
    void setUp() {
        /*
            * Graph:
            * 0 -> 1 -> 3 -> 7 -> 11
            *  \   \    \
            *   \   \--> 4 -> 8
            *    \
            *     \-> 2 -> 5 -> 9
            *          \
            *           \-> 6 -> 10
            *
         */

        graph = Graph.create(new int[][]{
                {0, 1, 1},
                {0, 2, 1},
                {1, 3, 1},
                {1, 4, 1},
                {2, 5, 1},
                {2, 6, 1},
                {3, 7, 1},
                {3, 4, 1},
                {4, 8, 1},
                {5, 9, 1},
                {6, 10, 1},
                {7, 11, 1},
        });
        expectedBfs = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        expectedDfs = List.of(0, 1, 3, 7, 11, 4, 8, 2, 5, 9, 6, 10);
    }

    @Test
    void testBfs() {
        GraphTraversal traversal = new GraphTraversal();
        List<Integer> actual = traversal.bfs(graph, 0);
        assertEquals(expectedBfs, actual);
    }

    @Test
    void testDfs() {
        GraphTraversal traversal = new GraphTraversal();
        List<Integer> actual = traversal.dfs(graph, 0);
        assertEquals(expectedDfs, actual);
    }

    @Test
    void testDfs_InStackApproach() {
        GraphTraversal traversal = new GraphTraversal();
        List<Integer> actual = traversal.dfs_v2_stack(graph, 0);
        assertEquals(expectedDfs, actual);
    }
}