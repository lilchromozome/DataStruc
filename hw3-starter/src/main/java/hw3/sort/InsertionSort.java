package hw3.sort;


import hw3.list.IndexedList;

/**
 * The Insertion Sort algorithm, with minimizing swaps optimization.
 *
 * @param <T> Element type.
 */
public final class InsertionSort<T extends Comparable<T>>
    implements SortingAlgorithm<T> {

  // function that returns boolean on whether a is greater than b or not
  private boolean greater(T a, T b) {
    return a.compareTo(b) > 0;
  }

  @Override
  public void sort(IndexedList<T> indexedList) {
    int i = 1;
    while (i < indexedList.length()) {
      T currentData = indexedList.get(i);
      int j = i - 1;
      while (j >= 0 && greater(indexedList.get(j), currentData)) {
        // Does the minimized swapping operation where j and j+1 are valid indices
        indexedList.put(j + 1, indexedList.get(j));
        j--;
      }
      indexedList.put(j + 1, currentData);
      i++;
    }
  }

  @Override
  public String name() {
    return "Insertion Sort";
  }
}
