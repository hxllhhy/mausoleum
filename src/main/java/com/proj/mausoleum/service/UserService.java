package com.proj.mausoleum.service;

import com.proj.mausoleum.pojo.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    Boolean saveUser(User user);

    User getUser(User user);

    User getSession(HttpSession session);

    Boolean modifyUser(User user);

    List<User> getUserList(Integer page, Integer limit);

    Integer getUserListCount();

    Boolean destroyUser(Integer userId);
}
