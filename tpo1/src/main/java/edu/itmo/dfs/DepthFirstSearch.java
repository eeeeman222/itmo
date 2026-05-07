package edu.itmo.dfs;

public final class DepthFirstSearch {

    private final UndirectedGraph graph;
    private DfsTrace trace;

    public DepthFirstSearch(UndirectedGraph graph) {
        this.graph = graph;
    }

    public void setTrace(DfsTrace trace) {
        this.trace = trace;
    }

    //начинаем с начала
    public void traverseAllComponents(int startVertex) {
        hit(DfsCheckpoint.ALGO_START);
        int n = graph.getVertexCount();
        boolean[] visited = new boolean[n];
        dfsVisit(startVertex, -1, visited);
        for (int v = 0; v < n; v++) {
            if (!visited[v]) {
                dfsVisit(v, -1, visited);
            }
        }
        hit(DfsCheckpoint.ALGO_DONE);
    }

    public void traverseFrom(int startVertex) {
        hit(DfsCheckpoint.ALGO_START);
        boolean[] visited = new boolean[graph.getVertexCount()];
        dfsVisit(startVertex, -1, visited);
        hit(DfsCheckpoint.ALGO_DONE);
    }

    private void dfsVisit(int u, int parent, boolean[] visited) {
        hit(DfsCheckpoint.VISIT_ENTER);
        visited[u] = true;
        hit(DfsCheckpoint.VISIT_MARKED);
        for (int v : graph.neighbors(u)) {
            hit(DfsCheckpoint.NEIGHBOR_ITER);
            if (v == parent) {
                hit(DfsCheckpoint.SKIP_PARENT);
                continue;
            }
            if (!visited[v]) {
                hit(DfsCheckpoint.DESCEND_TREE_EDGE);
                dfsVisit(v, u, visited);
                hit(DfsCheckpoint.RETURN_FROM_CHILD);
            } else {
                hit(DfsCheckpoint.BACK_OR_CROSS_EDGE);
            }
        }
        hit(DfsCheckpoint.VISIT_COMPLETE);
    }

    private void hit(DfsCheckpoint c) {
        if (trace != null) {
            trace.hit(c);
        }
    }
}
