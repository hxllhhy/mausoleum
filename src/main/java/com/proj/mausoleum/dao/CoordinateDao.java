package com.proj.mausoleum.dao;

import com.proj.mausoleum.pojo.Coordinate;
import com.proj.mausoleum.pojo.CoordinateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoordinateDao {

    Integer insertCoordinate(Coordinate coordinate);

    Coordinate selectCoordinateByCoordinateId(@Param("coordinateId") Integer coordinateId);

    //Integer updateCoordinate(Coordinate coordinate);

    List<Coordinate> selectCoordinateList();

    Integer countCoordinateList();

    Integer deleteCoordinateByCoordinateId(@Param("coordinateId") Integer coordinateId);

    List<Coordinate> selectSelfCoordinateList(@Param("userId") Integer userId);

    List<CoordinateVO> selectMInfoCoordinateList();

}
