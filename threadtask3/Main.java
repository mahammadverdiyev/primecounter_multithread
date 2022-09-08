package threadtask3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int[] threadCounts = {1, 2, 3, 4, 6, 12, 24, 48};

        PrimeFinder primeFinder = new PrimeFinder(100000, 400000, 1);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("performance_metrics.txt"))) {
            for (int threadCount : threadCounts) {
                primeFinder.setThreadCount(threadCount);
                primeFinder.startCalculation();
                double performance = primeFinder.getLastCalculationPerformance();
                writer.write("Thread count: " + threadCount + ", Performance: " + performance + " seconds" + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
