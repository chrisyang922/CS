/*
 * Student First Name: Jeongyong Student Last Name: Yang Student BU Number: U95912941 Purpose:
 * Instead of Mergesort that merges and sorts two arrays, this bigMerge takes in as many arrays as a
 * 2D list that is already sorted and merges them into one single sorted array
 */
package hw4;

// **********************************************************

// Assignment4: BigMerge.java

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
import java.util.Arrays;

public class BigMerge {

  /*
   * Returns j such that a[j][index[j]] is the minimum of the set S={a[i][index[i]] for every i such
   * that index[i]<a[i].length} If the set S is empty, returns -1, Runs in time a.length.
   */
  public static int argMin(int[][] a, int[] index) {

    // if the length of a is 0, returns negative -1
    if (a.length == 0) {
      return -1;
    }
    // if the length of a is not 0, then do the following
    else {
      int theIndexOfMinimumValue = 0;
      int minimumValue = 0;
      int first = 0;
      while (true) {
        if (first > a.length) {
          return -1;
        }
        if (a[first].length <= index[first]) {
          first++;
        } else {
          minimumValue = a[first][index[first]];
          theIndexOfMinimumValue = first;
          break;
        }
      }
      for (int x = 0; x < a.length; x++) {
        if (a[x].length <= index[x]) {
          continue;
        }
        if (a[x][index[x]] < minimumValue) {
          minimumValue = a[x][index[x]];
          theIndexOfMinimumValue = x;
        }
      }
      return theIndexOfMinimumValue;
    }
  }

  /*
   * Assumes a[0]...a[length-1] each is sorted in increasing order Returns a single sorted array
   * containing all the elements in a Does not modify a. Runs in time k^2 * n, where k is a.length
   * and n is the total number of integers in a.
   */
  public static int[] merge(int[][] a) {

    int lengthOfArray = 0;
    int[] ret = new int[a.length];
    for (int x = 0; x < a.length; x++) {
      lengthOfArray += a[x].length;
    }
    int[] answerArray = new int[lengthOfArray];
    int index = 0;
    for (int x = 0; x < lengthOfArray; x++) {
      int theIndex = argMin(a, ret);
      answerArray[index] = a[theIndex][ret[theIndex]];
      ret[theIndex] += 1;
      index++;
    }
    return answerArray;

  }
}
