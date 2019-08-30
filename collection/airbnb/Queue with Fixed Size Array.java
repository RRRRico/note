class Solution {
  class Node {
    Node(int length) {
      this.arr = new int[length];
    }

    Node next;
    int[] arr;
  }

  private int size;
  private Node head;
  private int i;
  private Node tail;
  private int j;
  private int length;
  private int cap;

  Solution(int length) {
    this.length = length;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  private boolean isFull() {
    return size == cap || j == length;
  }

  private void addNode() {
    Node node = new Node(length);
    if (isEmpty()) {
      head = node;
      tail = node;
      i = j = 0;
    }
    tail.next = node;
    tail = node;
    j = 0;
    cap += length;
  }

  private void delHead() {
    Node next = head.next;
    head.next = null;
    head = next;
    cap -= length;
    i = 0;
  }

  public void push(int obj) {
    if (isFull()) addNode();
    tail.arr[j++] = obj;
    size++;
  }

  public int poll() {
    if (isEmpty()) throw new IllegalStateException("Queue is empty.");
    int val = head.arr[i++];
    if (i == length) delHead();
    size--;
    return val;
  }

  public static void main(String[] args) {
    Solution solution = new Solution(5);
    for (int i = 0; i < 100; i++) {
      solution.push(i);
      if (i % 3 == 0) solution.poll();
    }

    while (!solution.isEmpty()) {
      solution.poll();
    }
  }
}
