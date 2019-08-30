import java.util.*;

class Solution {

  private static String[] pagination(String[] listings, int size) {
    int len = listings.length;
    if (len < size) return listings;

    Map<Integer, Integer> map = new HashMap<>();
    int[] counts = new int[len];
    int[] arr = new int[len];

    int curr = 0;
    for (int i = 0; i < len; i++) {
      String listing = listings[i];
      int host = Integer.parseInt(listing.substring(0, listing.indexOf(',')));

      int temp = map.getOrDefault(host, -1);

      int page = temp >= curr ? temp + 1 : curr;

      arr[i] = page;
      map.put(host, page);
      counts[page]++;

      if (counts[curr] == size) curr++;
    }

    for (int i = 1; i < counts.length; i++) {
      counts[i] += counts[i - 1];
    }

    String[] pages = new String[len];
    for (int i = len - 1; i >= 0; i--) {
      pages[--counts[arr[i]]] = listings[i];
    }

    return pages;
  }

  public static void main(String[] args) {
    String[] listings = {
      "1,28,300.1,SanFrancisco",
      "4,5,209.1,SanFrancisco",
      "20,7,208.1,SanFrancisco",
      "23,8,207.1,SanFrancisco",
      "16,10,206.1,Oakland",
      "1,16,205.1,SanFrancisco",
      "6,29,204.1,SanFrancisco",
      "7,20,203.1,SanFrancisco",
      "8,21,202.1,SanFrancisco",
      "2,18,201.1,SanFrancisco",
      "2,30,200.1,SanFrancisco",
      "15,27,109.1,Oakland",
      "10,13,108.1,Oakland",
      "11,26,107.1,Oakland",
      "12,9,106.1,Oakland",
      "13,1,105.1,Oakland",
      "22,17,104.1,Oakland",
      "1,2,103.1,Oakland",
      "28,24,102.1,Oakland",
      "18,14,11.1,SanJose",
      "6,25,10.1,Oakland",
      "19,15,9.1,SanJose",
      "3,19,8.1,SanJose",
      "3,11,7.1,Oakland",
      "27,12,6.1,Oakland",
      "1,3,5.1,Oakland",
      "25,4,4.1,SanJose",
      "5,6,3.1,SanJose",
      "29,22,2.1,SanJose",
      "30,23,1.1,SanJose"
      };
    String[] strs = Solution.pagination(listings, 10);
    int page = 0;
    int row = 0;
    for (String str : strs) {
      if (row == 10) {
        System.out.println("---------------" + ++page +"-----------");
        row = 0;
      }
      row++;
      System.out.println(str);
    }
  }
}
