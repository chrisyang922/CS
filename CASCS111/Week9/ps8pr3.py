# 
# ps8pr3.py - Problem Set 8, Problem 3
#
# Markov text generation
#
# name:  Jeongyong(Chris) Yang
# email: chrisyan@bu.edu
#
# If you worked with a partner, put his or her contact info below:
# partner's name:
# partner's email:
#

import random

def create_dictionary(filename):
    """ returns a dictionary of key-value pairs in which:
        each key is a word encountered in the text file and
        the corresponding value is a list of words that
        follow the key word in the text file
        input filename: the name of the file
    """
    file = open(filename, 'r')
    word = []
    for line in file:
        if line[-1] == '\n':
            line = line[:-1]
        the_word_list = line.split()
        word += the_word_list
    dictionary = {}
    current_word = '$'
    for next_word in word:
        if current_word not in dictionary:
            dictionary[current_word] = [next_word]
        else:
            dictionary[current_word] += [next_word]
        current_word = next_word
        if next_word[-1] not in 'qwertyuiopasdfghjklzxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM':
            current_word = '$'
    return dictionary

def generate_text(word_dict, num_words):
    """ generate and print a string of num_words (parameter variable) words
        input word_dict: dictionary of word transitions
        input num_words: positive integer
    """
    count = 0
    current_word = '$'
    while count < num_words:

        next_word = word_dict[current_word]
        the_number = len(word_dict[current_word])
        wordlist = next_word[random.choice(range(the_number))]
        count+=1
        
        print(wordlist, end = ' ')
        current_word = wordlist
        if wordlist[-1] not in 'qwertyuiopasdfghjklzxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM':
            current_word = '$'
        
        
    
    
