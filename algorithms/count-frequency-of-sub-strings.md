---
description: 'Given a string, count frequency of all sub-strings.'
---

# Count Frequency of Sub-Strings

### Example

| Input | Output |
| :--- | :--- |
| aaa | 4 |
| abcabc | 6 |
| ieooieoogg | 15 |
| nookznzkkn | 8 |

### Solution

#### Dynamic Programming

`[i, j]` track the longest length of suffix ending at `i` which is same as suffix ending at `j` .

State transition function `[i, j] = [i] == [j] ? [i - 1, j - 1] + 1 : 0`

Thus, space can be optimized to `O(n)` while time complexity be `O(n^2)`

```java
public static int menu(String str) {
  int len = str.length();
  int[] arr = new int[len];

  int count = 0;
  for (int i = 0; i < len; i++) {
    int prev = arr[i];
    arr[i] = 0;  // set [i, i] to zero
    for (int j = i + 1; j < len; j++) {
      int curr = arr[j];
      if (str.charAt(i) == str.charAt(j)) {
        arr[j] = prev + 1;
        count += arr[j]; // for every substring ending at i, j
      } else arr[j] = 0;
      prev = curr;
    }
  }
  return count;
}

```

#### Suffix Trie

Build a suffix trie with compression to save memory

```java
class Node {
  int k; // the length of the compressed string
  Node[] children = new Node[Solution.SIZE];
  int count; // times of node visited

  Node(int k) {
    this.k = k;
    count = 1;
  }
}
// insert each substring
private static int menu(String str) {
  Node root = new Node(-1);
  root.count = 0;
  int total = 0;
  for (int i = 0; i < str.length(); i++) {
    total += insert(str, i, root);
  }
  return total;
}

private static int insert(String str, int i, Node node) {
  int delta = 0;
  Node curr = node;
  while (i < str.length()) {
    int m = index(str, i);

    if (curr.children[m] == null) {
      // same as the character of compressed string
      if (curr.k > 0 && curr.k < str.length() && index(str, curr.k) == m) {
        curr.children[m] = new Node(curr.k + 1);
        curr.k = -1;
      } else {  // completely different substring
        curr.children[m] = new Node(i + 1);
        return delta;
      }
    }
    curr = curr.children[m];
    delta += curr.count;
    curr.count++;
    i++;
  }
  return delta;
}

private static int index(String str, int i) {
  return str.charAt(i) - 'a';
}
```

