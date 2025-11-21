package app.model;

public class Board {
    private final char[][] grid = new char[3][3]; // '\0' means empty

    public Board() {
        reset();
    }

    public void reset() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                grid[r][c] = '\0';
            }
        }
    }

    public boolean isCellEmpty(int row, int col) {
        return grid[row][col] == '\0';
    }

    public boolean placeMark(int row, int col, char mark) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3) return false;
        if (!isCellEmpty(row, col)) return false;
        grid[row][col] = mark;
        return true;
    }

    public char getMark(int row, int col) {
        return grid[row][col];
    }

    public boolean hasWin(char mark) {
        // rows & cols
        for (int i = 0; i < 3; i++) {
            if (grid[i][0] == mark && grid[i][1] == mark && grid[i][2] == mark) return true;
            if (grid[0][i] == mark && grid[1][i] == mark && grid[2][i] == mark) return true;
        }
        // diagonals
        return (grid[0][0] == mark && grid[1][1] == mark && grid[2][2] == mark) ||
               (grid[0][2] == mark && grid[1][1] == mark && grid[2][0] == mark);
    }

    public boolean isFull() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (grid[r][c] == '\0') return false;
            }
        }
        return true;
    }

    public char[][] snapshot() {
        char[][] copy = new char[3][3];
        for (int r = 0; r < 3; r++) {
            System.arraycopy(grid[r], 0, copy[r], 0, 3);
        }
        return copy;
    }
}
