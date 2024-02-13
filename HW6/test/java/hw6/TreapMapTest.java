package hw6;

import hw6.bst.TreapMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to Treap.
 */
@SuppressWarnings("All")
public class TreapMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new TreapMap<>(10);
  }

  //priorities for convenience:
  // 1st insert: -1157793070
  // 2nd insert:  1913984760
  // 3rd insert:  1107254586
  // 4th insert:  1773446580
  // 5th insert:  254270492
  // 6th insert:  -1408064384
  // 7th insert:   1048475594
  // 8th insert:   1581279777
  // 9th insert:   -778209333
  // 10th insert:  1532292428

  @Test
  @DisplayName("Insertion causes no structural change in the tree")
  public void insertNoRotation() {
    map.insert("2", "a");
    map.insert("1", "b");
    map.insert("3", "c");
    String[] expected = new String[]{
            "2:a:-1157793070",
            "1:b:1913984760 3:c:1107254586"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Inserting one node causes one left stuctural rotation")
  public void insertLeftRotation() {
    map.insert("1", "a");
    map.insert("2", "b");
    map.insert("3", "c");
    String[] expected = new String[]{
            "1:a:-1157793070",
            "null 3:c:1107254586",
            "null null 2:b:1913984760 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Inserting nodes causes one right structural rotation")
  public void insertRightRotation() {
    map.insert("7", "a");
    map.insert("5", "b");
    map.insert("3", "c");
    String[] expected = new String[]{
            "7:a:-1157793070",
            "3:c:1107254586 null",
            "null 5:b:1913984760 null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Removing multiple nodes causes consecutive left rotations")
  public void insertMultipleLeftRotation() {
    map.insert("1", "a");
    map.insert("2", "b");
    map.insert("3", "c");
    map.insert("4", "d");
    map.insert("5", "e");
    String[] expected = new String[]{
            "1:a:-1157793070",
            "null 5:e:254270492",
            "null null 3:c:1107254586 null",
            "null null null null 2:b:1913984760 4:d:1773446580 null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Inserting multiple nodes casues repeated right only rotations")
  public void insertMultipleRightRotation() {
    map.insert("5", "e");
    map.insert("4", "d");
    map.insert("3", "c");
    map.insert("2", "b");
    map.insert("1", "a");
    String[] expected = new String[]{
            "5:e:-1157793070",
            "1:a:254270492 null",
            "null 3:c:1107254586 null null",
            "null null 2:b:1773446580 4:d:1913984760 null null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Inserting multiple nodes causes left and right structural rotations")
  public void insertMultipleLeftandRightRotations() {
    map.insert("3", "a");
    map.insert("7", "b");
    map.insert("8", "c");
    map.insert("6", "d");
    map.insert("5", "e");
    map.insert("4", "f");
    String[] expected = new String[]{
            "4:f:-1408064384",
            "3:a:-1157793070 5:e:254270492",
            "null null null 8:c:1107254586",
            "null null null null null null 6:d:1773446580 null",
            "null null null null null null null null null null null null null 7:b:1913984760 null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Removing one node with two children imposes left rotation after initial right rotation")
  public void removeSingleRightThenLeftRotationTwoChildren() {
    map.insert("2", "a");
    map.insert("3", "c");
    map.insert("1", "b");
    map.remove("2");
    String[] expected = new String[]{
            "1:b:1107254586",
            "null 3:c:1913984760"

    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Removing multiple node imposes multiple left rotations")
  public void removeMultipleLeftRotations() {
    map.insert("1", "a");
    map.insert("3", "c");
    map.insert("4", "d");
    map.insert("2", "d");
    map.insert("5", "d");
    map.remove("2");
    map.remove("1");
    String[] expected = new String[]{
            "5:d:254270492",
            "4:d:1107254586 null",
            "3:c:1913984760 null null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Removing a leaf imposes no additional structural changes after its removal")
  public void removeLeaf() {
    map.insert("1", "a");
    map.insert("2", "b");
    map.insert("3", "c");
    map.insert("4", "d");
    map.insert("5", "d");
    map.remove("2");
    String[] expected = new String[]{
            "1:a:-1157793070",
            "null 5:d:254270492",
            "null null 3:c:1107254586 null",
            "null null null null null 4:d:1773446580 null null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Removing node with two children imposes a right rotation after an initial left rotation")
  public void removeSingleLeftThenRightRotationTwoChildren() {
    map.insert("6", "b");
    map.insert("3", "c");
    map.insert("8", "a");
    map.remove("6");
    String[] expected = new String[]{
            "8:a:1107254586",
            "3:c:1913984760 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Removing one node with one child imposes right rotation")
  public void removeRightRotationOneChild() { // i think i need to change
    map.insert("3", "a");
    map.insert("5", "a");
    map.insert("9", "a");
    map.insert("7", "a");
    map.remove("7");
    String[] expected = new String[]{
            "3:a:-1157793070",
            "null 9:a:1107254586",
            "null null 5:a:1913984760 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Removing one node with one child imposes single left rotation")
  public void removeLeftRotationOneChild() {
    map.insert("3", "a");
    map.insert("8", "a");
    map.insert("9", "a");
    map.insert("7", "a");
    map.remove("7");
    String[] expected = new String[]{
            "3:a:-1157793070",
            "null 9:a:1107254586",
            "null null 8:a:1913984760 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Removing multiple node imposes multiple right rotations")
  public void removeMultipleRightRotations() {
    map.insert("9", "a");
    map.insert("6", "a");
    map.insert("8", "a");
    map.insert("7", "a");
    map.remove("7");
    map.remove("8");
    String[] expected = new String[]{
            "9:a:-1157793070",
            "6:a:1913984760 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Remove imposes no structural changes on tree after removal")
  public void removeNoRotations() {
    map.insert("2", "a");
    map.insert("3", "b");
    map.insert("4", "c");
    map.insert("5", "d");
    map.insert("6", "d");
    map.remove("3");
    map.remove("5");
    String[] expected = new String[]{
            "2:a:-1157793070",
            "null 6:d:254270492",
            "null null 4:c:1107254586 null"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }


}