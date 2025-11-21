package app.ui;

import app.service.GameService;
import app.service.MatchResult;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BoardPanel extends JPanel {

    private final JButton[][] buttons = new JButton[3][3];
    private final GameService gameService;
    private final StatusBar statusBar;
    private final Runnable onGameFinished;

    public BoardPanel(GameService gameService, StatusBar statusBar, Runnable onGameFinished) {
        this.gameService = gameService;
        this.statusBar = statusBar;
        this.onGameFinished = onGameFinished;

        initUi();
        refreshFromService();
    }

    private void initUi() {
        setLayout(new GridLayout(3, 3, 8, 8));
        setBackground(new Color(30, 30, 30));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Font font = new Font("SansSerif", Font.BOLD, 60);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                final int row = r;
                final int col = c;

                JButton btn = new JButton("");
                btn.setFont(font);
                btn.setFocusPainted(false);
                btn.setBackground(new Color(240, 240, 240));
                btn.setOpaque(true);
                btn.setBorder(new LineBorder(new Color(80, 80, 80), 2));
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                btn.addActionListener(e -> handleMove(row, col));

                buttons[r][c] = btn;
                add(btn);
            }
        }
    }

    private void handleMove(int row, int col) {
        boolean success = gameService.makeMove(row, col);
        if (!success) {
            // Either invalid move or game already finished
            return;
        }

        refreshFromService();

        if (gameService.isGameOver()) {
            highlightWinningLine();   // make winning combo glow
            showGameOverDialog();
            if (onGameFinished != null) {
                onGameFinished.run();
            }
        }
    }

    public void refreshFromService() {
        char[][] grid = gameService.getBoardSnapshot();

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                char mark = grid[r][c];
                JButton btn = buttons[r][c];

                if (mark == '\0') {
                    btn.setText("");
                    btn.setForeground(Color.BLACK);
                    btn.setBackground(new Color(240, 240, 240));
                } else {
                    btn.setText(String.valueOf(mark));
                    if (mark == 'X') {
                        btn.setForeground(new Color(46, 134, 222)); // blue for X
                    } else {
                        btn.setForeground(new Color(231, 76, 60));  // red for O
                    }
                }
            }
        }

        if (!gameService.isGameOver()) {
            char turn = gameService.getCurrentMark();
            String name = gameService.getPlayerNameForMark(turn);
            statusBar.setMessage("Turn: " + name + " (" + turn + ")");
        }
    }

    private void showGameOverDialog() {
        MatchResult result = gameService.getResult();
        String message;

        switch (result) {
            case X_WIN -> message = gameService.getPlayerNameForMark('X') + " (X) wins!";
            case O_WIN -> message = gameService.getPlayerNameForMark('O') + " (O) wins!";
            case DRAW -> message = "It's a draw!";
            default -> message = "Game over.";
        }

        JOptionPane.showMessageDialog(
                this,
                message,
                "Game Finished",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Highlight the 3-in-a-row when someone wins
    private void highlightWinningLine() {
        MatchResult result = gameService.getResult();
        if (result != MatchResult.X_WIN && result != MatchResult.O_WIN) {
            return;
        }

        char winnerMark = (result == MatchResult.X_WIN) ? 'X' : 'O';
        char[][] grid = gameService.getBoardSnapshot();

        // rows
        for (int r = 0; r < 3; r++) {
            if (grid[r][0] == winnerMark && grid[r][1] == winnerMark && grid[r][2] == winnerMark) {
                highlightButtons(new int[][]{{r,0},{r,1},{r,2}});
                return;
            }
        }
        // cols
        for (int c = 0; c < 3; c++) {
            if (grid[0][c] == winnerMark && grid[1][c] == winnerMark && grid[2][c] == winnerMark) {
                highlightButtons(new int[][]{{0,c},{1,c},{2,c}});
                return;
            }
        }
        // main diag
        if (grid[0][0] == winnerMark && grid[1][1] == winnerMark && grid[2][2] == winnerMark) {
            highlightButtons(new int[][]{{0,0},{1,1},{2,2}});
            return;
        }
        // other diag
        if (grid[0][2] == winnerMark && grid[1][1] == winnerMark && grid[2][0] == winnerMark) {
            highlightButtons(new int[][]{{0,2},{1,1},{2,0}});
        }
    }

    private void highlightButtons(int[][] cells) {
        for (int[] cell : cells) {
            int r = cell[0];
            int c = cell[1];
            JButton btn = buttons[r][c];
            btn.setBackground(new Color(241, 196, 15)); // yellow highlight
        }
    }
}
