import java.util.Scanner;

/**
 * the sudoku class creates the main board and rules for
 * the game sudoku, where in a 9x9 grid, there can only be
 * one of each digit (1-9) in each row, col, or 3x3 box
 */
public class Sudoku {

    private int[][]board;

    public Sudoku() {
        board = new int[9][9];
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                board[i][j] = 0;
            }
        }
    }

    /**
     * call this method to see the rules
     */
    public void printRules() {
        String rules;
        rules = "Welcome to Sudoku!\n" +
                "Here is how to play Sudoku:\n\n" +
                "In a 9x9 grid, you attempt to enter digits 1-9, " +
                "filling up the board.\n There cannot be repeat digits in " +
                "any row, column, or inner 3x3 box.\n\n" +
                "Note:\n" +
                "1. This game does not generate its own playing boards, " +
                "so you will need to import your own or use the template.\n\n" +
                "2. The rows are numbered from the bottom up, so pay close attention " +
                "and follow the guides printed on the board.\n\n" +
                "3. You will be playing by yourself.\n\n" +
                "4. The game will let you know if your move breaks the game rules " +
                "and prompt you to make a different move.\n\n" +
                "5. The game will end when the board is full or you win, " +
                "although you will probably not fill the board without winning.\n\n" +
                "6. Your moves may overlap, and if you input a board with some spots " +
                "already full, you can overlap those, so be careful.";
        System.out.println(rules);
    }

    public void startGame(int[][] field) {
        for (int i=0; i<9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = field[i][j];
            }
        }
        System.out.println(toString());
    }

    public String toString() {
        String string;
        string  = "    0  1  2   3  4  5   6  7  8\n";
        String line = "  -------------------------------\n";
        string += line;
        for (int i=8; i>-1; i--) {
            if (i == 5 || i == 2){
                string += line;
            }
            for (int j=0; j<9; j++) {
                if (j == 0) {
                    string += i + " |";
                }
                if (j == 3 || j == 6) {
                    string += "|";
                }
                string += " "+ board[i][j] + " ";
                if (j == 8) {
                    string += "|\n";
                }
            }
        }
        string += line;
        return string;
    }

    /**
     * This is the method to play a game by yourself,
     * the game will go on until the board is full or you win.
     * You will be notified of incorrect moves
     */
    public void play() {
        Scanner scanner = new Scanner(System.in);
        if (isFull()) {
            System.out.println("The board is full");
            return;
        }
        System.out.println("Enter the row of your desired move");
        int row = scanner.nextInt();
        System.out.println("Enter the column of your desired move");
        int col = scanner.nextInt();
        System.out.println("Enter the number you want placed in spot: " +row+ ", " +col);
        int data = scanner.nextInt();
        if (!verifyMove(row, col, data)) {
            System.out.println("that move is not valid, try again");
            play();
            return;
        }
        else {
            addMove(row, col, data);
        }
        if (checkWin()) {
            System.out.println(toString());
            System.out.println("You won!");
            return;
        }
        else {
            System.out.println(toString());
            play();
        }
        return;
    }

    /**
     * Uses basic rule "no repeating numbers in each row,
     * column, or inner square" to verify user inputted move is allowed
     * @param row is the row of the move
     *      *            int must be between 0-8
     * @param col is the column of the move
     *      *            int must be between 0-8
     * @param data is the value in that spot
     *             int must be 1-9
     * squareX is the beginning row of the inner square containing move
     * sqaureY is the beginning col of the inner square containing move
     */
    public boolean verifyMove(int row, int col, int data){
        if (data<1 || data>9){ // checks data is within parameters
            return false;
        }
        if ((row>8 || row<0)||(col>8 || col<0)){
            return false;
        }
        for (int i=0; i<9; i++){ // checks repeats in column of the move
            if (i != row && board[i][col] == data){
                return false;
            }
        }
        for (int j=0; j<9; j++){ // checks repeats in row of the move
            if (j != col && board[row][j] == data){
                return false;
            }
        }
        int squareX = innerSquare(row); // checks repeats in inner square
        int squareY = innerSquare(col);
        for (int i=squareX; i< squareX+3; i++){
            for (int j=squareY; j< squareY+3; j++){
                if ((i != row || j != col) && board[i][j] == data){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * uses verifyMove to check that move is legal
     * then adds move to board
     * @param row is the row of the move
     *            int must be between 0-8
     * @param col is the column of the move
     *            int must be between 0-8
     * @param data is the value in that spot
     *             int must be 1-9
     */
    public void addMove(int row, int col, int data){
        if (verifyMove(row, col, data)){
            board[row][col] = data;
        }
    }

    /**
     * uses the location of a move to determine which
     * inner square the move is contained in
     * @param num is either the row or column of the move
     * @return starting place of the inner square
     */
    public int innerSquare(int num){
        if (num<3){ return 0; }
        else if (num>2 && num<6){ return 3; }
        else if (num>5 && num<9){ return 6; }
        else return -100;
    }

    /**
     * checks if there are repeating entries in specified col
     * @param num is the col in question
     * @return true if no repeats
     */
    public boolean colCheck(int num) {
        int count;
        for (int i=1; i<10; i++) { // i = entry number to check for repeats
            count = 0;
            for (int j=0; j<9; j++) { // j = row number
                if (board[j][num] == i) {
                    count += 1;
                }
            }
            if (count != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks if there are repeating entries in specified row
     * @param num is the row in question
     * @return true if no repeats
     */
    public boolean rowCheck(int num) {
        int count;
        for (int i=1; i<10; i++) { // i = entry number to check for repeats
            count = 0;
            for (int j=0; j<9; j++) { // j = col number
                if (board[num][j] == i) {
                    count += 1;
                }
            }
            if (count != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks if there are repeating entries in each inner square
     * @param row row inside inner square to check
     * @param col col inside inner square to check
     * @return true if no repeats
     */
    public boolean innerSquareCheck(int row, int col) {
        int count;
        int squareX = innerSquare(row); // make sure starting point is correct
        int squareY = innerSquare(col);
        for (int i=1; i<10; i++) { //i = entry number to check for repeats
            count = 0;
            for (int j=squareX; j< squareX+3; j++){ // j = row
                for (int k=squareY; k< squareY+3; k++){ // k = col
                    if (board[j][k] == i) {
                        count += 1;
                    }
                }
            }
            if (count != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * determines if there are still empty spaces in the board
     * @return true if no empty spots
     */
    public boolean isFull() {
        for (int i=0; i<9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkWin() {
        if (!isFull()){
            return false;
        }
        for (int i=0; i<9; i++) {
            if (!rowCheck(i) || !colCheck(i)) {
                return false;
            }
        }
        for (int i=1; i<9; i+=3) {
            for (int j=1; j<9; j+=3) {
                if (!innerSquareCheck(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[]args) {
        Sudoku game = new Sudoku();
        //game.printRules();
        int[][] template = {{0,0,8,0,6,0,0,3,4},{7,6,3,0,2,9,0,1,0},
                {4,0,5,0,0,8,6,9,0},{5,4,0,6,1,0,0,0,7},{0,0,0,2,5,0,4,6,9},
                {0,0,0,0,8,0,0,0,0},{0,0,4,0,0,0,0,7,0},{0,5,7,0,3,0,9,0,6},
                {3,0,0,7,0,5,0,2,8}};
        int[][] winning = {{9,2,8,5,6,1,7,3,4},{7,6,3,4,2,9,8,1,5},
                {4,1,5,3,7,8,6,9,2},{5,4,9,6,1,3,2,8,7},{8,3,1,2,5,7,4,6,9},
                {6,7,2,9,8,4,3,5,1},{2,8,4,1,9,6,5,7,3},{1,5,7,8,3,2,9,4,6},
                {3,9,6,7,4,5,1,2,8}};
        game.startGame(template);
        //System.out.println(game.toString());
        //System.out.println(game.checkWin());
        game.play();
    }
}
