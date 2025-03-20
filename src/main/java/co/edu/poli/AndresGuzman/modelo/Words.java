package co.edu.poli.AndresGuzman.modelo;
public class Words {
    private int id;
    private String word;
    private Difficulty difficulty;

    public enum Difficulty {
        easy, medium, hard
    }

    public Words(String word, Difficulty difficulty) {
        this.word = word;
        this.difficulty = (difficulty != null) ? difficulty : Difficulty.easy;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDifficulty() {
        return difficulty.name();
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", difficulty=" + difficulty +
                '}';
    }
}

