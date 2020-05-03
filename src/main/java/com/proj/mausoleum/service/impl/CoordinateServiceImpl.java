package com.proj.mausoleum.service.impl;

import com.github.pagehelper.PageHelper;
import com.proj.mausoleum.dao.CoordinateDao;
import com.proj.mausoleum.pojo.Coordinate;
import com.proj.mausoleum.pojo.CoordinateVO;
import com.proj.mausoleum.service.CoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoordinateServiceImpl implements CoordinateService {

    @Autowired
    CoordinateDao coordinateDao;

    public Boolean saveCoordinate(Coordinate coordinate) {
        return coordinateDao.insertCoordinate(coordinate) == 1;
    }

    public Coordinate getCoordinate(Integer coordinateId) {
        return coordinateDao.selectCoordinateByCoordinateId(coordinateId);
    }

//    public Boolean modifyCoordinate(Coordinate coordinate) {
//        return coordinateDao.updateCoordinate(coordinate) == 1;
//    }

    public List<Coordinate> getCoordinateList(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return coordinateDao.selectCoordinateList();
    }

    public Integer getCoordinateListCount() {
        return coordinateDao.countCoordinateList();
    }

    public Boolean destroyCoordinate(Integer coordinateId) {
        return coordinateDao.deleteCoordinateByCoordinateId(coordinateId) >= 0;
    }

    @Override
    public List<Coordinate> getSelfCoordinateList(Integer userId) {
        return coordinateDao.selectSelfCoordinateList(userId);
    }

    @Override
    public List<CoordinateVO> getMInfoCoordinateList() {
        return coordinateDao.selectMInfoCoordinateList();
    }

    @Override
    public List<CoordinateVO> searchName(String name) {
        List<CoordinateVO> coordinateVOList = getMInfoCoordinateList();
        List<CoordinateVO> coordinateVOListMatch = new ArrayList<CoordinateVO>();
        for (int i = 0; i < coordinateVOList.size(); i++) {
            if (coordinateVOList.get(i).getName().contains(name)) {
                coordinateVOListMatch.add(coordinateVOList.get(i));
            }
        }
        return coordinateVOListMatch;
    }


}
