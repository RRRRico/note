import java.util.*;

class Solution {
  private static final int CS = 26;
  private static final int[][] MOVES = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

  private int m;
  private int n;
  private Map<String, Set<Integer>> map = new HashMap<>();
  private List<String> res = new ArrayList<>();

  class Node {
    Node[] next = new Node[CS];
    String word;
  }

  public List<String> findWords(char[][] board, String[] words) {
    m = board.length;
    List<String> list = new ArrayList<>();
    if (m == 0) return list;
    n = board[0].length;

    Node trie = new Node();
    for (String word : words) insert(trie, word);

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        dfs(board, i, j, trie, list);
      }
    }

    fill(board, list, 0, new ArrayList<>());
    return res;
  }

  private int idx(int i, int j) {
    return i * n + j;
  }

  private void dfs(char[][] board, int i, int j, Node curr, List<String> list) {
    char c = board[i][j];
    if (curr.next[c - 'a'] == null) return;

    board[i][j] = 0;
    curr = curr.next[c - 'a'];

    if (curr.word != null) {
      list.add(curr.word);
      Set<Integer> set = map.getOrDefault(curr.word, new HashSet<>());
      set.add(idx(i, j));
      map.put(curr.word, set);
    }

    for (int[] move : MOVES) {
      int x = i + move[0];
      int y = j + move[1];

      if (x >= 0 && x < m && y >= 0 && y < n && board[x][y] > 0) {
        dfs(board, x, y, curr, list);
      }
    }

    board[i][j] = c;
  }

  private void insert(Node root, String word) {
    Node curr = root;

    for (int i = 0; i < word.length(); i++) {
      int idx = word.charAt(i) - 'a';

      if (curr.next[idx] == null) curr.next[idx] = new Node();
      curr = curr.next[idx];
    }
    curr.word = word;
  }

  public void fill(char[][] board, List<String> list, int i, List<String> curr) {
    if (res.size() > list.size() - i + curr.size()) return;

    String word = list.get(i);
    for (int idx : map.get(word)) {
      int x = idx / n;
      int y = idx % n;
      bt(board, word, word.length() - 1, x, y, list, i, curr);
    }
  }

  public void bt(
      char[][] board,
      String word,
      int k,
      int x,
      int y,
      List<String> list,
      int i,
      List<String> curr) {
    if (k == 0) {
      curr.add(word);
      if (res.size() < curr.size()) res = new ArrayList<>(curr);
      fill(board, list, i + 1, curr);
      curr.remove(curr.size() - 1);
    }

    char c = board[x][y];
    board[x][y] = 0;

    for (int[] move : MOVES) {
      int a = x + move[0];
      int b = y + move[1];

      if (a >= 0 && a < m && b >= 0 && b < n && k > 0 && board[a][b] == word.charAt(k - 1)) {
        bt(board, word, k - 1, a, b, list, i, curr);
      }
    }
    board[x][y] = c;
  }

  public static void main(String[] args) {
    char[][] mtx = {
      {'c', 'x', 'b', 'd'},
      {'d', 'a', 'b', 'c'},
      {'y', 'o', 'd', 'e'},
      {'z', 'g', 'h', 'i'},
    };
    String[] words = {"bbd", "dce", "ace", "res", "abc"};
    Solution solution = new Solution();
    System.out.println(solution.findWords(mtx, words));
  }
}
