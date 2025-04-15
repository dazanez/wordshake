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

    public static int calcularPuntaje(Words palabra, int puntaje){
        String dificultad = palabra.getDifficulty();
        int extension = palabra.getWord().length();
        switch (dificultad) {
            case "easy":
                puntaje += 10;
                break;
            case "medium":
                puntaje += 20;
                break;
            case "hard":
                puntaje += 30;
                break;
        
            default:
                break;
        }
        if (extension >= 3 && extension <= 4) {
            puntaje += 5;
        }
        else if(extension >= 5 && extension <= 6){
            puntaje += 10;
        }
        else if(extension >= 7){
            puntaje += 20;
        }
        return puntaje;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", difficulty=" + difficulty +
                '}';
    }
}

