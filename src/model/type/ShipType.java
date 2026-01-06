package model.type;

public enum ShipType {
    BATTLESHIP(2),
    CRUISER(3),
    DESTROYER(4),
    NAVALMINE(1); // Doit faire perdre le joueur qui tire dessus

    private final int length;

    ShipType(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
