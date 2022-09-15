#
# ps2pr6.py - Problem Set 2, Problem 6
#
# list comprehensions
#

# Problem 6-1: LC puzzles!
# This code won't work until you complete the list comprehensions!
# If you can't figure out how to complete one of them, please
# comment out the corresponding lines by putting a # at the start
# of the appropriate lines.

# part a
lc1 = [x + 7  for x in range(5)]
print(lc1)

# part b
words = ['hello', 'world', 'how', 'goes', 'it?']
lc2 = [w[len(w) - 1] for w in words]
print(lc2)

# part c
lc3 = [word[::2] for word in ['hello', 'bye', 'no']]
print(lc3)

# part d
lc4 = [x for x in range(1, 20) if x % 6 == 0]
print(lc4)

# part e
lc5 = [c == 'a' for c in 'aardvark']
print(lc5)


# Problem 5-2: Put your definition of the powers_of() function below.

def powers_of(base, count):
    """returns the list of first count (parameter variable)
        powers of base starting with 0 power
        input base, count: any number (int or float)
    """
    list_of_powers = [base ** count for count in range(count)]
    return list_of_powers

# Problem 5-3: Put your definition of the starts_with() function below.

def starts_with(prefix, wordlist):
    """returns the list of first count (parameter variable)
        powers of base starting with 0 power
        input prefix: any string values
        input wordlist: any list
    """
    list_of_prefix = [word for word in wordlist if (word[:len(prefix)] == prefix)]
    return list_of_prefix
