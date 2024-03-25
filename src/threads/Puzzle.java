package threads;

public class Puzzle {
    public static int ROWS = 8;
    public static int COLUMNS = 6;
    public Object rows;

    private char[][] board = new char[ROWS][COLUMNS];

    public char at(int row, int column) {
        return board[row][column];
    }

    private Puzzle() {

    }

    public static Puzzle load(String encoding) {
        Puzzle puzzle = new Puzzle();
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                puzzle.board[row][column] = (char) (encoding.charAt(row * COLUMNS + column) + 32);
            }
        }
        return puzzle;
    }

    public boolean inRange(int row, int column) {
        return row >= 0 && row < ROWS && column >= 0 && column < COLUMNS;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                builder.append(at(row, column));
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
