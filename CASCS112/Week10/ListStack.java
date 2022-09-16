package hw5;

public class ListStack<T> {
    private class Node {
        T item;
        Node next;
        Node (T item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    private Node head = null;
    
    public void push (T item) {
        head = new Node(item, head);
    }
    
    public T pop () {
        if (isEmpty()) {
            throw new RuntimeException ("Stack Underflow");
        }

        T ret = head.item;
        head = head.next;
        return ret;
    }
    
    public boolean isEmpty() {
        return head == null;
    }
    
    /*
     * Prints one item per line, with the most recently added item at the bottom
     */
    public String toString () {
        String s = "";
        for (Node p = head; p != null; p = p.next) {
            s=p.item.toString()+"\n"+s;
        }
        return s;
    }
}