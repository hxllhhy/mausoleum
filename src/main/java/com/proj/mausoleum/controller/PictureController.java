package com.proj.mausoleum.controller;

import com.proj.mausoleum.dao.PictureDao;
import com.proj.mausoleum.pojo.MInfo;
import com.proj.mausoleum.pojo.Picture;
import com.proj.mausoleum.result.CodeMsg;
import com.proj.mausoleum.result.PicResult;
import com.proj.mausoleum.result.Result;
import com.proj.mausoleum.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    PictureService pictureService;

    private static final String ROOT_PATH = "/mausoleum";
    private static final String VIRTUAL_PATH = ROOT_PATH + "/picture/";
    private static final String REAL_PATH = "D:/mausoleum_file/picture/";

    @RequestMapping("/save")
    public PicResult savePicture(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null) {
                return PicResult.error(CodeMsg.PARAMETER_NULL);
            }
            String fileName = file.getOriginalFilename();
            if (fileName.lastIndexOf(".") == -1) {
                return PicResult.error(CodeMsg.PICSUFFIX_ERROR);
            }
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            if (!suffix.equalsIgnoreCase(".gif") && !suffix.equalsIgnoreCase(".png")
                    && !suffix.equalsIgnoreCase(".jpg") && !suffix.equalsIgnoreCase("jpeg")
                    && !suffix.equalsIgnoreCase(".svg")) {
                return PicResult.error(CodeMsg.PICSUFFIX_ERROR);
            }
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String newName = uuid + fileName;
            File targetFile = new File(REAL_PATH, newName);
            file.transferTo(targetFile);
            String filepath = VIRTUAL_PATH + newName;
            return PicResult.success(filepath);
        } catch (Exception e) {
            return PicResult.error(CodeMsg.SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getList", method = GET)
    public Result getPictureList(@RequestParam("page") Integer page,
                                 @RequestParam("limit") Integer limit) {
        List<Picture> pictureList = pictureService.getPictureList(page, limit);
        Integer count = pictureService.getPictureListCount();
        return Result.page(pictureList, count);
    }
}
