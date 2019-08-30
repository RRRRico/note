import java.util.ArrayList;
import java.util.List;

class Solution {
  enum State {
    START,
    QUOTED,
    ESCAPE
  }

  private static final char QUOTE = '"';
  private static final char DELIM = ',';
  private static final char LF = '\n';

  public static String parser(String str) {
    List<String> tokens = new ArrayList<>();

    StringBuilder sb = new StringBuilder();
    State curr = State.START;

    for (int i = 0; i < str.length(); i++) {
      State next = curr;
      char c = str.charAt(i);

      switch (curr) {
        case ESCAPE:
        sb.append(c);
        next = State.QUOTED;
        break;
        case START:
        switch (c) {
          case QUOTE:
          next = State.QUOTED;
          break;
          case DELIM:
          tokens.add(sb.toString());
          sb = new StringBuilder();
          break;
          default:
          sb.append(c);
        }
        break;
        case QUOTED:
        if (c == QUOTE) {
          if (i + 1 == str.length() || str.charAt(i + 1) == DELIM) {
            next = State.START;
          } else next = State.ESCAPE;
        } else sb.append(c);
        break;
        default:
        break;
      }
      curr = next;
    }
    tokens.add(sb.toString());
    return String.join("|", tokens);
  }

  public static void main(String... args) {
    System.out.println(Solution.parser("Jane,Roberts,janer@msn.com,\"San Francisco, CA\",0"));
    System.out.println(
      Solution.parser("\"Alexandra \"\"Alex\"\"\",Menendez,alex.menendez@gmail.com,Miami,1"));
  }
}
