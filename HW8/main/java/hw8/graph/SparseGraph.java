package hw8.graph;

import exceptions.InsertionException;
import exceptions.PositionException;
import exceptions.RemovalException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;


/**
 * An implementation of Graph ADT using incidence lists
 * for sparse graphs where most nodes aren't connected.
 *
 * @param <V> Vertex element type.
 * @param <E> Edge element type.
 */
public class SparseGraph<V, E> implements Graph<V, E> {

  //chosen for efficiency
  private HashMap<V, Vertex<V>> vertexList;
  private HashSet<Edge<E>> edgeList;


  /**Default constructor for SparseGraph.
   *
   */
  public SparseGraph() {
    this.vertexList = new HashMap<>();
    this.edgeList = new HashSet<>();
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

  /**Checks whether a given vertex is valid or not for an exception.
   * @param vertex the vertex to check that is valid or not
   * @return boolean value of if valid or not
   */
  //returns true if not null and in list
  private boolean isNotValidVertex(Vertex<V> vertex) {
    if (Objects.equals(vertex, null)) {
      return true;
    }
    return !vertexList.containsKey(vertex.get());
  }

  /**Checks whether the given edge exists already or not.
   * @param vertexFrom the starting vertex
   * @param vertexTo the ending vertex
   * @return boolean of whether the edge already exists or not
   */
  private boolean edgeExistsAlready(Vertex<V> vertexFrom, Vertex<V> vertexTo) {
    for (Edge<E> edge : outgoing(vertexFrom)) {
      if (Objects.equals(to(edge), vertexTo)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Vertex<V> insert(V v) throws InsertionException {
    //throw exception if null or already exists
    if (Objects.equals(v, null) || vertexList.containsKey(v)) {
      throw new InsertionException();
    }
    VertexNode<V> newVertexNode = new VertexNode<>(v, this);
    vertexList.put(v, newVertexNode);
    return newVertexNode;
  }

  @Override
  public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
      throws PositionException, InsertionException {
    //throw exception for invalid vertices
    if (isNotValidVertex(from) || isNotValidVertex(to)) {
      throw new PositionException();
    }
    //throw exception for multiple edges or multiple loops
    if (edgeExistsAlready(from, to) || Objects.equals(from, to)) {
      throw new InsertionException();
    }
    VertexNode<V> fromVertex = convert(from);
    VertexNode<V> toVertex = convert(to);
    //utilizing non-default constructor to assign ownership of the graph
    EdgeNode<E> newEdgeInsert = new EdgeNode<>(fromVertex, toVertex, e, this);
    //add edge to existing hashmap of edges (upcasts to edge object)
    edgeList.add(newEdgeInsert);
    //add outgoing and incoming edges (upcasts to edge object)
    fromVertex.outgoingEdges.add(newEdgeInsert);
    toVertex.incomingEdges.add(newEdgeInsert);
    return newEdgeInsert;

  }

  @Override
  public V remove(Vertex<V> v) throws PositionException, RemovalException {
    if (isNotValidVertex(v)) {
      throw new PositionException();
    }
    //throw removal exception if vertex still has incident edges
    if (!convert(v).outgoingEdges.isEmpty() || !convert(v).incomingEdges.isEmpty()) {
      throw new RemovalException();
    }
    //has no edges connected
    V data = v.get();
    vertexList.remove(data);
    return data;
  }

  @Override
  public E remove(Edge<E> e) throws PositionException {
    //throw exception if null or doesn't exist
    if (Objects.equals(e, null)) {
      throw new PositionException();
    }
    EdgeNode<E> node = convert(e);
    if (!edgeList.contains(node)) {
      throw new PositionException();
    }
    VertexNode<V> fromVertex = convert(from(e));
    VertexNode<V> toVertex = convert(to(e));
    //remove from edge list
    edgeList.remove(e);
    //remove from incoming/outgoing edges
    fromVertex.outgoingEdges.remove(e);
    toVertex.incomingEdges.remove(e);
    return e.get();
  }

  @Override
  public Iterable<Vertex<V>> vertices() {
    return Collections.unmodifiableCollection(vertexList.values());
  }

  @Override
  public Iterable<Edge<E>> edges() {
    return Collections.unmodifiableCollection(edgeList);
  }

  @Override
  public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
    //outgoing edges from a given vertex v
    //throw exception if already exist or null
    if (Objects.equals(v, null) || !vertexList.containsKey(v.get())) {
      throw new PositionException();
    }
    return Collections.unmodifiableCollection(convert(v).outgoingEdges);
  }

  @Override
  public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
    //incoming edges from a given vertex v
    //throw exception if already exist or null
    if (Objects.equals(v, null) || !vertexList.containsKey(v.get())) {
      throw new PositionException();
    }
    return Collections.unmodifiableCollection(convert(v).incomingEdges);
  }

  @Override
  public Vertex<V> from(Edge<E> e) throws PositionException {
    //Vertex edge is going from
    //throw exception if already exist or null
    if (Objects.equals(e, null) || !edgeList.contains(e)) {
      throw new PositionException();
    }
    return convert(e).from;
  }

  @Override
  public Vertex<V> to(Edge<E> e) throws PositionException {
    //Vertex edge is going to
    //throw exception if already exist or null
    if (Objects.equals(e, null) || !edgeList.contains(e)) {
      throw new PositionException();
    }
    return convert(e).to;
  }

  @Override
  public void label(Vertex<V> v, Object l) throws PositionException {
    if (isNotValidVertex(v)) {
      throw new PositionException();
    }
    VertexNode<V> vertexNode = convert(v);
    vertexNode.label = l;
  }

  @Override
  public void label(Edge<E> e, Object l) throws PositionException {
    //throw exception when null or doesn't exist
    if (Objects.equals(e, null) || !edgeList.contains(e)) {
      throw new PositionException();
    }
    EdgeNode<E> edgeNode = convert(e);
    edgeNode.label = l;
  }

  @Override
  public Object label(Vertex<V> v) throws PositionException {
    if (isNotValidVertex(v)) {
      throw new PositionException();
    }
    VertexNode<V> vertexNode = convert(v);
    return vertexNode.label;
  }

  @Override
  public Object label(Edge<E> e) throws PositionException {
    //throw exception when null or doesn't exist
    if (Objects.equals(e, null) || !edgeList.contains(e)) {
      throw new PositionException();
    }
    EdgeNode<E> edgeNode = convert(e);
    return edgeNode.label;
  }

  @Override
  public void clearLabels() {
    //clear all vertices and edges
    for (Vertex<V> vertex : vertexList.values()) {
      convert(vertex).label = null;
    }
    for (Edge<E> edge : edgeList) {
      convert(edge).label = null;
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

    //added fields
    ArrayList<Edge<E>> outgoingEdges;
    ArrayList<Edge<E>> incomingEdges;

    VertexNode(V v) {
      this.data = v;
      this.label = null;
      this.outgoingEdges = new ArrayList<>();
      this.incomingEdges = new ArrayList<>();
    }

    /**Non-Default constructor for VertexNode.
     * @param v the data to use for the vertex
     * @param graph the owner of the vertex
     */
    VertexNode(V v, Graph<V, E> graph) {
      this.data = v;
      this.label = null;
      this.owner = graph;
      this.outgoingEdges = new ArrayList<>();
      this.incomingEdges = new ArrayList<>();
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

    /**Non-Default constructor for EdgeNode.
     * @param from the starting node for the edge
     * @param to the ending node for the edge
     * @param graph the owner of the edge
     */
    EdgeNode(VertexNode<V> from, VertexNode<V> to, E edge, Graph<V,E> graph) {
      this.from = from;
      this.to = to;
      this.data = edge;
      this.label = null;
      this.owner = graph;
    }

    @Override
    public E get() {
      return this.data;
    }
  }
}
