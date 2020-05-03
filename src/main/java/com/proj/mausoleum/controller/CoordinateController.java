package com.proj.mausoleum.controller;

import com.proj.mausoleum.pojo.Coordinate;
import com.proj.mausoleum.pojo.CoordinateVO;
import com.proj.mausoleum.pojo.MInfo;
import com.proj.mausoleum.pojo.User;
import com.proj.mausoleum.result.CodeMsg;
import com.proj.mausoleum.result.Result;
import com.proj.mausoleum.service.AuditService;
import com.proj.mausoleum.service.CoordinateService;
import com.proj.mausoleum.service.MInfoService;
import com.proj.mausoleum.service.UserService;
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
@RequestMapping("/coordinate")
public class CoordinateController {
    @Autowired
    UserService userService;

    @Autowired
    AuditService auditService;

    @Autowired
    CoordinateService coordinateService;

    @Autowired
    MInfoService mInfoService;


    @RequestMapping(value = "/getList", method = GET)
    public Result getCoordinateList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<Coordinate> coordinateList = coordinateService.getCoordinateList(page, limit);
        Integer count = coordinateService.getCoordinateListCount();
        return Result.page(coordinateList, count);
    }

    @RequestMapping(value = "/getSelfList", method = GET)
    public Result getSelfCoordinateList(HttpSession session) {
        User user = userService.getSession(session);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_NOTEXISTS);
        }
        List<Coordinate> coordinateList = coordinateService.getSelfCoordinateList(user.getUserId());
        if (coordinateList.size() == 0) {
            return Result.success(null);
        } else {
            return Result.success(coordinateList);
        }
    }


    @RequestMapping(value = "/get", method = GET)
    public Result getCoordinate(@RequestParam("coordinateId") Integer coordinateId) {
        if (coordinateId == null) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        Coordinate coordinate = coordinateService.getCoordinate(coordinateId);
        if (coordinate == null) {
            return Result.error(CodeMsg.COORD_NOTEXISTS);
        } else {
            return Result.success(coordinate);
        }
    }

    @Transactional
    @RequestMapping(value = "/destroy", method = POST)
    public Result destroyCoordinate(@RequestParam("coordinateId") Integer coordinateId,
                                    HttpSession session) {
        if (coordinateId == null) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        User user = userService.getSession(session);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_NOTEXISTS);
        }
        Coordinate coordinate = coordinateService.getCoordinate(coordinateId);
        if (coordinate != null) {
            MInfo mInfo = new MInfo();
            mInfo.setmInfoId(coordinate.getBelongId());
            mInfo.setCoordinateId(0);
            if (coordinateService.destroyCoordinate(coordinateId)) {
                if (coordinate.getCoordinateType() == 5) {
                    if (mInfoService.modifyMInfo(mInfo)) {
                        return Result.success(null);
                    } else {
                        return Result.error(CodeMsg.MINFOMODIFY_FAIL);
                    }
                } else {
                    return Result.success(null);
                }
            } else {
                return Result.error(CodeMsg.COORDDESTROY_FAIL);
            }
        } else {
            return Result.error(CodeMsg.COORDDESTROY_FAIL);
        }
    }

    @RequestMapping(value = "/getMCList", method = GET)
    public Result getMInfoCoordinateList() {
        List<CoordinateVO> coordinateVOList = coordinateService.getMInfoCoordinateList();
        for (CoordinateVO coordinateVO : coordinateVOList) {
            coordinateVO.setDetails(Jsoup.parse(coordinateVO.getDetails()).text());
        }
        return Result.success(coordinateVOList);
    }

    @RequestMapping(value = "/search", method = GET)
    public Result searchName(@RequestParam("name") String name) {
        List<CoordinateVO> coordinateVOList = coordinateService.searchName(name);
        if (coordinateVOList.size() == 0) {
            return Result.success(null);
        } else {
            return Result.success(coordinateVOList);
        }
    }
}
