package hw3;

import exceptions.IndexException;
import hw3.list.MeasuredIndexedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MeasuredIndexedListTest {

  private static final int LENGTH = 15;
  private static final int DEFAULT_VALUE = 3;

  private MeasuredIndexedList<Integer> measuredIndexedList;

  @BeforeEach
  void setup() {
    measuredIndexedList = new MeasuredIndexedList<>(LENGTH, DEFAULT_VALUE);
  }

  @Test
  @DisplayName("MeasuredIndexedList starts with zero reads")
  void zeroReadsStart() {
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexedList starts with zero writes")
  void zeroWritesStart() {
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Does not increment mutations after calling put")
  void noAccessesAfterPut() {
    measuredIndexedList.put(1,2);
    assertEquals(0,measuredIndexedList.accesses());
    assertEquals(1,measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Does not increment accesses after calling get")
  void noMutationsAfterPut() {
    int firstValue = measuredIndexedList.get(2);
    assertEquals(1,measuredIndexedList.accesses());
    assertEquals(0,measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Correctly increments accesses without messing with mutations")
  void returnsAccessesAfterManyGets() {
    int firstValue = measuredIndexedList.get(2);
    assertEquals(1, measuredIndexedList.accesses());
    assertEquals(0, measuredIndexedList.mutations());

    int secondValue = measuredIndexedList.get(3);
    assertEquals(2, measuredIndexedList.accesses());
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Correctly increments mutations without messing with accesses")
  void returnsMutationsAfterManyPuts() {
    measuredIndexedList.put(1,2);
    assertEquals(1, measuredIndexedList.mutations());
    assertEquals(0, measuredIndexedList.accesses());

    measuredIndexedList.put(2,3);
    assertEquals(2, measuredIndexedList.mutations());
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("Count method correctly counts values after put")
  void correctCountAfterPut() {
    assertEquals(LENGTH, measuredIndexedList.count(DEFAULT_VALUE));
    measuredIndexedList.put(2,1);
    assertEquals(LENGTH - 1, measuredIndexedList.count(DEFAULT_VALUE));
  }

  @Test
  @DisplayName("Count method correctly counts values after multiple puts")
  void correctCountAfterManyPuts() {
    assertEquals(LENGTH, measuredIndexedList.count(DEFAULT_VALUE));
    measuredIndexedList.put(2,1);
    assertEquals(LENGTH - 1, measuredIndexedList.count(3));
    for (int i = 0; i < LENGTH; i++) {
      measuredIndexedList.put(i, 1);
    }
    assertEquals(0,measuredIndexedList.count(DEFAULT_VALUE));
    assertEquals(LENGTH, measuredIndexedList.count(1));
  }

  @Test
  @DisplayName("Count method successfully increases the accesses value after calling")
  void countIncreasesAccesses() {
    measuredIndexedList.count(DEFAULT_VALUE);
    assertEquals(15, measuredIndexedList.accesses());
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Reset method cleans out both accesses and mutations")
  void resetCleansAccessesAndMutations() {
    for (int i = 0; i < LENGTH; i++) {
      measuredIndexedList.put(i, 1);
      int testValue = measuredIndexedList.get(i);
    }
    assertEquals(LENGTH, measuredIndexedList.accesses());
    assertEquals(LENGTH, measuredIndexedList.mutations());
    measuredIndexedList.reset();
    assertEquals(0, measuredIndexedList.accesses());
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Correctly only updates mutations after running reset method")
  void putAfterResetUpdatesMutationsOnly() {
    measuredIndexedList.reset();
    measuredIndexedList.put(0,1);
    assertEquals(0, measuredIndexedList.accesses());
    assertEquals(1, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Correctly only updates accesses after running reset method")
  void getAfterResetUpdatesAccessesOnly() {
    measuredIndexedList.reset();
    int testValue = measuredIndexedList.get(0);
    assertEquals(1, measuredIndexedList.accesses());
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Length does not increment any counters")
  void lengthDoesNotIncrement() {
    int length = measuredIndexedList.length();
    assertEquals(0, measuredIndexedList.accesses());
    assertEquals(0, measuredIndexedList.mutations());
    assertEquals(length, LENGTH);
  }

  @Test
  @DisplayName("Count returns zero for something not in the list")
  void countReturnsZeroForNotExistingValue() {
    int testCount = measuredIndexedList.count(DEFAULT_VALUE - 1);
    assertEquals(0, testCount);
  }

  @Test
  @DisplayName("Put works at the zero index")
  void putWorksAtZeroIndex() {
    measuredIndexedList.put(0,1);
    assertEquals(measuredIndexedList.get(0), 1);
    assertEquals(1, measuredIndexedList.accesses());
    assertEquals(1, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Put works at the last index")
  void putWorksAtLastIndex() {
    measuredIndexedList.put(LENGTH - 1,1);
    assertEquals(measuredIndexedList.get(LENGTH - 1), 1);
    assertEquals(1, measuredIndexedList.accesses());
    assertEquals(1, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Get works at the zero index")
  void getWorksAtZeroIndex() {
    int testValue = measuredIndexedList.get(0);
    assertEquals(DEFAULT_VALUE, testValue);
    assertEquals(1, measuredIndexedList.accesses());
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Get works at the last index")
  void getWorksAtLastIndex() {
    int testValue = measuredIndexedList.get(LENGTH - 1);
    assertEquals(DEFAULT_VALUE, testValue);
    assertEquals(1, measuredIndexedList.accesses());
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Multiple consecutive resets maintain zero counters")
  void multipleResetsWorkCorrectly() {
    measuredIndexedList.reset();
    measuredIndexedList.reset();
    assertEquals(0, measuredIndexedList.accesses());
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Count still works after a reset")
  void countWorksAfterReset() {
    measuredIndexedList.reset();
    measuredIndexedList.put(0, 1);
    assertEquals(measuredIndexedList.count(1), 1);
    assertEquals(LENGTH, measuredIndexedList.accesses());
    assertEquals(1, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Access is not incremented when get called with invalid index")
  void noAccessIncrementWhenGetInvalidIndex() {
    try {
      measuredIndexedList.get(LENGTH + 1);
      fail("The Expected IndexException was not thrown.");
    } catch (IndexException ex) {
      return;
    }
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("Access is not incremented when get called with negative index")
  void noAccessIncrementWhenGetNegativeIndex() {
    try {
      measuredIndexedList.get(-1);
      fail("The Expected IndexException was not thrown.");
    } catch (IndexException ex) {
      assertEquals(0, measuredIndexedList.accesses());
      return;
    }
  }

  @Test
  @DisplayName("Mutations is not incremented when put called with invalid index")
  void noMutationIncrementWhenPutInvalidIndex() {
    try {
      measuredIndexedList.put(LENGTH + 1, DEFAULT_VALUE);
      fail("The Expected IndexException was not thrown.");
    } catch (IndexException ex) {
      assertEquals(0, measuredIndexedList.mutations());
      return;
    }

  }

  @Test
  @DisplayName("Mutations is not incremented when put called with negative index")
  void noMutationIncrementWhenPutNegativeIndex() {
    try {
      measuredIndexedList.put(-1, DEFAULT_VALUE);
      fail("The Expected IndexException was not thrown.");
    } catch (IndexException ex) {
      assertEquals(0, measuredIndexedList.mutations());
      return;
    }

  }

  @Test
  @DisplayName("Iterating over the list does not change the counters")
  void iteratorDoesNotChangeCounters() {
    measuredIndexedList.reset();
    for (Integer value : measuredIndexedList) {
      int testValue = value;
    }

    assertEquals(0, measuredIndexedList.accesses());
    assertEquals(0,measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("Reset method cleans and does not change values")
  void resetDoesNotChangeValues() {
    for (int i = 0; i < LENGTH; i++) {
      measuredIndexedList.put(i, 1);
      int testValue = measuredIndexedList.get(i);
    }
    assertEquals(LENGTH, measuredIndexedList.accesses());
    assertEquals(LENGTH, measuredIndexedList.mutations());
    measuredIndexedList.reset();
    assertEquals(0, measuredIndexedList.accesses());
    assertEquals(0, measuredIndexedList.mutations());
    for (int i = 0; i < LENGTH; i++) {
      assertEquals(1, measuredIndexedList.get(i));
    }
  }

}
