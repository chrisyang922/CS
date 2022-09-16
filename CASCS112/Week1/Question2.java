package hw1;

import WeekOne.BinarySearch;

public class Question2 {

	public static boolean interpolationSearch(int[] a, int desiredItem)
	{
		/*
		 * TODO: You can reuse the implementation of the binary search from lecture 
		 * here. However, make sure that in interpolationSearch you do not calculate the
		 * middleIndex but instead calculate the index using the function provided to you 
		 * which is called findIndex. 
		 */
		int leftIndex = 0;
		int rightIndex = a.length - 1;
		int index;
		int count = 0;
		while(leftIndex < rightIndex)
		{
			count += 1;
			index = findIndex(a, leftIndex, rightIndex, desiredItem);
			if (a[index] == desiredItem)
			{
				System.out.println(count);
				return true;
			}
			else if (a[index] < desiredItem)
			{
				leftIndex = index + 1;
			}
			else
			{
				rightIndex = index - 1;
			}
			
		}
		
		System.out.println(count);

		return false; 
	
	} 


	/*
	 * DO NOT MODIFY THIS METHOD
	 * You must call the method findIndex inside your interpolationSearch
	 */
	private static int findIndex(int[] a, int first, int last, int desiredItem) {

		 double p = ((double)desiredItem - a[first]) / (a[last] - a[first]);
		 int index = first + (int)Math.ceil((last - first) * p);
		 if (index>last)
			 return last;
		 if (index<first)
			 return first;
		 return index;
		 }

	/*
	 * You can modify the main function in any way you like, 
	 * however, we will not mark your main function.
	 */
	public static void main(String[] args) 
	{
		int a[] = {-10, -5, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 
				13, 14, 15, 16, 20, 34, 99, 100, 200, 10000};

		System.out.println("Searching the array");
		for (int element : a)
			System.out.print(element + " ");
		System.out.println();	
		System.out.println();	

		if (Question2.interpolationSearch(a, 14)) 
			System.out.println("PASSES: 14 was found");
		else 
			System.out.println("FAILS: 14 was not found");

		if (Question2.interpolationSearch(a,-10)) 
			System.out.println("PASSES: -10 was found");
		else 
			System.out.println("FAILS: -10 was not found");
		
		if (Question2.interpolationSearch(a, 10000)) 
			System.out.println("PASSES: 10000 was found");
		else 
			System.out.println("FAILS: 10000 was not found");

		if (Question2.interpolationSearch(a, 200)) 
			System.out.println("PASSES: 200 was found");
		else 
			System.out.println("FAILS: 200 was not found");

		if (Question2.interpolationSearch(a, 23456)) 
			System.out.println("FAILS: 23456 was found");
		else 
			System.out.println("PASSES: 23456 was not found");

		if (Question2.interpolationSearch(a, -6)) 
			System.out.println("FAILS: -6 was found");
		else 
			System.out.println("PASSES: -6 was not found");

		if (Question2.interpolationSearch(a, 35)) 
			System.out.println("FAILS: 35 was found");
		else 
			System.out.println("PASSES: 35 was not found");
	} 
} 

/*
The expected output from the main function must be as follows: 


Searching the array
-10 -5 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 20 34 99 100 200 10000 

PASSES: 14 was found
PASSES: -10 was found
PASSES: 10000 was found
PASSES: 200 was found
PASSES: 23456 was not found
PASSES: -6 was not found
PASSES: 35 was not found
*/