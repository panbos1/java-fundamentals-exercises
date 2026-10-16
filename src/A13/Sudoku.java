package A13;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Sudoku {

    private static final int BOARD_SIZE = 9;
    private static final int SUBSECTION_SIZE = 3;
    private static final int BOARD_START_INDEX = 0;

    private static final int NO_VALUE = 0;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 9;

    private static int[][] board = {
            {8, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 3, 6, 0, 0, 0, 0, 0},
            {0, 7, 0, 0, 9, 0, 2, 0, 0},
            {0, 5, 0, 0, 0, 7, 0, 0, 0},
            {0, 0, 0, 0, 4, 5, 7, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 3, 0},
            {0, 0, 1, 0, 0, 0, 0, 6, 8},
            {0, 0, 8, 5, 0, 0, 0, 1, 0},
            {0, 9, 0, 0, 0, 0, 4, 0, 0}
    };

    public static void main(String[] args) {
        Sudoku solver = new Sudoku();
        solver.solve(board);
        solver.printBoard();

        int N;
        int Validcount=0;
        int Invalidcount=0;
        System.out.println("Please give me the number of sudokus u want to create and solve");
        Scanner in = new Scanner(System.in);
        N = in.nextInt();
        long start = System.currentTimeMillis();
        for (int i=0;i<N;i++){
            int brd[][]= new int [9][9];
            solver.fillValues(brd);
            solver.solve(brd);
            solver.printSudoku(brd);
            if (solver.isValidBoard(brd)){
                Validcount ++;
            }
            else{
                Invalidcount++;
            }
        }
        long end = System.currentTimeMillis();
        float sec = (end - start) / 1000F; System.out.println("Elapsed time in seconds:"+sec );
        System.out.println("Valid boards created :"+Validcount);
        System.out.println("Invalid boards created :"+Invalidcount);
    }

    private boolean isValidBoard(int[ ][ ] brd) {
        HashSet<Integer> numbers = new HashSet<>();
        for (int row = 0; row < brd.length; row++)
        {
            numbers.clear();
            for (int i = 0; i < 9; i++) {
                if (numbers.contains(brd[row][i]))
                    return false;
                if (brd[row][i] != 0)
                    numbers.add(brd[row][i]);
            }
        }
        for(int col=0;col<brd.length;col++)
        {
            numbers.clear();
            for (int i = 0; i < 9; i++) {
                if (numbers.contains(brd[i][col]))
                    return false;
                if (brd[i][col] != 0)
                    numbers.add(brd[i][col]);
            }
        }
        for (int row = 0; row < 3; row++) {
            numbers.clear();
            for (int col = 0; col < 3; col++) {
                int curr = brd[row][col];

                if (numbers.contains(curr))
                    return false;

                if (curr != 0)
                    numbers.add(curr);
            }
        }
        for (int row = 0 ; row < 9; row = row + 3)
        {
            for (int col = 0; col < 9; col = col + 3)
            {
                numbers.clear();
                for(int i = row; i < row+3; i++)
                {
                    for(int j= col; j < col+3; j++)
                    {
                        if (brd[i][j] < 0 || brd[i][j] > 9){
                            return false;
                        }

                        else if (brd[i][j] != 0){
                            if (!numbers.add(brd[i][j])) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isSolvableBoard(int[][] board)
    {
        boolean valid = true;
        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j<board[i].length;j++)
            {
                if(!isValid(board,i,j))
                {
                    valid = false;
                }
            }
        }
        if(valid && isValidBoard(board))
        {
            return true;
        }
        return false;
    }

    private boolean correctCheck(int[][] board, int row, int col, int num)
    {
        for (int j = 0; j<board.length; j++)
            if (board[row][j] == num)
                return false;
        for (int i = 0; i<board.length; i++)
            if (board[i][col] == num)
                return false;
        for(int rowStart=0;rowStart<9;rowStart+=3)
        {
            for(int colStart=0;colStart<9;colStart+=3)
            {
                for (int i = 0; i<3; i++)
                    for (int j = 0; j<3; j++)
                        if (board[rowStart+i][colStart+j]==num)
                            return false;
            }
        }

        return true;
    }

    int N=9;

    Double SRNd = Math.sqrt(N);
    int SRN = SRNd.intValue();
    int K=15;

    public void fillValues(int[][] brd)
    {

        fillDiagonal(brd);
        fillRemaining(0, SRN,brd);
        removeKDigits(brd);
    }

    void fillDiagonal(int[][] brd)
    {
        for (int i = 0; i<N; i=i+SRN)
            fillBox(i, i,brd);
    }


    boolean unUsedInBox(int rowStart, int colStart, int num, int[][] brd)
    {
        for (int i = 0; i<SRN; i++)
            for (int j = 0; j<SRN; j++)
                if (brd[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }


    void fillBox(int row, int col, int[][] brd)
    {
        int num;
        for (int i=0; i<SRN; i++)
        {
            for (int j=0; j<SRN; j++)
            {
                do
                {
                    num = randomGenerator(N);
                }
                while (!unUsedInBox(row, col, num, brd));

                brd[row+i][col+j] = num;
            }
        }
    }

    // Random generator
    int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }


    boolean CheckIfSafe(int i, int j, int num, int[][] brd)
    {
        return (unUsedInRow(i, num, brd) &&
                unUsedInCol(j, num, brd) &&
                unUsedInBox(i-i%SRN, j-j%SRN, num,brd ));
    }


    boolean unUsedInRow(int i, int num, int[][] brd)
    {
        for (int j = 0; j<N; j++)
            if (brd[i][j] == num)
                return false;
        return true;
    }

    boolean unUsedInCol(int j, int num, int[][] brd)
    {
        for (int i = 0; i<N; i++)
            if (brd[i][j] == num)
                return false;
        return true;
    }

    boolean fillRemaining(int i, int j, int[][] brd)
    {
        if (j>=N && i<N-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=N && j>=N)
            return true;

        if (i < SRN)
        {
            if (j < SRN)
                j = SRN;
        }
        else if (i < N-SRN)
        {
            if (j==(int)(i/SRN)*SRN)
                j =  j + SRN;
        }
        else
        {
            if (j == N-SRN)
            {
                i = i + 1;
                j = 0;
                if (i>=N)
                    return true;
            }
        }

        for (int num = 1; num<=N; num++)
        {
            if (CheckIfSafe(i, j, num,brd))
            {
                brd[i][j] = num;
                if (fillRemaining(i, j+1,brd))
                    return true;

                brd[i][j] = 0;
            }
        }
        return false;
    }
    public void removeKDigits(int[][] brd)
    {
        int count = K;
        while (count != 0)
        {
            int cellId = randomGenerator(N*N)-1;
            int i = (cellId/N);
            int j = cellId%9;
            if (j != 0)
                j = j - 1;
            if (brd[i][j] != 0)
            {
                count--;
                brd[i][j] = 0;
            }
        }
    }
    public void printSudoku(int[][] brd)
    {
        for (int i = 0; i<N; i++)
        {
            for (int j = 0; j<N; j++)
                System.out.print(brd[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
//    private void boardGenerator(int[][] board, int emptyCells)
//    {
//        Random rand = new Random();
//        int random = rand.nextInt(9) + 1;
//        for(int i=0;i<board.length;i++)
//        {
//            for(int j=0;j<board[i].length;j++)
//            {
//                while(!correctCheck(board,i,j,random))
//                {
//                    random = rand.nextInt(9) + 1;
//                }
//                board[i][j] = random;
//            }
//        }
//    }

    private void printBoard() {
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
            for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
                System.out.print(board[row][column] + " ");
            }
            System.out.println();
        }
    }

    private boolean solve(int[][] board) {
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
            for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
                if (board[row][column] == NO_VALUE) {
                    for (int k = MIN_VALUE; k <= MAX_VALUE; k++) {
                        board[row][column] = k;
                        if (isValid(board, row, column) && solve(board)) {
                            return true;
                        }
                        board[row][column] = NO_VALUE;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] board, int row, int column) {
        return rowConstraint(board, row) &&
                columnConstraint(board, column) &&
                subsectionConstraint(board, row, column);
    }

    private boolean subsectionConstraint(int[][] board, int row, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        int subsectionRowStart = (row / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionRowEnd = subsectionRowStart + SUBSECTION_SIZE;

        int subsectionColumnStart = (column / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionColumnEnd = subsectionColumnStart + SUBSECTION_SIZE;

        for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                if (!checkConstraint(board, r, constraint, c)) return false;
            }
        }
        return true;
    }

    private boolean columnConstraint(int[][] board, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
                .allMatch(row -> checkConstraint(board, row, constraint, column));
    }

    private boolean rowConstraint(int[][] board, int row) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
                .allMatch(column -> checkConstraint(board, row, constraint, column));
    }

    private boolean checkConstraint(int[][] board, int row, boolean[] constraint, int column) {
        if (board[row][column] != NO_VALUE) {
            if (!constraint[board[row][column] - 1]) {
                constraint[board[row][column] - 1] = true;
            } else {
                return false;
            }
        }
        return true;
    }
}