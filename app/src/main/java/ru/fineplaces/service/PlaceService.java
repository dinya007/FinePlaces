package ru.fineplaces.service;

import java.util.List;

import ru.fineplaces.domain.PlaceDto;

public interface PlaceService {

    List<PlaceDto> getAll();

    PlaceDto save(PlaceDto placeDto);

}
