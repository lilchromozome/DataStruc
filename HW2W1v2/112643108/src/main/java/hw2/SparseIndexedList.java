package hw2;

import exceptions.IndexException;
import exceptions.LengthException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An implementation of an IndexedList designed for cases where
 * only a few positions have distinct values from the initial value.
 *
 * @param <T> Element type.
 */
public class SparseIndexedList<T> implements IndexedList<T> {

  private Node<T> head;
  private int length;
  private T defaultValue;
  /**
   * Constructs a new SparseIndexedList of length size
   * with default value of defaultValue.
   *
   * @param size Length of list, expected: size > 0.
   * @param defaultValue Default value to store in each slot.
   * @throws LengthException if size <= 0.
   */

  public SparseIndexedList(int size, T defaultValue) throws LengthException {
    if (size <= 0) {
      throw new LengthException();
    }
    this.defaultValue = defaultValue;
    length = size;
  }

  @Override
  public int length() {
    return length;
  }

  @Override
  public T get(int index) throws IndexException {
    if (!isValid(index)) {
      throw new IndexException();
    }
    Node<T> target = head;

    while (target != null && target.index < index) {
      target = target.next;
    }

    if (target == null || target.index != index) {
      return defaultValue;
    } else {
      return target.data;
    }
  }

  /**
   * Determines whether a value is between a specified range.
   *
   * @param index value representing position of node.
   * @return true if value within range, false otherwise
   */
  private boolean isValid(int index) {
    return index >= 0 && index < length;
  }

  /**
   * Determines whether a node with the same index already exists within the list.
   *
   * @param index representing position of node that is searched.
   * @return node before the target node if exists, null otherwise
   */
  private Node<T> find(int index) {
    Node<T> beforeTarget = head;
    if (head.index == index) {
      return beforeTarget;
    }
    while (beforeTarget.next != null && beforeTarget.next.index <= index) {
      if (beforeTarget.next.index == index) {
        return beforeTarget;
      }
      beforeTarget = beforeTarget.next;
    }
    return null;
  }

  /**
   * Inserts a new node at the given index.
   *
   * @param index representing a position in this list.
   * @param value to be written at the given index.
   *              Post: this.get(index) == value
   *              Inv: value != defaultValue
   */
  private void insert(int index, T value) {

    Node<T> node = new Node<>(index, value);
    Node<T> before = head;
    while (before.next != null && before.next.index < node.index) {
      before = before.next;
    }
    if (node.index < before.index) { //index is before current head index
      node.next = before;
      head = node;
    } else if (before.next == null) {
      before.next = node;
    } else { //adding between 2 nodes
      node.next = before.next;
      before.next = node;
    }
  }

  /**
   * Deletes an existing node.
   *
   * @param before representing the node before the one that gets deleted.
   */
  private void delete(Node<T> before) {
    if (before == head && before.next == null) {
      head = null;
    } else if (before.next.next != null) { //in middle
      before.next = before.next.next;
    } else { //deleting end
      before.next = null;
    }
  }

  /**
   * Updates the data of an existing node.
   *
   * @param before representing the node before the one that gets modified.
   * @param data to be written at the given index.
   *             Post: this.get(index) == data
   *             Inv: data != defaultValue
   */
  private void update(Node<T> before, T data) {
    if (before == head) {
      before.data = data;
    } else {
      before.next.data = data;
    }
  }

  @Override
  public void put(int index, T value) throws IndexException {
    Node<T> beforeTarget;

    if (!isValid(index)) {
      throw new IndexException();
    }
    if (head == null) { //list is empty
      head = new Node<>(index, value);
    } else {
      beforeTarget = find(index);
      if (beforeTarget == null && value != defaultValue) { //adding new node
        insert(index, value);
      } else if (beforeTarget != null) { // node exists
        if (value != defaultValue) {
          update(beforeTarget, value);
        } else { //deleting node
          delete(beforeTarget);
        }
      }
    }
  }

  @Override
  public Iterator<T> iterator() {
    return new SparseIndexedListIterator();
  }

  private static class Node<T> {
    T data;
    int index;
    Node<T> next;

    Node(int index, T data) {
      this.index = index;
      this.data = data;
    }
  }

  private class SparseIndexedListIterator implements Iterator<T> {
    private Node<T> current;
    private int counter;

    SparseIndexedListIterator() {
      current = head;
      counter = 0;
    }

    @Override
    public boolean hasNext() {
      return counter < length;
    }

    @Override
    public T next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      if (current == null) {
        counter++;
        return defaultValue;
      } else {
        if (current.index == counter) {
          counter++;
          T data = current.data;
          current = current.next;
          return data;
        } else {
          counter++;
          return defaultValue;
        }
      }
    }
  }
}
