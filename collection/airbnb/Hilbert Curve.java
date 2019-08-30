class Solution {
  public static int hilbertCurve(int x, int y, int i) {
    if (i == 0) return 1;
    int l = 1 << (i - 1);

    if (x < l && y < l) {
      return hilbertCurve(y, x, i - 1);
    } else if (x >= l && y < l) {
      return hilbertCurve(l - 1 - y, 2 * l - 1 - x, i - 1) + 3 * l * l;
    } else if (x < l && y >= l) {
      return hilbertCurve(x, y - l, i - 1) + l * l;
    } else {
      return hilbertCurve(x - l, y - l, i - 1) + l * l * 2;
    }
  }

  public static void main(String... args) {
    System.out.println(hilbertCurve(1, 1, 1));
    System.out.println(hilbertCurve(7, 0, 3));
  }
}
