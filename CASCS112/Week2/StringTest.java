package hw2;

import java.util.Scanner;

public class StringTest { 
  
  public static void main(String[] args) {
    
    // Print out welcome message
    
    System.out.println("\nWelcome to the String Test Program!");
    System.out.println("Demonstrate various features of the String Library");
    
    // Define a scanner for user input
    
    Scanner userInput = new Scanner(System.in);
    

    System.out.println("\nType in a integer or Control-d to end:");
    int count = 0; 
    while(userInput.hasNextInt() ){
       int n = userInput.nextInt();
       System.out.println("Number " + count + " = " + n); 
       ++count;    
    }
   
 /* This one will input a line of text, and uses a for loop
 
    System.out.println("\nType in a line of text (a String) or Control-d to end:");
    for (int counter = 0; userInput.hasNextLine(); ++counter ){
       String line = userInput.nextLine();
       System.out.println("Line " + counter + " = " + line);     
    }
	
    */
    
  }
  
}