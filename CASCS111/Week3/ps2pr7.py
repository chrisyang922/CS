# 
# ps2pr7.py - Problem Set 2, Problem 7
#
# Functions using Recursion Part II
#
# name:  Jeongyong(Chris) Yang
# email: chrisyan@bu.edu
#

def letter_score(letter):
    """ returns the point of the corresponding lower case letter as scrabble tile
        input letter: lowercased character ('a' to 'z')
    """
    assert (len(letter) == 1)
    if letter in ['a', 'e', 'i', 'l', 'n', 'o', 'r', 's', 't', 'u']:
        return 1
    elif letter in ['d', 'g']:
        return 2
    elif letter in ['b', 'c', 'p', 'm']:
        return 3
    elif letter in ['f', 'h', 'v', 'w', 'y']:
        return 4
    elif letter == 'k':
        return 5
    elif letter in ['j', 'x']:
        return 8
    elif letter in ['q', 'z']:
        return 10
    else: 
        return 0

# function 2
def scrabble_score(word):
    """ returns the scrabble score of the string word
        input word: any string consisted of lowercase words
    """
    if len(word) == 0:
        return 0
    else:
        rest_score = scrabble_score(word[1:])
        points = rest_score + letter_score(word[0])
        return points
    
#function 3
def num_diff(s1, s2):
    """ returns the number of differences of the two strings s1 and s2 (parameter variables)
        input s1, s2: any string with length of s1 and s2 being equal
    """
    if len(s1) == 0:
        return 0
    else:
        rest_difference = num_diff(s1[1:], s2[1:])
        if s1[0] == s2[0]:
            return rest_difference
        else:
            return rest_difference + 1


# function 4
def weave(s1, s2):
    """ returns a combination of s1 and s2(parameter variables) by weaving the
        two string inputs, with the first character being from the first
        index of s1 and second character being first index of s2, and etc.
        input s1, s2: any string value
    """
    if len(s1) == 0 and len(s2) == 0:
        return ''
    else:
        rest_weave = weave(s1[1:], s2[1:])
        if len(s1) > 0 and len(s2) > 0:
            return  s1[0] + s2[0] + rest_weave
        elif len(s1) > 0:
            return s1[0] + rest_weave
        else:
            return s2[0] + rest_weave

# test function 
def test():
    """ performs test calls on the functions above """
    print('letter_score("w") returns', letter_score('w'))
    print('letter_score("q") returns', letter_score('q'))
    print('letter_score("&") returns', letter_score('&'))
    print('letter_score("C") returns', letter_score('C'))
    print('scrabble_score("python") returns', scrabble_score('python'))
    print('scrabble_score("a") returns', scrabble_score('a'))
    print('scrabble_score("quetzal") returns', scrabble_score('quetzal'))
    print('num_diff("alien", "allen") returns', num_diff('alien', 'allen'))
    print('num_diff("alone", "alien") returns', num_diff('alone', 'allen'))
    print('num_diff("same", "same") returns', num_diff('same', 'same'))
    print('weave("aaaaaa", "bbbbbb") returns', weave('aaaaaa', 'bbbbbb'))
    print('weave("abcde", "VWXYZ") returns', weave('abcde', 'VWXYZ'))
    print('weave("aaaaaa", "bb") returns', weave('aaaaaa', 'bb'))
    print('weave("aaaa", "") returns', weave('aaaa', ''))
    print('weave("", "bbbb") returns', weave('', 'bbbb'))
    print('weave("aaaa", "bbbbbb") returns', weave('aaaa', 'bbbbbb'))
    print('weave("", "") returns', weave('', ''))
    
    
