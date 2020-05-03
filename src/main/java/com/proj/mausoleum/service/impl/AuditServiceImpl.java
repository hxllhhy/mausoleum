package com.proj.mausoleum.service.impl;

import com.github.pagehelper.PageHelper;
import com.proj.mausoleum.dao.AuditDao;
import com.proj.mausoleum.pojo.Audit;
import com.proj.mausoleum.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    AuditDao auditDao;

    public Boolean saveAudit(Audit audit) {
        return auditDao.insertAudit(audit) == 1;
    }

    public List<Audit> getAuditList(Integer page, Integer limit, Integer type) {
        PageHelper.startPage(page, limit);
        return auditDao.selectAuditList(type);
    }

    public Integer getAuditListCount(Integer type) {
        return auditDao.countAuditList(type);
    }

    public Boolean modifyAudit(Audit audit) {
        return auditDao.updateAudit(audit) == 1;
    }

    public Boolean destroyAuditAboutModifyMInfo(Integer belongId) {
        return auditDao.deleteAuditAboutModifyMInfo(belongId) >= 0;
    }
}
