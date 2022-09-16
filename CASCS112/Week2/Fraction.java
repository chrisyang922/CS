package hw2;

// **********************************************************

// Assignment2:

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
public class Fraction {
  private int numerator;
  private int denominator;

  public Fraction() {
    numerator = 0;
    denominator = 1;
  }


  public Fraction(int initialNumerator, int initialDenominator) {
    numerator = initialNumerator;
    if (initialDenominator == 0) {
      denominator = 1;
    } else {
      denominator = initialDenominator;
    }
    adjustSigns();
    reduceToLowestTerms();



  } // end constructor



  public int getNumerator() {
    return numerator;


  } // end getNumerator


  public int getDenominator() {
    return denominator;


  } // end getDenominator



  /*
   * The add method must return back a new fraction which is represented as the addition of the two
   * fractions i.e., this AND operand. Here is how you add two fractions: a/b + c/d is (ad +
   * cb)/(bd)
   * 
   */
  public Fraction add(Fraction operand) {
    int newNumerator = this.numerator * operand.denominator + this.denominator * operand.numerator;
    int newDenominator = this.denominator * operand.denominator;
    Fraction newFraction = new Fraction(newNumerator, newDenominator);
    adjustSigns();
    reduceToLowestTerms();
    return newFraction;

  } // end add


  /*
   * The subtract method must return back a new fraction which is represented as the subtraction of
   * the two fractions i.e., this AND operand. Here is how you add two fractions: a/b - c/d is (ad -
   * cb)/(bd)
   * 
   */
  public Fraction subtract(Fraction operand) {
    int newNumerator = this.numerator * operand.denominator - this.denominator * operand.numerator;
    int newDenominator = this.denominator * operand.denominator;
    Fraction newFraction = new Fraction(newNumerator, newDenominator);
    adjustSigns();
    reduceToLowestTerms();
    return newFraction;

  } // end subtract


  /*
   * The multiply method must return back a new fraction which is represented as the subtraction of
   * the two fractions i.e., this AND multiplier. Here is how you add two fractions: a/b * c/d is
   * (ac)/(bd)
   * 
   */
  public Fraction multiply(Fraction multiplier) {
    int newNumerator = this.numerator * multiplier.numerator;
    int newDenominator = this.denominator * multiplier.denominator;
    Fraction newFraction = new Fraction(newNumerator, newDenominator);
    adjustSigns();
    reduceToLowestTerms();
    return newFraction;


  } // end multiply


  /*
   * The divide method must return back a new fraction which is represented as the subtraction of
   * the two fractions i.e., this AND divisor. Here is how you add two fractions: (a/b) / (c/d) is
   * (ad)/(bc) The divide method must make use of the getReciprocal on the divisor and the multiply
   * method.
   * 
   */
  public Fraction divide(Fraction divisor) {
    int newNumerator = this.numerator * divisor.denominator;
    int newDenominator = this.denominator * divisor.numerator;
    Fraction newFraction = new Fraction(newNumerator, newDenominator);
    adjustSigns();
    reduceToLowestTerms();
    return newFraction;


  } // end divide


  public Fraction getReciprocal() {
    Fraction newFraction = new Fraction(denominator, numerator);
    adjustSigns();
    reduceToLowestTerms();
    return newFraction;


  } // end getReciprocal

  /*
   * If fraction this>other then return back +1 if fraction this==other then return back 0 if
   * fraction this<other then return back -1
   */

  public int compareTo(Fraction other) {
    if (this.subtract(other).getNumerator() > 0) {
      return 1;
    } else if (this.subtract(other).getNumerator() < 0) {
      return -1;
    } else {
      return 0;
    }


  } // end compareTo



  public String toString() {
    return numerator + "/" + denominator;
  } // end toString


  private void reduceToLowestTerms() {
    int gcd = greatestCommonDivisor(Math.abs(numerator), Math.abs(denominator));

    numerator = numerator / gcd;
    denominator = denominator / gcd;
  } // end reduceToLowestTerms


  private int greatestCommonDivisor(int integerOne, int integerTwo) {
    int result;

    if (integerOne % integerTwo == 0)
      result = integerTwo;
    else
      result = greatestCommonDivisor(integerTwo, integerOne % integerTwo);

    return result;
  } // end greatestCommonDivisor



  /*
   * This method takes in a character i.e., `-' and it simply sets it. So for example, if you have a
   * fraction 3/2, and by calling setSign by passing in `-', it would simply make it -3/2. If the
   * fraction is -3/2, and you call the setSign by passing in `-', then the fraction continues to
   * remain -3/2. If you have -3/2, and pass in `+', then the fraction becomes 3/2. This method is
   * called from adjustSigns().
   */
  public void setSign(char sign) {
    numerator = Math.abs(numerator);
    denominator = Math.abs(denominator);

    if (sign == '-')
      numerator = -numerator;
  } // end setSign


  /*
   * Adjusts the signs of the numerator and denominator so that the numerator's sign is the sign of
   * the fraction and the denominator's sign is +. You will call the method adjustSigns() from the
   * Fraction constructor.
   */

  private void adjustSigns() {
    if (this.denominator < 0 && this.numerator > 0) {
      this.denominator *= -1;
      setSign('-');
    } else if (this.denominator < 0 && this.numerator < 0) {
      this.denominator *= -1;
      setSign('+');
    }


  } // end adjustSigns



  public static void main(String[] args) {

    System.out.println("----------TESTING CONSTRUCTORS--------------");
    Fraction n1 = new Fraction(1, 2);
    System.out.println(n1); // MUST PRINT BACK 1/2

    Fraction n2 = new Fraction(4, 8);
    System.out.println(n2); // MUST PRINT BACK 1/2

    Fraction n3 = new Fraction(20, 0);
    System.out.println(n3); // MUST PRINT BACK 20/1

    Fraction n4 = new Fraction(10, 100);
    System.out.println(n4); // MUST PRINT BACK 1/10

    Fraction n5 = new Fraction(7, 8);
    System.out.println(n5); // MUST PRINT BACK 7/8

    System.out.println("----------TESTING GET DENOMINATOR--------------");
    System.out.println(n1.getDenominator()); // MUST PRINT BACK 2
    System.out.println(n2.getDenominator()); // MUST PRINT BACK 2
    System.out.println(n3.getDenominator()); // MUST PRINT BACK 1
    System.out.println(n4.getDenominator()); // MUST PRINT BACK 10
    System.out.println(n5.getDenominator()); // MUST PRINT BACK 8
    System.out.println("-------TESTING GET NUMERATOR-----------------");

    System.out.println(n1.getNumerator()); // MUST PRINT BACK 1
    System.out.println(n2.getNumerator()); // MUST PRINT BACK 1
    System.out.println(n3.getNumerator()); // MUST PRINT BACK 20
    System.out.println(n4.getNumerator()); // MUST PRINT BACK 1
    System.out.println(n5.getNumerator()); // MUST PRINT BACK 7

    System.out.println("---------TESTING MORE CONSTRUCTORS---------------");

    Fraction n6 = new Fraction(-7, 8);
    System.out.println(n6); // MUST PRINT BACK -7/8

    Fraction n7 = new Fraction(-7, -8);
    System.out.println(n7); // MUST PRINT BACK 7/8

    Fraction n8 = new Fraction(7, -8);
    System.out.println(n8); // MUST PRINT BACK -7/8

    System.out.println("------------TESTING ADDING------------");

    Fraction n9 = n1.add(n2); // adding 1/2 + 1/2
    System.out.println(n9); // MUST PRINT BACK 1/1

    Fraction n10 = n3.add(n4); // adding 20/1 + 1/10
    System.out.println(n10); // MUST PRINT BACK 201/10

    Fraction n11 = n6.add(n8);
    System.out.println(n11); // MUST PRINT BACK -7/4

    System.out.println("--------TESTING SUBTRACTION----------------");
    Fraction n12 = n1.subtract(n2);
    System.out.println(n12); // MUST PRINT BACK 0/1

    Fraction n13 = n3.subtract(n4);
    System.out.println(n13); // MUST PRINT BACK 199/10

    Fraction n14 = n6.subtract(n8);
    System.out.println(n14); // MUST PRINT BACK 0/1


    System.out.println("---------TESTING MULTIPLY---------------");
    Fraction n15 = n1.multiply(n2);
    System.out.println(n15); // MUST PRINT BACK 1/4

    Fraction n16 = n3.multiply(n4);
    System.out.println(n16); // MUST PRINT BACK 2/1

    Fraction n17 = n6.multiply(n8);
    System.out.println(n17); // MUST PRINT BACK 49/64

    System.out.println("---------TESTING GET RECIPROCAL---------------");


    System.out.println(n1.getReciprocal()); // MUST PRINT BACK 2/1
    System.out.println(n1); // MUST PRINT BACK 1/2

    System.out.println(n2.getReciprocal()); // MUST PRINT BACK 2/1
    System.out.println(n2); // MUST PRINT BACK 1/2

    System.out.println(n3.getReciprocal()); // MUST PRINT BACK 1/20
    System.out.println(n3); // MUST PRINT BACK 20/1


    System.out.println(n4.getReciprocal()); // MUST PRINT BACK 10/1
    System.out.println(n4); // MUST PRINT BACK 1/10

    System.out.println(n5.getReciprocal()); // MUST PRINT BACK 8/7
    System.out.println(n5); // MUST PRINT BACK 7/8

    System.out.println("---------TESTING DIVIDE---------------");
    Fraction n18 = n1.divide(n2);
    System.out.println(n18); // MUST PRINT BACK 1/1

    Fraction n19 = n3.divide(n4);
    System.out.println(n19); // MUST PRINT BACK 200/1

    Fraction n20 = n6.divide(n8);
    System.out.println(n20); // MUST PRINT BACK 1/1


    System.out.println("---------TESTING COMPARE TO---------------");
    System.out.println(n1.compareTo(n2)); // MUST PRINT BACK 0
    System.out.println(n3.compareTo(n4)); // MUST PRINT BACK 1
    System.out.println(n6.compareTo(n8)); // MUST PRINT BACK 0
    System.out.println(n4.compareTo(n3)); // MUST PRINT BACK -1
    System.out.println(n8.compareTo(n6)); // MUST PRINT BACK 0
  }

}
