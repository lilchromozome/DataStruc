package hw5;

/**
 * Set implemented using plain Java arrays and transpose-sequential-search heuristic.
 *
 * @param <T> Element type.
 */
public class TransposeArraySet<T> extends ArraySet<T> {

  /** Main function.
   * @param args not used in this main function
   */
  public static void main(String[] args) {
    TransposeArraySet<String> transposeArray = new TransposeArraySet<>();
    transposeArray.insert("a");
    transposeArray.insert("b");
    transposeArray.has("a");
    transposeArray.insert("c");
    transposeArray.insert("d");
    transposeArray.insert("b");
    transposeArray.insert("a");
    transposeArray.insert("e");
    transposeArray.remove("b");
    transposeArray.insert("d");
    transposeArray.insert("a");
    transposeArray.insert("e");
    transposeArray.has("Hello World!");
    transposeArray.remove("a");
    transposeArray.has("e");
    transposeArray.remove("c");
  }

  /** transposing heuristic.
   * @param index to swap with its predecessor element
   */
  private void transposeArrayHeuristic(int index) {
    T predecessor = this.data[index - 1];
    this.data[index - 1] = this.data[index];
    this.data[index] = predecessor;
  }

  /** finding function.
   * @param t element that is being searched
   */
  @Override
  protected int find(T t) {
    int foundIndex = super.find(t);
    if (foundIndex > 0) {
      transposeArrayHeuristic(foundIndex);
      return foundIndex - 1;
    }
    return foundIndex;
  }



}
