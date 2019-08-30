import java.util.*;

public class Solution {

  private static int count = 0;
  private static String target;
  private static Random rand = new Random();

  public static void main(String[] args) {
    System.out.println(sendAndReceive("start"));
    System.out.println("Result: " + guess());
  }

  // Simulation method, to generate or reset the random number, don't have to focus on it
  public static void reset() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 4; ++i) {
      sb.append(rand.nextInt(6) + 1);
    }
    count = 0;
    target = sb.toString();
  }

  // Simulation method, don't have to focus it
  public static String sendAndReceive(String str) {
    if (str.toLowerCase().equals("start")) {
      reset();
      return "Ready, target # is " + target;
    }
    System.out.println("Times of method call: " + ++count + ", coming number: " + str);
    int bulls = 0;
    int cows = 0;
    int[] number = new int[10];

    for (int i = 0; i < target.length(); i++) {
      char s = str.charAt(i);
      char t = target.charAt(i);

      if (s == t) bulls++;
      else {
        if (number[s - '0'] > 0) cows++;
        if (number[t - '0'] < 0) cows++;
        number[t - '0']++;
        number[s - '0']--;
      }
    }
    return bulls + " " + cows;
  }

  private static int call(char[] arr) {
    return Integer.parseInt(sendAndReceive(String.valueOf(arr)).split(" ")[0]);
  }

  // Solutions
  public static String guess() {
    int count = 0;
    int[] cnts = new int[7];
    char[] res = new char[4];
    char[] arr = new char[4];

    char base = '6';
    int first = 0;

    for (char c = '1'; c < '6'; c++) {
      Arrays.fill(arr, c);
      int val = call(arr);
      if (val == 4) return String.valueOf(arr);
      else if (val > 0) {
        cnts[c - '0'] = val;
        if (val > first) {
          base = c;
          first = val;
        }
      }
      count += val;
      if (count == 4) break;
    }
    // handle case 6
    if (count < 4) cnts[6] = 4 - count;

    if (cnts[6] > first) {
      base = '6';
      first = cnts[6];
    }

    System.out.println();
    for (int val : cnts) System.out.print(val + ",");

    Arrays.fill(arr, base);
    String s = String.valueOf(arr);
    System.out.println("\n" + base + "," + first + "," + s);

    for (int i = 0; i < 3; i++) {
      arr = s.toCharArray();

      for (int j = 1; j <= 6; j++) {
        char cj = (char) (j + '0');
        if (cnts[j] == 0 || cj == base) continue;

        arr[i] = cj;
        int curr = call(arr);

        if (curr != first) {
          res[i] = curr > first ? arr[i] : base;
          cnts[res[i] - '0']--;
          break;
        }
      }
      if (res[i] == 0) res[i] = base;
    }

    for (int i = 1; i <= 6; i++) {
      if (cnts[i] > 0) res[3] = (char) (i + '0');
    }
    return String.valueOf(res);
  }
}
