# Assignment 05
This folder contains the source code for **Assignment 05** of the course **ECE 325 – Iterative Methods**.  
The assignment consists of two exercises focusing on decrease-and-conquer strategies and graph traversal.

---

## Exercise 1 – Decrease-and-Conquer Algorithms (60%)

### Description
This exercise implements four decrease-and-conquer algorithms, covering three categories:

1. **Insertion Sort (Decrease-by-1)**  
   - **Objective:** Sort an array of integers in ascending order.  
   - **Decrease Step:** The unsorted portion of the array is reduced by one element at each iteration.  
   - **Source:** `ECE325_2025_Assignment5_ex1_1a_Team17_InsertionSort.java`

2. **Linear Search (Decrease-by-1)**  
   - **Objective:** Search for a key element in an unsorted array.  
   - **Decrease Step:** The search space is reduced by examining one element at a time.  
   - **Source:** `ECE325_2025_Assignment5_ex1_1b_Team17_LinearSearch.java`

3. **Binary Search (Decrease-by-constant-factor)**  
   - **Objective:** Search for a key element in a sorted array.  
   - **Decrease Step:** The search space is halved at each iteration.  
   - **Source:** `ECE325_2025_Assignment5_ex1_2_Team17_BinarySearch.java`

4. **QuickSelect (Variable-size Decrease)**  
   - **Objective:** Find the k-th smallest element in an unsorted array.  
   - **Decrease Step:** The array is partitioned around a random pivot, reducing the search to one partition whose size varies dynamically.  
   - **Source:** `ECE325_2025_Assignment5_ex1_3_Team17_QuickSelect.java`

### Running-Time Observations
- Execution time is measured in milliseconds using `System.nanoTime()`.
- Times vary based on input size `n` and algorithm characteristics.

| Algorithm      | Category                    | Complexity      | Observations                    |
|----------------|----------------------------|----------------|---------------------------------|
| Insertion Sort | Decrease-by-1               | O(n²)          | Efficient for small arrays      |
| Linear Search  | Decrease-by-1               | O(n)           | Scales linearly with n          |
| Binary Search  | Decrease-by-constant-factor | O(log n)       | Efficient on large sorted arrays|
| QuickSelect    | Variable-size Decrease      | O(n) avg, O(n²) worst | Fast for k-th element selection|

---

## Exercise 2 – Graph Traversal (40%)

### Description
This program reads an undirected graph from a `.txt` file and traverses it using DFS or BFS.

- **Input File Format:**  
<number_of_nodes> <number_of_edges>
u1 v1
u2 v2
...

- **Traversal Options:**
- Depth-First Search (DFS) – recursive approach.
- Breadth-First Search (BFS) – queue-based iterative approach.

- **Source:** `ECE325_2025_Assignment5_ex2_Team17.java`

### Notes
- Traversal starts from node `0`.
- DFS prints vertices in depth-first order, exploring as far as possible along each branch.
- BFS prints vertices in breadth-first order, exploring all neighbors at the current depth before going deeper.
- Execution order may differ depending on graph structure and adjacency list order.
- Observed differences:
- DFS tends to go deep quickly, may produce longer paths before backtracking.
- BFS explores nearest neighbors first, producing the shortest path to nodes from the start in unweighted graphs.
