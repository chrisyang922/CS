#include <stdio.h> 
#define N 8 
int matrix[N][N];

void print_matrix(){
	int i,j;
	for ( i = 0 ; i < N ; i++ ){
		for( j = 0 ; j < N ; j++ ){
			printf( "%d ", matrix[i][j]);
		}
		printf( "\n" );
	}
	printf( "\n" );
}

int valid( int i ){
  // function that allows us to check whether putting queen on 
  // the grid i is available
  // returns 0 if unavailable and returns 1 if available
	
  // Convert grid position i on the NxN board to row x and column y
	int row = i / 8;
	int column = i % 8;
 // Declare variables that we will use in for loops
	int x;
	int y;
 // 6 for loops to check column, row, and 4 diagonal ways 
 //to see whether there is a queen
	for(x = 0; x < N; x++)
	{
		if(matrix[row][x])
		{
			return 0;
		}
	}
	
	for(x = 0; x < N; x++)
	{
		if(matrix[x][column])
		{
			return 0;
		}
	}
	for(x = row, y = column; x >=0 && y>= 0; x--,y--)
	{
		if(matrix[x][y])
		{
			return 0;
		}	
	}
	for(x = row,y=column; x < N&& y <N; x++,y++)
	{
		if(matrix[x][y])
		{
			return 0;
		}
	}
	for(x = row,y=column; x < N&&y>=0; x++,y--)
        {      
                if(matrix[x][y])
                {
                        return 0;
                }
                
        }
	for(x = row,y=column; x >= 0&&y<N; x--,y++)
        {
               
                if(matrix[x][y])
                {
                        return 0;
                }
                
        }
	//if there are no queens on all diagonals, rows, and columns,
	//return 1, which means that you can insert queen at that spot
	return 1;	
}

void putall( int id ){ 
	//function that puts all the queens in right spot	
	for (int i = 0; i < N*N ; i++ )
	{
		// if we can insert the queen in the spot
        	if( valid(i) )
		{ 
			// we insert
			matrix[i/N][i%N] = 1;
			if( id == N-1 )
			{  
				//If this is the last one to place
				//then we end it by printing the matrix
               			print_matrix();
			}
			else
			{
				//if not, call recursion and check if 
				//next position of queen is available
				int next = id + 1;
				putall(next);

				//backtracking if the solution is not correct 
				//by making that grid 0 and continuing to check
				//whether other solutions are available
				matrix[i/N][i%N] = 0;
			}
          			
        }
    }
}

