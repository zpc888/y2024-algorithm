package algo.heap;

// build a heap one by one (from root to leaf node) is O(nlogn)
// build a heap from bottom up is O(n) in array
public class PriorityQueue_In_Array {
    int[] arr;
    int heapsize;
    private boolean isMaxHeap = false;

    public static PriorityQueue_In_Array createMinHeap(int capacity) {
        return new PriorityQueue_In_Array(capacity);
    }

    public static PriorityQueue_In_Array createMaxHeap(int capacity) {
        return new PriorityQueue_In_Array(capacity, true);
    }

    public PriorityQueue_In_Array(int[] data, boolean maxHeap) {
        this.arr = data;
        this.isMaxHeap = maxHeap;
        this.heapsize = data.length;
        for (int i = data.length; i >= 0; i--) {            // this way is O(n)
            heapify(i);
        }
    }

    public PriorityQueue_In_Array(int capacity) {
        this(capacity, false);
    }

    public PriorityQueue_In_Array(int capacity, boolean maxHeap) {
        arr = new int[capacity];
        heapsize = 0;
        this.isMaxHeap = maxHeap;
    }

    public void add(int val) {
        if (heapsize == arr.length) {
            throw new IllegalStateException("Heap is full");
        }
        arr[heapsize] = val;
        heapInsert(heapsize);
        heapsize++;
    }

    public void update(int idx, int val) {
        if (idx < 0 || idx >= heapsize) {
            throw new IllegalArgumentException("Invalid index");
        }
        int oldVal = arr[idx];
        arr[idx] = val;
        if (oldVal == val) {
            return;
        }
        if (isMaxHeap) {
            if (val > oldVal) {
                heapInsert(idx);
            } else {
                heapify(idx);
            }
        } else {
            if (val < oldVal) {
                heapInsert(idx);
            } else {
                heapify(idx);
            }
        }
    }

    public int peek() {
        if (heapsize == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return arr[0];
    }

    public int poll() {
        if (heapsize == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        int ret = arr[0];
        swap(0, --heapsize);
        heapify(0);
        return ret;
    }

    void heapify(int idx) {
        int idxL = idx * 2 + 1;         // left kid index
        while (idxL < heapsize) {
            int selKidIdx = idxL;
            if (idxL + 1 < heapsize) {		// has right kid
                if (isMaxHeap) {
                    if (arr[idxL + 1] > arr[idxL]) {
                        selKidIdx = idxL + 1;
                    }
                } else {
                    if (arr[idxL + 1] < arr[idxL]) {
                        selKidIdx = idxL + 1;
                    }
                }
            }
            if (isMaxHeap && arr[idx] >= arr[selKidIdx]) {
                break;		// already heap now
            }
            if (!isMaxHeap && arr[idx] <= arr[selKidIdx]) {
                break;		// already heap now
            }
            swap(idx, selKidIdx);
            idx = selKidIdx;
            idxL = idx * 2 + 1;
        }
    }

    void heapInsert(int idx) {
        int parentIdx = (idx - 1) / 2;          // -1 / 2 = 0
        while ((isMaxHeap && arr[idx] > arr[parentIdx]) || (!isMaxHeap && arr[idx] < arr[parentIdx])) {
            swap(idx, parentIdx);
            idx = parentIdx;
            parentIdx = (idx - 1) / 2;
        }
    }

    void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public int size() {
        return heapsize;
    }
}
