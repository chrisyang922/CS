package hw2;

// **********************************************************

// Assignment2:

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

public class BigInt {


  public static final int SIZE = 20;

  public static final int[] NaBI = {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


  /*
   * Convert the integer n into a big integer; if n < 0, then return NaBI; note that n won’t
   * overflow the array, since it’s an int.
   */
  public static int[] intToBigInt(int n) {
    int[] integerArray = new int[SIZE];
    for (int x = 0; x < integerArray.length; x++) {
      integerArray[x] = 0;
    }
    if (n < 0) {
      return NaBI;
    } else {
      int count = SIZE - 1;
      int remainder = 0;
      while (n > 0) {
        remainder = n % 10;
        n = n / 10;
        integerArray[count] = remainder;
        count--;
      }
    }

    return integerArray;
  }

  /*
   * Convert the String s into a big integer; if s does not represent a legal big int (i.e.,
   * contains a character other than ’0’ .. ’9’ or is longer than SIZE characters) return NaBI.
   */
  public static int[] stringToBigInt(String s) {
    int[] integerArray = new int[SIZE];
    String empty = "";
    int change = 0;
    for (int x = 0; x < integerArray.length; x++) {
      integerArray[x] = 0;
    }
    if (s.length() > SIZE) {
      return NaBI;
    }
    for (int x = 0; x < s.length(); x++) {
      if (Character.isDigit(s.charAt(x)) == false) {
        return NaBI;
      }
    }
    int length = SIZE - s.length();
    for (int x = 0; x < s.length(); x++) {
      empty = Character.toString(s.charAt(x));
      change = Integer.parseInt(empty);
      integerArray[length] = change;
      length++;

    }


    return integerArray;
  }


  /*
   * Return a String representation of the big integer (with leading zeros suppressed); if any
   * member of A is NOT a digit (e.g., is negative or > 9) return "NaBI".
   */
  public static String bigIntToString(int[] A) {
    String answer = "";

    for (int x = 0; x < A.length; x++) {
      if (A[x] > 9 || A[x] < 0) {
        return "NaBI";
      }
    }

    int startingPoint = 0;
    for (int x = 0; x < A.length; x++) {
      if (A[x] != 0) {
        startingPoint = x;
        break;
      }
      if (x == A.length - 1) {
        return "0";
      }
    }
    for (int y = startingPoint; y < A.length; y++) {
      answer += A[y];
    }


    return answer;
  }

  // Compare the two big integers A and B and return -1, 0, or 1, depending
  // on whether A < B, A == B, or A > B, respectively.

  public static int compareTo(int[] A, int[] B) {

    for (int x = 0; x < A.length; x++) {
      if (A[x] > B[x]) {
        return 1;
      } else if (A[x] < B[x]) {
        return -1;
      }

    }

    return 0;
  }

  /*
   * Add two big integers and return a new array representing their sum; if the result overflows,
   * i.e., contains more than SIZE digits, return NaBI.
   */

  public static int[] add(int[] A, int[] B) {
    int[] answerArray = new int[SIZE];
    int value;
    int yes = 0;
    for (int x = 0; x < answerArray.length; x++) {
      answerArray[x] = 0;
    }
    for (int x = A.length - 1; x >= 0; x--) {
      value = yes + A[x] + B[x];
      if (value >= 10) {
        yes = value / 10;
        value = value % 10;
      } else {
        yes = 0;
      }
      answerArray[x] = value;
    }

    if (yes >= 1) {
      return NaBI;
    }
    return answerArray;
  }

  public static int[] mul(int[] A, int[] B) {
    int[] answerArray = new int[SIZE];
    int[] subAnswerArray = new int[SIZE];

    int value = 0;
    int number = 0;

    for (int x = 0; x < answerArray.length; x++) {
      answerArray[x] = 0;
    }
    for (int x = A.length - 1; x >= 0; x--) {
      int v = B.length - 1 - number;
      for (int y = B.length - 1; y >= 0; y--) {
        if (v >= 0) {
          value = B[y] * A[x];
          subAnswerArray[v] += value;
          v--;
        }
      }

      answerArray = add(subAnswerArray, answerArray);
      if (answerArray == NaBI) {
        return NaBI;
      }
      for (int z = 0; z < answerArray.length; z++) {
        subAnswerArray[z] = 0;
      }

      number++;


    }
    return answerArray;
  }



}
