package hw6.bst;

import hw6.OrderedMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

/**
 * Map implemented as a Treap.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class TreapMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'rand'. ***/
  private static Random rand;
  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;

  private int size;

  /**
   * Make a TreapMap.
   */
  public TreapMap() {
    rand = new Random();
    size = 0;
  }


  /**
   * Make a TreapMap.
   * @param seedValue the user-inputted integer for the random number generator
   */
  public TreapMap(int seedValue) {
    rand = new Random(seedValue);
    size = 0;
  }


  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    //generate a random priority for each node before inserting
    //also  needs to do rotations after the priorities are off
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    this.size++;
  }

  private Node<K, V> insert(Node<K, V> n, K k, V v) {
    if (n == null) {
      //priority is already generated through node constructor
      return new Node<>(k, v);
    }
    int cmp = k.compareTo(n.key);
    if (cmp < 0) {
      n.left = insert(n.left, k, v);
    } else if (cmp > 0) {
      n.right = insert(n.right, k, v);
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }
    return priorityRotations(n);
  }

  //implementing min-heap priorities
  private Node<K, V> priorityRotations(Node<K, V> node) {
    //2 cases for rotations (only left or right)
    if (node.right != null && node.right.priority < node.priority) {
      //do a left rotation
      return leftRotate(node);
    } else if (node.left != null && node.left.priority < node.priority) {
      //do a right rotation
      return rightRotate(node);
    } else {
      return node;
    }
  }

  private Node<K, V> leftRotate(Node<K, V> rootNode) {
    Node<K,V> child = rootNode.right;
    rootNode.right = child.left;
    child.left = rootNode;
    return child;
  }

  private Node<K, V> rightRotate(Node<K, V> rootNode) {
    Node<K,V> child = rootNode.left;
    rootNode.left = child.right;
    child.right = rootNode;
    return child;
  }

  @Override
  public V remove(K k) {
    Node<K, V> node = findForSure(k);
    V value = node.value;
    root = remove(root, node);
    size--;
    return value;
  }

  // Remove node with given key from subtree rooted at given node;
  // Return changed subtree with given key missing.
  private Node<K, V> remove(Node<K, V> subtreeRoot, Node<K, V> toRemove) {
    int cmp = subtreeRoot.key.compareTo(toRemove.key);
    if (cmp == 0) {
      return remove(subtreeRoot);
    } else if (cmp > 0) {
      subtreeRoot.left = remove(subtreeRoot.left, toRemove);
    } else {
      subtreeRoot.right = remove(subtreeRoot.right, toRemove);
    }

    return priorityRotations(subtreeRoot);
  }

  // Remove given node and return the remaining tree (structural change).
  private Node<K, V> remove(Node<K, V> node) {
    // Easy if the node has 0 or 1 child.
    if (node.right == null) {
      //basically right rotation
      return node.left;
    } else if (node.left == null) {
      //basically left rotation
      return node.right;
    }

    // If two children, rotate based on priority and recursively delete
    node.priority = Integer.MAX_VALUE;
    if (node.left.priority > node.right.priority) {
      //left is higher priority than right
      Node<K,V> subTreeNode = leftRotate(node);
      node = remove(subTreeNode, node);
    } else {
      //right priority is higher so do a right rotate
      Node<K,V> subTreeNode = rightRotate(node);
      node = remove(subTreeNode, node);
    }
    return node;
  }

  private Node<K, V> findForSure(K k) {
    Node<K, V> n = find(k);
    if (n == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return n;
  }

  private Node<K, V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    Node<K, V> n = root;
    while (n != null) {
      int cmp = k.compareTo(n.key);
      if (cmp < 0) {
        n = n.left;
      } else if (cmp > 0) {
        n = n.right;
      } else {
        return n;
      }
    }
    return null;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    n.value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    return n.value;
  }

  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return find(k) != null;
  }

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public Iterator<K> iterator() {
    return new InorderIterator();
  }

  /*** Do not change this function's name or modify its code. ***/
  @Override
  public String toString() {
    return BinaryTreePrinter.printBinaryTree(root);
  }


  /**
   * Feel free to add whatever you want to the Node class (e.g. new fields).
   * Just avoid changing any existing names, deleting any existing variables,
   * or modifying the overriding methods.
   * Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers. Since this is
   * a node class for a Treap we also include a priority field.
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int priority;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      priority = generateRandomInteger();
    }

    // Use this function to generate random values
    // to use as node priorities as you insert new
    // nodes into your TreapMap.
    private int generateRandomInteger() {
      // Note: do not change this function!
      return rand.nextInt();
    }

    @Override
    public String toString() {
      return key + ":" + value + ":" + priority;
    }

    @Override
    public BinaryTreeNode getLeftChild() {
      return left;
    }

    @Override
    public BinaryTreeNode getRightChild() {
      return right;
    }
  }

  private class InorderIterator implements Iterator<K> {
    private final Stack<Node<K, V>> stack;

    InorderIterator() {
      stack = new Stack<>();
      pushLeft(root);
    }

    private void pushLeft(Node<K, V> curr) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() {
      Node<K, V> top = stack.pop();
      pushLeft(top.right);
      return top.key;
    }
  }
}
