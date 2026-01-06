import java.io.*;
import java.util.*;

public class ECE325_2025_Assignment5_ex2_Team17 {


    public static void DFS_Visit(int v, boolean[] visited, ArrayList<ArrayList<Integer>> adj_list) {
        visited[v] = true;
        System.out.print(v + " "); // print current node

        for (int neighbor : adj_list.get(v)) { // go through all neighbors
            if (!visited[neighbor]) {
                DFS_Visit(neighbor, visited, adj_list);
            }
        }
    }

    public static void BFS(int start, ArrayList<ArrayList<Integer>> adj_list) {
        boolean[] visited = new boolean[adj_list.size()];
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int v = queue.poll();
            System.out.print(v + " ");

            for (int neighbor : adj_list.get(v)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }

    // Main program
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Choose DFS or BFS
        System.out.print("Please select a) for Depth-First Search or b) for Breadth-First Search: ");
        char c = sc.next().charAt(0);
        while (c != 'a' && c != 'b') {
            System.out.print("Invalid input. Please select a) for Depth-First Search or b) for Breadth-First Search: ");
            c = sc.next().charAt(0);
        }

        // Ask for file name
        System.out.print("Enter the file name: ");
        String name = sc.next();

        ArrayList<ArrayList<Integer>> adj_list = new ArrayList<>();
        int nodes = 0, edges = 0;

        try {
            Scanner reader = new Scanner(new File(name));

            // Read first line 
            if (reader.hasNextInt()) {
                nodes = reader.nextInt();
                edges = reader.nextInt();

                // initialize adjacency list
                for (int i = 0; i < nodes; i++) {
                    adj_list.add(new ArrayList<>());
                }


                for (int i = 0; i < edges; i++) {
                    int u = reader.nextInt();
                    int v = reader.nextInt();
                    adj_list.get(u).add(v);
                    adj_list.get(v).add(u); 
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            sc.close();
            return;
        }

        boolean[] visited = new boolean[nodes];

        if (c == 'a') {
            System.out.println("\nDepth-First Search order:");
            DFS_Visit(0, visited, adj_list); // start from node 0
        } else {
            System.out.println("\nBreadth-First Search order:");
            BFS(0, adj_list);
        }

        System.out.println(); // new line at end
        sc.close();
    }
}
