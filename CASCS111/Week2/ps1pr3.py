# 
# ps1pr3.py - Problem Set 1, Problem 3
#
# Functions with numeric inputs
#
# name:  Jeongyong(Chris) Yang
# email: chrisyan@bu.edu
#
# If you worked with a partner, put his or her contact info below:
# partner's name:
# partner's email:
#

# function 0
def opposite(x):
    """ returns the opposite of its input
        input x: any number (int or float)
    """
    return -1*x

# put your definitions for the remaining functions below

# function 1
def root(x):
    """ returns the square root of the input
        input x: any positive number (int or float) or 0
    """
    return x ** (1/2)

# function 2
def gap(num1, num2):
    """ returns the difference between two numbers
        input num1, num2: any number (int or float)
    """
    value = num1 - num2
    if value > 0:
        return value
    elif value < 0:
        return value * -1
    else:
        return 0

# function 3
def larger_gap(a1, a2, b1, b2):
    """ returns the larger gap between (a1 and a2), and (b1 and b2)
        input a1, a2, b1, b2: any number (int or float)
    """
    gap_one = gap(a1, a2)
    gap_two = gap(b1, b2)
    difference_between_gaps = gap_one - gap_two
    if difference_between_gaps >= 0:
        return gap_one
    else: 
        return gap_two

# function 4
def distance(x1, y1, x2, y2):
    """ returns the distance between the coordinates (x1, y1) and (x2, y2)
        input x1, y1, x2, y2: any number (int or float)
    """
    distance_of_x = x2 - x1
    distance_of_y = y2 - y1
    distance_squared = (distance_of_x) ** 2 + (distance_of_y) ** 2
    sqrt_of_distance = root(distance_squared)
    return sqrt_of_distance
    
    




# test function with a sample test call for function 0
def test():
    """ performs test calls on the functions above """
    print('opposite(-8) returns', opposite(-8))
    print('root(36) returns', root(36))
    print('gap(5, 3) returns', gap(5, 3))
    print('gap(5, 9) returns', gap(5, 9))
    print('gap(5, 5) returns', gap(5, 5))
    print('larger_gap(3, 2, 5, 9) returns', larger_gap(3, 2, 5, 9))
    print('larger_gap(3, 9, 7, 9) returns', larger_gap(3, 9, 7, 9))
    print('larger_gap(3, 3, 5, 5) returns', larger_gap(3, 3, 5, 5))
    print('larger_gap(3, 5, 6, 4) returns', larger_gap(3, 5, 6, 4))
    print('distance(2, 3, 5, 7) returns', distance(2, 3, 5, 7))
    print('distance(2, 2, 2, 2) returns', distance(2, 2, 2, 2))
    print('distance(0, 0, 5, 12) returns', distance(0, 0, 5, 12))
    print('distance(1, -1, 0, 0) returns', distance(1, -1, 0, 0))


    # optional but encouraged: add test calls for your functions below
