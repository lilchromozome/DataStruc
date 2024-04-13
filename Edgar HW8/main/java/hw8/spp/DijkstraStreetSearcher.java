package hw8.spp;

import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.Vertex;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DijkstraStreetSearcher extends StreetSearcher {

  private Map<Vertex<String>, Double> distance; // Stores shortest distance from start vertex to other vertices
  private Set<Vertex<String>> explored; // Set of vertices that have already been explored
  private PriorityQueue<Vertex<String>> priorityQueue; // Priority queue to figure out which vertex to explore

  /**
   * Creates a StreetSearcher object.
   *
   * @param graph an implementation of Graph ADT.
   */
  public DijkstraStreetSearcher(Graph<String, String> graph) {
    super(graph);
    this.distance = new HashMap<>();
    this.explored = new HashSet<>();
    this.priorityQueue = new PriorityQueue<>(new DistanceComparator());
  }

  /**
   * Comparator class for comparing vertices based on their distance.
   *
   */
  private class DistanceComparator implements Comparator<Vertex<String>> {
    @Override
    public int compare(Vertex<String> firstVertex, Vertex<String> secondVertex) {
      // Compare vertices based on shortest distance from start vertex
      return distance.get(firstVertex).compareTo(distance.get(secondVertex));
    }
  }

  /**
   * Initializes each node with infinite distance except for start, adds start to priority queue.
   *
   * @param start The starting vertex for path search.
   */
  private void initializeSearch(Vertex<String> start) {
    // Initialize vertices with infinite distance and null labels
    for (Vertex<String> v : graph.vertices()) {
      distance.put(v, Double.POSITIVE_INFINITY);
      graph.label(v, null);
    }
    // Set start vertex distance to 0 and add to priority queue
    distance.put(start, 0.0);
    priorityQueue.add(start);
  }

  /**
   * Updates minimum distance of neighboring vertex and adds to priority queue if necessary.
   *
   * @param current The vertex currently being processed.
   * @param edge The edge connecting the vertex to neighbor.
   */
  private void updateMinimumDistance(Vertex<String> current, Edge<String> edge) {
    // Calculate new distance for neighboring vertex
    Vertex<String> neighbor = graph.to(edge);
    double edgeWeight = (double) graph.label(edge);
    double newDistance = distance.get(current) + edgeWeight;

    // Update neighbor's distance if new distance is shorter
    if (newDistance < distance.get(neighbor)) {
      distance.put(neighbor, newDistance);
      graph.label(neighbor, edge);
      priorityQueue.add(neighbor); // Add neighbor to priority queue
    }
  }

  /**
   * Processes vertices until priority queue empties or the destination is found.
   *
   * @param end The destination vertex.
   */
  private void processVertices(Vertex<String> end) {
    // Process priority queue until it is empty
    while (!priorityQueue.isEmpty()) {
      // Grab vertex with shortest distance
      Vertex<String> current = priorityQueue.poll();

      // Skip vertex if already explored, mark current as explored, break if end reached.
      if (explored.contains(current)) {
        continue;
      }
      explored.add(current);
      if (current.equals(end)) {
        break;
      }

      // Update distances to neighboring vertices if shorter path found
      for (Edge<String> edge : graph.outgoing(current)) {
        updateMinimumDistance(current, edge);
      }
    }
  }

  /**
   * Finds the shortest path between start and end vertices.
   *
   * @param startName Starting vertex name.
   * @param endName   Ending vertex name.
   */
  @Override
  public void findShortestPath(String startName, String endName) {
    // Check if the endpoints are valid using StreetSearcher's method
    try {
      checkValidEndpoint(startName);
      checkValidEndpoint(endName);
    } catch (IllegalArgumentException ex) {
      System.out.println(ex.getMessage());
      return;
    }

    // Find start and end vertices, initialize the search from start and process vertices
    Vertex<String> start = vertices.get(startName);
    Vertex<String> end = vertices.get(endName);

    initializeSearch(start);
    processVertices(end);

    // Calculate total distance of shortest path to end vertex
    double totalDist = distance.get(end);

    // These method calls will create and print the path for you
    List<Edge<String>> path = getPath(end, start);
    if (VERBOSE) {
      printPath(path, totalDist);
    }
  }
}
