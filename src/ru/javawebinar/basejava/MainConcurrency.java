package ru.javawebinar.basejava;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private volatile int counter;
    private final AtomicInteger atomicInteger = new AtomicInteger();

    private static final ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"));
//    private static final Object LOCK = new Object();
//    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Object lock1 = new Object();
        Object lock2 = new Object();

        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
//                throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }

            private void inc() {
                synchronized (this) {
//                    counter++;
                }
            }

        }).start();

        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        CompletionService completionService = new ExecutorCompletionService(executorService);
//
//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() ->

//            Thread thread = new Thread(() ->
            {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
//                    System.out.println(threadLocal.get().format(new Date()));
                }
                latch.countDown();
                return 5;
            });

//            thread.start();
//            threads.add(thread);
        }

/*
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
*/
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
//        System.out.println(mainConcurrency.counter);
        System.out.println(mainConcurrency.atomicInteger.get());
//        deadLock(lock1, lock2);
//        deadLock(lock2, lock1);

        System.out.println(minValue(new int[]{1, 2, 3, 2, 2, 3}));

        List<Integer> integers = new ArrayList<>(Arrays.asList(1,2,3,5,6));
        System.out.println(oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> 10 * a + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers
                .stream()
                .filter(integers.stream().mapToInt(Integer::intValue)
                        .sum() % 2 != 0 ? n -> n % 2 == 0 : n -> n % 2 != 0)
                .collect(Collectors.toList());
    }

    private static void deadLock(Object lock1, Object lock2) {
        new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Holding lock1");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Waiting for lock2");
                synchronized (lock2) {
                    System.out.println("Holding lock2");
                }
            }
        }).start();
    }

    private void inc() {
//        synchronized (this){
//        synchronized (MainConcurrency.class) {
//        lock.lock();
//        try {
        atomicInteger.incrementAndGet();
//            counter++;
//        } finally {
//            lock.unlock();
//        }
//                wait();
//        }
    }
}
