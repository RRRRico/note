import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
  public String[] display(List<String> list, int limit) {
    String[] strs = new String[list.size()];
    Map<Integer, Integer> map = new HashMap<>();
    List<Integer> pages = new ArrayList<>();
    int[] arr = new int[list.size()];

    int curr = 0;
    for (int i = 0; i < list.size(); i++) {
      int id = getId(list.get(i));

      int page = map.containsKey(id) ? map.get(id) + 1 : curr;

      arr[i] = page;
      if (page == pages.size()) pages.add(0);
      pages.set(page, pages.get(page) + 1);

      if (pages.get(curr) == limit) curr++;
      map.put(id, page);
    }

    for (int i = 1; i < pages.size(); i++) {
      pages.set(i, pages.get(i - 1) + pages.get(i));
    }

    for (int i = list.size() - 1; i >= 0; i--) {
      int page = arr[i];
      int length = pages.get(page);

      strs[--length] = list.get(i);
      pages.set(page, length);
    }

    return strs;
  }

  private int getId(String s) {
    return Integer.parseInt(s.split(",")[0]);
  }

  public static void main(String[] args) {
    int PER_PAGE = 12;

    var list =
        List.of(
            "1,28,300.6,San Francisco",
            "4,5,209.1,San Francisco",
            "20,7,203.4,Oakland",
            "6,8,202.9,San Francisco",
            "6,10,199.8,San Francisco",
            "1,16,190.5,San Francisco",
            "6,29,185.3,San Francisco",
            "7,20,180.0,Oakland",
            "6,21,162.2,San Francisco",
            "2,18,161.7,San Jose",
            "2,30,149.8,San Jose",
            "3,76,146.7,San Francisco",
            "2,14,141.8,San Jose");

    ArrayList<String> input = new ArrayList<String>();
    input.add("1,28,300.1,San Francisco");
    input.add("4,5,209.1,San Francisco");
    input.add("20,7,208.1,San Francisco");
    input.add("23,8,207.1,San Francisco");
    input.add("16,10,206.1,Oakland");
    input.add("1,16,205.1,San Francisco");
    input.add("6,29,204.1,San Francisco");
    input.add("7,20,203.1,San Francisco");
    input.add("8,21,202.1,San Francisco");
    input.add("2,18,201.1,San Francisco");
    input.add("2,30,200.1,San Francisco");
    input.add("15,27,109.1,Oakland");
    input.add("10,13,108.1,Oakland");
    input.add("11,26,107.1,Oakland");
    input.add("12,9,106.1,Oakland");
    input.add("13,1,105.1,Oakland");
    input.add("22,17,104.1,Oakland");
    input.add("1,2,103.1,Oakland");
    input.add("28,24,102.1,Oakland");
    input.add("18,14,11.1,San Jose");
    input.add("6,25,10.1,Oakland");
    input.add("19,15,9.1,San Jose");
    input.add("3,19,8.1,San Jose");
    input.add("3,11,7.1,Oakland");
    input.add("27,12,6.1,Oakland");
    input.add("1,3,5.1,Oakland");
    input.add("25,4,4.1,San Jose");
    input.add("5,6,3.1,San Jose");
    input.add("29,22,2.1,San Jose");
    input.add("30,23,1.1,San Jose");

    Solution solution = new Solution();
    String[] output = solution.display(list, PER_PAGE);

    for (String s : output) {
      System.out.println(s);
    }
  }
}
