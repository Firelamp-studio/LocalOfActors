package game;

import api.Element;

public class Armchair extends Element {
    private boolean occupied;

    public Armchair() {
        setSprite("sit.png", 0.7);
        setRotation(180);
        occupied = false;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
