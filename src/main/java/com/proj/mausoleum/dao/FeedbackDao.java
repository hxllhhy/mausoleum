package com.proj.mausoleum.dao;

import com.proj.mausoleum.pojo.Feedback;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedbackDao {

    Integer insertFeedback(Feedback feedback);

    List<Feedback> selectFeedbackList();

    Integer countFeedbackList();

    Integer updateFeedback(Feedback feedback);
}
