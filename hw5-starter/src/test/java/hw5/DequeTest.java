package hw5;

import exceptions.EmptyException;
import exceptions.LengthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class DequeTest {

  private Deque<String> deque;

  @BeforeEach
  public void setUp() {
    this.deque = createDeque();
  }

  protected abstract Deque<String> createDeque();

  @Test
  @DisplayName("Deque is empty after construction.")
  public void testConstructor() {
    assertTrue(deque.empty());
    assertEquals(0, deque.length());
  }

  @Test
  @DisplayName("Deque front throws error when empty")
  public void testFrontEmpty() {
    try {
      String front = deque.front();
      fail("Failed to throw exception for front when deque is empty");
    } catch (EmptyException e) {
      return;
    }
  }

  @Test
  @DisplayName("Deque back throws error when empty")
  public void testBackEmpty() {
    try {
      String back = deque.back();
      fail("Failed to throw exception for back when deque is empty");
    } catch (EmptyException e) {
      return;
    } catch (LengthException l) {
      fail("Threw the wrong kind of exception (LengthException)");
    }
  }

  @Test
  @DisplayName("Insert Front of Empty Deque")
  public void insertFrontEmptyDeque() {
    String word = "Hello World!";
    deque.insertFront(word);
    assertEquals(word, deque.front());
  }

  @Test
  @DisplayName("Insert Back of Empty Deque")
  public void insertBackEmptyDeque() {
    String word = "Hello World!";
    deque.insertBack(word);
    assertEquals(word, deque.back());
  }

  @Test
  @DisplayName("Remove Back of Empty Deque throws EmptyException")
  public void removeBackEmptyDeque() {
    try {
      deque.removeBack();
      fail("Failed to throw exception when removing back on empty deque");
    } catch (EmptyException e) {
      return;
    }
  }

  @Test
  @DisplayName("Remove Front of Empty Deque throws EmptyException")
  public void removeFrontEmptyDeque() {
    try {
      deque.removeFront();
      fail("Failed to throw exception when removing front on empty deque");
    } catch (EmptyException e) {
      return;
    }
  }

  @Test
  @DisplayName("Remove Front of one Element Deque")
  public void removeFrontOneElementDeque() {
    String word = "Hello World!";
    deque.insertFront(word);
    assertEquals(word, deque.front());
    deque.removeFront();
    assertTrue(deque.empty());
  }

  @Test
  @DisplayName("One Element Deque is non-empty when inserted from the front")
  public void emptyInsertFrontOneElementDeque() {
    String word = "Hello World!";
    deque.insertFront(word);
    assertFalse(deque.empty());
  }

  @Test
  @DisplayName("One Element Deque is non-empty when inserted from the back")
  public void emptyInsertBackOneElementDeque() {
    String word = "Hello World!";
    deque.insertBack(word);
    assertFalse(deque.empty());
  }

  @Test
  @DisplayName("Length of one Element Deque is one when inserted from the front")
  public void lengthInsertFrontOneElementDeque() {
    String word = "Hello World!";
    deque.insertFront(word);
    assertEquals(1, deque.length());
  }

  @Test
  @DisplayName("Length of multiple Element Deque is not zero when inserted from the front")
  public void lengthInsertFrontMultipleElementDeque() {
    String word = "Hello World!";
    String wordTwo = "Hello World!";
    deque.insertFront(word);
    deque.insertFront(wordTwo);
    assertEquals(2, deque.length());
  }

  @Test
  @DisplayName("Length of multiple Element Deque is not zero when inserted from the back")
  public void lengthInsertBackMultipleElementDeque() {
    String word = "Hello World!";
    String wordTwo = "Hello World!";
    deque.insertBack(word);
    deque.insertBack(wordTwo);
    assertEquals(2, deque.length());
  }

  @Test
  @DisplayName("Length of multiple Element Deque is not zero when inserted from the back")
  public void lengthInsertFrontBackMultipleElementDeque() {
    String word = "Hello World!";
    String wordTwo = "Hello World!";
    deque.insertFront(word);
    deque.insertBack(wordTwo);
    assertEquals(2, deque.length());
  }

  @Test
  @DisplayName("Length of one Element Deque is one when inserted from the back")
  public void lengthInsertBackOneElementDeque() {
    String word = "Hello World!";
    deque.insertBack(word);
    assertEquals(1, deque.length());
  }

  @Test
  @DisplayName("Remove Back of one Element Deque")
  public void removeBackOneElementDeque() {
    String word = "Hello World!";
    deque.insertBack(word);
    assertEquals(word, deque.back());
    deque.removeBack();
    assertTrue(deque.empty());
  }

  @Test
  @DisplayName("Inserting Front Multiple element Deque")
  public void insertFrontMultipleElementsDeque() {
    deque.insertFront("a");
    //a
    assertEquals("a", deque.front());
    deque.insertFront("b");
    //ba
    assertEquals("b", deque.front());
    deque.insertFront("c");
    //cba
    assertEquals("c", deque.front());
  }

  @Test
  @DisplayName("Inserting Back Multiple element Deque")
  public void insertBackMultipleElementsDeque() {
    deque.insertBack("a");
    //a
    assertEquals("a", deque.back());
    deque.insertBack("b");
    //ab
    assertEquals("b", deque.back());
    deque.insertBack("c");
    //abc
    assertEquals("c", deque.back());
  }

  @Test
  @DisplayName("Inserting Front and Back Multiple element Deque")
  public void insertFrontBackMultipleElementsDeque() {
    deque.insertFront("a");
    //a
    assertEquals(deque.front(), "a");
    deque.insertBack("b");
    //ab
    assertEquals("b", deque.back());
    deque.insertFront("c");
    //cab
    assertEquals("c", deque.front());
  }

  @Test
  @DisplayName("Removing Back on a Multiple element Deque")
  public void removeBackMultipleElementsDeque() {

    deque.insertBack("a");
    deque.insertBack("b");
    deque.insertBack("c");
    //abc
    deque.removeBack();
    assertEquals(deque.back(), "b");
    deque.removeBack();
    assertEquals(deque.back(), "a");
    deque.removeBack();
    assertTrue(deque.empty());
  }

  @Test
  @DisplayName("Removing Front on a Multiple element Deque")
  public void removeFrontMultipleElementsDeque() {
    deque.insertFront("a");
    deque.insertFront("b");
    deque.insertFront("c");
    //cba
    deque.removeFront();
    assertEquals(deque.front(), "b");
    deque.removeFront();
    assertEquals(deque.front(), "a");
    deque.removeFront();
    assertTrue(deque.empty());
  }

  @Test
  @DisplayName("Removing Front & Back on a Multiple element Deque")
  public void removeFrontBackMultipleElementsDeque() {
    deque.insertBack("a");
    deque.insertFront("b");
    deque.insertBack("c");
    //bac
    deque.removeFront();
    assertEquals(deque.front(), "a");
    deque.removeBack();
    assertFalse(deque.empty());
    assertEquals(deque.back(), "a");
    deque.removeFront();
    assertTrue(deque.empty());
  }
}
