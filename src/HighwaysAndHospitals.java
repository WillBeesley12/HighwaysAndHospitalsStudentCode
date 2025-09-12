import java.util.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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

    /**
     * TODO: Complete this function, cost(), to return the minimum cost to provide
     *  hospital access for all citizens in Menlo County.
     */
    public static long cost(int n, int hospitalCost, int highwayCost, int cities[][]) {
        // When the hospital cost is less than or equal than the highway cost, we can build a hospital in every city instead.
        if (hospitalCost <= highwayCost) {
            return (long) n * hospitalCost;
        }
        Map<Integer, ArrayList<Integer>> graph = new HashMap<>();
        for (int i = 0; i < cities.length; i++) {
            int first = cities[i][0];
            int second = cities[i][1];
            graph.putIfAbsent(first, new ArrayList<>());
            graph.putIfAbsent(second, new ArrayList<>());
            graph.get(first).add(second);
            graph.get(second).add(first);
        }
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        long highways = 0;
        long hospitals = 0;
        while (visited.size() < n) {
            int random_city = 0;
            for (int i = 1; i < n + 1; i++) {
                if (!visited.contains(i)) {
                    random_city = i;
                    break;
                }
            }
            visited.add(random_city);
            queue.offer(random_city);
            hospitals++;
            while (!queue.isEmpty()) {
                int queueSize = queue.size();
                for (int j = 0; j < queueSize; j++) {
                    int current = queue.poll();
                    if (graph.containsKey(current)) {
                        for (int neighbor : graph.get(current)) {
                            if (!visited.contains(neighbor)) {
                                highways++;
                                visited.add(neighbor);
                                queue.offer(neighbor);
                            }
                        }
                    }
                }
            }
        }
        long answer = highways * highwayCost + hospitals * hospitalCost;
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
