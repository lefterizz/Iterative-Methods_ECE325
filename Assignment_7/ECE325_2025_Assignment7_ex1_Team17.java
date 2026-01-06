import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ECE325_2025_Assignment7_ex1_Team17 {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(System.in);

        // --- Read the file ---
        System.out.print("Enter file name: ");
        String fileName = sc.nextLine();

        Scanner file = new Scanner(new File(fileName));
        int c = file.nextInt();                  // number of denominations
        int[] coins = new int[c];
        for (int i = 0; i < c; i++) {
            coins[i] = file.nextInt();
        }
        file.close();

        Arrays.sort(coins);

        // --- Ask user for amount M ---
        System.out.print("Enter the amount M (in cents): ");
        int M = sc.nextInt();

        int originalM = M;
        int[] count = new int[c];

        // --- Greedy algorithm ---
        for (int i = c - 1; i >= 0; i--) {
            count[i] = M / coins[i];
            M -= count[i] * coins[i];
        }

        // --- Output ---
        System.out.println("\nGreedy solution for " + originalM + " cents:");
        for (int i = c - 1; i >= 0; i--) {  // Changed from i > 0 to i >= 0
            System.out.println(coins[i] + "c coin: " + count[i]);
        }
        
        sc.close();
    }
}