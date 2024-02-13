package hw2;

import exceptions.IndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit Tests for any class implementing the IndexedList interface.
 */
public abstract class IndexedListTest {
  protected static final int LENGTH = 10;
  protected static final int INITIAL = 7;
  private IndexedList<Integer> indexedList;

  public abstract IndexedList<Integer> createArray();

  @BeforeEach
  public void setup() {
    indexedList = createArray();
  }

  @Test
  @DisplayName("get() returns the default value after IndexedList is instantiated.")
  void testGetAfterConstruction() {
    for (int index = 0; index < indexedList.length(); index++) {
      System.out.println("test get after construction");
      assertEquals(INITIAL, indexedList.get(index));
    }
  }

  @Test
  @DisplayName("get() throws exception if index is below the valid range.")
  void testGetWithIndexBelowRangeThrowsException() {
    try {
      System.out.println("test index below range");
      indexedList.get(-1);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("get() throws exception if index is above the valid range")
  void testGetForDefaultValue() {
    try {
      System.out.println("test for out of range");
      indexedList.get(LENGTH + 1);
      fail("IndexException was not thrown for index > length");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() inserts new value at first after IndexedList is instantiated.")
  void testPutFirst() {
      System.out.println("test put at first index");
      indexedList.put(1,2);
      assertEquals(2, indexedList.get(1));
  }

  @Test
  @DisplayName("put() inserts new value at last index after IndexedList is instantiated.")
  void testPutLast() {
    System.out.println("test put at last index");
    indexedList.put(9,2);
    assertEquals(2, indexedList.get(9));
    assertEquals(7, indexedList.get(1));
  }

  @Test
  @DisplayName("put() changes values twice")
  void testPutTwice() {
    System.out.println("test put twice");
    indexedList.put(9,2);
    assertEquals(2, indexedList.get(9));
    indexedList.put(9,3);
    assertEquals(3, indexedList.get(9));
    assertEquals(7, indexedList.get(1));
  }
  @Test
  @DisplayName("put() throws exception if index is above bounds")
  void testPutAboveOutOfBounds() {
    try {
      System.out.println("test put for above out of range");
      indexedList.put(LENGTH + 1, 30);
      fail("IndexException was not thrown for index > length");
    } catch (IndexException ex) {
      return;
    }
  }
  @Test
  @DisplayName("put() a default value removes node")
  void testPutDefault() {
    try {
      System.out.println("test put for default value");
      indexedList.put(2, 30);
      assertEquals(30, indexedList.get(2));
      indexedList.put(2, 7);
      //just use breakpoint nd visualizer since i only have get and put
    } catch (IndexException ex) {
      return;
    }
  }


}
