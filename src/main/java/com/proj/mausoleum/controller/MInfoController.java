package com.proj.mausoleum.controller;


import com.github.pagehelper.PageHelper;
import com.proj.mausoleum.pojo.MInfo;
import com.proj.mausoleum.pojo.User;
import com.proj.mausoleum.result.CodeMsg;
import com.proj.mausoleum.result.Result;
import com.proj.mausoleum.service.*;
import org.jsoup.Jsoup;
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
@RequestMapping("/minfo")
public class MInfoController {

    @Autowired
    UserService userService;

    @Autowired
    AuditService auditService;

    @Autowired
    MInfoService mInfoService;

    @Autowired
    CoordinateService coordinateService;

    @Autowired
    PictureService pictureService;

    @RequestMapping(value = "/getList", method = GET)
    public Result showMInfoList(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "limit", required = false) Integer limit,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "isHtml") Boolean isHtml) {
        if (page != null && limit != null) {
            PageHelper.startPage(page, limit);
        }
        MInfo mInfo = new MInfo();
        mInfo.setName(name);
        List<MInfo> mInfoList = mInfoService.getMInfoList(mInfo);
        if (!isHtml) {
            for (MInfo mInfo1 : mInfoList) {
                mInfo1.setDetails(Jsoup.parse(mInfo1.getDetails()).text());
            }
        }
        Integer count = mInfoService.getMInfoListCount(mInfo);
        return Result.page(mInfoList, count);
    }

    @RequestMapping(value = "/get", method = GET)
    public Result getMInfo(@RequestParam("mInfoId") Integer mInfoId,
                           @RequestParam("isHtml") Boolean isHtml) {
        if (mInfoId == null || isHtml == null) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        MInfo mInfo = mInfoService.getMInfo(mInfoId);
        if (!isHtml) {
            mInfo.setDetails(Jsoup.parse(mInfo.getDetails()).text());
        }
        if(mInfo == null) {
            return Result.error(CodeMsg.MINFO_NOTEXISTS);
        } else {
            return Result.success(mInfo);
        }
    }

    @Transactional
    @RequestMapping(value = "/destroy", method = POST)
    public Result destroyMInfo(@RequestParam("mInfoId") Integer mInfoId,
                               HttpSession session) {
        if(mInfoId == null) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        User user = userService.getSession(session);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_NOTEXISTS);
        }
        MInfo mInfo = mInfoService.getMInfo(mInfoId);
        if (mInfo != null) {
            if (mInfoService.destroyMInfo(mInfoId)) {
                System.out.println(1);
                if (auditService.destroyAuditAboutModifyMInfo(mInfoId)) {
                    System.out.println(2);
                    if (pictureService.destroyPicture(mInfoId)) {
                        System.out.println(3);
                        if (coordinateService.destroyCoordinate(mInfo.getCoordinateId())) {
                            System.out.println(4);
                            return Result.success(null);
                        } else {
                            return Result.error(CodeMsg.COORDDESTROY_FAIL);
                        }
                    } else {
                        return Result.error(CodeMsg.PICDESTROY_FAIL);
                    }
                } else {
                    return Result.error(CodeMsg.AUDITDESTROY_FAIL);
                }
            } else {
                return Result.error(CodeMsg.MINFODESTROY_FAIL);
            }
        } else {
            return Result.error(CodeMsg.MINFODESTROY_FAIL);
        }
    }
}
