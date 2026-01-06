# Assignment 03
This folder contains the source code for **Assignment 03** of the course **ECE 325 – Iterative Methods**.  
The assignment consists of five independent problems, each focusing on algorithm efficiency, recursion, and basic data structures.

## Problem 1 – Algorithm Efficiency Comparison (20%)

### Description
This program implements four algorithms with different efficiency classes:

1. **Binary Search** – logarithmic O(log n)  
   Objective: Search for a target element in a sorted array.
2. **Sum of Array Elements** – linear O(n)  
   Objective: Compute the sum of all array elements.
3. **Bubble Sort** – quadratic O(n²)  
   Objective: Sort an array using repeated swaps.
4. **Permutation Generation** – factorial O(n!)  
   Objective: Print all possible arrangements of n elements.

### Running-Time Table (microseconds)

| n       | Binary Search | Sum Array | Bubble Sort | Permutations |
|---------|---------------|-----------|-------------|--------------|
| 3       | 1             | 1         | 1           | 5890         |
| 5       | 1             | 1         | 17          | 57981        |
| 7       | 2             | 1         | 908         | Too Long     |
| 9       | 68            | 9         | 91400       | Too Long     |
| 10      | 1             | 93        | 20318599    | Too Long     |
| 21      | 1075          | Too Long  | Too Long    | Too Long     |

### Notes
- Scatter plots can be generated to compare input size versus execution time.
- Execution for very large inputs of factorial and quadratic algorithms was skipped due to excessive runtime.

---

## Problem 2 – Algorithm Comparison by Complexity (20%)

### Description
Two algorithms are compared:

- Algorithm A: `fA(n) = 40n² + 192n`  
- Algorithm B: `fB(n) = 2n³`

Analysis:

- Dominant terms: A → n², B → n³  
- Classes: Θ(fA) = Θ(n²), Θ(fB) = Θ(n³)  
- Asymptotically, A is more efficient than B.  
- However, B can be faster than A for small n:  
  - For n ∈ [1, 23], fB(n) < fA(n)  
  - For n ≥ 25, fA(n) < fB(n)

---

## Problem 3 – Fibonacci Number Computation (15%)

### Description
Computes Fibonacci numbers for `n = 5, 10, 15, 20, 25, 30` using two algorithms:

1. **Fib1 (Recursive)** – exponential O(2ⁿ)
2. **Fib2 (Iterative)** – linear O(n)

Execution time measured by running each algorithm M = 1000 times for small n.

---

## Problem 4 – Tower of Hanoi (20%)

### Description
This program outputs the series of moves required to solve the Tower of Hanoi puzzle for `n` disks using recursion:

- Move n-1 smaller disks from A to B  
- Move the largest disk from A to C  
- Move n-1 smaller disks from B to C  

The program prints each move and the total number of moves performed.

---

## Problem 5 – Brute-Force String Matching (25%)

### Description
The program searches if a given pattern string exists as a substring within a longer text string using a queue-based implementation.

- User inputs:
  1. Pattern string (ends with newline)
  2. Text string (ends with newline)
- Algorithm checks each possible starting position in the text.
- Prints whether the pattern exists in the text.

### Basic Operation
`listNode.data == patternNode.data`

### Complexity Analysis

- **Best case**: first character mismatch at every position  
  - `C_best(m,n) = (n - m + 1) * 1`
- **Worst case**: almost entire pattern matches at every starting position  
  - `C_worst(m,n) = (n - m + 1) * m`

### Example
- **Best case**:  
  Text: "aaaaaaaaaa" (n=10)  
  Pattern: "c" (m=1)  
  Comparisons: 10
- **Worst case**:  
  Text: "aaaaaaaaaa" (n=10)  
  Pattern: "aaaaa" (m=5)  
  Comparisons: 30
