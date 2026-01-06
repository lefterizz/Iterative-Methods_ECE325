import java.io.*;
import java.util.*;

public class ECE325_2025_Assignment5_ex1_2_Team17_BinarySearch {
    public static int binarySearch(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == key) {
                return mid; // key found at index mid
            } else if (array[mid] < key) {
                left = mid + 1; // Search in the right half
            } else {
                right = mid - 1; // Search in the left half
            }
        }
        return -1; // key not found
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        int K = 0, index;

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the filename: ");
        String filename = sc.nextLine();

        try {
            // Open the file entered by user
            File file = new File(filename);
            Scanner reader = new Scanner(file);

            // Read integers from file
            while (reader.hasNextInt()) {
                list.add(reader.nextInt());
            }

            reader.close();
            System.out.println("File read successfully!");
            System.out.println("Size n of sorted array: " + list.size());
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found. Please check the filename and try again.");
            e.printStackTrace();
            sc.close();
            return;
        }

        int[] array = list.stream().mapToInt(i -> i).toArray();

        System.out.print("Enter the key (integer): ");
        K = sc.nextInt();

        long startTime, endTime, duration;

            startTime = System.nanoTime();
            index = binarySearch(array, K);
            endTime = System.nanoTime();

        duration = endTime - startTime;

        if (index != -1)
            System.out.println("Key K found at index: " + index);
        else
            System.out.println("Key K not found in the array.");

        System.out.println("Time taken (in nanoseconds): " + duration);
        sc.close();
    }
}
