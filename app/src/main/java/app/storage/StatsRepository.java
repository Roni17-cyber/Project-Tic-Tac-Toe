package app.storage;

import app.model.Player;

import java.util.List;

public interface StatsRepository {
    Player loadOrCreate(String nickname);      // get existing player, or new with 0–0
    void save(Player player);                  // write/update one player
    List<Player> loadAllPlayers();             // list for stats dialog
}
