import java.util.*;
class Solution {
  Map<Integer, Integer> map = new HashMap<>();
  public int cost(List<List<Integer>> wizards, int src, int dst) {
    int[] dist = new int[wizards.size()];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;

    PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(a -> dist[a]));
    queue.add(src);

    while (!queue.isEmpty()) {
      int u = queue.poll();

      if (u == dst) return dist[dst];

      for (int v : wizards.get(u)) {
        int d = dist(u, v);
        if (dist[v] > dist[u] + d) {
          dist[v] = dist[u] + d;
          queue.remove(v);
          queue.add(v);
          map.put(v, u);
        }
      }
    }
    return dist[dst];
  }

  private int dist(int a, int b) {
    return (a - b) * (a - b);
  }

  public static void main(String[] args) {
    List<List<Integer>> wizards = Arrays.asList(
      Arrays.asList(1, 5, 9),  //0
      Arrays.asList(2, 3, 9),  //1
      Arrays.asList(4),        //2
      Arrays.asList(5, 7, 8),  //3
      Arrays.asList(6, 7),     //4
      Arrays.asList(),         //5
      Arrays.asList(8),        //6
      Arrays.asList(9),        //7
      Arrays.asList(9),        //8
      Arrays.asList()          //9
      );
    Solution sol = new Solution();
    int res = sol.cost(wizards, 0, 9);
    System.out.println(res + "," + sol.map);
  }
}
