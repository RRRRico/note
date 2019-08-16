---
description: Eliminate obsolete object references.
---

# Item 7

### Memory Leak

#### Self Maintained Memory

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

When programmers are first stung by this problem, they may overcompensate by nulling out every object reference as soon as the program is finished using it. This is neither necessary nor desirable; it clutters up the program unnecessarily. Nulling out object references should be the exception rather than the norm. The best way to eliminate an obsolete reference is to let the variable that contained the reference fall out of scope. This occurs naturally if you define each variable in the [narrowest possible scope](chapter-2-creating-and-destroying-objects.md#memory-leak)

#### Other Common Mistakes

**Cache**

Once you put an object reference into a cache, it’s easy to forget that it’s there and leave it in the cache long after it becomes irrelevant. There are several solutions to this problem. If you’re lucky enough to implement a cache for which an entry is relevant exactly so long as there are references to its key outside of the cache, represent the cache as a `WeakHashMap`; entries will be removed automatically after they become obsolete. Remember that WeakHashMap is useful only if the desired lifetime of cache entries is determined by external references to the key, not the value. More commonly, the useful lifetime of a cache entry is less well defined, with entries becoming less valuable over time. Under these circumstances, the cache should occasionally be cleansed of entries that have fallen into disuse. This can be done by a background thread \(perhaps a `ScheduledThreadPoolExecutor`\) or as a side effect of adding new entries to the cache. The `LinkedHashMap` class facilitates the latter approach with its removeEldestEntry method. For more sophisticated caches, you may need to use `java.lang.ref` directly.

**listeners and callbacks**

If you implement an API where clients register callbacks but don’t deregister them explicitly, they will accumulate unless you take some action. One way to ensure that callbacks are garbage collected promptly is to store only weak references to them, for instance, by storing them only as keys in a `WeakHashMap`.

#### Readings and References

* `WeakHashMap`
* `ScheduledThreadPoolExecutor`
* callbacks
* _heap profiler_

