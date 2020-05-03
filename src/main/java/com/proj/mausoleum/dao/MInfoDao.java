package com.proj.mausoleum.dao;

import com.proj.mausoleum.pojo.MInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MInfoDao {

    Integer insertMInfo(MInfo mInfo);

    MInfo selectMInfoByMInfoId(@Param("mInfoId") Integer mInfoId);

    Integer updateMInfo(MInfo mInfo);

    List<MInfo> selectMInfoList(MInfo mInfo);

    Integer countMInfoList(MInfo mInfo);

    Integer deleteMInfoByMInfoId(@Param("mInfoId") Integer mInfoId);
}
