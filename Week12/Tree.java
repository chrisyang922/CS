package hw6;

// **********************************************************

// Assignment6: Tree.java

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

public class Tree {
  private Node root;

  private class Node {
    private String data;
    private String color;
    private Node parent;
    private ArrayList<Node> arr;

    public Node() {
      color = "WHITE";
      arr = new ArrayList<>();
      for (int x = 0; x < 26; x++) {
        arr.add(null);
      }
      parent = null;
    }

  }



  public Tree() {
    root = new Node();

  }

  public void add(String word) {
    _add(word, this.root);
  }

  private void _add(String word, Node node) {
    if (word.length() == 0) {
      return;
    } else {
      char c = word.charAt(0);
      int value = convertFromLetterToIndexPosition(c);
      Node temp = node;
      Node theNode = new Node();
      theNode.data = c + "";
      theNode.parent = temp;
      if (node.arr.get(value) == null) {
        node.arr.set(value, theNode);
      }
      if (word.length() == 1) {
        if (node.arr.get(value) != null) {
          node.arr.get(value).color = "BLACK";
        }
      }

      _add(word.substring(1, word.length()), node.arr.get(value));
    }
  }

  public boolean check(String word) {
    return _check(word, this.root);
  }

  private boolean _check(String word, Node node) {
    if (word.length() == 0) {
      return false;
    } else if (node.data == null && this.root != node) {
      return false;
    } else {
      boolean answer = false;
      char c = word.charAt(0);
      int value = convertFromLetterToIndexPosition(c);
      if (node.arr.get(value) != null) {
        if (node.arr.get(value).data.equals(c + "")) {
          if (word.length() == 1 && node.arr.get(value).color.equals("BLACK")) {
            answer = true;
            return answer;
          }
          answer = _check(word.substring(1, word.length()), node.arr.get(value));
        }
      }
      return answer;
    }
  }



  private static int convertFromLetterToIndexPosition(char letter) {
    int temp = (int) letter;
    int temp_integer = 96;
    return (temp - temp_integer - 1);

  }



  private static String convertFromIndexToLetter(int index) {
    return String.valueOf((char) (index + 97));
  }


  public String getDictionaryInAlphabeticalOrder() {
    return _getDictionary(this.root);
  }

  public String _getDictionary(Node node) {
    if (node == null) {
      return "";
    } else {
      String answer = "";
      for (Node x : node.arr) {
        if (x != null) {
          if (x.color.equals("BLACK")) {
            Node temp = x;
            while (temp != root) {
              answer = temp.data + answer;
              temp = temp.parent;
            }
            if (temp == this.root) {
              answer += " ";
            }
          }
          answer += _getDictionary(x);
        }
      }
      return answer;
    }
  }


  public static void main(String[] args) {
    Tree dictionary = new Tree();
    dictionary.add("abbas");
    dictionary.add("ab");
    dictionary.add("a");
    dictionary.add("xan");
    dictionary.add("ban");
    dictionary.add("acbbas");
    dictionary.add("cat");
    dictionary.add("dog");

    System.out.println(dictionary.check("abbas")); // true
    System.out.println(dictionary.check("ab")); // true
    System.out.println(dictionary.check("abb")); // false
    System.out.println(dictionary.check("a")); // true
    System.out.println(dictionary.check("xab")); // false
    System.out.println(dictionary.check("xan")); // true


    System.out.println(dictionary.getDictionaryInAlphabeticalOrder()); // a ab abbas acbbas ban cat
                                                                       // dog xan

  }

}

