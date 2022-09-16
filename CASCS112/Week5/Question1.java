package hw3;
// **********************************************************

// Assignment3: Question 1

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

public class Question1 {


  public static void question1(int[] items) {
    int sameItem = 0;
    int count = 0;
    int index = items.length - 3;
    int dontcare = items.length - 2;
    boolean yes = false;
    while (index >= -1) {
      if (count == 0) {

        if (items[index + 1] == items[index + 2] && items[index + 1] != items[index]
            && yes == false) {
          swap(index + 1, index, items);
          dontcare = index;
          sameItem = items[index + 1];
          count++;
          index -= 1;
        } else if (items[index + 1] == items[index + 2]
            && (items[index + 1] == items[index] || yes == true)) {
          yes = true;
          swap(dontcare, index, items);
          sameItem = items[index + 1];
          count++;
          index -= 1;
        }
      } else {
        if (items[index + 1] != items[index + 2] && items[index + 1] != sameItem) {
          sameItem = items[index + 1];
          swap(index + 1, dontcare, items);
          dontcare -= 1;
        }
      }
      index -= 1;
    }
  }

  private static void swap(int i, int j, int[] items) {
    int temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }

  public static void main(String[] args) {

    int[] items = {2, 2, 4, 4, 4, 5, 8, 8};
    Question1.question1(items);
    System.out.println(Arrays.toString(items)); // must print [2, 8, 4, 4, 2, 4, 5, 8]
    System.out.println("--------------------------");


    int[] items1 = {1, 2, 2, 2, 2, 2, 2, 2, 2};
    Question1.question1(items1);
    System.out.println(Arrays.toString(items1)); // must print [2, 2, 2, 2, 2, 2, 2, 1, 2]
    System.out.println("--------------------------");


    int[] items2 = {1, 1, 1, 1, 1, 1, 1, 1, 1};
    Question1.question1(items2);
    System.out.println(Arrays.toString(items2)); // must print [1, 1, 1, 1, 1, 1, 1, 1, 1]
    System.out.println("--------------------------");


    int[] items3 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    Question1.question1(items3);
    System.out.println(Arrays.toString(items3)); // must print [1, 2, 3, 4, 5, 6, 7, 8, 9]
    System.out.println("--------------------------");

    int[] items4 = {1, 2, 3, 4, 5, 6, 6, 7, 7, 8, 8, 9, 9};
    Question1.question1(items4);
    System.out.println(Arrays.toString(items4)); // must print [9, 6, 8, 7, 1, 2, 3, 4, 5, 6, 7, 8,
    // 9]
    System.out.println("--------------------------");

  }



}
