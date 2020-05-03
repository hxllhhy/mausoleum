package com.proj.mausoleum.service.impl;

import com.github.pagehelper.PageHelper;
import com.proj.mausoleum.dao.MInfoDao;
import com.proj.mausoleum.pojo.MInfo;
import com.proj.mausoleum.service.MInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MInfoServiceImpl implements MInfoService {

    @Autowired
    MInfoDao mInfoDao;

    public Boolean saveMInfo(MInfo mInfo) {
        return mInfoDao.insertMInfo(mInfo) == 1;
    }

    public MInfo getMInfo(Integer mInfoId) {
        return mInfoDao.selectMInfoByMInfoId(mInfoId);
    }

    public Boolean modifyMInfo(MInfo mInfo) {
        return mInfoDao.updateMInfo(mInfo) == 1;
    }

    public List<MInfo> getMInfoList(MInfo mInfo) {
        return mInfoDao.selectMInfoList(mInfo);
    }

    public Integer getMInfoListCount(MInfo mInfo) {
        return mInfoDao.countMInfoList(mInfo);
    }

    public Boolean destroyMInfo(Integer mInfoId) {
        return mInfoDao.deleteMInfoByMInfoId(mInfoId) == 1;
    }
}
