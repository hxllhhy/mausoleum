package com.proj.mausoleum.service;

import com.proj.mausoleum.pojo.Coordinate;
import com.proj.mausoleum.pojo.CoordinateVO;

import java.util.List;

public interface CoordinateService {

    Boolean saveCoordinate(Coordinate coordinate);

    Coordinate getCoordinate(Integer coordinateId);

    //Boolean modifyCoordinate(Coordinate coordinate);

    List<Coordinate> getCoordinateList(Integer page, Integer limit);

    Integer getCoordinateListCount();

    Boolean destroyCoordinate(Integer coordinateId);

    List<Coordinate> getSelfCoordinateList(Integer userId);

    List<CoordinateVO> getMInfoCoordinateList();

    List<CoordinateVO> searchName(String name);
}
