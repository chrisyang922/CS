# 
# ps1pr1.py - Problem Set 1, Problem 1
#
# Indexing and slicing puzzles
#
# This is an individual-only problem that you must complete on your own.
# 

#
# List puzzles
#

pi = [3, 1, 4, 1, 5, 9]
e = [2, 7, 1]

# Example puzzle (puzzle 0):
# Creating the list [3, 7, 1] from pi and e
answer0 = [pi[0]] + e[-2:]     
print(answer0)

# Puzzle 1:
# Creating the list [1, 5, 9] from pi and e
answer1 = pi[3:]
print(answer1)

# Puzzle 2:
# Creating the list [3, 4, 5] from pi and e
answer2 = pi[::2]
print(answer2)

# Puzzle 3:
# Creating the list [9, 1, 1, 1] from pi and e
answer3 = pi[-1::-2] + e[2:3]
print(answer3)


#
# String puzzles
#

b = 'boston'
u = 'university'
t = 'terriers'

# Example puzzle (puzzle 4)
# Creating the string 'bossy'
answer4 = b[:3] + t[-1] + u[-1]
print(answer4)

# Puzzle 5:
# Creating the string 'vest'
answer5 = u[3:5] + b[2:4]
print(answer5)

# Puzzle 6:
# Creating the string 'revisit'
answer6 = u[-5:-8:-1] + u[-3:-5:-1] + u[7:9]
print(answer6)

# Puzzle 7:
# Creating the string 'booooooo'
answer7 = b[0] + b[1] * 7
print(answer7)

# Puzzle 8:
# Creating the string 'borris'
answer8 = b[0:2] + t[2:4] + u[-3:-5:-1]
print(answer8)


