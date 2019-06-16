import java.util.HashSet;
import java.util.Set;

public class sudokuSolver{
    public static void main(String[] args) {
        //start time for performance profiling
        long start = System.currentTimeMillis();
        //this is apparently the hardest sudoku game with 11 stars (found after a google search)
        int[][] sudoku = {
                {8, 0, 0,   0, 0, 0,   0, 0, 0},
                {0, 0, 3,   6, 0, 0,   0, 0, 0},
                {0, 7, 0,   0, 9, 0,   2, 0, 0},

                {0, 5, 0,   0, 0, 7,   0, 0, 0},
                {0, 0, 0,   0, 4, 5,   7, 0, 0},
                {0, 0, 0,   1, 0, 0,   0, 3, 0},

                {0, 0, 1,   0, 0, 0,   0, 6, 8},
                {0, 0, 8,   5, 0, 0,   0, 1, 0},
                {0, 9, 0,   0, 0, 0,   4, 0, 0},
        };
        sudokuSolver solver = new sudokuSolver();
        //check if the board is initially invalid
        if(!solver.check(sudoku)){
            System.err.println("Invalid sudoku board.");
        }
        //check for a solution
        else if(solver.solve(sudoku)) {
            //end time for profiling
            long end = System.currentTimeMillis();
            System.out.println("Sudoku solved in "+(end-start)+"ms");
            //print the board
            solver.printBoard(sudoku);
        }
        else {
            long end = System.currentTimeMillis();
            System.err.println("Invalid sudoku board.\nTook "+(end-start)+"ms, could not reach a solution.");
        }
    }

    private void printBoard(int[][] board){
        //print the sudoku board with proper formatting
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                System.out.print(board[i][j] + " ");
                if(((j+1) % 3) == 0 && j < 8)
                    System.out.print("   ");
            }
            System.out.println();
            if(((i+1) % 3) == 0 && i < 8){
                System.out.println();
            }

        }
    }

    private boolean solve(int[][] sudoku) {
        //checks each cell in the board
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(sudoku[i][j] != 0){
                    continue;
                }
                //if cell is empty (0)
                for(int k = 1; k <= 9; k++){
                    //test every possible number
                    if(test(i, j, k, sudoku)) {
                        //if placement is valid, put number in cell
                        sudoku[i][j] = k;

                        //check if this leads to a solution
                        if (solve(sudoku)) {
                            //returns true if a solution is found
                            return true;
                        } else {
                            //if no solution is reached with this number,
                            //empty the cell and try another number
                            sudoku[i][j] = 0;
                        }
                    }
                }
                //returns false if no possible number can be placed
                return false;
            }
        }
        //every cell seen, solution reached
        return true;
    }

    private boolean check(int[][] board){
        Set<Integer> set = new HashSet<>();
        //check rows
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(board[i][j] != 0){
                    if(set.contains(board[i][j])){
                        return false;
                    }
                    set.add(board[i][j]);
                }
            }
            set.clear();
        }
        set.clear();
        //check cols
        for(int j = 0; j < 9; j++){
            for(int i = 0; i < 9; i++){
                if(board[i][j] != 0){
                    if(set.contains(board[i][j])){
                        return false;
                    }
                    set.add(board[i][j]);
                }
            }
            set.clear();
        }
        set.clear();
        //check boxes
        for(int row = 0; row <= 6; row+=3){
            for(int col = 0; col <= 6; col+=3){
                for(int i = row; i < (row+3); i++){
                    for(int j = col; j < (col+3); j++){
                        if(!(board[i][j] == 0)){
                            if(set.contains(board[i][j])){
                                return false;
                            }
                            else{
                                set.add(board[i][j]);
                            }
                        }
                    }
                }
                set.clear();
            }
        }
        return true;
    }

    private boolean test(int row, int col, int number, int[][] sudoku){
        //test rows
        for(int i = 0; i < 9; i++){
            if(sudoku[row][i] == number){
                return false;
            }
        }
        //test cols
        for(int i = 0; i < 9; i++){
            if(sudoku[i][col] == number){
                return false;
            }
        }
        //test boxes
        row -= row % 3; //go to the first cell in this box
        col -= col % 3;

        for (int i = row; i < row + 3; i++)
            for (int j = col; j < col + 3; j++)
                if (sudoku[i][j] == number){
                    return false;
                }

        return true;
    }
}