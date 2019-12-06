package com.spring.ym.exportThread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTask extends RecursiveTask<Long> {
    private static final long MAX = 1000000000L;
    private static final long THRESHOLD = 10000000L;
    private long start;
    private long end;

    public ForkJoinTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) {
        test();
        System.err.println("================================");
        testForkJoin();
    }
    private static void test(){
        System.err.println("test");
        long start = System.currentTimeMillis();
        long sum = 0L;
        for (int i = 0; i <= MAX; i++) {
            sum+=i;
        }
        System.err.println(sum);
        System.err.println(System.currentTimeMillis()-start +"ms");
    }
    private static void testForkJoin(){
        System.out.println("testForkJoin");
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long sum = forkJoinPool.invoke(new ForkJoinTask(1L, MAX));
        System.err.println(sum);
        System.err.println(System.currentTimeMillis()-start +"ms");
    }
    @Override
    protected Long compute() {
        long sum = 0L;
        if(end-start <= THRESHOLD){
            for (long i = start;i<= end;i++){
                sum+=i;
            }
            return sum;
        }else{
            long mid = (start + end)>>1;
            ForkJoinTask forkJoinTask1 = new ForkJoinTask(start, mid);
            forkJoinTask1.fork();
            ForkJoinTask forkJoinTask2 = new ForkJoinTask(mid + 1L, end);
            forkJoinTask2.fork();
            return forkJoinTask1.join() + forkJoinTask2.join();
        }
    }
}
