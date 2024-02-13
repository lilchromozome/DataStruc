# Discussion

**Document all error conditions you determined and why they are error
 conditions. Do this by including the inputs that you used to test your
  program and what error conditions they exposed:**

1. Error Condition: Not enough operands (less than 2) for operators (*, /, +, -, and %)

All core logic operators (i.e. *, /, +, -, and %) require two operands to execute succesfully. .

For example, if the input 1 - is entered by the user, then an EmptyException error is thrown. To avoid program termination,
I handled this by catching the EmptyException and printing "ERROR: Too few operands present in the stack. Please add more".

Another example input (more complex input):

Input: 
1 2 3 5 7 9
?
- - - - - - 
?
Output:
[1, 2, 3, 5, 7, 9]
ERROR: Too few operands present in the stack. Please add more
[-5]

Last example (empty stack)
Input: + - 
Output:
ERROR: Too few operands present in the stack. Please add more
ERROR: Too few operands present in the stack. Please add more

2. Error Condition: Empty Stack for . special command 

For the . special command, it will throw an EmptyException and terminate the program if performed on 
an empty stack. The . special command needs at least 1 element in the stack. As a result
to circumvent this, I handle this by catching the Empty Exception and printing out
Error: No Current Elements in Stack.

Input: . ?
Output:
Error: No Current Elements in Stack
[]

3. Error Condition: Divide by zero (division operator)

Even if there are two operands (a divisor and a dividend), the division operator requires that 
the dividend can't be zero. Otherwise, an ArithmeticException will be thrown because of the x / 0 command (where x is any integer).
To circumvent program termination from this exception, I handle it be printing ERROR: Divide By Zero (Second operand can't be 0).

Example input: 
1 0 0 0
?
/ / / /
?
Output:
[1, 0, 0, 0]
ERROR: Divide By Zero (Second operand can't be 0)
ERROR: Divide By Zero (Second operand can't be 0)
ERROR: Divide By Zero (Second operand can't be 0)
ERROR: Divide By Zero (Second operand can't be 0)
[1, 0, 0, 0]

4. Error Condition: Divide by zero (modulus operator) 

Even if there are two operands for a modulus operator, x mod 0 is not defined mathematically and will throw
an ArithmeticException because of the x % 0 command (where x is any integer). To circumvent program termination from this exception, 
I handle it be printing ERROR: Divide By Zero (Second operand can't be 0).
Input: 1 0 0 0 0
?
% % % % %
?
Output:
[1, 0, 0, 0, 0]
ERROR: Divide By Zero (Second operand can't be 0)
ERROR: Divide By Zero (Second operand can't be 0)
ERROR: Divide By Zero (Second operand can't be 0)
ERROR: Divide By Zero (Second operand can't be 0)
ERROR: Divide By Zero (Second operand can't be 0)
[1, 0, 0, 0, 0]

5. Error Condition: Improper Token Input

The only accepted inputs for the program are any integer inputs,
and particular string inputs (i.e. "?", ".", "!", "*", "/", "%", "-", "+"). Any other inputs
like floats, other strings, or anything else isn't accepted by the program and will be handled
by printing ERROR: bad token for each invalid user input.

Example input: 
?
1132ads 123uu 123iu @ # $
?
Output:
[]
ERROR: bad token
ERROR: bad token
ERROR: bad token
ERROR: bad token
ERROR: bad token
ERROR: bad token
[]