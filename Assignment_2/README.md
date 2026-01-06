# Assignment 02

This folder contains the source code for Assignment 02 of the course ECE 325.  
The assignment is divided into three independent problems, each focusing on fundamental data structures and iterative programming techniques.

---

## Problem 1 – Stack and Queue Implementation (30%)

### Description
This program implements both a FIFO queue and a LIFO stack.  
The user first selects the implementation type:
- Array-based
- Linked-list-based

If the array implementation is selected, the user specifies the array size, resulting in fixed memory allocation.  
If the linked list implementation is selected, dynamic memory allocation is used, allowing unlimited insertions.

In both cases, user input characters are stored simultaneously in both the queue and the stack.

### Special Commands
- `#`  
  Prompts for a number M, removes M elements from both the queue and the stack, prints the removed elements, and continues accepting input.
- `?`  
  Prompts the user to:
  1) Move one element from the queue to the stack  
  2) Move one element from the stack to the queue
- `;`  
  Prints all elements of both structures, clears allocated memory, and returns to the initial menu.
- `*`  
  Terminates the program.

### Data Structures
- Stack and queue implemented using arrays (manual index management)
- Stack and queue implemented using singly linked lists with dynamic memory allocation

---

## Problem 2 – Dictionary Word Validation (40%)

### Description
This program verifies whether a user-provided sentence can be constructed using only words from a dictionary file (`dictionary.txt`).

The user selects one of the following data structures:
1. Hash table using ASCII sum mod 20  
2. Hash table using ASCII sum mod 450  
3. Linked list

All dictionary words are loaded into the selected structure.  
The program then reads a sentence from input, checks each word, and outputs:
- `True` if all words are found
- `False` followed by the missing words otherwise

### Hash Function
The hash key is computed as:

(sum of ASCII values of characters in the word) mod N

where N is either 20 or 450.

### Observations
Hash tables provide significantly faster lookup times than linked lists.  
Using a larger modulus (450) reduces collisions and improves search performance.  
The linked list implementation performs a linear search and is noticeably slower.

---

## Problem 3 – Aircraft Routes Graph Representation (30%)

### Description
This problem models aircraft routes between countries using a weighted, undirected graph.

Each vertex represents a country.  
Each edge represents a direct aircraft route.  
The weight of each edge corresponds to the distance between two countries, measured by counting the number of grid rectangles crossed by the route in the given figure.

### Vertices
The graph contains 9 vertices:
- UK
- Germany
- Poland
- Austria
- France
- Spain
- Italy
- Greece
- Cyprus

### Adjacency Matrix

The adjacency matrix below follows the country order:  
UK, Germany, Poland, Austria, France, Spain, Italy, Greece, Cyprus

| Country   | UK | Ger | Pol | Aus | Fra | Spa | Ita | Gre | Cyp |
|-----------|----|-----|-----|-----|-----|-----|-----|-----|-----|
| UK        | 0  | 3   | 0   | 0   | 3   | 0   | 0   | 0   | 0   |
| Germany   | 3  | 0   | 3   | 4   | 5   | 0   | 0   | 0   | 0   |
| Poland    | 0  | 3   | 0   | 4   | 0   | 0   | 0   | 0   | 0   |
| Austria   | 0  | 4   | 4   | 0   | 0   | 0   | 3   | 6   | 0   |
| France    | 3  | 5   | 0   | 0   | 0   | 4   | 6   | 0   | 0   |
| Spain     | 0  | 0   | 0   | 0   | 4   | 0   | 5   | 0   | 11  |
| Italy     | 0  | 0   | 0   | 3   | 6   | 5   | 0   | 4   | 0   |
| Greece    | 0  | 0   | 0   | 6   | 0   | 0   | 4   | 0   | 4   |
| Cyprus    | 0  | 0   | 0   | 0   | 0   | 11  | 0   | 4   | 0   |

### Adjacency List

- UK → Germany (3), France (3)  
- Germany → UK (3), Poland (3), Austria (4), France (5)  
- Poland → Germany (3), Austria (4)  
- Austria → Germany (4), Poland (4), Italy (3), Greece (6)  
- France → UK (3), Germany (5), Spain (4), Italy (6)  
- Spain → France (4), Italy (5), Cyprus (11)  
- Italy → Austria (3), France (6), Spain (5), Greece (4)  
- Greece → Austria (6), Italy (4), Cyprus (4)  
- Cyprus → Spain (11), Greece (4)

---

## Notes
- All implementations are iterative.
- No built-in data structures or algorithms are used.
- Memory allocation and deallocation are handled manually.
- Code is written in C and intended for academic use.
