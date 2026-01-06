import java.io.*;
import java.util.*;

public class ECE325_2025_Assignment4_ex2_Team17 {

    // Linear search
    public static int linearSearch(List<Integer> list, int key) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == key) {
                return i; // key found at index i
            }
        }
        return -1; // key not found
    }

    // Binary search
    public static int binarySearch(List<Integer> list, int key) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midVal = list.get(mid);

            if (midVal == key) {
                return mid; // key found at index mid
            } else if (midVal < key) {
                left = mid + 1; // search right half
            } else {
                right = mid - 1; // search left half
            }
        }
        return -1; // key not found
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        int K = 0, index;

        try {
            // Open the file
            File file = new File("sorted_list.txt");
            Scanner reader = new Scanner(file);

            // Read each number
            while (reader.hasNextInt()) {
                int input = reader.nextInt();
                list.add(input);
            }

            reader.close(); // Close file
            System.out.println("File read successfully!");
            System.out.println("Size n of sorted list: " + list.size());
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return;
        }

        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter the key (integer): ");
        K = sc.nextInt();
        System.out.print("Choose approach: 1) Linear Search  2) Binary Search: ");
        int approach = sc.nextInt();

        sc.close();

        long startTime, endTime, duration;

        if (approach == 1) {
            startTime = System.nanoTime();
            index = linearSearch(list, K);
            endTime = System.nanoTime();
        } else if (approach == 2) {
            startTime = System.nanoTime();
            index = binarySearch(list, K);
            endTime = System.nanoTime();
        } else {
            System.out.println("Invalid approach selected.");
            return;
        }

        duration = endTime - startTime;

        if (index != -1) {
            System.out.println("Key K found at index: " + index);
        } else {
            System.out.println("Key K not found in the list.");
        }

        System.out.println("Time taken (in nanoseconds): " + duration);
    }
}
