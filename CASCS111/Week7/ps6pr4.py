#
# ps6pr4.py - Problem Set 6, Problem 4
#
# Definite vs Indefinite loops
#
# name:  Jeongyong(Chris) Yang
# email: chrisyan@bu.edu

# function 1
def log(b,n):
    """ return the logarithm to the base b of a number n – or, in cases in
        which n is not an integral power of b, an integer estimate of the log
        input b, n - any positive integers
    """
    count = 0
    while n > 1:
        print('dividing', n, 'by', b, 'gives', n // b)
        count += 1
        n = n // b
    return count

# function 2
def add_powers(m,n):
    """ return the sum of the first m powers of n
        (from n**0 up to and including n**(m-1) power)
        input m - any positive integer
        input n - any integer
    """
    sum = 0
    for x in range(m):
        print(n, '**', x, '=', n ** x)
        a = n ** x
        sum += a
    return sum
