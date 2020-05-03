package com.proj.mausoleum.service;

import com.proj.mausoleum.pojo.Audit;

import java.util.List;

public interface AuditService {

    Boolean saveAudit(Audit audit);

    List<Audit> getAuditList(Integer page, Integer limit, Integer type);

    Integer getAuditListCount(Integer type);

    Boolean modifyAudit(Audit audit);

    Boolean destroyAuditAboutModifyMInfo(Integer belongId);
}
