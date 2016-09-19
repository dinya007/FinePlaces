package ru.fineplaces.domain;


public class PlaceDto {

    private Coordinate coordinate;
    private String locationName;
    private String description;
    private int sale;
    private String ownerLogin;

    public PlaceDto() {
    }

    public PlaceDto(Coordinate coordinate, String locationName, int sale, String description, String ownerLogin) {
        this.coordinate = coordinate;
        this.locationName = locationName;
        this.sale = sale;
        this.description = description;
        this.ownerLogin = ownerLogin;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }
}
