package ru.fineplaces.domain;


public class PlaceDto {

    private Coordinates coordinates;
    private String locationName;
    private String description;
    private int sale;
    private String ownerLogin;

    public PlaceDto() {
    }

    public PlaceDto(Coordinates coordinates, String locationName, int sale, String description, String ownerLogin) {
        this.coordinates = coordinates;
        this.locationName = locationName;
        this.sale = sale;
        this.description = description;
        this.ownerLogin = ownerLogin;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
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
