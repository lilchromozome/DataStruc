package hw2;

import exceptions.IndexException;
import exceptions.LengthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

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
  @DisplayName("constructor() throws exception when size < 0.")
  void testConstructorThrowsExceptionWithNegativeSize() {
    try {
      IndexedList<Integer> testIndexedList = new SparseIndexedList<>(-1, 3);
      fail("LengthException was not thrown for size < 0");
    } catch (LengthException ex) {
      return;
    }
  }

  @Test
  @DisplayName("constructor() throws exception when size = 0.")
  void testConstructorThrowsExceptionWithZeroSize() {
    try {
      IndexedList<Integer> testIndexedList = new SparseIndexedList<>(0, 3);
      fail("LengthException was not thrown for size = 0");
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
  @DisplayName("get() returns default value after IndexedList of size 1 is instantiated.")
  void testGetReturnsDefaultValueWithListOfSizeOne() {
    SparseIndexedList<Integer> sizeOne = new SparseIndexedList<>(1, INITIAL);
    assertEquals(INITIAL, sizeOne.get(0));
  }

  @Test
  @DisplayName("get() throws exception if index is below the valid range.")
  void testGetWithIndexBelowRangeThrowsException() {
    try {
      indexedList.get(-1);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("length() returns the correct size of the SparseLinkedList")
  void testLengthReturnsCorrectSize() {
    assertEquals(LENGTH, indexedList.length());
  }

  @Test
  @DisplayName("put() throws exception if index is below the valid range.")
  void testPutWithIndexBelowRangeThrowsException() {
    try {
      indexedList.put(-1, 5);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("put() throws exception if index is above the valid range.")
  void testPutWithIndexAboveRangeThrowsException() {
    try {
      indexedList.put(LENGTH, 5);
      fail("IndexException was not thrown for index < 0");
    } catch (IndexException ex) {
      return;
    }
  }

  @Test
  @DisplayName("get() after put() with non-default value returns the non-default value at a valid node.")
  void testGetAfterPutReturnsNonDefaultValueAtValidNode() {
    indexedList.put(3, 1);
    for (int i = 0; i < LENGTH; i++) {
      if (i == 3) {
        assertEquals(1, indexedList.get(i));
      } else {
        assertEquals(INITIAL, indexedList.get(i));
      }
    }
  }

  @Test
  @DisplayName("put() successfully replaces a non-default value with another non-default value.")
  void testPutReplacesNonDefaultValueWithAnotherNonDefaultValue() {
    indexedList.put(8, 123);
    assertEquals(123, indexedList.get(8));
    indexedList.put(8, 412);
    assertEquals(412, indexedList.get(8));
  }

  @Test
  @DisplayName("get() after put() with default value returns the default value.")
  void testGetAfterPutReturnsDefaultValue() {
    indexedList.put(3, 2);
    assertEquals(2, indexedList.get(3));
    indexedList.put(3, INITIAL);
    for (int i = 0; i < LENGTH; i++) {
      assertEquals(INITIAL, indexedList.get(i));
    }
  }

  @Test
  @DisplayName("put() at last index in empty list is successful.")
  void testPutAtLastIndexInEmptyList() {
    indexedList.put(LENGTH - 1, 28);
    for (int i = 0; i < LENGTH; i++) {
      if (i == LENGTH - 1) {
        assertEquals(28, indexedList.get(i));
      } else {
        assertEquals(INITIAL, indexedList.get(i));
      }
    }
  }

  @Test
  @DisplayName("put() at index 0 in empty list is successful.")
  void testPutAtZeroIndexInEmptyList() {
    indexedList.put(0, 49);
    assertEquals(49, indexedList.get(0));
    for (int i = 1; i < LENGTH; i++) {
      assertEquals(INITIAL, indexedList.get(i));
    }
  }

  @Test
  @DisplayName("put() at index 0 and last index in an empty list is successful.")
  void testPutAtZeroAndLastIndexInEmptyList() {
    indexedList.put(0, 99);
    indexedList.put(LENGTH - 1, 32);
    assertEquals(99, indexedList.get(0));
    assertEquals(32, indexedList.get(LENGTH - 1));

    for (int i = 1; i < LENGTH - 1; i++) {
      assertEquals(INITIAL, indexedList.get(i));
    }
  }

  @Test
  @DisplayName("iterator iterates through an empty node list with for each loop.")
  void testIteratorWorksOnEmptyNodeList() {
    int count = 0;
    for (int data: indexedList) {
      assertEquals(data, INITIAL);
      count++;
    }
    assertEquals(indexedList.length(), count);
  }

  @Test
  @DisplayName("iterator returns data correctly after insertion at beginning, and then end of list," +
      " with a for each loop.")
  void testIteratorReturnsDataInOrderOfIndices() {
    indexedList.put(0, 10);
    indexedList.put(LENGTH - 1, 100);

    int count = 0;
    for (int data: indexedList) {
      if (count == 0) {
        assertEquals(10, data);
      } else if (count == LENGTH - 1) {
        assertEquals(100, data);
      } else {
        assertEquals(INITIAL, data);
      }
      count++;
    }
    assertEquals(indexedList.length(), count);
  }

  @Test
  @DisplayName("iterator returns data in correct order in a fully populated list")
  void testIteratorOnFullyPopulatedList() {
    int testVal = 10;
    for (int i = 0; i < indexedList.length(); i++) {
      indexedList.put(i, testVal++);
    }

    int newTestVal = 10;
    int count = 0;
    for (int data: indexedList) {
      assertEquals(newTestVal++, data);
      count++;
    }
    assertEquals(indexedList.length(), count);
  }

  @Test
  @DisplayName("put() replaces head with default value and replaces head with next successfully.")
  void testPutDefaultValueAtHeadInPopulatedList() {
    indexedList.put(4, 50);
    indexedList.put(8, 70);
    indexedList.put(3, 40);
    indexedList.put(8, INITIAL);
    indexedList.put(1, 20);
    indexedList.put(0, INITIAL);
    indexedList.put(7, 80);
    indexedList.put(5, 60);
    indexedList.put(9, 100);
    indexedList.put(2, 30);
    indexedList.put(1, INITIAL);

    for (int i = 0; i < indexedList.length(); i++) {
      if (i == 2) {
        assertEquals(30, indexedList.get(i));
      } else if (i == 3) {
        assertEquals(40, indexedList.get(i));
      } else if (i == 4) {
        assertEquals(50, indexedList.get(i));
      } else if (i == 5) {
        assertEquals(60, indexedList.get(i));
      } else if (i == 7) {
        assertEquals(80, indexedList.get(i));
      } else if (i == 9) {
        assertEquals(100, indexedList.get(i));
      } else {
        assertEquals(INITIAL, indexedList.get(i));
      }
    }
  }

  @Test
  @DisplayName("put() replaces head with default value and replaces head with next successfully.")
  void testPutDefaultValueAtOneAfterHead() {
    indexedList.put(4, 50);
    indexedList.put(8, 70);
    indexedList.put(3, 40);
    indexedList.put(8, INITIAL);
    indexedList.put(1, 20);
    indexedList.put(0, INITIAL);
    indexedList.put(7, 80);
    indexedList.put(5, 60);
    indexedList.put(9, 100);
    indexedList.put(2, 30);
    indexedList.put(2, INITIAL);

    for (int i = 0; i < indexedList.length(); i++) {
      if (i == 1) {
        assertEquals(20, indexedList.get(i));
      } else if (i == 3) {
        assertEquals(40, indexedList.get(i));
      } else if (i == 4) {
        assertEquals(50, indexedList.get(i));
      } else if (i == 5) {
        assertEquals(60, indexedList.get(i));
      } else if (i == 7) {
        assertEquals(80, indexedList.get(i));
      } else if (i == 9) {
        assertEquals(100, indexedList.get(i));
      } else {
        assertEquals(INITIAL, indexedList.get(i));
      }
    }
  }

  @Test
  @DisplayName("hasNext() returns true until the iterator reaches one past the last element.")
  void testHasNextReturnsTrueUntilItReachesEnd() {
    Iterator<Integer> iter = indexedList.iterator();
    int count = 0;
    while (iter.hasNext()) {
      count++;
      iter.next();
    }
    assertEquals(count, LENGTH);
  }

  @Test
  @DisplayName("hasNext() returns false after next() has been called LENGTH times.")
  void testHasNextReturnsFalseAfterNextRunsLengthTimes() {
    Iterator<Integer> iter = indexedList.iterator();
    int count = 0;
    while (count < 10) {
      count++;
      iter.next();
    }

    assertFalse(iter.hasNext());
  }

  @Test
  @DisplayName("next() returns default value each time in an empty list.")
  void testNextReturnsDefaultValueEachTimeInEmptyList() {
    Iterator<Integer> iter = indexedList.iterator();
    for (int i = 0; i < 10; i++) {
      assertEquals(iter.next(), INITIAL);
    }
  }

  @Test
  @DisplayName("next() returns correct values each time in a list with one element.")
  void testNextReturnsDefaultValueEachTimeInListWithOneNode() {
    indexedList.put(6, 99);
    Iterator<Integer> iter = indexedList.iterator();
    for (int i = 0; i < 10; i++) {
      if (i == 6) {
        assertEquals(iter.next(), 99);
      } else {
        assertEquals(iter.next(), INITIAL);
      }
    }
  }

  @Test
  @DisplayName("next() returns correct values each time in a list where the only " +
      "indices populated are at index = 0 and index = length - 1.")
  void testNextReturnsDefaultValueEachTimeInListWithTwoSpreadOutNode() {
    indexedList.put(0, 99);
    indexedList.put(LENGTH - 1, 13);
    Iterator<Integer> iter = indexedList.iterator();
    for (int i = 0; i < 10; i++) {
      if (i == 0) {
        assertEquals(iter.next(), 99);
      } else if (i == 9) {
        assertEquals(iter.next(), 13);
      } else {
        assertEquals(iter.next(), INITIAL);
      }
    }
  }

  @Test
  @DisplayName("next() throws exception when called LENGTH + 1 times")
  void testNextThrowsExceptionWhenCalledLengthPlusOneTimes() {
    Iterator<Integer> iter = indexedList.iterator();
    try {
      for (int i = 0; i < LENGTH + 1; i++) {
        iter.next();
      }
      fail("NoSuchElementException was not thrown.");
    } catch (NoSuchElementException ex) {
      return;
    }
  }
}
