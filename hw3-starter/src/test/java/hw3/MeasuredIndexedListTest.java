package hw3;

import exceptions.IndexException;
import hw3.list.MeasuredIndexedList;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
  @DisplayName("MeasuredIndexedList count returns all elements")
  void lengthCountStart() {
    int counter = measuredIndexedList.count(DEFAULT_VALUE);
    assertEquals(LENGTH, counter);
  }

  @Test
  @DisplayName("MeasuredIndexedList reset keeps 0 mutations and accesses at 0")
  void zeroResetNothing() {
    measuredIndexedList.reset();
    assertEquals(0, measuredIndexedList.mutations());
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexedList changes mutations with one put")
  void mutationsOneElement() {
    measuredIndexedList.put(0, 10);
    assertEquals(1, measuredIndexedList.mutations());
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexedList changes mutations with multiple puts")
  void mutationsMultipleElement() {
    measuredIndexedList.put(3, 10);
    measuredIndexedList.put(0, 10);
    // putting the same elements
    measuredIndexedList.put(5, 20);
    measuredIndexedList.put(5, 20);
    measuredIndexedList.put(5, 20);
    assertEquals(5, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("MeasuredIndexedList changes accesses with one get")
  void accessesOneElement() {
    measuredIndexedList.get(0);
    assertEquals(1, measuredIndexedList.accesses());
    assertEquals(0, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("MeasuredIndexedList changes accesses with multiple get")
  void accessesMultipleElement() {
    // get the same element multiple times
    measuredIndexedList.get(0);
    measuredIndexedList.get(0);
    measuredIndexedList.get(0);
    measuredIndexedList.get(1);
    assertEquals(4, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexedList number of accesses doesn't change with invalid read above bounds")
  void accessesDoesNotChangeAboveException() {
    try {
      measuredIndexedList.get(100);
      fail("failed to throw Index Exception for above bounds");
    }
    catch (IndexException e) {
      assertEquals(0, measuredIndexedList.accesses());
    }
    return;
  }

  @Test
  @DisplayName("MeasuredIndexedList number of accesses doesn't change with invalid read below bounds")
  void accessesDoesNotChangeBelowException() {
    try {
      measuredIndexedList.get(-100);
      fail("failed to throw Index Exception for below bounds");
    }
    catch (IndexException e) {
      assertEquals(0, measuredIndexedList.accesses());
    }
    return;
  }

  @Test
  @DisplayName("MeasuredIndexedList number of accesses doesn't change with invalid mutation below bounds")
  void mutationsDoesNotChangeBelowException() {
    try {
      measuredIndexedList.put(-100, 50);
      fail("failed to throw Index Exception for below bounds");
    }
    catch (IndexException e) {
      assertEquals(0, measuredIndexedList.mutations());
    }
    return;
  }

  @Test
  @DisplayName("MeasuredIndexedList number of accesses doesn't change with invalid mutation above bounds")
  void mutationsDoesNotChangeAboveException() {
    try {
      measuredIndexedList.put(100, 50);
      fail("failed to throw Index Exception for above bounds");
    }
    catch (IndexException e) {
      assertEquals(0, measuredIndexedList.mutations());
    }
    return;
  }

  @Test
  @DisplayName("MeasuredIndexedList accesses and mutations reset to 0 after changes")
  void resetAfterChanges() {
    measuredIndexedList.put(10, 50);
    measuredIndexedList.put(0, 50);
    measuredIndexedList.get(0);
    measuredIndexedList.get(2);
    measuredIndexedList.get(10);
    measuredIndexedList.reset();
    assertEquals(0, measuredIndexedList.mutations());
    assertEquals(0, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexedList mutates successfully after reset to 0")
  void mutateAfterReset() {
    measuredIndexedList.reset();
    measuredIndexedList.put(10, 50);
    measuredIndexedList.put(0, 50);
    assertEquals(2, measuredIndexedList.mutations());
  }

  @Test
  @DisplayName("MeasuredIndexedList accesses successfully after reset to 0")
  void accessAfterReset() {
    measuredIndexedList.reset();
    measuredIndexedList.get(0);
    measuredIndexedList.get(2);
    measuredIndexedList.get(10);
    assertEquals(3, measuredIndexedList.accesses());
  }

  @Test
  @DisplayName("MeasuredIndexedList count returns 0 for nonexistent element")
  void countAfterNoMutations() {
    int counter = measuredIndexedList.count(100);
    assertEquals(0, counter);
  }

  @Test
  @DisplayName("MeasuredIndexedList count returns number of element")
  void countAfterMutations() {
    measuredIndexedList.put(0, 100);
    measuredIndexedList.put(1, 100);
    measuredIndexedList.put(3, 50);
    int counterHundred = measuredIndexedList.count(100);
    int counterFifty= measuredIndexedList.count(50);
    assertEquals(2, counterHundred);
    assertEquals(1, counterFifty);
  }

  @Test
  @DisplayName("MeasuredIndexedList iterator does not change the number of accesses and mutations")
  void iteratorNoChangeMutationsAccesses() {
    Iterator<Integer> iterator = measuredIndexedList.iterator();
    for (int i = 0; i < LENGTH; i++) {
      assertEquals(0, measuredIndexedList.mutations());
      assertEquals(0, measuredIndexedList.accesses());
      iterator.next();
    }
  }


}
