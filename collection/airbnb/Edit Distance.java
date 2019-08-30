import java.util.ArrayList;
import java.util.List;

public class Solution {
  class Node {
    Node[] children = new Node[26];
    String word;
    char c;
  }
  public List<String> distance(String[] words, String target, int k) {
    Node root = new Node();

    for (String word : words) insert(root, word);
    List<String> list = new ArrayList<>();

    int[] init = new int[target.length() + 1];
    for (int i = 0; i <= target.length(); i++) init[i] = i;

    for (int i = 0; i < 26; i++) {
      if (root.children[i] == null) continue;
      dfs(list, root.children[i], target, init, k);
    }
    return list;
  }

  private void dfs(List<String> list, Node node, String target, int[] prev, int k) {
    System.out.println(node.c);
    System.out.println("before");
    print(prev);
    int len = target.length();

    int[] curr = new int[len + 1];
    curr[0] = prev[0] + 1;

    for (int i = 1; i <= len; i++) {
      curr[i] = Math.min(prev[i], curr[i - 1]) + 1;
      int step = prev[i - 1];
      if (node.c != target.charAt(i - 1)) step++;
      curr[i] = Math.min(curr[i], step);
    }
    System.out.println("after");
    print(curr);
    if (node.word != null && curr[len] <= k) {
      System.out.println(curr[len]);
      list.add(node.word);
    }

    for (int i = 0; i < 26; i++) {
      if (node.children[i] == null) continue;
      dfs(list, node.children[i], target, curr, k);
    }
  }

  private void insert(Node root, String word) {
    Node curr = root;

    for (int i = 0; i < word.length(); i++) {
      int idx = word.charAt(i) - 'a';

      if (curr.children[idx] == null) curr.children[idx] = new Node();
      curr = curr.children[idx];
      curr.c = word.charAt(i);
    }
    curr.word = word;
  }

  public static void main(String[] args) {
    Solution sol = new Solution();
    String[] input = new String[]{"abcd", "abc", "abd", "ad", "c", "cc"};
    String target = "abcd";
    int k = 2;
    List<String> res = sol.distance(input, target, k);
    System.out.println(res);
  }

  private void print(int[] arr) {
    for (int val : arr) System.out.print(val + ",");
    System.out.println();
  }
}
