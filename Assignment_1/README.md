# Assignment 01

This folder contains the source code for **Assignment 01** of the course **ECE 325 – Iterative Methods**.  
The assignment consists of three independent C programs, each addressing a different problem focused on iterative algorithm design, correctness, and efficiency.

---

## Problem 1 – Common Elements of Two Sorted Lists

### Description
This program reads two **sorted lists of integers** from standard input.  
Each list is terminated by the character `#`.

The program:
- Validates that both input lists are sorted
- Finds all **common elements** between the two lists
- Counts the number of **comparisons** performed
- Computes the **sum of the common elements**

### Algorithmic Approach
- Uses two indices, one for each list
- Advances indices based on element comparison
- Runs until the end of either list is reached

### Complexity
- **Time Complexity:**  
  \( O(m + n) \), where \( m \) and \( n \) are the sizes of the two lists  
- **Maximum Comparisons:**  
  At most \( m + n - 1 \)

---

## Problem 2 – Operations on an Array of Integers

### Description
This program performs several operations on a **global array of 10 integers**.

Implemented functions:
- Compute the average of all elements
- Compute the difference between maximum and minimum values
- Find the index of the maximum element
- Extract unique elements
- Square each element
- Sort the array using **insertion sort**

### Algorithmic Approach
- All operations are implemented iteratively
- Sorting is performed manually using insertion sort
- No built-in data structures or algorithms are used

### Complexity
- **Average, min/max, index search:** \( O(n) \)
- **Unique element extraction:** \( O(n^2) \)
- **Insertion sort:** \( O(n^2) \), with \( n = 10 \)

---

## Problem 3 – Student Competition Records Management

### Description
This program manages participant records using a **structure** and a menu-driven interface.

Each record includes:
- ID
- Name
- Number of previous participations
- Project 1 score (60% weight)
- Project 2 score (40% weight)
- Total weighted score

### Supported Operations
- Add, delete, and update participant records
- Save records to a `.txt` file
- Load records from a `.txt` file
- View all participants
- Filter participants by number of participations
- Display participant with the highest total score
- Compute average total score

### Algorithmic Approach
- Records are stored in a fixed-size array
- File I/O uses plain text with comma-separated values
- All operations are implemented iteratively

### Complexity
- **Add / Update / View:** \( O(1) \) to \( O(n) \)
- **Delete / Search:** \( O(n) \)
- **Highest score / Average:** \( O(n) \)

---

## Notes
- All programs are written in **C**
- No built-in algorithms (e.g., sorting) are used
- Input validation is included where required
- The code is intended for educational and academic use
