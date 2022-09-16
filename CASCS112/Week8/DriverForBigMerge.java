package hw4;
/*
 * TODO: THIS IS THE FILE FOR TESTING YOUR CODE -- 
 * DO WHATEVER YOU WANT HERE, IT WON'T BE GRADED
 */

public class DriverForBigMerge {
    public static void testBigMerge() {
        int [][] a = {{0,5,10,15,20},{1,6,11},{12},{3,8,13,18,23,28}};
        for (int x: BigMerge.merge(a)) System.out.println(x+" ");
    }
    
    public static void main(String[] args) {
        testBigMerge();
    
    }       
}