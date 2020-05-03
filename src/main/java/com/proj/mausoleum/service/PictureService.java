package com.proj.mausoleum.service;

import com.proj.mausoleum.pojo.Picture;

import java.util.List;

public interface PictureService {

    List<Picture> getPictureListByDetails(String details);

    String getFirstImgSrcByPictureList(String details);

    Boolean savePicture(Integer mInfoId, String details);

    Boolean modifyPicture(Integer mInfoId, String details);

    List<Picture> getPictureList(Integer page, Integer limit);

    Integer getPictureListCount();

    Boolean destroyPicture(Integer mInfoId);

}
