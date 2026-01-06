import java.io.*;
import java.util.*;

public class ECE325_2025_Assignment4_ex1_Team17 {

    public static void selectionSort(int[] arr) { // Brute force
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    public static void mergeSort(int[] arr, int left, int right) { // Divide and conquer
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        // Copy data into temp arrays
        for (int i = 0; i < n1; i++) {
            L[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        // Merge halfes
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // place remaining elements
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static int[] readFromFile(String filename){
        ArrayList<Integer> numbers = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                for (String part : parts) {
                    if (!part.isEmpty()) {
                        numbers.add(Integer.parseInt(part));
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error: File not found or invalid data!");
            return new int[0]; // return empty array if error
        }

        // ArrayList to int array
        int[] arr = new int[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            arr[i] = numbers.get(i);
        }
        return arr;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter input filename: ");
        String inputFile = scanner.nextLine();

        int[] data = readFromFile(inputFile);

        if (data.length == 0) { // error opening file
            System.out.println("No data to sort. Exiting program.");
            scanner.close();
            return;
        }

        System.out.println("Read " + data.length + " numbers from file.\n");
        System.out.println("Choose sorting method:");
        System.out.println("1) Brute Force (Selection sort)");
        System.out.println("2) Divide & Conquer (Merge sort)");
        System.out.print("Enter choice (1 or 2): ");
        int choice = scanner.nextInt();

        long startTime = System.nanoTime();

        if (choice == 1) {
            System.out.println("\nUsing Selection sort...");
            selectionSort(data);
        } else if (choice == 2) {
            System.out.println("\nUsing Merge sort...");
            mergeSort(data, 0, data.length - 1);
        } else {
            System.out.println("Invalid choice!");
            scanner.close();          
            return;
        }

        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1_000_000.0; // calculate time of executing and convert to ms

        // Print sorted array
        System.out.println("\nSorted list:");
        for (int num : data) {
            System.out.print(num + "\n");
        }

        System.out.println("\nTime taken: " + elapsedTime + " ms");
        scanner.close();
    }
}