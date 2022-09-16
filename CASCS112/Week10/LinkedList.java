package hw5;

//**********************************************************

//Assignment5: LinkedList

//BU UserName: chrisyan

//First Name: Jeongyong

//Last Name: Yang

//

//

//Honor Code: I pledge that this program represents my own

//program code and that I have coded on my own. I have also

//read the collaboration policy on the course syllabus for

//CS 112 and my program adheres and is consistent with the

//course syllabus.

//*********************************************************

public class LinkedList
{
  private Node dummyNode;     
  private int  numberOfEntries; 

  public LinkedList()
  {
    initializeDataFields();
  } 

  public void clear()
  {
    initializeDataFields();
  } 

  public void add(String newEntry)        
  {
    Node newNode = new Node(newEntry,null);
    if(numberOfEntries == 0)
    {
      dummyNode.setNextNode(newNode);
    }
    else
    {
      Node current = getNodeAt(numberOfEntries); 
      current.setNextNode(newNode); 

    }
    numberOfEntries ++;
  }  

  public void add(int newPosition, String newEntry)
  {
    if(newPosition == 0)
    {
      dummyNode.setNextNode(new Node(newEntry,null));
    }
    else
    {
      Node newNode = new Node(newEntry,null);
      Node prevNode = getNodeAt(newPosition - 1);
      System.out.println(prevNode);
      newNode.setNextNode(prevNode.getNextNode());
      prevNode.setNextNode(newNode);
    }
    numberOfEntries++;
  } 

  public String remove(int givenPosition)
  {
    String s ="";
    if(givenPosition >= 0)
    {
      Node prevNode = getNodeAt(givenPosition-1);
      Node nextNode = getNodeAt(givenPosition);
      s = nextNode.getData();
      prevNode.setNextNode(nextNode.getNextNode());
      numberOfEntries--;
    }
    return s;
  }

  public String replace(int givenPosition, String newEntry)
  {
    String s = "";
    if(givenPosition >= 0)
    {
      Node nextNode = getNodeAt(givenPosition);
      s = nextNode.getData();
      nextNode.setData(newEntry);
    }
    return s;
  } 

  public String getEntry(int givenPosition)
  {
    String s = "";
    if(givenPosition >= 0)
    {
      Node nextNode = getNodeAt(givenPosition);
      s = nextNode.getData();
    }
    return s;
  } 

  public String[] toArray()
  {
    String[] x = new String[this.getLength()];
    for(int y = 0; y < x.length; y++)
    {
      Node theNode = getNodeAt(y);
      x[y] = theNode.getData();
    }
    return x;
  } 

  public boolean contains(String anEntry)
  {
    Node c = dummyNode.next;
    while(c!= null)
    {
      if(c.getData().compareTo(anEntry) == 0)
      {
        return true;
      }
      c = c.getNextNode();
    }
    return false;
  }

  public int getLength()
  {
    return numberOfEntries;
  } 

  public boolean isEmpty()
  {
    if(numberOfEntries == 0)
    {
      return true;
    }
    return false;
  } 

  private void initializeDataFields()
  {
    dummyNode = new Node();     
    numberOfEntries = 0;
  }  

  private Node getNodeAt(int givenPosition)
  {
    Node currentNode = dummyNode;
    for (int counter = 0; counter < givenPosition; counter++)
      currentNode = currentNode.getNextNode();
    return currentNode;
  }

  private class Node
  {
    private String    data; // Entry in list
    private Node next; // Link to next node

    private Node()
    {           
      data = null;
      next = null;
    }
    private Node(String dataPortion)
    {
      data = dataPortion;
      next = null;    
    } 

    private Node(String dataPortion, Node nextNode)
    {
      data = dataPortion;
      next = nextNode;    
    }

    private String getData()
    {
      return data;
    } 

    private void setData(String newData)
    {
      data = newData;
    } 

    private Node getNextNode()
    {
      return next;
    }

    private void setNextNode(Node nextNode)
    {
      next = nextNode;
    } 
  } 
}