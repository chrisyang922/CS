package hw6;
// **********************************************************

// Assignment6: BSTWithDuplicates.java

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
import java.util.ArrayList;
import java.util.List;



public class BSTWithDuplicates<T extends Comparable<T>> {
  BinaryNode root;

  public BSTWithDuplicates() {
    super();
  }


  private class BinaryNode {
    T data;
    BinaryNode left;
    BinaryNode right;

    private BinaryNode(T rootEntry) {
      data = rootEntry;
    }
  }

  public BSTWithDuplicates(T rootEntry) {
    root = new BinaryNode(rootEntry);
  }

  public T getMin() {
    if (this.root != null) {
      return _getMin(this.root);
    }
    return null;
  }

  private T _getMin(BinaryNode node) {
    if (node.left == null) {
      return node.data;
    } else {
      return _getMin(node.left);
    }
  }

  public T getMax() {
    if (this.root != null) {
      return _getMax(this.root);
    }
    return null;
  }

  private T _getMax(BinaryNode node) {
    if (node.right == null) {
      return node.data;
    } else {
      return _getMin(node.right);
    }
  }

  public T getEntry(T entry) {
    if (this.root != null) {
      return _getEntry(entry, this.root);
    }
    return null;
  }

  private T _getEntry(T entry, BinaryNode node) {
    if (node == null) {
      return null;
    } else {
      T answer = _getEntry(entry, node.left);
      T answer2 = _getEntry(entry, node.right);
      if (answer != null && answer2 != null) {
        return answer;
      } else if (answer != null) {
        return answer;
      } else if (answer2 != null) {
        return answer2;
      } else {
        if (node.data.compareTo(entry) == 0) {
          return entry;
        } else {
          return null;
        }
      }
    }
  }

  public ArrayList<T> getAllEntriesEqualTo(T entry) // *
  {
    if (this.root != null) {
      if (_getAllEntriesEqualTo(entry, this.root).isEmpty()) {
        return null;
      } else {
        return _getAllEntriesEqualTo(entry, this.root);
      }
    }
    return null;
  }

  private ArrayList<T> _getAllEntriesEqualTo(T entry, BinaryNode node) {
    if (node == null) {
      ArrayList<T> array = new ArrayList<>();
      return array;
    } else {
      ArrayList<T> array = _getAllEntriesEqualTo(entry, node.left);
      ArrayList<T> arrayTwo = _getAllEntriesEqualTo(entry, node.right);
      if (entry.compareTo(node.data) == 0) {
        array.add(node.data);
      }
      for (T x : array) {
        arrayTwo.add(x);
      }
      return arrayTwo;

    }
  }

  public boolean contains(T entry) {
    if (getEntry(entry) != null) {
      return true;
    } else {
      return false;
    }
  }

  public T add(T newEntry) {
    if (this.root == null) {
      this.root = new BinaryNode(newEntry);
      return newEntry;
    } else {
      _add(newEntry, this.root);
      return newEntry;
    }
  }

  private void _add(T newEntry, BinaryNode node) {
    if (node == null) {
      return;
    } else {
      if (newEntry.compareTo(node.data) >= 0) {
        if (node.right == null) {
          node.right = new BinaryNode(newEntry);
        } else {
          _add(newEntry, node.right);
        }
      } else {
        if (node.left == null) {
          node.left = new BinaryNode(newEntry);
        } else {
          _add(newEntry, node.left);
        }
      }
    }
  }


}

