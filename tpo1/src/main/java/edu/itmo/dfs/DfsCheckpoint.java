package edu.itmo.dfs;

public enum DfsCheckpoint {
    ALGO_START,
    VISIT_ENTER,
    VISIT_MARKED,
    NEIGHBOR_ITER,
    SKIP_PARENT,
    DESCEND_TREE_EDGE,
    RETURN_FROM_CHILD,
    BACK_OR_CROSS_EDGE,
    VISIT_COMPLETE,
    ALGO_DONE
}
