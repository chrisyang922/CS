#
# ps9pr4.py  (Problem Set 9, Problem 4)
#
# AI Player for use in Connect Four   
#

import random  
from ps9pr3 import *

class AIPlayer(Player):
    """ more “intelligent” computer player – one that uses techniques
        from artificial intelligence (AI) to choose its next move
    """

    def __init__(self, checker, tiebreak, lookahead):
        """ constructs a new AIPlayer object
            input checker: checker with 'X' or 'O'
            input tiebreak: 'LEFT' 'RIGHT' or 'RANDOM'
            input lookahead: any positive integer or 0
        """
        assert(checker == 'X' or checker == 'O')
        assert(tiebreak == 'LEFT' or tiebreak == 'RIGHT' or tiebreak == 'RANDOM')
        assert(lookahead >= 0)
        super().__init__(checker)
        self.tiebreak = tiebreak
        self.lookahead = lookahead

    def __repr__(self):
        """ returns a string representing an AIPlayer object
        """
        return "Player " + self.checker + " (" + self.tiebreak + ", " + str(self.lookahead) + ')'


    def max_score_column(self, scores):
        """ returns the index of the column with the maximum score. If one
            or more columns are tied for the maximum score, the method should
            apply the called AIPlayer‘s tiebreaking strategy to break the tie
            input scores: any list consisted of -1, 0, 50, 100
        """
        max_number = scores[0]
        the_index = [0]
        
        for x in range(1, len(scores)):
            if max_number == scores[x]:
                the_index += [x]
            elif max_number < scores[x]:
                the_index = [x]
                max_number = scores[x]
        if len(the_index) == 1:
            return the_index[0]
        else:
            if self.tiebreak == "LEFT":
                return the_index[0]
            elif self.tiebreak == 'RIGHT':
                return the_index[len(the_index)-1]
            else:
                x = random.choice(range(len(the_index)))
                return the_index[x]

    def scores_for(self, board):
        """ return a list containing one score for each column
            input board: any board
        """
        scores = [50] * board.width
        for col in range(board.width):
            if board.can_add_to(col) == False:
                scores[col] = -1
            elif board.is_win_for(self.checker) == True:
                scores[col] = 100
            elif board.is_win_for(self.opponent_checker()) == True:
                scores[col] = 0
            elif scores[col] == 100:
                scores[col] = 100
            elif scores[col] == 0:
                scores[col] = 0
            elif self.lookahead == 0:
                scores[col] = 50
            else:
                board.add_checker(self.checker, col)
                if self.checker == 'X':
                    new = 'O'
                else:
                    new = 'X'
                AIPlayer_new = AIPlayer(new, self.tiebreak, self.lookahead - 1)
                opp_scores = AIPlayer_new.scores_for(board)
                the_max = max(opp_scores)
                if the_max == -1:
                    scores[col] = -1
                elif the_max == 100:
                    scores[col] = 0
                elif the_max == 0:
                    scores[col] = 100
                elif the_max == 50:
                    scores[col] = 50
                board.remove_checker(col)
        return scores

    def next_move(self,board):
        """ return the called AIPlayer‘s judgment of its best possible move
            input board: any board
        """

        possible_scores = self.scores_for(board)
        the_column = self.max_score_column(possible_scores)
        self.num_moves +=1
        return the_column

    
