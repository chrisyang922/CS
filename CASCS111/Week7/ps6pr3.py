#
# ps6pr3.py - Problem Set 6, Problem 3
#
# name:  Jeongyong(Chris) Yang
# email: chrisyan@bu.edu
#
# If you worked with a partner, put his or her contact info below:
# partner's name: Sae Mi Jung
# partner's email: jsaemi@bu.edu
#


# function 1
def add_spaces(s):
    """
    return the string formed by adding a space between each pair of adjacent
    characters in the string
    input s: any string except an empty string
    """
    result = ''
    count = 0

    for x in s:
        count += 1
        if (count == len(s)):
            result += x
        else:
            result += x + ' '

    return result


# function 2
def num_diff(s1, s2):
    """ return the number of differences between the two strings
    """
    result = 0
    len_shorter = min(len(s1), len(s2))

    for i in range(len_shorter):
        if s1[i] != s2[i]:
            result += 1
    if len(s1) > len(s2):
        result += len(s1) - len(s2)
    else:
        result += len(s2) - len(s1)
    return result

# function 3
def negate_odds(values):
    """ modifies the list so that all of its odd-valued elements are
    replaced with their negated values
    """
    result = []
    for i in range(len(values)):
        if values[i] % 2 == 1:
            values[i] *= -1
     
        
    

# function 4
def find(elem,seq):
    """ return the index of the first occurrence of elem in seq
    """
    index = -1
    for x in range(len(seq)):
        if elem == seq[x]:
            return x
    return index
    
        


