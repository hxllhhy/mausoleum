package com.proj.mausoleum.dao;

import com.proj.mausoleum.pojo.Audit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuditDao {

    Integer insertAudit(Audit audit);

    List<Audit> selectAuditList(@Param("type") Integer type);

    Integer countAuditList(@Param("type") Integer type);

    Integer updateAudit(Audit audit);

    Integer deleteAuditAboutModifyMInfo(@Param("belongId") Integer belongId);
}

