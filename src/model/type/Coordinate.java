package model.type;

public class Coordinate {
    private int x;
    private int y;
    // Penser à vérifier si les coordonnées sont comprises dans le plateau

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        // Penser à vérifier si les coordonnées sont comprises dans le plateau
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Autres fonctions plus tard
}
