#
# ps3pr4.py - Problem Set 3, Problem 4
#
# More Algorithm design
#
# name:  Jeongyong(Chris) Yang
# email: chrisyan@bu.edu


# function 1
def find(elem, seq):
    """ returns the index of first occurence of elem(parameter variable)
    in seq(parameter variable) or -1 if there is no occurence of elem
    input elem: If the type of seq is string, elem should be a character
    and if the type of seq is list, elem can be any value
    input seq: Any list or string of characters
    """
    if len(seq) == 0:
        return -1
    elif elem == seq[0]:
        return 0
    else:
        rest_seq = find(elem, seq[1:])
        if rest_seq >= 0:
            return rest_seq + 1
        else:
            return rest_seq


# function 2
def rem_first(elem, values):
    """ removes the first occurrence of elem from the list values
    input elem: any character with length one
    values: any string
    """
    if values == '':
        return ''
    elif values[0] == elem:
        return values[1:]
    else:
        result_rest = rem_first(elem, values[1:])

        return values[:1] + result_rest


def jscore(s1, s2):
    """ returns the number of shared characters between s1(parameter variable)
    and s2 (parameter variables)
    input s1, s2: any string
    """
    lc = [1 for x in s1 if x in s2]
    lc2 = [1 for x in s2 if x in s1]
    if lc < lc2:
        sum_value = sum(lc)
        return sum_value
    else:
        sum_value = sum(lc2)
        return sum_value


# function 3
def lcs(s1, s2):
    """ returns the longest common subsequence that is shared by
    s1(parameter variable) and s2(parameter variable)
    input threshold: any number (int or float)
    input values: any list containing numbers (int or float)
    """
    if len(s1) == 0:
        return ''
    elif len(s2) == 0:
        return ''
    else:
        if s1[0] == s2[0]:
            lcs_rest = lcs(s1[1:], s2[1:])
            return s1[0] + lcs_rest
        else:
            lcs_rest_one = lcs(s1[1:], s2)
            lcs_rest_two = lcs(s1, s2[1:])
            if len(lcs_rest_one) > len(lcs_rest_two):
                return lcs_rest_one
            else:
                return lcs_rest_two


# test function with a sample test call
def test():
    print('find(5, [4, 10, 5, 3, 7, 5]) returns', find(5, [4, 10, 5, 3, 7, 5]))
    print("find('hi', ['well', 'hi', 'there']) returns", find('hi', ['well', 'hi', 'there']))
    print("find('hi', ['hi', 'hi', 'hi']) returns", find('hi', ['hi', 'hi', 'hi']))
    print("find('hi', ['3', '5', 8, 'happy', 'hi', 'habit', 'he']) returns",
          find('hi', ['3', '5', 8, 'happy', 'hi', 'habit', 'he']))
    print("find('b', 'banana') returns", find('b', 'banana'))
    print("find('a', 'banana') returns", find('a', 'banana'))
    print("find('i', 'team') returns", find('i', 'team'))
    print("find('hi', ['hello', 111, True]) returns", find('hi', ['hello', 111, True]))
    print("find(True, ['hello', 111, True]) returns", find(True, ['hello', 111, True]))
    print("find('a', '') returns", find('a', ''))
    print("find(42, []) returns", find(42, []))
    print("jscore('diner', 'syrup') returns", jscore('diner', 'syrup'))
    print("jscore('always', 'bananas') returns", jscore('always', 'bananas'))
    print("jscore('always', 'walking') returns", jscore('always', 'walking'))
    print("jscore('recursion', 'excursion')", jscore('recursion', 'excursion'))
    print("jscore('recursion', '') returns", jscore('recursion', ''))
    print("jscore('', 'recursion') returns", jscore('', 'recursion'))
    print("jscore('', '') returns", jscore('', ''))
    print("lcs('human', 'chimp') returns", lcs('human', 'chimp'))
    print("lcs('gattaca', 'tacgaacta') returns", lcs('gattaca', 'tacgaacta'))
    print("lcs('wow', 'whew') returns", lcs('wow', 'whew'))
    print("lcs('', 'whew') returns", lcs('', 'whew'))
    print("lcs('abcdefgh', 'efghabcd') returns", lcs('abcdefgh', 'efghabcd'))

    # optional but encouraged: add test calls for your functions below

