package hw3.sort;


import hw3.list.IndexedList;

/**
 * The Insertion Sort algorithm, with minimizing swaps optimization.
 *
 * @param <T> Element type.
 */
public final class InsertionSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

  /**
   * Sorts the indexed list given using InsertionSort.
   *
   * @param indexedList The list to sort.
   */
  @Override
  public void sort(IndexedList<T> indexedList) {
    int currIndex = 1;
    while (currIndex < indexedList.length()) {
      T currElement = indexedList.get(currIndex);
      int prevIndex = currIndex - 1;
      // Use Comparable interface's compareTo method, and shift larger elements right
      while (prevIndex >= 0 && (indexedList.get(prevIndex).compareTo(currElement) > 0)) {
        indexedList.put(prevIndex + 1, indexedList.get(prevIndex));
        prevIndex--;
      }
      // Place element in its position then move on to next element
      indexedList.put(prevIndex + 1, currElement);
      currIndex++;
    }
  }

  @Override
  public String name() {
    return "Insertion Sort";
  }
}
