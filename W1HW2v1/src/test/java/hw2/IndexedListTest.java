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
    @DisplayName("get() returns the default value after IndexedList is instantiated.")
    void testGetAfterConstruction() {
      for (int index = 0; index < indexedList.length(); index++) {
        assertEquals(INITIAL, indexedList.get(index));
      }
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
    // add tests

    @Test
    @DisplayName("get() returns default value for list of size 1")
    void testGetReturnsDefaultValueForListOfSizeOne() {
      SparseIndexedList<Integer> sizeOne = new SparseIndexedList<>(1, INITIAL);
      assertEquals(INITIAL, sizeOne.get(0));
    }


    @Test
    @DisplayName("get() after non-default put() retrieves the non-default value at a  node.")
    void testGetAfterNonDefaultPutRetrievesNonDefaultValueAtNode() {
      indexedList.put(5, 9);
      for (int i = 0; i < LENGTH; i++) {
        if (i == 5) {
          assertEquals(9, indexedList.get(i));
        } else {
          assertEquals(INITIAL, indexedList.get(i));
        }
      }
    }

    @Test
    @DisplayName("length() returns the declared capacity of the sparsed list.")
    void testLengthReturnsDeclaredCapacity() {
      assertEquals(LENGTH, indexedList.length());
    }



    @Test
    @DisplayName("Exception thrown if index is negative during put()")
    void testExceptionThrownForNegativeIndexDuringPut() {
      try {
        indexedList.put(-42, 5);
        fail("Expected IndexException for negative index");
      } catch (IndexException ex) {
        return;
      }
    }

    @Test
    @DisplayName("Exception thrown if index exceeds the maximum allowed during put()")
    void testExceptionThrownForExcessiveIndexDuringPut() {
      try {
        indexedList.put(LENGTH, 5);
        fail("Expected IndexException for excessive index");
      } catch (IndexException ex) {
        return;
      }
    }


    @Test
    @DisplayName("put() replaces non-default value with another value successfully")
    void testPutReplacesNonDefaultValueWithAnotherValueSuccessfully() {
      indexedList.put(5, 789);
      assertEquals(789, indexedList.get(5));
      indexedList.put(5, 321);
      assertEquals(321, indexedList.get(5));
    }

    @Test
    @DisplayName("get() after put() with default value returns the default value as expected")
    void testGetAfterPutReturnsDefaultValueAsExpected() {
      indexedList.put(7, 99);
      assertEquals(99, indexedList.get(7));
      indexedList.put(7, INITIAL);
      for (int i = 0; i < LENGTH; i++) {
        assertEquals(INITIAL, indexedList.get(i));
      }
    }

    @Test
    @DisplayName("put() at the last index in an empty list is successful")
    void testPutAtLastIndexInEmptyListIsSuccessful() {
      indexedList.put(LENGTH - 1, 1000);
      for (int i = 0; i < LENGTH; i++) {
        if (i == LENGTH - 1) {
          assertEquals(1000, indexedList.get(i));
        } else {
          assertEquals(INITIAL, indexedList.get(i));
        }
      }
    }

    @Test
    @DisplayName("put() at the start and end in an empty list is successful")
    void testPutAtStartAndEndInEmptyListIsSuccessful() {
      indexedList.put(0, 111);
      indexedList.put(LENGTH - 1, 222);
      assertEquals(111, indexedList.get(0));
      assertEquals(222, indexedList.get(LENGTH - 1));

      for (int i = 1; i < LENGTH - 1; i++) {
        assertEquals(INITIAL, indexedList.get(i));
      }
    }

    @Test
    @DisplayName("Successful put() at first and last index in an empty list")
    void testSuccessfulPutAtFirstAndLastIndexInEmptyList() {
      indexedList.put(0, 42);
      indexedList.put(LENGTH - 1, 76);
      assertEquals(42, indexedList.get(0));
      assertEquals(76, indexedList.get(LENGTH - 1));

      for (int i = 1; i < LENGTH - 1; i++) {
        assertEquals(INITIAL, indexedList.get(i));
      }
    }

    @Test
    @DisplayName("Constructor throws exception for negative size during instantiation")
    void testConstructorThrowsExceptionForNegativeSize() {
      try {
        IndexedList<Integer> testIndexedList = new SparseIndexedList<>(-42, 3);
        fail("Expected LengthException for negative size");
      } catch (LengthException ex) {
        return;
      }
    }

    @Test
    @DisplayName("Constructor throws exception for size zero during instantiation")
    void testConstructorThrowsExceptionForZeroSize() {
      try {
        IndexedList<Integer> testIndexedList = new SparseIndexedList<>(0, 3);
        fail("Expected LengthException for size zero");
      } catch (LengthException ex) {
        return;
      }
    }

    @Test
    @DisplayName("Iterator operates correctly on an empty node list using for-each loop")
    void testIteratorOperatesOnEmptyNodeList() {
      int count = 0;
      for (int data: indexedList) {
        assertEquals(data, 5);
        count++;
      }
      assertEquals(indexedList.length(), count);
    }

    @Test
    @DisplayName("Iterator returns data accurately after insertion at the start and end of the list using for-each loop")
    void testIteratorReturnsDataAfterInsertionAtStartAndEnd() {
      indexedList.put(0, 15);
      indexedList.put(LENGTH - 1, 200);

      int count = 0;
      for (int data: indexedList) {
        if (count == 0) {
          assertEquals(15, data);
        } else if (count == LENGTH - 1) {
          assertEquals(200, data);
        } else {
          assertEquals(5, data);
        }
        count++;
      }
      assertEquals(indexedList.length(), count);
    }

    @Test
    @DisplayName("Iterator returns data in the correct order in a fully populated list")
    void testIteratorOnFullyPopulatedList() {
      int testVal = 20;
      for (int i = 0; i < indexedList.length(); i++) {
        indexedList.put(i, testVal++);
      }

      int newTestVal = 20;
      int count = 0;
      for (int data: indexedList) {
        assertEquals(newTestVal++, data);
        count++;
      }
      assertEquals(indexedList.length(), count);
    }

    @Test
    @DisplayName("Iterator returns correct values for a list with a single element")
    void testIteratorReturnsValuesInListWithOneElement() {
      indexedList.put(6, 88);
      Iterator<Integer> iter = indexedList.iterator();
      for (int i = 0; i < 10; i++) {
        if (i == 6) {
          assertEquals(iter.next(), 88);
        } else {
          assertEquals(iter.next(), 42);
        }
      }
    }

    @Test
    @DisplayName("Iterator returns values as expected in a list with only two populated indices")
    void testIteratorReturnsValuesInListWithTwoSpreadOutNodes() {
      indexedList.put(0, 77);
      indexedList.put(LENGTH - 1, 19);
      Iterator<Integer> iter = indexedList.iterator();
      for (int i = 0; i < 10; i++) {
        if (i == 0) {
          assertEquals(iter.next(), 77);
        } else if (i == 9) {
          assertEquals(iter.next(), 19);
        } else {
          assertEquals(iter.next(), 42);
        }
      }
    }

    @Test
    @DisplayName("Iterator throws exception when called more than LENGTH times")
    void testIteratorThrowsExceptionWhenCalledMoreThanLengthTimes() {
      Iterator<Integer> iter = indexedList.iterator();
      try {
        for (int i = 0; i < LENGTH + 1; i++) {
          iter.next();
        }
        fail("Expected NoSuchElementException, but none was thrown.");
      } catch (NoSuchElementException ex) {
        return;
      }
    }

    @Test
    @DisplayName("Iterator ensures hasNext() returns true until the end is reached.")
    void testIteratorEnsuresHasNextReturnsTrueUntilEnd() {
      Iterator<Integer> iter = indexedList.iterator();
      int count = 0;
      while (iter.hasNext()) {
        count++;
        iter.next();
      }
      assertEquals(count, LENGTH);
    }

    @Test
    @DisplayName("Iterator ensures hasNext() returns false after reaching the end.")
    void testIteratorEnsuresHasNextReturnsFalseAfterEnd() {
      Iterator<Integer> iter = indexedList.iterator();
      int count = 0;
      while (count < 10) {
        count++;
        iter.next();
      }

      assertFalse(iter.hasNext());
    }

    @Test
    @DisplayName("Iterator ensures next() returns the default value in an empty list.")
    void testIteratorEnsuresNextReturnsDefaultValueInEmptyList() {
      Iterator<Integer> iter = indexedList.iterator();
      for (int i = 0; i < 10; i++) {
        assertEquals(iter.next(), INITIAL);
      }
    }

    @Test
    @DisplayName("put() replaces initial value at the head and then at the following index successfully.")
    void testPutReplacesInitialValueAtHeadAndFollowingIndex() {
      indexedList.put(4, 90);
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
          assertEquals(90, indexedList.get(i));
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
    @DisplayName("put() replaces initial value at the head and then at one position after the head successfully.")
    void testPutReplacesInitialValueAtOneAfterHead() {
      indexedList.put(4, 90);
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
          assertEquals(90, indexedList.get(i));
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


    // testing testing

  }