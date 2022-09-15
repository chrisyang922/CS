#
# ps3pr3.py (Problem Set 3, Problem 3)
#
# Caesar cipher / decipher
#

# A template for a helper function called rot that we recommend you write
# as part of your work on the encipher function.

# function 1
def rot(c, n):
    """ returns a character that rotates the character c(parameter variable)
    forward by n(parameter variable) spots in the alphabet
    input c: any character with one letter (including special characters like ?)
    input n: any integer from 0 to 25, inclusive
    """
    # check to ensure that c is a single character
    assert (type(c) == str and len(c) == 1)
    if 'a' <= c <= 'z':
        new_ord = ord(c) + n
        if new_ord > ord('z'):
            new_ord = new_ord - 26
    elif 'A' <= c <= 'Z':
        new_ord = ord(c) + n
        if new_ord > ord('Z'):
            new_ord = new_ord - 26
    else:
        new_ord = ord(c)
    return chr(new_ord)

    # Put the rest of your code for this function below.


### Put your code for the encipher function below. ###

def encipher(s, n):
    """returns a new string in which characters in s(parameter variable)
    are rotated by n(parameter variable) in the forward direction in alphabet
    input s: any string consisted of letters or special character (including
    charactes such as ?)
    input n: any integer from 0 to 25
    """
    if len(s) == 1:
        return rot(s, n)
    else:
        enci_rest = encipher(s[1:], n)
        return rot(s[0], n) + enci_rest


# A helper function that could be useful when assessing
# the "Englishness" of a phrase.
# You do *NOT* need to modify this function.
# function 2
def letter_prob(c):
    """ if c is the space character (' ') or an alphabetic character,
        returns c's monogram probability (for English);
        returns 1.0 for any other character.
        adapted from:
        http://www.cs.chalmers.se/Cs/Grundutb/Kurser/krypto/en_stat.html
    """
    # check to ensure that c is a single character
    assert (type(c) == str and len(c) == 1)

    if c == ' ': return 0.1904
    if c == 'e' or c == 'E': return 0.1017
    if c == 't' or c == 'T': return 0.0737
    if c == 'a' or c == 'A': return 0.0661
    if c == 'o' or c == 'O': return 0.0610
    if c == 'i' or c == 'I': return 0.0562
    if c == 'n' or c == 'N': return 0.0557
    if c == 'h' or c == 'H': return 0.0542
    if c == 's' or c == 'S': return 0.0508
    if c == 'r' or c == 'R': return 0.0458
    if c == 'd' or c == 'D': return 0.0369
    if c == 'l' or c == 'L': return 0.0325
    if c == 'u' or c == 'U': return 0.0228
    if c == 'm' or c == 'M': return 0.0205
    if c == 'c' or c == 'C': return 0.0192
    if c == 'w' or c == 'W': return 0.0190
    if c == 'f' or c == 'F': return 0.0175
    if c == 'y' or c == 'Y': return 0.0165
    if c == 'g' or c == 'G': return 0.0161
    if c == 'p' or c == 'P': return 0.0131
    if c == 'b' or c == 'B': return 0.0115
    if c == 'v' or c == 'V': return 0.0088
    if c == 'k' or c == 'K': return 0.0066
    if c == 'x' or c == 'X': return 0.0014
    if c == 'j' or c == 'J': return 0.0008
    if c == 'q' or c == 'Q': return 0.0008
    if c == 'z' or c == 'Z': return 0.0005
    return 1.0


#### Put your code for the decipher function below. ####

def scores(s):
    """returns the sum of probablities for each letter from the string s
    input s: any string consisted of letters and special characters(such as ?)
    """
    if len(s) == 1:
        return letter_prob(s)
    else:
        score_rest = scores(s[1:])
        return score_rest + letter_prob(s[0])


def decipher(s):
    """returns the string that deciphers the string s(parameter variable)that
    has already been enciphered (to the best of its ability to original English)
    input s: any string consisted of letters and special characters(such as ?)
    that has been enciphered
    """
    list_of_list = [encipher(s, n) for n in range(26)]
    list_of_list_of_list_one = [[scores(word), word] for word in list_of_list]
    max_percent = max(list_of_list_of_list_one)
    max_percent = max_percent[1]
    return max_percent


# test function with a sample test call
def test():
    """ performs test calls on the functions above """
    print('rot("a", 1) returns', rot('a', 1))
    print('rot("A", 3) returns', rot('A', 3))
    print('rot("Y", 3) returns', rot('Y', 3))
    print('rot("!", 4) returns', rot('!', 4))
    print('encipher("xyza", 1) returns', encipher('xyza', 1))
    print('encipher("Z A", 2) returns', encipher('Z A', 2))
    print('encipher("Caesar cipher? I prefer Caesar salad.", 25) returns',
          encipher('Caesar cipher? I prefer Caesar salad.', 25))
    print('decipher("Bzdrzq bhogdq? H oqdedq Bzdrzq rzkzc.") returns',
          decipher('Bzdrzq bhogdq? H oqdedq Bzdrzq rzkzc.'))
    print('decipher("python") returns', decipher('python'))
    print('decipher("Hu lkbjhapvu pz doha ylthpuz hmaly dl mvynla lclyfaopun dl ohcl slhyulk.") returns',
          decipher('Hu lkbjhapvu pz doha ylthpuz hmaly dl mvynla lclyfaopun dl ohcl slhyulk.'))
    print('decipher("kwvozibctibqwva izm qv wzlmz nwz iv mfkmttmvb rwj") returns',
          decipher('kwvozibctibqwva izm qv wzlmz nwz iv mfkmttmvb rwj'))

    # optional but encouraged: add test calls for your functions below

