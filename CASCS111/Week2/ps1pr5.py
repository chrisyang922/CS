# 
# ps1pr5.py - Problem Set 1, Problem 5
#
# Functions on strings and lists, part 1
#
# name:  Jeongyong(Chris) Yang
# email: chrisyan@bu.edu
#

# function 1
def outer_vals(values):
    """ returns the first and last element of its input 
        input values: any list (can contain any value)
    """
    first = values[0] 
    last = values[-1]
    return [first, last]


# function 2
def ends_match(s):
    """ returns whether the first and last element of its input
        are same characters or not
        input s: any string of characters except empty string 
    """
    first = s[0] 
    last = s[-1]
    if first == last:
        return True
    else:
        return False

# function 3
def every_other(values):
    """ returns every other value from the input
        input values: any list (can contain any value)
    """
    new_list = values[::2]
    return new_list




