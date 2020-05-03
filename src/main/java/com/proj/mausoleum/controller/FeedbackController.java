package com.proj.mausoleum.controller;

import com.proj.mausoleum.pojo.Feedback;
import com.proj.mausoleum.pojo.MInfo;
import com.proj.mausoleum.pojo.User;
import com.proj.mausoleum.result.CodeMsg;
import com.proj.mausoleum.result.Result;
import com.proj.mausoleum.service.FeedbackService;
import com.proj.mausoleum.service.UserService;
import com.proj.mausoleum.utils.MyTime;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    UserService userService;
    @Autowired
    FeedbackService feedbackService;

    @RequestMapping(value = "/save", method = POST)
    public Result saveFeedback(@RequestParam("content") String content, HttpSession session) {
        if (StringUtils.isEmpty(content)) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        User user = userService.getSession(session);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_NOTEXISTS);
        }
        Feedback feedback = new Feedback();
        feedback.setContent(content);
        feedback.setUsername(user.getUsername());
        feedback.setTime(MyTime.getCurrentTime());
        feedback.setStatus(0);
        if (feedbackService.saveFeedback(feedback)) {
            return Result.success(feedback);
        } else {
            return Result.error(CodeMsg.FBSAVE_FAIL);
        }
    }

    @RequestMapping(value = "/getList", method = GET)
    public Result getFeedbackList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<Feedback> feedbackList = feedbackService.getFeedbackList(page, limit);
        Integer count = feedbackService.getFeedbackListCount();
        return Result.page(feedbackList, count);
    }

    @RequestMapping(value = "/read", method = POST)
    public Result readFeedback(@RequestParam("feedbackId") Integer feedbackId, HttpSession session) {
        if (feedbackId == null) {
            return Result.error(CodeMsg.PARAMETER_NULL);
        }
        User user = userService.getSession(session);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_NOTEXISTS);
        }
        Feedback feedback = new Feedback();
        feedback.setFeedbackId(feedbackId);
        feedback.setStatus(1);
        if (feedbackService.modifyFeedback(feedback)) {
            return Result.success(null);
        } else {
            return Result.error(CodeMsg.FBREAD_FAIL);
        }
    }

}
