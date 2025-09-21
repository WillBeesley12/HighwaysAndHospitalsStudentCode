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
        // Initialize our map; for now, make every value 0 so that the "blank" nodes will have value less than or equal to 0 at the end.
        int[] map = new int[n];
        // Now, iterate through each edge. Here, we will use the union find algorithm to calculate the number of disconnected subgraphs.
        int length = cities.length;
        for (int i = 0; i < length; i++) {
            // Define variables
            int A = cities[i][0];
            int B = cities[i][1];
            int root_A = findRoot(A, map);
            int root_B = findRoot(B, map);
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
        }
        // Now all I have to do is count the number of components aka root nodes there are by going through my map and looking for negative values that represent the order of a subgraph.
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
    private static int findRoot(int node, int[] map) {
        // Find the root of the node by checking for a number less than or equal to 0.
        int root = node;
        while (map[root - 1] > 0) {
            root = map[root - 1];
        }
        // Path compression: assign the root of each node between the original node and the root directly to the root.
        int temp;
        while (map[node - 1] > 0) {
            temp = map[node - 1];
            map[node - 1] = root;
            node = temp;
        }
        return root;
    }
}
