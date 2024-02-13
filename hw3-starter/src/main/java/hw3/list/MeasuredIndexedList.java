package hw3.list;

import exceptions.IndexException;
import java.util.Objects;

/**
 * An ArrayIndexedList that is able to report the number of
 * accesses and mutations, as well as reset those statistics.
 *
 * @param <T> The type of the array.
 */
public class MeasuredIndexedList<T> extends ArrayIndexedList<T>
    implements Measured<T> {
  int accessesCounter;
  int mutationsCounter;

  /**
   * Constructor for a MeasuredIndexedList.
   *
   * @param size         The size of the array.
   * @param defaultValue The initial value to set every object to in the array..
   */
  public MeasuredIndexedList(int size, T defaultValue) {
    super(size, defaultValue);
    accessesCounter = 0;
    mutationsCounter = 0;
  }

  @Override
  public int length() {
    return super.length();
  }

  @Override
  public T get(int index) throws IndexException {
    try {
      T data = super.get(index);
      accessesCounter++;
      return data;
    } catch (IndexException e) {
      throw new IndexException();
      //return null;
    }
  }

  @Override
  public void put(int index, T value) throws IndexException {
    try {
      super.put(index, value);
      mutationsCounter++;
    } catch (IndexException e) {
      throw new IndexException();
      //return;
    }
  }

  @Override
  public void reset() {
    mutationsCounter = 0;
    accessesCounter = 0;
  }

  @Override
  public int accesses() {
    return accessesCounter;
  }

  @Override
  public int mutations() {
    return mutationsCounter;
  }

  @Override
  public int count(T value) {
    int count = 0;
    for (T data : this) {
      //handling null values as well
      if (Objects.equals(data, value)) {
        count++;
      }
      accessesCounter++;
    }
    return count;
  }
}
