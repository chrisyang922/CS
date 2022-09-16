package hw4;

// **********************************************************

// Assignment4: WordGameHelperClass.java

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

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/*
 * This class is used by: 1. FindSpacing.java 2. FindSpacingDriver.java 3. WordGame.java 4.
 * WordGameDriver.java
 */
public class WordGameHelperClass {

  /*
   * Returns true if an only the string s is equal to one of the strings in dict. Assumes dict is in
   * alphabetical order.
   */
  public static boolean inDictionary(String[] dict, String s) {

    for (String str : dict) {
      int areTheySame = str.compareTo(s);
      if (areTheySame == 0) {
        return true;
      }
    }

    return false;
  }


  /*
   * Returns true if and only if dict is in alphabetical order
   */
  public static boolean checkAlphaOrder(String[] dict) {
    for (int x = 0; x < dict.length - 1; x++) {
      if (dict[x].compareTo(dict[x + 1]) > 0) {
        return false;
      }
    }
    return true;
  }


  /*
   * Reads in a dictionary file (one word per line) and checks if it's in alphabetical order.
   * Returns null in case of failure.
   */
  public static String[] readDictionary(String dictionaryFileName) {
    Scanner fileScanner;

    // Open the dictionary file
    try {
      fileScanner = new Scanner(new File(dictionaryFileName));
    } catch (IOException e) {
      System.err.println("Unable to open dictionary file. " + e.getMessage());
      System.err.println("Currently in directory " + System.getProperty("user.dir"));
      return null;
    }

    // read the dictionary file
    LinkedList<String> prelimDict = new LinkedList<String>();
    while (fileScanner.hasNext()) {
      prelimDict.add(fileScanner.next().toLowerCase());
    }
    String[] dict = prelimDict.toArray(new String[0]);
    fileScanner.close();
    if (checkAlphaOrder(dict)) {
      return dict;
    } else {
      System.err.println("Error: dictionary not in alphabetical order.");
      return null;
    }
  }

  /*
   * Returns true if and only if board is a rectangular array: that is, board[i].length is the same
   * for every i from 0 to board.length-1
   */
  public static boolean checkIfRectangle(char[][] board) {
    if (board.length == 0) {
      return false;
    } else {
      int widthOfBoard = board[0].length;
      for (int x = 0; x < board.length; x++) {
        if (widthOfBoard != board[x].length) {
          return false;
        }
      }
    }
    return true;
  }

  /*
   * Converts a text file into a two-dimensional array of characters, where a[i][j] is the character
   * in row i from the top (starting at 0) and column j from the left (starting at 0). Newline
   * characters are not included in the array. Checks that the array is rectangular. Returns null in
   * case of failure.
   */
  public static char[][] readBoard(String boardFileName) {
    Scanner fileScanner;
    // open the board file
    try {
      fileScanner = new Scanner(new File(boardFileName));
    } catch (IOException e) {
      System.out.println("Unable to open board file " + e.getMessage());
      System.out.println("Currently in directory " + System.getProperty("user.dir"));
      return null;
    }

    // read the board file
    LinkedList<char[]> prelimBoard = new LinkedList<char[]>();
    while (fileScanner.hasNext()) {
      prelimBoard.add(fileScanner.next().toLowerCase().toCharArray());
    }
    char[][] board = prelimBoard.toArray(new char[0][]);
    fileScanner.close();

    if (checkIfRectangle(board)) {
      return board;
    } else {
      System.err.println("Error: board is not rectangular.");
      return null;
    }
  }

}
