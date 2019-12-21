
/**
 * Node Class for Linked List
 */

public class Node<E> {

    private E element; // Value for node
    private Node<E> next; // Reference to next node in list

    /** Constructor
     * @param item the element to be stored in the Node
     * @param nextVal the next node that is pointing to
     */

    public Node(E item, Node<E> nextVal) {
        element = item;
        next = nextVal;
    }

    /** Constructor
     * @param item the element to be stored in Node
     */
    public Node(E item) {
        element = item;
        next = null;
    }

    // Another constructor
    public Node() {
        element = null;
        next = null;
    }

    /**
     * @return the Node that is next to this
     */
    public Node<E> getNext() {
        return next;
    }

    /**
     * Sets this next to the given Node
     * @param nextVal is the node that is to be set to this Node's next
     */
    public void setNext(Node<E> nextVal) {
        next = nextVal;
    }

    /**
     * returns the element in the Node
     * @return element in the Node
     */
    public E getElement() {
        return element;
    }

    /**
     * Sets the element stored in the Node to the element given
     * @param item the element to be stored in the Node.
     */
    public E setElement(E item) {
        return element = item;
    }

}
