package edu.itmo.dfs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("DFS: трассировка характерных точек и сравнение с эталонной последовательностью")
class DfsTraceTest {

    private static final class Recorder implements DfsTrace {
        private final List<DfsCheckpoint> hits = new ArrayList<>();

        @Override
        public void hit(DfsCheckpoint checkpoint) {
            hits.add(checkpoint);
        }

        List<DfsCheckpoint> hits() {
            return hits;
        }
    }

    @Test
    @DisplayName("Путь из трёх вершин 0—1—2, старт 0 (эталонная трассировка)")
    void pathThreeVerticesReferenceTrace() {
        UndirectedGraph g = new UndirectedGraph(3);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        DepthFirstSearch dfs = new DepthFirstSearch(g);
        Recorder rec = new Recorder();
        dfs.setTrace(rec);
        dfs.traverseFrom(0);

        List<DfsCheckpoint> expected = List.of(
                DfsCheckpoint.ALGO_START,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.DESCEND_TREE_EDGE,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.SKIP_PARENT,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.DESCEND_TREE_EDGE,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.SKIP_PARENT,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.RETURN_FROM_CHILD,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.RETURN_FROM_CHILD,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.ALGO_DONE
        );
        assertEquals(expected, rec.hits());
    }

    @Test
    @DisplayName("Звезда: центр 0, листья 1,2,3 — порядок соседей по возрастанию")
    void starGraphReferenceTrace() {
        UndirectedGraph g = new UndirectedGraph(4);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        DepthFirstSearch dfs = new DepthFirstSearch(g);
        Recorder rec = new Recorder();
        dfs.setTrace(rec);
        dfs.traverseFrom(0);

        List<DfsCheckpoint> expected = List.of(
                DfsCheckpoint.ALGO_START,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.DESCEND_TREE_EDGE,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.SKIP_PARENT,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.RETURN_FROM_CHILD,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.DESCEND_TREE_EDGE,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.SKIP_PARENT,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.RETURN_FROM_CHILD,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.DESCEND_TREE_EDGE,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.SKIP_PARENT,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.RETURN_FROM_CHILD,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.ALGO_DONE
        );
        assertEquals(expected, rec.hits());
    }

    @Test
    @DisplayName("Треугольник K₃")
    void triangleTriggersBackOrCrossEdge() {
        UndirectedGraph g = new UndirectedGraph(3);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(0, 2);
        DepthFirstSearch dfs = new DepthFirstSearch(g);
        Recorder rec = new Recorder();
        dfs.setTrace(rec);
        dfs.traverseFrom(0);

        List<DfsCheckpoint> expected = List.of(
                DfsCheckpoint.ALGO_START,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.DESCEND_TREE_EDGE,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.SKIP_PARENT,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.DESCEND_TREE_EDGE,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.BACK_OR_CROSS_EDGE,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.SKIP_PARENT,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.RETURN_FROM_CHILD,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.RETURN_FROM_CHILD,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.BACK_OR_CROSS_EDGE,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.ALGO_DONE
        );
        assertEquals(expected, rec.hits());
    }

    @Test
    @DisplayName("Две компоненты связности: второй запуск visit из изолированной вершины")
    void twoComponentsTraverseAllFromFirst() {
        UndirectedGraph g = new UndirectedGraph(3);
        g.addEdge(0, 1);
        DepthFirstSearch dfs = new DepthFirstSearch(g);
        Recorder rec = new Recorder();
        dfs.setTrace(rec);
        dfs.traverseAllComponents(0);

        List<DfsCheckpoint> expected = List.of(
                DfsCheckpoint.ALGO_START,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.DESCEND_TREE_EDGE,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.SKIP_PARENT,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.RETURN_FROM_CHILD,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.ALGO_DONE
        );
        assertEquals(expected, rec.hits());
    }

    @Test
    @DisplayName("Одиночная вершина без рёбер")
    void isolatedVertex() {
        UndirectedGraph g = new UndirectedGraph(1);
        DepthFirstSearch dfs = new DepthFirstSearch(g);
        Recorder rec = new Recorder();
        dfs.setTrace(rec);
        dfs.traverseFrom(0);

        List<DfsCheckpoint> expected = List.of(
                DfsCheckpoint.ALGO_START,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.ALGO_DONE
        );
        assertEquals(expected, rec.hits());
    }

    @Test
    @DisplayName("Граф: отрицательное число вершин — исключение")
    void graphRejectsNegativeSize() {
        assertThrows(IllegalArgumentException.class, () -> new UndirectedGraph(-1));
    }

    @Test
    @DisplayName("Ребро: неверный индекс вершины")
    void graphRejectsBadVertex() {
        UndirectedGraph g = new UndirectedGraph(2);
        assertThrows(IllegalArgumentException.class, () -> g.addEdge(0, 5));
    }

    @Test
    @DisplayName("Ребро: отрицательный индекс вершины")
    void graphRejectsNegativeVertexIndex() {
        UndirectedGraph g = new UndirectedGraph(2);
        assertThrows(IllegalArgumentException.class, () -> g.addEdge(-1, 0));
    }

    @Test
    @DisplayName("Петля на вершине не добавляет смежность к самой себе")
    void selfLoopIgnored() {
        UndirectedGraph g = new UndirectedGraph(1);
        g.addEdge(0, 0);
        DepthFirstSearch dfs = new DepthFirstSearch(g);
        Recorder rec = new Recorder();
        dfs.setTrace(rec);
        dfs.traverseFrom(0);
        assertEquals(5, rec.hits().size());
    }

    @Test
    @DisplayName("Повторное добавление того же ребра не дублирует соседа")
    void duplicateEdgeIdempotent() {
        UndirectedGraph g = new UndirectedGraph(2);
        g.addEdge(0, 1);
        g.addEdge(0, 1);
        DepthFirstSearch dfs = new DepthFirstSearch(g);
        Recorder rec = new Recorder();
        dfs.setTrace(rec);
        dfs.traverseFrom(0);
        List<DfsCheckpoint> expected = List.of(
                DfsCheckpoint.ALGO_START,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.DESCEND_TREE_EDGE,
                DfsCheckpoint.VISIT_ENTER,
                DfsCheckpoint.VISIT_MARKED,
                DfsCheckpoint.NEIGHBOR_ITER,
                DfsCheckpoint.SKIP_PARENT,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.RETURN_FROM_CHILD,
                DfsCheckpoint.VISIT_COMPLETE,
                DfsCheckpoint.ALGO_DONE
        );
        assertEquals(expected, rec.hits());
    }
}
