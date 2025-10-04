package metrics;

import metrics.PerformanceTracker;
import metrics.CSVExporter;

public class Main {
    public static void main(String[] args) {
        PerformanceTracker tracker = new PerformanceTracker();

        tracker.addComparisons(120);
        tracker.addSwaps(45);
        tracker.addArrayAccesses(230);
        tracker.addMemoryAllocations(3);

        CSVExporter.exportToCsv(tracker, "metrics.csv");
    }
}

