package co.edu.poli.AndresGuzman.modelo;

public class Player {
    private int id;
    private String username;
    public Player(String username) {
        this.username = username;
    }

    public Player(int id, String username) {
        this.id = id;
        this.username = username;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
