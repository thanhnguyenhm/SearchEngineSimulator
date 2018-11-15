package pa2;

import java.util.Arrays;

/**
 * Implementation of Heap Sort algorithms based on the textbook
 */
public class HeapSortEngine{

    // Declare instance variables
    int heapSize;
    int[] array;


    public HeapSortEngine(int[] array) {
        this.array = array;
        heapSize = array.length - 1;
    }

    // Method to determine the location of parent, left, right nodes in an
    // array
    public int parent(int i) {return i/2;}
    public int left(int i) {return 2 * i + 1;}
    public int right(int i) {return 2 * i + 2;}

    /**
     * MAX-HEAPIFY implementation to maintain max-heap property
     * @param i: index
     */
    public void maxHeapify(int i) {
        int l = left(i);
        int r = right(i);
        int largest;

        if (l <= heapSize && array[l] > array[i])
            largest = l;
        else largest = i;
        if (r <= heapSize && array[r] > array[largest])
            largest = r;
        if (largest != i) {
            int temp = array[i];
            array[i] = array[largest];
            array[largest] = temp;
            maxHeapify(largest);
        }
    }

    /**
     * BUILD-MAX-HEAP Implementation
     */
    public void buildMaxHeap() {
        //build heap from last non-leaf to root
        for (int i = (this.array.length / 2) - 1; i >= 0; i--)
            maxHeapify(i);
    }

    /**
     * HEAP-SORT Implementation
     */
    public void heapSort() {
        buildMaxHeap();
        for(int i = heapSize; i > 0; i--) {
            int temp = this.array[0];
            this.array[0] = this.array[i];
            this.array[i] = temp;
            heapSize--;
            maxHeapify(0);
        }
    }

    /**
     * MAX-HEAP-INSERT Implementation
     * @param key to be inserted into heap
     */
    public void maxHeapInsert(int key) {
        heapSize++;
        array[heapSize] = Integer.MIN_VALUE;
        heapIncreaseKey(heapSize, key);

    }

    /**
     * HEAP-INCREASE-KEY Implementation
     * @param i
     * @param key
     */
    public void heapIncreaseKey(int i, int key) {
        if(key < array[i]) {
            System.out.println("New key is smaller than current key");
        }
        array[i] = key;
        while(i > 0 && parent(i) < array[i]) {
            int temp = array[i];
            array[i] = array[parent(i)];
            array[parent(i)] = temp;
            i = parent(i);
        }
    }

    /**
     * HEAP-EXTRACT-MAX Implementation
     * @return the maximum(root)
     */
    public int heapExtractMax() {
        if (heapSize < 1) {
            System.out.println("Heap underflow");
        }
        int max = array[0];
        array[0] = array[heapSize];
        heapSize--;
        maxHeapify(0);
        return max;
    }

    /**
     * HEAP-MAXIMUM Implementation: Return the maximum element of the heap
     * @return root of the heap
     */
    public int heapMaximum() {
        return array[0];
    }

}

class Tester {
    public static void main(String[] args) {
        int b[] = {4, 6, 3, 5, 2, 9, 11, 9};
        HeapSortEngine heap = new HeapSortEngine(b);
        heap.heapSort();

        System.out.println(Arrays.toString(b));

        int[] c = {15, 13, 9, 5, 12,8,7,4,0,6,2,1};
        HeapSortEngine heap2 = new HeapSortEngine(c);
        int max = heap2.heapExtractMax();
        System.out.println(max);
        max = heap2.heapExtractMax();
        System.out.println(max);
        max = heap2.heapExtractMax();
        System.out.println(max);
        max = heap2.heapExtractMax();
        System.out.println(max);
        heap2.maxHeapInsert(20);
        max = heap2.heapMaximum();
        System.out.println(max);
        heap2.heapIncreaseKey(0, 21);
        System.out.println(heap2.heapMaximum());

        HeapSortEngine heap3 = new HeapSortEngine(b);
        heap3.buildMaxHeap();
        int max2 = heap3.heapExtractMax();
        System.out.println(max2);
        max2 = heap3.heapExtractMax();
        System.out.println(max2);
    }
}
