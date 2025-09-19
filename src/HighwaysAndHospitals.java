import java.util.*;

/**
 * Highways & Hospitals
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *
 * Completed by: William Beesley
 *
 */

public class HighwaysAndHospitals {
    public static long cost(int n, int hospitalCost, int highwayCost, int cities[][]) {
        // When the hospital cost is less than or equal than the highway cost, we can build a hospital in every city instead.
        if (hospitalCost <= highwayCost) {
            return (long) n * hospitalCost;
        }
        // Initialize our map; for now, make every value -1 so that the "blank" nodes will have value -1 at the end.
        int[] map = new int[n];
        for (int i = 0; i < n; i++) {
            map[i] = 0;
        }
        // Now, iterate through each edge. Here, we will use the union find algorithm to calculate the number of disconnected subgraphs.
        int length = cities.length;
        for (int i = 0; i < length; i++) {
            // Define variables
            int A = cities[i][0];
            int B = cities[i][1];
            int root_A;
            int root_B;
            // If the node is a root, set the root equal to it, for both A and B
            if (map[A - 1] <= 0) {
                root_A = A;
            }
            else {
                root_A = map[A - 1];
            }
            if (map[B - 1] <= 0) {
                root_B = B;
            }
            else {
                root_B = map[B - 1];
            }
            // Y and Z are the order of the roots of A and B
            int Y = map[root_A - 1];
            int Z = map[root_B - 1];
            // If the roots are not the same, connect the two subgraphs.
            if (root_A != root_B) {
                // Set the root of A or B to whichever one has a higher order. Add the order of the existing one.
                if (Y < Z) {
                    map[root_A - 1] += map[root_B - 1] - 1;
                    map[root_B - 1] = root_A;
                } else {
                    map[root_B - 1] += (map[root_A - 1] - 1);
                    map[root_A - 1] = root_B;
                }
            }
            // Use the while loop to find the root of A, storing it under X
            int x = A;
            while (map[x - 1] > 0) {
                x = map[x - 1];
            }
            // Go through and assign the root of each node between the original A to X directly to X.
            int temp;
            while (map[A - 1] > 0) {
                temp = map[A - 1];
                map[A - 1] = x;
                A = temp;
            }
            // Here, I am comparing the root nodes. If they are not the same, I will point the second root to the first to connect them into the same component.
            // I need to add the second condition because or else the map will never be edited, as the value of each node starts at -1 and are therefore equal to each other.
        }
        // Now all I have to do is count the number of components there are by going through my map and looking for "-1"s.
        long comps = 0;
        for (int i = 0; i < n; i++) {
            if (map[i] <= 0) {
                comps++;
            }
        }
        // To compute the answer:
        // The cost of hospitals is just the number of components times the hospital cost, as the optimal solution will have exactly one hospital in every disconnected subgraph.
        // The cost of highways is the sum of all the "V - 1"s for each disconnected component. However, we know the sum of all Vs adds to N, so all we need to do is subtract 1 from N for each disconnected subgraph, then multiply by the cost of one highway.
        long answer = (comps * hospitalCost) + (highwayCost * (n - comps));
        return answer;
    }
    public static void main(String[] args) {
        int n = 7;
        int hospitalCost = 3;
        int highwayCost = 2;
        int cities[][] = {{2,7}, {1,3}, {1,2}, {2,3}, {5,6}, {4,6}};
        long cost = cost(n, hospitalCost, highwayCost, cities);
        System.out.println(cost);
    }
}
