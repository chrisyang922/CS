package hw5;
/*
 * You can edit this file in any way you like. This will not be graded. 
 */
import hw5.OrderedList;
public class OrderedListDriver {

    public static void main(String[] args) {
        OrderedList<Integer> l = new OrderedList<Integer>();
        System.out.println(l);
        l.insert(100);
        System.out.println(l);
        l.insert(100);
        System.out.println(l);
        l.insert(22);
        System.out.println(l);
        l.insert(Integer.MIN_VALUE);
        System.out.println(l);
        System.out.println(l.delete(100));
        System.out.println(l.delete(2));
        System.out.println(l);
        System.out.println(l.contains(100));
        System.out.println(l.contains(22));
        System.out.println(l.contains(4));
        OrderedList<Integer> l2 = new OrderedList<Integer>();
        //l2.insert(0);
        //l2.insert(5);
        //l2.insert();
        System.out.println(l2);
        System.out.println(l2.merge(l));
        
        
        
        
        /*
         * You should get the following output once you have your code completed: 
        []
        [100]
        [100, 100]
        [22, 100, 100]
        [-2147483648, 22, 100, 100]
        true
        false
        [-2147483648, 22, 100]
         */
    }
}