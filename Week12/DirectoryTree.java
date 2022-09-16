package hw6;

// **********************************************************

// Assignment6: DirectoryTree.java

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
import java.util.Stack;

public class DirectoryTree {

  private class Node {
    private String data;
    private ArrayList<Node> child;
    private Node parent;

    public Node(String d) {
      data = d;
      child = new ArrayList<>();
      parent = null;
    }
  }

  private Node root;
  private Node currentD;
  private int size;

  public DirectoryTree() {
    root = new Node("");
    currentD = root;
  }

  public boolean mkdir(String name) {
    Stack<Node> s = new Stack<>();
    if (currentD != null) {
      s.push(currentD);
    }
    Node can = s.pop();
    if (can.data.equals(name)) {
      return false;
    } else {
      for (Node x : can.child) {
        if (x.data.equals(name)) {
          return false;
        }
      }
      can.child.add(new Node(name));
      size = size + 1;
      return true;
    }
  }

  public boolean cd(String name) {
    for (Node x : currentD.child) {
      if (x.data.equals(name)) {
        Node temp = currentD;
        currentD = x;
        currentD.parent = temp;
        size = 1;
        return true;
      }
    }
    return false;
  }


  public boolean cdUp() {

    if (currentD != root) {
      currentD = currentD.parent;
      if (currentD.data.equals(root.data)) {
        currentD.parent = null;
      } else {
        currentD.parent = currentD.parent;
      }
      return true;
    } else {
      return false;
    }

  }


  public boolean rmdir(String name) {
    return _rmdir(root, name);
  }

  private boolean _rmdir(Node n, String name) {
    if (n == null) {
      return false;
    } else {
      Boolean value = false;
      for (Node x : n.child) {
        if (x.data.equals(name)) {
          n.child.remove(x);
          value = true;
          return value;
        } else {
          if (value == true) {
            return true;
          }
          value = _rmdir(x, name);
        }
      }
      return value;
    }
  }

  public String ls() {
    String answer = "";
    for (Node x : currentD.child) {
      answer += x.data + "\n";
    }
    return answer;
  }

  public String printSubTree() {
    return _printSubTree(currentD, 0);
  }

  public String _printSubTree(Node node, int depth) {
    if (node == null) {
      return "";
    } else {
      String ret = "";
      int i = 0;
      while (i < depth) {
        ret = ret + " ";
        i++;
      }
      ret = ret + node.data + "\n";
      for (Node x : node.child) {
        ret += _printSubTree(x, depth + 1);
      }
      return ret;
    }
  }


  public String pwd() {

    if (_pwd2(root).equals("")) {
      return "/";
    } else {
      return _pwd2(root);
    }

  }

  private String _pwd2(Node node) {
    if (node == null) {
      return "";
    } else {
      String answer = "";
      for (Node x : node.child) {
        if (x.data.equals(currentD.data)) {
          Node t = x;
          while (t != root) {
            answer = "/" + t.data + answer;
            t = t.parent;
          }
          return answer;
        } else {
          answer += _pwd2(x);
        }
      }
      return answer;
    }
  }

  public int numNodes() {
    return _numNodes(currentD) + 1;
  }

  private int _numNodes(Node node) {
    int count = 0;
    if (node == null) {
      return 0;
    } else {
      count = node.child.size();
      for (Node x : node.child) {
        count += _numNodes(x);
      }
    }
    return count;
  }
}
