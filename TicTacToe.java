import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class TicTacToe {

Scanner k = new Scanner(System.in);
        
int row = 3;
int col = 3;

char[][] board = new char[row][col];

public void fillBoard() {
    for(int i = 0; i < board.length; i++) {
        for(int j = 0; j < board[i].length; j++) {
            board[i][j] = '-';
        }
    }
}


public void printBoard(char[][] boardToPrint) {
System.out.println("    0 1 2");
System.out.println("  ---------");
    for(int i = 0; i < boardToPrint.length; i++) {

        System.out.print(i + " ");
              System.out.print("| ");
        for(int j = 0; j <boardToPrint[i].length; j++) {
             System.out.print(boardToPrint[i][j] + " ");
        }
        System.out.print("|");
        System.out.println();
    }
    System.out.println("  ---------");
}

public int[] getMove() {
    System.out.println("What is your moves row?");
        int xCoord = k.nextInt();
    System.out.println("What is your moves column?");
        int yCoord = k.nextInt();

    int[] move = {xCoord, yCoord};
    return move;
}

public char[][] makeMove(char[][] boardToChange, int[] moveCoords, char player) {

    int row = moveCoords[0];
    int col = moveCoords[1];

    if (!isValidMove(boardToChange, moveCoords)) {
        throw new IllegalArgumentException( "Can't make move (" + row + ", " + col + "), square already taken!");
    }


    char[][] newBoard = new char[boardToChange.length][boardToChange[0].length];
    
    for (int i = 0; i < boardToChange.length; i++) {
        for (int j = 0; j < boardToChange[i].length; j++) {
            newBoard[i][j] = boardToChange[i][j];
        }
    }

    newBoard[row][col] = player;

    return newBoard;
}


public boolean isValidMove(char[][] board, int[] moveCoords) {
    int row = moveCoords[0];
    int col = moveCoords[1];

    if (row < 0 || row >= board.length) {
        return false;
    }

    if (col < 0 || col >= board[0].length) {
        return false;
    }

    return board[row][col] == '-';
}

public Character getWinner(char[][] boardToCheck) {
   for (int i = 0; i < 3; i++) {
        if (boardToCheck[i][0] == boardToCheck[i][1] && boardToCheck[i][1] == boardToCheck[i][2] && boardToCheck[i][0] != '-') {
        return boardToCheck[i][0];
        }
    }

    for (int i = 0; i < 3; i++) {
        if (boardToCheck[0][i] == boardToCheck[1][i] && boardToCheck[1][i] == boardToCheck[2][i] && boardToCheck[0][i] != '-') {
            return boardToCheck[0][i];
        }
    }

    if (boardToCheck[0][0] == boardToCheck[1][1] && boardToCheck[1][1] == boardToCheck[2][2] && boardToCheck[0][0] != '-') {
        return boardToCheck[0][0];
    }
    
    if (boardToCheck[0][2] == boardToCheck[1][1] && boardToCheck[1][1] == boardToCheck[2][0] && boardToCheck[0][2] != '-') {
        return boardToCheck[0][2];
    }
return null;
}

public int[] randomAI(char[][] boardToChange, char player) {
    ArrayList<int[]> legalMoves = new ArrayList<>();

    for (int i = 0; i < boardToChange.length; i++) {
        for (int j = 0; j < boardToChange[i].length; j++) {
            if (boardToChange[i][j] == '-') {
                legalMoves.add(new int[]{i, j});
            }
        }
    }
    Random rand = new Random();
    int randomIndex = rand.nextInt(legalMoves.size());

    return legalMoves.get(randomIndex);
}

public int[] findWinningMoveAi(char[][] boardToChange, char player) {
    for (int i = 0; i < boardToChange.length; i++) {
        for (int j = 0; j < boardToChange[i].length; j++) {
            if (boardToChange[i][j] == '-') {
                int[] move = {i, j};

                char[][] newBoard = makeMove(boardToChange, move, player);

                Character winner = getWinner(newBoard);
                if (winner != null && winner == player) {
                    return move;
                }
            }
        }
    }
    return randomAI(boardToChange, player);
}

public int[] findImmediateWinningMove(char[][] boardToCheck, char player) {
    for (int i = 0; i < boardToCheck.length; i++) {
        for (int j = 0; j < boardToCheck[i].length; j++) {
            if (boardToCheck[i][j] == '-') {
                int[] move = {i, j};

                char[][] newBoard = makeMove(boardToCheck, move, player);

                Character winner = getWinner(newBoard);
                if (winner != null && winner == player) {
                    return move;
                }
            }
        }
    }
    return null;
}

public int[] findWinningAndLosingMovesAi(char[][] boardToChange, char player) {
    int[] winningMove = findImmediateWinningMove(boardToChange, player);
    if (winningMove != null) {
        return winningMove;
    }

    char opponent = (player == 'X') ? 'O' : 'X';
    int[] blockingMove = findImmediateWinningMove(boardToChange, opponent);
    if (blockingMove != null) {
        return blockingMove;
    }


    return randomAI(boardToChange, player);
}

public int[] humanPlayer(char[][] boardToChange, char player) {
    
    while (true) {
        int[] move = getMove();

        if (isValidMove(boardToChange, move)) {
            return move;
        } else {
            System.out.println("Invalid move. Please try again.");
        }
    }
}

public ArrayList<int[]> getLegalMoves(char[][] boardToChange) {
    ArrayList<int[]> legalMoves = new ArrayList<>();
    for (int i = 0; i < boardToChange.length; i++) {
        for (int j = 0; j < boardToChange[i].length; j++) {
            if (boardToChange[i][j] == '-') {
                legalMoves.add(new int[]{i, j});
            }
        }
    }
    return legalMoves;
}

public boolean isBoardFull(char[][] boardToChange) {
    for (int i = 0; i < boardToChange.length; i++) {
        for (int j = 0; j < boardToChange[i].length; j++) {
            if (boardToChange[i][j] == '-') {
                return false;
            }
        }
    }
    return true;
}

public char getOpponent(char player) {
    return (player == 'X') ? 'O' : 'X';
}



public int minimaxScore(char[][] boardToChange, char currentPlayer, char aiPlayer) {
    Character winner = getWinner(boardToChange);

    if (winner != null && winner == aiPlayer) {
        return 10;
    } else if (winner != null && winner != aiPlayer) {
        return -10;
    } else if (isBoardFull(boardToChange)) {
        return 0;
    }

    ArrayList<int[]> legalMoves = getLegalMoves(boardToChange);

        if (currentPlayer == aiPlayer) {
            int bestScore = Integer.MIN_VALUE;
            for (int[] move : legalMoves) {
                char[][] newBoard = makeMove(boardToChange, move, currentPlayer);
                int score = minimaxScore(newBoard, getOpponent(currentPlayer), aiPlayer);
                
                if (score > bestScore) {
                    bestScore = score;
                }
            } 
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int[] move : legalMoves) {
                char[][] newBoard = makeMove(boardToChange, move, currentPlayer);
                int score = minimaxScore(newBoard, getOpponent(currentPlayer), aiPlayer);

                if (score < bestScore) {
                    bestScore = score;
                }
            }
            return bestScore;
        }
}

public int[] minimaxAi(char[][] boardToChange, char player) {

    ArrayList<int[]> legalMoves = getLegalMoves(boardToChange);

    int bestScore = Integer.MIN_VALUE;
    int[] bestMove = null;

    for (int[] move : legalMoves) {
        char[][] newBoard = makeMove(boardToChange, move, player);
        int score = minimaxScore(newBoard, getOpponent(player), player);
        if (score > bestScore) {
            bestScore = score;
            bestMove = move;
        }
    }
    return bestMove;
}

public static void main(String[] args) {
    TicTacToe game = new TicTacToe();

    game.fillBoard();

    String xPlayer = "human";
    String oPlayer = "minimax_ai";

    for (int turn = 0; turn < 9; turn++) {
    game.printBoard(game.board);

    char currentPlayer;

        if (turn % 2 == 0) {
            currentPlayer = 'X';
        } else {
            currentPlayer = 'O';
        }

    String currentPlayerName;

      if (currentPlayer == 'X') {
        currentPlayerName = xPlayer;
    } else {
        currentPlayerName = oPlayer;
    }

    int[] move;

    System.out.println("Player " + currentPlayerName + " using " + currentPlayer + " is moving.");

    if (currentPlayerName.equals("human")) {
        move = game.humanPlayer(game.board, currentPlayer);
    } else if (currentPlayerName.equals("random_ai")) {
        move = game.randomAI(game.board, currentPlayer);
    } else if (currentPlayerName.equals("find_winning_moves_ai")) {
        move = game.findWinningMoveAi(game.board, currentPlayer);
    } else if (currentPlayerName.equals("find_winning_and_losing_moves_ai")) {
        move = game.findWinningAndLosingMovesAi(game.board, currentPlayer);
    } else if (currentPlayerName.equals("minimax_ai")) {
        move = game.minimaxAi(game.board, currentPlayer);
    } else {
        throw new IllegalArgumentException("Unknown player type: " + currentPlayerName);
    }

        

        game.board = game.makeMove(game.board, move, currentPlayer);

        Character winner = game.getWinner(game.board);

        if (winner != null) {
            game.printBoard(game.board);
            System.out.println("Player " + winner + " wins!");
            return;
        }
    }

    game.printBoard(game.board);
    System.out.println("It's a draw!");
}
}












    




    
