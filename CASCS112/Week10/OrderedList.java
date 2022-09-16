package hw5;

// **********************************************************

// Assignment5: OrderedList

// BU UserName: chrisyan

// First Name: Jeongyong

// Last Name: Yang

//

//

// Honor Code: I pledge that this program represents my own

// program code and that I have coded on my own. I have also

// read the collaboration policy on the course syllabus for

// CS 112 and my program adheres and is consistent with the

// course syllabus.

// *********************************************************

public class OrderedList<T extends Comparable<T>> {
  private class Node {
    T value;
    Node next;

    Node(T v, Node n) {
      value = v;
      next = n;
    }
  }

  private Node head = new Node(null, null);

  public void insert(T v) {
    if (head.next == null) {
      Node newNode = new Node(v, null);
      head.next = newNode;
    }

    else if (head.next.next == null) {
      int compare = head.next.value.compareTo(v);
      if (compare < 0) {
        head.next.next = new Node(v, null);
        return;
      } else {
        Node current = head.next;
        head.next = new Node(v, current);
        return;
      }
    } else {

      Node current = head.next;
      Node previous = head;
      while (current != null) {
        int compare = current.value.compareTo(v);
        if (compare >= 0) {
          previous.next = new Node(v, current);
          return;
        }
        previous = current;
        current = current.next;
      }
      previous.next = new Node(v, null);
    }
  }

  /*
   * Removes v; if there is more than one instance of v, removes only one. If v was found, returns
   * true. Else, does nothing and returns false.
   */
  public boolean delete(T v) {
    Node prev = head, cur = head.next;
    while (cur != null) {
      int compResult = cur.value.compareTo(v);
      if (compResult == 0) {
        prev.next = cur.next;
        return true;
      }
      if (compResult > 0) {
        return false;
      }
      prev = cur;
      cur = cur.next;
    }
    return false;
  }

  /*
   * Returns true if the list contains v
   */

  public boolean contains(T v) {
    Node current = head.next;
    while (current != null) {
      if (current.value == v) {
        return true;
      }
      current = current.next;
    }
    return false;
  }

  /*
   * Returns the result of merging this and that; runs in time O(|this|+|that|). Does not modify
   * this or that.
   */
  OrderedList<T> merge(OrderedList<T> that) {
    Node current1 = this.head.next;
    Node current2 = that.head.next;
    OrderedList<T> newList = new OrderedList<T>();
    newList.head = new Node(null, null);
    Node previous = newList.head;
    while (current1 != null || current2 != null) {
      if (current1 == null) {
        previous.next = new Node(current2.value, null);
        current2 = current2.next;
      } else if (current2 == null) {
        previous.next = new Node(current1.value, null);
        current1 = current1.next;
      } else {
        int compare = current1.value.compareTo(current2.value);
        if (compare < 0) {
          previous.next = new Node(current1.value, null);
          current1 = current1.next;
        } else {
          previous.next = new Node(current2.value, null);
          current2 = current2.next;
        }
      }
      previous = previous.next;
    }

    return newList; // Just a placeholder--remove
  }

  /*
   * Same output format as java.util.Arrays.toString
   */
  public String toString() {
    if (head.next == null)
      return "[]";
    String s = "[";
    Node p;
    for (p = head.next; p.next != null; p = p.next) {
      s += p.value + ", ";
    }
    s += p.value + "]"; // last one is special, because it has no comma after
    return s;
  }

}
