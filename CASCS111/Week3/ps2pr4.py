# 
# ps2pr4.py - Problem Set 2, Problem 4
#
# Functions using Recursion Part I
#
# name:  Jeongyong(Chris) Yang
# email: chrisyan@bu.edu
#
# If you worked with a partner, put his or her contact info below:
# partner's name:
# partner's email:
#

# function 1
def mult(n,m):
    """ returns the product of the inputs n and m
        input n, m: any positive integers
    """
    if n == 1:
        return m
    else:
        rest_mult = mult(n-1, m)
        return rest_mult + m

# function 2
def dot(l1,l2):
    """ returns the dot product of l1 and l2 or
        0.0 if the length of l1 and l2 are different
        input l1,l2: list consisted of any numbers (both int and float)
    """
    length_of_one = len(l1)
    length_of_two = len(l2)
    if length_of_one == length_of_two:
        if len(l1) == 0:
            return 0.0
        else:
            rest_list = dot(l1[1:], l2[1:])
            return rest_list + l1[0] * l2[0]
    else:
        return 0.0
    
#function 3
def add_spaces(s):
    """ returns the string that adds a space between the adjacent characters
        in the input string s
        input s: any string
    """
    if len(s) == 1:
        return s
    else:
        rest_str = add_spaces(s[1:])
        return s[0] + " " + rest_str



# test function 
def test():
    """ performs test calls on the functions above """
    print('mult(6,7) returns', mult(6,7))
    print('mult(3,6) returns', mult(3,6))
    print('dot([5, 3], [6, 4])  returns', dot([5, 3], [6, 4]))
    print('dot([1, 2, 3, 4], [10, 100, 1000, 10000])  returns', dot([1, 2, 3, 4], [10, 100, 1000, 10000]))
    print('dot([5, 3], [6])  returns', dot([5, 3], [6]))
    print('add_spaces("hello") returns', add_spaces('hello'))
    print('add_spaces("hangman") returns', add_spaces('hangman'))
    print('add_spaces("x") returns', add_spaces('x'))
    
