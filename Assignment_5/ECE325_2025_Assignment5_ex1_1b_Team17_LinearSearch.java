import java.io.*;
import java.util.*;

public class ECE325_2025_Assignment5_ex1_1b_Team17_LinearSearch {
    
    public static int linearSearch(int[] array, int key) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == key) {
                return i; // key found at index i
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
            System.out.println("Size n of array: " + list.size());
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
        index = linearSearch(array, K);
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
