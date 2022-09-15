# 
# ps1pr6.py - Problem Set 1, Problem 6
#
# Functions on strings and lists, part 2
#
# name:  Jeongyong(Chris) Yang
# email: chrisyan@bu.edu
#

# function 1
def reverse(s):
    """ returns the reversed order of characters of the input
        input s: any string of characters
    """
    reversed_string = s[::-1]
    return reversed_string

# function 2
def begins_with(word, prefix):
    """ returns whether the string word begins with the string prefix
        input word, prefix: any string of characters 
    """
    length = len(prefix)
    if word[:length] == prefix:
        return True
    else:
        return False

# function 3
def flipside(s):
    """ returns the string with its first half of s becoming s's second
        half and second half of s becoming s's first half
        input s: any string of characters
    """
    length_half = len(s)//2
    new_string = s[length_half:] + s[:length_half]
    return new_string

# function 4
def adjust(s, length):
    """ returns the string in which the value of s is adjusted to fit
        the value of length
        input s: any string of characters
        input length: any integer
    """
    length_string = len(s)
    if length >= length_string:
        difference = length - length_string
        new_string = " " * difference + s
        return new_string
    else:
        new_string = s[:length]
        return new_string
        

