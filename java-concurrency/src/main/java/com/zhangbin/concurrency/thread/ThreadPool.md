

## 线程池使用笔记

### 线程池使用方式

#### Executors

- ```java
  ExecutorService executorService = Executors.newFixedThreadPool(2);
  ```

- ```java
  ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 0, TimeUnit.MILLISECONDS,
          new ArrayBlockingQueue<>(4));
  ```

- ```java
  CompletableFuture.runAsync(() -> {
  
  });
  ```





#### ThreadPoolExecutor

##### 任务执行流程图

![线程池执行任务流程](G:\笔记\线程池执行任务流程.png)

##### 源码浅析

```java
public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
             Executors.defaultThreadFactory(), defaultHandler);
    }
    
 /**
     * The default thread factory. 默认创建线程工厂
     */
    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                                  Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                          poolNumber.getAndIncrement() +
                         "-thread-";
        }

        // 创建一个线程的方法
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

```



- 当线程池调用 `submit(Runnable task)`  -> `execute(Runnable command)` -> `addWorker(Runnable firstTask, boolean core)` 
- 通过 `DefaultThreadFactory` 创建线程，然后启动线程





#### ForkJoinPool

- 根据当前处理器的核心数创建线程







### 思考

#### Fork/Join

<https://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html>

> The fork/join framework is an implementation of the `ExecutorService` interface that helps you <u>==take advantage of==</u> multiple processors. It is designed for work that can ==be broken into== smaller pieces recursively. The goal is to use all the available processing power to enhance the performance of your application.

- Fork/Join 框架 帮助你利用多处理器，它可以将工作递归地的分解成较小的任务。目标是可用的处理能力来提高你的应用的性能

  

> As with any `ExecutorService` implementation, the fork/join framework distributes tasks to worker threads in a thread pool. The fork/join framework is distinct because it uses a *work-stealing* algorithm. Worker threads that run out of things to do can steal tasks from other threads that are still busy.
>
> The center of the fork/join framework is the [`ForkJoinPool`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinPool.html) class, an extension of the `AbstractExecutorService` class. `ForkJoinPool` implements the core work-stealing algorithm and can execute[`ForkJoinTask`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinTask.html) processes.



#### work-stealing

<https://en.wikipedia.org/wiki/Work_stealing>



<https://segmentfault.com/a/1190000008140126>





