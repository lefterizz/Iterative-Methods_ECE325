# Assignment 07
This folder contains the source code for **Assignment 07** of the course **ECE 325 – Graph Algorithms and Greedy Methods**.  
The assignment consists of three exercises covering greedy algorithms, minimum spanning trees, and shortest path computations.

---

## Exercise 1 – Greedy Coin Change (20%)

### Description
This program reads coin denominations from a `.txt` file, asks the user for an amount `M` (in cents), and computes the minimum number of coins needed using a **greedy algorithm**.

- **Input File Format:**  
<number_of_denominations>  
d1 d2 d3 ... dn

- **Source:** `ECE325_2025_Assignment7_ex1_Team17.java`

### Notes
- Coins are sorted in ascending order internally.  
- The greedy algorithm selects the largest coin denomination first, then proceeds to smaller denominations.  
- Prints the number of coins used per denomination.  

---

## Exercise 2 – Minimum Spanning Tree (30%)

### Description
This program reads an undirected weighted graph from a `.txt` file and computes a **Minimum Spanning Tree (MST)** using either **Prim’s algorithm** or **Kruskal’s algorithm**.

- **Input File Format:**  
<number_of_nodes> <number_of_edges>  
u1 v1 w1  
u2 v2 w2  
...  
um vm wm

Where `u` and `v` are node indices (1-indexed) and `w` is the edge weight.

- **Source:** `ECE325_2025_Assignment7_ex2_Team17.java`

### Notes
- **Prim’s algorithm:** Uses a custom MinHeap to select edges with minimum weight.  
- **Kruskal’s algorithm:** Uses a custom **Disjoint Set Union (DSU)** for cycle detection and **selection sort** to order edges by weight.  
- Prints the MST edges, total cost, and execution time.

---

## Exercise 3 – Directed Weighted Graph Operations (50%)

### Description
This program reads a directed weighted graph from a `.txt` file and provides the following functionalities:

1. **Shortest Path (Dijkstra’s algorithm)**  
2. **Transitive Closure (Dynamic Programming / Warshall’s algorithm)**  
3. **All-Pairs Shortest Paths**  
   - Greedy approach: Dijkstra from each node  
   - Dynamic Programming approach: Floyd-Warshall

- **Input File Format:**  
<number_of_nodes>  
<number_of_edges>  
u1 v1 w1  
u2 v2 w2  
...  
um vm wm

Where `u` and `v` are node indices (0-indexed) and `w` is the edge weight (non-negative double).

- **Source:** `ECE325_2025_Assignment7_ex3_Team17.java`

### Notes
- Implements a **Node** class for the priority queue in Dijkstra’s algorithm.  
- Displays graph structure, shortest paths, transitive closure matrices, and all-pairs shortest path matrices.  
- Highlights improvements in distances and newly discovered paths at each iteration.  
- Handles unreachable nodes and validates file input for consistency and non-negative weights.
