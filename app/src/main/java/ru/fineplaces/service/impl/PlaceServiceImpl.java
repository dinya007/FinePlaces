package ru.fineplaces.service.impl;

import java.io.IOException;
import java.util.List;

import ru.fineplaces.da.PlaceDao;
import ru.fineplaces.domain.PlaceDto;
import ru.fineplaces.service.PlaceService;

public class PlaceServiceImpl implements PlaceService {


    PlaceDao placeDao;

    public PlaceServiceImpl(PlaceDao placeDao) {
        this.placeDao = placeDao;
    }

    @Override
    public List<PlaceDto> getAll() {
        try {
            return placeDao.getAll().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PlaceDto save(PlaceDto placeDto) {
        try {
            return placeDao.save(placeDto).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
