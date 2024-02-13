package hw6.bst;

import hw6.OrderedMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * Map implemented as an AVL Tree.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class AvlTreeMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;

  /**
   * Function to determine height of a node.
   * @param node to get height of
   * @return height of the node
   */
  public int height(Node<K, V> node) {
    if (node == null) {
      return -1;
    } else {
      return node.height;
    }
  }

  /**
   * Function to do determine which node height is greater.
   * @param heightOne height of first node to compare
   * @param heightTwo height of second node to compare
   * @return int height of the "higher" node
   */
  public int maximumHeight(int heightOne, int heightTwo) {
    return Math.max(heightOne, heightTwo);
  }

  /**
   * Function to determine the balance factor (bf) of each node.
   * @param node to get balance factor of
   * @return int balance factor
   */
  public int balanceFactor(Node<K, V> node) {
    // bf is calculated by diff of left and right
    return height(node.left) - height(node.right);
  }

  /**
   * Function to do a right rotation of a specified node.
   * @param rootNode to do right rotation with
   */
  private Node<K, V> rightRotate(Node<K, V> rootNode) {
    Node<K,V> child = rootNode.left;
    rootNode.left = child.right;
    child.right = rootNode;
    rootNode.height = maximumHeight(height(rootNode.left), height(rootNode.right)) + 1;
    child.height = maximumHeight(height(child.right), height(child.left)) + 1;
    return child;
  }

  /**
   * Function to do a left rotation of a specified node.
   * @param rootNode to do right rotation with
   */
  private Node<K, V> leftRotate(Node<K, V> rootNode) {
    Node<K,V> child = rootNode.right;
    rootNode.right = child.left;
    child.left = rootNode;
    rootNode.height = maximumHeight(height(rootNode.left), height(rootNode.right)) + 1;
    child.height = maximumHeight(height(child.left), height(child.right)) + 1;
    return child;
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    //insert needs to do rotations after the balance factors are off
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;

  }

  //using implementation from BSTMap for two insert methods as I need a node
  private Node<K, V> insert(Node<K, V> n, K k, V v) {
    if (n == null) {
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
    n.height = maximumHeight(height(n.left), height(n.right)) + 1;
    return rotations(n);
  }

  private Node<K, V> rotations(Node<K, V> node) {
    int balanceFactor = balanceFactor(node);
    //4 cases for rotations
    if (balanceFactor > 1) {
      //determine which subtree (left or right) of child we can use the key as comparison
      if (balanceFactor(node.left) >= 0) {
        //this means the inserted key is less than left node
        //i.e. this is a left subtree, left node problem
        //so right rotation
        return rightRotate(node);
      } else if (balanceFactor(node.left) < 0) {
        //left subtree, right node problem
        //left right rotation
        node.left = leftRotate(node.left);
        return rightRotate(node);
      }
    } else if (balanceFactor < -1) {
      //right node problems
      if (balanceFactor(node.right) <= 0) {
        //right subtree, right node problem
        //left rotation
        return leftRotate(node);
      } else if (balanceFactor(node.right) > 0) {
        //right subtree, left node problem
        //so right left rotation
        node.right = rightRotate(node.right);
        return leftRotate(node);
      }
    }
    return node;
  }


  //copying remove implementation from BSTMap
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
      Node<K,V> removedNode = remove(subtreeRoot);
      if (removedNode != null) {
        return rotations(removedNode);
      }
      return null;
    } else if (cmp > 0) {
      subtreeRoot.left = remove(subtreeRoot.left, toRemove);
      subtreeRoot.height = subtreeRoot.height - 1;
    } else {
      subtreeRoot.right = remove(subtreeRoot.right, toRemove);
      subtreeRoot.height = subtreeRoot.height - 1;
    }

    return rotations(subtreeRoot);
  }

  // Remove given node and return the remaining tree (structural change).
  private Node<K, V> remove(Node<K, V> node) {
    // Easy if the node has 0 or 1 child.
    if (node.right == null) {
      return node.left;
    } else if (node.left == null) {
      return node.right;
    }
    // If it has two children, find the predecessor (max in left subtree),
    Node<K, V> toReplaceWith = greatestLeftNode(node);
    // then copy its data to the given node (value change),
    node.key = toReplaceWith.key;
    node.value = toReplaceWith.value;
    // then remove the predecessor node (structural change).
    node.left = remove(node.left, toReplaceWith);

    return node;
  }

  private Node<K, V> greatestLeftNode(Node<K, V> node) {
    Node<K, V> curr = node.left;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
  }

  //copied from BSTMap
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
   *
   * <p>Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers.</p>
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    //added a height field
    int height;
    K key;
    V value;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null and height defaults to 0
      key = k;
      value = v;
      height = 0;
    }

    @Override
    public String toString() {
      return key + ":" + value;
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
