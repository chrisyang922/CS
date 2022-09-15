#
# ps4pr1.py - Problem Set 4, Problem 1
#
# From binary to decimal and back!
#
# name:  Jeongyong(Chris) Yang
# email: chrisyan@bu.edu
#
# If you worked with a partner, put his or her contact info below:
# partner's name:
# partner's email:
#


# function 1
def dec_to_bin(n):
    """ returns the value of integer n (parameter variable)
    converted into binary number
    input n: any positive integer or 0
    """
    if n == 1:
        return '1'
    elif n == 0:
        return '0'
    else:
        rem_bin = dec_to_bin(n // 2)
        if (n % 2 == 1):
            new = n * 2 + 1
        else:
            new = n * 2

        if new % 2 == 1:
            return rem_bin + '1'
        else:
            return rem_bin + '0'


# function 2
def bin_to_dec(b):
    """ returns the value of the binary number b (parameter varible)
    converted into a positive integer
    input b: any binary number (string consisted of 0s and 1s)
    """
    if b == '1':
        return 1
    elif b == '0':
        return 0
    else:
        rem_dec = bin_to_dec(b[:-1])
        if b[-1] == '0':
            return rem_dec * 2
        else:
            return rem_dec * 2 + 1


# test function with a sample test call
def test():
    """ performs test calls on the functions above """
    print("dec_to_bin(5) returns", dec_to_bin(5))
    print("dec_to_bin(12) returns", dec_to_bin(12))
    print("dec_to_bin(0) returns", dec_to_bin(0))
    print("dec_to_bin(1) returns", dec_to_bin(1))
    print("dec_to_bin(4) returns", dec_to_bin(4))
    print("dec_to_bin(7) returns", dec_to_bin(7))
    print("dec_to_bin(10) returns", dec_to_bin(10))
    print("dec_to_bin(111) returns", dec_to_bin(111))
    print("bin_to_dec('101') returns", bin_to_dec('101'))
    print("bin_to_dec('1100') returns", bin_to_dec('1100'))
    print("bin_to_dec('0') returns", bin_to_dec('0'))
    print("bin_to_dec('1') returns", bin_to_dec('1'))
    print("bin_to_dec('100') returns", bin_to_dec('100'))
    print("bin_to_dec('111') returns", bin_to_dec('111'))
    print("bin_to_dec('1110') returns", bin_to_dec('1110'))
    print("bin_to_dec('00011010') returns", bin_to_dec('00011010'))
    print("bin_to_dec('1111111') returns", bin_to_dec('1111111'))




    # optional but encouraged: add test calls for your functions below
