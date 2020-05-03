package com.proj.mausoleum.service.impl;

import com.github.pagehelper.PageHelper;
import com.proj.mausoleum.dao.FeedbackDao;
import com.proj.mausoleum.pojo.Feedback;
import com.proj.mausoleum.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackDao feedbackDao;

    public Boolean saveFeedback(Feedback feedback) {
        return feedbackDao.insertFeedback(feedback) == 1;
    }

    public List<Feedback> getFeedbackList(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return feedbackDao.selectFeedbackList();
    }

    public Integer getFeedbackListCount() {
        return feedbackDao.countFeedbackList();
    }

    public Boolean modifyFeedback(Feedback feedback) {
        return feedbackDao.updateFeedback(feedback) == 1;
    }
}
