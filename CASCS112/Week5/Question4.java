package hw3;

// **********************************************************

// Assignment3: Question 4

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

public class Question4 {
  /**
   * Sorts an array of positive integers that lie within the range 0 to max.
   * 
   * @param a The array.
   * @param max The largest value in the array.
   */
  public static void question4(int[] a, int max) {
    int[] newArray = new int[max + 1];
    for (int x = 0; x < a.length; x++) {
      newArray[a[x]] += 1;
    }

    for (int x = 0; x < a.length; x++) {
      a[x] = nextElement(newArray);
    }


  }

  private static int nextElement(int[] a) {
    for (int x = 0; x < a.length; x++) {
      if (a[x] != 0) {
        a[x] -= 1;
        return x;
      }
    }
    return 0;
  }


  public static void main(String[] args) {
    int[] array = {2, 8, 4, 10, 15, 0, 4, 8, 2, 2, 0, 15, 10};

    System.out.println("The array before sorting:");
    System.out.println(Arrays.toString(array)); // must print [2 8 4 10 15 0 4 8 2 2 0 15 10]

    question4(array, 15);

    System.out.println("\nThe array after sorting:");
    System.out.println(Arrays.toString(array)); // must print [0 0 2 2 2 4 4 8 8 10 10 15 15]
  }

}
