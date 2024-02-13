package hw6;

import hw6.bst.AvlTreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to AVL Tree.
 */
@SuppressWarnings("All")
public class AvlTreeMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() { return new AvlTreeMap<>();
  }


  @Test
  @DisplayName("insert causes no structural changes")
  public void insertNoRotation() {
    map.insert("2", "a");
    map.insert("1", "b");
    map.insert("3", "c");
    String[] expected = new String[]{
            "2:a",
            "1:b 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("insert causes left structural rotation")
  public void insertLeftRotation() {
    map.insert("1", "a");
    map.insert("2", "b");
    map.insert("3", "c");
    String[] expected = new String[]{
        "2:b",
        "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("insert causes right structural rotation")
  public void insertRightRotation() {
    map.insert("7", "a");
    map.insert("5", "b");
    map.insert("3", "c");
    String[] expected = new String[]{
            "5:b",
            "3:c 7:a"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("insert causes right-left structural rotation")
  public void insertRightLeftRotation() {
    map.insert("3", "a");
    map.insert("7", "b");
    map.insert("5", "c");
    String[] expected = new String[]{
            "5:c",
            "3:a 7:b"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("insert causes left-right structural rotation")
  public void insertLeftRightRotation() {
    map.insert("7", "a");
    map.insert("5", "b");
    map.insert("3", "c");
    String[] expected = new String[]{
            "5:b",
            "3:c 7:a"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("remove causes right structural rotation")
  public void removeRightRotation() {
    map.insert("7", "a");
    map.insert("8", "d");
    map.insert("5", "b");
    map.insert("3", "c");
    map.remove("8");
    String[] expected = new String[]{
            "5:b",
            "3:c 7:a"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("remove causes left structural rotation")
  public void removeLeftRotation() {
    map.insert("7", "a");
    map.insert("3", "c");
    map.insert("8", "d");
    map.insert("9", "b");
    map.remove("3");
    String[] expected = new String[]{
            "8:d",
            "7:a 9:b"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("remove causes left-right structural rotation")
  public void removeLeftRightRotation() {
    map.insert("5", "a");
    map.insert("6", "d");
    map.insert("3", "b");
    map.insert("4", "c");
    map.remove("6");
    String[] expected = new String[]{
            "4:c",
            "3:b 5:a"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("remove causes right-left structural rotation")
  public void removeRightLeftRotation() {
    map.insert("3", "a");
    map.insert("1", "d");
    map.insert("7", "b");
    map.insert("5", "c");
    String[] expected = new String[]{
            "3:a",
            "1:d 7:b",
            "null null 5:c null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }


  @Test
  @DisplayName("removing leaf imposes no structural rotation")
  public void removeLeaf() {
    map.insert("5", "a");
    map.insert("3", "b");
    map.insert("7", "c");
    map.remove("7");
    String[] expected = new String[]{
            "5:a",
            "3:b null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("removing one child")
  public void removeOneChild() {
    map.insert("5", "a");
    map.insert("3", "b");
    map.remove("5");
    String[] expected = new String[]{
            "3:b"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("remove a node with one child in a large tree")
  public void removeOneChildLargeTree() {
    map.insert("5", "b");
    map.insert("4", "b");
    map.insert("3", "b");
    map.insert("2", "b");
    map.insert("6", "b");
    map.insert("1", "b");
    map.remove("5");
    String[] expected = new String[]{
            "4:b",
            "2:b 6:b",
            "1:b 3:b null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("remove a node with two children")
  public void removeTwoChildren() {
    map.insert("5", "a");
    map.insert("3", "b");
    map.insert("7", "c");
    map.remove("5");
    String[] expected = new String[]{
            "3:b",
            "null 7:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("remove a node to cause no structural rotations")
  public void removeNoRotation() {
    map.insert("5", "a");
    map.insert("3", "b");
    map.insert("7", "c");
    map.insert("9", "d");
    map.remove("9");
    String[] expected = new String[]{
            "5:a",
            "3:b 7:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("insert causes no rotation in a large tree")
  public void insertNoRotationLargeTree() {
    map.insert("5", "a");
    map.insert("2", "b");
    map.insert("7", "c");
    map.insert("6", "d");
    map.insert("8", "e");

    String[] expected = new String[]{
            "5:a",
            "2:b 7:c",
            "null null 6:d 8:e"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("insert causes left rotation in a large tree")
  public void insertLeftRotationLargeTree() {
    map.insert("5", "a");
    map.insert("3", "b");
    map.insert("7", "c");
    map.insert("6", "d");
    map.insert("8", "e");
    map.insert("9", "f");

    String[] expected = new String[]{
            "7:c",
            "5:a 8:e",
            "3:b 6:d null 9:f"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("insert causes right rotation in a large tree")
  public void insertRightRotationLargeTree() {
    map.insert("5", "a");
    map.insert("3", "b");
    map.insert("6", "c");
    map.insert("2", "d");
    map.insert("4", "e");
    map.insert("1", "f");

    String[] expected = new String[]{
            "3:b",
            "2:d 5:a",
            "1:f null 4:e 6:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("insert causes left and right rotations in a large tree")
  public void insertLeftAndRightRotationsLargeTree() {
    map.insert("5", "a");
    map.insert("3", "b");
    map.insert("1", "f");
    map.insert("2", "d");
    map.insert("9", "d");
    map.insert("6", "c");
    map.insert("7", "d");
    map.insert("4", "e");
    map.insert("8", "d");

    String[] expected = new String[]{
            "3:b",
            "1:f 6:c",
            "null 2:d 5:a 8:d",
            "null null null null 4:e null 7:d 9:d"

    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }


  @Test
  @DisplayName("remove causes left rotation in a large tree")
  public void removeLeftRotationLargeTree() {
    map.insert("3", "c");
    map.insert("2", "b");
    map.insert("5", "e");
    map.insert("1", "a");
    map.insert("4", "d");
    map.insert("6", "f");
    map.insert("7", "g");

    map.remove("1");
    String[] expected = new String[]{
            "5:e",
            "3:c 6:f",
            "2:b 4:d null 7:g"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("remove causes right rotations in a large tree")
  public void removeRightRotationLargeTree() {
    map.insert("5", "e");
    map.insert("3", "c");
    map.insert("6", "f");
    map.insert("2", "b");
    map.insert("4", "d");
    map.insert("7", "g");
    map.insert("1", "a");

    map.remove("7");
    String[] expected = new String[]{
            "3:c",
            "2:b 5:e",
            "1:a null 4:d 6:f"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }
}
