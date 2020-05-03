package com.proj.mausoleum.service.impl;

import com.github.pagehelper.PageHelper;
import com.proj.mausoleum.dao.UserDao;
import com.proj.mausoleum.pojo.User;
import com.proj.mausoleum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public Boolean saveUser(User user) {
        return userDao.insertUser(user) == 1;
    }

    public User getUser(User user) {
        return userDao.selectUser(user);
    }

    public User getSession(HttpSession session) {
        return (User) session.getAttribute("loginUser");
    }

    @Override
    public Boolean modifyUser(User user) {
        return userDao.updateUser(user) == 1;
    }

    @Override
    public List<User> getUserList(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        return userDao.selectUserList();
    }

    @Override
    public Integer getUserListCount() {
        return userDao.countUserList();
    }

    @Override
    public Boolean destroyUser(Integer userId) {
        return userDao.deleteUserByUserId(userId) == 1;
    }
}
