package hw3;
// **********************************************************

// Assignment3: Set

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

import java.util.Arrays;

public class Set {

  private int SIZE = 10; // initial length of the array

  private int[] S; // array holding the set

  private int next; // pointer to next available slot in array



  /*
   * Default constructor; constructs this set as an instance of the empty set
   */
  public Set() {
    S = new int[SIZE];
    next = 0;
  }


  /*
   * Construct a set consisting of exactly the elements of A (A does not contain any duplicates)
   */
  public Set(int[] A) {
    this();
    next = 0;
    for (int x = 0; x < A.length; x++) {
      this.insert(A[x]);
    }
  }


  /*
   * return an exact copy of this set
   */
  public Set clone() {
    Set newSet = new Set(this.S);
    newSet.delete(0);
    return newSet;
  }

  /*
   * replace array A with array twice as big, and copy everything over
   */
  private void resize() {
    int[] T = new int[SIZE * 2];
    for (int i = 0; i < S.length; ++i) {
      T[i] = S[i];
    }
    SIZE = SIZE * 2;
    S = T;
  }


  /*
   * Return a string representation of this set
   */
  public String toString() {
    if (next == 0) {
      return "[]";
    }
    String s = "[";
    for (int i = 0; i < next - 1; ++i) {
      s += S[i] + ",";
    }
    s += S[next - 1] + "]";
    return s;
  }


  /*
   * Return the number of elements in the set
   */

  public int size() {
    return next;
  }

  /*
   * Return true if this is the empty set (has no members) and false otherwise.
   */

  public boolean isEmpty() {
    if (next == 0) {
      return true;
    } else {
      return false;
    }
  }

  /*
   * Return true if n is in the set and false otherwise
   */
  public boolean member(int k) {
    for (int x = 0; x < next; x++) {
      if (k == this.S[x]) {
        return true;
      }
    }
    return false;
  }



  /*
   * Returns true if this set is a subset of T, that is, every member of this set is a member of T,
   * and false otherwise.
   */
  public boolean subset(Set T) {
    for (int x = 0; x < this.next; x++) {
      if (T.member(this.S[x]) == false) {
        return false;
      }
    }
    return true;
  }


  /*
   * Returns true if this set and T have exactly the same members.
   */
  public boolean equal(Set T) {
    if (this.subset(T) && T.subset(this)) {
      return true;
    }
    return false;
  }


  /*
   * If the integer k is a member of the set already, do nothing, otherwise, add to the set; if this
   * would cause an ArrayOutOfBoundsException, call resize() to increase size of array before doing
   * insertion
   */
  public void insert(int k) {
    if (S.length == next) {
      this.resize();
    }
    for (int x = 0; x < next; x++) {
      if (k == S[x]) {
        return;
      }
    }
    S[next] = k;
    next++;
  }


  /*
   * If the integer k is not a member, do nothing; else remove it from the set; you must shift all
   * elements which occur after k in the array to the left by one slot
   */
  public void delete(int k) {
    for (int x = 0; x < next; x++) {
      if (k == S[x]) {
        for (int y = x; y < next; y++) {
          S[y] = S[y + 1];
        }
        next--;
        break;
      }
    }
  }

  /*
   * Return a new set consisting of all of the elements of this set and T combined (without
   * duplicates)
   */

  public Set union(Set T) {
    Set newSet = this.clone();
    for (int x = 0; x < T.next; x++) {
      newSet.insert(T.S[x]);
    }
    return newSet;
  }

  /*
   * Returns the intersection of S and T as a new set
   */
  public Set intersection(Set T) {
    Set newSet = new Set();
    for (int x = 0; x < T.next; x++) {
      for (int y = 0; y < this.next; y++) {
        if (T.S[x] == this.S[y]) {
          newSet.insert(T.S[x]);
        }
      }
    }
    return newSet;
  }


  /*
   * Returns the set difference this / T, i.e., all elements of this set which are not in T, as a
   * new set.
   */
  public Set setdifference(Set T) {
    Set newSet = new Set();
    for (int x = 0; x < this.next; x++) {
      if (T.member(S[x]) == false) {
        newSet.insert(S[x]);
      }
    }
    return newSet;
  }

  public static void main(String[] args) {

    System.out.println("\nUnit Test for Set: note that your answers, when they are");
    System.out.println("  sets, could be in a different order (since order does");
    System.out.println("  not matter), this is the meaning of \"same set as...\"\n");

    Set A = new Set();
    Set B = new Set(new int[] {5});
    Set C = new Set(new int[] {5, 3, 7, 4, 1});
    Set D = new Set(new int[] {4, 3, -3, 10, 8});
    Set E = new Set(new int[] {8, 4, 10});
    Set F = new Set(new int[] {10, 8, 4});

    System.out.println("Test 01: Should be\n[]");
    System.out.println(A);
    System.out.println();

    System.out.println("Test 02: Should be\n[5]");
    System.out.println(B);
    System.out.println();

    System.out.println("Test 03: Should be same set as\n[5,3,7,4,1]");
    System.out.println(C);
    System.out.println();

    System.out.println("Test 04: Should be\n[]");
    System.out.println(A.clone());
    System.out.println();

    System.out.println("Test 05: Should be same set as\n[5,3,7,4,1]");
    System.out.println(C.clone());
    System.out.println();

    System.out.println("Test 06: Should be\n0");
    System.out.println(A.size());
    System.out.println();

    System.out.println("Test 07: Should be\n5");
    System.out.println(D.size());
    System.out.println();

    System.out.println("Test 08: Should be\ntrue");
    System.out.println(A.isEmpty());
    System.out.println();

    System.out.println("Test 09: Should be\nfalse");
    System.out.println(F.isEmpty());
    System.out.println();

    System.out.println("Test 10: Should be\nfalse");
    System.out.println(A.member(4));
    System.out.println();

    System.out.println("Test 11: Should be\ntrue");
    System.out.println(C.member(1));
    System.out.println();

    System.out.println("Test 12: Should be\nfalse");
    System.out.println(D.member(1));
    System.out.println();


    System.out.println("Test 13: Should be\ntrue");
    System.out.println(A.subset(D));
    System.out.println();

    System.out.println("Test 14: Should be\nfalse");
    System.out.println(D.subset(C));
    System.out.println();

    System.out.println("Test 15: Should be\ntrue");
    System.out.println(E.subset(D));
    System.out.println();

    System.out.println("Test 16: Should be\nfalse");
    System.out.println(D.subset(E));
    System.out.println();


    System.out.println("Test 17: Should be\nfalse");
    System.out.println(D.equal(E));
    System.out.println();

    System.out.println("Test 18: Should be\ntrue");
    System.out.println(E.equal(F));
    System.out.println();

    System.out.println("Test 19: Should be\ntrue");
    System.out.println(F.equal(E));
    System.out.println();

    System.out.println("Test 20: Should be\ntrue");
    System.out.println(A.equal(A));
    System.out.println();

    System.out.println("Test 21: Should be same set as\n[4,6]");
    Set A1 = A.clone();
    A1.insert(4);
    A1.insert(6);
    A1.insert(4);
    System.out.println(A1);
    System.out.println();

    System.out.println("Test 22: Should be same set as\n[10,8,4,5]");
    Set F1 = F.clone();
    F1.insert(5);
    F1.insert(4);
    System.out.println(F1);
    System.out.println();

    System.out.println("Test 23: Should be same set as\n[8,4,10]");
    Set E1 = E.clone();
    E1.insert(10);
    System.out.println(E1);
    System.out.println();

    System.out.println("Test 24: Should be\n[]");
    A1 = A.clone();
    A1.delete(5);
    System.out.println(A1);
    System.out.println();

    System.out.println("Test 25: Should be\n[]");
    Set B1 = B.clone();
    B1.delete(5);
    System.out.println(B1);
    System.out.println();

    System.out.println("Test 26: Should be same set as\n[8,4,10]");
    E1 = E.clone();
    E1.delete(5);
    System.out.println(E1);
    System.out.println();

    System.out.println("Test 27: Should be same set as\n[4,10]");
    E1 = E.clone();
    E1.delete(8);
    System.out.println(E1);
    System.out.println();

    System.out.println("Test 28: Should be same set as\n[3,4]");
    System.out.println(C.intersection(D));
    System.out.println();

    System.out.println("Test 29: Should be\n[8,4,10]");
    System.out.println(E.intersection(F));
    System.out.println();

    System.out.println("Test 30: Should be same set as\n[]");
    System.out.println(A.intersection(F));
    System.out.println();

    System.out.println("Test 31: Should be same set as\n[]");
    System.out.println(B.intersection(F));
    System.out.println();

    System.out.println("Test 32: Should be same set as\n[4,3,-3,10,8,5,7,1]");
    System.out.println(C.union(D));
    System.out.println();

    System.out.println("Test 33: Should be same set as\n[10,8,4]");
    System.out.println(E.union(F));
    System.out.println();

    System.out.println("Test 34: Should be same set as\n[10,8,4]");
    System.out.println(A.union(F));
    System.out.println();

    System.out.println("Test 35: Should be same set as\n[5,3,7,4,1]");
    System.out.println(C.union(B));
    System.out.println();

    System.out.println("Test 36: Should be same set as\n[5,7,1]");
    System.out.println(C.setdifference(D));
    System.out.println();

    System.out.println("Test 37: Should be same set as\n[]");
    System.out.println(E.setdifference(F));
    System.out.println();

    System.out.println("Test 38: Should be same set as\n[5,3,7,4,1]");
    System.out.println(C.setdifference(A));
    System.out.println();

    System.out.println("Test 39: Should be same set as\n[]");
    System.out.println(C.setdifference(C));
    System.out.println();

    System.out.println(
        "Test 40: Should be same set as\n[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31]");
    Set G = new Set();
    for (int i = 0; i < 32; ++i) {
      G.insert(i);
    }
    System.out.println(G);
    System.out.println();


  }
}
