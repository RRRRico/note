# KMP Algorithm

the **Knuth–Morris–Pratt** Algorithm searches for occurrences of a "word" `W` within a main "text string" `S` by employing the observation that when a mismatch occurs, the word itself embodies sufficient information to determine where the next match could begin, thus bypassing re-examination of previously matched characters.

```java
public class Solution {
  /**
   * @param source:
   * @param target:
   * @return: return the index
   */
  public int strStr(String source, String target) {
    int ls = source.length();
    int lt = target.length();
    if (lt == 0) return 0;
    if (ls == 0) return -1;


    int i = 0;
    int j = 0;
    int[] arr = pre(target);

    while (i < ls) {
      while (i < ls && j < lt && source.charAt(i) == target.charAt(j)) {
        i++;
        j++;
      }

      if (j == lt) return i - j;
      if (j > 0) j = arr[j - 1];
      else i++;
    }
    return -1;
  }

  private int[] pre(String target) {
    int len = target.length();
    int[] arr = new int[len];

    for (int i = 1; i < len; i++) {
      char c = target.charAt(i);
      int j = arr[i - 1];

      // arr[j] means from [0, j) matching
      // think in dp would make more sense
      while (j > 0 && c != target.charAt(j)) {
        j = arr[j - 1];
      }

      // if match then j + 1
      // otherwise j or just zero
      arr[i] = s.charAt(j) != c ? j : j + 1;
    }
    return arr;
  }
}
```

