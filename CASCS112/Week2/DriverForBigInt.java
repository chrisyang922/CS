package hw2;
/*
 * Thanks to Professor Wayne Snyder for preparing this.
 * You can change this file in any way you like. We will not use this file
 * for grading. You are also not asked to submit this file. 
 */
import java.util.Arrays;

public class DriverForBigInt {
	// Unit Test: Here is where we put tests to verify that this class works properly; it is
	// not used except for debugging and testing purposes, and will be ignored when you use
	// this class as as a static library.
	    
	    public static void main(String [] args) {  
	        
	        System.out.println("\nUnit Test for BigInt Library.\n");
	        
	        
	        System.out.println("Test 1: Should be\n1234567");
	        int[] A = { 0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,6,7 };
	        System.out.println(BigInt.bigIntToString(A));
	        System.out.println(); 
	        
	        System.out.println("Test 2: Should be\n0");
	        int[] B = { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
	        System.out.println(BigInt.bigIntToString(B));
	        System.out.println();  
	        
	        System.out.println("Test 3: Should be\nNaBI");
	        int[] C = { 0,0,0,0,23,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
	        System.out.println(BigInt.bigIntToString(C));
	        System.out.println(); 
	        
	        System.out.println("Test 4: Should be\n[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7]");
	        A = BigInt.intToBigInt( 1234567 );
	        System.out.println(Arrays.toString(A));
	        System.out.println(); 
	        
	        System.out.println("Test 5: Should be\n1234567");
	        System.out.println(BigInt.bigIntToString(A));
	        System.out.println();
	        
	        System.out.println("Test 6: Should be \n0");
	        B = BigInt.intToBigInt( 0 );
	        System.out.println(BigInt.bigIntToString(B));
	        System.out.println();
	        
	        System.out.println("Test 7: Should be\nNaBI");
	        C = BigInt.intToBigInt( -4 );
	        System.out.println(BigInt.bigIntToString(C));
	        System.out.println();
	        
	        System.out.println("Test 8: Should be\n[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 4, 1, 5, 9, 2]");
	        System.out.println(Arrays.toString( BigInt.stringToBigInt( "3141592" ) ));
	        System.out.println();
	        
	        System.out.println("Test 9: Should be\n0");
	        System.out.println(BigInt.bigIntToString( BigInt.stringToBigInt( "0" ) ));
	        System.out.println();
	        
	        System.out.println("Test 10: Should be \nNaBI");
	        System.out.println(BigInt.bigIntToString( BigInt.stringToBigInt( "234h56" ) ));
	        System.out.println(); 
	        
	        System.out.println("Test 11: Should be\nNaBI");
	        System.out.println(BigInt.bigIntToString( BigInt.stringToBigInt( "23456379238472937829384272939472937429374" ) ));
	        System.out.println();
	        
	        System.out.println("Test 12: Should be\n0");
	        System.out.println(BigInt.compareTo( BigInt.stringToBigInt("12375"), BigInt.stringToBigInt("12375")));
	        System.out.println();
	        
	        System.out.println("Test 13: Should be\n-1");
	        System.out.println(BigInt.compareTo( BigInt.stringToBigInt("12375"), BigInt.stringToBigInt("12378")));
	        System.out.println();
	        
	        System.out.println("Test 14: Should be\n1");
	        System.out.println(BigInt.compareTo( BigInt.stringToBigInt("12395"), BigInt.stringToBigInt("12375")));
	        System.out.println();
	        
	        System.out.println("Test 15: Should be\n0");
	        System.out.println(BigInt.compareTo( BigInt.stringToBigInt("0"), BigInt.stringToBigInt("0")));
	        System.out.println();
	        
	        System.out.println("Test 16: Should be\n-1");
	        System.out.println(BigInt.compareTo( BigInt.stringToBigInt("0"), BigInt.stringToBigInt("12375")));
	        System.out.println();
	        
	        System.out.println("Test 17: Should be\n1");
	        System.out.println(BigInt.compareTo( BigInt.stringToBigInt("111"), BigInt.stringToBigInt("0")));
	        System.out.println();
	        
	        System.out.println("Test 18: Should be\n123456789123456789");
	        System.out.println(BigInt.bigIntToString(BigInt.add( BigInt.stringToBigInt("36182736036182736"), BigInt.stringToBigInt("87274053087274053"))));
	        System.out.println();
	        
	        System.out.println("Test 19: Should be\n3141592653598");
	        System.out.println(BigInt.bigIntToString(BigInt.add( BigInt.stringToBigInt("0"), BigInt.stringToBigInt("3141592653598"))));
	        System.out.println();
	        
	        System.out.println("Test 20: Should be\n10000000000000000000");
	        System.out.println(BigInt.bigIntToString(BigInt.add( BigInt.stringToBigInt("9999999999999999999"), BigInt.stringToBigInt("1"))));
	        System.out.println();
	        
	        System.out.println("Test 21: Should be\nNaBI");
	        System.out.println(BigInt.bigIntToString(BigInt.add( BigInt.stringToBigInt("99999999999999999999"), BigInt.stringToBigInt("1"))));
	        System.out.println();
	        
	        
	    }

}