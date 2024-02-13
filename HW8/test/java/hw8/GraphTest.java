package hw8;

import exceptions.PositionException;
import exceptions.InsertionException;
import exceptions.RemovalException;
import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class GraphTest {

  protected Graph<String, String> graph;

  @BeforeEach
  public void setupGraph() {
    this.graph = createGraph();
  }

  protected abstract Graph<String, String> createGraph();

  @Test
  @DisplayName("insert(v) returns a vertex with given data")
  public void canGetVertexAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    assertEquals(v1.get(), "v1");
  }

  @Test
  @DisplayName("insert (v) throws InsertionException when vertex has null data")
  public void insertNullVertex() {
    try {
      graph.insert(null);
      fail("failed to throw insertionException when inserted vertex is null");
    } catch (InsertionException e) {
      return;
    }
  }


  @Test
  @DisplayName("insert (v) throws InsertionException when vertex already exists")
  public void insertPreExistingVertex() {
    graph.insert("v1");
    try {
      graph.insert("v1");
      fail("failed to throw insertionException when inserted vertex already exists");
    } catch (InsertionException e) {
      return;
    }
  }

  @Test
  @DisplayName("insert(v) returns a vertex with given data for multiple vertices")
  public void canGetMultipleVerticesAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    assertEquals(v1.get(), "v1");
    assertEquals(v2.get(), "v2");
    assertEquals(v3.get(), "v3");
  }

  @Test
  @DisplayName("insert(U, V, e) returns an edge with given data")
  public void canGetEdgeAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals(e1.get(), "v1-v2");
  }

  @Test
  @DisplayName("insert(null, V, e) throws exception")
  public void insertEdgeThrowsPositionExceptionWhenFirstVertexIsNull() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(null, v, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(V, null, e) throws exception")
  public void insertEdgeThrowsPositionExceptionWhenSecondVertexIsNull() {
    Vertex<String> v = graph.insert("v");
    try {
      graph.insert(v, null, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(V, V, e) throws exception because of loop")
  public void insertEdgeThrowsInsertionExceptionWhenCreatingLoop() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(v, v, "e");
      fail("The expected exception was not thrown");
    } catch (InsertionException e) {
      return;
    }
  }

  @Test
  @DisplayName("insert(U, V, e) throws exception because of multiple edges with different values")
  public void insertEdgeThrowsInsertionExceptionWhenCreatingMultipleEdgesDifferentValues() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.insert(v1, v2, "e1");
    try {
      graph.insert(v1, v2, "e2");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(U, V, e) throws exception because of multiple edges with same value")
  public void insertEdgeThrowsInsertionExceptionWhenCreatingMultipleEdgesSameValues() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.insert(v1, v2, "e1");
    try {
      graph.insert(v1, v2, "e1");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(U, V, null) returns an edge with null value")
  public void canGetEdgeNullValueAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, null);
    assertNull(e1.get());
  }

  @Test
  @DisplayName("insert(U, V, e) returns multiple edge with given data in both directions")
  public void canGetMultipleEdgeInBothDirectionsAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v1, "v2-v1");
    assertEquals(e1.get(), "v1-v2");
    assertEquals(e2.get(), "v2-v1");
  }

  @Test
  @DisplayName("insert(U, V, e) returns multiple edge with given data")
  public void canGetMultipleEdgesAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "e");
    Edge<String> e2 = graph.insert(v1, v3, "e");
    Edge<String> e3 = graph.insert(v2, v3, "e");
    assertEquals(e1.get(), "e");
    assertEquals(e2.get(), "e");
    assertEquals(e3.get(), "e");
  }

  //remove tests

  @Test
  @DisplayName("remove(null) throws exception")
  public void removeNullVertex() {
    graph.insert("v1");
    try {
      graph.remove((Vertex<String>) null);
      fail("The expected exception was not thrown");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("remove(vertex) on empty graph throws exception")
  public void removeVertexFromEmptyGraph() {
    Vertex<String> v1 = graph.insert("v1");
    try {
      graph.remove(v1);
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("remove(vertex) with nonexistent vertex on graph throws exception")
  public void removeNonExistentVertexFromGraph() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    try {
      graph.remove(v1);
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("remove(vertex) with vertex with edges on graph throws exception")
  public void removeVertexWithEdgesFromGraph() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.insert(v1, v2, "e");
    try {
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (RemovalException e) {
      return;
    }
  }

  @Test
  @DisplayName("remove(vertex) with valid vertex without edges return right value")
  public void removeValidVertexFromGraph() {
    Vertex<String> v1 = graph.insert("v1");
    String data = graph.remove(v1);
    assertEquals(data, "v1");
  }

  @Test
  @DisplayName("remove(vertex) with valid vertices without edges return right values")
  public void removeValidVerticesFromGraph() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    String dataOne = graph.remove(v1);
    String dataTwo = graph.remove(v2);
    assertEquals(dataOne, "v1");
    assertEquals(dataTwo, "v2");
  }

  @Test
  @DisplayName("remove(edge) with nonexistent edge")
  public void removeNonExistentEdgeFromGraph() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e");
    graph.remove(e1);
    try {
      graph.remove(e1);
      fail("The expected exception was not thrown");
    } catch (PositionException e) {
        return;
      }
    }

  @Test
  @DisplayName("remove(edge) with valid edge")
  public void removeValidEdgeFromGraph() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e");
    String data = graph.remove(e1);
    assertEquals(data, "e");
  }

  @Test
  @DisplayName("remove(edge) with valid edges")
  public void removeValidEdgesFromGraph() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e");
    Edge<String> e2 = graph.insert(v2, v1, "e");
    String dataOne = graph.remove(e1);
    String dataTwo = graph.remove(e2);
    assertEquals(dataOne, "e");
    assertEquals(dataTwo, "e");
  }

  @Test
  @DisplayName("remove(edge) with valid edge as a null value")
  public void removeValidNullEdgeFromGraph() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2,  null);
    String dataOne = graph.remove(e1);
    assertNull(dataOne);
  }


  @Test
  @DisplayName("vertices() iterable for empty graph")
  public void verticesIterableEmptyGraph() {
    Iterable<Vertex<String>> verticesIterable = graph.vertices();
    //for each loop to test out
    for (Vertex<String> vertex : verticesIterable) {
      fail("The graph is empty yet vertex detected");
    }
  }

  @Test
  @DisplayName("vertices() iterable for nonempty graph")
  public void verticesIterableNonEmptyGraph() {
    graph.insert("v1");
    graph.insert("v2");
    graph.insert("v3");
    Iterable<Vertex<String>> verticesIterable = graph.vertices();
    int vertexCounter = 0;
    //for each loop to test out
    for (Vertex<String> vertex : verticesIterable) {
      vertexCounter++;
    }
    assertEquals(vertexCounter, 3);
  }

  @Test
  @DisplayName("edges() iterable for empty graph")
  public void edgesIterableEmptyGraph() {
    Iterable<Edge<String>> edgesIterable = graph.edges();
    //for each loop to test out
    for (Edge<String> vertex : edgesIterable) {
      fail("The graph is empty yet edge detected");
    }
  }

  @Test
  @DisplayName("edges() iterable for nonempty graph")
  public void edgesIterableNonEmptyGraph() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    graph.insert(v1, v2, "e");
    graph.insert(v2, v1, "e");
    graph.insert(v1, v3, "e");
    int edgeCounter = 0;
    Iterable<Edge<String>> edgesIterable = graph.edges();
    //for each loop to test out
    for (Edge<String> vertex : edgesIterable) {
      edgeCounter++;
    }
    assertEquals(edgeCounter, 3);
  }

  @Test
  @DisplayName("outgoing(vertex) iterable for null vertex throws exception")
  public void outgoingIterableNullVertex() {
    try {
      Iterable<Edge<String>> outgoingIterable = graph.outgoing(null);
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("outgoing(vertex) iterable for nonexistent vertex throws exception")
  public void outgoingIterableNonExistentVertex() {
    Vertex<String> v1 = graph.insert("v1");
    graph.remove(v1);
    try {
      Iterable<Edge<String>> outgoingIterable = graph.outgoing(v1);
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("outgoing(vertex) iterable for vertex with no edges")
  public void outgoingIterableNoEdgesVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Iterable<Edge<String>> outgoingIterable = graph.outgoing(v1);
    for (Edge<String> edge : outgoingIterable) {
      fail("edge detected even though none present");
    }
  }

  @Test
  @DisplayName("outgoing(vertex) iterable for vertex with no outgoing edges")
  public void outgoingIterableNoOutgoingEdgesVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.insert(v2, v1, "e");
    Iterable<Edge<String>> outgoingIterable = graph.outgoing(v1);
    for (Edge<String> edge : outgoingIterable) {
      fail("edge detected even though non outgoing present");
    }
  }

  @Test
  @DisplayName("outgoing(vertex) iterable for valid vertex")
  public void outgoingIterableValidVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    graph.insert(v1, v2, "e");
    graph.insert(v1, v3, "e");
    graph.insert(v1, v4, "e");
    int outgoingCounter = 0;
    Iterable<Edge<String>> outgoingIterable = graph.outgoing(v1);
    for (Edge<String> edge : outgoingIterable) {
      outgoingCounter++;
    }
    assertEquals(outgoingCounter, 3);
  }

  @Test
  @DisplayName("incoming(vertex) iterable for null vertex throws exception")
  public void incomingIterableNullVertex() {
    try {
      Iterable<Edge<String>> incomingIterable = graph.incoming(null);
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("incoming(vertex) iterable for nonexistent vertex throws exception")
  public void incomingIterableNonExistentVertex() {
    Vertex<String> v1 = graph.insert("v1");
    graph.remove(v1);
    try {
      Iterable<Edge<String>> incomingIterable = graph.incoming(v1);
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("incoming(vertex) iterable for vertex with no edges")
  public void incomingIterableNoEdgesVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Iterable<Edge<String>> incomingIterable = graph.incoming(v1);
    for (Edge<String> edge : incomingIterable) {
      fail("edge detected even though none present");
    }
  }

  @Test
  @DisplayName("incoming(vertex) iterable for vertex with only outgoing edges")
  public void incomingIterableOnlyOutgoingEdgesNoIncomingVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.insert(v1, v2, "e");
    Iterable<Edge<String>> incomingIterable = graph.incoming(v1);
    for (Edge<String> edge : incomingIterable) {
      fail("edge detected even though none present");
    }
  }

  @Test
  @DisplayName("incoming(vertex) iterable for valid vertex")
  public void incomingIterableValidVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Vertex<String> v4 = graph.insert("v4");
    graph.insert(v1, v2, "e");
    graph.insert(v3, v2, "e");
    graph.insert(v4, v2, "e");
    int incomingCounter = 0;
    Iterable<Edge<String>> outgoingIterable = graph.incoming(v2);
    for (Edge<String> edge : outgoingIterable) {
      incomingCounter++;
    }
    assertEquals(incomingCounter, 3);
  }

  @Test
  @DisplayName("from(edge) with nonexistent edge throws exception")
  public void fromNonExistentEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e");
    graph.remove(e1);
    try {
      graph.from(e1);
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("from(edge) with a valid null edge")
  public void fromNullEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, null);
    Vertex<String> vertex = graph.from(e1);
    assertEquals(vertex, v1);
  }

  @Test
  @DisplayName("from(edge) with a valid non-null edge")
  public void fromValidEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e");
    Vertex<String> vertex = graph.from(e1);
    assertEquals(vertex, v1);
  }

  @Test
  @DisplayName("to(edge) with nonexistent edge throws exception")
  public void toNonExistentEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e");
    graph.remove(e1);
    try {
      graph.to(e1);
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("to(edge) with a valid null edge")
  public void toNullEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, null);
    Vertex<String> vertex = graph.to(e1);
    assertEquals(vertex, v2);
  }

  @Test
  @DisplayName("to(edge) with a valid non-null edge")
  public void toValidEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e");
    Vertex<String> vertex = graph.to(e1);
    assertEquals(vertex, v2);
  }

  @Test
  @DisplayName("label(vertex, string) with a null vertex throws exception")
  public void labelNullVertex() {
    try {
      graph.label((Vertex<String>) null, "e1");
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("label(vertex, string) with a non-existent vertex throws exception")
  public void labelNonExistentVertex() {
    Vertex<String> v1 = graph.insert("v1");
    graph.remove(v1);
    try {
      graph.label(v1, "e1");
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("label(vertex, string) with a valid vertex")
  public void labelValidVertex() {
    Vertex<String> v1 = graph.insert("v1");
    graph.label(v1, "label1");
    assertEquals("label1", graph.label(v1));
  }

  @Test
  @DisplayName("label(vertex, null) with a valid vertex")
  public void labelValidVertexWithNull() {
    Vertex<String> v1 = graph.insert("v1");
    graph.label(v1, null);
    assertNull(graph.label(v1));
  }

  @Test
  @DisplayName("label(Edge, string) with a null edge throws exception")
  public void labelNullEdge() {
    try {
      graph.label((Edge<String>) null, "label1");
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }
  @Test
  @DisplayName("label(Edge, string) with a non-existent vertex throws exception")
  public void labelNonExistentEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    graph.remove(e1);
    try {
      graph.label(e1, "label1");
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("label(edge, string) with a valid edge")
  public void labelValidEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    graph.label(e1, "label1");
    assertEquals("label1", graph.label(e1));
  }

  @Test
  @DisplayName("label(edge, null) with a valid edge")
  public void labelValidEdgeWithNull() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    graph.label(e1, null);
    assertNull(graph.label(e1));
  }

  @Test
  @DisplayName("label(vertex) with a null vertex throws exception")
  public void labelGetNullVertex() {
    try {
      graph.label((Vertex<String>) null);
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("label(vertex) with a non-existent vertex throws exception")
  public void labelGetNonExistentVertex() {
    Vertex<String> v1 = graph.insert("v1");
    graph.remove(v1);
    try {
      graph.label(v1);
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("label(vertex) with a valid vertex")
  public void labelGetValidVertex() {
    Vertex<String> v1 = graph.insert("v1");
    assertNull(graph.label(v1));
  }

  @Test
  @DisplayName("label(edge) with a null edge throws exception")
  public void labelGetNullEdge() {
    try {
      graph.label((Edge<String>) null);
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("label(Edge) with a non-existent Edge throws exception")
  public void labelGetNonExistentEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    graph.remove(e1);
    try {
      graph.label(e1);
      fail("failed to throw expected exception");
    } catch (PositionException e) {
      return;
    }
  }

  @Test
  @DisplayName("label(edge) with a valid edge")
  public void labelGetValidEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    assertNull(graph.label(e1));
  }

  @Test
  @DisplayName("clearLabels() for all null labeled edges and vertices")
  public void clearLabelsAllNull() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    graph.clearLabels();
    assertNull(graph.label(e1));
    assertNull(graph.label(v1));
    assertNull(graph.label(v2));
  }

  @Test
  @DisplayName("clearLabels() for all empty graph")
  public void clearLabelsEmptyGraph() {
    graph.clearLabels();
    for (Vertex<String> vertex : graph.vertices()) {
      assertNull(graph.label(vertex));
    }
    for (Edge<String> edge : graph.edges()) {
      assertNull(graph.label(edge));
    }
    return;
  }

  @Test
  @DisplayName("clearLabels() for a non-empty graph")
  public void clearLabelsNonEmptyGraph() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    graph.label(v1, "label1");
    graph.label(v2, "label2");
    graph.label(e1, "label3");
    assertEquals(graph.label(v1), "label1");
    assertEquals(graph.label(v2), "label2");
    assertEquals(graph.label(e1), "label3");
    graph.clearLabels();
    for (Vertex<String> vertex : graph.vertices()) {
      assertNull(graph.label(vertex));
    }
    for (Edge<String> edge : graph.edges()) {
      assertNull(graph.label(edge));
    }
    return;
  }
}











