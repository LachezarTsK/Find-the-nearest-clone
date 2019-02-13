import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Solution {

	private static Set<Edge>[] verticesAndEdges;

	public static void addVertex(Integer nodeNumber) {
		if (verticesAndEdges[nodeNumber - 1] == null) {
			verticesAndEdges[nodeNumber - 1] = new HashSet<Edge>();
		}
	}

	/**
	 * Creates one-directional edge.
	 */
	public static void addEdge(Integer from, Integer to, int edgeLength) throws IllegalArgumentException {
		verticesAndEdges[from - 1].add(new Edge(to, edgeLength));
	}

	/**
	 * Searches for a route from the start node to the nearest node with the same
	 * color.
	 * 
	 * @return minimum distance from current start node.
	 */
	private static int shortestReach(int startNodeNumber, int colorToBeChecked, int[] allColors) {

		int numberOfNodes = verticesAndEdges.length;
		int currentMinimum = Integer.MAX_VALUE;
		int distanceFromStart = 0;

		List<Integer> queue = new LinkedList<Integer>();
		queue.add(startNodeNumber);
		boolean[] visited = new boolean[numberOfNodes];

		while (!queue.isEmpty()) {

			int current = queue.remove(0);
			if (allColors[current - 1] == colorToBeChecked) {
				if (distanceFromStart != 0) {
					currentMinimum = distanceFromStart;
					break;
				}
			}
			distanceFromStart++;

			if (visited[current - 1] == false) {
				visited[current - 1] = true;

				for (Edge edge : verticesAndEdges[current - 1]) {
					if (visited[edge.getEndNode() - 1] == false) {
						queue.add(edge.getEndNode());
					}
				}
			}
		}
		return currentMinimum;
	}

	/**
	 * Checks for the shortest route between any two nodes with the color to be
	 * analyzed.
	 * 
	 * @return minimum distance, if there is a route. Otherwise -1;
	 */
	private static int checkNodesForColorToBeAnalyzed(int colorToBeAnalyzed, int[] collor) {
		int minimum = Integer.MAX_VALUE;
		for (int i = 0; i < collor.length; i++) {
			if (collor[i] == colorToBeAnalyzed) {
				minimum = Math.min(minimum, shortestReach(i + 1, colorToBeAnalyzed, collor));
			}
		}
		/**
		 * Since the maximum number of nodes equals Math.pow(10, 6) and each edge has a
		 * length of one, the minimum distance between any two nodes is always less than
		 * Integer.MAX_VALUE.
		 * 
		 * Thus, if the initial value of 'minimum' is unchanged, there is no connection.
		 */
		if (minimum == Integer.MAX_VALUE) {
			minimum = -1;
		}
		return minimum;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int numberOfNodes = Integer.parseInt(st.nextToken());
		int numberOfEdges = Integer.parseInt(st.nextToken());
		verticesAndEdges = new HashSet[numberOfNodes];
		int[] allColors = new int[numberOfNodes];

		for (int i = 0; i < numberOfEdges; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			addVertex(from);
			addVertex(to);
			/**
			 * The weight of each edge equals one by default.
			 */
			addEdge(from, to, 1);
		}

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < numberOfNodes; i++) {
			allColors[i] = Integer.parseInt(st.nextToken());
		}

		st = new StringTokenizer(br.readLine());
		int colorToBeAnalyzed = Integer.parseInt(st.nextToken());
		System.out.println(checkNodesForColorToBeAnalyzed(colorToBeAnalyzed, allColors));
	}
}

class Edge {

	private int edgeLength;
	private int end;

	public Edge(int end, int edgeLength) {
		this.end = end;
		this.edgeLength = edgeLength;
	}

	public int getEndNode() {
		return this.end;
	}

	public int getEdgeLength() {
		return edgeLength;
	}
}
