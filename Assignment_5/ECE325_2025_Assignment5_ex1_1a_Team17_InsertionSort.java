import java.io.*;
import java.util.*;

public class ECE325_2025_Assignment5_ex1_1a_Team17_InsertionSort {

    public static void insertionSort(int[] arr) {
        int n = arr.length;

        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            // Move elements greater than 'key' one position ahead
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }

            // Place the key at its correct position
            arr[j + 1] = key;
        }
    }

    public static int[] readFromFile() {
        List<Integer> numbers = new ArrayList<>();

        String filePath = "C:/Users/rest of filepath/numbers.txt"; // Update with your file path
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                for (String part : parts) {
                    if (!part.isEmpty()) {
                        numbers.add(Integer.parseInt(part));
                    }
                }
            }

            System.out.println("File read successfully: " + filePath);
            System.out.println("Total numbers read: " + numbers.size());

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found at path:\n" + filePath);
            return new int[0];
        } catch (NumberFormatException e) {
            System.out.println("Error: File contains non-numeric data!");
            return new int[0];
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return new int[0];
        }

        // Convert List<Integer> → int[]
        int[] arr = new int[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            arr[i] = numbers.get(i);
        }

        return arr;
    }


    public static void main(String[] args) {

        int[] data = readFromFile();

        if (data.length == 0) { // error opening file
            System.out.println("No data to sort. Exiting program.");
            return;
        }

        System.out.println("Read " + data.length + " numbers from file.\n");

        long startTime = System.nanoTime();

        insertionSort(data);

        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1_000_000.0; // calculate time of executing and convert to ms

        System.out.println("Time taken: " + elapsedTime + " ms");
    }
}