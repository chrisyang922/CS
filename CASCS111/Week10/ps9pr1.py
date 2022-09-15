# 
# ps9pr1.py - Problem Set 9, Problem 1
#
# A Connect Four Board class
#
class Board:
    """ a data type for a Connect Four board with
        arbitrary dimensions
    """
    def __init__(self, height, width):
        """ constructs a new Board object by initializing height, width, and slot
            input height: any positive integer
            input width: any positive integer
        """
        self.height = height
        self.width = width
        self.slots = [[' '] * self.width for row in range(self.height)]
        
    def __repr__(self):
        """ returns a string representing a Board object"""
        s = ''
        for row in range(self.height):
            s+= '|'
            for col in range(self.width):
                s+= self.slots[row][col] +'|'
            s+= '\n'
        s+='-'
        for col in range(self.width):
            s += '--'
        s+= '\n'
        number = 0
        for col in range(self.width):
            s+= ' ' + str(number)
            number += 1
            if number > 9:
                number -= 10
        return s
    
    def add_checker(self,checker,col):
        """ adds the specified checker to column col
            input checker: 'X' or 'O'
            input col: any positive integer less than the column of the board
        """
        assert(checker == 'X' or checker == 'O')
        assert(0 <= col < self.width)

        row = 0
        while self.slots[row + 1][col] == ' ':
            row+= 1
            if row == self.height - 1:
                self.slots[row][col] = checker
                break
        self.slots[row][col] = checker
        
    def reset(self):
        """ reset the Board object on which it is called by setting
            all slots to contain a space character
        """
        height = self.height
        width = self.width
        self.__init__(height,width)
        
    def add_checkers(self,colnums):
        """ takes in a string of column numbers and places alternating
        checkers in those columns of the called Board object, 
        starting with 'X'.
        """
        checker = 'X'
        for col_str in colnums:
            col = int(col_str)
            if 0 <= col < self.width:
                self.add_checker(checker,col)
            if checker == 'X':
                checker = 'O'
            else:
                checker = 'X'

    def can_add_to(self,col):
        """ returns True if it is valid to place a checker in the column
            col on the calling Board object or False otherwise
            input col: any positive integer less than the column of the board
        """
        if col < 0 or col >= self.width:
            return False
        if self.slots[0][col] == ' ':
            return True
        else:
            return False

    def is_full(self):
        """ returns True if the called Board object is completely
            full of checkers, and False otherwise
        """
        for x in range(self.width):
            if self.can_add_to(x) == True:
                return False
        return True

    def remove_checker(self,col):
        """ removes the top checker from column col of the called Board object
            but if the column is empty, then the method should do nothing
            input col: any positive integer less than the column of the board
        """
        for x in range(self.height):
            if self.slots[x][col] != ' ':
                self.slots[x][col] = ' '
                break

    def is_horizontal_win(self,checker):
        """ Checks for a horizontal win for the specified checker
            input checker: 'X' or 'O'
        """
        for row in range(self.height):
            for col in range(self.width - 3):
                if (self.slots[row][col] == checker and
                    self.slots[row][col+1] == checker and
                    self.slots[row][col+2] == checker and
                    self.slots[row][col+3] == checker):
                        return True
        return False
        
    def is_vertical_win(self,checker):
        """ Checks for a vertical win for the specified checker
            input checker: 'X' or 'O'
        """
        for row in range(self.height-3):
            for col in range(self.width):
                if (self.slots[row][col] == checker and
                    self.slots[row+1][col] == checker and
                    self.slots[row+2][col] == checker and
                    self.slots[row+3][col] == checker):
                        return True
        return False

    def is_diagonal_win(self,checker):
        """ Checks for a diagonal win for the specified checker
            input checker: 'X' or 'O'
        """
        for row in range(self.height - 3):
            for col in range(self.width - 3):
                if (self.slots[row][col] == checker and
                    self.slots[row+1][col+1] == checker and
                    self.slots[row+2][col+2] == checker and
                    self.slots[row+3][col+3] == checker):
                        return True
        for row in range(3, self.height):
            for col in range(self.width - 3):
                if (self.slots[row][col] == checker and
                    self.slots[row-1][col+1] == checker and
                    self.slots[row-2][col+2] == checker and
                    self.slots[row-3][col+3] == checker):
                        return True
        return False
        
    def is_win_for(self,checker):
        """ returns True if there are four consecutive slots containing checker
            on the board or False otherwise
            input checker: 'X' or 'O'
        """
        assert(checker == 'X' or checker == 'O')

        if (self.is_horizontal_win(checker) == True or
            self.is_vertical_win(checker) == True or
            self.is_diagonal_win(checker) == True):
            return True
        return False
        
        
    
