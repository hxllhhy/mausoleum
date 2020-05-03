package com.proj.mausoleum.service.impl;

import com.github.pagehelper.PageHelper;
import com.proj.mausoleum.dao.VideoDao;
import com.proj.mausoleum.pojo.Video;
import com.proj.mausoleum.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoDao videoDao;

    public Boolean saveVideo(Video video) {
        return videoDao.insertVideo(video) == 1;
    }
    

    public List<Video> getVideoList(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return videoDao.selectVideoList();
    }

    public Integer getVideoListCount() {
        return videoDao.countVideoList();
    }

    public Boolean destroyVideo(Integer videoId) {
        return videoDao.deleteVideoByVideoId(videoId) == 1;
    }
}
