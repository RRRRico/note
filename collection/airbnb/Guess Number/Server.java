import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
  private final String secret = "4321";
  public static final int port = 23453;

  public void start() throws IOException {
    System.out.println("Server started.");
    try (ServerSocket ss = new ServerSocket(port)) {
      while (true) {
        try (Socket socket = ss.accept();
          PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
          Scanner in = new Scanner(socket.getInputStream())) {
          String str = in.nextLine();
          out.println(count(str));
        }
      }
    }
  }

  private int count(String str) {
    int cnt = 0;

    for (int i = 0; i < secret.length(); i++) {
      if (str.charAt(i) == secret.charAt(i)) cnt++;
    }
    return cnt;
  }

  public static void main(String[] args) throws IOException {
    new Server().start();
  }
}
