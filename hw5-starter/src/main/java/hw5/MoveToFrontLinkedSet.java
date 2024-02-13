package hw5;

import java.util.Objects;

/**
 * Set implemented using a doubly linked list and move-to-front heuristic.
 *
 * @param <T> Element type.
 */
public class MoveToFrontLinkedSet<T> extends LinkedSet<T> {

  /** Main function.
   * @param args not used in this main function
   */
  public static void main(String[] args) {
    MoveToFrontLinkedSet<String> moveFrontLinkedSet = new MoveToFrontLinkedSet<>();
    moveFrontLinkedSet.insert("a");
    moveFrontLinkedSet.insert("b");
    moveFrontLinkedSet.has("a");
    moveFrontLinkedSet.insert("c");
    moveFrontLinkedSet.insert("d");
    moveFrontLinkedSet.insert("b");
    moveFrontLinkedSet.insert("a");
    moveFrontLinkedSet.insert("e");
    moveFrontLinkedSet.remove("b");
    moveFrontLinkedSet.insert("d");
    moveFrontLinkedSet.insert("d");
    moveFrontLinkedSet.has("Hello World!");
    moveFrontLinkedSet.remove("a");
    moveFrontLinkedSet.has("e");
    moveFrontLinkedSet.remove("c");
  }

  /** moveToFront heuristic.
   * @param foundNode to move to front of the linked list
   */
  private void moveFrontHeuristic(Node<T> foundNode) {
    //list has element found so can't be empty (if 1 element, then keep as is
    // if multiple then need to shift over and keep as head
    foundNode.next = this.head;
    this.head.prev = foundNode;
    foundNode.prev = null;
    this.head = foundNode;
  }

  /** finding function.
   * @param t element that is being searched
   */
  @Override
  protected Node<T> find(T t) {
    Node<T> searchNode = super.find(t);
    if (!Objects.equals(searchNode, null)) {
      if (numElements > 1) {
        super.remove(searchNode);
        moveFrontHeuristic(searchNode);
      } else {
        return this.head;
      }
    }
    return searchNode;
  }

}
