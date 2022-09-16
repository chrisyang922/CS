package hw4;
import java.io.*;
/*
 * THIS IS THE FILE FOR TESTING YOUR CODE -- 
 * DO WHATEVER YOU WANT HERE, IT WON'T BE GRADED
 */

public class WordGameDriver {
  
    public static void main(String[] args) {
        String dictionaryFileName = "src"+File.separatorChar+"hw4"+File.separatorChar+"dictionary.txt";
        String boardFileName = "src"+File.separatorChar+"hw4"+File.separatorChar+"board.txt";

        String [] dict = WordGameHelperClass.readDictionary(dictionaryFileName);
        if (dict == null) return;
        char [][] board = WordGameHelperClass.readBoard(boardFileName);
        if (board == null) return;
    
        String [] result = WordGame.search(dict, board);
        for (String w : result) {
            System.out.println(w);
        }
    }   
    
}