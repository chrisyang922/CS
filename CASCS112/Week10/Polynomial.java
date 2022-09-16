package hw5;

// **********************************************************

// Assignment5: Polynomial

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
public class Polynomial implements CalculatorOperand<Polynomial> {

  private class PolyNode {
    int coeff;
    int degree;
    PolyNode next;

    public PolyNode(int c, int d, PolyNode n) {
      coeff = c;
      degree = d;
      next = n;
    }
  }

  private PolyNode monomialsList;

  Polynomial(int coeff, int degree) {
    monomialsList = new PolyNode(0, 0, new PolyNode(coeff, degree, null));
  }

  public Polynomial addTimesMonomial(Polynomial that, int coeff, int degree) {
    Polynomial newP = null;
    Polynomial answerP = null;
    for (PolyNode y = that.monomialsList.next; y != null; y = y.next) {
      if (newP == null) {
        newP = new Polynomial(coeff * y.coeff, degree + y.degree);
      } else {
        PolyNode one = newP.monomialsList.next;
        PolyNode two = newP.monomialsList;

        while (one != null) {
          two = one;
          one = one.next;
        }
        if (coeff * y.coeff != 0)
          two.next = new PolyNode(coeff * y.coeff, degree + y.degree, null);
      }
    }

    PolyNode current = this.monomialsList.next;
    PolyNode current2 = newP.monomialsList.next;
    answerP = null;
    while (current != null || current2 != null) {
      if (current == null) {
        if (answerP == null) {
          answerP = new Polynomial(current2.coeff, current2.degree);
        } else {
          PolyNode one = answerP.monomialsList.next;
          PolyNode two = answerP.monomialsList;
          while (one != null) {
            two = one;
            one = one.next;
          }
          two.next = new PolyNode(current2.coeff, current2.degree, null);
        }
        current2 = current2.next;
      } else if (current2 == null) {
        if (answerP == null) {
          answerP = new Polynomial(current.coeff, current.degree);
        } else {
          PolyNode one = answerP.monomialsList.next;
          PolyNode two = answerP.monomialsList;
          while (one != null) {
            two = one;
            one = one.next;
          }
          two.next = new PolyNode(current.coeff, current.degree, null);
        }
        current = current.next;
      } else if (current.degree > current2.degree) {
        if (answerP == null) {
          answerP = new Polynomial(current.coeff, current.degree);
        } else {
          PolyNode one = answerP.monomialsList.next;
          PolyNode two = answerP.monomialsList;

          while (one != null) {
            two = one;
            one = one.next;
          }
          two.next = new PolyNode(current.coeff, current.degree, null);
        }
        current = current.next;
      } else if (current.degree < current2.degree) {
        if (answerP == null) {
          answerP = new Polynomial(current2.coeff, current2.degree);
        } else {
          PolyNode one = answerP.monomialsList.next;
          PolyNode two = answerP.monomialsList;
          while (one != null) {
            two = one;
            one = one.next;
          }
          two.next = new PolyNode(current2.coeff, current2.degree, null);
        }
        current2 = current2.next;
      } else {
        if (answerP == null) {
          answerP = new Polynomial(current.coeff + current2.coeff, current.degree);
        } else {
          PolyNode one = answerP.monomialsList.next;
          PolyNode two = answerP.monomialsList;

          while (one != null) {
            two = one;
            one = one.next;
          }
          if (current.coeff + current2.coeff != 0) {
            two.next = new PolyNode(current.coeff + current2.coeff, current.degree, null);
          }
        }
        current2 = current2.next;
        current = current.next;
      }
    }
    if (answerP.monomialsList.next.coeff == 0) {
      answerP = new Polynomial(0, 0);
    }
    return answerP;
  }

  public Polynomial add(Polynomial that) {
    return this.addTimesMonomial(that, 1, 0);
  }

  public Polynomial subtract(Polynomial that) {
    return this.addTimesMonomial(that, -1, 0);
  }


  public Polynomial multiply(Polynomial that) {
    Polynomial newP = new Polynomial(0, 0);
    for (PolyNode x = this.monomialsList.next; x != null; x = x.next) {
      newP = newP.addTimesMonomial(that, x.coeff, x.degree);
    }
    newP = newP.subtract(new Polynomial(0, 0));

    return newP;


  }

  /**
   * Prints the polynomial the way a human would like to read it
   * 
   * @return the human-readable string representation
   */
  public String toString() {
    if (monomialsList.next == null)
      return "0";

    String ret = monomialsList.next.coeff < 0 ? "-" : "";
    for (PolyNode p = monomialsList.next; p != null; p = p.next) {
      if (p.degree == 0 || (p.coeff != 1 && p.coeff != -1))
        ret = ret + java.lang.Math.abs(p.coeff);
      if (p.degree > 0)
        ret = ret + "x";
      if (p.degree > 1)
        ret = ret + "^" + p.degree;
      if (p.next != null)
        ret = ret + (p.next.coeff < 0 ? " - " : " + ");
    }
    return ret;
  }

}
