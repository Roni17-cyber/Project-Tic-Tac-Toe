package app.model;

public class Player {
    private final String nickname;
    private int wins;
    private int losses;

    public Player(String nickname) {
        this(nickname, 0, 0);
    }

    public Player(String nickname, int wins, int losses) {
        this.nickname = nickname == null || nickname.isBlank()
                ? "Unknown"
                : nickname.trim();
        this.wins = wins;
        this.losses = losses;
    }

    public String getNickname() {
        return nickname;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public void recordWin() {
        wins++;
    }

    public void recordLoss() {
        losses++;
    }
}
