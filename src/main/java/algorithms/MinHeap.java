package algorithms;

import metrics.PerformanceTracker;
import java.util.Arrays;

public class MinHeap {
    private int[] heap;
    private int size;
    private PerformanceTracker tracker;

    public MinHeap(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.heap = new int[capacity];
        this.size = 0;
        this.tracker = new PerformanceTracker();
    }

    public MinHeap(int[] data, boolean copy) {
        if (data == null) throw new IllegalArgumentException("Input array is null");
        this.size = data.length;
        this.heap = copy ? Arrays.copyOf(data, data.length) : data;
        this.tracker = new PerformanceTracker();
        buildHeap();
    }

    public PerformanceTracker getTracker() { return tracker; }

    private int parent(int i) { return (i - 1) / 2; }
    private int left(int i) { return 2 * i + 1; }
    private int right(int i) { return 2 * i + 2; }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= heap.length) return;
        int newCap = Math.max(heap.length * 2, minCapacity);
        heap = Arrays.copyOf(heap, newCap);
        tracker.addMemoryAllocations(1); // conservative count
    }

    private void swap(int i, int j) {
        tracker.addSwaps(1);
        tracker.addArrayAccesses(4); // read two, write two
        int tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    // Insert: O(log n) worst, Ω(1) best
    public void insert(int key) {
        ensureCapacity(size + 1);
        heap[size] = key; tracker.addArrayAccesses(1);
        int cur = size; size++;

        while (cur > 0) {
            int p = parent(cur);
            tracker.addComparisons(1);
            tracker.addArrayAccesses(2); // heap[cur], heap[p]
            if (heap[cur] < heap[p]) {
                swap(cur, p);
                cur = p;
            } else break;
        }
    }

    // Extract-min: O(log n)
    public int extractMin() {
        if (size == 0) throw new IllegalStateException("Heap is empty");
        tracker.addArrayAccesses(1);
        int min = heap[0];
        heap[0] = heap[size - 1]; tracker.addArrayAccesses(2);
        size--;
        heapifyDown(0);
        return min;
    }

    private void heapifyDown(int i) {
        while (true) {
            int l = left(i); int r = right(i);
            int smallest = i;
            if (l < size) {
                tracker.addComparisons(1);
                tracker.addArrayAccesses(1);
                if (heap[l] < heap[smallest]) smallest = l;
            }
            if (r < size) {
                tracker.addComparisons(1);
                tracker.addArrayAccesses(1);
                if (heap[r] < heap[smallest]) smallest = r;
            }
            if (smallest != i) {
                swap(i, smallest);
                i = smallest;
            } else break;
        }
    }

    // Decrease-key: index-based API
    public void decreaseKey(int index, int newValue) {
        if (index < 0 || index >= size) throw new IllegalArgumentException("Invalid index");
        tracker.addArrayAccesses(1);
        if (newValue > heap[index]) throw new IllegalArgumentException("newValue is greater than current value");
        heap[index] = newValue; tracker.addArrayAccesses(1);
        int cur = index;
        while (cur > 0) {
            int p = parent(cur);
            tracker.addComparisons(1);
            tracker.addArrayAccesses(2);
            if (heap[cur] < heap[p]) { swap(cur, p); cur = p; }
            else break;
        }
    }

    // Merge: concatenate underlying arrays and rebuild — O(n + m)
    public void merge(MinHeap other) {
        if (other == null) throw new IllegalArgumentException("Other heap is null");
        int newSize = this.size + other.size;
        int[] newHeap = new int[newSize];
        System.arraycopy(this.heap, 0, newHeap, 0, this.size);
        System.arraycopy(other.heap, 0, newHeap, this.size, other.size);
        tracker.addArrayAccesses(this.size + other.size);
        tracker.addMemoryAllocations(1);
        this.heap = newHeap;
        this.size = newSize;
        buildHeap();
    }

    // Build-heap bottom-up: O(n)
    private void buildHeap() {
        for (int i = Math.max(0, size / 2 - 1); i >= 0; i--) heapifyDown(i);
    }

    // Utilities
    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }
    public int peek() { if (size==0) throw new IllegalStateException("empty"); tracker.addArrayAccesses(1); return heap[0]; }

    // For tests: expose a copy of internal array for validation
    public int[] toArray() { return Arrays.copyOf(heap, size); }
}