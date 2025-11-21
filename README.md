🧩 Tic-Tac-Toe Game (Java Swing + OOP + Gradle)

A modern, GUI-based Tic-Tac-Toe game built using Java, Swing, Object-Oriented Programming, and Gradle.
This project was created following the requirements:

✔ Ask for nicknames before each match
✔ Keep record of wins/losses for every nickname
✔ Save stats to a file after every match
✔ Provide a clean modern UI
✔ Follow OOP principles (separation of model, service, storage, UI)

🎮 Features
✅ 1. Modern GUI

Dark theme

Highlighting winning combinations

Smooth, modern buttons

Custom nickname dialog (not the default ugly Swing input)

Clean status bar with turn indicator

✅ 2. Play the Game

Two-player (local) Tic-Tac-Toe

Clickable 3×3 grid

Instantly checks win, loss, or draw

Play again with same players

Or start with new players

✅ 3. Nicknames Before Each Match

Before each game, players enter their names through a custom dialog:

Player X: Ronish
Player O: Sam

✅ 4. Persistent Player Stats

The game keeps track of:

Wins

Losses

For every nickname that has ever played

Stats save into:

player-stats.txt


Example:

Ronish;3;1
Alex;1;4
Sara;5;2

✅ 5. View Stats (Leaderboard)

The “📊 Stats” button shows a leaderboard:

Player           Wins   Losses
---------------------------------
Ronish             3       1
Alex               1       4

📁 Project Structure (OOP Clean Architecture)
src/main/java/app/
│
├── model/
│   ├── Player.java
│   └── Board.java
│
├── service/
│   ├── GameService.java
│   ├── GameServiceImpl.java
│   └── MatchResult.java
│
├── storage/
│   ├── StatsRepository.java
│   └── FileStatsRepository.java
│
└── ui/
    ├── MainFrame.java
    ├── BoardPanel.java
    ├── StatusBar.java
    └── NicknameDialog.java


This structure follows proper OOP and separation of concerns:

model → data classes (board, players)

service → game logic

storage → saving/loading stats

ui → everything the user interacts with

🚀 How to Run the Game
Requires:

Java 17 or 18

Gradle Wrapper (included)

Run command:
./gradlew run


Windows PowerShell:

.\gradlew.bat run


The game window will appear automatically.

🛠 Technologies Used

Java 17/18

Swing for GUI

Gradle for build automation

OOP principles

File I/O for saving stats

👤 Author

Ronish Kumar Karki
Computer Science @ Texas State University
SLAC Tutor • Software Developer • AI & SWE Enthusiast
