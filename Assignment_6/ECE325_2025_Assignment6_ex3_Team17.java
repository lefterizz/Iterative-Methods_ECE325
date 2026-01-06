import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ECE325_2025_Assignment6_ex3_Team17 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the file name: ");
        String fileName = sc.nextLine();

        double[][] A;
        double[] b;
        int n;

        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            n = fileScanner.nextInt();
            A = new double[n][n];
            b = new double[n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    A[i][j] = fileScanner.nextDouble();
                }
                b[i] = fileScanner.nextDouble();
            }
            fileScanner.close();

            System.out.print("Choose method: (a) Gaussian Elimination or (b) LU Decomposition: ");
            String choice = sc.nextLine().trim().toLowerCase();

            long start = System.nanoTime();
            double[] x;

            if (choice.equals("a")) {
                x = gaussianElimination(A, b);
                long end = System.nanoTime();
                System.out.println("\nSolution using Gaussian Elimination:");
                printVector(x);
                System.out.printf("Execution time: %.3f ms%n", (end - start) / 1e6);
            }
            else if (choice.equals("b")) {
                x = luDecomposition(A, b);
                long end = System.nanoTime();
                System.out.println("\nSolution using LU Decomposition:");
                printVector(x);
                System.out.printf("Execution time: %.3f ms%n", (end - start) / 1e6);
            }
            else {
                System.out.println("Invalid choice. Use 'a' or 'b'.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    // ───────────────────────────────────────────────
    // Gaussian Elimination
    // ───────────────────────────────────────────────
    public static double[] gaussianElimination(double[][] A, double[] b) {
        int n = b.length;
        double[][] M = new double[n][n];
        double[] c = new double[n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, M[i], 0, n);
            c[i] = b[i];
        }

        for (int k = 0; k < n - 1; k++) {
            int maxRow = k;
            for (int i = k + 1; i < n; i++) {
                if (Math.abs(M[i][k]) > Math.abs(M[maxRow][k])) {
                    maxRow = i;
                }
            }
            double[] temp = M[k];
            M[k] = M[maxRow];
            M[maxRow] = temp;
            double t = c[k];
            c[k] = c[maxRow];
            c[maxRow] = t;

            for (int i = k + 1; i < n; i++) {
                double factor = M[i][k] / M[k][k];
                for (int j = k; j < n; j++) {
                    M[i][j] -= factor * M[k][j];
                }
                c[i] -= factor * c[k];
            }
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += M[i][j] * x[j];
            }
            x[i] = (c[i] - sum) / M[i][i];
        }
        return x;
    }

    // ───────────────────────────────────────────────
    // LU Decomposition
    // ───────────────────────────────────────────────
    public static double[] luDecomposition(double[][] A, double[] b) {
        int n = b.length;
        double[][] L = new double[n][n];
        double[][] U = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int k = i; k < n; k++) {
                double sum = 0;
                for (int j = 0; j < i; j++) {
                    sum += L[i][j] * U[j][k];
                }
                U[i][k] = A[i][k] - sum;
            }

            for (int k = i; k < n; k++) {
                if (i == k) {
                    L[i][i] = 1;
                } else {
                    double sum = 0;
                    for (int j = 0; j < i; j++) {
                        sum += L[k][j] * U[j][i];
                    }
                    L[k][i] = (A[k][i] - sum) / U[i][i];
                }
            }
        }

        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += L[i][j] * y[j];
            }
            y[i] = b[i] - sum;
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += U[i][j] * x[j];
            }
            x[i] = (y[i] - sum) / U[i][i];
        }

        return x;
    }

    // ───────────────────────────────────────────────
    // Utility
    // ───────────────────────────────────────────────
    public static void printVector(double[] x) {
        for (int i = 0; i < x.length; i++) {
            System.out.printf("x[%d] = %.3f%n", i + 1, x[i]);
        }
    }
}
