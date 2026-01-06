import java.io.*;
import java.util.*;

public class ECE325_2025_Assignment5_ex1_3_Team17_QuickSelect{

    // Reads integers (space or line separated) from a file

    private static int[] readArrayFromFile(String filename) {
        List<Integer> list = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextInt()) {
                list.add(fileScanner.nextInt());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
            return null;
        }

        // Convert List<Integer> to int[]
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }


    // Quickselect core function

    public static int quickSelect(int[] arr, int k) {
        return quickSelectHelper(arr, 0, arr.length - 1, k - 1);
    }

    private static int quickSelectHelper(int[] arr, int left, int right, int kIndex) {
        if (left == right) {
            return arr[left];
        }

        Random rand = new Random();
        int pivotIndex = left + rand.nextInt(right - left + 1);
        swap(arr, pivotIndex, right);

        int pivotFinal = partition(arr, left, right);

        if (pivotFinal == kIndex) {
            return arr[pivotFinal];
        } else if (kIndex < pivotFinal) {
            return quickSelectHelper(arr, left, pivotFinal - 1, kIndex);
        } else {
            return quickSelectHelper(arr, pivotFinal + 1, right, kIndex);
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left;
        for (int j = left; j < right; j++) {
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right);
        return i;
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    // Main program

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Ask for the filename
        System.out.print("Enter the file name : ");
        String filename = input.nextLine();

        // Read data from file
        int[] data = readArrayFromFile(filename);
        if (data == null || data.length == 0) {
            System.out.println("File is empty or not found!");
            return;
        }

        System.out.println("File loaded successfully. Array size = " + data.length);

        // Ask for k
        System.out.print("Enter k (which smallest element to find): ");
        int k = input.nextInt();

        if (k < 1 || k > data.length) {
            System.out.println("Invalid k value!");
            return;
        }

        // Measure time
        long start = System.nanoTime();
        int result = quickSelect(data, k);
        long end = System.nanoTime();

        System.out.println("\n" + k + "th smallest element = " + result);
        System.out.printf("Running time: %.3f ms\n", (end - start) / 1e6);
    }
}
