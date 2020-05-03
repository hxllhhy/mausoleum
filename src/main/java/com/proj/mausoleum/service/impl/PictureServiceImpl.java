package com.proj.mausoleum.service.impl;

import com.github.pagehelper.PageHelper;
import com.proj.mausoleum.dao.PictureDao;
import com.proj.mausoleum.pojo.Picture;
import com.proj.mausoleum.service.PictureService;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String DEFAULTPIC_SRC = "/mausoleum/picture/汉高祖刘邦长陵.png";

    @Autowired
    PictureDao pictureDao;

    public List<Picture> getPictureListByDetails(String details) {
        Document document = Jsoup.parse(details);
        Elements elements = document.select("img[src]");//获取到的值为所有的<img src="...">
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : elements) {
            Picture picture = new Picture();
            picture.setAlt(element.attr("alt"));
            picture.setSrc(element.attr("src"));
            pictureList.add(picture);
        }
        return pictureList;
    }

    public String getFirstImgSrcByPictureList(String details) {
        List<Picture> pictureList = getPictureListByDetails(details);
        if (pictureList.size() == 0) {
            return DEFAULTPIC_SRC;
        } else {
            return pictureList.get(0).getSrc();
        }
    }

    public Boolean savePicture(Integer mInfoId, String details) {
        List<Picture> pictureList = getPictureListByDetails(details);
        if (pictureList.size() == 0) {
            return true;
        } else {
            for (Picture picture : pictureList) {
                picture.setmInfoId(mInfoId);
            }
            return pictureDao.insertPictureList(pictureList) > 0;
        }
    }

    public Boolean modifyPicture(Integer mInfoId, String details) {
        if(pictureDao.deletePictureBymInfoId(mInfoId) >= 0) {
            return savePicture(mInfoId, details);
        } else {
            return false;
        }
    }

    public List<Picture> getPictureList(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return pictureDao.selectPictureList();
    }

    @Override
    public Integer getPictureListCount() {
        return pictureDao.countPictureList();
    }

    @Override
    public Boolean destroyPicture(Integer mInfoId) {
        return pictureDao.deletePictureBymInfoId(mInfoId) >= 0;
    }


}
