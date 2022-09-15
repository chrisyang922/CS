# 
# ps3pr2.py - Problem Set 3, Problem 2
#
# Algorithm design
#
# name:  Jeongyong(Chris) Yang
# email: chrisyan@bu.edu
#
# If you worked with a partner, put his or her contact info below:
# partner's name:
# partner's email:
#


# function 1
def cube_all_lc(values):
    """ returns the list of numbers that are cubed from the
    variable values(parameter variable) by using list comprehension
    input values: any list containing numbers (int or float)
    """
    new_list = [x ** 3 for x in values]
    return new_list
    
# function 2
def cube_all_rec(values):
    """ returns the list of numbers that are cubed from the
    variable values(parameter variable) by using recursion
    input values: any list containing numbers (int or float)
    """
    if len(values) == 0:
        return []
    else:
        cube_rest = cube_all_rec(values[1:])
        new_list =  [values[0] ** 3] + cube_rest
        return new_list

# function 3
def num_larger(threshold, values):
    """ returns the number of elements from values(parameter variable)that
    are larger than the variable threshold(parameter variable)
    input threshold: any number (int or float)
    input values: any list containing numbers (int or float)
    """
    new_list = [1 for x in values  if x > threshold]
    sum_of_list = sum(new_list)
    return sum_of_list

# function 4
def num_vowels(s):
    """ returns the number of vowels in the string s
        input: s is a string of 0 or more lowercase letters
    """
    if s == '':
        return 0
    else:
        num_in_rest = num_vowels(s[1:])
        if s[0] in 'aeiou':
            return 1 + num_in_rest
        else:
            return 0 + num_in_rest
        
def most_consonants(words):
    """ returns the string in the list words (parameter variable) that
    has the most consonants
    input words: any list of string consisted with lowercase letters
    """
    new_list = [[len(x) - num_vowels(x), x] for x in words]
    max_number = max(new_list)
    max_number = max_number[1]
    return max_number

# function 5
def price_string(cents):
    """ returns a string which the price from cents(parameter varible)
    is converted to a mixture of dollars and cents
    input cents: any positive integer 
    """
    d = cents // 100  # compute whole number of dollars
    c = cents % 100

    price = ''            # initial value of the price string

    ## add code below to build up the price string

    if d > 1 :
        price = price + str(d) + ' dollars'
        if c >= 1:
            price = price + ', '
    elif d == 1:
        price = price + str(d) + ' dollar'
        if c >= 1:
            price = price + ', '

    if c > 1:
        price = price + str(c) + ' cents'
    elif c ==  1:
        price = price + str(c) + ' cent'

    return price



# test function with a sample test call 
def test():
    """ performs test calls on the functions above """
    print('cube_all_lc([-2, 5, 4, -3]) returns', cube_all_lc([-2, 5, 4, -3]))
    print('cube_all_lc([1, 2, 3]) returns', cube_all_lc([1, 2, 3]))
    print('cube_all_rec([-2, 5, 4, -3]) returns', cube_all_rec([-2, 5, 4, -3]))
    print('cube_all_rec([1, 2, 3]) returns', cube_all_rec([1, 2, 3]))
    print('num_larger(5, [1, 7, 3, 5, 10]) returns', num_larger(5, [1, 7, 3, 5, 10]))
    print('num_larger(2, [1, 7, 3, 5, 10]) returns', num_larger(2, [1, 7, 3, 5, 10]))
    print('num_larger(10, [1, 7, 3, 5, 10]) returns', num_larger(10, [1, 7, 3, 5, 10]))
    print('most_consonants(["python", "is", "such", "fun"] returns', most_consonants(['python', 'is', 'such', 'fun']))
    print('most_consonants(["oooooooh", "i", "see", "now"] returns', most_consonants(['oooooooh', 'i', 'see', 'now']))
    print('price_string(452) returns', price_string(452))
    print('price_string(871) returns', price_string(871))
    print('price_string(27) returns', price_string(27))
    print('price_string(300) returns', price_string(300))
    print('price_string(201) returns', price_string(201))
    print('price_string(3) returns', price_string(3))
    print('price_string(117) returns', price_string(117))
    print('price_string(101) returns', price_string(101))
    
    
    
    
    
    # optional but encouraged: add test calls for your functions below
