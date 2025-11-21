package app.service;

public interface GameService {

    void startNewGame(String xNickname, String oNickname);

    boolean makeMove(int row, int col); // true if move accepted

    char getCurrentMark();              // 'X' or 'O'

    MatchResult getResult();

    boolean isGameOver();

    char[][] getBoardSnapshot();

    String getPlayerNameForMark(char mark);

    void resetGameSamePlayers();
}
