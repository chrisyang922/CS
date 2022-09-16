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

import java.util.Scanner;

public class Palindrome {

  public static boolean isPalindrome(String line) {
    char[] punctuation = {'.', ',', ';', ':', '?', '/', '\'', '\"', '!', '-', '~', '(', ')'};
    line = line.toLowerCase();
    for (int x = 0; x < line.length(); x++) {
      if (Character.isDigit(line.charAt(x)) == false
          && Character.isLetter(line.charAt(x)) == false) {
        line = line.replace(Character.toString(line.charAt(x)), "");
      } else if (Character.isWhitespace(line.charAt(x))) {
        line = line.replace(Character.toString(line.charAt(x)), "");
      }

    }
    for (int x = 0; x < line.length(); x++) {
      if (line.charAt(x) != line.charAt(line.length() - x - 1)) {
        return false;
      }
    }
    return true;



  }


  public static void main(String[] args) {

    System.out.println("\nWelcome to the Palindrome Test Program!");

    Scanner userInput = new Scanner(System.in);

    System.out.println("\nType in a sentence to be tested or Quit-d  to end:");

    while (userInput.hasNextLine()) {

      String line = userInput.nextLine();

      if (isPalindrome(line))
        System.out.println("Palindrome!");
      else
        System.out.println("Not a palindrome!");
    }

    System.out.println("bye!");

  }
}
