package t0l1k.ttt;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private static final char EMPTY = ' ';
    private static final char X = 'X';
    private static final char O = 'O';
    private static final int dim = 3;
    private static final int size = dim * dim;
    private char[] board;
    private char turn;
    private boolean isBegin;

    public Board() {
        this.board = "         ".toCharArray();
        this.turn = X;
        if (possibleMoves().length < size - 1) {
            isBegin = true;
        }
    }

    public Board(char[] board, char turn) {
        this.board = board;
        this.turn = turn;
        if (possibleMoves().length < size - 1) {
            isBegin = true;
        }
    }

    public Board(String board) {
        this.board = board.toCharArray();
        this.turn = X;
        if (possibleMoves().length < size - 1) {
            isBegin = true;
        }
    }

    public Board(String s, char turn) {
        this.board = s.toCharArray();
        this.turn = turn;
        if (possibleMoves().length < size - 1) {
            isBegin = true;
        }
    }

    public char[] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return new String(board);
    }

    public char getTurn() {
        return turn;
    }

    public boolean isBegin() {
        return isBegin;
    }

    public Board move(int idx) {
        if (!isBegin) {
            isBegin = true;
        }
        char[] newBoard = board.clone();
        newBoard[idx] = turn;
        return new Board(newBoard, turn == X ? O : X);
    }

    public Integer[] possibleMoves() {
        List<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == EMPTY) {
                list.add(i);
            }
        }
        Integer[] arr = new Integer[list.size()];
        list.toArray(arr);
        return arr;
    }

    public boolean win(char turn) {
        for (int i = 0; i < dim; i++) {
            if (winLine(turn, i * dim, 1) || winLine(turn, i, dim)) {
                return true;
            }
        }
        if (winLine(turn, dim - 1, dim - 1) || winLine(turn, 0, dim + 1)) {
            return true;
        }
        return false;
    }

    private boolean winLine(char turn, int start, int step) {
        for (int i = 0; i < dim; i++) {
            if (board[start + step * i] != turn) {
                return false;
            }
        }
        return true;
    }

    public int getSize() {
        return size;
    }

    public boolean gameEnd() {
        return win(X) || win(O) || possibleMoves().length == 0;
    }

    public int minimax() {
        if (win(X)) {
            return 100;
        }
        if (win(O)) {
            return -100;
        }
        if (possibleMoves().length == 0) {
            return 0;
        }
        Integer mm = null;
        for (Integer idx : possibleMoves()) {
            Integer value = move(idx).minimax();
            if (mm == null || turn == X && mm < value || turn == O && value < mm) {
                mm = value;
            }
        }
        return mm + (turn == X ? -1 : 1);
    }

    public int bestMove() {
        Integer mm = null;
        int best = -1;
        for (Integer idx : possibleMoves()) {
            Integer value = move(idx).minimax();
            if (mm == null || turn == X && mm < value || turn == O && value < mm) {
                mm = value;
                best = idx;
            }
        }
        return best;
    }
}
