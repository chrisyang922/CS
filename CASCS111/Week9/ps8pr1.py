# 
# ps8pr1.py - Problem Set 8, Problem 1
#
# String-method puzzles
#

s1 = 'Three little kittens lost their mittens'
s2 = 'Star light, star bright'

# Example puzzle (puzzle 0):
# Count all occurrences of the letter T (both lower- and upper-case) in s1.
answer0 = s1.lower().count('t')     
print('answer0 =', answer0)

answer1 = s1.replace('tt','pp')
print('answer1 =', answer1)

answer2 = s2.split('r')
print('answer2 =', answer2)

answer3 = s2.lower().replace('star','night').upper()     
print('answer3 =', answer3)

answer4 = s1.lower().split('th')
print('answer4 =', answer4)

answer5 = s2.replace('ight','ook').split(',')
print('answer5 =', answer5)
