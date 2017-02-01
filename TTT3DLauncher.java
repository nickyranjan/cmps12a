import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Assignment #4.
 * TTT3DLauncher.java
 *
 * This program is a basic program that creates a Tic-Tac-Toe game, however its
 * programmed to never let the user win, only losses and draws.
 * The game is played on a board based off a 4x4x3 dimensional array, each cell created
 * can be occupied by a move by the player or computer.
 * The game is won by the first to occupy four cells in a straight line.
 * 76 such lines possible in the game array.
 *
 * This file contains multiple classes, making it easier for me to understand and break
 * down each essential part of the program.
 *
 * Authors: Nikhil Ranjan (nranjan@ucsc.edu)
 *
 * RESOURCES USED:
 * Sample program of TTT3D
 * https://classes.soe.ucsc.edu/cmps012a/Fall16/SECURE/PAs/pa4.html
 *  Dean Bailey
 */


/**
 * main class and runner class
 * Create instance of the class and constructor
 * Use this object to call certain methods in the program
 */
public class TTT3DLauncher {
    public static void main(String[] args) {
        System.out.println("\n");
        try {
            TTT3D game = new TTT3D(args[0]);
            while (true) {
                game.getPlayerMove();//calls method that prompts user
                game.getComputerMove();//calls method that gets the computer play
                game.board.printBoard();//calls method that creates the physical board
            }
        } catch (Exception e) {
            TTT3D game = new TTT3D();
            while (true) {
                game.getPlayerMove();
                game.getComputerMove();
                game.board.printBoard();
            }
        }
    }
}

/**
 * Public class Board creates the physical aspect of the board
 * Contains two methods that create the board itself
 * First method to create the array, to setup the rows and columns
 * Print the allignments
 * Second Method to retrieve file information on row and column
 * Saves
 */
class Board {
    private State[][][] cells = new State[4][4][4];
    public boolean playerWon = false;

    public void printBoard() {
        for (int i = 3; i >= 0; i--) {
            System.out.println();
            for (int j = 3; j >= 0; j--) {
                int n;
                for (n = 0; n < j; n++) {
                    System.out.printf(" ");
                }
                System.out.printf("%d%1d ", i, j);
                for (n = 0; n < 4; n++) {
                    System.out.printf("%s ", cells[i][j][n]);
                }
                System.out.println();
            }
        }
        System.out.printf("   0 1 2 3\n");
    }

    /**
     * gets numbers in file and saves them as certain variables
     * saves coordinates with level, row, col
     * @param filename, file name inputted
     */
    public void createBoard (String filename) {
        makeBoard();
        if (filename == null)
            return;
        System.out.println("Getting initial setup from file.");
        try {
            Scanner file = new Scanner(new FileInputStream(filename));
            int lines = file.nextInt();
            for(int i = 0; i < lines; i++) {
                int level = file.nextInt();
                int row = file.nextInt();
                int col = file.nextInt();
                int val = file.nextInt();
                int[] coordinates = {level, row, col};
                if (val == 5)
                    playerCell(coordinates);
                else if (val == 1)
                    computerCell(coordinates);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        }
    }

    /**
     * Create empty board
     * intializes all the values to 0
     */
    public void makeBoard() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length;j++) {
                for (int k = 0; k < cells[i][j].length;k++) {
                    cells[i][j][k] = State.Empty;
                }
            }
        }
    }

    public State[][][] getCells() {
        return cells;
    }

    public void computerCell(int[] coords) {
        cells[coords[0]][coords[1]][coords[2]] = State.Computer;
    }

    public void playerCell(int[] coords) {
        cells[coords[0]][coords[1]][coords[2]] = State.Player;
    }

    public boolean isEmpty(int[] coords) {
        return cells[coords[0]][coords[1]][coords[2]] == State.Empty;
    }

    /**
     * Checks if there is a line for a win
     * for both the player or computer
     */
    public void scanWinningLines() {
        clearAllLists();
        int value = 0;
        for (int i = 0; i < lines.length; i++) {
            value = lines[i].getLineValue(cells);
            Line checkLine = lines[i];
            if (value == 20) {
                playerWon = true;
            }
            else if (value == 15) {
                addPlayerWinningLine(checkLine);
            }
            else if (value == 2) {
                addComputerHalfForksAndForks(checkLine);
            }

            else if (value == 3) {
                addComputerWinningLines(checkLine);
            }

            else if ((value/5) == 0) {
                addPlayerEmptyLine(checkLine);
            }

            else if (value == 10) {
                addPlayerHalfForksLine(checkLine);
            }
        }
    }

    /**
     * determines cells of player for winning moves
     * @return
     */
    public boolean arePlayerWinningLines() {
        return playerWinningLines.size() > 0;
    }

    /**
     * determines cells of computer for winning moves
     * @return
     */
    public boolean areComputerWinningLines() {
        return computerWinningLines.size() > 0;
    }

    /**
     * determines true or false for fork lines in computer moves
     * @return
     */
    public boolean areComputerForkLines() {
        return computerForkLines.size() > 1;
    }

    /**
     * determines true or false for fork lines in player moves
     * @return
     */
    public boolean arePlayerForkLines() {
        return playerHalfForkLines.size() > 1;
    }

    /**
     * determines true or false for empty moves in player
     * @return
     */
    public boolean arePlayerEmptyLines() {
        return playerEmptyLines.size() > 0;
    }

    /**
     * get first computer line
     * @return
     */
    public Line getFirstComputerWinningLine() {
        return computerWinningLines.get(0);
    }

    /**
     * get first player winning line
     * @return
     */
    public Line getFirstPlayerWinningLine() {
        return playerWinningLines.get(0);
    }

    /**
     * gets computer fork line
     * @return
     */
    public Line getComputerForkLine(int index) {
        return computerForkLines.get(index);
    }

    /**
     * gets player fork line
     * @return
     */
    public Line getPlayerForkLine(int index) {
        return playerHalfForkLines.get(index);
    }

    public Line getRandomEmptyLine() {
        int random = new Random().nextInt(playerEmptyLines.size());
        Line emptyLine = playerEmptyLines.get(random);
        return emptyLine;
    }


    private void clearAllLists() {
        playerWinningLines.clear();
        computerWinningLines.clear();
        computerForkLines.clear();
        computerHalfForkLines.clear();
        playerHalfForkLines.clear();
        playerEmptyLines.clear();
    }

    private void addPlayerWinningLine(Line winningLine) {
        playerWinningLines.add(winningLine);
    }

    private void addComputerWinningLines(Line winningLine) {
        computerWinningLines.add(winningLine);
    }

    /**
     * add computer, looks for fork on the playing board
     * @param line
     */
    private void addComputerHalfForksAndForks(Line line) {
        computerHalfForkLines.add(line);
        if (computerHalfForkLines.size() > 1) {
            for (int j = 0; j < computerHalfForkLines.size(); j++) {
                if (Line.haveCommonPoint(cells,computerHalfForkLines.get(0),computerHalfForkLines.get(1))) {
                    computerForkLines.add(computerHalfForkLines.get(0));
                    computerForkLines.add(computerHalfForkLines.get(1));
                }
            }
        }
    }

    private void addPlayerEmptyLine(Line emptyLine) {
        playerEmptyLines.add(emptyLine);
    }

    private void addPlayerHalfForksLine(Line halfFork) {
        playerHalfForkLines.add(halfFork);
    }

    private ArrayList<Line> playerEmptyLines = new ArrayList<Line>();

    private ArrayList<Line> playerHalfForkLines = new ArrayList<Line>();

    private ArrayList<Line> playerWinningLines = new ArrayList<Line>();

    private ArrayList<Line> computerWinningLines = new ArrayList<Line>();

    private ArrayList<Line> computerForkLines = new ArrayList<Line>();

    private ArrayList<Line> computerHalfForkLines = new ArrayList<Line>();

    private final Line[] lines = {
            new Line(new int[][]{{0, 0, 0}, {0, 0, 1}, {0, 0, 2}, {0, 0, 3}}),  //lev 0; row 0   rows in each lev
            new Line(new int[][]{{0, 1, 0}, {0, 1, 1}, {0, 1, 2}, {0, 1, 3}}),  //       row 1
            new Line(new int[][]{{0, 2, 0}, {0, 2, 1}, {0, 2, 2}, {0, 2, 3}}),  //       row 2
            new Line(new int[][]{{0, 3, 0}, {0, 3, 1}, {0, 3, 2}, {0, 3, 3}}),  //       row 3
            new Line(new int[][]{{1, 0, 0}, {1, 0, 1}, {1, 0, 2}, {1, 0, 3}}),  //lev 1; row 0
            new Line(new int[][]{{1, 1, 0}, {1, 1, 1}, {1, 1, 2}, {1, 1, 3}}),  //       row 1
            new Line(new int[][]{{1, 2, 0}, {1, 2, 1}, {1, 2, 2}, {1, 2, 3}}),  //       row 2
            new Line(new int[][]{{1, 3, 0}, {1, 3, 1}, {1, 3, 2}, {1, 3, 3}}),  //       row 3
            new Line(new int[][]{{2, 0, 0}, {2, 0, 1}, {2, 0, 2}, {2, 0, 3}}),  //lev 2; row 0
            new Line(new int[][]{{2, 1, 0}, {2, 1, 1}, {2, 1, 2}, {2, 1, 3}}),  //       row 1
            new Line(new int[][]{{2, 2, 0}, {2, 2, 1}, {2, 2, 2}, {2, 2, 3}}),  //       row 2
            new Line(new int[][]{{2, 3, 0}, {2, 3, 1}, {2, 3, 2}, {2, 3, 3}}),  //       row 3
            new Line(new int[][]{{3, 0, 0}, {3, 0, 1}, {3, 0, 2}, {3, 0, 3}}),  //lev 3; row 0
            new Line(new int[][]{{3, 1, 0}, {3, 1, 1}, {3, 1, 2}, {3, 1, 3}}),  //       row 1
            new Line(new int[][]{{3, 2, 0}, {3, 2, 1}, {3, 2, 2}, {3, 2, 3}}),  //       row 2
            new Line(new int[][]{{3, 3, 0}, {3, 3, 1}, {3, 3, 2}, {3, 3, 3}}),  //       row 3
            new Line(new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 2, 0}, {0, 3, 0}}),  //lev 0; col 0   cols in each lev
            new Line(new int[][]{{0, 0, 1}, {0, 1, 1}, {0, 2, 1}, {0, 3, 1}}),  //       col 1
            new Line(new int[][]{{0, 0, 2}, {0, 1, 2}, {0, 2, 2}, {0, 3, 2}}),  //       col 2
            new Line(new int[][]{{0, 0, 3}, {0, 1, 3}, {0, 2, 3}, {0, 3, 3}}),  //       col 3
            new Line(new int[][]{{1, 0, 0}, {1, 1, 0}, {1, 2, 0}, {1, 3, 0}}),  //lev 1; col 0
            new Line(new int[][]{{1, 0, 1}, {1, 1, 1}, {1, 2, 1}, {1, 3, 1}}),  //       col 1
            new Line(new int[][]{{1, 0, 2}, {1, 1, 2}, {1, 2, 2}, {1, 3, 2}}),  //       col 2
            new Line(new int[][]{{1, 0, 3}, {1, 1, 3}, {1, 2, 3}, {1, 3, 3}}),  //       col 3
            new Line(new int[][]{{2, 0, 0}, {2, 1, 0}, {2, 2, 0}, {2, 3, 0}}),  //lev 2; col 0
            new Line(new int[][]{{2, 0, 1}, {2, 1, 1}, {2, 2, 1}, {2, 3, 1}}),  //       col 1
            new Line(new int[][]{{2, 0, 2}, {2, 1, 2}, {2, 2, 2}, {2, 3, 2}}),  //       col 2
            new Line(new int[][]{{2, 0, 3}, {2, 1, 3}, {2, 2, 3}, {2, 3, 3}}),  //       col 3
            new Line(new int[][]{{3, 0, 0}, {3, 1, 0}, {3, 2, 0}, {3, 3, 0}}),  //lev 3; col 0
            new Line(new int[][]{{3, 0, 1}, {3, 1, 1}, {3, 2, 1}, {3, 3, 1}}),  //       col 1
            new Line(new int[][]{{3, 0, 2}, {3, 1, 2}, {3, 2, 2}, {3, 3, 2}}),  //       col 2
            new Line(new int[][]{{3, 0, 3}, {3, 1, 3}, {3, 2, 3}, {3, 3, 3}}),  //       col 3
            new Line(new int[][]{{0, 0, 0}, {1, 0, 0}, {2, 0, 0}, {3, 0, 0}}),  //cols in vert plane in front
            new Line(new int[][]{{0, 0, 1}, {1, 0, 1}, {2, 0, 1}, {3, 0, 1}}),
            new Line(new int[][]{{0, 0, 2}, {1, 0, 2}, {2, 0, 2}, {3, 0, 2}}),
            new Line(new int[][]{{0, 0, 3}, {1, 0, 3}, {2, 0, 3}, {3, 0, 3}}),
            new Line(new int[][]{{0, 1, 0}, {1, 1, 0}, {2, 1, 0}, {3, 1, 0}}),  //cols in vert plane one back
            new Line(new int[][]{{0, 1, 1}, {1, 1, 1}, {2, 1, 1}, {3, 1, 1}}),
            new Line(new int[][]{{0, 1, 2}, {1, 1, 2}, {2, 1, 2}, {3, 1, 2}}),
            new Line(new int[][]{{0, 1, 3}, {1, 1, 3}, {2, 1, 3}, {3, 1, 3}}),
            new Line(new int[][]{{0, 2, 0}, {1, 2, 0}, {2, 2, 0}, {3, 2, 0}}),  //cols in vert plane two back
            new Line(new int[][]{{0, 2, 1}, {1, 2, 1}, {2, 2, 1}, {3, 2, 1}}),
            new Line(new int[][]{{0, 2, 2}, {1, 2, 2}, {2, 2, 2}, {3, 2, 2}}),
            new Line(new int[][]{{0, 2, 3}, {1, 2, 3}, {2, 2, 3}, {3, 2, 3}}),
            new Line(new int[][]{{0, 3, 0}, {1, 3, 0}, {2, 3, 0}, {3, 3, 0}}),  //cols in vert plane in rear
            new Line(new int[][]{{0, 3, 1}, {1, 3, 1}, {2, 3, 1}, {3, 3, 1}}),
            new Line(new int[][]{{0, 3, 2}, {1, 3, 2}, {2, 3, 2}, {3, 3, 2}}),
            new Line(new int[][]{{0, 3, 3}, {1, 3, 3}, {2, 3, 3}, {3, 3, 3}}),
            new Line(new int[][]{{0, 0, 0}, {0, 1, 1}, {0, 2, 2}, {0, 3, 3}}),  //diags in lev 0
            new Line(new int[][]{{0, 3, 0}, {0, 2, 1}, {0, 1, 2}, {0, 0, 3}}),
            new Line(new int[][]{{1, 0, 0}, {1, 1, 1}, {1, 2, 2}, {1, 3, 3}}),  //diags in lev 1
            new Line(new int[][]{{1, 3, 0}, {1, 2, 1}, {1, 1, 2}, {1, 0, 3}}),
            new Line(new int[][]{{2, 0, 0}, {2, 1, 1}, {2, 2, 2}, {2, 3, 3}}),  //diags in lev 2
            new Line(new int[][]{{2, 3, 0}, {2, 2, 1}, {2, 1, 2}, {2, 0, 3}}),
            new Line(new int[][]{{3, 0, 0}, {3, 1, 1}, {3, 2, 2}, {3, 3, 3}}),  //diags in lev 3
            new Line(new int[][]{{3, 3, 0}, {3, 2, 1}, {3, 1, 2}, {3, 0, 3}}),
            new Line(new int[][]{{0, 0, 0}, {1, 0, 1}, {2, 0, 2}, {3, 0, 3}}),  //diags in vert plane in front
            new Line(new int[][]{{3, 0, 0}, {2, 0, 1}, {1, 0, 2}, {0, 0, 3}}),
            new Line(new int[][]{{0, 1, 0}, {1, 1, 1}, {2, 1, 2}, {3, 1, 3}}),  //diags in vert plane one back
            new Line(new int[][]{{3, 1, 0}, {2, 1, 1}, {1, 1, 2}, {0, 1, 3}}),
            new Line(new int[][]{{0, 2, 0}, {1, 2, 1}, {2, 2, 2}, {3, 2, 3}}),  //diags in vert plane two back
            new Line(new int[][]{{3, 2, 0}, {2, 2, 1}, {1, 2, 2}, {0, 2, 3}}),
            new Line(new int[][]{{0, 3, 0}, {1, 3, 1}, {2, 3, 2}, {3, 3, 3}}),  //diags in vert plane in rear
            new Line(new int[][]{{3, 3, 0}, {2, 3, 1}, {1, 3, 2}, {0, 3, 3}}),
            new Line(new int[][]{{0, 0, 0}, {1, 1, 0}, {2, 2, 0}, {3, 3, 0}}),  //diags left slice
            new Line(new int[][]{{3, 0, 0}, {2, 1, 0}, {1, 2, 0}, {0, 3, 0}}),
            new Line(new int[][]{{0, 0, 1}, {1, 1, 1}, {2, 2, 1}, {3, 3, 1}}),  //diags slice one to right
            new Line(new int[][]{{3, 0, 1}, {2, 1, 1}, {1, 2, 1}, {0, 3, 1}}),
            new Line(new int[][]{{0, 0, 2}, {1, 1, 2}, {2, 2, 2}, {3, 3, 2}}),  //diags slice two to right
            new Line(new int[][]{{3, 0, 2}, {2, 1, 2}, {1, 2, 2}, {0, 3, 2}}),
            new Line(new int[][]{{0, 0, 3}, {1, 1, 3}, {2, 2, 3}, {3, 3, 3}}),  //diags right slice
            new Line(new int[][]{{3, 0, 3}, {2, 1, 3}, {1, 2, 3}, {0, 3, 3}}),
            new Line(new int[][]{{0, 0, 0}, {1, 1, 1}, {2, 2, 2}, {3, 3, 3}}),  //cube vertex diags
            new Line(new int[][]{{3, 0, 0}, {2, 1, 1}, {1, 2, 2}, {0, 3, 3}}),
            new Line(new int[][]{{0, 3, 0}, {1, 2, 1}, {2, 1, 2}, {3, 0, 3}}),
            new Line(new int[][]{{3, 3, 0}, {2, 2, 1}, {1, 1, 2}, {0, 0, 3}})
    };
}


class Line
{
    public int[][] lineCoordinates = new int[4][3];

    Line(int[][] lineCoordinates)
    {
        this.lineCoordinates = lineCoordinates;
    }


    public int getCoordinateValue(State[][][] cells, int index)
    {
        int[] coords = this.lineCoordinates[index];
        return cells[coords[0]][coords[1]][coords[2]].getValue();
    }

    public int getLineValue(State[][][] cells)
    {
        int sum = 0;
        for (int i = 0; i < this.lineCoordinates.length; i++)
        {
            sum += this.getCoordinateValue(cells,i);
        }
        return sum;
    }

    /**
     * compares points to find common
     * @param line
     * @return null
     */
    public int[] getCommonPoint(Line line)
    {
        for (int i = 0; i < this.lineCoordinates.length; i++)
        {
            for (int j = 0; j < this.lineCoordinates.length; j++)
            {
                boolean result = Arrays.equals(this.lineCoordinates[i], line.lineCoordinates[j]);
                if (result)
                {
                    return lineCoordinates[i];
                }
            }
        }
        return null;
    }

    /**
     * Boolean method to find if common point is real or not
     * @param cells
     * @param line1
     * @param line2
     * @return
     */
    public static boolean haveCommonPoint(State[][][] cells,Line line1, Line line2)
    {
        for (int i = 0; i < line1.lineCoordinates.length; i++)
        {
            for (int j = 0; j < line1.lineCoordinates.length; j++)
            {
                boolean result = Arrays.equals(line1.lineCoordinates[i], line2.lineCoordinates[j]);
                if (result && line1.getCoordinateValue(cells,i) == 0)
                {
                    return result;
                }
            }
        }
        return false;
    }

    public static int getCoordinateValue(State[][][] cells,int[] coords)
    {
        return cells[coords[0]][coords[1]][coords[2]].getValue();
    }
}

/**
 * Class State, an enumeration, a value
 * Hold values for player and computer and empty
 */
enum State {
    Player(5),
    Computer(1),
    Empty(0);

    public int value;

    State(int value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        switch (this)
        {
            case Empty:
                return "-";
            case Player:
                return "X";
            case Computer:
                return "O";
            default: return null;
        }
    }

    public int getValue() {return value;}
}

/**
 * TTT3D is the key class to this entire program.
 * Uses Board class to print board, and create off the file
 * Scanner to get the move from the user, also uses the 3D array
 * contains prompt for the user
 * Moves on for the computer and its moves
 * Each contain methods that are called in specific situations
 *
 */
class TTT3D {
    Board board = new Board();
    Scanner scanner = new Scanner(System.in);
    State[][][] cells = board.getCells();//fills this array with values from array in board class


    public TTT3D(String filename) {
        board.createBoard(filename);
        board.printBoard();
    }
    public TTT3D() {
        board.makeBoard();
        board.printBoard();
    }

    /**
     * Prompts user to get his/her choice in line, row, column
     * saves those coordinates in an array
     * if/else statement to make sure move is viable
     *
     */
    public void getPlayerMove() {
        System.out.println("Type your move as one three digit number(lrc):");//prompts user to decide where to play
        String move = scanner.next();
        int[] coords = stringToCoords(move);//saves the coordinates in array
        if (board.isEmpty(coords)) {
            board.playerCell(coords);//calls board and sets up cells based on coords
        }
        else {
            System.out.println("This cell is occupied");
            getPlayerMove();
        }
    }

    /**
     * This method gets the computers move
     * Uses scanWinningLines to look for wins
     * Calls board obviously to find moves
     * Calls specific methods based on specific situation
     */
    public void getComputerMove() {
        board.scanWinningLines();
        if (board.playerWon) {
            printComputerDefeatMessage();//print win meessage
        }
        else if (board.areComputerWinningLines()) {

            finishComputerWinningLine();
        }
        else if (board.arePlayerWinningLines()) {
            blockPlayerWinningLine();//calls method to block player
        }

        else if (board.areComputerForkLines()) {
            createComputerForkLine();//method that creates a fork for the player
        }

        else if (board.arePlayerForkLines()) {
            tryToBlockPlayerForkLine();//if fork created, try to block move around it
        }

        else if (board.arePlayerEmptyLines()) {
            makeRandomComputerMove();
        }
    }

    /**
     * Basic method that prints message is computer is defeated
     * Exits the program if player wins
     */
    private void printComputerDefeatMessage() {
        board.printBoard();
        System.out.println("Player won");//print for user
        System.exit(1);
    }

    /**
     * This method turns a three character string of numbers into an array of coordinates
     * corresponding to a cell on the board that is unoccupied
     * @param string, integers that have been parsed
     * @return, level, row, column
     */
    private int[] stringToCoords(String string) {
        int value = Integer.parseInt(string);//gets integers and converts them to strings
        int level = value/100;
        int row = ((value % 100)/10);
        int column = value % 10;
        return new int[]{level,row,column};
    }

    /**
     * Method is called when the computer can make a winning move immediately. The cell on the board will be
     * filled and the computer's win message will be printed
     */
    private void finishComputerWinningLine() {
        Line winningLine = board.getFirstComputerWinningLine();
        for (int i = 0; i < winningLine.lineCoordinates.length;i++) {
            if (winningLine.getCoordinateValue(cells ,i) == 0) {
                board.computerCell(winningLine.lineCoordinates[i]);
                board.printBoard();
                System.out.println("Computer wins again!");
                System.exit(1);
            }
        }
    }

    /**
     * This method will allow the computer to block a winning move by the player by filling th winning cell with an
     * '0' to represent the computer's tokens
     */
    private void blockPlayerWinningLine() {
        Line playerWinningLine = board.getFirstPlayerWinningLine();

        for (int i = 0; i < playerWinningLine.lineCoordinates.length;i++) {
            if (playerWinningLine.getCoordinateValue(cells,i) == 0) {
                board.computerCell(playerWinningLine.lineCoordinates[i]);
                return;
            }
        }
    }

    /**
     * This method will create a fork against the player for the computer. Then will result in two
     * possible winning moves and should create a win for the computer in the next turn
     */
    private void createComputerForkLine() {
        Line forkLine1 = board.getComputerForkLine(0);
        Line forkLine2 = board.getComputerForkLine(1);
        int[] coords = forkLine1.getCommonPoint(forkLine2);
        if (Line.getCoordinateValue(cells,coords) == 0) {
            board.computerCell(coords);
            return;
        }
    }

    /**
     * Lowest level for the computer's move. This will generate a set of random set of coordinates
     * and will make the computer to place a 0 on the board in that position
     */
    private void makeRandomComputerMove() {
        Line emptyLine = board.getRandomEmptyLine();
        for (int i = 0; i < emptyLine.lineCoordinates.length;i++) {
            if (emptyLine.getCoordinateValue(cells,i) == 0) {
                board.computerCell(emptyLine.lineCoordinates[i]);
                return;
            }
        }
    }

    /**
     * This method will block any forks that the player creates with their placements. If the player
     * has a situation that causes two possible winning moves on the next turn, method will be called to blocks move
     */
    private void tryToBlockPlayerForkLine() {
        Line halfForkLine1 = board.getPlayerForkLine(0);
        Line halfForkLine2 = board.getPlayerForkLine(1);
        int[] coords = halfForkLine1.getCommonPoint(halfForkLine2);
        if (coords != null && Line.getCoordinateValue(cells,coords) == 0) {
            board.computerCell(coords);
            return;
        }
        else if (board.arePlayerEmptyLines()) {
            makeRandomComputerMove();
        }
    }
}
