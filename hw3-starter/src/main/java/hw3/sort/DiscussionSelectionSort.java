package hw3.sort;




/**
 * The Insertion Sort algorithm, with minimizing swaps optimization.
 *
 * @param <T> Element type.
 */
public final class DiscussionSelectionSort<T extends Comparable<T>> {
  private boolean greater(T a, T b) {
    return a.compareTo(b) > 0;
  }

  /**
   * The Insertion Sort algorithm, with minimizing swaps optimization.
   *
   * @param a  integer array
   */
  public static void selectionSort(int[] a) {
    int max;
    int temp;
    for (int i = 0; i < a.length - 1; i++) {
      max = i;
      for (int j = i + 1; j < a.length; j++) {
        if (a[j] > a[max]) {
          max = j;
        }
      }
      temp = a[i];
      a[i] = a[max];
      a[max] = temp;
    }
  }

  /**
   * The Insertion Sort algorithm, with minimizing swaps optimization.
   *
   * @return name
   */
  public String name() {
    return "Discussion Selection Sort";
  }
}
