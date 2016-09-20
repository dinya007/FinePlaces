package ru.fineplaces.state;

import java.util.HashMap;

import ru.fineplaces.domain.Coordinates;
import ru.fineplaces.domain.PlaceDto;
import ru.fineplaces.domain.PlaceKey;

public class PlaceMap extends HashMap<PlaceKey, PlaceDto> {

    public PlaceDto getByPosition(int position) {
        for (Entry<PlaceKey, PlaceDto> entry : entrySet()) {
            PlaceKey key = entry.getKey();
            if (key.getPosition() == position) return entry.getValue();
        }
        return null;
    }

    public PlaceDto getByCoordinates(Coordinates coordinates) {
        for (Entry<PlaceKey, PlaceDto> entry : entrySet()) {
            PlaceKey key = entry.getKey();
            if (key.getCoordinates().equals(coordinates)) return entry.getValue();
        }
        return null;
    }

    public PlaceDto put(Coordinates coordinates, PlaceDto placeDto) {
        for (Entry<PlaceKey, PlaceDto> entry : entrySet()) {
            PlaceKey key = entry.getKey();
            if (key.getCoordinates().equals(coordinates)) return put(key, placeDto);
        }
        throw new IllegalArgumentException("There is no place with such coordinates");
    }

}
