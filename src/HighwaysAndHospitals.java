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
            map[i] = -1;
        }
        // Now, iterate through each edge. Here, we will use the union find algorithm to calculate the number of disconnected subgraphs.
        int length = cities.length;
        for (int i = 0; i < length; i++) {
            // I am using these while loops to make sure we find the root node of both A and B.
            int root_A = cities[i][0];
            while (map[root_A - 1] != -1) {
                root_A = map[root_A - 1];
            }
            int root_B = cities[i][1];
            while (map[root_B - 1] != -1) {
                root_B = map[root_B - 1];
            }
            // Here, I am comparing the root nodes. If they are not the same, I will point the second root to the first to connect them into the same component.
            // I need to add the second condition because or else the map will never be edited, as the value of each node starts at -1 and are therefore equal to each other.
            if ((root_A != root_B)) {
                map[root_B - 1] = root_A;
            }
        }
        // Now all I have to do is count the number of components there are by going through my map and looking for "-1"s.
        long comps = 0;
        for (int i = 0; i < n; i++) {
            if (map[i] == -1) {
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
