package edu.itmo.dfs;

@FunctionalInterface
public interface DfsTrace {
    void hit(DfsCheckpoint checkpoint);
}
