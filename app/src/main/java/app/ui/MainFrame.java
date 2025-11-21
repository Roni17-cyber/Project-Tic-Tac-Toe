package app.ui;

import app.model.Player;
import app.service.GameService;
import app.service.GameServiceImpl;
import app.storage.FileStatsRepository;
import app.storage.StatsRepository;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private final StatsRepository statsRepository = new FileStatsRepository();
    private final GameService gameService = new GameServiceImpl(statsRepository);

    private final StatusBar statusBar = new StatusBar();
    private BoardPanel boardPanel;

    public MainFrame() {
        super("Tic Tac Toe - Ronish");

        // Dark modern theme
        getContentPane().setBackground(new Color(30, 30, 30));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top title panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 30, 30));

        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 34));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton statsButton = new JButton("📊 Stats");
        styleButton(statsButton, new Color(70, 130, 180));
        statsButton.addActionListener(e -> showStatsDialog());

        topPanel.add(title, BorderLayout.CENTER);
        topPanel.add(statsButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Status bar at bottom
        add(statusBar, BorderLayout.SOUTH);

        // Start game
        askForPlayersAndStartGame();
    }

    /** Style buttons */
    private void styleButton(JButton button, Color bg) {
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bg);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /** BEFORE every match, ask for nicknames */
    private void askForPlayersAndStartGame() {
        NicknameDialog dialog = new NicknameDialog(this);
        dialog.setVisible(true);

        if (!dialog.isConfirmed()) {
            dispose();
            return;
        }

        String xName = dialog.getXName();
        String oName = dialog.getOName();

        gameService.startNewGame(xName, oName);

        if (boardPanel != null) {
            remove(boardPanel);
        }

        boardPanel = new BoardPanel(gameService, statusBar, this::offerPlayAgain);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(boardPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    /** Offer replay */
    private void offerPlayAgain() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Play again with the same players?",
                "Play Again",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            gameService.resetGameSamePlayers();
            boardPanel.refreshFromService();
        } else {
            int newPlayers = JOptionPane.showConfirmDialog(
                    this,
                    "Play with new players?",
                    "New Players",
                    JOptionPane.YES_NO_OPTION
            );

            if (newPlayers == JOptionPane.YES_OPTION) {
                askForPlayersAndStartGame();
            } else {
                dispose();
            }
        }
    }

    /** Stats popup */
    private void showStatsDialog() {
        List<Player> players = statsRepository.loadAllPlayers();

        if (players.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No stats yet! Play a game first.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("PLAYER           WINS   LOSSES\n");
        sb.append("---------------------------------\n");

        for (Player p : players) {
            sb.append(String.format("%-15s   %4d   %4d%n",
                    p.getNickname(), p.getWins(), p.getLosses()));
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(350, 300));

        JOptionPane.showMessageDialog(this, scrollPane, "Player Stats", JOptionPane.PLAIN_MESSAGE);
    }
}
