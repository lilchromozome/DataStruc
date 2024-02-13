//package hw2;
//
//import exceptions.IndexException;
//import exceptions.LengthException;
//import java.util.Iterator;
//import java.util.NoSuchElementException;
//
//
///**
// * An implementation of an IndexedList designed for cases where
// * only a few positions have distinct values from the initial value.
// *
// * @param <T> Element type.
// */
//public class SparseIndexedList<T> implements IndexedList<T> {
//
//    private Node<T> head;
//    private int length;
//    private T defaultValue;
//    /**
//     * Constructs a new SparseIndexedList of length size
//     * with default value of defaultValue.
//     *
//     * @param size Length of list, expected: size > 0.
//     * @param defaultValue Default value to store in each slot.
//     * @throws LengthException if size <= 0.
//     */
//
//    public SparseIndexedList(int size, T defaultValue) throws LengthException {
//        if (size <= 0) {
//            throw new LengthException();
//        }
//        this.defaultValue = defaultValue;
//        length = size;
//    }
//
//    @Override
//    public int length() {
//        return length;
//    }
//
//    @Override
//    public T get(int index) throws IndexException {
//        if (index < 0 || index >= length) {
//            throw new IndexException();
//        }
//
//        Node<T> target = head;
//
//        while (target != null && target.index < index) {
//            target = target.next;
//        }
//
//        if (target == null || target.index != index) {
//            return defaultValue;
//        } else {
//            return target.data;
//        }
//    }
//
//    private void putEmpty(Node<T> n) {
//        if (n.data != defaultValue) {
//            head = n;
//        }
//    }
//
//    @Override
//    public void put(int index, T value) throws IndexException {
//
//        if (index < 0 || index >= length) {
//            throw new IndexException();
//        }
//        Node<T> node = new Node<>(index, value);
//        Node<T> target = head;
//
//        //if list empty
//        if (head == null) {
//            putEmpty(node);
//        } else {
//            while (target.next != null && target.next.index < index) {
//                target = target.next;
//            }
//
//            //if nothing after
//            if (target.next == null) {
//                //if head only node
//                if (target == head) {
//                    if (value != defaultValue) {
//                        //prepend
//                        if (index < target.index) {
//                            node.next = target;
//                            head = node;
//                        } else if (target.index == index) {
//                            target.data = value;
//                        } else { //target.index > index
//                            target.next = node;
//                        }
//                    } else { //value is default
//                        if (target.index == index) {
//                            head = null;
//                        }
//                    }
//                } else { //when target not head so have to add after
//                    target.next = node;
//                }
//
//            } else { //node exists after target
//                if (target.next.index != index) {
//                    //value isnt default
//                    if (value != defaultValue) {
//                        node.next = target.next;
//                        target.next = node;
//                    }
//                } else { //node already exists
//                    if (value != defaultValue) {
//                        target.next.data = value;
//                    } else { //value is default
//                        //if in middle of 2 node
//                        if (target.next.next != null) {
//                            target.next = target.next.next;
//                        } else { //if node is last and want to delete
//                            target.next = null;
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public Iterator<T> iterator() {
//        return new SparseIndexedListIterator();
//    }
//
//    private static class Node<T> {
//        T data;
//        int index;
//        Node<T> next;
//
//        Node(int index, T data) {
//            this.index = index;
//            this.data = data;
//        }
//    }
//
//    private class SparseIndexedListIterator implements Iterator<T> {
//        private Node<T> current;
//        private int counter;
//
//        SparseIndexedListIterator() {
//            current = head;
//            counter = 0;
//        }
//
//        @Override
//        public boolean hasNext() {
//            return counter < length;
//        }
//
//        @Override
//        public T next() throws NoSuchElementException {
//            if (!hasNext()) {
//                throw new NoSuchElementException();
//            }
//
//            T data = get(counter);
//            counter++;
//            return data;
//        }
//    }
//}
