package hw2;

import exceptions.IndexException;
import exceptions.LengthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
  @DisplayName("constructor throws exception if size is <= 0.")
  void testConstructorThrowsExceptionForInvalidSize() {
    try {
      indexedList = new SparseIndexedList<>(-LENGTH, INITIAL);
      fail("LengthException was not thrown where it was expected!");
    } catch (LengthException ex) {
      return;
    }
  }

  @Test
  @DisplayName("get() returns the default value after IndexedList is instantiated.")
  void testGetAfterConstruction() {
    for (int index = 0; index < indexedList.length(); index++) {
      assertEquals(INITIAL, indexedList.get(index));
    }
  }

  @Test
  @DisplayName("get() returns new value after IndexedList is filled")
  void testGetAfterAllNodesFilled() {
    int newValue = 4;
    for (int i = 0; i < LENGTH; i++) {
      indexedList.put(i, newValue);
    }
    for (int index = 0; index < indexedList.length(); index++) {
      assertEquals(newValue, indexedList.get(index));
    }
  }

  @Test
  @DisplayName("get() throws exception if index is below the valid range.")
  void testGetWithIndexBelowRangeThrowsException() {
    try {
      indexedList.get(-LENGTH);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }
  @Test
  @DisplayName("get() throws exception if index is above the valid range.")
  void testGetWithIndexAboveRangeThrowsException() {
    try {
      indexedList.get(LENGTH);
      fail("IndexException was not thrown for index > length");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() throws exception if index is below the valid range.")
  void testPutWithIndexBelowRangeThrowsException() {
    try{
      indexedList.put(-LENGTH,10);
      fail("IndexException was not thrown for index <0");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() throws exception if index is above the valid range.")
  void testPutWithIndexAboveRangeThrowsException() {
    try {
      indexedList.put(LENGTH,10);
      fail("IndexedException was not thrown for index >= length");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() inserts a value into the list")
  void testPutInsertsValueIntoProperIndex() {
    indexedList.put(5,1);
    assertEquals(1, indexedList.get(5));
  }

  @Test
  @DisplayName("put() inserts node after head and deletes it")
  void testPutInsertsValueAndDeletesAfterHead() {
    indexedList.put(5,1);
    indexedList.put(6,5);
    indexedList.put(6, INITIAL);
    assertEquals(INITIAL, indexedList.get(6));
  }

  @Test
  @DisplayName("Deleting node when in between 2 nodes")
  void testPutDeleteNodeInBetweenNodes() {
    indexedList.put(1, 2);
    indexedList.put(3,4);
    indexedList.put(6,9);
    indexedList.put(3,INITIAL);
    assertEquals(INITIAL, indexedList.get(3));
  }

  @Test
  @DisplayName("put() updates an element in the list.")
  void testPutUpdatesExistingElement() {
    indexedList.put(5,1);
    indexedList.put(5,8);
    assertEquals(8, indexedList.get(5));
  }

  @Test
  @DisplayName("put() updates an element to the default value in the list")
  void testPutUpdatesDefaultValueExistingElement() {
    indexedList.put(5,1);
    indexedList.put(5,INITIAL);
    assertEquals(INITIAL, indexedList.get(5));
  }

  @Test
  @DisplayName("put() inserts a value in between two nodes")
  void testPutInsertsValueBetweenTwoNodes() {
    indexedList.put(2,5);
    indexedList.put(6,4);
    indexedList.put(4,6);
    assertEquals(6, indexedList.get(4));
  }

  @Test
  @DisplayName("put() inserts 4 nodes")
  void testPutInsertsFourNodes() {
    indexedList.put(1,5);
    indexedList.put(2,4);
    indexedList.put(4,6);
    indexedList.put(6,8);
    assertEquals(6, indexedList.get(4));
  }

  @Test
  @DisplayName("test put() with index 0")
  void testPutValueIndexZero() {
    indexedList.put(0,5);
    assertEquals(5, indexedList.get(0));
  }

  @Test
  @DisplayName("length() returns length of the list.")
  void testLength() {
    assertEquals(LENGTH, indexedList.length());
  }

  @Test
  @DisplayName("test enhanced for loop iterator after IndexedList is instantiated.")
  void testEnhancedLoopAfterConstruction() {
    int counter = 0;
    for (int element: indexedList) {
      assertEquals(INITIAL, element);
      counter++;
    }
    assertEquals(LENGTH,counter);
  }

  @Test
  @DisplayName("test iterator Object")
  void testIteratorObjectLoopAfterConstruction() {
    int counter = 0;
    Iterator<Integer> it = indexedList.iterator();
    while (it.hasNext()) {
      Integer element = it.next();
      assertEquals(INITIAL, element);
      counter++;
    }
    assertEquals(LENGTH, counter);
  }

  @Test
  @DisplayName("test enhanced for loop iterator after IndexedList is modified.")
  void testEnhancedLoopAfterModified() {
    int counter = 0;
    int newValue = 4;
    for (int i = 0; i < LENGTH; i++) {
      indexedList.put(i, newValue);
    }
    for (int element: indexedList) {
      assertEquals(newValue, element);
      counter++;
    }
    assertEquals(LENGTH,counter);
  }

  @Test
  @DisplayName("hasNext() returns false when no more elements")
  void testHasNextReturnsFalseWhenCounterAboveLength() {
    Iterator<Integer> it = indexedList.iterator();
    for (int i = 0; i < LENGTH; i++) {
      it.next();
    }
    assertEquals(false, it.hasNext());
  }

  @Test
  @DisplayName("next() throws exception when hasNext is false")
  void testNextWhenHasNextFalseThrowsException() {
    Iterator<Integer> it = indexedList.iterator();
    while (it.hasNext()) {
      Integer element = it.next();
      assertEquals(INITIAL, element);
    }
    try {
      Integer element = it.next();
      fail("NoSuchElementException was not thrown for hasNext() == false");
    } catch (NoSuchElementException ex) {
      return;
    }
  }
}
