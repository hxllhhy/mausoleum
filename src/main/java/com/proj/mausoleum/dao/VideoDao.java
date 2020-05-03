package com.proj.mausoleum.dao;

import com.proj.mausoleum.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VideoDao {

    Integer insertVideo(Video video);

    Integer deleteVideoByVideoId(@Param("videoId") Integer videoId);

    List<Video> selectVideoList();

    Integer countVideoList();
}
