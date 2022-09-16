package hw3;
// **********************************************************

// Assignment3: Question 2

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

public class Question2 {

  public static void question2(int[] items) {
    int index = 0;
    int length = items.length - 1;
    int location = 0;
    while (index < items.length - 1 && location < length) {
      if (items[index] > 0) {
        swap(index, length, items);
        length--;
      } else if (items[index] < 0) {
        swap(index, location, items);
        location++;
        index++;
      } else {
        index++;
      }
      if (index > length) {
        break;
      }
    }
  }

  private static void swap(int i, int j, int[] items) {
    int temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }


  public static void main(String[] args) {


    int[] items = {-7, -3, 20, 10, 8, 2};
    Question2.question2(items);
    System.out.println(Arrays.toString(items)); // must print [-7, -3, 10, 8, 2, 20]

    int[] items1 = {1, 1, 1, 1, 1, 1, 1, 1};
    Question2.question2(items1);
    System.out.println(Arrays.toString(items1)); // must print [1, 1, 1, 1, 1, 1, 1, 1]


    int[] items2 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    Question2.question2(items2);
    System.out.println(Arrays.toString(items2)); // must print [2, 3, 4, 5, 6, 7, 8, 9, 1]

    int[] items3 = {6, 7, 8, 0, 1, 2, 3, -1, -2};
    Question2.question2(items3);
    System.out.println(Arrays.toString(items3)); // must print [-2, -1, 0, 1, 2, 3, 8, 7, 6]

    int[] items4 = {0, 0, 0, 0, 1, 2, 3, -1, -2, -3};
    Question2.question2(items4);
    System.out.println(Arrays.toString(items4)); // must print [-3, -2, -1, 0, 0, 0, 0, 3, 2, 1]

    int[] items5 = {-1, 4, 5, 6, 0, 0, -2};
    Question2.question2(items5);
    System.out.println(Arrays.toString(items5)); // must print [-1, -2, 0, 0, 0, 6, 5, 4]



  }
}
