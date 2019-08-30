# Day-Stout-Warren\(DSW\) Algorithm

The **Day–Stout–Warren \(DSW\) algorithm** is a method for efficiently balancing binary search tree with `O(n)` time complexity and `O(1)` time complexity.

```java
import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

  static class TreeNode {
    /**
     * Internal TreeNode data structure
     *
     * @param val node value, can be satellite data
     */
    TreeNode(int val) {
      this.val = val;
    }

    int val;
    TreeNode left;
    TreeNode right;
  }

  private TreeNode root;

  /**
   * Right Rotate Tree
   *
   * @param x node to rotate
   */
  private static void rightRotate(TreeNode x) {
    var y = x.left;
    var b = x.right;
    if (y != null) {
      var z = y.right;
      var a = y.left;

      // swap y and x
      swap(x, y);

      // put nodes back
      x.left = a;
      x.right = y;
      y.left = z;
      y.right = b;
    }
  }

  /**
   * Left Rotate Tree
   *
   * @param y node to rotate left
   */
  private static void leftRotate(TreeNode y) {
    var a = y.left;
    var x = y.right;

    if (x != null) {
      var z = x.left;
      var b = x.right;

      swap(x, y);

      y.right = b;
      y.left = x;
      x.right = z;
      x.left = a;
    }
  }

  /**
   * Swap node values, can be satellite data for better extensibility
   *
   * @param x node x
   * @param y node y
   */
  private static void swap(TreeNode x, TreeNode y) {
    int temp = x.val;
    x.val = y.val;
    y.val = temp;
  }

  /**
   * Construct Binary Tree from sorted array
   *
   * @param vals sorted array
   * @return binary search tree
   */
  private static BinaryTree from(int[] vals) {
    var node = build(vals, 0, vals.length - 1);
    var binaryTree = new BinaryTree();
    binaryTree.root = node;
    return binaryTree;
  }

  /**
   * in-order print
   *
   * @param node root node
   */
  private static void print(TreeNode node) {
    if (node == null) return;
    print(node.left);
    System.out.println(node.val);
    print(node.right);
  }

  /**
   * Serialize Tree
   *
   * @return String representation of tree nodes
   */
  public String serialize() {
    var list = new ArrayList<String>();
    serialize(root, list);
    return String.join(",", list);
  }

  /**
   * pre-order serialization
   *
   * @param node root node
   * @param list result container
   */
  private static void serialize(TreeNode node, List<String> list) {
    if (node == null) list.add("null");
    else {
      list.add(String.valueOf(node.val));
      serialize(node.left, list);
      serialize(node.right, list);
    }
  }

  /** in-order print */
  public void print() {
    print(root);
  }

  private static TreeNode build(int[] vals, int i, int j) {
    if (i > j) return null;
    int m = (i + j) >> 1;
    var node = new TreeNode(vals[m]);
    TreeNode l = build(vals, i, m - 1);
    TreeNode r = build(vals, m + 1, j);

    node.left = l;
    node.right = r;

    return node;
  }

  /**
   * Convert a binary tree to a linked-list-like super unbalanced tree
   *
   * @param root root node
   * @return size of tree
   */
  public static int flatten(TreeNode root) {
    var curr = root;
    int cnt = 0;
    while (curr != null) {
      cnt++;
      while (curr.left != null) rightRotate(curr);
      curr = curr.right;
    }
    return cnt;
  }

  public static TreeNode balance(TreeNode node, int size) {
    TreeNode sen = new TreeNode(0);
    sen.right = node;

    // rotate all leaves
    int leaves = bottom(size);
    compress(sen.right, leaves);

    // now total should be 2^n - 1
    // make the rest tree a complete binary tree
    int total = size - leaves;
    while (total > 1) {
      // get count for current level of nodes
      int cnt = total - total / 2;
      // compress cnt - 1 times to fix the over rotation
      compress(sen.right, cnt - 1);
      total >>= 1;
    }
    return sen.right;
  }

  /**
   * Compress the tree by left rotate nodes
   *
   * @param node root node to rotate
   * @param times number of rotations
   */
  private static void compress(TreeNode node, int times) {
    while (times > 0) {
      leftRotate(node);
      node = node.right;
      times--;
    }
  }

  /**
   * Calculate the number of bottom leaves If bottom level is a complete level, 0 would be returned
   *
   * @param size tree size
   * @return bottom count
   */
  private static int bottom(int size) {
    int sum = 1;
    int next = 2;

    while (sum + next <= size) {
      sum += next;
      next <<= 1;
    }
    return size - sum;
  }

  /** public method for dsw algorithm */
  public void dsw() {
    int size = flatten(root);
    balance(root, size);
  }

  public static void main(String[] args) {
    var vals = new int[] {1, 3, 4, 6, 8, 9, 11, 12};
    var binaryTree = from(vals);
    // balanced form
    System.out.println(binaryTree.serialize());

    // unbalanced form
    int size = flatten(binaryTree.root);
    System.out.println(binaryTree.serialize());

    balance(binaryTree.root, size);
    System.out.println(binaryTree.serialize());
  }
}
```

### highlights

* Left and right rotation implemented with swap value allow no parent reference in `TreeNode` class.
* In the pre-process method `curr` pointer visited all nodes, which calculate the total size of tree.
* Every time `compress` function is called, one level of nodes are build from bottom up.
* The algorithm itself!

## References

* [wikipedia](https://en.wikipedia.org/wiki/Day–Stout–Warren_algorithm)
* [Explanation in Detail](http://www.smunlisted.com/day-stout-warren-dsw-algorithm.html)

  This article provide visualized and detailed explanation how DSW algorithm works, however, there's a over rotation in the last step\(Node 30\). Fix available at line 180.

