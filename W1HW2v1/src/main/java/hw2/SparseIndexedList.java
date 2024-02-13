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
  private final int length;
  private final T defaultValue;
  /**
   * Constructs a new SparseIndexedList of length size
   * with default value of defaultValue.
   *
   * @param size Length of list, expected: `size` > 0.
   *
   * @param defaultValue Default value to store in each slot.
   * @throws LengthException if size <= 0.
   */

  public SparseIndexedList(int size, T defaultValue) throws LengthException {
    if (size <= 0) {
      throw new LengthException();
    }
    this.head = null;
    this.length = size;
    this.defaultValue = defaultValue;
  }

  @Override
  public int length() {
    return length;
  }

  private boolean isValid(int index) {
    return index >= 0 && index < length();
  }

  @Override
  public T get(int index) throws IndexException {
    if (!isValid(index)) {
      throw new IndexException();
    }
    Node<T> targetNode = find(index);

    // Logic to return the appropriate value
    if (targetNode == null) {
      return defaultValue;
    } else {
      return targetNode.value;
    }
  }



  private void placeNode(int index, T value) {
    Node<T> newNode = new Node<>(index, value);
    Node<T> current = head;
    Node<T> previous = null;

    // Traverse list to find the appropriate position for the new node
    while (current != null && current.position < index) {
      previous = current;
      current = current.next;
    }

    // Insert new node at the appropriate position
    if (previous == null) {
      newNode.next = head;
      head = newNode;
    } else {
      newNode.next = previous.next;
      previous.next = newNode;
    }
  }

  private Node<T> findPrevNode(int index) throws IndexException {
    if (index < 0 || index >= length()) {
      throw new LengthException();
    }

    Node<T> prevNode = null;
    Node<T> currentNode = head;

    while (currentNode != null && currentNode.position != index) {
      prevNode = currentNode;
      currentNode = currentNode.next;
    }

    return prevNode;
  }


  private Node<T> find(int index) throws IndexException {
    if (!isValid(index)) {
      throw new IndexException();
    }

    return findNodeRecursively(head, index);
  }

  private Node<T> findNodeRecursively(Node<T> currentNode, int index) {
    if (currentNode == null) {
      return null;
    }

    if (currentNode.position == index) {
      return currentNode;
    }

    return findNodeRecursively(currentNode.next, index);
  }


  private void removeNode(int index) throws IndexException {
    if (!isValid(index)) {
      throw new IndexException();
    }

    Node<T> currentNode = head;
    Node<T> previousNode = null;

    // Traverse the list to find the node at the given index
    while (currentNode != null && currentNode.position != index) {
      previousNode = currentNode;
      currentNode = currentNode.next;
    }

    if (currentNode != null) {
      // Found the target node at the given index
      if (previousNode == null) {
        // The target node is the head
        head = head.next;
      } else {
        // Update the next reference of the previous node to skip the target node
        previousNode.next = currentNode.next;
      }
    }
  }


  @Override
  public void put(int index, T value) throws IndexException {
    if (!isValid(index)) {
      throw new IndexException();
    }

    if (value.equals(defaultValue)) {
      removeNode(index);
    } else {
      Node<T> nodeAtIndex = find(index);

      if (nodeAtIndex == null) {
        placeNode(index, value);
      } else {
        nodeAtIndex.assignValue(value);
      }
    }
  }


  @Override
  public Iterator<T> iterator() {
    return new SparseIndexedListIterator();
  }


  private class SparseIndexedListIterator implements Iterator<T> {
    private int currentIndex;
    private Node<T> currentNode;

    SparseIndexedListIterator() {
      currentIndex = 0;
      currentNode = head;
    }

    @Override
    public boolean hasNext() {
      return currentIndex < length();
    }

    @Override
    public T next() throws NoSuchElementException {
      if (hasNext()) {
        return get(currentIndex++);
      } else {
        throw new NoSuchElementException();
      }
    }
  }

  // Node class to store position and val pairs

  private static class Node<T> {
    private final int position;
    private T value;
    private Node<T> next;

    Node(int position, T value) {
      this.value = value;
      this.position = position;
      this.next = null;
    }

    public void assignValue(T data) {
      this.value = value;
    }
  }
}
