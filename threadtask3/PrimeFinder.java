package threadtask3;

import java.util.ArrayList;
import java.util.List;

public class PrimeFinder {
    private int primeCounter = 0;

    private final int start;
    private final int end;
    private int threadCount;
    private long lastCalculationPerformance;

    private class PrimeCalculator implements Runnable {
        private int start, end;

        public PrimeCalculator(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = this.start; i < this.end; i++) {
                if (isPrime(i)) increment();
            }
        }

        public boolean isPrime(int n) {
            if (n == 2) return true;
            for (int i = 2; i < n; i++) {
                if (n % i == 0) return false;
            }
            return true;
        }

        private synchronized void increment() {
            primeCounter += 1;
        }
    }

    public PrimeFinder(int start, int end, int threadCount) {
        this.start = start;
        this.end = end;
        this.threadCount = threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getPrimeCount() {
        return primeCounter;
    }

    public double getLastCalculationPerformance() {
        return (double) lastCalculationPerformance / 1000;
    }

    public int startCalculation() throws InterruptedException {
        List<Thread> allThreads = new ArrayList<>(this.threadCount);

        final int numberPerThread = (end - start) / threadCount;
        int numberTracker = start;

        for (int i = 0; i < threadCount; i++) {
            Runnable runnable = new PrimeCalculator(numberTracker, numberTracker + numberPerThread);
            numberTracker += numberPerThread;
            Thread thread = new Thread(runnable);
            allThreads.add(thread);
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            allThreads.get(i).start();
        }
        for (int i = 0; i < threadCount; i++) {
            allThreads.get(i).join();
        }
        long endTime = System.currentTimeMillis();
        this.lastCalculationPerformance = endTime - startTime;

        return primeCounter;
    }
}
