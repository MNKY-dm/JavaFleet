package model.entity;

import controller.GameManager;
import model.Ship;

// Plaçable par le joueur, mais n'ajoute pas de points de vie à sa flotte
public class NavalMine extends Ship {
    private static final int LENGTH = 1;

    public NavalMine() {
        super(LENGTH, "NavalMine");
    }

    public void explodePlayer() {
        GameManager.getInstance().getGame().getCurrentPlayer().explodePlayer();
    }

    @Override
    public void onSunk() {
        explodePlayer();
    }
}
