import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Solution {
  class Path {
    Path(int val) {
      this.val = val;
      next = new HashMap<>();
    }

    int val;
    Map<String, Path> next;
    Consumer<Path> watcher;
  }

  Path root;

  public Solution() {
    root = new Path(0);
  }

  public void create(String path, int val) throws FileNotFoundException {
    path = process(path);
    Path p = walk(path, true);
    p.val = val;
  }

  public int get(String path) throws FileNotFoundException {
    path = process(path);
    Path p = walk(path, false);
    return p.val;
  }

  private String process(String path) {
    if (path.equals("/")) return "";
    return path;
  }

  private Path walk(String path, boolean createIfMissing) throws FileNotFoundException {
    if (path.equals("")) return root;
    Path p = root;
    int curr = 1;
    while (curr < path.length()) {
      if (createIfMissing && p.watcher != null) p.watcher.accept(p);
      int next = path.indexOf('/', curr);
      if (next < 0) next = path.length();
      String name = path.substring(curr, next);
      if (!p.next.containsKey(name)) {
        if (!createIfMissing) throw new FileNotFoundException(name + " not found on path:" + path);
        p.next.put(name, new Path(0));
      }
      p = p.next.get(name);
      curr = next + 1;
    }
    return p;
  }

  public void watch(String path, Consumer<Path> callback) throws FileNotFoundException {
    path = process(path);
    Path p = walk(path, false);
    p.watcher = callback;
  }

  public static void main(String[] args) throws FileNotFoundException {
    Solution fs = new Solution();
    fs.create("/a", 2);
    fs.watch("/a", (a) -> System.out.println(a.val));
    fs.create("/a/b", 1);
    System.out.println(fs.get("/a/b"));
    fs.create("/a/b/ce", 1);
    fs.get("/a/d");
  }
}
