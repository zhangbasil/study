## Synchronized

#### 作用

- 多线程下同时访问同一个资源保证只有一个线程(Only one thread can execute at a time. )



#### 运用

- 作用在方法上面

```java
public synchronized void send() {
    System.out.println(" Hello World " );
}
```

- 同步代码块上面

```java
public void send() {
    //Only one thread can execute at a time. 
    synchronized (this) {
        // Access shared variables and other
  		// shared resources
    }
}
```



#### 原理推敲

- synchronized 是Java语言中的关键字，所以肯定是有JVM来帮我们做同步的
  - JVM只会执行Java字节码

- 了解Java 字节码

  - 同步指令

  

#### 官方规范

> Method-level synchronization is performed implicitly, as part of method invocation and return ([§2.11.8](https://docs.oracle.com/javase/specs/jvms/se12/html/jvms-2.html#jvms-2.11.8)). A `synchronized` method is distinguished in the run-time constant pool's `method_info` structure ([§4.6](https://docs.oracle.com/javase/specs/jvms/se12/html/jvms-4.html#jvms-4.6)) by the `ACC_SYNCHRONIZED` flag, which is checked by the method invocation instructions. When invoking a method for which `ACC_SYNCHRONIZED` is set, the executing thread enters a monitor, invokes the method itself, and exits the monitor whether the method invocation completes normally or abruptly. During the time the executing thread owns the monitor, no other thread may enter it. If an exception is thrown during invocation of the `synchronized` method and the `synchronized` method does not handle the exception, the monitor for the method is automatically exited before the exception is rethrown out of the `synchronized` method.

- 方法级的同步是隐式执行的，作为方法调用和返回的一部分



> Synchronization of sequences of instructions is typically used to encode the `synchronized` block of the Java programming language. The Java Virtual Machine supplies the *monitorenter* and *monitorexit* instructions to support such language constructs. Proper implementation of `synchronized` blocks requires cooperation from a compiler targeting the Java Virtual Machine ([§3.14](https://docs.oracle.com/javase/specs/jvms/se12/html/jvms-3.html#jvms-3.14)).

- 同步代码块是通过 *monitorenter* 和 *monitorexit*



> Synchronization in the Java Virtual Machine is implemented by monitor entry and exit, either explicitly (by use of the *monitorenter* and *monitorexit* instructions) or implicitly (by the method invocation and return instructions).



#### 代码实践

- 代码块显示的通过 *monitorenter*  和 *monitorexit* 指令

```java
void onlyMe(Foo foo) {
    synchronized (foo) {
        doSomething();
    }
}

```

javap -v SynchronizedQuestion.class

>   flags:
>     Code:
>       stack=2, locals=4, args_size=2
>          0: aload_1
>          1: dup
>          2: astore_2
>          3: ==monitorenter==						// Enter the monitor associated with foo
>          4: aload_0
>          5: invokespecial #2                  // Method doSomething:()V
>          8: aload_2
>          9: ==monitorexit==                       	// Exit the monitor associated with foo
>         10: goto          18
>         13: astore_3
>         14: aload_2
>         15: ==monitorexit==						// Be sure to exit the monitor!
>         16: aload_3
>         17: athrow
>         18: return
>       Exception table:
>          from    to  target type
>              4    10    13   any
>             13    16    13   any
>       LineNumberTable:
>         line 18: 0
>         line 19: 4
>         line 20: 8
>         line 21: 18

- 方法级隐式

```java
synchronized void sync() {
    doSomething();
}
```

> synchronized void sync();
>     descriptor: ()V
>     flags: ==ACC_SYNCHRONIZED==
>     Code:
>       stack=1, locals=1, args_size=1
>          0: aload_0
>          1: invokespecial #2                  // Method doSomething:()V
>          4: return
>       LineNumberTable:
>         line 13: 0
>         line 14: 4


monitorenter 通过 cmpxchg （Compare and Exchange）指令

#### 参考文献

------

<https://docs.oracle.com/javase/specs/jvms/se12/html/jvms-2.html#jvms-2.11.10>

<https://docs.oracle.com/javase/specs/jvms/se12/html/jvms-3.html#jvms-3.14>



