package hw3;

// **********************************************************

// Assignment3: Question 0

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
public class Question0 {

  /*
   * takes in as input an array of String and returns back a boolean value whether it is sorted in
   * Lexicographical order or not.
   */
  public static boolean isSorted(String[] array) {
    for (int x = 0; x < array.length - 1; x++) {
      if (array[x].compareTo(array[x + 1]) == 1) {
        return false;
      }
    }
    return true;
  }



  public static void main(String[] args) {

    String[] items = {"a", "b", "c"};
    System.out.println(Question0.isSorted(items)); // must print true

    String[] items1 = {"c", "b", "a"};
    System.out.println(Question0.isSorted(items1)); // must print false

    String[] items2 = {"111", "112", "131", "132"};
    System.out.println(Question0.isSorted(items2)); // must print true

    String[] items3 = {"a", "aa", "b", "bb", "c", "ca"};
    System.out.println(Question0.isSorted(items3)); // must print true

    String[] items4 = {"z", "zz", "zzz", "zzzz"};
    System.out.println(Question0.isSorted(items4)); // must print true

  }
}
