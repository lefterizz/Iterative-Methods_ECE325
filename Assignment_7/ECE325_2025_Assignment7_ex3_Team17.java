import java.io.*;
import java.util.*;

//Represents a weighted edge in the graph
class Edge {
    int destination;
    double weight;
    
    public Edge(int destination, double weight) {
        this.destination = destination;
        this.weight = weight;
    }
    
    @Override
    public String toString() {
        return "-> " + destination + " (weight: " + weight + ")";
    }
}

//Node class used in PriorityQueue for Dijkstra's algorithm
//Stores node ID and its current distance from source
class Node implements Comparable<Node> {
    int id;
    double distance;
    
    public Node(int id, double distance) {
        this.id = id;
        this.distance = distance;
    }
    
    @Override
    public int compareTo(Node other) {
        return Double.compare(this.distance, other.distance);
    }
}

//Main Graph class with adjacency list representation
public class ECE325_2025_Assignment7_ex3_Team17 {
    private int numNodes;
    private int numEdges;
    private Map<Integer, List<Edge>> adjacencyList;
    
    public ECE325_2025_Assignment7_ex3_Team17() {
        adjacencyList = new HashMap<>();
    }
    
    //Reads the graph from a text file
    public void readGraphFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        
        try {
            // Read number of nodes
            numNodes = Integer.parseInt(reader.readLine().trim());
            // Read number of edges
            numEdges = Integer.parseInt(reader.readLine().trim());
            
            // Initialize adjacency list for all nodes
            for (int i = 0; i < numNodes; i++) {
                adjacencyList.put(i, new ArrayList<>());
            }
            
            // Read edges
            for (int i = 0; i < numEdges; i++) {
                String line = reader.readLine();
                if (line == null) {
                    throw new IOException("File has fewer edges than specified");
                }
                
                String[] parts = line.trim().split("\\s+");
                if (parts.length != 3) {
                    throw new IOException("Invalid edge format: " + line);
                }
                
                int source = Integer.parseInt(parts[0]);
                int destination = Integer.parseInt(parts[1]);
                double weight = Double.parseDouble(parts[2]);
                
                if (weight < 0) {
                    throw new IOException("Negative weight detected: " + weight);
                }
                
                if (source < 0 || source >= numNodes || destination < 0 || destination >= numNodes) {
                    throw new IOException("Invalid node ID in edge: " + line);
                }
                
                // Add edge to adjacency list (directed graph)
                adjacencyList.get(source).add(new Edge(destination, weight));
            }
            
            System.out.println("Graph loaded successfully!");
            System.out.println("Nodes: " + numNodes);
            System.out.println("Edges: " + numEdges);
            
        } finally {
            reader.close();
        }
    }
    
     /*Dijkstra's algorithm to find shortest path from source to destination
     Returns an array where:
     distances[i] = shortest distance from source to node i
     predecessors[i] = previous node in shortest path to node i*/
    private class DijkstraResult {
        double[] distances;
        int[] predecessors;
        
        DijkstraResult(double[] distances, int[] predecessors) {
            this.distances = distances;
            this.predecessors = predecessors;
        }
    }
    
    private DijkstraResult dijkstra(int source) {
        // Initialize distances to infinity and predecessors to -1
        double[] distances = new double[numNodes];
        int[] predecessors = new int[numNodes];
        boolean[] visited = new boolean[numNodes];
        
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        Arrays.fill(predecessors, -1);
        
        // Distance to source is 0
        distances[source] = 0;
        
        // Priority queue to process nodes with smallest distance first
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(source, 0));
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.id;
            
            // Skip if already visited
            if (visited[u]) continue;
            visited[u] = true;
            
            // Process all neighbors of current node
            for (Edge edge : adjacencyList.get(u)) {
                int v = edge.destination;
                double weight = edge.weight;
                
                // Relaxation step
                if (!visited[v] && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    predecessors[v] = u;
                    pq.offer(new Node(v, distances[v]));
                }
            }
        }
        
        return new DijkstraResult(distances, predecessors);
    }

     //Computes and displays the shortest path from source to destination
    public void computeShortestPath(int source, int destination) {
        // Validate input
        if (source < 0 || source >= numNodes || destination < 0 || destination >= numNodes) {
            System.out.println("Error: Invalid node IDs. Nodes must be between 0 and " + (numNodes - 1));
            return;
        }
        
        System.out.println("\nComputing shortest path from " + source + " to " + destination + "...\n");
        
        // Run Dijkstra's algorithm
        DijkstraResult result = dijkstra(source);
        
        // Check if destination is reachable
        if (result.distances[destination] == Double.POSITIVE_INFINITY) {
            System.out.println("No path exists from " + source + " to " + destination);
            return;
        }
        
        // Reconstruct the path from destination to source
        List<Integer> path = new ArrayList<>();
        int current = destination;
        while (current != -1) {
            path.add(current);
            current = result.predecessors[current];
        }
        Collections.reverse(path);
        
        // Display the path with edge costs
        System.out.println("Shortest Path:");
        
        double totalCost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int from = path.get(i);
            int to = path.get(i + 1);
            
            // Find the edge weight between consecutive nodes in path
            double edgeWeight = 0;
            for (Edge edge : adjacencyList.get(from)) {
                if (edge.destination == to) {
                    edgeWeight = edge.weight;
                    break;
                }
            }
            
            System.out.printf("  %d -> %d  (cost: %.2f)%n", from, to, edgeWeight);
            totalCost += edgeWeight;
        }
        
        System.out.printf("Total Path Cost: %.2f%n", totalCost);
        System.out.println();
    }
    
    //Displays the graph structure
    public void displayGraph() {
        System.out.println("\nGraph Structure:");
        for (int i = 0; i < numNodes; i++) {
            System.out.print("Node " + i + ": ");
            List<Edge> edges = adjacencyList.get(i);
            if (edges.isEmpty()) {
                System.out.println("(no outgoing edges)");
            } else {
                for (Edge edge : edges) {
                    System.out.print(edge + "  ");
                }
                System.out.println();
            }
        }
        System.out.println();
    }
    
    public int getNumNodes() {
        return numNodes;
    }
    
     /*Computes the transitive closure using dynamic programming (Warshall's algorithm)
     T^(k)[i][j] = 1 if there exists a path from i to j using intermediate nodes {0, 1, ..., k-1}
     
     The recurrence relation is:
     T^(k)[i][j] = T^(k-1)[i][j] OR (T^(k-1)[i][k] AND T^(k-1)[k][j])*/
    public void computeTransitiveClosure() {
        System.out.println("Using Dynamic Programming Compute transitive closure\n");
        
        // Array to store all matrices T^(0), T^(1), ..., T^(n)
        // We'll use boolean for clarity (true = 1, false = 0)
        boolean[][][] T = new boolean[numNodes + 1][numNodes][numNodes];
        
        // Initialize T^(0): Base case - only direct edges
        System.out.println("Initializing T^(0) with direct edges...\n");
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                T[0][i][j] = false;
            }
            // Check if there's a direct edge from i to j
            for (Edge edge : adjacencyList.get(i)) {
                T[0][i][edge.destination] = true;
            }
        }
        
        // Display T^(0)
        System.out.println("T^(0) - Paths using no intermediate nodes (direct edges only):");
        displayMatrix(T[0]);
        
        // Compute T^(1), T^(2), ..., T^(n) using dynamic programming
        for (int k = 1; k <= numNodes; k++) {
            System.out.println("=================================================================");
            System.out.println("Computing T^(" + k + ") - Paths using intermediate nodes {0, 1, ..., " + (k-1) + "}");
            System.out.println("=================================================================");
            
            // Apply the recurrence relation
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    // T^(k)[i][j] = T^(k-1)[i][j] OR (T^(k-1)[i][k-1] AND T^(k-1)[k-1][j])
                    T[k][i][j] = T[k-1][i][j] || (T[k-1][i][k-1] && T[k-1][k-1][j]);
                }
            }
            
            // Show which new paths were discovered in this iteration
            displayMatrixWithChanges(T[k], T[k-1], k);
        }
        
        // Display final summary
        System.out.println("T^(" + numNodes + ")");
        System.out.println("\n==================================");
        displayMatrix(T[numNodes]);
        
        // Provide interpretation
        System.out.println("\nInterpretation:");
        System.out.println("  1 = Path exists from row node to column node");
        System.out.println("  0 = No path exists from row node to column node");
        
        // Summary statistics
        int totalPaths = 0;
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (T[numNodes][i][j]) totalPaths++;
            }
        }
        System.out.println("\nTotal reachable node pairs: " + totalPaths + " out of " + (numNodes * numNodes) + " possible");
        
        // Show reachability for each node
        System.out.println("\nReachability Summary:");
        for (int i = 0; i < numNodes; i++) {
            List<Integer> reachable = new ArrayList<>();
            for (int j = 0; j < numNodes; j++) {
                if (T[numNodes][i][j]) {
                    reachable.add(j);
                }
            }
            System.out.print("  Node " + i + " can reach: ");
            if (reachable.isEmpty()) {
                System.out.println("(no nodes)");
            } else {
                System.out.println(reachable);
            }
        }
        System.out.println();
    }
    
    //Displays a boolean matrix in a formatted way
    private void displayMatrix(boolean[][] matrix) {
        int n = matrix.length;
        
        // Print column headers
        System.out.print("\n     ");
        for (int j = 0; j < n; j++) {
            System.out.printf("%3d", j);
        }
        System.out.println();
        System.out.print("    " + "-".repeat(n * 3 + 1));
        System.out.println();
        
        // Print rows
        for (int i = 0; i < n; i++) {
            System.out.printf("%3d |", i);
            for (int j = 0; j < n; j++) {
                System.out.printf("%3s", matrix[i][j] ? "1" : "0");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    //Computes all-pairs shortest paths using the greedy approach (Dijkstra from each node)
    public void computeAllPairsShortestPathsGreedy() {
        System.out.println("\nGreedy Aproach, shortest path");
        
        // Result matrix to store shortest distances
        double[][] distances = new double[numNodes][numNodes];
        int[][] nextNode = new int[numNodes][numNodes]; // For path reconstruction
        
        // Run Dijkstra from each node
        for (int source = 0; source < numNodes; source++) {
            DijkstraResult result = dijkstra(source);
            
            // Store results
            for (int dest = 0; dest < numNodes; dest++) {
                distances[source][dest] = result.distances[dest];
                nextNode[source][dest] = result.predecessors[dest];
            }
        }
        
        System.out.println("All-Pairs Shortest Paths Matrix:");
        System.out.println("==================================");
        displayDistanceMatrix(distances);
        
        // Provide interpretation
        System.out.println("  Entry [i][j] = shortest distance from node i to node j");
        System.out.println("  ∞ = no path exists from i to j");
        
        // Show some example paths
        System.out.println("\nExample shortest paths:");
        for (int i = 0; i < Math.min(3, numNodes); i++) {
            for (int j = 0; j < Math.min(3, numNodes); j++) {
                if (i != j && distances[i][j] != Double.POSITIVE_INFINITY) {
                    System.out.printf("  %d → %d: distance = %.2f%n", i, j, distances[i][j]);
                }
            }
        }
        System.out.println();
    }
    
    /*Computes all-pairs shortest paths using dynamic programming (Floyd-Warshall)
     D^(k)[i][j] = shortest path from i to j using intermediate nodes {0, 1, ..., k-1}*/
    public void computeAllPairsShortestPathsDP() {
        System.out.println("\nDynamic Programming, shortest path");
        
        // Array to store all distance matrices D^(0), D^(1), ..., D^(n)
        double[][][] D = new double[numNodes + 1][numNodes][numNodes];
        
        // Initialize D^(0): Base case - direct edges only
        System.out.println("Initializing D^(0) with direct edge weights...\n");
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (i == j) {
                    D[0][i][j] = 0; // Distance to self is 0
                } else {
                    D[0][i][j] = Double.POSITIVE_INFINITY; // Initially no path
                }
            }
            
            // Set distances for direct edges
            for (Edge edge : adjacencyList.get(i)) {
                D[0][i][edge.destination] = edge.weight;
            }
        }
        
        // Display D^(0)
        System.out.println("D^(0) - Shortest paths using no intermediate nodes (direct edges only):");
        displayDistanceMatrix(D[0]);
        
        // Compute D^(1), D^(2), ..., D^(n) using dynamic programming
        for (int k = 1; k <= numNodes; k++) {
            System.out.println("\n=================================================================");
            System.out.println("Computing D^(" + k + ") - Shortest paths using intermediate nodes {0, 1, ..., " + (k-1) + "}");
            System.out.println("=================================================================");
            
            // Apply the Floyd-Warshall recurrence relation
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    // D^(k)[i][j] = min(D^(k-1)[i][j], D^(k-1)[i][k-1] + D^(k-1)[k-1][j])
                    double directPath = D[k-1][i][j];
                    double pathViaK = D[k-1][i][k-1] + D[k-1][k-1][j];
                    
                    D[k][i][j] = Math.min(directPath, pathViaK);
                }
            }
            
            // Show which paths were improved in this iteration
            displayDistanceMatrixWithChanges(D[k], D[k-1], k);
        }
        
        // Display final summary
        System.out.println("\n=================================================================");
        System.out.println("Final Distance Matrix - D^(" + numNodes + ")");
        System.out.println("All-Pairs Shortest Paths");
        System.out.println("=================================================================");
        displayDistanceMatrix(D[numNodes]);
        
        // Check for negative cycles
        boolean hasNegativeCycle = false;
        for (int i = 0; i < numNodes; i++) {
            if (D[numNodes][i][i] < 0) {
                hasNegativeCycle = true;
                break;
            }
        }
        
        if (hasNegativeCycle) {
            System.out.println("\nNegative cycle detected in the graph!");
        }
        
        // Provide interpretation
        System.out.println("\nInterpretation:");
        System.out.println("  Entry [i][j] = shortest distance from node i to node j");
        System.out.println("  ∞ = no path exists from i to j");
        
        // Summary statistics
        int reachablePairs = 0;
        double totalDistance = 0;
        int finitePaths = 0;
        
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (i != j && D[numNodes][i][j] != Double.POSITIVE_INFINITY) {
                    reachablePairs++;
                    totalDistance += D[numNodes][i][j];
                    finitePaths++;
                }
            }
        }
        
        System.out.println("\nStatistics:");
        System.out.printf("  Reachable pairs: %d out of %d possible%n", reachablePairs, numNodes * (numNodes - 1));
        if (finitePaths > 0) {
            System.out.printf("  Average shortest path length: %.2f%n", totalDistance / finitePaths);
        }
        System.out.println();
    }
    
    //Displays a distance matrix in a formatted way
    private void displayDistanceMatrix(double[][] matrix) {
        int n = matrix.length;
        
        // Print column headers
        System.out.print("\n       ");
        for (int j = 0; j < n; j++) {
            System.out.printf("%7d", j);
        }
        System.out.println();
        System.out.print("      " + "-".repeat(n * 7 + 1));
        System.out.println();
        
        // Print rows
        for (int i = 0; i < n; i++) {
            System.out.printf("%5d |", i);
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == Double.POSITIVE_INFINITY) {
                    System.out.printf("%7s", "∞");
                } else {
                    System.out.printf("%7.1f", matrix[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    //Displays a distance matrix and highlights changes from the previous iteration
    private void displayDistanceMatrixWithChanges(double[][] current, double[][] previous, int k) {
        int n = current.length;
        List<String> improvements = new ArrayList<>();
        double totalImprovement = 0;
        
        // Print column headers
        System.out.print("\n       ");
        for (int j = 0; j < n; j++) {
            System.out.printf("%7d", j);
        }
        System.out.println();
        System.out.print("      " + "-".repeat(n * 7 + 1));
        System.out.println();
        
        // Print rows and identify changes
        for (int i = 0; i < n; i++) {
            System.out.printf("%5d |", i);
            for (int j = 0; j < n; j++) {
                boolean improved = current[i][j] < previous[i][j];
                
                if (current[i][j] == Double.POSITIVE_INFINITY) {
                    System.out.printf("%7s", "∞");
                } else {
                    String valueStr;
                    if (improved) {
                        valueStr = String.format("%.1f*", current[i][j]);
                        double improvement = previous[i][j] - current[i][j];
                        improvements.add(String.format("(%d → %d): %.1f → %.1f (saved %.1f)", 
                            i, j, previous[i][j], current[i][j], improvement));
                        totalImprovement += improvement;
                    } else {
                        valueStr = String.format("%.1f", current[i][j]);
                    }
                    System.out.printf("%7s", valueStr);
                }
            }
            System.out.println();
        }
        
        // Explain the changes
        if (!improvements.isEmpty()) {
            System.out.println("\n  * = Path improved in this iteration");
            System.out.println("  Improvements by using node " + (k-1) + " as intermediate:");
            for (String improvement : improvements) {
                System.out.println("    " + improvement);
            }
            System.out.printf("  Total distance saved: %.1f%n", totalImprovement);
            
            // Explain HOW the improvements were found
            System.out.println("\n  How it works:");
            System.out.println("    For each improved path (i → j), we found that:");
            System.out.printf("    (i → %d) + (%d → j) < (i → j) direct%n", k-1, k-1);
        } else {
            System.out.println("\n  No improvements found using node " + (k-1) + " as intermediate.");
        }
        System.out.println();
    }
    
    //Displays a matrix and highlights changes from the previous iteration
    private void displayMatrixWithChanges(boolean[][] current, boolean[][] previous, int k) {
        int n = current.length;
        List<String> newPaths = new ArrayList<>();
        
        // Print column headers
        System.out.print("\n     ");
        for (int j = 0; j < n; j++) {
            System.out.printf("%3d", j);
        }
        System.out.println();
        System.out.print("    " + "-".repeat(n * 3 + 1));
        System.out.println();
        
        // Print rows and identify changes
        for (int i = 0; i < n; i++) {
            System.out.printf("%3d |", i);
            for (int j = 0; j < n; j++) {
                String value = current[i][j] ? "1" : "0";
                
                // Check if this is a newly discovered path
                if (current[i][j] && !previous[i][j]) {
                    System.out.printf("%3s", value + "*"); // Mark new paths with *
                    newPaths.add(String.format("(%d → %d)", i, j));
                } else {
                    System.out.printf("%3s", value);
                }
            }
            System.out.println();
        }
        
        // Explain the changes
        if (!newPaths.isEmpty()) {
            System.out.println("\n  * = New path discovered in this iteration");
            System.out.println("  New paths found by using node " + (k-1) + " as intermediate: " + newPaths);
            
            // Explain HOW each new path was found
            System.out.println("\n  Explanation:");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (current[i][j] && !previous[i][j]) {
                        System.out.printf("    Path %d → %d exists because: ", i, j);
                        if (previous[i][k-1] && previous[k-1][j]) {
                            System.out.printf("(%d → %d) AND (%d → %d) both exist%n", 
                                i, k-1, k-1, j);
                        }
                    }
                }
            }
        } else {
            System.out.println("\n  No new paths discovered using node " + (k-1) + " as intermediate.");
        }
        System.out.println();
    }
    
    //Main method to run the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ECE325_2025_Assignment7_ex3_Team17 graph = new ECE325_2025_Assignment7_ex3_Team17();
        
        // Read graph from file
        System.out.print("Enter the filename containing the graph: ");
        String filename = scanner.nextLine().trim();
        
        try {
            graph.readGraphFromFile(filename);
            graph.displayGraph();
            
            // Main menu loop
            while (true) {
                System.out.println("\n======== MENU ========");
                System.out.println("a) Compute shortest path (Dijkstra's algorithm)");
                System.out.println("b) Compute transitive closure (Dynamic Programming)");
                System.out.println("c) Compute all-pairs shortest paths");
                System.out.println("q) Quit");
                System.out.print("\nSelect an operation: ");
                
                String choice = scanner.nextLine().trim().toLowerCase();
                
                if (choice.equals("q")) {
                    System.out.println("Exiting program...");
                    break;
                }
                
                switch (choice) {
                    case "a":
                        System.out.print("Enter source node: ");
                        int source = Integer.parseInt(scanner.nextLine().trim());
                        
                        System.out.print("Enter destination node: ");
                        int destination = Integer.parseInt(scanner.nextLine().trim());
                        
                        graph.computeShortestPath(source, destination);
                        break;
                    
                    case "b":
                        graph.computeTransitiveClosure();
                        break;
                    
                    case "c":
                        System.out.println("\nSelect approach for all-pairs shortest paths:");
                        System.out.println("  1) Greedy approach (Run Dijkstra from each node)");
                        System.out.println("  2) Dynamic Programming approach (Floyd-Warshall)");
                        System.out.print("Enter choice (1/2): ");
                        
                        String approach = scanner.nextLine().trim().toLowerCase();
                        
                        if (approach.equals("1")) {
                            graph.computeAllPairsShortestPathsGreedy();
                        } else if (approach.equals("2")) {
                            graph.computeAllPairsShortestPathsDP();
                        } else {
                            System.out.println("Invalid approach selected.");
                        }
                        break;
                        
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: File '" + filename + "' not found.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format in file or input.");
        } finally {
            scanner.close();
        }
    }
}