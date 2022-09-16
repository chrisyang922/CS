package hw5;

import java.util.Arrays;

public class TestingMethodsForExamTwo {

  public static void main(String[] args)
  {
    int[] arr = {1,4,3,19,12,10};
    insertionSort(arr);
    System.out.println(arr.toString());
    System.out.println(Arrays.toString(arr));
  }

  public static void selectionSort(int[] array)
  {
    int position = 0;
    while(position < array.length)
    {
      for(int x = position + 1; x <array.length; x++)
      {
        if (array[position] > array[x])
        {
          int temp = array[position];
          array[position] = array[x];
          array[x] = temp;
        }
      }
      position++;
    }
  }

  public static void insertionSort(int[] array)
  {
    int position = 0;
    while(position < array.length - 1)
    {

      int nextPosition = position + 1;
      for(int x = nextPosition - 1; x >= 0; x--)
      {
        if(array[x] > array[nextPosition])
        {
          int temp = array[nextPosition];
          array[nextPosition] = array[x];
          array[x] = temp;
          System.out.println(array[x] + array[nextPosition]);
          System.out.println(Arrays.toString(array));
        }
      }

      position++;
    }
  }
  

}
