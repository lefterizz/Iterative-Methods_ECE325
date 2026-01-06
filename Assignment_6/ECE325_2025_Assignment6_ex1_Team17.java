import java.io.*;
import java.util.*;

public class ECE325_2025_Assignment6_ex1_Team17 {

    public static void TopologicalSort(ArrayList<ArrayList<Integer>> adj_list, int nodes){
        boolean[] visited = new boolean[nodes];
        boolean[] inRecursion = new boolean[nodes];
        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < nodes; i++){
            if(!visited[i]){
                if(!DFS_Visit(i, visited, inRecursion, adj_list, stack)){
                    System.out.println("\nThere is a cycle in the graph. Cannot perform Topological order.");
                    return;
                }
            }
        }

        System.out.println("\nThere is no cycle in the graph. The Topological order is:");

        List<Integer> TOrder = new ArrayList<>();
        while(!stack.isEmpty()){
            TOrder.add(stack.pop());
        }
        for (int node : TOrder) {
            System.out.print((node + 1) + " "); // print as 1-based
        }
    }

    public static boolean DFS_Visit(int node, boolean[] visited, boolean[] inRecursion, ArrayList<ArrayList<Integer>> adj_list, Stack<Integer> stack){
        visited[node] = true;
        inRecursion[node] = true;   //is in recursion

        for (int neighbor : adj_list.get(node)) {
            if (!visited[neighbor]) {
                if (!DFS_Visit(neighbor, visited, inRecursion, adj_list, stack))
                    return false;   //cycle detected
            } else if (inRecursion[neighbor]) {
                return false;       //cycle detected
            }
        }

        inRecursion[node] = false;      //is removed from recursion
        stack.push(node);
        return true;        //no cycles detected
    }

    public static void main(String[] args) {

        Scanner sc =  new Scanner(System.in);
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
                    int u = reader.nextInt() - 1;
                    int v = reader.nextInt() - 1;
                    adj_list.get(u).add(v);
                }
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            sc.close();
            return;
        }

        Stack<Integer> stack = new Stack<>();
        TopologicalSort(adj_list, nodes);
        System.out.println("\nExiting Program...\n");
    }
}
