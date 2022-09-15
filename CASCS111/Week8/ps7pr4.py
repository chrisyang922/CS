#
# ps7pr4.py  (Problem Set 7, Problem 4)
#
# Matrix Operations
#
# Computer Science 111
#

def display_menu():
    """ prints a menu of options
    """
    print()
    print('(0) Enter a new matrix')
    print('(1) Negate the matrix')
    print('(2) Multiply a row by a constant')
    print('(3) Add one row to another')
    print('(4) Add a multiple of one row to another')
    print('(5) Transpose the matrix')
    print('(6) Quit')
    print()


def print_matrix(matrix):
    """ prints the specified matrix in rectangular form.
        input: matrix is a rectangular 2-D list numbers
    """
    for r in range(len(matrix)):
        for c in range(len(matrix[0])):
            print('%6.2f' % matrix[r][c], end=' ')
        print()


def get_matrix():
    """ gets a new matrix from the user and returns it
    """
    matrix = eval(input('Enter a new 2-D list of numbers: '))
    return matrix


def negate_matrix(matrix):
    """ negates all of the elements in the specified matrix
        inputs: matrix is a rectangular 2-D list of numbers
    """
    for r in range(len(matrix)):
        for c in range(len(matrix[0])):
            matrix[r][c] *= -1
    # We don't need to return the matrix!
    # All changes to the matrix will still be there when the
    # function returns, because we received a copy of the
    # *reference* to the matrix used by main().


### Add your functions for options 2-5 here. ###
def mult_row(matrix, r, m):
    """ multiplies row r of the specified matrix by the specified multiplier m
        input matrix: rectangular 2-D list of numbers
        input r: row number of the matrix
        input m: any number (integer or float)
    """
    for x in range(len(matrix[0])):
        matrix[r][x] *= m

def add_row_into(matrix, source, dest):
    """ takes the specified 2-D list matrix and adds each element
        of the row with index source (the source row) to the
        corresponding element of the row with index dest (the destination row)
        input matrix: rectangular 2-D list of numbers
        input source, dest: any row number of matrix
    """
    for x in range(len(matrix[0])):
        matrix[dest][x] += matrix[source][x]

def add_mult_row_into(matrix, m, source, dest):
    """ takes the specified 2-D list matrix and adds each element
        of row source (the source row), multiplied by m, to the
        corresponding element of row dest (the destination row)
        input matrix: rectangular 2-D list of numbers
        input m: any number (integer or float)
        input source, dest: any row number of matrix
    """
    for x in range(len(matrix[0])):
        matrix[dest][x] += matrix[source][x] * m

def transpose(matrix):
    new_matrix = []
    for r in range(len(matrix)):
        y = matrix[r][:]
        new_matrix += [y]
    new_new_matrix = []
    for r in range(len(matrix[0])):
        row = [0] * len(matrix)  # a row containing width 0s
        new_new_matrix += [row]
    for r in range(len(matrix)):
        for c in range(len(matrix[0])):
            new_new_matrix[c][r] = matrix[r][c]
    matrix = new_new_matrix
    return matrix


def main():
    """ the main user-interaction loop
    """
    ## The default starting matrix.
    ## DO NOT CHANGE THESE LINES.
    matrix = [[1, 2, 3, 4],
              [5, 6, 7, 8],
              [9, 10, 11, 12]]

    while True:
        print()
        print_matrix(matrix)
        display_menu()
        choice = int(input('Enter your choice: '))

        if choice == 0:
            matrix = get_matrix()
        elif choice == 1:
            negate_matrix(matrix)
        elif choice == 2:
            row = int(input('Index of row: '))
            multiplier = float(input('Multiplier: '))
            mult_row(matrix, row, multiplier)
        elif choice == 3:
            source = int(input('Index of source row: '))
            dest = int(input('Index of destionation row: '))
            add_row_into(matrix, source, dest)
        elif choice == 4:
            source = int(input('Index of source row: '))
            dest = int(input('Index of destionation row: '))
            multiplier = float(input('Multiplier: '))
            add_mult_row_into(matrix, multiplier,source,dest)
        elif choice == 5:
            matrix = transpose(matrix)
        elif choice == 6:
            break
        else:
            print('Invalid choice. Try again.')

