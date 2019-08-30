import java.util.*;

class Solution {

  private static final int UNVISIT = 0;
  private static final int VISIT = 1;
  private static final int FINISH = 2;

  public Set<Integer> traverse(int[][] edges, int len) {
    // components
    List<Integer> roots = new ArrayList<>();
    int[] scc = kosaraju(edges, len, roots);
    print(scc);

    Set<Integer> set = new HashSet<>(roots);
    // remove components with inbound edge
    for (int[] edge : edges) {
      int src = scc[edge[0]];
      int tgt = scc[edge[1]];
      if (src != tgt) set.remove(tgt);
    }

    return set;
  }

  public int[] kosaraju(int[][] edges, int len, List<Integer> roots) {
    List<List<Integer>> graph = build(edges, len, false);
    List<List<Integer>> trans = build(edges, len, true);

    int[] visited = new int[len];
    Deque<Integer> stack = new ArrayDeque<>();

    // sort vertices by finish-time
    for (int i = 0; i < len; i++) {
      if (visited[i] == UNVISIT) dfs(graph, i, visited, stack);
    }
    // reset vertices
    Arrays.fill(visited, UNVISIT);
    // extract scc by dfs on transposed graph
    int[] scc = new int[len];
    while (!stack.isEmpty()) {
      int u = stack.pop();
      if (visited[u] == UNVISIT) {
        dfs(trans, u, u, visited, scc);
        roots.add(u);
      }
    }
    return scc;
  }

  private void dfs(List<List<Integer>> graph, int u, int low, int[] visited, int[] scc) {
    visited[u] = VISIT;
    // set low-link on current vertex
    scc[u] = low;

    for (int v : graph.get(u)) {
      if (visited[v] == UNVISIT) {
        dfs(graph, v, low, visited, scc);
      }
    }
    visited[u] = FINISH;
  }

  private void dfs(List<List<Integer>> graph, int u, int[] visited, Deque<Integer> stack) {
    visited[u] = VISIT;

    for (int v : graph.get(u)) {
      if (visited[v] == UNVISIT) {
        dfs(graph, v, visited, stack);
      }
    }
    // add vertex into finish time stack
    stack.push(u);
    visited[u] = FINISH;
  }

  private List<List<Integer>> build(int[][] edges, int len, boolean transpose) {
    List<List<Integer>> graph = new ArrayList<>(len);

    for (int i = 0; i < len; i++) {
      graph.add(new ArrayList<>());
    }

    for (int[] edge : edges) {
      if (transpose) graph.get(edge[1]).add(edge[0]);
      else graph.get(edge[0]).add(edge[1]);
    }
    return graph;
  }

  public static void main(String... args) {
    int[][] edges = {
      {3, 4},
      {3, 7},
      {4, 5},
      {6, 4},
      {6, 2},
      {6, 0},
      {2, 0},
      {7, 3},
      {7, 5},
      {5, 6},
      {5, 0},
      {0, 1},
      {1, 2},
      {8, 9}
    };
    Solution sol = new Solution();
    System.out.println(sol.traverse(edges, 10));
  }

  private void print(int[] arr) {
    for (int i : arr) System.out.print(i + ",");
    System.out.println();
  }
}
