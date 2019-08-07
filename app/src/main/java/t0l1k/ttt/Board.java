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

    public Board() {
        this.board = "         ".toCharArray();
        this.turn = X;
    }

    public Board(char[] board, char turn) {
        this.board = board;
        this.turn = turn;
    }

    public Board(String board) {
        this.board = board.toCharArray();
        this.turn = X;
    }

    @Override
    public String toString() {
        return new String(board);
    }

    public char getTurn() {
        return turn;
    }

    public Board move(int idx) {
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
}
