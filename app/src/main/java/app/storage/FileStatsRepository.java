package app.storage;

import app.model.Player;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FileStatsRepository implements StatsRepository {

    private static final String FILE_NAME = "player-stats.txt";
    private final Path path = Paths.get(FILE_NAME);

    @Override
    public Player loadOrCreate(String nickname) {
        Map<String, Player> map = loadAsMap();
        if (nickname == null) {
            return new Player("Unknown");
        }
        Player existing = map.get(nickname.trim().toLowerCase());
        if (existing != null) {
            return existing;
        }
        return new Player(nickname);
    }

    @Override
    public void save(Player player) {
        Map<String, Player> map = loadAsMap();
        map.put(player.getNickname().trim().toLowerCase(),
                new Player(player.getNickname(), player.getWins(), player.getLosses()));
        writeMap(map);
    }

    @Override
    public List<Player> loadAllPlayers() {
        return new ArrayList<>(loadAsMap().values());
    }

    // -------- helpers --------

    private Map<String, Player> loadAsMap() {
        Map<String, Player> map = new LinkedHashMap<>();
        if (!Files.exists(path)) {
            return map;
        }
        try {
            for (String line : Files.readAllLines(path)) {
                if (line.isBlank()) continue;
                String[] parts = line.split(";");
                if (parts.length != 3) continue;
                String name = parts[0];
                int wins = Integer.parseInt(parts[1]);
                int losses = Integer.parseInt(parts[2]);
                map.put(name.trim().toLowerCase(), new Player(name, wins, losses));
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return map;
    }

    private void writeMap(Map<String, Player> map) {
        List<String> lines = new ArrayList<>();
        for (Player p : map.values()) {
            lines.add(p.getNickname() + ";" + p.getWins() + ";" + p.getLosses());
        }
        try {
            Files.write(path, lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
