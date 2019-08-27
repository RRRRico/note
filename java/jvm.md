---
description: Java Virtual Machine
---

# JVM

## [Class Loader](https://dzone.com/articles/java-virtual-machine-internals-class-loader)

Class loading is finding and loading types \(classes and interfaces\) at run-time dynamically. Types data are contained in binary files in class file format.

### **Bootstrap Class Loader**

Java classes are loaded by an instance of `java.lang.ClassLoader`. However, class loaders are classes themselves. Hence, the question is, who loads the _java.lang.ClassLoader_ itself_?_

This is where the bootstrap or primordial class loader comes into the picture.

It’s mainly responsible for loading JDK internal classes, typically _rt.jar_ and other core libraries located in _$JAVA\_HOME/jre/lib directory_. Additionally, **Bootstrap class loader serves as a parent of all the other** _**ClassLoader**_**instances**.

**This bootstrap class loader is part of the core JVM and is written in native code** as pointed out in the above example. Different platforms might have different implementations of this particular class loader.

### **Extension Class Loader**

The **extension class loader is a child of the bootstrap class loader and takes care of loading the extensions of the standard core Java classes** so that it’s available to all applications running on the platform.

Extension class loader loads from the JDK extensions directory, usually _$JAVA\_HOME/lib/ext_ directory or any other directory mentioned in the _java.ext.dirs_ system property.

### **System Class Loader**

The system or application class loader, on the other hand, takes care of loading all the application level classes into the JVM. **It loads files found in the classpath environment variable,** _**-classpath**_ **or** _**-cp**_ **command line option**. Also, it’s a child of Extensions classloader.

### Application Class Loader

Class loaders are part of the Java Runtime Environment. When the JVM requests a class, the class loader tries to locate the class and load the class definition into the runtime using the fully qualified class name.

The _**java.lang.ClassLoader.loadClass\(\)**_ **method is responsible for loading the class definition into runtime**. It tries to load the class based on a fully qualified name.

If the class isn’t already loaded, it delegates the request to the parent class loader. This process happens recursively.

Eventually, if the parent class loader doesn’t find the class, then the child class will call _java.net.URLClassLoader.findClass\(\)_ method to look for classes in the file system itself.

If the last child class loader isn’t able to load the class either, it throws [java.lang.NoClassDefFoundError or java.lang.ClassNotFoundException](https://www.baeldung.com/java-classnotfoundexception-and-noclassdeffounderror)

### Reading

* [java-doc Class Loader](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/ClassLoader.html)

## Garbage Collector

## Interpreter

## JIT Compiler

## Thread Management

## Reading

* \*\*\*\*[**The JVM Architecture Explained**](https://dzone.com/articles/jvm-architecture-explained)\*\*\*\*

