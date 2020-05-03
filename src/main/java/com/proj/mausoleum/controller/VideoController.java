package com.proj.mausoleum.controller;

import com.proj.mausoleum.pojo.MInfo;
import com.proj.mausoleum.pojo.User;
import com.proj.mausoleum.pojo.Video;
import com.proj.mausoleum.result.CodeMsg;
import com.proj.mausoleum.result.PicResult;
import com.proj.mausoleum.result.Result;
import com.proj.mausoleum.service.UserService;
import com.proj.mausoleum.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/video")
public class VideoController {
    private static final String ROOT_PATH = "/mausoleum";
    private static final String VIRTUAL_PATH = ROOT_PATH + "/picture/";
    private static final String REAL_PATH = "D:/mausoleum_file/video/";

    @Autowired
    UserService userService;

    @Autowired
    VideoService videoService;

    @RequestMapping("/save")
    public Result saveVideo(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        String fileName = file.getOriginalFilename();
        if (fileName.lastIndexOf(".") == -1) {
            return Result.error(CodeMsg.VIDEOSUFFIX_ERROR);
        }
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (!suffix.equalsIgnoreCase(".mp4")) {
            return Result.error(CodeMsg.VIDEOSUFFIX_ERROR);
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newName = uuid + fileName;
        File targetFile = new File(REAL_PATH, newName);
        file.transferTo(targetFile);
        String src = VIRTUAL_PATH + newName;
        return Result.success(src);
    }

    @RequestMapping(value = "/getList", method = GET)
    public Result getVideoList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "limit", defaultValue = "4") Integer limit) {
        List<Video> videoList = videoService.getVideoList(page, limit);
        Integer count = videoService.getVideoListCount();
        return Result.page(videoList, count);
    }

    @Transactional
    @RequestMapping(value = "/destroy", method = POST)
    public Result destroyVideo(@RequestParam("videoId") Integer videoId,
                               HttpSession session) {
        if(videoId == null) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        User user = userService.getSession(session);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_NOTEXISTS);
        }
        if(videoService.destroyVideo(videoId)) {
            return Result.success(null);
        } else {
            return Result.error(CodeMsg.VIDEODESTROY_FAIL);
        }
    }
}
