package hw7.hashing;

import hw7.Map;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ChainingHashMap<K, V> implements Map<K, V> {

  private static final double LOAD_FACTOR = 0.75;
  private Node<K,V>[] buckets;
  private int size;
  private int capacity;
  private final int[] primes = {5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421,
    12853, 25717, 51437, 102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977};

  /**
   * Instantiate ChainingHashMap.
   */
  public ChainingHashMap() {
    this.size = 0;
    this.capacity = primes[0];
    this.buckets = (Node<K,V>[]) new Node[this.capacity];
  }

  /**
   * Represents a node in the hash map, has a key-value pair and a reference to next node.
   *
   * @param <K> The type of key.
   * @param <V> The type of value.
   */
  private static class Node<K,V> {
    K key;
    V value;
    Node<K,V> next;

    // Node contains a key and a value for that key
    private Node(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  /**
   * Generates a hash code for a given key and maps it to an index in buckets.
   *
   * @param key The key to hash.
   * @return The index in the buckets array.
   */
  private int hash(K key) {
    // Grab the hashCode and calculate the index
    int hashCode = key.hashCode();
    int index = hashCode % capacity;
    if (index < 0) {
      index *= -1; // Ensures the index is non negative, absolute value
    }
    return index;
  }

  /**
   * Increases the capacity of the hash map when the load factor threshold is exceeded.
   */
  private void grow() {
    if ((double) size / capacity > LOAD_FACTOR) {
      int newCapacity = getNewCapacity(); // Grab new capacity from primes array
      if (newCapacity > capacity) {
        rehash(newCapacity); // Rehash the map with the new capacity if larger
      }
    }
  }

  /**
   * Finds the appropriate prime number to use as the capacity value.
   *
   * @return The new capacity.
   */
  private int getNewCapacity() {
    // Iterate through primes array and find new capacity
    for (int prime : primes) {
      if (prime > capacity) {
        return prime;
      }
    }
    return capacity * 2; // If no suitable prime found, double capacity instead.
  }

  /**
   * Rehashes all the entries in the hash map to new buckets with a new capacity.
   *
   * @param newCapacity The new capacity.
   */
  private void rehash(int newCapacity) {
    // Update capacity, store current buckets and create the new bucket array
    capacity = newCapacity;
    Node<K,V>[] oldBuckets = buckets;
    buckets = (Node<K,V>[]) new Node[capacity];

    // Iterate through old buckets to rehash elements
    for (Node<K,V> head : oldBuckets) {
      Node<K,V> curr = head;
      // Iterate through linked lists of elements in current bucket
      while (curr != null) {
        Node<K,V> nextNode = curr.next;
        int index = hash(curr.key);
        curr.next = buckets[index];
        buckets[index] = curr;
        curr = nextNode;
      }
    }
  }

  /**
   * Inserts a key-value pair into the ChainingHashMap.
   *
   * @param k The key.
   * @param v The value to be associated with k.
   * @throws IllegalArgumentException If the key is null or already present.
   */
  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    // If key is null or already has that key, throw exception
    if (k == null || has(k)) {
      throw new IllegalArgumentException();
    }

    // Increase capacity if the load factor threshold is exceeded
    if ((double) size / capacity > LOAD_FACTOR) {
      grow();
    }

    // Calculate the new index, create the new node and update bucket
    int index = hash(k);
    Node<K,V> head = buckets[index];
    Node<K,V> newNode = new Node<>(k,v);

    newNode.next = head;
    buckets[index] = newNode;
    size++;
  }

  /**
   * Removes the entry for a specified key from the map if it is present.
   *
   * @param k The key which is to be removed.
   * @return The previous value associated with the key.
   * @throws IllegalArgumentException If the key is null or not found.
   */
  @Override
  public V remove(K k) throws IllegalArgumentException {
    // Checks if key is null or doesn't have that key to remove
    if (k == null || !has(k)) {
      throw new IllegalArgumentException();
    }

    // Calculates the index, then removes it
    int index = hash(k);
    return remove(k, index);
  }

  /**
   * Removes the entry for the specified key and index from the ChainingHashMap if present.
   *
   * @param k The key which is to be removed.
   * @param index The index of the bucket where the key should be.
   * @return The value associated with the removed key.
   */
  private V remove(K k, int index) {
    Node<K,V> curr = buckets[index];
    Node<K,V> prev = null;

    // Iterate through the linked list in the bucket to find and remove the specified key
    while (curr != null) {
      if (curr.key.equals(k)) {
        // Found matching key, remove it and update
        if (prev != null) {
          prev.next = curr.next;
        } else {
          buckets[index] = curr.next;
        }
        size--;
        return curr.value; // Return value associated with removed key
      }
      prev = curr;
      curr = curr.next;
    }
    return null;
  }

  /**
   * Updates the value associated to a key in the ChainingHashMap.
   *
   * @param k The key.
   * @param v The new value to be associated with k.
   * @throws IllegalArgumentException If the key is null or not found.
   */
  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    // Check if key is null or doesn't have that key to update
    if (k == null || !has(k)) {
      throw new IllegalArgumentException();
    }

    int index = hash(k);
    Node<K,V> curr = buckets[index];
    while (curr != null) {
      if (curr.key.equals(k)) {
        curr.value = v; // Update value if the key already exists
        return;
      }
      curr = curr.next; // Move to the next node
    }

    // Create a new node with the key-value pair and update buckets
    Node<K,V> newNode = new Node<>(k,v);
    newNode.next = buckets[index];
    buckets[index] = newNode;
  }

  /**
   * Returns value to which the specified key is mapped.
   *
   * @param k The key.
   * @return The value to which the key is mapped.
   * @throws IllegalArgumentException If the key is null.
   */
  @Override
  public V get(K k) throws IllegalArgumentException {
    // Check for key not exist or doesn't have that key
    if (k == null || !has(k)) {
      throw new IllegalArgumentException();
    }

    int index = hash(k);
    Node<K,V> curr = buckets[index];
    while (curr != null) {
      if (curr.key.equals(k)) {
        return curr.value; // Return value with specified key
      }
      curr = curr.next; // Move to the next node
    }
    return null; // Key not found in bucket
  }

  /**
   * Returns true if the map contains a mapping for the specified key.
   *
   * @param k The key.
   * @return true if the map contains a mapping for the key, else false.
   */
  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }

    int index = hash(k);
    Node<K,V> curr = buckets[index];
    while (curr != null) {
      if (curr.key.equals(k)) {
        return true; // Return true if has key and it is found
      }
      curr = curr.next;
    }
    return false; // Key was not found, return false
  }

  /**
   * Returns the size or number of key-value mappings.
   *
   * @return The number of key-value mappings.
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Returns an iterator over the keys in the ChainingHashMap.
   *
   * @return an Iterator over the keys in the ChainingHashMap.
   */
  @Override
  public Iterator<K> iterator() {
    return new ChainingIterator();
  }

  /**
   * Iterator for the ChainingHashMap, allowing traversal of keys in the map.
   */
  private class ChainingIterator implements Iterator<K> {
    private int currIndex;
    private Node<K,V> curr;

    /**
     * Constructs an iterator starting at the beginning of the ChainingHashMap.
     */
    ChainingIterator() {
      this.currIndex = 0;
      this.curr = null;
      findNextBucket();
    }

    /**
     * Finds the next non-null bucket.
     */
    private void findNextBucket() {
      if (size > 0) { // Check if elements in the map
        while (currIndex < buckets.length && buckets[currIndex] == null) {
          // Iterate over buckets until a non-null bucket is found
          currIndex++;
        }
        if (currIndex < buckets.length) {
          curr = buckets[currIndex]; // Set current node to the first node in non-null bucket.
        }
      }
    }

    /**
     * Advances the iterator to the next available node in the map.
     */
    private void moveToNext() {
      if (curr != null) {
        curr = curr.next; // Move to the next node in current bucket
      }
      if (curr == null) {
        // Move to next bucket and find next non-null bucket
        currIndex++;
        findNextBucket();
      }
    }

    /**
     * Returns true if the iteration has more elements.
     *
     * @return True if there are more keys to iterate over.
     */
    @Override
    public boolean hasNext() {
      return curr != null;
    }

    /**
     * Returns the next key in the iteration.
     *
     * @return The next key in the iteration.
     * @throws NoSuchElementException If the iteration has no more elements
     */
    @Override
    public K next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      // Get key from node, move iterator to next node and return retrieved key.
      K key = curr.key;
      moveToNext();
      return key;
    }
  }
}
