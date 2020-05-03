package com.proj.mausoleum.service;


import com.proj.mausoleum.pojo.Feedback;

import java.util.List;

public interface FeedbackService {

    Boolean saveFeedback(Feedback feedback);

    List<Feedback> getFeedbackList(Integer page, Integer limit);

    Integer getFeedbackListCount();

    Boolean modifyFeedback(Feedback feedback);
}
