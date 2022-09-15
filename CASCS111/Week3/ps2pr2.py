# 
# ps2pr2.py - Problem Set 2, Problem 2
#
# Writing non-recursive functions
#
# This is an individual-only problem that you must complete on your own.
# 


# function 1
def move_to_end(s,n):
    """ returns a string with the first n characters of s moved
    to the end of string
    input s: any string of characters except empty string
    input n: any non-negative integer or 0
    """
    length_of_string = len(s)
    if n > length_of_string:
        return s
    else:
        string_new = s[n:] + s[:n]
        return string_new


# function 2
def repeat_elem(values, index, num_times):
    """ returns a list with the element of the value at position
    index(parameter variable) repeated num_times(parameter variable) times
    input values: any list 
    input index: any non-negative integer or 0
    input num_times: any non-negative integer or 0
    """
    the_index = values[index:index + 1]
    new_values = values[:index] + the_index * num_times + values[index+1:]
    return new_values

# test function with a sample test call 
def test():
    """ performs test calls on the functions above """
    print('move_to_end("computer", 3) returns', move_to_end('computer', 3))
    print('move_to_end("computer", 5) returns', move_to_end('computer', 5))
    print('move_to_end("computer", 0) returns', move_to_end('computer', 0))
    print('move_to_end("computer", 8) returns', move_to_end('computer', 8))
    print('move_to_end("computer", 9) returns', move_to_end('computer', 9))
    print('repeat_elem([10,11,12,13], 2, 4) returns', repeat_elem([10,11,12,13], 2, 4))
    print('repeat_elem([10,11,12,13], 2, 6) returns', repeat_elem([10,11,12,13], 2, 6))
    print('repeat_elem([5,6,7], 1, 3) returns', repeat_elem([5,6,7],1,3))
    print('repeat_elem([10,11,12,13], 5, 4) returns', repeat_elem([10,11,12,13], 5, 4))
    print('repeat_elem([10,11,12,13], 2, 0) returns', repeat_elem([10,11,12,13], 2, 0))
    


    # optional but encouraged: add test calls for your functions below
