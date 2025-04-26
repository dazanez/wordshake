package co.edu.poli.AndresGuzman.modelo;

import java.util.*;

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

    public Words(String palabra) {
        this.word =palabra;
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

    public static List<Character> generarLetras(int numLetras) {
        ArrayList<String> palabras = new ArrayList<>(Arrays.asList(
                "abecedario",    // A, B, C, D, E
                "fácil",         // F, A, C, I, L
                "gente",         // G, E, N, T
                "horizonte",     // H, O, R, I, Z
                "jabón",         // J, A, B, O, N
                "kilómetro",     // K, I, L, O, M, E, T
                "luna",          // L, U, N, A
                "mujer",         // M, U, J, E, R
                "naranja",       // N, A, R, J
                "océano",        // O, C, E, A, N
                "paz",           // P, A, Z
                "químico",       // Q, U, I, M, C, O
                "río",           // R, I, O
                "salvaje",       // S, A, L, V, J, E
                "tesoro",        // T, E, S, O, R
                "viento",        // V, I, E, N, T, O
                "xenón",         // X, E, N, O
                "yogur",         // Y, O, G, U, R
                "zorro"          // Z, O, R
        ));

        List<Character> letras = new ArrayList<>();
        Random random = new Random();

        // Repetimos hasta que tengamos la cantidad deseada de letras
        while (letras.size() < numLetras) {
            // Selección aleatoria de una palabra
            String palabra = palabras.get(random.nextInt(palabras.size()));

            // Añadimos las letras de la palabra seleccionada
            for (char c : palabra.toCharArray()) {
                if (letras.size() < numLetras) {
                    letras.add(c);
                } else {
                    break;
                }
            }
        }

        Collections.shuffle(letras);

        return letras;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", difficulty=" + difficulty +
                '}';
    }
}

