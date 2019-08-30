package us.com.samsung.wcms.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class Vector2D {
  Iterator<List<Integer>> row;
  Iterator<Integer> col;

  public Vector2D(List<List<Integer>> list) {
    row = list.iterator();
    col = Collections.emptyIterator();
  }

  public int next() {
    if (hasNext()) return col.next();
    throw new IllegalStateException("No element found.");
  }

  public boolean hasNext() {
    while (!col.hasNext() && row.hasNext()) {
      List<Integer> temp = row.next();
      while (temp == null && row.hasNext()) temp = row.next();
      if (temp == null) return false;
      col = temp.iterator();
    }
    return col.hasNext();
  }

  private void remove() {
    col.remove();
  }

  public static void main(String[] args) {
    List<List<Integer>> list = new ArrayList<>();
    List<Integer> l1 = null;

    List<Integer> l2 = new ArrayList<>();
    l2.add(1);
    l2.add(2);
    list.add(Collections.emptyList());
    list.add(l1);
    list.add(l2);

    Vector2D vector2D = new Vector2D(list);
    vector2D.next();
    vector2D.next();
    vector2D.remove();

    System.out.println(l1);
    System.out.println(l2);
  }
}
