package app.service;

import app.model.Board;
import app.model.Player;
import app.storage.StatsRepository;

public class GameServiceImpl implements GameService {

    private final Board board = new Board();
    private final StatsRepository statsRepository;

    private Player xPlayer;
    private Player oPlayer;
    private char currentMark = 'X';
    private MatchResult result = MatchResult.IN_PROGRESS;

    // Constructor gets the StatsRepository from MainFrame
    public GameServiceImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public void startNewGame(String xNickname, String oNickname) {
        // Load existing stats or create new player with 0 wins/losses
        this.xPlayer = statsRepository.loadOrCreate(xNickname);
        this.oPlayer = statsRepository.loadOrCreate(oNickname);
        resetBoard();
    }

    @Override
    public void resetGameSamePlayers() {
        resetBoard();
    }

    private void resetBoard() {
        board.reset();
        currentMark = 'X';
        result = MatchResult.IN_PROGRESS;
    }

    @Override
    public boolean makeMove(int row, int col) {
        if (result != MatchResult.IN_PROGRESS) return false;

        boolean placed = board.placeMark(row, col, currentMark);
        if (!placed) return false;

        // Check win or draw
        if (board.hasWin(currentMark)) {
            if (currentMark == 'X') {
                result = MatchResult.X_WIN;
                xPlayer.recordWin();
                oPlayer.recordLoss();
            } else {
                result = MatchResult.O_WIN;
                oPlayer.recordWin();
                xPlayer.recordLoss();
            }

            // Save updated stats to file
            statsRepository.save(xPlayer);
            statsRepository.save(oPlayer);

        } else if (board.isFull()) {
            result = MatchResult.DRAW;

        } else {
            // Continue game
            currentMark = (currentMark == 'X') ? 'O' : 'X';
        }

        return true;
    }

    @Override
    public char getCurrentMark() {
        return currentMark;
    }

    @Override
    public MatchResult getResult() {
        return result;
    }

    @Override
    public boolean isGameOver() {
        return result != MatchResult.IN_PROGRESS;
    }

    @Override
    public char[][] getBoardSnapshot() {
        return board.snapshot();
    }

    @Override
    public String getPlayerNameForMark(char mark) {
        if (mark == 'X') return xPlayer != null ? xPlayer.getNickname() : "Player X";
        if (mark == 'O') return oPlayer != null ? oPlayer.getNickname() : "Player O";
        return "";
    }
}
