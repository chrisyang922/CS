#
# ps4pr2.py - Problem Set 4, Problem 2
#
# Using your conversion functions
#
# name:  Jeongyong(Chris) Yang
# email: chrisyan@bu.edu

from ps4pr1 import *
# function 1
def mul_bin(b1,b2):
    """ returns the binary number in strings of the two binary number
    b1 and b2 (parameter variables) multipled to each other
    input b1 and b2: Any binary number(Any string consisted of 1s and 0s)
    """
    first_b = bin_to_dec(b1)
    second_b = bin_to_dec(b2)
    multiply = first_b * second_b
    binary_form = dec_to_bin(multiply)
    return binary_form


# function 2
def add_bytes(b1, b2):
    """ returns the sum of two binary numbers b1 and b2(parameter variables) in
    terms of binary number in the form of string representing 8-bit binary number
    input b1 and b2: Any binary number(Any string consisted of 1s and 0s)
    """
    first_b = bin_to_dec(b1)
    second_b = bin_to_dec(b2)
    add = first_b + second_b
    binary_form = dec_to_bin(add)
    if len(binary_form) == 8:
        return binary_form
    elif len(binary_form) > 8:
        return binary_form[len(binary_form)-8:]
    else:
        x = 8 - len(binary_form)
        return '0' * x + binary_form
    

# test function with a sample test call
def test():
    print("mul_bin('11', '10') returns", mul_bin('11', '10'))
    print("mul_bin('1001', '101') returns", mul_bin('1001', '101'))
    print("add_bytes('00000011', '00000001') returns", add_bytes('00000011', '00000001'))
    print("add_bytes('00011100', '00011110') returns", add_bytes('00011100', '00011110'))
    print("add_bytes('10000011', '11000001') returns", add_bytes('10000011', '11000001'))
    

    # optional but encouraged: add test calls for your functions below

