package hw4;

// **********************************************************

// Assignment4: WordGame.java

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
import java.util.Arrays;

public class WordGame {
  /*
   * Returns all strings that appear as a consecutive horizontal or vertical sequence of letters
   * (left-right, right-left, up-down, or down-up) in the array board and also appear in dict. Note
   * that the same word may appear multiple times on the board, and will then be multiple times in
   * the returned array.
   * 
   * dict is assumed to be in alphabetical order board is assumed to be rectangular
   */
  public static String[] search(String[] dict, char[][] board) {

    ArrayList<String> ret = new ArrayList<String>();
    int height = board.length;
    int width;


    // single Character Check (if a character itself is in the dictionary)
    if (board[0].length == 0) {
      width = 0;
    } else {
      width = board[0].length;
    }
    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        if (WordGameHelperClass.inDictionary(dict, Character.toString(board[x][y])) == true) {
          ret.add(Character.toString(board[x][y]));
        }
      }
    }


    // left-right Check
    String s = "";

    for (int y = 0; y < board.length; y++) {
      for (int x = 0; x < board[0].length; x++) {
        s += Character.toString(board[y][x]);
      }
      int beginning = 0;
      int length = s.length();
      while (s.substring(beginning, length).length() > 1) {
        while (s.substring(beginning, length).length() > 1) {
          if (WordGameHelperClass.inDictionary(dict, s.substring(beginning, length))) {
            ret.add(s.substring(beginning, length));
          }
          length--;
        }
        length = s.length();
        beginning++;
      }
      s = "";
    }

    // right-left Check
    s = "";

    for (int y = 0; y < board.length; y++) {
      for (int x = board[0].length - 1; x >= 0; x--) {
        s += Character.toString(board[y][x]);
      }
      int beginning = 0;
      int length = s.length();
      while (s.substring(beginning, length).length() > 1) {
        while (s.substring(beginning, length).length() > 1) {
          if (WordGameHelperClass.inDictionary(dict, s.substring(beginning, length))) {
            ret.add(s.substring(beginning, length));
          }
          length--;
        }
        length = s.length();
        beginning++;
      }
      s = "";
    }

    // Up-down Check
    s = "";

    for (int x = 0; x < board[0].length; x++) {
      for (int y = 0; y < board.length; y++) {
        s += Character.toString(board[y][x]);
      }
      int beginning = 0;
      int length = s.length();
      while (s.substring(beginning, length).length() > 1) {
        while (s.substring(beginning, length).length() > 1) {
          if (WordGameHelperClass.inDictionary(dict, s.substring(beginning, length))) {
            ret.add(s.substring(beginning, length));
          }
          length--;
        }
        length = s.length();
        beginning++;
      }
      s = "";
    }

    // Down-up Check
    s = "";

    for (int x = 0; x < board[0].length; x++) {
      for (int y = board.length - 1; y >= 0; y--) {
        s += Character.toString(board[y][x]);
      }
      int beginning = 0;
      int length = s.length();
      while (s.substring(beginning, length).length() > 1) {
        while (s.substring(beginning, length).length() > 1) {
          if (WordGameHelperClass.inDictionary(dict, s.substring(beginning, length))) {
            ret.add(s.substring(beginning, length));
          }
          length--;
        }
        length = s.length();
        beginning++;
      }
      s = "";
    }



    //
    // TODO (10% of your grade): if your board
    // has height h and width w, how many strings
    // do you need to check using inDictionary
    // (assume that you do nothing to filter out
    // duplicates or, equivalently, that the board
    // is such that there are no duplicates)
    // ANSWER: (w)*(w-1)*(h) + (h)*(h-1)*(w) + (w* h)
    /*
     * EXPLANATION OF THE ANSWER: For each character, we have to check whether it itself is a word
     * in the dictionary. Therefore, we have to check every character, which is height * width
     * 
     * Each Character check is height * width
     * 
     * For the left-right section, starting with the left most character, you look at every
     * combination of the left-right, except the combination with itself(it is already checked in
     * the previous section). Therefore, if the length of the combination is greater than or equal
     * to 2, we check whether it belongs to the dictionary. Since it is a left-right section, the
     * order of the combination of characters should begin from the left-most character. Depending
     * on the length of board[0], the number of combination checks are different, but have a pattern
     * of (width)*(width-1) / 2, which is similar to factorial addition. If the length of width is
     * 5, you have total of 10 combinations(I will provide an example below), which is 4+3+2+1. The
     * biggest number in this addition is 4(length of width - 1) and adds until 1. This step is
     * continued for every line, or the height of the board, which is why it should be multiplied by
     * height. So, the final equation for this part is (width)*(width-1)/2 * height.
     * 
     * For example, if the board's first line is "rate" and second line is "army", the left-right
     * algorithm checks from the left to right. "rate" gives 3 combinations that start with "r",
     * which are "ra", "rat", and "rate". Since we are currently looking at left to right, we ignore
     * the opposite direction and since single character "r" is already checked previously, it does
     * not need to counted again. In addition, "rate" gives 2 combinations that start with "a",
     * which are "at" and "ate" and 1 combination that start with "t", which is "te". When it
     * reaches the last character "e", it does not have any more characters at the right and it does
     * not require to check whether the last single character itself is in dictionary, we do not
     * need a single combination of "e". "rate", which has the length of 4, has 3+2+1 = 6
     * combinations and the biggest number 3 derives from the length of "rate"(4) minus 1, which is
     * 3. Finally, this shows that every word with length of 4 has total of 6 combinations that need
     * to be checked, which gives "army" also 6 combinations from left to right. Since the board is
     * a rectangle, the length of every word horizontally should be the same, which is why we can
     * assume that the first horizontal word has the same number of left-right combinations as the
     * next horizontal word.
     * 
     * Left-Right Section gives the equation of (width)*(width-1)*(height)/2.
     * 
     * For right-left section, starting with the right most character, you look at every combination
     * of right-left except the combination with itself (it is already checked in the first
     * section). Therefore, if the length of the combination is greater than or equal to 2, we check
     * whether it belongs to the dictionary. However, the number of checks of combinations it
     * requires to check equals to the number of combination checks of left to right, since we are
     * just reversing the order. Right-left combination can be expressed as a left-right combination
     * check of the reverse word. For example, instead of doing a right-left check of "rate", we can
     * do a left-right check of "etar". Therefore, it also has the equation
     * (width)*(width-1)*(height)/2.
     * 
     * Right-left Section gives the equation of (width)*(width-1)*(height)/2.
     * 
     * For up-down section, starting with the upper most character, you look at every combination of
     * the up-down, except the combination with itself(it is already checked in the first section).
     * Therefore, if the length of the combination is greater than or equal to 2, we check whether
     * it belongs to the dictionary. Since it is a up-down section, the order of the combination of
     * characters should begin from the upper most character.Depending on the length of board, the
     * number of combination checks are different, but have a pattern of (height)*(height-1)/2,
     * which is similar to factorial addition. If the length of height is 4, there will be a total
     * of (an example will be provided), which is 3+2+1. The biggest number in this addition is
     * 3(length of height - 1) and adds until 1. This step is continued for every width, which is
     * why it should be multiplied by height. So, the final equation for this part is (height) *
     * (height-1)/2 * width
     * 
     * For example, if the board's first vertical line is "qwer" and second vertical line is "tyui"
     * and the third vertical line is "zxcv", the up-down algorithm checks from the top to bottom.
     * "qwer" gives 3 combinations starting with "q", which are "qwer", "qwe", and "qw". Since we
     * are looking starting at the upper character, the reversed bottom-up is ignored. It gives 2
     * combinations starting with "w", which are "wer" and "we" and 1 combination starting with "e",
     * which is "er". When it reaches the last character "r", it does not have any more characters
     * to the bottom of it and since "r" itself is already checked in the first section, there are
     * no combinations to check when it reaches the last character. Therefore, there are total of
     * 3+2+1=6 combinations. It is continued for each vertical line, which is the width.
     * 
     * Up-down section gives the equation of (height)*(height-1)*(width)/2.
     * 
     * For the down-up section, starting with the down most character, you look at every combination
     * of down-up except the combination with itself (it is already checked in the first section).
     * Therefore, if the length of the combination is greater than or equal to 2, we check whether
     * it belongs to the dictionary. However, the number of checks of combinations it requires to
     * check equals to the number of combination checks of up to down, since we are just reversing
     * the order. Down-up combination can be expressed as a up-down combination check of the reverse
     * word. For example, instead of doing a down-up check of "qwer", we can do a up-down check of
     * "rewq". Therefore, it also has the equation (height)*(height-1)*(width)/2.
     * 
     * Down-up section gives the equation of (height)*(height-1)*(width)/2.
     * 
     * Therefore, the equation is (height)*(height-1)*(width)/2 + (height)*(height-1)*(width)/2 +
     * (width)*(width-1)*(height)/2 + (width)*(width-1)*(height)/2 + (width)*(height). It is same as
     * (width)*(width-1)*(height) + (height)*(height-1)*(width) + (width* height).
     */

    // This line converts ArrayList<String> to String []
    return ret.toArray(new String[0]);
  }

}
