package com.proj.mausoleum.service;

import com.proj.mausoleum.pojo.MInfo;

import java.util.List;

public interface MInfoService {

    Boolean saveMInfo(MInfo mInfo);

    MInfo getMInfo(Integer mInfoId);

    Boolean modifyMInfo(MInfo mInfo);

    List<MInfo> getMInfoList(MInfo mInfo);

    Integer getMInfoListCount(MInfo mInfo);

    Boolean destroyMInfo(Integer mInfoId);
}
