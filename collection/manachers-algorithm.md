# Manacher's Algorithm

```java
class Solution {
  public String longestPalindrome(String s) {
    int len = 2 * s.length() + 1;
    char[] str = new char[len];

    // for palindrome centered at i, keep track of radius
    // exclude i but include the right boundary
    int[] arr = new int[len];

    for (int i = 0; i < len; i++) {
      if (i % 2 == 1) str[i] = s.charAt(i / 2);
      else str[i] = '|';
    }

    int c = 0;
    int r = 0;
    int mc = 0;
    int mr = 0;

    for (int i = 1; i < len; i++) {
      // mirror
      int j = 2 * c - i;
      // if inside the center palindrome
      if (j >= 0 && i + arr[j] < c + r) arr[i] = arr[j];
      else {
        // get max possible k as i + k cannot exceed right boundary
        int k = j < 0 ? 0 : Math.min(arr[j], c + r - i);
        while (i - k >= 0 && i + k < len && str[i - k] == str[i + k]) k++;
        k--;

        arr[i] = k;
        if (k > mr) {
          mc = i;
          mr = k;
        }
        if (i + k > c + r) {
          c = i;
          r = k;
        }
      }
    }
    // trick and not proved
    return s.substring((mc - mr) / 2, (mc + mr) / 2);
  }
}
```

## Reference

* [Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)

