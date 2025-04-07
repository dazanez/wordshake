package co.edu.poli.AndresGuzman.modelo;

import java.time.LocalDateTime;

public class Game {
    private int id;
    private int playerId;
    private int totalScore;
    private LocalDateTime startDate;

    public Game(int playerId, int totalScore) {
        this.playerId = playerId;
        this.totalScore = totalScore;
        startDate = LocalDateTime.now();
    }

    public Game() {
        this(1, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
}
