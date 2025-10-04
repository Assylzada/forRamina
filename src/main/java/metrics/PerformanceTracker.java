package metrics;

public class PerformanceTracker {
    private long comparisons = 0;
    private long swaps = 0;
    private long arrayAccesses = 0;
    private long memoryAllocations = 0;

    public void addComparisons(long v) { comparisons += v; }
    public void addSwaps(long v) { swaps += v; }
    public void addArrayAccesses(long v) { arrayAccesses += v; }
    public void addMemoryAllocations(long v) { memoryAllocations += v; }

    public void reset() { comparisons = swaps = arrayAccesses = memoryAllocations = 0; }

    // getters
    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getArrayAccesses() { return arrayAccesses; }
    public long getMemoryAllocations() { return memoryAllocations; }

    public String toCsv() {
        return comparisons + "," + swaps + "," + arrayAccesses + "," + memoryAllocations;
    }
}