# Detect Cycle in a Partial Graph

## Problem statement

In a graph which some of edges are directed and some are undirected, determine if this graph can be made acyclic after give all undirected graph a direction.

If there's such a graph exists, return the topological sort of the graph in minimum *__lexicographical__* order.

## Solution

### Undirected edges are useless

Every acyclic graph can generate a topological sorted order. After that, if every undirected edge added into the graph follow the topological order, the graph remains acyclic.

### Prirority Queue to extract the minimum number

The ways to perform topological sort

1. DFS with back edge checking
2. Leaf extraction

```java
// heap + BFS, O(nlog(n))
private static List<Integer> sort(List<Set<Integer>> graph, int[] degree) {
  PriorityQueue<Integer> queue = new PriorityQueue<>();
  List<Integer> list = new ArrayList<>();

  for (int i = 0; i < graph.size(); i++) {
    if (degree[i] == 0) queue.offer(i);
  }

  while (!queue.isEmpty()) {
    int v = queue.poll();
    list.add(v);
    for (int u : graph.get(v)) {
      if (--degree[u] == 0) queue.offer(u);
    }
  }
  return list.size() == graph.size() ? list : Collections.emptyList();
}
```
