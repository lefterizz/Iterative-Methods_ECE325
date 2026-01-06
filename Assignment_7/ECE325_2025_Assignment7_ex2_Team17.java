import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ECE325_2025_Assignment7_ex2_Team17 {

    static class Edge {
        int u, v, w;
    }

    // Custom MinHeap for Prim
    static class MinHeap {
        Edge[] heap;
        int size;

        MinHeap(int capacity) {
            heap = new Edge[capacity + 1];
            size = 0;
        }

        void swap(int a, int b) {
            Edge temp = heap[a];
            heap[a] = heap[b];
            heap[b] = temp;
        }

        void insert(Edge e) {
            heap[++size] = e;
            int i = size;
            while (i > 1 && heap[i].w < heap[i / 2].w) {
                swap(i, i / 2);
                i /= 2;
            }
        }

        Edge extractMin() {
            if (size == 0) return null;
            Edge min = heap[1];
            heap[1] = heap[size--];

            int i = 1;
            while (true) {
                int left = i * 2, right = left + 1, smallest = i;

                if (left <= size && heap[left].w < heap[smallest].w)
                    smallest = left;

                if (right <= size && heap[right].w < heap[smallest].w)
                    smallest = right;

                if (smallest == i) break;

                swap(i, smallest);
                i = smallest;
            }
            return min;
        }
    }

    // Selection sort for Kruskal
    static void selectionSort(Edge[] edges, int m) {
        for (int i = 0; i < m - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < m; j++) {
                if (edges[j].w < edges[minIndex].w)
                    minIndex = j;
            }
            Edge temp = edges[i];
            edges[i] = edges[minIndex];
            edges[minIndex] = temp;
        }
    }

    // DSU for Kruskal
    static int find(int[] parent, int x) {
        if (parent[x] != x) parent[x] = find(parent, parent[x]);
        return parent[x];
    }

    static void union(int[] parent, int[] rank, int a, int b) {
        a = find(parent, a);
        b = find(parent, b);

        if (rank[a] < rank[b]) parent[a] = b;
        else if (rank[a] > rank[b]) parent[b] = a;
        else {
            parent[b] = a;
            rank[a]++;
        }
    }

    // Print Min spanning tree
    static void printMST(Edge[] mst, int count) {
        int total = 0;
        System.out.println("\nMST Edges:");
        for (int i = 0; i < count; i++) {
            System.out.println(mst[i].u + " -- " + mst[i].v + " (cost: " + mst[i].w + ")");
            total += mst[i].w;
        }
        System.out.println("Total MST Cost: " + total);
    }

    static void prim(Edge[] allEdges, int n, int m) {
        long start = System.nanoTime();

        ArrayList<ArrayList<Edge>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            adj.add(new ArrayList<>());

        // Build adjacency list
        for (int i = 0; i < m; i++) {
            Edge e = allEdges[i];
            adj.get(e.u).add(e);

            Edge rev = new Edge();
            rev.u = e.v;
            rev.v = e.u;
            rev.w = e.w;
            adj.get(e.v).add(rev);
        }

        boolean[] visited = new boolean[n + 1];
        Edge[] mst = new Edge[n - 1];
        int mstIndex = 0;

        MinHeap heap = new MinHeap(m * 2);

        // Start Prim at node 1
        int startNode = 1;
        visited[startNode] = true;

        for (Edge e : adj.get(startNode)) {
            heap.insert(e);
        }

        while (mstIndex < n - 1) {
            Edge e = heap.extractMin();
            if (e == null) break;

            if (visited[e.v]) continue;

            visited[e.v] = true;
            mst[mstIndex++] = e;

            for (Edge next : adj.get(e.v)) {
                if (!visited[next.v])
                    heap.insert(next);
            }
        }

        long end = System.nanoTime();
        printMST(mst, mstIndex);
        System.out.printf("Prim Execution Time: %.4f ms\n", (end - start) / 1_000_000.0);
    }

    static void kruskal(Edge[] edges, int n, int m) {
        long start = System.nanoTime();

        selectionSort(edges, m);

        int[] parent = new int[n + 1];
        int[] rank = new int[n + 1];

        for (int i = 1; i <= n; i++)
            parent[i] = i;

        Edge[] mst = new Edge[n - 1];
        int mstIndex = 0;

        for (int i = 0; i < m && mstIndex < n - 1; i++) {
            Edge e = edges[i];
            if (find(parent, e.u) != find(parent, e.v)) {
                mst[mstIndex++] = e;
                union(parent, rank, e.u, e.v);
            }
        }

        long end = System.nanoTime();
        printMST(mst, mstIndex);
        System.out.printf("Kruskal Execution Time: %.4f ms\n", (end - start) / 1_000_000.0);
    }

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter graph filename: ");
        String file = scan.nextLine();

        Scanner sc = new Scanner(new File(file));
        int nodesNo = sc.nextInt();
        int edgesNo = sc.nextInt();

        Edge[] edges = new Edge[edgesNo];
        for (int i = 0; i < edgesNo; i++) {
            edges[i] = new Edge();
            edges[i].u = sc.nextInt();
            edges[i].v = sc.nextInt();
            edges[i].w = sc.nextInt();
        }
        sc.close();

        System.out.println("Choose algorithm:");
        System.out.println("1) Prim");
        System.out.println("2) Kruskal");
        int choice = scan.nextInt();

        if (choice == 1)
            prim(edges, nodesNo, edgesNo);
        else if (choice == 2)
            kruskal(edges, nodesNo, edgesNo);
        else
            throw new Exception("Wrong choice");
    }
}
