package hw3.list;

import exceptions.IndexException;

/**
 * An ArrayIndexedList that is able to report the number of
 * accesses and mutations, as well as reset those statistics.
 *
 * @param <T> The type of the array.
 */
public class MeasuredIndexedList<T> extends ArrayIndexedList<T>
    implements Measured<T> {

  private int numAccesses;
  private int numMutations;

  /**
   * Constructor for a MeasuredIndexedList.
   *
   * @param size         The size of the array.
   * @param defaultValue The initial value to set every object to in the array..
   */
  public MeasuredIndexedList(int size, T defaultValue) {
    super(size, defaultValue);
    // Calling reset() here will perform same operations
    numAccesses = 0;
    numMutations = 0;
  }

  /**
   * Returns the length of the list.
   *
   * @return the length of the list.
   */
  @Override
  public int length() {
    return super.length();
  }

  /**
   * Retrieves element from list at the given index and increments accesses.
   *
   * @param index The index of element to be retrieved.
   * @return The element at the given index.
   * @throws IndexException if index is out of bounds.
   */
  @Override
  public T get(int index) throws IndexException {
    T getValue = super.get(index);
    numAccesses++;
    return getValue;
  }

  /**
   * Puts a value in the list at the given index and increments mutations.
   *
   * @param index The index to place the value.
   * @param value The value to be put at the given index.
   * @throws IndexException if index is out of bounds.
   */
  @Override
  public void put(int index, T value) throws IndexException {
    super.put(index, value);
    numMutations++;
  }

  /**
   *  Resets both accesses and mutations to zero.
   */
  @Override
  public void reset() {
    numAccesses = 0;
    numMutations = 0;
  }

  /**
   * Returns the number of accesses made.
   *
   * @return The number of accesses.
   */
  @Override
  public int accesses() {
    return numAccesses;
  }

  /**
   * Returns the number of mutations made.
   *
   * @return The number of mutations.
   */
  @Override
  public int mutations() {
    return numMutations;
  }

  /**
   * Counts the number of occurrences of value in the list.
   *
   * @param value The value to be counted in the list.
   * @return The number of occurrences of the given value in the list.
   */
  @Override
  public int count(T value) {
    // Create a counter to measure how many times the given value is found when iterating through list
    int counter = 0;
    for (int i = 0; i < length(); i++) {
      T currValue = get(i);
      if (currValue.equals(value)) {
        counter++;
      }
    }
    return counter;
  }

}
