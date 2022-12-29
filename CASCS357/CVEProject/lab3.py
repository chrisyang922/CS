#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
list of referenced sources: https://datagy.io/python-sha256/


"""
from base64 import encode
import hashlib



def hash_to_int(hash_list):
    """
        The first part of the assignment is relatively straightforward: You
        should write a program that finds the password given its sha256 hash,
        assuming that the password is a number between 1 and 500000.
        input: a list of strings, each string is sha256 hash of an intger
        output: a list of integers

        example test case:
        hash_to_int(["6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b"
                    "d4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35"])
                    -> [1, 2]
    """
    list_of_integers = []
    for x in hash_list:
        for y in range(1,500001):
            hashed = hashlib.sha256(str(y).encode("UTF-8"))
            hashedValue = hashed.hexdigest()
            if (hashedValue == x):
                list_of_integers.append(y)
                break
    return list_of_integers



def salted_hashes_to_int(hash_list):
    """
        You should write a program that finds the password given it's salted
        sha256 hash, assuming that the password is a number between 1 and 500000

        input: a list of tuples of size 2, where the first element is a string salt 
        and the second element is a string hash.
        output: a list of integers

        example test case:
        salted_hahes_to_int([("salt", "dc90cf07de907ccc64636ceddb38e552a1a0d984743b1f36a447b73877012c39")
                     ("salt2","6626e0d24e096d7af41b2c4d3c56335f6c451c3ef26bde0b7d4343318b3bafc2")])
                    -> [1, 2]
    """

    list_of_integers = []
    for x in hash_list:
        for y in range(1,500001):
            stringValue = str(x[0]+str(y))
            hashed = hashlib.sha256(stringValue.encode("UTF-8"))
            hashedValue = hashed.hexdigest()
            hash = x[1]
            if (hashedValue == hash):
                list_of_integers.append(y)
                break
    return list_of_integers

def hard_salted_hashes_to_int(hash_list):
    """
        you should write a program that finds the password given it's hash
        where the hash function used is "slow_hash" defined above,
        assuming that the password is a number between 1 and 500000.

        input: a list of tuples of size 2, where the first element is a small string
        salt and the second element is a string hash.
        output: a list of integers

        example test case:
        hash_to_int([("1", "4008ad2982764d814d13c952b703c4d741985fb00e3f2a929a6666ceb9df6f5a")
                     ("2","e9dc28d0778cc6be950ecd48b38d9e9bcec125ee9ef1ec96b0e5055a1f6e379a")])
                    -> [1, 2]
    """

    def slow_hash(string):
        final_hash = hashlib.sha256(string.encode()).hexdigest()
        for i in range(0, 50):
            final_hash = hashlib.sha256(final_hash.encode()).hexdigest()
        return final_hash
    
    list_of_integers = []
    for x in hash_list:
        for y in range(1,500001):
            stringValue = x[0] + str(y)
            toSlowHash = slow_hash(stringValue)
            if(toSlowHash == x[1]):
                list_of_integers.append(y)
                break
    return list_of_integers


    
    