package edu.itmo.dfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Неориентированный граф: списки смежности, соседи упорядочены по возрастанию для воспроизводимой трассировки.
 */
public final class UndirectedGraph {

    private final int vertexCount;
    private final List<List<Integer>> adjacency;

    public UndirectedGraph(int vertexCount) {
        if (vertexCount < 0) {
            throw new IllegalArgumentException("vertexCount must be non-negative");
        }
        this.vertexCount = vertexCount;
        this.adjacency = new ArrayList<>(vertexCount);
        for (int i = 0; i < vertexCount; i++) {
            adjacency.add(new ArrayList<>());
        }
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void addEdge(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        if (u == v) {
            return;
        }
        addHalfEdge(u, v);
        addHalfEdge(v, u);
    }

    private void addHalfEdge(int from, int to) {
        List<Integer> list = adjacency.get(from);
        if (!list.contains(to)) {
            list.add(to);
            Collections.sort(list);
        }
    }

    List<Integer> neighbors(int u) {
        return adjacency.get(u);
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= vertexCount) {
            throw new IllegalArgumentException("vertex out of range: " + v);
        }
    }
}
