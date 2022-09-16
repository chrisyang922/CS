package hw5;

// **********************************************************

// Assignment5: RPNCalculator

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

public class RPNCalculator<T extends CalculatorOperand<T>> {


  ListStack<T> stack; // the stack holding the operands

  static public enum OperationType {
    ADD, SUBTRACT, MULTIPLY
  };

  public RPNCalculator() {
    stack = new ListStack<T>();
  }

  public void operand(T value) {
    stack.push(value);
  }

  public void operation(OperationType operation) {
    if (stack.isEmpty()) {
      return;
    }
    T value = stack.pop();
    if (stack.isEmpty()) {
      stack.push(value);
      return;
    }
    T value2 = stack.pop();
    T value3 = value;

    switch (operation) {
      case ADD:
        value3 = value.add(value2);
        break;
      case SUBTRACT:
        value3 = value2.subtract(value);
        break;
      case MULTIPLY:
        value3 = value.multiply(value2);
        break;
    }
    stack.push(value3);
  }

  public String toString() {
    if (stack.isEmpty()) {
      return "Empty stack\n";
    } else {
      return stack.toString();
    }
  }
}
