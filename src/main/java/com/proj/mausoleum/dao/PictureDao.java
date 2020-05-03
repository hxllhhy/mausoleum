package com.proj.mausoleum.dao;

import com.proj.mausoleum.pojo.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PictureDao {

    Integer insertPictureList(List<Picture> pictureList);

    Integer deletePictureBymInfoId(@Param("mInfoId") Integer mInfoId);

    List<Picture> selectPictureList();

    Integer countPictureList();

}
