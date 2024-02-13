package hw8.spp;

import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.Vertex;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;



public class DijkstraStreetSearcher extends StreetSearcher {

  private HashMap<Vertex<String>, Double> distances;

  //explored is basically both checking for previous and explored
  private HashSet<Vertex<String>> explored;
  private PriorityQueue<Vertex<String>> vertexQueue;

  /**
   * Creates a StreetSearcher object.
   *
   * @param graph an implementation of Graph ADT.
   */
  public DijkstraStreetSearcher(Graph<String, String> graph) {
    super(graph);
    this.vertexQueue = new PriorityQueue<>(new DijkstraComparator());
    this.distances = new HashMap<>();
    this.explored = new HashSet<>();
  }


  private class DijkstraComparator implements Comparator<Vertex<String>> {
    @Override
    public int compare(Vertex<String> vertexOne, Vertex<String> vertexTwo) {
      //have a custom comparator that uses dijkstra's weighting
      return (distances.get(vertexOne).compareTo(distances.get(vertexTwo)));
    }
  }

  private void distanceCheck(Vertex<String> headVertex, Vertex<String> neighbor, Edge<String> outEdge) {
    double distance = distances.get(headVertex) + (double) graph.label(outEdge);
    if (distance < distances.get(neighbor)) {
      graph.label(neighbor, outEdge);
      distances.put(neighbor, distance);
      vertexQueue.add(neighbor);
    }
  }

  private void dijkstraSearch(Vertex<String> startVertex) {
    for (Vertex<String> vertex : vertices.values()) {
      //marks all vertexes as unexplored and infinite distance
      //by default all vertices are unexplored through initialization of hashset
      distances.put(vertex, Double.POSITIVE_INFINITY);
    }
    //first node distance is zero and is explored
    distances.put(startVertex, 0.0);
    vertexQueue.add(startVertex);
    while (!vertexQueue.isEmpty()) {
      //headVertex should already be the smallest distance node due to priority queue
      Vertex<String> headVertex = vertexQueue.remove();
      explored.add(headVertex);
      Iterable<Edge<String>> outgoingEdges = graph.outgoing(headVertex);
      for (Edge<String> outEdge : outgoingEdges) {
        Vertex<String> neighbor = graph.to(outEdge);
        if (Objects.equals(explored.contains(neighbor), false)) {
          //neighbor is truly unexplored
          distanceCheck(headVertex, neighbor, outEdge);
        }
      }
    }
  }

  @Override
  public void findShortestPath(String startName, String endName) {
    //catch any invalid arguments here
    try {
      checkValidEndpoint(startName);
      checkValidEndpoint(endName);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return;
    }

    Vertex<String> start = vertices.get(startName);
    Vertex<String> end = vertices.get(endName);

    double totalDist = -1;  // totalDist must be update below
    dijkstraSearch(start);
    totalDist = distances.get(end);
    // These method calls will create and print the path
    List<Edge<String>> path = getPath(end, start);
    if (VERBOSE) {
      printPath(path, totalDist);
    }
  }
}
