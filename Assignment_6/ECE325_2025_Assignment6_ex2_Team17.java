import java.io.*;
import java.util.*;

public class ECE325_2025_Assignment6_ex2_Team17 {

    // --- Evaluate f(x) using Horner's Rule ---
    public static double f(double x, double[] coeffs) {
        double result = 0.0;
        for (int i = coeffs.length - 1; i >= 0; i--) {
            result = result * x + coeffs[i];
        }
        return result;
    }

    // --- Bisection Method ---
    public static double[] bisection(double a, double b, double eps, int B, double[] coeffs) {
        double fa = f(a, coeffs);
        double fb = f(b, coeffs);

        if (fa * fb > 0) {
            System.out.println("Invalid interval: f(a) and f(b) have the same sign.");
            return new double[]{Double.NaN, 0};
        }

        double mid = 0;
        int i;
        for (i = 1; i <= B; i++) {
            mid = (a + b) / 2.0;
            double fm = f(mid, coeffs);

            // Stop if close enough
            if (Math.abs(fm) < eps || (b - a) / 2.0 < eps) {
                return new double[]{mid, i};
            }

            // Keep the subinterval that contains the root
            if (fa * fm < 0) {
                b = mid;
                fb = fm;
            } else {
                a = mid;
                fa = fm;
            }
        }

        System.out.println("Bisection did not converge within the maximum iterations.");
        return new double[]{mid, B};
    }

    // --- False Position (Regula Falsi) Method ---
    public static double[] falsePosition(double a, double b, double eps, int B, double[] coeffs) {
        double fa = f(a, coeffs);
        double fb = f(b, coeffs);

        if (fa * fb > 0) {
            System.out.println("Invalid interval: f(a) and f(b) have the same sign.");
            return new double[]{Double.NaN, 0};
        }

        double x = 0;
        int i;
        for (i = 1; i <= B; i++) {
            // x is the x-intercept of the secant line
            x = (a * fb - b * fa) / (fb - fa);
            double fx = f(x, coeffs);

            if (Math.abs(fx) < eps) {
                return new double[]{x, i};
            }

            // Keep subinterval containing the sign change
            if (fa * fx < 0) {
                b = x;
                fb = fx;
            } else {
                a = x;
                fa = fx;
            }
        }

        System.out.println("False Position did not converge within the maximum iterations.");
        return new double[]{x, B};
    }

    // --- Main Program ---
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter input file name (e.g. input1.txt): ");
        String filename = sc.nextLine().trim();

        double a = 0, b = 0, eps = 0;
        int B = 0, n = 0;
        double[] coeffs = null;

        try {
            File file = new File(filename);
            System.out.println("Reading file: " + file.getAbsolutePath());

            if (!file.exists()) {
                System.out.println("Error: File not found.");
                sc.close();
                return;
            }

            // Read all lines
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
            reader.close();

            // Basic validation
            if (lines.size() < 6) {
                System.out.println("Error: File must contain at least 6 lines (a, b, ε, B, n, and coefficients).");
                sc.close();
                return;
            }

            // Parse parameters
            a = Double.parseDouble(lines.get(0));
            b = Double.parseDouble(lines.get(1));
            eps = Double.parseDouble(lines.get(2));
            B = Integer.parseInt(lines.get(3));
            n = Integer.parseInt(lines.get(4));

            if (lines.size() < 5 + n + 1) {
                System.out.println("Error: Expected " + (n + 1) + " coefficients but found only " + (lines.size() - 5));
                sc.close();
                return;
            }

            // Read coefficients
            coeffs = new double[n + 1];
            for (int i = 0; i <= n; i++) {
                coeffs[i] = Double.parseDouble(lines.get(5 + i));
            }

            System.out.println("File read successfully!\n");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + e.getMessage());
            sc.close();
            return;
        }

        sc.close();

        // Display the polynomial information
        System.out.println("Interval: [" + a + ", " + b + "]");
        System.out.println("Error tolerance ε = " + eps);
        System.out.println("Max iterations B = " + B);
        System.out.println("Degree n = " + n);
        System.out.println("Coefficients: " + Arrays.toString(coeffs));
        System.out.println("Polynomial: f(x) = " + polynomialToString(coeffs));
        System.out.println("f(a) = " + f(a, coeffs) + ", f(b) = " + f(b, coeffs));
        System.out.println("------------------------------------------");

        // Check sign condition
        if (f(a, coeffs) * f(b, coeffs) > 0) {
            System.out.println("Error: f(a) and f(b) have the same sign. A root is not guaranteed in this interval.");
            return;
        }

        // Run Bisection Method
        System.out.println("\n=== Bisection Method ===");
        double[] resB = bisection(a, b, eps, B, coeffs);
        if (!Double.isNaN(resB[0])) {
            double root = resB[0];
            int it = (int) resB[1];
            System.out.printf("Root ≈ %.8f found in %d iterations%n", root, it);
            System.out.printf("f(root) = %.2e%n", f(root, coeffs));
        }

        // Run False Position Method
        System.out.println("\n=== False Position Method ===");
        double[] resFP = falsePosition(a, b, eps, B, coeffs);
        if (!Double.isNaN(resFP[0])) {
            double root = resFP[0];
            int it = (int) resFP[1];
            System.out.printf("Root ≈ %.8f found in %d iterations%n", root, it);
            System.out.printf("f(root) = %.2e%n", f(root, coeffs));
        }
    }

    // --- Helper: convert coefficients into human-readable f(x) string ---
    public static String polynomialToString(double[] coeffs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coeffs.length; i++) {
            if (i > 0 && coeffs[i] >= 0) sb.append(" + ");
            else if (i > 0) sb.append(" - ");
            sb.append(Math.abs(coeffs[i]));
            if (i > 0) {
                sb.append("x");
                if (i > 1) sb.append("^").append(i);
            }
        }
        return sb.toString();
    }
}

