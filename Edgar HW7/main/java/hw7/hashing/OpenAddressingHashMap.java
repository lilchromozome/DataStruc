package hw7.hashing;

import hw7.Map;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OpenAddressingHashMap<K, V> implements Map<K, V> {

  private static final double LOAD_FACTOR = 0.75;
  private Entry<K, V>[] map;
  private int size;
  private int capacity;
  private final int[] primes = {5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421,
      12853, 25717, 51437, 102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977};

  /**
   * Instantiate OpenAddressingHashMap.
   */
  public OpenAddressingHashMap() {
    this.size = 0;
    this.capacity = primes[0];
    this.map = (Entry<K, V>[]) new Entry[this.capacity];
  }

  /**
   * Represents an entry in the hash map, has a key-value pair and a boolean indicating tombstone.
   *
   * @param <K> The type of key.
   * @param <V> The type of value.
   */
  private static class Entry<K, V> {
    K key;
    V value;
    boolean tombstone;

    // Entry has a flag indicating if an entry is a tombstone
    private Entry(K key, V value) {
      this.key = key;
      this.value = value;
      this.tombstone = false;
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
    return capacity * 2; // If no suitable prime found, double capacity instead
  }

  /**
   * Rehashes all the entries in the hash map to new buckets with a new capacity.
   *
   * @param newCapacity The new capacity.
   */
  private void rehash(int newCapacity) {
    // Update capacities and store current entries, reset size
    capacity = newCapacity;
    Entry<K, V>[] oldMap = map;
    map = (Entry<K, V>[]) new Entry[capacity];
    size = 0;

    // Iterate through old entries, rehash, and insert them into new map using linear probing
    for (Entry<K, V> entry : oldMap) {
      if (entry != null && !entry.tombstone) {
        int index = hash(entry.key);
        while (map[index] != null) {
          index = (index + 1) % capacity; // Linear probing method
        }
        map[index] = new Entry<>(entry.key, entry.value); // Insert entry into new map
        size++; // Don't forget to increase size as it varies with OpenAddressingHashMap
      }
    }
  }

  /**
   * Inserts a key-value pair into the OpenAddressingHashMap.
   *
   * @param k The key.
   * @param v The value to be associated with k.
   * @throws IllegalArgumentException If the key is null or already present.
   */
  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    // Check for null key or if it already has the key
    if (k == null || has(k)) {
      throw new IllegalArgumentException();
    }

    // Increase capacity if the load factor threshold is exceeded
    if ((double) size / capacity > LOAD_FACTOR) {
      grow();
    }

    // Find index and next available slot with linear probing
    int index = hash(k);
    while (map[index] != null && !map[index].tombstone) {
      index = (index + 1) % capacity;
    }

    // Increase size and insert key-value pair
    map[index] = new Entry<>(k, v);
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
   * Removes the entry for the specified key and index from the OpenAddressingHashMap if present.
   *
   * @param k The key which is to be removed.
   * @param index The index of the key in the map.
   * @return The value associated with the removed key.
   */
  private V remove(K k, int index) {
    int count = 0;
    while (map[index] != null && count < capacity) {
      if (!map[index].tombstone && map[index].key.equals(k)) {
        map[index].tombstone = true; // Mark entry as tombstone and decrease size
        size--;
        return map[index].value; // Return value associated with removed key
      }
      index = (index + 1) % capacity; // Move to next slot with linear probing
      count++;
    }
    return null; // Key not found in specified slots
  }

  /**
   * Updates the value associated to a key in the OpenAddressingHashMap.
   *
   * @param k The key which is to be set.
   * @param v The value to be associated with k.
   * @throws IllegalArgumentException If the key is null or not found in the map.
   */
  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    // Check for null key or doesn't already have key
    if (k == null || !has(k)) {
      throw new IllegalArgumentException();
    }

    // Find index and search for key and update value if not found and not tombstone
    int index = hash(k);
    while (map[index] != null) {
      if (map[index].key.equals(k) && !map[index].tombstone) {
        map[index].value = v; // Update value associated with key
        return;
      }
      index = (index + 1) % capacity;
    }
  }

  /**
   * Retrieves the value associated with a specific key in the map.
   *
   * @param k The key whose value is to be returned.
   * @return The value to which the key is mapped.
   * @throws IllegalArgumentException If the key is null or not found in the map.
   */
  @Override
  public V get(K k) throws IllegalArgumentException {
    // Check for null key or doesn't already have key
    if (k == null || !has(k)) {
      throw new IllegalArgumentException();
    }

    // Find index and search for key and update value if not found and not tombstone
    int index = hash(k);
    int count = 0;
    while (count < capacity && map[index] != null) {
      if (!map[index].tombstone && map[index].key.equals(k)) {
        return map[index].value; // Grabs value as long as it isn't tombstone and key equals
      }
      index = (index + 1) % capacity; // Move to next slot with linear probing and increase count
      count++;
    }
    return null; // Key not found in specified slots
  }

  /**
   * Checks if a specific key is in the map.
   *
   * @param k The key whose presence is checked.
   * @return True if the map contains an entry for the key, else false.
   */
  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }

    int index = hash(k);
    int count = 0;
    while (map[index] != null && count < capacity) {
      if (!map[index].tombstone && map[index].key.equals(k)) {
        return true; // Return true if key is found and it isn't tombstone
      }
      index = (index + 1) % capacity; // Linear probing search
      count++;
    }
    return false; // Return false if not found after searching linearly
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
   * Returns an iterator over the keys in the OpenAddressingHashMap.
   *
   * @return an Iterator over the keys in the OpenAddressingHashMap.
   */
  @Override
  public Iterator<K> iterator() {
    return new OpenAddressingIterator();
  }

  /**
   * Iterator for the OpenAddressingHashMap, allowing traversal of keys in the map.
   */
  private class OpenAddressingIterator implements Iterator<K> {
    private int currIndex;

    /**
     * Constructs an iterator starting at the beginning of the OpenAddressingHashMap.
     */
    OpenAddressingIterator() {
      this.currIndex = 0;
      moveToNext();
    }

    /**
     * Advances the iterator to the next available node in the OpenAddressingHashMap.
     */
    private void moveToNext() {
      while (currIndex < capacity && (map[currIndex] == null || map[currIndex].tombstone)) {
        currIndex++; // Move to the next non-null and non-tombstone slot.
      }
    }

    /**
     * Returns true if the iteration has more elements.
     *
     * @return True if the iteration has more elements.
     */
    @Override
    public boolean hasNext() {
      return currIndex < capacity;
    }

    /**
     * Returns the next key in the iteration.
     *
     * @return The next key in the iteration.
     * @throws NoSuchElementException If iteration has no more elements.
     */
    @Override
    public K next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      // Get key from current slot, move to next non-null and non-tombstone slot and return key
      K key = map[currIndex].key;
      currIndex++;
      moveToNext();
      return key;
    }

  }
}
