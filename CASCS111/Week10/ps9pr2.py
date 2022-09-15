#
# ps9pr2.py  (Problem Set 9, Problem 2)
#
# A Connect-Four Player class 
#  

from ps9pr1 import Board

# write your class below
class Player:
    """ represent a player of the Connect Four game"""

    def __init__(self,checker):
        """ constructs a new Player object
            input checker: a one-character string that
            represents the gamepiece for the player
        """
        self.checker = checker
        self.num_moves = 0

    def __repr__(self):
        """ returns a string representing a Player object"""
        return "Player " + self.checker

    def opponent_checker(self):
        """ returns a one-character string representing the
            checker of the Player object’s opponent
        """

        if self.checker == 'X':
            return 'O'
        else:
            return 'X'

    def next_move(self,board):
        """ returns the column where the player wants to make the next move
            input board: Board object
        """

        x = int(input("Enter a column: "))
        while 0 > x or x > board.width - 1:
            print("Try again!")
            x = int(input("Enter a column: "))
        y = board.can_add_to(x)
        while y == False:
            print("Try again!")
            x = int(input("Enter a column: "))
            y = board.can_add_to(x)

        self.num_moves += 1
        return x
        
        
