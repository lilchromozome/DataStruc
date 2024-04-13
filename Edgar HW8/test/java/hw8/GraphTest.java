package hw8;

import exceptions.InsertionException;
import exceptions.RemovalException;
import exceptions.PositionException;
import hw8.graph.Edge;
import hw8.graph.Graph;
import hw8.graph.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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
  @DisplayName("insert(U, V, e) returns an edge with given data")
  public void canGetEdgeAfterInsert() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals(e1.get(), "v1-v2");
  }

  @Test
  @DisplayName("insert(null, V, e) throws PositionException when first vertex is null")
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
  @DisplayName("insert(V, null, e) throws PositionException when second vertex is null")
  public void insertEdgeThrowsPositionExceptionWhenSecondVertexIsNull() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.insert(v, null, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(null) throws InsertionException when vertex data is null")
  public void insertVertexThrowsInsertionExceptionWhenVertexIsNull() {
    try {
      Vertex<String> v = graph.insert(null);
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(v) throws InsertionException when vertex is a duplicate")
  public void insertVertexThrowsInsertionExceptionWhenVertexIsDuplicate() {
    Vertex<String> v1 = graph.insert("v1");

    try {
      graph.insert("v1");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(v) with multiple unique vertices adds them correctly")
  public void insertVertexWithMultipleUniqueVertices() {
    Vertex<String> v = graph.insert("v");
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    assertEquals("v", v.get());
    assertEquals("v1", v1.get());
    assertEquals("v2", v2.get());
  }

  @Test
  @DisplayName("insert(U, v, null) sets edge value to null")
  public void insertEdgeProperlySetsNullForNullEdgeValue() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, null);
    assertNull(e1.get());
  }

  @Test
  @DisplayName("insert(U, V, e) throws InsertionException when inserting duplicate or parallel edge")
  public void insertEdgeThrowsInsertionExceptionForDuplicateEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.insert(v1, v2, "v1-v2");

    try {
      graph.insert(v1, v2, "v1-v2");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(v, v, e) throws InsertionException for self-loop edge")
  public void insertEdgeThrowsInsertionExceptionWhenMakesSelfLoop() {
    Vertex<String> v = graph.insert("v");

    try {
      graph.insert(v, v, "e");
      fail("The expected exception was not thrown");
    } catch (InsertionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(U, V, e) with multiple unique edges adds them successfully")
  public void insertEdgeWithMultipleUniqueEdges() {
    Vertex<String> v = graph.insert("v");
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");

    Edge<String> e = graph.insert(v, v1, "v-v1");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    Edge<String> e2 = graph.insert(v2, v, "v2-v");

    assertEquals("v-v1", e.get());
    assertEquals("v1-v2", e1.get());
    assertEquals("v2-v", e2.get());
  }

  @Test
  @DisplayName("insert(U, V, e) allows multiple edges with different vertices but same label")
  public void insertEdgeForMultipleEdgesWithDuplicateLabel() {
    Vertex<String> v = graph.insert("v");
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");

    Edge<String> e = graph.insert(v, v1, "e");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    Edge<String> e2 = graph.insert(v2, v, "e2");

    assertEquals("e", e.get());
    assertEquals("e1", e1.get());
    assertEquals("e2", e2.get());
  }
  
  @Test
  @DisplayName("insert(U, V, e) correctly handles edges in both directions / opposite edge")
  public void insertEdgeValuesInBothDirections() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");

    Edge<String> v1v2 = graph.insert(v1, v2, "v1-v2");
    Edge<String> v2v1 = graph.insert(v2, v1, "v2-v1");

    assertEquals("v1-v2", v1v2.get());
    assertEquals("v2-v1", v2v1.get());
  }

  @Test
  @DisplayName("insert(U, V, e) throws PositionException with removed source vertex")
  public void insertEdgeFailsWithRemovedSourceVertex() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    graph.remove(v1);

    try {
      graph.insert(v1, v2, "v1-v2");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("insert(U, V, e) does not throw exception for non-duplicate edges")
  public void insertEdgeDoesNotThrowExceptionForNonDuplicateEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");

    graph.insert(v1, v2, "v1-v2");
    graph.insert(v1, v3, "v1-v3");
  }

  @Test
  @DisplayName("to() returns correct destination vertex of an edge")
  public void toReturnsCorrectDestinationVertexOfEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");

    Edge<String> v1v2 = graph.insert(v1, v2, "e");
    assertEquals(v2, graph.to(v1v2));
  }

  @Test
  @DisplayName("from() returns correct starting vertex of an edge")
  public void fromReturnsCorrectStartingVertexOfEdge() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");

    Edge<String> v1v2 = graph.insert(v1, v2, "e");
    assertEquals(v1, graph.from(v1v2));
  }

  @Test
  @DisplayName("to() throws PositionException if the edge is removed")
  public void toThrowsPositionExceptionIfEdgeNotInGraph() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    graph.remove(e1);

    try {
      graph.to(e1);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("from() throws PositionException if the edge is removed")
  public void fromThrowsPositionExceptionIfEdgeNotInGraph() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    graph.remove(e1);

    try {
      graph.from(e1);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("from() correctly identifies starting vertex for an edge")
  public void iterateFromVertexReturnsCorrectStartVertex() {
    Vertex<String> v = graph.insert("v");
    Vertex<String> v1 = graph.insert("v1");
    Edge<String> e = graph.insert(v, v1, "e");

    assertEquals(v, graph.from(e));
  }

  @Test
  @DisplayName("to() correctly identifies ending vertex for an edge")
  public void iterateToVertexReturnsCorrectEndVertex() {
    Vertex<String> v = graph.insert("v");
    Vertex<String> v1 = graph.insert("v1");
    Edge<String> e = graph.insert(v, v1, "e");

    assertEquals(v1, graph.to(e));
  }

  @Test
  @DisplayName("remove(v) throws PositionException when removing an already removed vertex")
  public void removeVertexThrowsPositionExceptionForDoubleRemoval() {
    Vertex<String> v1 = graph.insert("v1");
    assertEquals("v1", v1.get());
    graph.remove(v1);

    try {
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove(v) throws RemovalException when vertex has connected edges")
  public void removeVertexWithEdgesThrowsRemovalException() {
    Vertex<String> v1 = graph.insert("v1");
    assertEquals("v1", v1.get());
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals("v1-v2", e1.get());

    try {
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (RemovalException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove(v) returns the correct vertex with given data after removal")
  public void canGetVertexAfterRemove() {
    Vertex<String> v1 = graph.insert("v1");
    assertEquals("v1", v1.get());
    assertEquals("v1", graph.remove(v1));
  }

  @Test
  @DisplayName("remove(v) throws RemovalException for vertex with incident incoming edge")
  public void removeVertexWithIncidentIncomingEdgesThrowsRemovalException() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v2, v1, "v2-v1");

    try {
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (RemovalException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove(v) throws RemovalException for vertex with incident outgoing edge")
  public void removeVertexWithIncidentOutgoingEdgesThrowsRemovalException() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");

    try {
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (RemovalException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove(null) throws PositionException when v is null")
  public void removeVertexThrowsPositionExceptionWhenNull() {
    Vertex<String> v1 = null;

    try {
      graph.remove(v1);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove(v) successfully removes multiple unique vertices")
  public void removeVertexWithMultipleUniqueVertices() {
    Vertex<String> v = graph.insert("v");
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");

    assertEquals("v", graph.remove(v));
    assertEquals("v1", graph.remove(v1));
    assertEquals("v2", graph.remove(v2));
  }

  @Test
  @DisplayName("remove(e) returns an edge with given data after removal")
  public void canGetEdgeDataAfterRemove() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");

    assertEquals("v1-v2", graph.remove(e1));
  }

  @Test
  @DisplayName("remove(e) correctly returns data of the removed edge")
  public void removeEdgeReturnsCorrectData() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    assertEquals("v1-v2", e1.get());
    assertEquals("v1-v2", graph.remove(e1));
  }

  @Test
  @DisplayName("remove(e) throws PositionException when edge is already removed")
  public void removeEdgeThrowsPositionExceptionWhenNonExistent() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "v1-v2");
    graph.remove(e1);

    try {
      graph.remove(e1);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove(null) throws PositionException for null edge")
  public void removeEdgeThrowsPositionExceptionWhenNull() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e = null;
    try {
      graph.remove(e);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("remove(e) works for multiple edges")
  public void removeEdgeWithMultipleUniqueEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    Edge<String> e2 = graph.insert(v2, v3, "e2");

    assertEquals("e1", graph.remove(e1));
    assertEquals("e2", graph.remove(e2));
  }

  @Test
  @DisplayName("Iterating over vertices in empty graph yields no vertices")
  public void iterateVertexForEmptyGraph() {
    Iterable<Vertex<String>> vertices = graph.vertices();

    assertFalse(vertices.iterator().hasNext());

    for (Vertex<String> vertex : vertices) {
      fail("There should be no vertices to iterate over in an empty graph");
    }
  }

  @Test
  @DisplayName("Iterating over vertices in a graph with multiple vertices is accurate")
  public void iterateVertexForMultipleVertices() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");

    int counter = 0;

    for (Vertex<String> v: graph.vertices()) {
      counter++;
    }
    assertEquals(3, counter);
  }

  @Test
  @DisplayName("Iterating over vertices does not include removed vertex after removal")
  public void iterateVertexWorksAfterVertexRemoval() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    graph.remove(v1);

    boolean found = false;
    for (Vertex<String> vertex : graph.vertices()) {
      if (vertex.equals(v1)) {
        found = true;
        break;
      }
    }

    assertFalse(found);
  }

  @Test
  @DisplayName("Iterating over edges in empty graph yields no edges")
  public void iterateEdgeForEmptyGraph() {
    Iterable<Edge<String>> edges = graph.edges();

    assertFalse(edges.iterator().hasNext());
    for (Edge<String> edge : edges) {
      fail("There should be no edges to iterate over in an empty graph");
    }
  }

  @Test
  @DisplayName("Iterating over edges in a graph with multiple edges is accurate")
  public void iterateEdgeOverMultipleEdges() {
    Vertex<String> v = graph.insert("v");
    Vertex<String> v1 = graph.insert("v1");
    graph.insert(v, v1, "v-v1");

    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v3 = graph.insert("v3");
    graph.insert(v2, v3, "v2-v3");

    Vertex<String> v4 = graph.insert("v4");
    Vertex<String> v5 = graph.insert("v5");
    graph.insert(v4, v5, "v4-v5");

    Vertex<String> v6 = graph.insert("v6");
    Vertex<String> v7 = graph.insert("v7");
    graph.insert(v6, v7, "v6-v7");

    int counter = 0;
    for (Edge<String> e : graph.edges()) {
      counter++;
    }

    assertEquals(4, counter);
  }

  @Test
  @DisplayName("incoming(null) throws PositionException for null vertex")
  public void iterateIncomingEdgesThrowPositionExceptionWhenNullVertex() {
    try {
      graph.incoming(null);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("outgoing(null) throws PositionException for null vertex")
  public void iterateOutgoingEdgesThrowPositionExceptionWhenNullVertex() {
    try {
      graph.outgoing(null);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("incoming(v) throws PositionException when vertex is removed")
  public void iterateIncomingEdgesThrowsPositionExceptionWhenVertexIsRemoved() {
    try {
      Vertex<String> v = graph.insert("v");
      graph.remove(v);
      graph.incoming(v);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("outgoing(v) yields no edges for vertex with no outgoing edges")
  public void iterateOutgoingForVertexWithNoOutgoingEdges() {
    Vertex<String> v = graph.insert("v");
    Iterable<Edge<String>> outgoingEdges = graph.outgoing(v);
    assertFalse(outgoingEdges.iterator().hasNext());
  }

  @Test
  @DisplayName("incoming(v) yields no edges for vertex with no incoming edges")
  public void iterateIncomingEdgesForVertexWithNoIncomingEdges() {
    Vertex<String> v = graph.insert("v");
    Iterable<Edge<String>> incomingEdges = graph.incoming(v);
    assertFalse(incomingEdges.iterator().hasNext());
  }

  @Test
  @DisplayName("outgoing(v) yields no edges for vertex with only incoming edges")
  public void iterateOutgoingEdgesForVertexWithOnlyIncomingEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e = graph.insert(v2, v1, "e");
    Iterable<Edge<String>> outgoingEdges = graph.outgoing(v1);
    assertFalse(outgoingEdges.iterator().hasNext());
  }

  @Test
  @DisplayName("incoming(v) yields no edges for vertex with only outgoing edges")
  public void iterateIncomingEdgesForVertexWithOnlyOutgoingEdges() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> v1v2 = graph.insert(v1, v2, "v1v2");
    Iterable<Edge<String>> incomingEdges = graph.incoming(v1);
    assertFalse(incomingEdges.iterator().hasNext());
  }

  @Test
  @DisplayName("outgoing(v) correctly identifies all outgoing edges")
  public void iterateOutgoingEdgesAreCorrect() {
    Vertex<String> v = graph.insert("v");
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");

    graph.insert(v, v1, "v-v1");
    graph.insert(v, v2, "v-v2");

    Set<String> expectedOutgoing = new HashSet<>();
    expectedOutgoing.add("v-v1");
    expectedOutgoing.add("v-v2");

    int counter = 0;
    for (Edge<String> edge : graph.outgoing(v)) {
      assertTrue(expectedOutgoing.contains(edge.get()));
      counter++;
    }

    assertEquals(2, counter);
  }

  @Test
  @DisplayName("incoming(v) correctly identifies all incoming edges")
  public void iterateIncomingEdgesAreCorrect() {
    Vertex<String> v = graph.insert("v");
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");

    graph.insert(v1, v, "v1-v");
    graph.insert(v2, v, "v2-v");

    Set<String> expectedIncoming = new HashSet<>();
    expectedIncoming.add("v1-v");
    expectedIncoming.add("v2-v");

    int counter = 0;
    for (Edge<String> edge : graph.incoming(v)) {
      assertTrue(expectedIncoming.contains(edge.get()));
      counter++;
    }
    assertEquals(2, counter);
  }

  @Test
  @DisplayName("from() returns the correct starting vertex of an edge")
  public void getStartingVertexOfEdge() {
    Vertex<String> startVertex = graph.insert("v1");
    Vertex<String> endVertex = graph.insert("v2");
    Edge<String> e = graph.insert(startVertex, endVertex, "e");

    assertEquals(startVertex, graph.from(e));
  }

  @Test
  @DisplayName("to() returns the correct ending vertex of an edge")
  public void getEndingVertexOfEdge() {
    Vertex<String> startVertex = graph.insert("v1");
    Vertex<String> endVertex = graph.insert("v2");
    Edge<String> e = graph.insert(startVertex, endVertex, "e");

    assertEquals(endVertex, graph.to(e));
  }

  @Test
  @DisplayName("to(null) throws PositionException for null edge")
  public void toThrowsPositionExceptionForNullEdge() {
    try {
      Edge<String> e = null;
      graph.to(e);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("from(null) throws PositionException for null edge")
  public void fromThrowsPositionExceptionForNullEdge() {
    try {
      Edge<String> e = null;
      graph.from(e);
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("label(v, l) correctly labels a vertex and can retrieve the label")
  public void labelVertexAndRetrieveLabel() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");

    graph.label(v1, "label for v1");
    graph.label(v2, "label for v2");

    assertEquals("label for v1", graph.label(v1));
    assertEquals("label for v2", graph.label(v2));
  }

  @Test
  @DisplayName("label(null, l) throws PositionException for null vertex position")
  public void labelVertexThrowsPositionExceptionForNull() {
    try {
      Vertex<String> v1 = null;
      graph.label(v1, "v1 label");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("label(v, l) throws PositionException for invalid vertex position")
  public void labelVertexThrowsPositionExceptionForInvalid() {
    Vertex<String> v1 = graph.insert("v1");
    try {
      graph.remove(v1);
      graph.label(v1, "v1 label");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("label(v, null) allows null labels for vertices")
  public void labelVertexAllowsNullLabels() {
    Vertex<String> v1 = graph.insert("v1");
    graph.label(v1, null);

    assertNull(graph.label(v1));
  }

  @Test
  @DisplayName("label(e, l) correctly labels an edge and can retrieve the label")
  public void labelEdgeAndRetrieveLabel() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Vertex<String> v = graph.insert("v");

    Edge<String> e1 = graph.insert(v1, v2, "e1");
    Edge<String> e = graph.insert(v, v1, "e");

    graph.label(e1, "label for e1");
    graph.label(e, "label for e");

    assertEquals("label for e1", graph.label(e1));
    assertEquals("label for e", graph.label(e));
  }

  @Test
  @DisplayName("label(null, l) throws PositionException for null edge")
  public void labelEdgeThrowsPositionExceptionForNull() {
    try {
      Edge<String> e = null;
      graph.label(e, "null");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("label(e, l) throws PositionException for invalid edge after removal")
  public void labelEdgeThrowsPositionExceptionForInvalid() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e = graph.insert(v1, v2, "e");

    try {
      graph.remove(e);
      graph.label(e, "e");
      fail("The expected exception was not thrown");
    } catch (PositionException ex) {
      return;
    }
  }

  @Test
  @DisplayName("clearLabels() effectively clears all labels from vertices and edges")
  public void clearLabelsGetsRidOfLabels() {
    Vertex<String> v = graph.insert("v");
    Vertex<String> v1 = graph.insert("v1");

    Edge<String> e = graph.insert(v, v1, "v-v1");

    graph.label(v1, "v1");
    graph.label(e, "e");

    assertEquals("v1", graph.label(v1));
    assertEquals("e", graph.label(e));

    graph.clearLabels();

    assertNull(graph.label(v1));
    assertNull(graph.label(e));
  }

  @Test
  @DisplayName("label(e, null) allows null labels for edges")
  public void labelEdgeAllowsNullLabels() {
    Vertex<String> v1 = graph.insert("v1");
    Vertex<String> v2 = graph.insert("v2");
    Edge<String> e1 = graph.insert(v1, v2, "e1");
    graph.label(e1, null);
    assertNull(graph.label(e1));
  }

}
