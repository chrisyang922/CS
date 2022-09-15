#
# ps4pr3.py - Problem Set 4, Problem 3
#
# Recursive operations on binary numbers
#
# name:  Jeongyong(Chris) Yang
# email: chrisyan@bu.edu


# function 1
def bitwise_and(b1, b2):
    """ returns the bitwise AND of the two binary number b1 and b2 (parameter variables)
    in the form of a string
    input b1 and b2: any binary numbers in strings (String consisted of 1s and 0s)
    """
    if b1 == '' and b2 == '':
        return ''
    elif b1 == '' or b2 == '':
        if len(b1) != 0:
            return '0' * len(b1)
        else:
            return '0' * len(b2)
    else:
        bit_rest = bitwise_and(b1[:-1], b2[:-1])
        if b1[-1] == '1' and b2[-1] == '1':
            return bit_rest + '1'
        else:
            return bit_rest + '0'


# function 2


def add_bitwise(b1, b2):
    """ returns the addition of two binary numbers b1 and b2 (parameter variables)
    in the form of a string
    input b1 and b2: any binary numbers in strings (String consisted of 1s and 0s)
    """
    if b1 == '' and b2 == '':
        return ''
    elif b1 == '' or b2 == '':
        if len(b1) != 0:
            return b1
        else:
            return b2
    else:
        bit_rest = add_bitwise(b1[:-1], b2[:-1])
        if b1[-1] == '1' and b2[-1] == '1':
            bit_rest = bit_rest + '0'
            bit_rest_one = add_bitwise(bit_rest, '10')
            return bit_rest_one

        elif b1[-1] == '0' and b2[-1] == '1':
            return bit_rest + '1'
        elif b1[-1] == '1' and b2[-1] == '0':
            return bit_rest + '1'
        else:
            return bit_rest + '0'


# test function with a sample test call
def test():
    print("bitwise_and('11010', '10011') returns", bitwise_and('11010', '10011'))
    print("bitwise_and('1001111', '11011') returns", bitwise_and('1001111', '11011'))
    print("bitwise_and('', '') returns", bitwise_and('', ''))
    print("bitwise_and('101', '') returns", bitwise_and('101', ''))
    print("bitwise_and('', '11010') returns", bitwise_and('', '11010'))
    print("add_bitwise('11100', '11110') returns", add_bitwise('11100', '11110'))
    print("add_bitwise('10101', '10101') returns", add_bitwise('10101', '10101'))
    print("add_bitwise('11111', '11111') returns", add_bitwise('11111', '11111'))
    print("add_bitwise('11', '1') returns", add_bitwise('11', '1'))
    print("add_bitwise('11', '100') returns", add_bitwise('11', '100'))
    print("add_bitwise('11', '') returns", add_bitwise('11', ''))
    print("add_bitwise('', '101') returns", add_bitwise('', '101'))
    print("add_bitwise('011', '101') returns", add_bitwise('011', '101'))

    # optional but encouraged: add test calls for your functions below
