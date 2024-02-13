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
  private final T defaultValue;
  private final int length;
  private Node<T> head;

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

    this.length = size;
    this.defaultValue = defaultValue;
    this.head = null;
  }

  /**
   * Length getter.
   * @return the length of the list.
   */
  @Override
  public int length() {
    return length;
  }

  /**
   * A method to retrieve the data value of a node at a given index.
   * @param index representing a position in this list.
   * @return the data value of the Node.
   * @throws IndexException when index < 0 or index >= length
   */
  @Override
  public T get(int index) throws IndexException {
    if (index < 0 || index >= length()) {
      throw new IndexException();
    }
    Node<T> targetNode = find(index);
    if (targetNode == null) {
      return this.defaultValue;
    } else {
      return targetNode.data;
    }
  }


  /**
   * A method that handles inserting a new node.
   * Pre: head is not null
   * @param index the index to insert the node at.
   *              Pre: index >= 0 and index < length.
   * @param value the non-default value.
   * @throws IndexException if index < 0 or index >= length
   */
  private void handleNodeInsertion(int index, T value) {
    Node<T> newNode = new Node<>(index, value);
    if (index < head.index) {
      newNode.next = head;
      head = newNode;
    } else {
      // If head is the only node that exists, then we just make this node its next
      sortNode(newNode, index);
    }
  }

  /**
   * Helper method to sort a node based on its index.
   * @param newNode the node to insert.
   * @param index the node to insert's index.
   *              Pre: index > the index at the head, and head is not null.
   */
  private void sortNode(Node<T> newNode, int index) {
    if (head.next == null) {
      head.next = newNode;
    } else {
      Node<T> curr = head;
      Node<T> prev = head; // Keep track of previous node in case newNode should be in the middle of the list.
      while (curr.index < index && curr.next != null) {
        prev = curr;
        curr = curr.next;
      }
      if (curr.index < index) { // If newNode's index is greater, simply append it to the end.
        curr.next = newNode;
      } else { // Else we place it in between the prev and curr nodes.
        prev.next = newNode;
        newNode.next = curr;
      }
    }
  }

  /**
   * A method to retrieve the node prior to the target node in the list.
   * @param index the index of the target node.
   * @return the node who has the target node as its next node.
   * @throws IndexException if index < 0 or index >= length.
   */
  private Node<T> getNodeBeforeTarget(int index) throws IndexException {
    if (index < 0 || index >= length()) {
      throw new LengthException();
    }

    Node<T> curr = head;
    if (curr == null) {
      return null;
    }

    if (curr.index == index) {
      return curr;
    }

    while (curr.next != null) {
      if (curr.next.index == index) {
        return curr;
      }
      curr = curr.next;
    }
    return null;
  }

  /**
   * A method to find the node at a given index.
   * @param index the index of the node to find.
   * @return the node at the given index, or null if none is found.
   * @throws IndexException if index < 0 or index >= length
   */
  private Node<T> find(int index) throws IndexException {
    if (index < 0 || index >= length()) {
      throw new IndexException();
    }
    Node<T> curr = head;
    while (curr != null) {
      if (curr.index == index) {
        return curr;
      }
      curr = curr.next;
    }
    return null;
  }

  /**
   * A helper method to handle inserting a node with a default value,
   * effectively "deleting" the node.
   * @param index the index of the node to delete.
   * @throws IndexException if index < 0 or index >= length
   */
  private void delete(int index) throws IndexException {
    if (index < 0 || index >= length()) {
      throw new IndexException();
    }
    Node<T> targetNode = find(index);
    if (targetNode != null) {
      if (targetNode == head) {
        head = head.next;
        return;
      }
      Node<T> beforeTargetNode = getNodeBeforeTarget(index);
      if (beforeTargetNode == head) {
        head.next = head.next.next;
      } else {
        beforeTargetNode.next = targetNode.next;
      }
    }
  }

  /**
   * A method to place a node at a given position.
   * @param index representing a position in this list.
   * @param value to be written at the given index.
   *              Post: this.get(index) == value
   * @throws IndexException if index < 0 or index >= length.
   */
  @Override
  public void put(int index, T value) throws IndexException {
    if (index < 0 || index >= length()) {
      throw new IndexException();
    }

    if (value != defaultValue) {
      if (head == null) {
        head = new Node<T>(index, value);
        return;
      }
      // Head is not null here
      // If a node at this index does not exist
      if (find(index) == null) {
        handleNodeInsertion(index, value);
      } else {
        find(index).setData(value);
      }
    } else {
      delete(index);
    }
  }

  /**
   * Iterator override.
   * @return the iterator for the list.
   */
  @Override
  public Iterator<T> iterator() {
    return new SparseIndexedListIterator();
  }

  /**
   * The nested iterator class.
   */
  private class SparseIndexedListIterator implements Iterator<T> {

    private int counter;

    SparseIndexedListIterator() {
      counter = 0;
    }

    @Override
    public boolean hasNext() {
      return counter < length();
    }

    @Override
    public T next() throws NoSuchElementException {
      if (hasNext()) {
        return get(counter++);
      } else {
        throw new NoSuchElementException();
      }
    }
  }

  /**
   * The nested static Node class.
   * @param <T> the variable type of the data the Node will hold.
   */
  private static class Node<T> {
    private final int index;
    private T data;
    private Node<T> next;

    Node(int index, T data) {
      this.data = data;
      this.index = index;
      this.next = null;
    }

    /**
     * Setter for the data field.
     * @param data the data to overwrite the instance's current data with.
     */
    public void setData(T data) {
      this.data = data;
    }
  }
}
