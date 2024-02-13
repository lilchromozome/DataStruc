package hw7.hashing;

import hw7.Map;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class OpenAddressingHashMap<K, V> implements Map<K, V> {

  private static final double LOAD_FACTOR = 0.5;
  private int currSize;

  private int capacity;

  private static class Node<K, V> {
    private K key;
    private boolean tombstone;
    private V value;

    private Node(K k, V v) {
      key = k;
      value = v;
      tombstone = false;
    }
  }

  private Node<K,V>[] openHashTable;
  private final int[] primeNumbers = {5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853,
    25717, 51437, 102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977};

  private int primeCounter;



  /** Default constructor of OpenAddressingHashMap.
   * Initializes the object with no user inputs
   */
  public OpenAddressingHashMap() {
    primeCounter = 0;
    capacity = primeNumbers[primeCounter];
    openHashTable = (Node<K,V>[]) new Node[capacity];
    currSize = 0;
  }

  /** Non-default constructor to use when rehashing.
   * @param newCapacity to increase the table size to
   */
  public OpenAddressingHashMap(int newCapacity) {
    capacity = newCapacity;
    openHashTable = (Node<K,V>[]) new Node[capacity];
    currSize = 0;
  }

  /** Returns index to insert into following linearProbing strategy.
   * @param oldIndex the index identified through simple hashing
   * @return the new Index to insert into
   */
  private int linearProbing(int oldIndex) {
    int linearProbeCounter = oldIndex;
    while (!Objects.equals(openHashTable[linearProbeCounter], null)) {
      linearProbeCounter++;
      linearProbeCounter %= capacity;
    }
    return linearProbeCounter;
  }
  int[] test = {1,2,4};
  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (has(k) || Objects.equals(null, k)) {
      throw new IllegalArgumentException();
    }
    //key value isn't present so insert a new value into the table
    int index = calcHash(k);
    int newIndex = linearProbing(index);
    //now found the empty spot in the list
    openHashTable[newIndex] = new Node<>(k, v);
    currSize++;
    // rehash operation after certain proportion is filled
    if (currSize >= LOAD_FACTOR * capacity) {
      rehashTable();
    }
  }

  /** Rehashes table to increase capacity.
   */
  private void rehashTable() {
    int newCapacity = primeNumbers[++primeCounter];
    OpenAddressingHashMap<K, V> rehashedTable = new OpenAddressingHashMap<>(newCapacity);
    for (Node<K,V> node : openHashTable) {
      if (!Objects.equals(null, node)) {
        //tombstones are reset as well
        rehashedTable.insert(node.key, node.value);
      }
    }
    capacity = newCapacity;
    openHashTable = rehashedTable.openHashTable;
  }

  /** Find the node with the given key.
   * @param key the key to search for
   * @return Node the node that was found for the given key
   */
  private Node<K, V> find(K key) throws IllegalArgumentException {
    if (key == null) {
      throw new IllegalArgumentException();
    }
    int index = calcHash(key);
    if (currSize > 0) {
      for (int i = 0; i < capacity; ++i) {
        if (openHashTable[index] == null) {
          return null;
        }
        if (Objects.equals(key, openHashTable[index].key)) {
          return openHashTable[index];
        }
        index++;
        index %= capacity;
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
    if (!has(k)) {
      throw new IllegalArgumentException();
    }
    Node<K, V> removedNode = find(k);
    if (removedNode != null) {
      removedNode.tombstone = true;
      removedNode.key = null;
      V removedKey = removedNode.value;
      removedNode.value = null;
      currSize--;
      return removedKey;
    }
    return null;

  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
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
    return new OpenAddressingIterator();
  }

  private class OpenAddressingIterator implements Iterator<K> {
    private int nextIndex;
    private int counter;

    OpenAddressingIterator() {
      counter = 0;
      nextIndex = 0;
    }

    @Override
    public boolean hasNext() {
      return counter < currSize;
    }

    @Override
    public K next() throws NoSuchElementException {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      Node<K, V> node = openHashTable[nextIndex];
      while (node == null || node.tombstone) {
        node = openHashTable[++nextIndex]; //go to next
      }
      nextIndex++;
      counter++;
      return node.key;
    }
  }
}
