package com.proj.mausoleum.service;

import com.proj.mausoleum.pojo.Video;

import java.util.List;

public interface VideoService {

    Boolean saveVideo(Video video);
    
    List<Video> getVideoList(Integer page, Integer limit);

    Integer getVideoListCount();

    Boolean destroyVideo(Integer videoId);
}
