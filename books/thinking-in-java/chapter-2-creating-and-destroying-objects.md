# Chapter 2: Creating and Destroying Objects

## Item 7: Eliminate obsolete object references

### Memory Leak

In this case, any references outside of the “active portion” of the element array are obsolete. The active portion consists of the elements whose index is less than `size`.

```java
public class Stack {
  private static final int DEFAULT_INITIAL_CAPACITY = 16;
  private Object[] elements;
  private int size = 0;

  public Stack() {
    elements = new Object[DEFAULT_INITIAL_CAPACITY];
  }

  public void push(Object e) {
    ensureCapacity();
    elements[size++] = e;
  }

  public Object pop() {
    if (size == 0)
      throw new EmptyStackException();
    // elements[size] = null; // Eliminate obsolete reference
    return elements[--size];
  }

  /**
   * Ensure space for at least one more element, roughly
   * doubling the capacity each time the array needs to grow.
   */
  private void ensureCapacity() {
    if (elements.length == size)
      elements = Arrays.copyOf(elements, 2 * size + 1);
  }
}
```

When programmers are first stung by this problem, they may overcompensate by nulling out every object reference as soon as the program is finished using it. This is neither necessary nor desirable; it clutters up the program unnecessarily. Nulling out object references should be the exception rather than the norm. The best way to eliminate an obsolete reference is to let the variable that contained the reference fall out of scope. This occurs naturally if you define each variable in the [narrowest possible scope](chapter-2-creating-and-destroying-objects.md#markdown-header-memory-leak)

### Common Mistakes

* whenever a class manages its own memory, the programmer should be alert for memory leaks.
* Another common source of memory leaks is caches.
* A third common source of memory leaks is listeners and other callbacks.

