---
description: Static classes are basically a way of grouping classes together in Java.
---

# Static Nested Class in Java

Java doesn't allow you to create top-level static classes; only nested \(inner\) static classes. Let's take a look at an example.  
Here's a class called `CarParts` that declares a static inner class called Wheel.

```java
public class CarParts {
    
    public static class Wheel {
        public Wheel() {
            System.out.println("Wheel created!");
        }
    }

    public CarParts() {
        System.out.println("Car Parts object created!");
    }
}
```

 I've added constructors to both classes so that we can see when they are instantiated. In other words, when objects are actually created from them. We can use the `CarParts` class in the normal way; note that creating a `CarParts` object _does not_ create a `Wheel` object.

```java
public class App {
    public static void main(String[] args) {
        CarParts carParts = new CarParts();
    }
}
// Car Parts object created!
```

```java
public class App {
    public static void main(String[] args) {
        CarParts.Wheel wheel = new CarParts.Wheel();
    }
}
// Wheel created!
```

