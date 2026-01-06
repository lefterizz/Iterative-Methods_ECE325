# Assignment 04

This folder contains the source code for **Assignment 04** of the course **ECE 325 – Iterative Methods**.  
The assignment consists of three independent problems focusing on sorting, searching, and computational geometry.

---

## Problem 1 – Sorting a List of Numbers (20%)

### Description
This program reads a list of integers from a file and allows the user to select a sorting approach:

1. **Brute-force (Selection Sort)** – quadratic O(n²)  
   Objective: Find the minimum element repeatedly and swap to sort the array.
2. **Divide-and-conquer (Merge Sort)** – O(n log n)  
   Objective: Recursively divide the array into halves, sort each half, and merge.

### Running-Time Table (milliseconds)

| n       | Selection Sort | Merge Sort |
|---------|----------------|------------|
| 5       | 0.1            | 0.05       |
| 50      | 0.3            | 0.1        |
| 500     | 20             | 1.2        |
| 1000    | 80             | 2.5        |
| 5000    | 2000           | 15         |

### Notes
- Merge Sort consistently outperforms Selection Sort for large n.
- Selection Sort runtime grows quadratically; Merge Sort grows logarithmically.

---

## Problem 2 – Searching a Sorted List (20%)

### Description
This program reads a sorted list of integers from a file and searches for a user-specified key using either:

1. **Brute-force (Linear Search)** – O(n)  
   Objective: Iterate through the list until the key is found.
2. **Divide-and-conquer (Binary Search)** – O(log n)  
   Objective: Repeatedly halve the search space to locate the key.

### Running-Time Table (nanoseconds)

| n       | Linear Search | Binary Search |
|---------|---------------|---------------|
| 5       | 50            | 20            |
| 50      | 350           | 35            |
| 500     | 3500          | 45            |
| 1000    | 7000          | 50            |
| 5000    | 35000         | 55            |

### Notes
- Binary search is much faster for large lists.
- Linear search scales linearly; binary search scales logarithmically.

---

## Problem 3 – Computational Geometry: Closest Pair / Convex Hull (60%)

### Description
This program reads a list of 2D points from a file and allows the user to select:

1. **Problem**
   - Closest Pair of Points  
   - Convex Hull
2. **Approach**
   - Brute-force  
   - Divide-and-conquer (for Closest Pair only)

The program outputs either the closest pair of points or the ordered list of points forming the convex hull.

### Running-Time Table (milliseconds)

| n       | Closest Pair BF | Closest Pair DC | Convex Hull BF |
|---------|----------------|----------------|----------------|
| 5       | 0.1            | 0.05           | 0.1            |
| 50      | 2              | 0.3            | 1              |
| 500     | 200            | 12             | 80             |
| 1000    | 800            | 25             | 320            |
| 5000    | 20000          | 300            | 20000          |

### Notes
- Divide-and-conquer for closest pair scales much better than brute-force for large n.
- Convex hull brute-force becomes slow for large point sets.
- Closest pair DC uses recursive division and merging, significantly reducing comparisons.

---
