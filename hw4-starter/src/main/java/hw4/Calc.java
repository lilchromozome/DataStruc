package hw4;

import java.util.Objects;
import java.util.Scanner;

/**
 * A program for an RPN calculator that uses a stack.
 */
public final class Calc {

  /**
   * Checks to see if stack has sufficient number of Operands by checking emptiness.
   * @param stack the stack to search through
   * @return boolean of if there are more elements in the stack or not
   */
  private static boolean sufficientOperands(LinkedStack<Integer> stack) {
    if (!stack.empty()) {
      return true;
    } else {
      System.out.println("ERROR: Too few operands present in the stack. Please add more");
      return false;
    }
  }

  /**
   * Operator logic for all basic arithmetic operators.
   * The operators and operands are required to all be valid
   * @param stack the stack to search through
   * @param first first operand
   * @param second second operand
   * @param operator inputted operator by the user
   */
  private static void operationLogic(int first, int second, String operator, LinkedStack<Integer> stack) {
    switch (operator) {
      case ("*"): stack.push(first * second);
      break;
      case ("/"): stack.push(first / second);
        break;
      case ("+"): stack.push(first + second);
        break;
      case ("-"): stack.push(first - second);
        break;
      //default case is for modulus operator
      default: stack.push(first % second);
    }
  }

  /**
   * Checks to see if operator is valid.
   * @param operator the user inputs
   * @return boolean of whether operator is valid or not
   */
  private static boolean isValidOperator(String operator) {
    String[] approvedOperators = new String[] {"-", "+", "*", "%", "/"};
    //checks to see if operator is a valid operator
    for (String approvedOperator: approvedOperators) {
      if (Objects.equals(operator, approvedOperator)) {
        return true;
      }
    }
    System.out.println("ERROR: bad token");
    return false;
  }

  /**
   * Implements Operation Logic after checking if stack is valid (i.e there are enough elements, error checking, etc).
   * @param stack the stack to search through
   * @param operator the operator the user inputs
   */
  private static void coreOperations(LinkedStack<Integer> stack, String operator) {
    if (isValidOperator(operator) && sufficientOperands(stack)) {
      int secondOperand = stack.top();
      //prevents exceptions from / and % operations
      if (secondOperand != 0 || (!Objects.equals("/", operator) && !Objects.equals("%", operator))) {
        stack.pop();
      } else {
        System.out.println("ERROR: Divide By Zero (Second operand can't be 0)!");
        return;
      }
      if (sufficientOperands(stack)) {
        int firstOperand = stack.top();
        stack.pop();
        operationLogic(firstOperand, secondOperand, operator, stack);
      } else {
        //adds element back onto stack if operations isn't feasible
        stack.push(secondOperand);
      }
    }
  }

  /**
   * Implements special operators and core operators.
   * @param stack the stack to search through
   * @param operator the operator the user wants to use
   */
  private static void allOperations(LinkedStack<Integer> stack, String operator) {
    switch (operator) {
      case ("?"):
        System.out.println(stack.toString());
        break;
      case ("."):
        //avoid empty exception error
        if (stack.empty()) {
          System.out.println("Error: No Current Elements in Stack");
        } else {
          System.out.println(stack.top());
        }
        break;
      default:
        coreOperations(stack, operator);
    }
  }

  /**
   * The main function.
   * @param args Not used.
   */
  public static void main(String[] args) {
    LinkedStack<Integer> stack = new LinkedStack<>();
    Scanner scanner = new Scanner(System.in);
    //Single while loop keeps O(N) complexity
    while (scanner.hasNext()) {
      String currToken = scanner.next();
      if (!Objects.equals("!", currToken)) {
        //add integer to stack or execute operator logic/catch bad tokens if not an integer
        try {
          Integer token = Integer.parseInt(currToken);
          stack.push(token);
        } catch (NumberFormatException e) {
          allOperations(stack, currToken);
        }
      } else {
        return;
      }
    }
  }
}
