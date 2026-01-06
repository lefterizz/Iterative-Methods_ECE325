# Assignment 06
This folder contains the source code for **Assignment 06** of the course **ECE 325 – Iterative Methods**.  
The assignment consists of three exercises covering graph algorithms, polynomial root-finding, and solving systems of linear equations.

---

## Exercise 1 – Topological Sort (20%)

### Description
This program reads a directed graph from a `.txt` file, detects cycles, and produces a valid topological order if possible.

- **Input File Format:**  
<number_of_nodes> <number_of_edges>
u1 v1
u2 v2
...

- **Source:** `ECE325_2025_Assignment6_ex1_Team17.java`

### Notes
- Nodes in the input file are 1-indexed; the program internally processes them as 0-indexed.  
- If the graph contains a cycle, the program reports that a topological order cannot be produced.  
- If acyclic, a valid topological ordering of nodes is printed.

---

## Exercise 2 – Polynomial Root-Finding (30%)

### Description
This program reads a polynomial and an interval from a `.txt` file and finds approximate roots using the **Bisection** and **False Position** methods.

- **Input File Format:**  
a  
b  
ε  
B  
n  
c0  
c1  
...  
cn

Where:  
- `a, b` : interval endpoints (float)  
- `ε` : acceptable error (float)  
- `B` : maximum iterations (integer)  
- `n` : degree of polynomial (integer)  
- `c0 ... cn` : polynomial coefficients (float)  

- **Source:** `ECE325_2025_Assignment6_ex2_Team17.java`

### Notes
- Uses Horner’s rule for efficient polynomial evaluation.  
- Checks that `f(a)` and `f(b)` have opposite signs before starting root-finding.  
- Prints the approximate root, number of iterations, and `f(root)` for each method.

---

## Exercise 3 – Solving Systems of Linear Equations (50%)

### Description
This program reads a system of `n` equations with `n` unknowns from a `.txt` file and solves it using **Gaussian Elimination** or **LU Decomposition**.

- **Input File Format:**  
n  
a11 a12 ... a1n b1  
a21 a22 ... a2n b2  
...  
an1 an2 ... ann bn

Where:  
- `n` : number of equations (integer)  
- Each line contains `n` coefficients followed by the right-hand side `b` (float).  

- **Source:** `ECE325_2025_Assignment6_ex3_Team17.java`

### Notes
- Users choose either Gaussian Elimination or LU Decomposition at runtime.  
- Prints the solution vector `x` and execution time.  
- Pivoting is used in Gaussian Elimination to improve numerical stability.
