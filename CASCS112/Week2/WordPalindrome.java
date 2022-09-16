package hw2;

// **********************************************************

// Assignment2

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
/*
 * Your First Name: Jeongyong Your Last Name: Yang Your BU username: chrisyan
 * 
 * Honor Code:
 * 
 */
import java.util.Scanner;

public class WordPalindrome {

  public static boolean isWordPalindrome(String line) {



    char[] punctuation = {'.', ',', ';', ':', '?', '/', '\'', '\'', '!', '-', '~', '(', ')'};
    line = line.toLowerCase();
    for (int x = 0; x < line.length(); x++) {
      if (Character.isDigit(line.charAt(x)) == false
          && Character.isLetter(line.charAt(x)) == false) {
        if (Character.isWhitespace(line.charAt(x)) == false) {
          line = line.replace(Character.toString(line.charAt(x)), "");
        }
      }
    }
    int count = 1;

    for (int x = 0; x < line.length(); x++) {
      if (Character.isWhitespace(line.charAt(x))) {
        count += 1;
      }
    }
    String[] stringArray = new String[count];
    int start = 0;
    int current = 0;
    int counting = 0;
    while (current < line.length()) {
      if (line.charAt(current) == ' ') {
        stringArray[counting] = line.substring(start, current);
        start = current + 1;
        counting += 1;
      }
      current += 1;
    }
    stringArray[counting] = line.substring(start, line.length());
    System.out.println(Arrays.toString(stringArray));
    for (int x = 0; x < stringArray.length; x++) {
      if (stringArray[x].equals(stringArray[stringArray.length - 1 - x]) == false) {
        return false;
      }
    }

    return true;



  }

  public static void main(String[] args) {

    System.out.println("\nWelcome to the Word Palindrome Test Program!");

    Scanner userInput = new Scanner(System.in);

    System.out.println("\nType in a sentence to be tested or Control-d to end:");

    while (userInput.hasNextLine()) {

      String line = userInput.nextLine();

      if (isWordPalindrome(line))
        System.out.println("Word Palindrome!");
      else
        System.out.println("Not a Word Palindrome!");
    }

    System.out.println("bye!");
  } // main()

}
