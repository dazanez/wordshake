package co.edu.poli.AndresGuzman.modelo;

import java.time.LocalDateTime;

public class ScoreView {
    private String username;
    private int score;
    private LocalDateTime playedAt;

    public ScoreView(String username, int score, LocalDateTime playedAt) {
        this.username = username;
        this.score = score;
        this.playedAt = playedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(LocalDateTime playedAt) {
        this.playedAt = playedAt;
    }
}

