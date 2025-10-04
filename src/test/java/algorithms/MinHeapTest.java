package algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    void testEmptyHeapBehavior() {
        MinHeap heap = new MinHeap(4);
        assertTrue(heap.isEmpty(), "Heap should be empty after initialization");

        // Extracting from empty heap must throw an exception
        assertThrows(IllegalStateException.class, heap::extractMin);
    }

    @Test
    void testInsertAndExtractMin() {
        MinHeap heap = new MinHeap(4);
        heap.insert(5);
        heap.insert(3);
        heap.insert(7);
        heap.insert(1);

        assertEquals(1, heap.extractMin(), "First extracted value should be minimum");
        assertEquals(3, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(7, heap.extractMin());
        assertTrue(heap.isEmpty());
    }

    @Test
    void testDecreaseKey() {
        MinHeap heap = new MinHeap(5);
        heap.insert(10);
        heap.insert(20);
        heap.insert(15);
        heap.decreaseKey(1, 5); // decrease 20 → 5
        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(15, heap.extractMin());
    }

    @Test
    void testMergeHeaps() {
        MinHeap h1 = new MinHeap(3);
        h1.insert(1);
        h1.insert(4);
        h1.insert(7);

        MinHeap h2 = new MinHeap(3);
        h2.insert(2);
        h2.insert(3);
        h2.insert(6);

        h1.merge(h2);
        assertEquals(6, h1.getSize(), "Merged heap size should equal sum of both heaps");

        int[] sorted = new int[6];
        for (int i = 0; i < 6; i++) sorted[i] = h1.extractMin();
        assertArrayEquals(new int[]{1, 2, 3, 4, 6, 7}, sorted);
    }

    @Test
    void testPeekAndArrayCopy() {
        MinHeap heap = new MinHeap(3);
        heap.insert(8);
        heap.insert(4);
        heap.insert(5);
        assertEquals(4, heap.peek());
        int[] arrCopy = heap.toArray();
        assertEquals(3, arrCopy.length);
        assertTrue(arrCopy[0] <= arrCopy[1] || arrCopy[0] <= arrCopy[2]); // heap property check
    }

    @Test
    void testIllegalDecreaseKey() {
        MinHeap heap = new MinHeap(2);
        heap.insert(5);
        heap.insert(10);
        assertThrows(IllegalArgumentException.class, () -> heap.decreaseKey(0, 20)); // invalid (increase)
        assertThrows(IllegalArgumentException.class, () -> heap.decreaseKey(5, 1));  // invalid index
    }
}
