# Summary of all sorts

| Sort | Time Complexity      | Space Complexity | Stable | In-Place |
|------|----------------------|------------------|--------|----------|
| Bubble Sort | O(n^2)               | O(1) | Yes | Yes |
| Selection Sort | O(n^2)               | O(1) | No | Yes |
| Insertion Sort | O(n^2)               | O(1) | Yes | Yes |
| Merge Sort | O(n log n)           | O(n) | Yes | No |
| Quick Sort | O(n^2) -> O(n log n) | O(log n) | No | Yes |
| Heap Sort | O(n log n)           | O(1) | No | Yes |
| Counting Sort | O(n + k)             | O(n + k) | Yes | No |
| Radix Sort | O(n * k)             | O(n + k) | Yes | No |
| Bucket Sort | O(n^2)               | O(n) | Yes | No |


# Result of TestingAllSorts.java
```text
Average array size: 5019 with max size can be: 10000 by running 10000 cycles
                               Sort Name :  Avg Nanos : TIMES (BASE = 1)
                           SelectionSort :    6092053 : 24.65 SLOW
                              BubbleSort :   18501135 : 74.87 SLOW
                               QuickSort :     276268 :  1.12 SLOW
                              QuickSort2 :     247114 :  1.00 BASE ==>> FASTEST
                QuickSort_In_NoRecursive :     331429 :  1.34 SLOW
                               MergeSort :     339916 :  1.38 SLOW
                           InsertionSort :    2028607 :  8.21 SLOW
                     HeapSort_In_OwnHeap :     277515 :  1.12 SLOW
                   HeapSort_In_OwnHeap_2 :     287025 :  1.16 SLOW
           HeapSort_In_JavaPriorityQueue :     498462 :  2.02 SLOW
                  RadixSort_BasicVersion :     316430 :  1.28 SLOW
               RadixSort_EnhancedVersion :     420450 :  1.70 SLOW
```

## Oberservation (from multiple runs):
  - Normally QuickSort is the fastest
  - MergeSort / HeapSort / Radix are the 2nd
  - SelectionSort and BubbleSort are the slowest
