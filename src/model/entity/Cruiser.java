package model.entity;

import model.Ship;

public class Cruiser extends Ship {
    private static final int LENGTH = 3;

    public Cruiser() {
        super(LENGTH, "Cruiser");
    }
}
