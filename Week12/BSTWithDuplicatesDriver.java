package hw6;

import java.util.List;

public class BSTWithDuplicatesDriver
{
    public static void main(String[] args) 
    {
        String brent = "Brent";
        String donna = "Donna";
        String jerry = "Jerry";
        String luke  = "Luke";
        String sue   = "Sue";
        String tom   = "Tom";

        String missy = "Missy";

        BSTWithDuplicates<String> myBST = new BSTWithDuplicates<>();
        myBST.add(luke);
        myBST.add(brent);
        myBST.add(donna);
        myBST.add(tom);
        myBST.add(sue);
        myBST.add(jerry);
        myBST.add(brent);
        myBST.add(brent);
        myBST.add(tom);
        myBST.add(luke);


        System.out.println("\nTest getEntry:\n");
        System.out.println("Brent: " + myBST.getEntry(brent));
        System.out.println("Donna: " + myBST.getEntry(donna));
        System.out.println("Jerry: " + myBST.getEntry(jerry));
        System.out.println("Luke:  " + myBST.getEntry(luke));
        System.out.println("Sue:   " + myBST.getEntry(sue));
        System.out.println("Tom:   " + myBST.getEntry(tom));
        System.out.println("Missy: " + myBST.getEntry(missy));

        System.out.println("\nTest getAllEntries:\n");

        List<String> entryList = myBST.getAllEntriesEqualTo(luke);      
        displayList(luke, entryList);
        entryList = myBST.getAllEntriesEqualTo(brent);      
        displayList(brent, entryList);
        entryList = myBST.getAllEntriesEqualTo(tom);      
        displayList(tom, entryList);
        entryList = myBST.getAllEntriesEqualTo(sue);      
        displayList(sue, entryList);
        entryList = myBST.getAllEntriesEqualTo(missy); 
        displayList(missy, entryList);

        System.out.println("\n\nTest contains:\n");     
        System.out.println("Is Brent in tree? " + myBST.contains(brent));
        System.out.println("Is Donna in tree? " + myBST.contains(donna));
        System.out.println("Is Jerry in tree? " + myBST.contains(jerry));
        System.out.println("Is Luke in tree?  " + myBST.contains(luke));
        System.out.println("Is Sue in tree?   " + myBST.contains(sue));
        System.out.println("Is Tom in tree?   " + myBST.contains(tom));
        System.out.println("Is Missy in tree? " + myBST.contains(missy));

        
        entryList = myBST.getAllEntriesEqualTo(brent);      
        displayList(brent, entryList);

    } // end main

    
    public static void displayList(String name, List<String> list)
    {
        if (list != null)
        {
            int numberOfEntries = list.size();
            System.out.println("\n" + name + "'s list contains " + numberOfEntries
                    + " entries, as follows:");
            for (int i = 0; i < numberOfEntries; i++)
                System.out.print(list.get(i) + " ");
            System.out.println("\n");
        }
        else
            System.out.println("\n" + name + " is not in the tree");

    } // end displayList
}  // end Driver
/*
 
Test getEntry:

Brent: Brent
Donna: Donna
Jerry: Jerry
Luke:  Luke
Sue:   Sue
Tom:   Tom
Missy: null

Test getAllEntries:


Luke's list contains 2 entries, as follows:
Luke Luke 


Brent's list contains 3 entries, as follows:
Brent Brent Brent 


Tom's list contains 2 entries, as follows:
Tom Tom 


Sue's list contains 1 entries, as follows:
Sue 


Missy is not in the tree


Test contains:

Is Brent in tree? true
Is Donna in tree? true
Is Jerry in tree? true
Is Luke in tree?  true
Is Sue in tree?   true
Is Tom in tree?   true
Is Missy in tree? false

Brent's list contains 3 entries, as follows:
Brent Brent Brent 

 */

