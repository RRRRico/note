import java.util.Comparator;
import java.util.Arrays;

class Solution {
  public static int[] roundNumbers(double... nums) {
    int len = nums.length;
    int[] arr = new int[len];
    Integer[] idx = new Integer[len];

    double diff = 0.0;

    for (int i = 0; i < len; i++) {
      arr[i] = (int)Math.floor(nums[i]);
      idx[i] = i;
      diff += nums[i] - arr[i];
    }

    Comparator<Integer> com = (a, b) -> Double.compare(nums[b] - arr[b], nums[a] - arr[a]);
    Arrays.sort(idx, com);

    for (int i = 0; i < Math.round(diff); i++) {
      arr[idx[i]]++;
    }

    for (int i = 0 ; i < len; i++) {
      System.out.print(arr[i] + ",");
    }
    System.out.println();

    return arr;
  }

  public static void main(String... args) {
    Solution.roundNumbers(30.3, 2.4, 3.54);
    Solution.roundNumbers(30.1, 2.1, 3.5, 1.9, 2.6);
  }
}
