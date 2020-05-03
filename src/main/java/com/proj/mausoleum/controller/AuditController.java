package com.proj.mausoleum.controller;

import com.proj.mausoleum.pojo.*;
import com.proj.mausoleum.result.CodeMsg;
import com.proj.mausoleum.result.Result;
import com.proj.mausoleum.service.*;
import com.proj.mausoleum.utils.MyTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private static final Integer addMInfo = 1;
    private static final Integer modifyMInfo = 2;
    private static final Integer addVideo = 3;
    private static final Integer addSelfCoordinate = 4;
    private static final Integer addMCoordinate = 5;


    @Autowired
    AuditService auditService;

    @Autowired
    UserService userService;

    @Autowired
    MInfoService mInfoService;

    @Autowired
    PictureService pictureService;

    @Autowired
    VideoService videoService;

    @Autowired
    CoordinateService coordinateService;
    
    @RequestMapping(value = "/save", method = POST)
    public Result saveAudit(@RequestParam("auditType") Integer auditType,
                            @RequestParam("belongId") Integer belongId,
                            @RequestParam("fieldOne") String fieldOne,
                            @RequestParam("fieldTwo") String fieldTwo,
                            @RequestParam(value = "fieldThree", required = false) String fieldThree,
                            HttpSession session) {
        if (auditType == null || belongId == null ||StringUtils.isEmpty(fieldOne) || StringUtils.isEmpty(fieldTwo)) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        User user = userService.getSession(session);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_NOTEXISTS);
        }
        Audit audit = new Audit();
        audit.setAuditType(auditType);
        if (auditType == 4) {
            audit.setBelongId(user.getUserId());
        } else {
            audit.setBelongId(belongId);
        }
        audit.setFieldOne(fieldOne);
        audit.setFieldTwo(fieldTwo);
        audit.setFieldThree(fieldThree);
        audit.setSubmitUsername(user.getUsername());
        audit.setSubmitTime(MyTime.getCurrentTime());
        audit.setAuditStatus(0);
        if (auditService.saveAudit(audit)) {
            return Result.success(audit);
        } else {
            return Result.error(CodeMsg.AUDITSAVE_FAIL);
        }
    }

    @RequestMapping(value = "/getList", method = GET)
    public Result getAuditList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                               @RequestParam("type") Integer type) {
        List<Audit> auditList = auditService.getAuditList(page, limit, type);
        Integer count = auditService.getAuditListCount(type);
        return Result.page(auditList, count);
    }

    @Transactional
    @RequestMapping(value = "/agree", method = POST)
    public Result agreeAudit(@RequestParam("auditId") Integer auditId,
                             @RequestParam("auditType") Integer auditType,
                             @RequestParam("belongId") Integer belongId,
                             @RequestParam("fieldOne") String fieldOne,
                             @RequestParam("fieldTwo") String fieldTwo,
                             @RequestParam(value = "fieldThree", required = false) String fieldThree,
                             HttpSession session) {
        if (auditType == null || belongId == null ||StringUtils.isEmpty(fieldOne) || StringUtils.isEmpty(fieldTwo)) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        User user = userService.getSession(session);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_NOTEXISTS);
        }
        String time = MyTime.getCurrentTime();
        MInfo mInfo = new MInfo();
        Video video = new Video();
        Coordinate coordinate = new Coordinate();
        Audit audit = new Audit();
        if (auditType.equals(addMInfo) || auditType.equals(modifyMInfo)) {
            mInfo.setName(fieldOne);
            mInfo.setDetails(fieldTwo);
            mInfo.setCover(pictureService.getFirstImgSrcByPictureList(fieldTwo));
            mInfo.setLastAuditUsername(user.getUsername());
            mInfo.setLastAuditTime(time);
        } else if (auditType.equals(addVideo)) {
            video.setAlt(fieldOne);
            video.setSrc(fieldTwo);
            video.setAuditUsername(user.getUsername());
            video.setAuditTime(time);
        } else if (auditType.equals(addSelfCoordinate) || auditType.equals(addMCoordinate)) {
            coordinate.setCoordinateType(auditType);
            coordinate.setBelongId(belongId);
            coordinate.setX(Double.parseDouble(fieldOne));
            coordinate.setY(Double.parseDouble(fieldTwo));
            coordinate.setRemark(fieldThree);
            coordinate.setAuditUsername(user.getUsername());
            coordinate.setAuditTime(time);
        }
        audit.setAuditId(auditId);
        audit.setAuditUsername(user.getUsername());
        audit.setAuditTime(time);
        audit.setAuditStatus(1);
        if (auditService.modifyAudit(audit)) {
            if (auditType.equals(addMInfo)) {
                mInfo.setCoordinateId(0);
                if (mInfoService.saveMInfo(mInfo)) {
                    if (pictureService.savePicture(mInfo.getmInfoId(), fieldTwo)) {
                        return Result.success(null);
                    } else {
                        return Result.error(CodeMsg.PICSAVE_FAIL);
                    }
                } else {
                    return Result.error(CodeMsg.MINFOSAVE_FAIL);
                }
            } else if(auditType.equals(modifyMInfo)) {
                mInfo.setmInfoId(belongId);
                if (mInfoService.modifyMInfo(mInfo)) {
                    if (pictureService.modifyPicture(belongId, fieldTwo)) {
                        return Result.success(null);
                    } else {
                        return Result.error(CodeMsg.PICMODIFY_FAIL);
                    }
                } else {
                    return Result.error(CodeMsg.MINFOMODIFY_FAIL);
                }
            } else if(auditType.equals(addVideo)) {
                if (videoService.saveVideo(video)) {
                    return Result.success(null);
                } else {
                    return Result.error(CodeMsg.VIDEOSAVE_FAIL);
                }
            } else if(auditType.equals(addSelfCoordinate)) {
                if (coordinateService.saveCoordinate(coordinate)) {
                    return Result.success(null);
                } else {
                    return Result.error(CodeMsg.COORDSAVE_FAIL);
                }
            } else if(auditType.equals(addMCoordinate)) {
                if (coordinateService.saveCoordinate(coordinate)) {
                    MInfo mInfo1 = new MInfo();
                    mInfo1.setmInfoId(belongId);
                    mInfo1.setCoordinateId(coordinate.getCoordinateId());
                    if (mInfoService.modifyMInfo(mInfo1)) {
                        return Result.success(null);
                    } else {
                        return Result.error(CodeMsg.MINFOMODIFY_FAIL);
                    }
                } else {
                    return Result.error(CodeMsg.COORDSAVE_FAIL);
                }
            } else {
                return Result.error(CodeMsg.SERVER_ERROR);
            }
        } else {
            return Result.error(CodeMsg.AUDITMODIFY_FAIL);
        }
    }

    @Transactional
    @RequestMapping(value = "/reject", method = POST)
    public Result rejectAudit(@RequestParam("auditId") Integer auditId,
                              HttpSession session) {
        if (auditId == null) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        User user = userService.getSession(session);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_NOTEXISTS);
        }
        Audit audit = new Audit();
        audit.setAuditId(auditId);
        audit.setAuditUsername(user.getUsername());
        audit.setAuditTime(MyTime.getCurrentTime());
        audit.setAuditStatus(-1);
        if (auditService.modifyAudit(audit)) {
            return Result.success(null);
        } else {
            return Result.error(CodeMsg.AUDITMODIFY_FAIL);
        }
    }
}
