package hw8.graph;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph ADT using incidence lists
 * for sparse graphs where most nodes aren't connected.
 *
 * @param <V> Vertex element type.
 * @param <E> Edge element type.
 */
public class SparseGraph<V, E> implements Graph<V, E> {

  private Map<V, VertexNode<V>> vertices; // HashMap for vertices
  private Set<EdgeNode<E>>  edges; // HashSet for edges

  /**
   * Constructor to instantiate SparseGraph.
   */
  public SparseGraph() {
    this.vertices = new HashMap<>();
    this.edges = new HashSet<>();
  }

  // Converts the vertex back to a VertexNode to use internally
  private VertexNode<V> convert(Vertex<V> v) throws PositionException {
    try {
      VertexNode<V> gv = (VertexNode<V>) v;
      if (gv.owner != this) {
        throw new PositionException();
      }
      return gv;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  // Converts and edge back to a EdgeNode to use internally
  private EdgeNode<E> convert(Edge<E> e) throws PositionException {
    try {
      EdgeNode<E> ge = (EdgeNode<E>) e;
      if (ge.owner != this) {
        throw new PositionException();
      }
      return ge;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  /**
   * Checks if there is already edge between the from and to vertices.
   *
   * @param fromVertex The starting vertex of the edge.
   * @param toVertex The ending vertex of the edge.
   * @return True if there is an edge between fromVertex and toVertex, else false.
   */
  private boolean isDuplicateEdge(VertexNode<V> fromVertex, VertexNode<V> toVertex) {
    return fromVertex.outgoing.containsKey(toVertex);
  }

  /**
   * Inserts a new vertex into the graph.
   *
   * @param v Vertex to insert.
   * @return The newly created vertex node.
   * @throws InsertionException If the vertex is null or already exists.
   */
  @Override
  public Vertex<V> insert(V v) throws InsertionException {
    // Check if input vertex is null or already exists in graph
    if (v == null || vertices.containsKey(v)) {
      throw new InsertionException();
    }
    // Create new node, set owner, and add to vertex collection, return added vertex
    VertexNode<V> newVertex = new VertexNode<>(v);
    newVertex.owner = this;
    vertices.put(v, newVertex);
    return newVertex;
  }

  /**
   * Insert a new edge into the graph between specified vertices.
   *
   * @param from Vertex position where edge starts.
   * @param to   Vertex position where edge ends.
   * @param e    Element to associate with new edge.
   * @return The newly created edge node.
   * @throws PositionException If either from or to vertices are null or not in graph.
   * @throws InsertionException If an edge between from and to already exists, or if from equals to.
   */
  @Override
  public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
      throws PositionException, InsertionException {
    // Check if either of the input vertices is null
    if (from == null || to == null) {
      throw new PositionException();
    }

    // Convert vertices and make sure they are in the graph
    VertexNode<V> fromVertex = convert(from);
    VertexNode<V> toVertex = convert(to);

    if (!vertices.containsKey(fromVertex.get()) || !vertices.containsKey(toVertex.get())) {
      throw new PositionException();
    }

    // Check for self-loop or if edge already exists
    if (from.equals(to) || isDuplicateEdge(fromVertex, toVertex)) {
      throw new InsertionException();
    }

    // Create new node, set owner, add to edges, outgoing, and incoming and return edge
    EdgeNode<E> newEdge = new EdgeNode<>(fromVertex, toVertex, e);
    newEdge.owner = this;
    edges.add(newEdge);
    fromVertex.outgoing.put(toVertex, newEdge);
    toVertex.incoming.add(newEdge);
    return newEdge;
  }

  /**
   * Removes a vertex from the graph.
   *
   * @param v Vertex position to remove.
   * @return The data in the removed vertex.
   * @throws PositionException If the vertex is null or not found in the graph.
   * @throws RemovalException If the vertex still has edges connected to it.
   */
  @Override
  public V remove(Vertex<V> v) throws PositionException, RemovalException {
    // Check if vertex is null or not present in graph
    if (v == null || !vertices.containsKey(v.get())) {
      throw new PositionException();
    }

    // Convert vertex, and check for outgoing or incoming edges
    VertexNode<V> vertexNode = convert(v);

    if (!vertexNode.outgoing.isEmpty() || !vertexNode.incoming.isEmpty()) {
      throw new RemovalException();
    }

    // Remove vertex, and return data
    vertices.remove(vertexNode.get());
    return vertexNode.data;
  }

  /**
   * Removes an edge from the graph.
   *
   * @param e Edge position to remove.
   * @return The data in the removed edge.
   * @throws PositionException If the edge is null or not found in graph.
   */
  @Override
  public E remove(Edge<E> e) throws PositionException {
    // Check if edge to be removed is null or not present in graph
    if (e == null) {
      throw new PositionException();
    }

    // Convert edge and check if it is part of graph
    EdgeNode<E> edgeNode = convert(e);

    if (!edges.contains(edgeNode)) {
      throw new PositionException();
    }

    // Remove edge, get from and to vertices
    edges.remove(edgeNode);
    VertexNode<V> fromNode = edgeNode.from;
    VertexNode<V> toNode = edgeNode.to;

    // Remove edge from outgoing/incoming, return data in removed edge
    fromNode.outgoing.remove(edgeNode.to);
    toNode.incoming.remove(edgeNode);

    return edgeNode.data;
  }

  /**
   * Returns an iterable collection of all vertices in the graph.
   *
   * @return An iterable collection of the vertices.
   */
  @Override
  public Iterable<Vertex<V>> vertices() {
    return Collections.unmodifiableCollection(vertices.values());
  }

  /**
   * Returns an iterable collection of all edges in the graph.
   *
   * @return An iterable collection of the edges.
   */
  @Override
  public Iterable<Edge<E>> edges() {
    return Collections.unmodifiableSet(edges);
  }

  /**
   * Returns an iterable collection of outgoing edges from a given vertex.
   *
   * @param v Vertex position which outgoing edges are retrieved from.
   * @return An iterable collection of outgoing edges from vertex.
   * @throws PositionException If vertex is null or not found in graph.
   */
  @Override
  public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
    // Check if vertex exists and is valid
    if (v == null || !vertices.containsKey(v.get())) {
      throw new PositionException();
    }
    // Retrieves unmodifiable collection of outgoing edges from given vertex
    VertexNode<V> vertexNode = convert(v);
    return Collections.unmodifiableCollection(vertexNode.outgoing.values());
  }

  /**
   * Returns an iterable collection of all incoming edges to a given vertex.
   *
   * @param v Vertex position to which incoming edges are directed.
   * @return An iterable collection of incoming edges to specified vertex.
   * @throws PositionException If vertex is null or not found in the graph.
   */
  @Override
  public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
    // Check if vertex exists and is valid
    if (v == null || !vertices.containsKey(v.get())) {
      throw new PositionException();
    }
    // Retrieves unmodifiable collection of incoming edges from given vertex
    VertexNode<V> vertexNode = convert(v);
    return Collections.unmodifiableCollection(vertexNode.incoming);
  }

  /**
   * Returns the starting vertex of a given edge.
   *
   * @param e The edge whose starting vertex is to be returned.
   * @return The starting vertex of the edge.
   * @throws PositionException If the edge is null or not found in the graph.
   */
  @Override
  public Vertex<V> from(Edge<E> e) throws PositionException {
    // Check existence and validity of edge and convert edge
    if (e == null) {
      throw new PositionException();
    }
    EdgeNode<E> edgeNode = convert(e);

    // Return starting vertex of given edge after converting and checking existence
    if (!edges.contains(edgeNode)) {
      throw new PositionException();
    }
    return edgeNode.from;
  }

  /**
   * Returns the ending vertex of a given edge.
   *
   * @param e Edge position whose ending vertex is returned.
   * @return The ending vertex of the edge.
   * @throws PositionException If the edge is null or not found in graph.
   */
  @Override
  public Vertex<V> to(Edge<E> e) throws PositionException {
    // Check existence and validity of edge and convert edge
    if (e == null) {
      throw new PositionException();
    }
    EdgeNode<E> edgeNode = convert(e);

    // Return ending vertex of given edge after converting and checking existence
    if (!edges.contains(edgeNode)) {
      throw new PositionException();
    }
    return edgeNode.to;
  }

  /**
   * Labels a vertex with given object.
   *
   * @param v Vertex position to label.
   * @param l Label object.
   * @throws PositionException If the vertex is null or not found in graph.
   */
  @Override
  public void label(Vertex<V> v, Object l) throws PositionException {
    // Assigns label to a vertex after ensuring the vertex is valid and exists in graph
    if (v == null || !vertices.containsKey(v.get())) {
      throw new PositionException();
    }
    VertexNode<V> vertexNode = convert(v);
    vertexNode.label = l;
  }

  /**
   * Labels an edge with a given object.
   *
   * @param e Edge position to label.
   * @param l Label object.
   * @throws PositionException If edge is null or not found in graph.
   */
  @Override
  public void label(Edge<E> e, Object l) throws PositionException {
    // Assigns label to a edge after ensuring the edge is valid and exists in graph
    if (e == null) {
      throw new PositionException();
    }
    EdgeNode<E> edgeNode = convert(e);

    if (!edges.contains(edgeNode)) {
      throw new PositionException();
    }
    edgeNode.label = l;
  }

  /**
   * Retrieves label of vertex.
   *
   * @param v Vertex position to query.
   * @return The label of the specified vertex.
   * @throws PositionException If the vertex is null or not found in graph.
   */
  @Override
  public Object label(Vertex<V> v) throws PositionException {
    // Retrieves label of vertex after validating vertex exists and is in graph
    if (v == null || !vertices.containsKey(v.get())) {
      throw new PositionException();
    }
    VertexNode<V> vertexNode = convert(v);
    return vertexNode.label;
  }

  /**
   * Retrieves label of edge.
   *
   * @param e Edge position to query.
   * @return The label of the specified edge.
   * @throws PositionException If the edge is null or not found in graph.
   */
  @Override
  public Object label(Edge<E> e) throws PositionException {
    // Retrieves label of edge after validating edge exists and is in graph
    if (e == null) {
      throw new PositionException();
    }
    EdgeNode<E> edgeNode = convert(e);

    if (!edges.contains(edgeNode)) {
      throw new PositionException();
    }
    return edgeNode.label;
  }

  /**
   * Clears all labels from vertices and edges in graph.
   */
  @Override
  public void clearLabels() {
    // Iterate over vertices and edges and set labels to null to clear
    for (VertexNode<V> v : vertices.values()) {
      v.label = null;
    }

    for (EdgeNode<E> e : edges) {
      e.label = null;
    }
  }

  @Override
  public String toString() {
    GraphPrinter<V, E> gp = new GraphPrinter<>(this);
    return gp.toString();
  }

  // Class for a vertex of type V
  private final class VertexNode<V> implements Vertex<V> {
    V data;
    Graph<V, E> owner;
    Object label;
    Map<VertexNode<V>, Edge<E>> outgoing;
    Set<Edge<E>> incoming;

    VertexNode(V v) {
      this.data = v;
      this.outgoing = new HashMap<>();
      this.incoming = new HashSet<>();
      this.label = null;
    }

    @Override
    public V get() {
      return this.data;
    }
  }

  //Class for an edge of type E
  private final class EdgeNode<E> implements Edge<E> {
    E data;
    Graph<V, E> owner;
    VertexNode<V> from;
    VertexNode<V> to;
    Object label;

    // Constructor for a new edge
    EdgeNode(VertexNode<V> f, VertexNode<V> t, E e) {
      this.from = f;
      this.to = t;
      this.data = e;
      this.label = null;
    }

    @Override
    public E get() {
      return this.data;
    }
  }
}
