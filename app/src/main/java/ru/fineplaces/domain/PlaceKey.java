package ru.fineplaces.domain;

public class PlaceKey {

    private Coordinates coordinates;
    private int position;

    public PlaceKey(Coordinates coordinates, int position) {
        this.coordinates = coordinates;
        this.position = position;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getPosition() {
        return position;
    }
}
