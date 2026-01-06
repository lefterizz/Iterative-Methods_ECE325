# Assignment 08
This folder contains the source code for **Assignment 08** of the course **ECE 325 – Iterative Methods**.  
The assignment involves implementing the **Simplex Method** to solve a linear programming problem for a simplified maintenance scheduling scenario.

---

## Exercise – Maintenance Scheduling Problem (100%)

### Description
This program schedules maintenance time for three sub-systems (A, B, C) under constraints on total maintenance hours, system throughput degradation, and relative allocation rules.  
The goal is to **maximize the remaining operational life of the system**.

- **Decision Variables:**  
  - `x1` : Time (hours) allocated to sub-system A  
  - `x2` : Time (hours) allocated to sub-system B  
  - `x3` : Time (hours) allocated to sub-system C  

- **Constraints:**  
  1. Total maintenance hours:  
     `x1 + x2 + x3 ≤ H`
  2. Throughput degradation:  
     `D1*x1 + D2*x2 + D3*x3 ≤ T`
  3. Relative allocation:  
     `x2 − 2*(x1 + x3) ≤ 0`
  4. Nonnegativity:  
     `x1 ≥ 0, x2 ≥ 0, x3 ≥ 0`

- **Objective Function:**  
  `maximize Z = r1*x1 + r2*x2 + r3*x3`  
  where `r1, r2, r3` are the operational life increases per hour of maintenance on subsystems A, B, and C.

- **Source File:** `ECE325_2025_Assignment8_Team17.java`

---

### Input File Format
The program reads parameters from a `.txt` file. Each parameter should be provided on a separate line:

| Parameter | Description |
|-----------|-------------|
| H         | Maximum total maintenance hours |
| T         | Maximum allowable throughput degradation (minutes) |
| D1        | Degradation caused by 1 hour of maintenance on sub-system A (minutes) |
| D2        | Degradation caused by 1 hour of maintenance on sub-system B (minutes) |
| D3        | Degradation caused by 1 hour of maintenance on sub-system C (minutes) |
| r1        | Operational life increase per hour of maintenance on sub-system A |
| r2        | Operational life increase per hour of maintenance on sub-system B |
| r3        | Operational life increase per hour of maintenance on sub-system C |

Example values for Scenario 1:

| H  | T   | D1 | D2 | D3 | r1 | r2 | r3 |
|----|-----|----|----|----|----|----|----|
| 60 | 250 | 8  | 4  | 3  | 6  | 5  | 2  |

Example values for Scenario 2:

| H  | T   | D1  | D2 | D3  | r1 | r2 | r3 |
|----|-----|-----|----|-----|----|----|----|
| 75 | 150 | 1.5 | 2  | 3.5 | 3  | 6  | 7  |

---

### Notes
- The program implements the **Simplex Method** from scratch (no built-in solvers allowed).  
- Includes pivoting and tableau updates for numerical stability.  
- Prints:
  - Optimal values of `x1`, `x2`, `x3`
  - Maximum objective function `Z`
- Validates feasibility of constraints and detects unbounded or infeasible solutions.  
- Typical test scenarios correspond to the two sets of parameters provided above.  
