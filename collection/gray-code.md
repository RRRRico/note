# Gray Code

## Original Problem

The gray code is a binary numeral system where two successive values differ in only one bit.

Given a non-negative integer _n_ representing the total number of bits in the code, print the sequence of gray code. A gray code sequence must begin with 0.

```java
class Solution {
  public List<Integer> grayCode(int n) {
    int cnt = 1 << n;
    List<Integer> list = new ArrayList<>(cnt);
    for (int i = 0; i < cnt; i++) list.add(i ^ i >> 1);
    return list;
  }
}
```

## Correctness

Goal of proof:

```bash
n -> g(n)
n + 1 -> g(n + 1)

g(n) and g(n + 1) differs only one bit

Binary form:
n         :0101...0111 (x trailing ones)
n >> 1    :00101...011 (x - 1 trailing ones)
n + 1     :0101...1000 (x trailing zeros)
n + 1 >> 1:00101...100 (x - 1 trailing zeros)

When xor all these 4 numbers, only
01
.0
10
.1

position differs, those 2 dots must be same as plus one won't change value of dots
Thus, only the first zero of n will change to one
```

## References

* [wikipedia](https://en.wikipedia.org/wiki/Gray_code)

