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
  private int length;
  private T defaultval;
  private Node<T> head;

  private static class Node<T> {
    T data;
    Node<T> next;
    int index;
  }
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
    length = size;
    head = new Node<T>();
    defaultval = defaultValue;
    head.next = null;
    head.data = defaultValue;
    head.index = 0;
  }

  @Override
  public int length() {
    return length;
  }

  private boolean isValid(int index) {
    return index >= 0 && index < length();
  }

  /**
   * Removes specified index of sparseindexedlist.
   *
   * @param index index to be added to node, expected: any index within bounds.
   * @param value value to be added to node, expected: anything.
   * @param nextNode nextNode that should be pointed to, expected: anything.
   */
  private Node<T> makeNode(int index, T value, Node<T> nextNode) {
    Node<T> newNode = new Node<T>();
    newNode.data = value;
    newNode.index = index;
    newNode.next = nextNode;
    return newNode;
  }

  private Node<T> searchPrev(int index) throws IndexException {
    if (!isValid(index)) {
      throw new IndexException();
    }
    if (head == null) {
      return null;
    }
    Node<T> node = head;
    while (node != null && node.index < (index - 1)) {
      if (node.next != null) {
        node = node.next;
      } else {
        break;
      }
    }
    return node;
  }

  private Node<T> find(int index) throws IndexException {
    Node<T> node = searchPrev(index);
    if (node.index == index) {
      return node;
    } else {
      return null;
    }
  }

  /**
   * Removes specified index of sparseindexedlist.
   *
   * @param index index to be removed, expected: to be within bounds.
   * @throws IndexException if index is not withound bounds..
   */
  public void remove(int index) throws IndexException {
    if (!isValid(index)) {
      throw new IndexException();
    }
    Node<T> node = head;
    while (node != null && node.index < (index - 1)) {
      if (node.next != null && node.next.index != index) {
        node = node.next;
      } else {
        break;
      }
    }
    node.next = node.next.next;
  }

  @Override
  public void put(int index, T value) throws IndexException {
    Node<T> foundNode = find(index);
    if (foundNode == null && head == null) {
      head = new Node<T>();
      if (!(value.equals(defaultval))) {
        head.next = makeNode(index, value, null);
      }
    } else if (foundNode == null && head != null) {
      Node<T> node = searchPrev(index);
      node.next = makeNode(index, value, node.next);
    } else {
      if (!(value.equals(defaultval))) {
        foundNode.data = value;
      } else {
        remove(foundNode.index);
      }
    }
  }

  @Override
  public T get(int index) throws IndexException {
    if (!(isValid(index))) {
      throw new IndexException();
    }
    if (head == null) {
      return null;
    }
    Node<T> currNode = head;
    while (currNode != null && currNode.index < index) {
      currNode = currNode.next;
    }
    if (currNode == null || currNode.index != index) {
      return head.data;
    } else {
      return currNode.data;
    }
  }


  @Override
  public Iterator<T> iterator() {
    return new SparseIndexedListIterator();
  }

  private class SparseIndexedListIterator implements Iterator<T> {
    Node<T> current;

    SparseIndexedListIterator() {
      current = head;
    }

    public boolean hasNext() {
      return current != null && current.next != null;
    }

    @Override
    public T next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      T t = current.data;
      current = current.next;
      return t;
    }
  }
}

