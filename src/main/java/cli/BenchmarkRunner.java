package cli;

import algorithms.MinHeap;
import metrics.PerformanceTracker;
import java.util.Random;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;

public class BenchmarkRunner {
    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 100000};
        int runs = 5;

        try (FileWriter writer = new FileWriter("metrics.csv")) {
            writer.write("n,avgTimeMs,avgComparisons,avgSwaps,avgAccesses\n");

            for (int n : sizes) {
                long totalTime = 0;
                PerformanceTracker combined = new PerformanceTracker();

                for (int r = 0; r < runs; r++) {
                    int[] data = randomArray(n);
                    MinHeap heap = new MinHeap(n);

                    long t0 = System.nanoTime();
                    for (int v : data) heap.insert(v);
                    long t1 = System.nanoTime();

                    totalTime += (t1 - t0);

                    PerformanceTracker t = heap.getTracker();
                    combined.addComparisons(t.getComparisons());
                    combined.addSwaps(t.getSwaps());
                    combined.addArrayAccesses(t.getArrayAccesses());
                }

                double avgTimeMs = totalTime / (runs * 1e6);
                long avgComps = combined.getComparisons() / runs;
                long avgSwaps = combined.getSwaps() / runs;
                long avgAccesses = combined.getArrayAccesses() / runs;

                System.out.printf("n=%d avgTimeMs=%.3f comps=%d swaps=%d accesses=%d\n",
                        n, avgTimeMs, avgComps, avgSwaps, avgAccesses);

                writer.write(String.format("%d,%.3f,%d,%d,%d\n",
                        n, avgTimeMs, avgComps, avgSwaps, avgAccesses));
            }

            System.out.println("\n metrics.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] randomArray(int n) {
        Random rnd = new Random(123);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt();
        return a;
    }
}
