import java.io.*;
import java.util.*;

// Simple Point class to store x and y coordinates
class Point {
    double x, y;
    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

public class ECE325_2025_Assignment4_ex3_Team17 {

    // Brute force method for finding the closest pair of points
    public static Point[] ClosestPair_bruteForce(List<Point> points) {
        int n = points.size();
        double min_dist = Double.MAX_VALUE;
        Point p1 = null, p2 = null;

        // Check every possible pair of points
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                double dist = Math.sqrt(Math.pow(points.get(i).x - points.get(j).x, 2)
                                      + Math.pow(points.get(i).y - points.get(j).y, 2));
                if (dist < min_dist) {
                    min_dist = dist;
                    p1 = points.get(i);
                    p2 = points.get(j);
                }
            }
        }
        return new Point[]{p1, p2};
    }

    // Divide & Conquer version (simplified implementation)
    public static Point[] ClosestPair_divideAndConquer(List<Point> points) {
        int n = points.size();
        double mean = 0, sum = 0, strip_dist = 0;
        if (n < 2) return new Point[]{null, null};

        // Find mean of all x-coordinates (used for dividing the set)
        for (int i = 0; i < n; i++) {
            sum += points.get(i).x;
        }
        mean = sum / n;

        // Split points into two subsets: left and right
        List<Point> left = new ArrayList<>();
        List<Point> right = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (points.get(i).x <= mean)
                left.add(points.get(i));
            else
                right.add(points.get(i));
        }

        // Compute closest pair on each side using brute force
        Point[] left_closest = ClosestPair_bruteForce(left);
        Point[] right_closest = ClosestPair_bruteForce(right);

        double left_dist = Math.sqrt(Math.pow(left_closest[0].x - left_closest[1].x, 2)
                                   + Math.pow(left_closest[0].y - left_closest[1].y, 2));
        double right_dist = Math.sqrt(Math.pow(right_closest[0].x - right_closest[1].x, 2)
                                    + Math.pow(right_closest[0].y - right_closest[1].y, 2));

        // Keep the smaller of the two distances
        strip_dist = Math.min(left_dist, right_dist);

        // Now build the strip region around the dividing line
        List<Point> in_strip = new ArrayList<>();
        for (int i = 0; i < left.size(); i++) {
            if (left.get(i).x > mean - strip_dist)
                in_strip.add(left.get(i));
        }
        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).x < mean + strip_dist)
                in_strip.add(right.get(i));
        }

        // Check if there’s a closer pair inside the strip
        if (in_strip.size() > 1) {
            Point[] strip_closest = ClosestPair_bruteForce(in_strip);
            double strip_closest_dist =
                Math.sqrt(Math.pow(strip_closest[0].x - strip_closest[1].x, 2)
                        + Math.pow(strip_closest[0].y - strip_closest[1].y, 2));

            if (strip_closest_dist < strip_dist)
                return strip_closest;
        }

        // Return whichever side has the smaller distance
        return (left_dist < right_dist) ? left_closest : right_closest;
    }

    // Brute force method for computing the convex hull
    public static ArrayList<Point> ConvexHull_bruteForce(List<Point> points) {
        int n = points.size();
        ArrayList<Point> hull = new ArrayList<>();
        if (n < 3) return hull;  // A hull needs at least 3 points

        // Try every pair of points and see if all others lie on one side
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int left = 0, right = 0;
                Point p1 = points.get(i);
                Point p2 = points.get(j);

                // Check where the rest of the points lie (left or right of the line)
                for (int k = 0; k < n; k++) {
                    if (k == i || k == j) continue;
                    Point p = points.get(k);
                    double cross = (p2.x - p1.x) * (p.y - p1.y)
                                 - (p2.y - p1.y) * (p.x - p1.x);
                    if (cross > 0) left++;
                    else if (cross < 0) right++;
                }

                // If all points are on one side, this edge is part of the hull
                if (left == 0 || right == 0) {
                    if (!hull.contains(p1)) hull.add(p1);
                    if (!hull.contains(p2)) hull.add(p2);
                }
            }
        }
        return hull;
    }

    public static void main(String[] args) {
        List<Point> points = new ArrayList<>();

        // --- Reading points from file ---
        try {
            File file = new File("C:\\Users\\User\\Desktop\\UNI OF CYPRUS\\5th Semester\\HMMY 325\\Assignments\\4th_Assignment\\map10000.txt");
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim();
                if (line.isEmpty()) continue;

                // Split by space or tab
                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    points.add(new Point(x, y));
                }
            }

            reader.close();
            System.out.println("File read successfully!");
            System.out.println("Number of points: " + points.size());

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Choose problem: 1) Closest Pair  2) Convex Hull");
        int problem = sc.nextInt();

        // --- Option 1: Closest Pair ---
        if (problem == 1) {
            System.out.println("Choose approach: 1) Brute Force  2) Divide & Conquer");
            int approach = sc.nextInt();
            sc.close();

            if (approach == 1) {
                long startTime = System.nanoTime();
                Point[] closest = ClosestPair_bruteForce(points);
                long endTime = System.nanoTime();

                System.out.println("Closest Pair:");
                System.out.println("(" + closest[0].x + ", " + closest[0].y + ") and (" +
                                   closest[1].x + ", " + closest[1].y + ")");
                System.out.println("Execution Time (Brute Force): " + 
                                   (endTime - startTime) / 1000000.0 + " ms");

            } else if (approach == 2) {
                long startTime = System.nanoTime();
                Point[] closest = ClosestPair_divideAndConquer(points);
                long endTime = System.nanoTime();

                System.out.println("Closest Pair:");
                System.out.println("(" + closest[0].x + ", " + closest[0].y + ") and (" +
                                   closest[1].x + ", " + closest[1].y + ")");
                System.out.println("Execution Time (Divide & Conquer): " + 
                                   (endTime - startTime) / 1000000.0 + " ms");
            } else {
                System.out.println("Invalid choice.");
            }
            

        // --- Option 2: Convex Hull ---
        } else if (problem == 2) {
            sc.close();
            System.out.println("Convex Hull Points (Brute Force):");
            long startTime = System.nanoTime();
            ArrayList<Point> hull = ConvexHull_bruteForce(points);
            long endTime = System.nanoTime();

            for (Point pt : hull) {
                System.out.println("(" + pt.x + ", " + pt.y + ")");
            }

            System.out.println("Execution Time (Brute Force): " + 
                               (endTime - startTime) / 1000000.0 + " ms");
        } else {
            System.out.println("Invalid choice.");
        }
    }
}