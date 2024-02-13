package hw7.hashing;

import hw7.Map;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;


public class ChainingHashMap<K, V> implements Map<K, V> {

  private static final double LOAD_FACTOR = 0.75;
  private int currSize;
  private int capacity;

  private static class Node<K, V> {
    K key;
    V value;

    private Node(K k, V v) {
      key = k;
      value = v;
    }
  }

  private LinkedList<Node<K,V>>[] chainHashTable;

  //prime numbers to rehash with
  private final int[] primeNumbers = {5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853,
    25717, 51437, 102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977};

  private int primeArrayCounter;


  /** Default constructor.
   * No arguments
   */
  public ChainingHashMap() {
    primeArrayCounter = 0;
    capacity = primeNumbers[primeArrayCounter];
    chainHashTable = (LinkedList<Node<K,V>>[]) new LinkedList[capacity];
    currSize = 0;
    //make a non-null list so that can alter the list
    for (int i = 0; i < capacity; i++) {
      chainHashTable[i] = new LinkedList<>();
    }
  }

  /** Non-default constructor to be used when rehashing.
   * @param newCapacity the new capacity to change the table to
   */
  public ChainingHashMap(int newCapacity) {
    capacity = newCapacity;
    chainHashTable = (LinkedList<Node<K,V>>[]) new LinkedList[newCapacity];
    currSize = 0;
    for (int i = 0; i < capacity; i++) {
      chainHashTable[i] = new LinkedList<>();
    }
  }


  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    //throw an exception if node is already found
    if (has(k) || Objects.equals(k, null)) {
      throw new IllegalArgumentException();
    }
    //key value isn't present so insert a new value into the table
    int index = calcHash(k);
    chainHashTable[index].add(new Node<>(k,v));
    currSize++;
    if (currSize >= LOAD_FACTOR * capacity) { // rehash operation after certain proportion is filled
      rehashTable();
    }
  }

  /** Rehashes table to increase capacity.
   */
  private void rehashTable() {
    //increase the capacity by using next available prime number
    int newCapacity = primeNumbers[++primeArrayCounter];
    ChainingHashMap<K, V> rehashedTable = new ChainingHashMap<>(newCapacity);
    //iterate through the linked lists to copy over into new table
    for (LinkedList<Node<K,V>> linkedList : chainHashTable) {
      for (Node<K,V> node : linkedList) {
        rehashedTable.insert(node.key, node.value);
      }
    }
    capacity = newCapacity;
    chainHashTable = rehashedTable.chainHashTable;
  }


  /** Find the node with the given key.
   * @param key the key to search for
   * @return Node the node that was found for the given key
   */
  private Node<K,V> find(K key) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    //go to that index in the array (find in that linked list
    if (currSize > 0) {
      for (Node<K, V> currNode : chainHashTable[calcHash(key)]) {
        if (Objects.equals(currNode.key, key)) {
          return currNode;
        }
      }
    }
    return null;
  }

  /** Calculates Index of key in array.
   * @param key to get enter into hash function
   * @return the index of the array where data will be stored
   */
  private int calcHash(K key) {
    int index = key.hashCode() % capacity;
    if (index < 0) {
      index *= -1;
    }
    return index;
  }

  @Override
  public V remove(K k) throws IllegalArgumentException {
    //throw exception if not valid type or doesn't exist
    if (!has(k)) {
      throw new IllegalArgumentException();
    }
    //removing the node
    int removeIndex = calcHash(k);
    Node<K, V> removeNode = find(k);
    if (removeNode != null) {
      V removedKey = removeNode.value;
      chainHashTable[removeIndex].remove(removeNode);
      currSize--;
      return removedKey;
    }
    return null;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    //change the value of the node
    if (!has(k)) {
      throw new IllegalArgumentException();
    }
    Node<K,V> foundNode = find(k);
    if (foundNode != null) {
      foundNode.value = v;
    }
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    if (!has(k)) {
      throw new IllegalArgumentException();
    }
    Node<K,V> foundNode = find(k);
    if (foundNode != null) {
      return foundNode.value;
    }
    return null;
  }

  @Override
  public boolean has(K k) {
    if (Objects.equals(k, null)) {
      return false;
    }
    return !Objects.equals(this.find(k), null);
  }

  @Override
  public int size() {
    return currSize;
  }

  @Override
  public Iterator<K> iterator() {
    return new ChainingIterator();
  }

  private class ChainingIterator implements Iterator<K> {
    private LinkedList<Node<K, V>> currList; // current linked list to search through
    private int counter; // counter of iterated elements
    private int tableIndex;
    private int linkedListIndex;

    private ChainingIterator() {
      tableIndex = 0;
      linkedListIndex = 0;
      currList = chainHashTable[tableIndex];
      counter = 0;
    }

    @Override
    public boolean hasNext() {
      return counter < currSize;
    }

    /** Goes to the nextPosition for the iterator.
     */
    private void nextPosition() {
      while (linkedListIndex >= currList.size()) {
        //the current linked list is iterated over and time to move to the next one
        linkedListIndex = 0;
        currList = chainHashTable[++tableIndex];

      }
    }

    @Override
    public K next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      nextPosition();
      Node<K, V> node = currList.pop();
      currList.push(node);
      linkedListIndex++;
      counter++;
      return node.key;

    }
  }
}
